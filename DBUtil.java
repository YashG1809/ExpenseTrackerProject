public class DBUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/your_db_name";
    private static final String USER = "your_real_username";
    private static final String PASSWORD = "your_real_password";

    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
