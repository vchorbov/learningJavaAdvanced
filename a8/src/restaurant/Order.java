package restaurant;

import restaurant.customer.AbstractCustomer;

public record Order(Meal meal, AbstractCustomer customer) implements Comparable<Order> {
    /* data-class, which describes the order and the customer to whom it belongs
     * we are making the assumption that we will only have one order per customer
     * for simplicity´s sake
     */
    @Override
    public int compareTo(Order o) {
        boolean currHasVipCard = this.customer.hasVipCard();
        boolean oHasVipCard = o.customer.hasVipCard();

        if (currHasVipCard == oHasVipCard) {
            return Integer.compare(this.meal.getCookingTime(), o.meal.getCookingTime());
        }

        if (currHasVipCard) {
            return 1;
        }

        return -1;
    }
}
