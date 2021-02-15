package collections;

import exceptions.ItemNotFoundException;
import item.Item;
import product.ProductCatalog;
import product.ProductInfo;

import java.util.*;

public class MapShoppingCart implements ShoppingCart {

    public Map<Item, Integer> items;
    public ProductCatalog catalog;

    public MapShoppingCart(ProductCatalog catalog) {
        this.catalog = catalog;
        //maybe TreeMap
        this.items = new HashMap<>();
    }

    public Collection<Item> getUniqueItems() {
        // the Set interface guarantees that the Items inside it will be unique
        Collection<Item> i = new HashSet<>();
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            i.add(entry.getKey());
        }
        return i;
    }

    @Override
    public void addItem(Item item) {
        if (item != null) {
            if (items.containsKey(item)) {
                Integer i = items.get(item);
                items.put(item, items.get(item) + 1);
            } else {
                items.put(item, 1);
            }
        }
    }

    @Override
    public void removeItem(Item item) {
        if (item == null || !items.containsKey(item)) {
            throw new ItemNotFoundException();
        }

        Integer occurrences = items.get(item);
        if (--occurrences == 0) {
            items.remove(item);
        } else {
            items.put(item, occurrences - 1);
        }
    }

    @Override
    public double getTotal() {
        double total = 0;
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            ProductInfo info = catalog.getProductInfo(entry.getKey().getId());
            total += info.price() * entry.getValue();
        }
        return total;
    }

    @Override
    public Collection<Item> getSortedItems() {
        List<Item> sortedItems = new ArrayList<>(items.keySet());
        Collections.sort(sortedItems, new Comparator<Item>() {
            @Override
            public int compare(Item item1, Item item2) {
                ProductInfo info1 = catalog.getProductInfo(item1.getId());
                ProductInfo info2 = catalog.getProductInfo(item2.getId());
                if (info1.price() > info2.price()) {
                    return -1;
                } else if (info1.price() < info2.price()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        return sortedItems;
    }

}