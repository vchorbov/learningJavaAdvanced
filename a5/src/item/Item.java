package item;

public interface Item extends Comparable<Item> {

    String getId();

    @Override
    default public int compareTo(Item i){
        if(getId().equals(getId())) return 0;
        if(getId().length() >= getId().length()) return 1;
        return 0;
    }
}
