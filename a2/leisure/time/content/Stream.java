package leisure.time.content;

import java.util.Objects;

public abstract class Stream implements Streamable{
    private String name;
    private Genre genre;
    private PgRating rating;
    private int watchedCounter =0;
    public Stream (String name, Genre genre, PgRating rating){
        this.name = name;
        this.genre = genre;
        this.rating = rating;
    }

    @Override
    public String getTitle(){
        return this.name;
    }

    @Override
    public abstract int getDuration();

    @Override
    public PgRating getRating(){
        return this.rating;
    }

    public void watch(){
        watchedCounter++;
        //return this.name;
    }
    public int getTimesWatched(){
        return watchedCounter;
    }
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if (!(o instanceof Stream)) return false;
        Stream stream = (Stream) o;
        return (name.equals(stream.getTitle())
        && genre == stream.genre
        && rating == stream.rating);
    }
    @Override
    public int hashCode(){
        return Objects.hash(name,genre,rating);
    }

}
