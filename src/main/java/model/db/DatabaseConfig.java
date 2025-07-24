package model.db;

public class DatabaseConfig {
    
    private static final String NAME = System.getenv("DB_NAME");
    private static final String HOST = System.getenv("DB_HOST");
    
    private static String url = "jdbc:mysql://" + HOST + "/" + NAME + "?sslMode=VERIFY_IDENTITY";
    private static String user = System.getenv("DB_USERNAME");
    private static String password = System.getenv("DB_PASSWORD");

    public static String getUrl() {
        return url;
    }

    public static String getUser() {
        return user;
    }

    public static String getPassword() {
        return password;
    }
    
}
