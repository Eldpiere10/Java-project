import Encapsulation.*;
import Polymorphism.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Transaction> transactionHistory = new ArrayList<>();

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("       WELCOME TO STUDENTS' CASHMATE     ");
        System.out.println("========================================");

        String name = getValidatedName("Enter Student Name: ");
        Student student = new Student(name);

        int choice;

        do {
            showMenu();
            choice = getMenuChoice("Choose an option: ");

            switch (choice) {
                case 1 -> addAllowance(student);
                case 2 -> addExpense(student);
                case 3 -> showBalance(student);
                case 4 -> showHistory();
                case 5 -> {
                    System.out.println("\nThank you for using CashMate!");
                    System.out.println("Goodbye, " + student.getName() + "!");
                }
                default -> System.out.println("Invalid option. Try again.");
            }

        } while (choice != 5);
    }

    
    public static void showMenu() {
        System.out.println("\n================ MAIN MENU ================");
        System.out.println("1. Add Allowance");
        System.out.println("2. Add Expense");
        System.out.println("3. Show Balance");
        System.out.println("4. View Transaction History");
        System.out.println("5. Exit");
    }

    
    public static void addAllowance(Student student) {

        int id = transactionHistory.size() + 1;

        String date = getValidatedDate("Enter date (MM/DD/YYYY): ");
        double amount = getValidatedAmount("Enter amount: ");
        String description = getValidatedDescription("Enter description: ");

        Allowance allowance = new Allowance(id, date, amount, description);
        allowance.apply(student);

        transactionHistory.add(allowance);
        System.out.println("\nAllowance added successfully!");
    }

    
    public static void addExpense(Student student) {

        int id = transactionHistory.size() + 1;

        System.out.println("\nExpense Categories:");
        System.out.println("1. Travel");
        System.out.println("2. Grocery");
        System.out.println("3. Emergency");
        System.out.println("4. School Supplies");
        System.out.println("5. Dorm");
        System.out.println("6. Load");

        int type = getExpenseCategory("Choose category (1-6): ");

        String date = getValidatedDate("Enter date (MM/DD/YYYY): ");
        double amount = getValidatedAmount("Enter amount: ");
        String description = getValidatedDescription("Enter description: ");

        Expense expense = switch (type) {
            case 1 -> new TravelExpense(id, date, amount, description);
            case 2 -> new GroceryExpense(id, date, amount, description);
            case 3 -> new EmergencyExpense(id, date, amount, description);
            case 4 -> new SchoolSuppliesExpense(id, date, amount, description);
            case 5 -> new DormExpense(id, date, amount, description);
            case 6 -> new LoadExpense(id, date, amount, description);
            default -> null;
        };

        if (expense == null) {
            System.out.println("Invalid expense type.");
            return;
        }

        try {
            expense.apply(student);
            transactionHistory.add(expense);
            System.out.println("\nExpense recorded successfully.");
        } catch (Exception e) {
            System.out.println("Error applying expense: " + e.getMessage());
        }
    }

    
    public static void showBalance(Student student) {
        System.out.println("\nCurrent Balance: " + student.getBalance() + " PHP");
    }
    
    
    public static void showHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("\nNo transactions recorded.");
            return;
        }

        System.out.println("\n============= TRANSACTION HISTORY =============");

        for (Transaction t : transactionHistory) {
            System.out.println(t);
        }
    }

    
    
    public static int getMenuChoice(String msg) {
        System.out.print(msg);

        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Enter a number 1-5: ");
            scanner.next();
        }

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 1 || choice > 5) {
            System.out.println("Invalid option. Choose between 1-5 only.");
            return getMenuChoice(msg);
        }

        return choice;
    }

    
    public static int getExpenseCategory(String msg) {
        System.out.print(msg);

        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Enter a number 1-6: ");
            scanner.next();
        }

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 1 || choice > 6) {
            System.out.println("Invalid category. Choose between 1-6.");
            return getExpenseCategory(msg);
        }

        return choice;
    }

    
    public static String getValidatedName(String msg) {
        System.out.print(msg);
        String input = scanner.nextLine().trim();

        while (!input.matches("[a-zA-Z ]+") || input.isEmpty()) {
            System.out.print("Invalid name. Letters only: ");
            input = scanner.nextLine().trim();
        }

        return input;
    }

    
    public static String getValidatedDate(String msg) {
        System.out.print(msg);
        String date = scanner.nextLine().trim();

        while (!date.matches("^(0[1-9]|1[0-2])/([0-2][0-9]|3[01])/\\d{4}$")) {
            System.out.print("Invalid date. Use MM/DD/YYYY: ");
            date = scanner.nextLine().trim();
        }

        return date;
    }

    
    public static double getValidatedAmount(String msg) {
        System.out.print(msg);

        while (!scanner.hasNextDouble()) {
            System.out.print("Invalid amount. Enter a valid number: ");
            scanner.next();
        }

        double amount = scanner.nextDouble();
        scanner.nextLine();

        while (amount <= 0) {
            System.out.print("Amount must be greater than zero: ");
            amount = getValidatedAmount(msg);
        }

        return amount;
    }

    
    public static String getValidatedDescription(String msg) {
        System.out.print(msg);
        String description = scanner.nextLine().trim();

        while (description.isEmpty()) {
            System.out.print("Description cannot be empty: ");
            description = scanner.nextLine().trim();
        }

        return description;
    }
}
