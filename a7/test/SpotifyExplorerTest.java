import org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class SpotifyExplorerTest {

    private static final String SPOTIFY_DATA = """
                id,artists,name,year,popularity,duration_ms,tempo,loudness,valence,acousticness,danceability,energy,liveness,speechiness,explicit
            3ftBPsC5vPBKxYSee08FDH,['Frank Parker'],Danny Boy,1921,3,210000,100.109,-9.316,0.165,0.967,0.275,0.309,0.381,0.0354,0
            08zfJvRLp7pjAb94MA9JmF,['Fortugé'],Il Etait Syndiqué,1921,0,196560,109.378,-16.415,0.771,0.982,0.684,0.257,0.504,0.399,0
            0H3k2CvJvHULnWChlbeFgx,['Georgel'],La Vipère,1922,0,190800,174.532,-12.562,0.493,0.99,0.315,0.363,0.292,0.0546,0
            1EP3FZb36DEY1RsrZiZ4lD,['Georgel'],Ole Bitch,1927,0,182622,88.408,-3.178,0.666,0.0486,0.436,0.709,0.168,0.281,1
            """;
    private static final String SPOTIFY_DATA_SAME_NAME = """
            id,artists,name,year,popularity,duration_ms,tempo,loudness,valence,acousticness,danceability,energy,liveness,speechiness,explicit
            19P3z6k36DEY1RsrZgZll9,['Ron'],Ole Bitch,1997,89,182622,88.408,-3.178,0.666,0.0486,0.436,0.709,0.168,0.281,1
            1EPFZb36DEY1rsf333dZ4lD,['Ron'],Ole Bitch,1992,92,182622,88.408,-3.178,0.666,0.0486,0.436,0.709,0.168,0.281,1
            """;
    private static final String SPOTIFY_DATA_80s = """
            id,artists,name,year,popularity,duration_ms,tempo,loudness,valence,acousticness,danceability,energy,liveness,speechiness,explicit
            19P3z6k36DEY1RsrZgZll9,['Ron'],Ole Bitch,1987,0,182622,88.408,-3.178,0.666,0.0486,0.436,0.709,0.168,0.281,1
            1EPFZb36DEY1rsf333dZ4lD,['Ron'],Ole Bitch,1982,0,182622,88.408,-3.178,0.999,0.0486,0.436,0.709,0.168,0.281,1
            1EPFZb36DEY1rsf333dZ4lD,['Ron'],Ole Bitch,1990,0,182622,88.408,-3.178,0.899,0.0486,0.436,0.709,0.168,0.281,1
            """;


    private final SpotifyTrack t1 = SpotifyTrack.of("3ftBPsC5vPBKxYSee08FDH,['Frank Parker'],Danny Boy,1921,3,210000,100.109,-9.316,0.165,0.967,0.275,0.309,0.381,0.0354,0");
    private final SpotifyTrack t2 = SpotifyTrack.of("08zfJvRLp7pjAb94MA9JmF,['Fortugé'],Il Etait Syndiqué,1921,0,196560,109.378,-16.415,0.771,0.982,0.684,0.257,0.504,0.399,0");
    private final SpotifyTrack t3 = SpotifyTrack.of("0H3k2CvJvHULnWChlbeFgx,['Georgel'],La Vipère,1922,0,190800,174.532,-12.562,0.493,0.99,0.315,0.363,0.292,0.0546,0");
    private final SpotifyTrack t4 = SpotifyTrack.of("1EP3FZb36DEY1RsrZiZ4lD,['Georgel'],Ole Bitch,1927,0,182622,88.408,-3.178,0.666,0.0486,0.436,0.709,0.168,0.281,1");


    private final SpotifyTrack v1 = SpotifyTrack.of("19P3z6k36DEY1RsrZgZll9,['Ron'],Ole Bitch,1987,0,182622,88.408,-3.178,0.666,0.0486,0.436,0.709,0.168,0.281,1");
    private final SpotifyTrack v2 = SpotifyTrack.of("1EPFZb36DEY1rsf333dZ4lD,['Ron'],Ole Bitch,1982,0,182622,88.408,-3.178,0.999,0.0486,0.436,0.709,0.168,0.281,1");

    @Test
    public void testGetAllSpotifyTracksWhichDropsTheFirstLine() throws IOException {
        Reader reader = new StringReader(SPOTIFY_DATA);
        SpotifyExplorer explorer = new SpotifyExplorer(reader);
        Collection<SpotifyTrack> result = explorer.getAllSpotifyTracks();

        assertEquals("The length of the set must be equal to the length of the string-1", result.size(), 4);

    }

    @Test
    public void testGetAllSpotifyTracksAgainstExpected() throws IOException {
        Reader reader = new StringReader(SPOTIFY_DATA);
        SpotifyExplorer explorer = new SpotifyExplorer(reader);
        Collection<SpotifyTrack> result = explorer.getAllSpotifyTracks();

        Set<SpotifyTrack> expected = Set.of(t1, t2, t3, t4);
        assertEquals("The length of the set must be equal to the length of the string-1", expected, result);

    }

    @Test
    public void testGetExplicitSpotifyTracks() {
        Reader reader = new StringReader(SPOTIFY_DATA);
        SpotifyExplorer explorer = new SpotifyExplorer(reader);
        Collection<SpotifyTrack> explicit = explorer.getExplicitSpotifyTracks();
        //set of the only explicit record
        Collection<SpotifyTrack> reference = Set.of(t4);

        assertEquals("The two sets should be equal", reference, explicit);
    }

    @Test
    public void testGroupSpotifyTracksByYear() {
        Reader reader = new StringReader(SPOTIFY_DATA);
        SpotifyExplorer explorer = new SpotifyExplorer(reader);

        Map<Integer, Set<SpotifyTrack>> reference = Map.of(1921, Set.of(t1, t2), 1922, Set.of(t3), 1927, Set.of(t4));

        assertEquals("The two maps must be equal", reference, explorer.groupSpotifyTracksByYear());
    }

    @Test
    public void testGetArtistActiveYears() {
        Reader reader = new StringReader(SPOTIFY_DATA_SAME_NAME);
        SpotifyExplorer explorer = new SpotifyExplorer(reader);


        assertEquals("The two years must be equal", 5, explorer.getArtistActiveYears("Ron"));
    }

    @Test
    public void testGetTopNHighestValenceTracksFromThe80s() {
        Reader reader = new StringReader(SPOTIFY_DATA_80s);
        SpotifyExplorer explorer = new SpotifyExplorer(reader);

        List<SpotifyTrack> topValences = List.of(v2, v1);

        assertEquals("The two valences must be equal", topValences, explorer.getTopNHighestValenceTracksFromThe80s(2));
    }
    @Test
    public void testGetMostPopularTrackFromThe90s() {
        Reader reader = new StringReader(SPOTIFY_DATA_SAME_NAME);
        SpotifyExplorer explorer = new SpotifyExplorer(reader);
        SpotifyTrack popular =  SpotifyTrack.of("1EPFZb36DEY1rsf333dZ4lD,['Ron'],Ole Bitch,1992,92,182622,88.408,-3.178,0.666,0.0486,0.436,0.709,0.168,0.281,1");

        assertEquals("The two valences must be equal",popular, explorer.getMostPopularTrackFromThe90s());
    }
}
