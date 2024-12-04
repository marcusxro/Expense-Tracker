package cscorner;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ExpenseTracker {
    static class Expense {
        String category;
        String type; // Income or Expense
        double amount;
        boolean isRecurring;
        int recurrenceDays; // Number of days between occurrences

        Expense(String category, String type, double amount, boolean isRecurring, int recurrenceDays) {
            this.category = category;
            this.type = type;
            this.amount = amount;
            this.isRecurring = isRecurring;
            this.recurrenceDays = recurrenceDays;
        }
    }

    private static final ArrayList<Expense> expenses = new ArrayList<>();
    private static double totalIncome = 0;
    private static double totalExpense = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nExpense Tracker");
            System.out.println("==================");
            System.out.println("1. Add Expense/Income");
            System.out.println("2. View Expenses/Income");
            System.out.println("3. Edit Expense/Income");
            System.out.println("4. Delete Expense/Income");
            System.out.println("5. Calculate for a Time Period");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = validateIntInput(scanner, 1, 6);
            switch (choice) {
                case 1:
                    addExpense(scanner);
                    break;
                case 2:
                    viewExpenses();
                    break;
                case 3:
                    editExpense(scanner);
                    break;
                case 4:
                    deleteExpense(scanner);
                    break;
                case 5:
                    calculateForPeriod(scanner);
                    break;
                case 6:
                    System.out.println("Exiting... Goodbye!");
                    scanner.close();
                    return;
            }
        }
    }

    private static void addExpense(Scanner scanner) {
        System.out.print("Enter category: ");
        scanner.nextLine();  // Consume the newline left by previous next() call
        String category = scanner.nextLine();  // Now reads the full category name

        String type;
        while (true) {
            System.out.println("Is it Income or Expense? [I] Income, [E] Expense");
            type = scanner.next().toUpperCase();
            if (type.equals("I") || type.equals("E")) {
                break;
            }
            System.out.println("Invalid input. Please enter 'I' for Income or 'E' for Expense.");
        }

        double amount = validateDoubleInput(scanner, "Enter amount: ");

        System.out.println("Is this a recurring entry? [Y] Yes, [N] No");
        boolean isRecurring = scanner.next().equalsIgnoreCase("Y");

        int recurrenceDays = 0;
        if (isRecurring) {
            System.out.print("Enter recurrence interval in days: ");
            recurrenceDays = validateIntInput(scanner, 1, Integer.MAX_VALUE);
        }

        expenses.add(new Expense(category, type, amount, isRecurring, recurrenceDays));

        if (type.equals("I")) {
            totalIncome += amount;
        } else {
            totalExpense += amount;
        }

        System.out.println("Entry added successfully!");
    }
    private static void viewExpenses() {
        System.out.println("\nExpenses:");
        System.out.println("================================================================");
        System.out.printf("%-5s %-15s %-10s %-20s %-10s %-10s\n", "Index", "Category", "Type", "Amount", "Recurring", "Every");
        System.out.println("================================================================");

        totalIncome = 0;  // Reset totalIncome and totalExpense to recalculate
        totalExpense = 0;

        // Find the maximum income recurrence days (for comparison)
        int maxIncomeRecurrenceDays = 0;
        for (int i = 0; i < expenses.size(); i++) {
            Expense expense = expenses.get(i);
            if (expense.type.equals("I") && expense.isRecurring) {
                maxIncomeRecurrenceDays = Math.max(maxIncomeRecurrenceDays, expense.recurrenceDays);
            }
        }

        // Process each expense and calculate total income and total expense
        for (int i = 0; i < expenses.size(); i++) {
            Expense expense = expenses.get(i);

            // Display each expense entry with the original amount
            System.out.printf("%-5d %-15s %-10s ₱%-20.2f %-10s %-10d\n", i,
                    expense.category,
                    expense.type.equals("I") ? "Income" : "Expense",
                    expense.amount,                // Original amount
                    expense.isRecurring ? "Yes" : "No",
                    expense.recurrenceDays);

            // Handle Income
            if (expense.type.equals("I")) {
                totalIncome += expense.amount;  // Add income to total income
            }

            // Handle Expense
            if (expense.type.equals("E")) {
                if (expense.isRecurring) {
                    if (expense.recurrenceDays < maxIncomeRecurrenceDays) {
                        // For expenses occurring less frequently than income recurrence (multiply by max income periods)
                        totalExpense += expense.amount * (maxIncomeRecurrenceDays / expense.recurrenceDays);
                    } else if (expense.recurrenceDays == maxIncomeRecurrenceDays) {
                        // Directly add the expense if recurrence is the same as the income recurrence period
                        totalExpense += expense.amount;
                    } else {
                        // For expenses occurring more frequently than income recurrence, just add the expense
                        totalExpense += expense.amount;
                    }
                } else {
                    // Non-recurring expenses are added directly
                    totalExpense += expense.amount;
                }
            }
        }

        System.out.println("================================================================");
        System.out.printf("Total Income: ₱%.2f\n", totalIncome);
        System.out.printf("Total Expense: ₱%.2f\n", totalExpense);
        System.out.printf("Balance: ₱%.2f\n", totalIncome - totalExpense);
    }





    private static void deleteExpense(Scanner scanner) {
        viewExpenses();
        System.out.print("Enter the index of the entry to delete: ");
        int index = validateIntInput(scanner, 0, expenses.size() - 1);

        Expense expense = expenses.remove(index);

        if (expense.type.equals("I")) {
            totalIncome -= expense.amount;
        } else {
            totalExpense -= expense.amount;
        }

        System.out.println("Entry deleted successfully!");
    }
    private static void editExpense(Scanner scanner) {
        viewExpenses();
        System.out.print("Enter the index of the entry to edit: ");
        int index = validateIntInput(scanner, 0, expenses.size() - 1);

        Expense expense = expenses.get(index);

        System.out.print("Enter new category (current: " + expense.category + "): ");
        String newCategory = scanner.next();

        String newType = "";
        while (true) {
            System.out.println("Enter new type [I] Income, [E] Expense (current: " + (expense.type.equals("I") ? "Income" : "Expense") + "):");
            newType = scanner.next().toUpperCase();
            if (newType.equals("I") || newType.equals("E")) {
                break;
            }
            System.out.println("Invalid input. Please enter 'I' or 'E'.");
        }

        double newAmount = validateDoubleInput(scanner, "Enter new amount (current: " + expense.amount + "): ");

        System.out.println("Is this a recurring entry? [Y] Yes, [N] No (current: " + (expense.isRecurring ? "Yes" : "No") + "):");
        boolean newIsRecurring = scanner.next().equalsIgnoreCase("Y");

        int newRecurrenceDays = 0;
        if (newIsRecurring) {
            System.out.print("Enter recurrence interval in days (current: " + expense.recurrenceDays + "): ");
            newRecurrenceDays = validateIntInput(scanner, 1, Integer.MAX_VALUE);
        }

        // Adjust totals before updating the entry
        if (expense.type.equals("I")) {
            totalIncome -= expense.amount;
        } else {
            totalExpense -= expense.amount;
        }

        // Update the expense details
        expense.category = newCategory;
        expense.type = newType;
        expense.amount = newAmount;
        expense.isRecurring = newIsRecurring;
        expense.recurrenceDays = newRecurrenceDays;

        // Adjust totals after updating the entry
        if (newType.equals("I")) {
            totalIncome += newAmount;
        } else {
            totalExpense += newAmount;
        }

        System.out.println("Entry updated successfully!");
    }

    private static void calculateForPeriod(Scanner scanner) {
        System.out.print("Enter the number of days for calculation: ");
        int days = validateIntInput(scanner, 1, Integer.MAX_VALUE);

        double periodIncome = 0;
        double periodExpense = 0;

        for (Expense expense : expenses) {
            double amount = expense.amount;
            if (expense.isRecurring && expense.recurrenceDays > 0) {
                int occurrences = days / expense.recurrenceDays;
                amount *= occurrences;
            }

            if (expense.type.equals("I")) {
                periodIncome += amount;
            } else {
                periodExpense += amount;
            }
        }

        System.out.printf("\nFor %d days:\n", days);
        System.out.printf("Total Income: $%.2f\n", periodIncome);
        System.out.printf("Total Expense: $%.2f\n", periodExpense);
        System.out.printf("Balance: $%.2f\n", periodIncome - periodExpense);
    }

    private static int validateIntInput(Scanner scanner, int min, int max) {
        while (true) {
            try {
                int input = scanner.nextInt();
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.println("Please enter a number between " + min + " and " + max + ".");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
    }

    private static double validateDoubleInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next();
            }
        }
    }
}
