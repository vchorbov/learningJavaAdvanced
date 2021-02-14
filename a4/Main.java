import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        LocalDateTime ldFuture = LocalDateTime.of(2022,2,2,22,21);
        LocalDateTime ldPast = LocalDateTime.of(2020,2,2,22,21);



        int resultFuture = ldFuture.compareTo(LocalDateTime.now());
        int resultPast = ldPast.compareTo(LocalDateTime.now());
        int a = 0;
    }


}
