package cscorner;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
	
	//type of datas
    static class Expense {
        String category;
        String type; // Income or Expense
        double amount;
        boolean isRecurring;
        int recurrenceDays; // Number of days between occurrences (Every)

        //Assigning data
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

        // validator of inputs
        while (true) {
        	System.out.println(" ");
        	
        	System.out.println("" 
        			+" ____                                                      ______                      __                      \r\n"
        			+ "/\\  _`\\                                                   /\\__  _\\                    /\\ \\                     \r\n"
        			+ "\\ \\ \\L\\_\\  __  _  _____      __    ___     ____     __    \\/_/\\ \\/ _ __    __      ___\\ \\ \\/'\\      __   _ __  \r\n"
        			+ " \\ \\  _\\L /\\ \\/'\\/\\ '__`\\  /'__`\\/' _ `\\  /',__\\  /'__`\\     \\ \\ \\/\\`'__\\/'__`\\   /'___\\ \\ , <    /'__`\\/\\`'__\\\r\n"
        			+ "  \\ \\ \\L\\ \\/>  </\\ \\ \\L\\ \\/\\  __//\\ \\/\\ \\/\\__, `\\/\\  __/      \\ \\ \\ \\ \\//\\ \\L\\.\\_/\\ \\__/\\ \\ \\\\`\\ /\\  __/\\ \\ \\/ \r\n"
        			+ "   \\ \\____//\\_/\\_\\\\ \\ ,__/\\ \\____\\ \\_\\ \\_\\/\\____/\\ \\____\\      \\ \\_\\ \\_\\\\ \\__/.\\_\\ \\____\\\\ \\_\\ \\_\\ \\____\\\\ \\_\\ \r\n"
        			+ "    \\/___/ \\//\\/_/ \\ \\ \\/  \\/____/\\/_/\\/_/\\/___/  \\/____/       \\/_/\\/_/ \\/__/\\/_/\\/____/ \\/_/\\/_/\\/____/ \\/_/ \r\n"
        			+ "                    \\ \\_\\                                                                                      \r\n"
        			+ "                     \\/_/                                                                                      "
        			+"");                                                             
        	
            System.out.println("===============================================================================================================");
            System.out.println("[1] Add Expense/Income");
            System.out.println("[2] View Expenses/Income");
            System.out.println("[3] Edit Expense/Income");
            System.out.println("[4] Delete Expense/Income");
            System.out.println("[5] Calculate for a Time Period");
            System.out.println("[6] Exit");
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
                    System.out.println(""
                    		+ ""
                    		+" ____                        __  __                        \r\n"
                    		+ "/\\  _`\\                     /\\ \\/\\ \\                       \r\n"
                    		+ "\\ \\ \\L\\_\\    ___     ___    \\_\\ \\ \\ \\____  __  __     __   \r\n"
                    		+ " \\ \\ \\L_L   / __`\\  / __`\\  /'_` \\ \\ '__`\\/\\ \\/\\ \\  /'__`\\ \r\n"
                    		+ "  \\ \\ \\/, \\/\\ \\L\\ \\/\\ \\L\\ \\/\\ \\L\\ \\ \\ \\L\\ \\ \\ \\_\\ \\/\\  __/ \r\n"
                    		+ "   \\ \\____/\\ \\____/\\ \\____/\\ \\___,_\\ \\_,__/\\/`____ \\ \\____\\\r\n"
                    		+ "    \\/___/  \\/___/  \\/___/  \\/__,_ /\\/___/  `/___/> \\/____/\r\n"
                    		+ "                                               /\\___/      \r\n"
                    		+ "                                               \\/__/       "
                    		+ "");
                    scanner.close();
                    return;
            }
        }
    }

    
    //Add function of Instance and Expense
    private static void addExpense(Scanner scanner) {
        String category = "";
        String type = "";
        double amount = 0;
        boolean isRecurring = false;
        int recurrenceDays = 0;

        while (true) {
            // Step 1: Enter category
            if (category.isEmpty()) {
            	System.out.println("----------------------------------");
                System.out.print("Enter category ");
                category = scanner.next();
//                if (category.equalsIgnoreCase("b")) {
//                    return; // Go back to the main menu
//                }
                scanner.nextLine(); // Consume the newline character
                continue; // Proceed to the next step
            }

            // Step 2: Enter type (Income/Expense)
            if (type.isEmpty()) {
            	System.out.println("----------------------------------");
                System.out.println("Is it Income or Expense? [I] Income, [E] Expense ");
                type = scanner.next().toUpperCase();
              if (!type.equals("I") && !type.equals("E")) {
                    System.out.println("Invalid input. Please enter '[I]' for Income or '[E]' for Expense.");
                    type = ""; // Reset type and ask again
                    continue;
                }
                continue; // Proceed to the next step
            }

            // Step 3: Enter amount
            if (amount == 0) {
            	System.out.println("----------------------------------");
                System.out.print("Enter amount ");
                String input = scanner.next();
//                if (input.equalsIgnoreCase("b")) {
//                    type = ""; // Reset type and go back to the previous step
//                    continue;
//                }
                try {
                    amount = Double.parseDouble(input);
                    if (amount <= 0) {
                        System.out.println("Amount must be positive.");
                        amount = 0; // Reset amount and ask again
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid amount. Please enter a numeric value.");
                    amount = 0; // Reset amount and ask again
                }
                continue; // Proceed to the next step
            }

            // Step 4: Recurrence
            if (!isRecurring && recurrenceDays == 0) {
            	System.out.println("----------------------------------");
                System.out.println("Is this a recurring entry? [Y] Yes, [N] No ");
                String recurringInput = scanner.next().toUpperCase();
             if (recurringInput.equals("Y")) {
                    isRecurring = true;
                	System.out.println("----------------------------------");
                    System.out.print("Enter recurrence interval in days: ");
                    recurrenceDays = validateIntInput(scanner, 1, Integer.MAX_VALUE);
                } else if (recurringInput.equals("N")) {
                    break; // Exit loop if not recurring
                } else {
                    System.out.println("Invalid input. Please enter '[Y]' for Yes or '[N]' for No.");
                }
                continue; // Proceed to the next step or retry
            }

            break; // Exit the loop once all inputs are valid
        }

        // Create the Expense object and add it to the list
        Expense newExpense = new Expense(category, type, amount, isRecurring, recurrenceDays);
        expenses.add(newExpense);

        // Update totals for Income or Expense
        if (type.equals("I")) {
            totalIncome += amount;
        } else {
            totalExpense += amount;
        }

        System.out.println("Entry added successfully!");
    }

   
   //view method of (Expense and Income) 
    private static void viewExpenses() {
    	System.out.println(" ");
    	System.out.println(" ");
        System.out.println("\nExpenses:");
        System.out.println("============================================================================================================");
        System.out.printf("%-10s %-15s %-10s %-20s %-10s %-10s\n", "Index", "Category", "Type", "Amount", "Recurring", "Every");
        System.out.println("============================================================================================================");

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
            if(expenses.size() >= 1) {
        	   System.out.printf("%-10d %-15s %-10s ₱%-20.2f %-10s %-10d\n", i,
                       expense.category,
                       expense.type.equals("I") ? "Income" : "Expense",
                       expense.amount,                // Original amount
                       expense.isRecurring ? "Yes" : "No",
                       expense.recurrenceDays);
           }

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
        
        if(expenses.size() >= 1) {
            System.out.println(" ");
      	    System.out.println(" ");
            System.out.println("============================================================================================================");
            System.out.printf("Total Income: ₱%.2f\n", totalIncome);
            System.out.printf("Total Expense: ₱%.2f\n", totalExpense);
            System.out.printf("Balance: ₱%.2f\n", totalIncome - totalExpense);
            
            System.out.println(" ");
      	    System.out.println(" ");
        } else {
        	System.out.println("No data yet");
            System.out.println("============================================================================================================");
            System.out.println(" ");
      	    System.out.println(" ");
        	return;
        }
        
    
    }




    private static void deleteExpense(Scanner scanner) {
        System.out.println(" ");
        System.out.println(" ");
        viewExpenses();

        // Loop until the user provides a valid index or chooses to go back
        while (true) {
            System.out.print("Enter the index of the entry to delete or [B] to go back: ");
            String input = scanner.next().toUpperCase();  // Read input as a string
            
            // If the user chooses to go back
            if (input.equals("B")) {
                return;  // Exit the deleteExpense method and go back
            }

            // Try parsing the input to an integer
            int index;
            try {
                index = Integer.parseInt(input);
                
                // Check if the index is valid
                if (index < 0 || index >= expenses.size()) {
                    System.out.println("Invalid index. Please try again.");
                    continue;  // Continue to the next iteration and ask for input again
                }
                
                // If valid index, remove the expense and update totals
                Expense expense = expenses.remove(index);
                if (expense.type.equals("I")) {
                    totalIncome -= expense.amount;
                } else {
                    totalExpense -= expense.amount;
                }

                System.out.println("Entry deleted successfully!");
                return;  // Exit the method after deletion

            } catch (NumberFormatException e) {
                // If input is not a valid integer
                System.out.println("Invalid input. Please enter a valid index or [B] to go back.");
            }
        }
    }

    
 
    private static void editExpense(Scanner scanner) {
        System.out.println("\nEditing an expense:");

        // If there are no entries in expenses
        if (expenses.isEmpty()) {
            System.out.println("You don't have any data to edit.");

            while (true) {
                String response = scanner.nextLine().trim().toUpperCase();
                if (response.equals("Y")) {
                    addExpense(scanner);
                    return; // Exit after adding an expense
                } else if (response.equals("B")) {
                    return; // Exit to the main menu
                } else {
                    System.out.println("Please enter [Y] to create an entry or [B] to go back.");

                }
            }
        }

        // View expenses before allowing editing
        viewExpenses();

        while (true) {
            String userInput = scanner.nextLine().trim().toUpperCase();

            if (userInput.equals("B")) {
                return;
            }

            try {
                int index = Integer.parseInt(userInput);

                // Validate index range
                if (index < 0 || index >= expenses.size()) {
                    System.out.println("Invalid index. Please try again.");
                    continue;
                }

                Expense expense = expenses.get(index);

                // Edit category
                System.out.print("Enter new category (current: " + expense.category + "): ");
                String newCategory = scanner.nextLine().trim();

                // Edit type
                String newType;
                while (true) {
                    System.out.print("Enter new type [I] Income, [E] Expense (current: " + (expense.type.equals("I") ? "Income" : "Expense") + "): ");
                    newType = scanner.nextLine().trim().toUpperCase();
                    if (newType.equals("I") || newType.equals("E")) {
                        break;
                    }
                    System.out.println("Invalid input. Please enter [I] or [E].");
                }

                // Edit amount
                double newAmount = validateDoubleInput(scanner, "Enter new amount (current: " + expense.amount + "): ");

                // Edit recurring status
                boolean newIsRecurring;
                while (true) {
                    String response = scanner.nextLine().trim().toLowerCase();
                    if (response.equals("y")) {
                        newIsRecurring = true;
                        break;
                    } else if (response.equals("n")) {
                        newIsRecurring = false;
                        break;
                    } else {
                        System.out.print("Is this a recurring entry? (Y/N): ");
                    }
                }

                // Edit recurrence days if recurring
                int newRecurrenceDays = 0;
                if (newIsRecurring) {
                    while (true) {
                        System.out.print("Enter recurrence interval in days (current: " + expense.recurrenceDays + "): ");
                        String input = scanner.nextLine().trim();
                        try {
                            newRecurrenceDays = Integer.parseInt(input);
                            if (newRecurrenceDays > 0) {
                                break;
                            } else {
                                System.out.println("Please enter a positive integer.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid integer.");
                        }
                    }
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
                break;

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid index or [B] to go back.");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }



    private static void calculateForPeriod(Scanner scanner) {
        System.out.println(" ");
        System.out.println(" ");
        
        // Prompt the user for the number of days for calculation, or "B" to go back
        while (true) {
            System.out.print("Enter the number of days for calculation or [B] to go back: ");
            String input = scanner.next().toUpperCase();

            // If the user chooses to go back, exit the method
            if (input.equals("B")) {
                return;  // Go back to the previous menu
            }

            // Validate the input as a valid integer for the number of days
            try {
                int days = Integer.parseInt(input);
                if (days < 1) {
                    System.out.println("The number of days must be greater than zero. Please try again.");
                    continue;
                }

                double periodIncome = 0;
                double periodExpense = 0;

                // Iterate over the list of expenses
                for (Expense expense : expenses) {
                    double amount = expense.amount;
                    
                    // If the expense is recurring, calculate the total amount based on the occurrences
                    if (expense.isRecurring && expense.recurrenceDays > 0) {
                        int occurrences = days / expense.recurrenceDays;
                        amount *= occurrences;
                    }

                    // Add the amount to either income or expense based on the type
                    if (expense.type.equals("I")) {
                        periodIncome += amount;
                    } else {
                        periodExpense += amount;
                    }
                }

                // Check if there are any expenses
                if (expenses.size() >= 1) {
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.printf("\nFor %d days:\n", days);
                    System.out.printf("Total Income: ₱%.2f\n", periodIncome);
                    System.out.printf("Total Expense: ₱%.2f\n", periodExpense);
                    System.out.printf("Balance: ₱%.2f\n", periodIncome - periodExpense);
                } else {
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println("============================================================================================================");
                    System.out.println("Please add data first.");
                    System.out.println("============================================================================================================");
                }
                break;  // Exit the loop after the calculation is done
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number of days or [B] to go back.");
            }
        }
    }

    
    //validation of input and data type
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
    
    
  //validation of input and data type
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
