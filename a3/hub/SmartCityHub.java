package hub;

import device.*;
import enums.DeviceType;
import hub.DeviceAlreadyRegisteredException;
import hub.DeviceNotFoundException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;

public class SmartCityHub {
    private static final String DEVICE_CANNOT_BE_NULL_MESSAGE = "Provided device cannot be null";
    private static final String N_CANNOT_BE_NEGATIVE_MESSAGE = "Provided value for 'n' cannot be negative";
    Set<SmartDevice> hash;
    List<SmartDevice> listOfRegisteredDevices;


    public SmartCityHub() {
        this.hash = new TreeSet<>();
        this.listOfRegisteredDevices = new ArrayList<>();

    }

    /**
     * Adds a @device to the SmartCityHub.
     *
     * @throws IllegalArgumentException         in case @device is null.
     * @throws DeviceAlreadyRegisteredException in case the @device is already registered.
     */
    public void register(SmartDevice device) throws DeviceAlreadyRegisteredException {
        checkIfNull(device);

        if (checkIfPresentInTheHash(device)) {
            throw new UnsupportedOperationException();
        } else {
            hash.add(device);
            listOfRegisteredDevices.add(device);
        }


    }

    /**
     * Removes the @device from the SmartCityHub.
     *
     * @throws IllegalArgumentException in case null is passed.
     * @throws DeviceNotFoundException  in case the @device is not found.
     */
    public void unregister(SmartDevice device) throws DeviceNotFoundException {
        checkIfNull(device);

        if (!checkIfPresentInTheHash(device)) {
            throw new UnsupportedOperationException();
        } else {
            hash.remove(device);
            listOfRegisteredDevices.add(device);
        }


    }

    /**
     * Returns a SmartDevice with an ID @id.
     *
     * @throws IllegalArgumentException in case @id is null.
     * @throws DeviceNotFoundException  in case device with ID @id is not found.
     */
    public SmartDevice getDeviceById(String id) throws DeviceNotFoundException {
        if (id == null) throw new IllegalArgumentException();

        for (SmartDevice sd : hash) {
            if (sd.getId().equals(id)) {
                return sd;
            }
        }
        throw new UnsupportedOperationException("Such device does not exist");
    }


    /**
     * Returns the total number of devices with type @type registered in SmartCityHub.
     *
     * @throws IllegalArgumentException in case @type is null.
     */
    public int getDeviceQuantityPerType(DeviceType type) {
        if (type == null) throw new IllegalArgumentException();
        switch (type) {
            case LAMP -> {
                return SmartLamp.getLampQuantity();
            }
            case CAMERA -> {
                return SmartCamera.getCameraQuantity();
            }
            case TRAFFIC_LIGHT -> {

                return SmartTrafficLight.getTrafficLightQuantity();
            }

        }
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a collection of IDs of the top @n devices which consumed
     * the most power from the time of their installation until now.
     * <p>
     * The total power consumption of a device is calculated by the hours elapsed
     * between the two LocalDateTime-s: the installation time and the current time (now)
     * multiplied by the stated nominal hourly power consumption of the device.
     * <p>
     * If @n exceeds the total number of devices, return all devices available sorted by the given criterion.
     *
     * @throws IllegalArgumentException in case @n is a negative number.
     */
    public Collection<String> getTopNDevicesByPowerConsumption(int n) {
        if (n < 0) throw new IllegalArgumentException(N_CANNOT_BE_NEGATIVE_MESSAGE);
        if (n >= hash.size()) n = hash.size();

        List<String> result = hash.stream().map(x -> x.getId()).collect(Collectors.toList());
        return result;
    }

    /**
     * Returns a collection of the first @n registered devices, i.e the first @n that were added
     * in the SmartCityHub (registration != installation).
     * <p>
     * If @n exceeds the total number of devices, return all devices available sorted by the given criterion.
     *
     * @throws IllegalArgumentException in case @n is a negative number.
     */
    public Collection<SmartDevice> getFirstNDevicesByRegistration(int n) {
        if (n < 0) throw new IllegalArgumentException(N_CANNOT_BE_NEGATIVE_MESSAGE);

        if (n > listOfRegisteredDevices.size()) n = listOfRegisteredDevices.size();
        // then use subList();
        return listOfRegisteredDevices.subList(0, n);


    }

    // checks
    private void checkIfNull(SmartDevice device) {
        if (device == null) {
            throw new IllegalArgumentException(DEVICE_CANNOT_BE_NULL_MESSAGE);
        }
    }

    private boolean checkIfPresentInTheHash(SmartDevice device) {
        return hash.contains(device);
    }
}
