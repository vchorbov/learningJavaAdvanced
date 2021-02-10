package leisure.time.content;

public enum PgRating {
    G("General audience"), // it is available for everyone
    PG13("May be inappropriate for children under 13"), // it is available only for people aged 14 and over
    NC17("Adults Only"); // it is available only for people aged 18 and over

    private final String details;

     private PgRating(String details) {
        this.details = details;
    }
}
