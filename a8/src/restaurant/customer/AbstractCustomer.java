package restaurant.customer;

import restaurant.Meal;
import restaurant.Order;
import restaurant.Restaurant;

import java.util.Random;

public abstract class AbstractCustomer extends Thread {

    private static final Random RANDOM = new Random();

    private final Restaurant restaurant;

    public AbstractCustomer(Restaurant restaurant) {

        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        try {
            /*
             * this will sleep the thread up to 5sec which represents the time,
             *  in which the customer is choosing a meal
             */
            Thread.sleep(RANDOM.nextInt(5000));
        }catch (InterruptedException ex){
            // Not suppose to happen
            System.err.print("Unexpected exception was thrown: " + ex.getMessage());
            ex.printStackTrace();
        }

        restaurant.submitOrder(new Order(Meal.chooseFromMenu(), this));
    }

    public abstract boolean hasVipCard();
}