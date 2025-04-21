import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

public class ExpenseTracker {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<String> CATEGORIES = Arrays.asList(
            "Food and Drinks", "Clothing", "Rent", "Recharges",
            "Recreation", "Education", "Travel"
    );

    public static void main(String[] args) {
        System.out.println("==== Welcome to Expense Tracker ====");

        User currentUser = loginOrRegister();

        boolean running = true;
        while (running) {
            System.out.println("\n-- Menu --");
            System.out.println("1. Add Expense");
            System.out.println("2. View Monthly Report");
            System.out.println("3. View Last 3 Months Summary");
            System.out.println("4. View All Expenses");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addExpense(currentUser);
                case 2 -> viewMonthlyReport(currentUser);
                case 3 -> viewLast3Months(currentUser);
                case 4 -> viewAllExpenses(currentUser);
                case 5 -> {
                    System.out.println("Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static User loginOrRegister() {
        while (true) {
            System.out.println("==== Welcome to Expense Tracker ====");
            System.out.println("1. Login");
            System.out.println("2. Sign Up");
            System.out.println("3. Exit");
            System.out.print("Choose: ");
            String option = scanner.nextLine();
    
            if (option.equals("3")) {
                System.out.println("Exiting... Goodbye!");
                System.exit(0);
            }

            System.out.println("To continue enter 5. To go back to Login or Signup enter 0 ");
            String zero=scanner.nextLine();
            if(zero.equals("0")){
                
                continue;

            }

    
            
    
            if (option.equals("1")) {

                System.out.print("Enter username: ");
            String username = scanner.nextLine();
    
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
                
                User user = UserDAO.login(username, password);
                if (user != null) return user;
                System.out.println("Invalid credentials.\n");
            } else if (option.equals("2")) {
                System.out.print("Enter username: ");
            String username = scanner.nextLine();
    
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            
                if (UserDAO.register(username, password)) {
                    System.out.println("Registration successful. Logging in...");
                    return UserDAO.login(username, password);
                } else {
                    System.out.println("Username already exists. Try a different one.\n");
                }
            } else {
                System.out.println("Invalid option.\n");
            }
        }
    }
    

    private static void addExpense(User user) {
        System.out.println("Select category:");
        for (int i = 0; i < CATEGORIES.size(); i++) {
            System.out.println((i + 1) + ". " + CATEGORIES.get(i));
        }
        System.out.print("Choose option: ");
        int categoryChoice = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter amount Rs.: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter description: ");
        String desc = scanner.nextLine();

        String category = CATEGORIES.get(categoryChoice - 1);
        Expense expense = new Expense(category, amount, desc, LocalDate.now());
        ExpenseDAO.addExpense(user.getId(), expense);
        System.out.println("Expense added successfully!");
    }

    private static void viewAllExpenses(User user) {
        List<Expense> expenses = ExpenseDAO.getExpensesByUser(user.getId());
        System.out.println("== All Expenses ==");
        for (Expense e : expenses) {
            System.out.println(e);
        }
    }

    private static void viewMonthlyReport(User user) {
        YearMonth currentMonth = YearMonth.now();
        List<Expense> expenses = ExpenseDAO.getExpensesByUser(user.getId());

        double total = 0;
        Map<String, Double> catTotals = new HashMap<>();
        for (Expense e : expenses) {
            if (YearMonth.from(e.getDate()).equals(currentMonth)) {
                total += e.getAmount();
                catTotals.put(e.getCategory(), catTotals.getOrDefault(e.getCategory(), 0.0) + e.getAmount());
            }
        }

        System.out.println("== Monthly Report: " + currentMonth + " ==");
        System.out.println("Total Spent: Rs." + total);
        for (String cat : catTotals.keySet()) {
            System.out.println(cat + ": Rs." + catTotals.get(cat));
        }
    }

    private static void viewLast3Months(User user) {
        YearMonth now = YearMonth.now();
        List<Expense> expenses = ExpenseDAO.getExpensesByUser(user.getId());

        for (int i = 0; i < 3; i++) {
            YearMonth month = now.minusMonths(i);
            double total = 0;
            Map<String, Double> catTotals = new HashMap<>();

            for (Expense e : expenses) {
                if (YearMonth.from(e.getDate()).equals(month)) {
                    total += e.getAmount();
                    catTotals.put(e.getCategory(), catTotals.getOrDefault(e.getCategory(), 0.0) + e.getAmount());
                }
            }

            System.out.println("\n== Month: " + month + " ==");
            System.out.println("Total Spent: Rs." + total);
            for (String cat : catTotals.keySet()) {
                System.out.println(cat + ": Rs." + catTotals.get(cat));
            }
        }
    }
}
