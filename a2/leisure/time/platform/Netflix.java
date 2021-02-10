package leisure.time.platform;

import leisure.time.account.Account;
import leisure.time.content.PgRating;
import leisure.time.content.Stream;
import leisure.time.content.Streamable;
import leisure.time.exceptions.ContentNotFoundException;
import leisure.time.exceptions.ContentUnavailableException;
import leisure.time.exceptions.UserNotFoundException;

import java.time.LocalDate;

public class Netflix implements StreamingService{
    private static int CONTENT_RESTRICTION_13 = 13;
    private static int CONTENT_RESTRICTION_17 = 17;


    private Account[] accounts;
    private Streamable[] streamableContent;
    private int totalTimeWatched;

    public Netflix(Account[] accounts, Streamable[] streamableContent){
        this.accounts = accounts;
        this.streamableContent = streamableContent;
        this.totalTimeWatched = 0;

    }
    /**
     * Simulates watching activity for the given user.
     * @param user the user that will watch the video. The user must be registered in the platform in order to access its contents.
     * @param videoContentName the exact name of the video content: movie or series
     *                         If the content is of type Series, we assume that the user will watch all episodes in it.
     * @throws ContentUnavailableException if the content is age restricted and the user is not yet permitted to access it.
     * @throws UserNotFoundException if the user is not registered in the platform.
     * @throws ContentNotFoundException if the content is not present in the platform.
     */
    @Override
    public void watch(Account user, String videoContentName) throws ContentUnavailableException {
        Stream toWatch = null;
        try{
            findUser(user);
        }catch (UserNotFoundException unf){
            System.err.println("There is no such account. Please, register to gain access to the platform content!");
            return;
        }
        try {
          toWatch = (Stream) findByName(videoContentName);

        }catch (ContentNotFoundException cnf){
            System.err.println("This content does not exist or is no longer available!");
            return;
        }
        checkIfContentIsAppropriate(user, toWatch);
        toWatch.watch();


        totalTimeWatched += toWatch.getDuration();

    }
    /**
     * @param videoContentName the exact name of the video content: movie or series
     * @return the Streamable resource with name that matches the provided name or null if no such content exists in the platform.
     */
    @Override
    public Streamable findByName(String videoContentName) throws ContentNotFoundException {
        for (Streamable s: streamableContent) {
            Stream stream = (Stream) s;
            if(stream.getTitle().equals(videoContentName)){
                return stream;
            }

        }
         throw new ContentNotFoundException();
    }
    /**
     * @return the most watched Streamable resource available in the platform or null if no streams were done yet.
     */
    @Override
    public Streamable mostViewed() {
        int maxViews = 0;
        Streamable mostViewed = null;
        for (Streamable s : streamableContent) {
            Stream stream = (Stream) s;
            if(stream.getTimesWatched()>maxViews){
                mostViewed = (Streamable) stream;
            }
        }
        return mostViewed;
    }

    @Override
    public int totalWatchedTimeByUsers(){
        return totalTimeWatched;
    }

    private Account findUser(Account toFind) throws UserNotFoundException{
        for (Account a : accounts) {
            if(a.equals(toFind)){
                return a;
            }
        }
        throw new UserNotFoundException();
    }

    private boolean checkIfContentIsAppropriate(Account account, Stream stream) throws ContentUnavailableException{
        if(stream.getRating().equals(PgRating.G)){
            return true;
        }else{

            LocalDate now = LocalDate.now();
            int age = now.compareTo(account.getBirthdayDate());

            if(stream.getRating().equals(PgRating.PG13)
            && age > CONTENT_RESTRICTION_13){
                return true;
            }else if(stream.getRating().equals(PgRating.NC17)
            && age > CONTENT_RESTRICTION_17){
                return true;
            }
        }
        throw new ContentUnavailableException();
    }
}
