package restaurant.customer;

import restaurant.Restaurant;

public class VipCustomer extends AbstractCustomer {

    public VipCustomer(Restaurant restaurant) {
        super(restaurant);
    }

    @Override
    public boolean hasVipCard() {
        return true;
    }
}
