package leisure.time.content;

public class Movie extends Stream {

    private int duration;

    public Movie(String name, Genre genre, PgRating rating, int duration){
        super(name, genre,rating);
        this.duration = duration;

    }

    @Override
    public int getDuration(){
        return this.duration;
    }
}
