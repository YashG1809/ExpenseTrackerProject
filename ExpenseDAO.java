import java.sql.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

public class ExpenseDAO {

    public static void addExpense(int userId, Expense expense) {
        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO expenses(user_id, category, amount, description, date) VALUES(?, ?, ?, ?, ?)");
            stmt.setInt(1, userId);
            stmt.setString(2, expense.getCategory());
            stmt.setDouble(3, expense.getAmount());
            stmt.setString(4, expense.getDescription());
            stmt.setDate(5, java.sql.Date.valueOf(expense.getDate()));

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Expense> getExpensesByUser(int userId) {
        List<Expense> list = new ArrayList<>();
        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM expenses WHERE user_id = ?");
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Expense expense = new Expense(
                    rs.getString("category"),
                    rs.getDouble("amount"),
                    rs.getString("description"),
                    rs.getDate("date").toLocalDate()
                );
                list.add(expense);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
