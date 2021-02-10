package leisure.time.platform;

import leisure.time.account.Account;
import leisure.time.content.Streamable;
import leisure.time.exceptions.ContentNotFoundException;
import leisure.time.exceptions.ContentUnavailableException;
import leisure.time.exceptions.UserNotFoundException;

import java.io.IOException;

public interface StreamingService {


        /**
         * Simulates watching activity for the given user.
         * @param user the user that will watch the video. The user must be registered in the platform in order to access its contents.
         * @param videoContentName the exact name of the video content: movie or series
         *                         If the content is of type Series, we assume that the user will watch all episodes in it.
         * @throws ContentUnavailableException if the content is age restricted and the user is not yet permitted to access it.
         * @throws UserNotFoundException if the user is not registered in the platform.
         * @throws ContentNotFoundException if the content is not present in the platform.
         */
        void watch(Account user, String videoContentName) throws ContentUnavailableException;


        /**
         * @param videoContentName the exact name of the video content: movie or series
         * @return the Streamable resource with name that matches the provided name or null if no such content exists in the platform.
         */
        Streamable findByName(String videoContentName) throws ContentNotFoundException;

        /**
         * @return the most watched Streamable resource available in the platform or null if no streams were done yet.
         */
        Streamable mostViewed();

        /**
         * @return the minutes spent by all users registered in the platform while watching streamable content.
         */
        int totalWatchedTimeByUsers();

    }

