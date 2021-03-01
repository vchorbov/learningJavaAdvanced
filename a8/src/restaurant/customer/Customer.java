package restaurant.customer;

import restaurant.Restaurant;

public  class Customer extends AbstractCustomer{

    public Customer(Restaurant restaurant) {
        super(restaurant);
    }

    @Override
    public boolean hasVipCard() {
        return false;
    }
}
