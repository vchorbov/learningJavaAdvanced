package delivery;

import delivery.DeliveryServiceWarehouse;
import exceptions.CapacityExceededException;
import exceptions.CapacityExceedsAndNoProlongedParcelsException;
import exceptions.ParcelNotFoundException;

import java.awt.event.MouseAdapter;
import java.time.LocalDateTime;
import java.util.*;

public class MJTExpressWarehouse<L, P> implements DeliveryServiceWarehouse<L, P> {
    private int capacity;
    private int retentionPeriod;

    /**
     * Creates a new instance of delivery.MJTExpressWarehouse with the given characteristics
     *
     * @param capacity        the total number of parcels that the warehouse can store
     * @param retentionPeriod the maximum number of days for which a parcel can stay in the warehouse, counted from the day the parcel
     * was submitted. After that time passes, the parcel can be removed from the warehouse
     */

    private Map<MJTExpressLabel<L>, P> storage;

    public MJTExpressWarehouse(int capacity, int retentionPeriod) {
        this.capacity = capacity;
        this.retentionPeriod = retentionPeriod;
        this.storage = new TreeMap<>();


    }


    @Override
    public void submitParcel(L label, P parcel, LocalDateTime submissionDate) throws CapacityExceededException {
        if (checkIfDateInTheFuture(submissionDate)
                || label == null
                || parcel == null
                || submissionDate == null) {
            throw new IllegalArgumentException("Invalid input. Inputs cannot be null.");
        }

        if (storageIsFull()) {
            try {
                MJTExpressLabel<L> prolonged = checkForProlongedParcels();
                storage.remove(prolonged);
                storage.put(new MJTExpressLabel<L>(label, submissionDate), parcel);
            } catch (CapacityExceedsAndNoProlongedParcelsException ex) {
                throw new CapacityExceededException(String.format("Warehouse with capacity %d has no more free space available", capacity));

            }

        } else {
            storage.put(new MJTExpressLabel<L>(label, submissionDate), parcel);
        }


    }

    @Override
    public P getParcel(L label) {
        if (label == null) throw new IllegalArgumentException("Provided label cannot be null");

        return this.storage.get(wrapLabel(label));
    }

    @Override
    public P deliverParcel(L label) throws ParcelNotFoundException {
        if (label == null) {
            throw new IllegalArgumentException("Provided label cannot be null");
        }

        MJTExpressLabel<L> key = wrapLabel(label);
        P parcel = this.storage.get(key);
        if (parcel == null) {
            throw new ParcelNotFoundException(String.format("Parcel with label %s not found in warehouse", label));
        }

        this.storage.remove(key);
        return parcel;
    }

    @Override
    public double getWarehouseSpaceLeft() {
        return (double) 1 - (this.storage.size() / (double) this.capacity);
    }

    @Override
    public Map<L, P> getWarehouseItems() {
        Map<L, P> newMap = new HashMap<>();
        for (Map.Entry<MJTExpressLabel<L>, P> entry : storage.entrySet()) {
            newMap.put(entry.getKey().getLabel(), entry.getValue());
        }

        return newMap;
    }

    /**
     * Removes all items submitted before the given date from the warehouse, and returns them
     *
     * @param before the date that is used for filtering the items
     * @return the items that will be delivered. If there are no items submitted before the given date,
     * the returned Map is empty. If the given date is in the future, all items in the warehouse are returned
     * @throws IllegalArgumentException when the given date is null
     */

    @Override
    public Map<L, P> deliverParcelsSubmittedBefore(LocalDateTime before) {
        Map<L, P> beforeMap = new HashMap<>();
        if (before == null) {
            throw new IllegalArgumentException("Provided before date cannot be null");
        }

        if (before.isAfter(LocalDateTime.now())) {
            return getWarehouseItems();
        }

        Set<MJTExpressLabel<L>> keys = storage.keySet();
        for (MJTExpressLabel<L> label : keys) {
            if (label.getCreationDate().isBefore(before)) {
                beforeMap.put(label.getLabel(), storage.get(label.getLabel()));
            }

        }
        return beforeMap;
    }

    /**
     * Removes all items submitted after the given date from the warehouse, and returns them
     *
     * @param after the date that is used for filtering the items
     * @return the items that will be delivered. If there are no items submitted after the given date,
     * the returned Map is empty. An empty Map is returned if the given date is in the future
     * @throws IllegalArgumentException when the given date is null
     */
    @Override
    public Map<L, P> deliverParcelsSubmittedAfter(LocalDateTime after) {
        Map<L, P> afterMap = new HashMap<>();
        if (after == null) {
            throw new IllegalArgumentException("Provided before date cannot be null");
        }
        // is in the future
        if (after.isAfter(LocalDateTime.now())) {
            return afterMap;
        }
        Set<MJTExpressLabel<L>> keys = storage.keySet();
        for (MJTExpressLabel<L> label : keys) {
            if (label.getCreationDate().isAfter(after)) {
                afterMap.put(label.getLabel(), storage.get(label.getLabel()));
            }

        }
        return afterMap;

    }

    private boolean checkIfDateInTheFuture(LocalDateTime submissionDate) {
        int result = submissionDate.compareTo(LocalDateTime.now());
        // 1 - future // 0 - the exact same moment // -1 - past
        return (result > 0);
    }

    private boolean storageIsFull() {
        return storage.size() >= capacity;
    }

    private MJTExpressLabel<L> checkForProlongedParcels() throws CapacityExceedsAndNoProlongedParcelsException {
        for (MJTExpressLabel<L> labelObject : storage.keySet()) {
            if (labelObject.getCreationDate()
                    .plusDays(retentionPeriod)
                    .isAfter(LocalDateTime.now())) {
                return labelObject;
            }

        }

        throw new CapacityExceedsAndNoProlongedParcelsException
                (String.format("Warehouse with capacity %d has no more free space available", capacity));
    }

    // when label are equal, MJTExpressLabel<T> are equal due to the overridden equals() method
    private MJTExpressLabel<L> wrapLabel(L label) {
        return new MJTExpressLabel<>(label, null);
    }
}

