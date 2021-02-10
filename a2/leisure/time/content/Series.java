package leisure.time.content;

public class Series extends Stream{


    private Episode[] episodes;

    public Series(String name, Genre genre, PgRating rating, Episode[] episodes){
        super(name, genre, rating);
        this.episodes = episodes;

    }

    @Override
    public int getDuration(){
       int totalDuration = 0;
        for (Episode e : episodes) {
            totalDuration += e.getEpisodeDuration();
        }
        return totalDuration;
    }
}
