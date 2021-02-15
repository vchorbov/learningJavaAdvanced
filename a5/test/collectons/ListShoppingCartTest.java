package collectons;

import collections.ListShoppingCart;
import item.Apple;
import item.Chocolate;
import item.Item;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.MockingDetails;
import product.ProductCatalog;
import product.ProductInfo;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ListShoppingCartTest {


    @Test
    public void testGetUniqueItemsViaAddItem() {
        ProductCatalog productCatalog = mock(ProductCatalog.class);
        ListShoppingCart list = new ListShoppingCart(productCatalog);
        list.addItem(new Apple("healthy"));
        list.addItem(new Chocolate("yam"));
        list.addItem(new Chocolate("yam"));

        Set<Item> reference = new TreeSet<>();
        reference.add(new Apple("healthy"));
        reference.add(new Chocolate("yam"));
        Collection<Item> result = list.getUniqueItems();

        assertEquals("The two Sets must have identical Items.", reference, result);

    }

    @Test
    public void testGetUniqueItemsViaRemoveItem() {
        ProductCatalog productCatalog = mock(ProductCatalog.class);
        ListShoppingCart list = new ListShoppingCart(productCatalog);
        list.addItem(new Apple("healthy"));
        list.addItem(new Chocolate("yam"));
        list.addItem(new Chocolate("yam"));
        list.removeItem(new Apple("healthy"));

        Set<Item> reference = new TreeSet<>();
        reference.add(new Chocolate("yam"));
        Collection<Item> result = list.getUniqueItems();

        assertEquals("The two Sets must have identical Items.", reference, result);

    }

    @Test
    public void testGetUniqueItemsViaRemoveItemToEmptySet() {
        ProductCatalog productCatalog = mock(ProductCatalog.class);
        ListShoppingCart list = new ListShoppingCart(productCatalog);
        list.addItem(new Apple("healthy"));
        list.addItem(new Chocolate("yam"));
        list.addItem(new Chocolate("yam"));
        list.removeItem(new Apple("healthy"));
        list.removeItem(new Chocolate("yam"));

        Set<Item> reference = new TreeSet<>();
        Collection<Item> result = list.getUniqueItems();

        Assert.assertEquals("The two Sets must have identical Items.", reference.size(), result.size(), 1);

    }

    @Test
    public void testGetTotal(){
        ProductCatalog productCatalog = mock(ProductCatalog.class);
        when(productCatalog.getProductInfo("healthy")).thenReturn(new ProductInfo("","",10.10));
        when(productCatalog.getProductInfo("yam")).thenReturn(new ProductInfo("","",10));



        ListShoppingCart list = new ListShoppingCart(productCatalog);
        list.addItem(new Apple("healthy"));
        list.addItem(new Chocolate("yam"));
        list.addItem(new Chocolate("yam"));
        list.addItem(new Apple("healthy"));
        list.addItem(new Chocolate("yam"));

        assertEquals("The total price of the Items must be 50.2, which comes from two Apples(10.10) and three Chocolates(10)"
                ,50.2,list.getTotal(),0.1);
    }

    @Test
    public void testGetSortedItems(){
        ProductCatalog productCatalog = mock(ProductCatalog.class);

        ListShoppingCart list = new ListShoppingCart(productCatalog);
        list.addItem(new Apple("healthy"));
        list.addItem(new Chocolate("yam"));
        list.addItem(new Chocolate("yam"));
        list.addItem(new Apple("healthy"));
        list.addItem(new Chocolate("yam"));

        // In descending order depending on the quantity
        Set<Item> reference = Set.of(new Chocolate("yam"),new Apple("healthy"));

        assertEquals("Equal sorted sets of Items, sorted on the quantity of each Item ", reference, list.getSortedItems());

    }

    @Test
    public void testGetSortedItemsWithTheSameAmount(){
        ProductCatalog productCatalog = mock(ProductCatalog.class);

        ListShoppingCart list = new ListShoppingCart(productCatalog);
        list.addItem(new Apple("healthy"));
        list.addItem(new Chocolate("yam"));



        // In descending order depending on the quantity
        Set<Item> reference = Set.of(new Apple("healthy"), new Chocolate("yam"));

        assertEquals("If two items have the same amount, the first taken, will be the first added.", reference, list.getSortedItems());

    }
}
