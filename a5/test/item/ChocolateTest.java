package item;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChocolateTest {

    @Test
    public void testGetId(){
        Chocolate chocolate = new Chocolate("tasty");

        assertEquals("The test is successful if the two ids match.", "tasty", chocolate.getId());
    }
}
