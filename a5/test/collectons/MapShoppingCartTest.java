package collectons;

import collections.MapShoppingCart;
import exceptions.ItemNotFoundException;
import item.Apple;
import item.Chocolate;
import item.Item;
import org.junit.Test;
import product.ProductCatalog;
import product.ProductInfo;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MapShoppingCartTest {

    @Test
    public void testGetUniqueItemsViaAddItem() {
        ProductCatalog productCatalog = mock(ProductCatalog.class);
        MapShoppingCart mapShoppingCart = new MapShoppingCart(productCatalog);
        mapShoppingCart.addItem(new Apple("healthy"));
        mapShoppingCart.addItem(new Apple("healthy"));
        mapShoppingCart.addItem(new Apple("healthy"));
        mapShoppingCart.addItem(new Chocolate("yam"));
        // the map should contain Apple("healthy"), 3 and Chocolate("yam"),1
        Set<Item> reference = Set.of(new Chocolate("yam"), new Apple("healthy"));

        assertEquals("", reference, mapShoppingCart.getUniqueItems());
    }


    @Test
    public void testGetUniqueItemsViaAddItemAndRemoveItemUntilCompletelyGone() {
        ProductCatalog productCatalog = mock(ProductCatalog.class);
        MapShoppingCart mapShoppingCart = new MapShoppingCart(productCatalog);
        mapShoppingCart.addItem(new Apple("healthy"));
        mapShoppingCart.addItem(new Apple("healthy"));
        mapShoppingCart.addItem(new Apple("healthy"));
        mapShoppingCart.addItem(new Chocolate("yam"));
        mapShoppingCart.removeItem(new Chocolate("yam"));

        Set<Item> reference = Set.of(new Apple("healthy"));

        assertEquals("", reference, mapShoppingCart.getUniqueItems());
    }

    @Test
    public void testGetUniqueItemsViaAddItemAndRemoveItem() {
        ProductCatalog productCatalog = mock(ProductCatalog.class);
        MapShoppingCart mapShoppingCart = new MapShoppingCart(productCatalog);
        mapShoppingCart.addItem(new Apple("healthy"));
        mapShoppingCart.addItem(new Chocolate("yam"));
        mapShoppingCart.addItem(new Apple("healthy"));
        mapShoppingCart.addItem(new Apple("healthy"));
        mapShoppingCart.addItem(new Chocolate("yam"));


        mapShoppingCart.removeItem(new Chocolate("yam"));

        Set<Item> reference = Set.of(new Apple("healthy"), new Chocolate("yam"));

        assertEquals("", reference, mapShoppingCart.getUniqueItems());
    }


    @Test(expected = ItemNotFoundException.class)
    public void testGetUniqueItemsViaAddItemAndRemoveItemThrowsException() {
        ProductCatalog productCatalog = mock(ProductCatalog.class);
        MapShoppingCart mapShoppingCart = new MapShoppingCart(productCatalog);
        mapShoppingCart.addItem(new Apple("healthy"));
        mapShoppingCart.removeItem(new Chocolate("yam"));

    }

    @Test(expected = ItemNotFoundException.class)
    public void testGetUniqueItemsViaAddItemAndRemoveItemThrowsExceptionNull() {
        ProductCatalog productCatalog = mock(ProductCatalog.class);
        MapShoppingCart mapShoppingCart = new MapShoppingCart(productCatalog);
        mapShoppingCart.addItem(new Apple("healthy"));
        mapShoppingCart.removeItem(null);

    }

    @Test
    public void testGetTotal() {
        ProductCatalog productCatalog = mock(ProductCatalog.class);
        MapShoppingCart mapShoppingCart = new MapShoppingCart(productCatalog);
        when(productCatalog.getProductInfo("healthy")).thenReturn(new ProductInfo("", "", 10.2));
        when(productCatalog.getProductInfo("yam")).thenReturn(new ProductInfo("", "", 18.3));

        mapShoppingCart.addItem(new Apple("healthy"));
        mapShoppingCart.addItem(new Chocolate("yam"));
        mapShoppingCart.addItem(new Apple("healthy"));
        assertEquals("", 38.7, mapShoppingCart.getTotal(), 0.1);
    }

    @Test
    public void testGetSortedItemsSortsTheItemsByPrice() {
        ProductCatalog productCatalog = mock(ProductCatalog.class);
        MapShoppingCart mapShoppingCart = new MapShoppingCart(productCatalog);
        when(productCatalog.getProductInfo("healthy")).thenReturn(new ProductInfo("", "", 10.2));
        when(productCatalog.getProductInfo("yam")).thenReturn(new ProductInfo("", "", 18.3));
        when(productCatalog.getProductInfo("expensive")).thenReturn(new ProductInfo("", "", 98.99));
        when(productCatalog.getProductInfo("affordable")).thenReturn(new ProductInfo("", "", 5.5));



        mapShoppingCart.addItem(new Chocolate("affordable"));
        mapShoppingCart.addItem(new Apple("expensive"));
        mapShoppingCart.addItem(new Chocolate("yam"));
        mapShoppingCart.addItem(new Apple("healthy"));

        List<Item> reference = List.of(new Apple("expensive"), new Chocolate("yam"),
                new Apple("healthy"),new Chocolate("affordable"));

        assertEquals("", reference, mapShoppingCart.getSortedItems());
    }
}
