package item;

import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class AppleTest {

    @Test
    public void testGetId(){
    Apple apple = new Apple("1234");
    assertEquals("The test is successful if the two ids match.","1234",apple.getId());
    }

}
