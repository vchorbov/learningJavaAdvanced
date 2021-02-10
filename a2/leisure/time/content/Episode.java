package leisure.time.content;

public class Episode {

    private String name;
    private int duration;

    Episode(String name, int duration){
        this.name = name;
        this.duration = duration;

    }

    public String getEpisodeName(){
        return this.name;
    }

    public int getEpisodeDuration(){
        return this.duration;
    }
}
