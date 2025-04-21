import java.time.LocalDate;

public class Expense {
    private String category;
    private double amount;
    private String description;
    private LocalDate date;

    public Expense(String category, double amount, String description, LocalDate date) {
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    public String toString() {
        return "[" + date + "] " + category + ": ₹" + amount + " - " + description;
    }
}
