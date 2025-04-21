import java.sql.*;

public class UserDAO {

    public static User login(String username, String password) {
        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean register(String username, String password) {
        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO users(username, password) VALUES(?, ?)");
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Username already taken.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
