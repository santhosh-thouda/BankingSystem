package banking;

import repository.AccountRepository;
import repository.TransactionRepository;
import util.InputValidator;
import exceptions.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.function.Consumer;
import database.DatabaseInitializer;

public class BankingApplication {
    private static final Scanner scanner = new Scanner(System.in);
    private static final AccountRepository accountRepository = new AccountRepository();
    private static final TransactionRepository transactionRepository = new TransactionRepository();

    public static void main(String[] args) {
        try {
            DatabaseInitializer.initialize();
            System.out.println("\n🚀 Welcome to Java Banking System!");
            
            while (true) {
                printMenu();
                
                try {
                    int choice = Integer.parseInt(scanner.nextLine());
                    handleUserChoice(choice);
                } catch (NumberFormatException e) {
                    System.out.println("\n⚠️ Error: Please enter a number between 1-8");
                } catch (Exception e) {
                    System.out.println("\n❌ Error: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("\n🔴 Critical system error: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void printMenu() {
        System.out.println("\n===== Banking System Menu =====");
        System.out.println("1. Create Account");
        System.out.println("2. View Account Details");
        System.out.println("3. Deposit Funds");
        System.out.println("4. Withdraw Funds");
        System.out.println("5. Transfer Funds");
        System.out.println("6. Apply Interest (Savings Accounts)");
        System.out.println("7. Close Account");
        System.out.println("8. Exit");
        System.out.print("Choose an option (1-8): ");
    }

    private static void handleUserChoice(int choice) throws Exception {
        switch (choice) {
            case 1 -> createAccount();
            case 2 -> viewAccount();
            case 3 -> deposit();
            case 4 -> withdraw();
            case 5 -> transfer();
            case 6 -> applyInterest();
            case 7 -> closeAccount();
            case 8 -> exitApplication();
            default -> System.out.println("\n⚠️ Invalid option! Please choose 1-8");
        }
    }

    private static void createAccount() throws Exception {
        System.out.println("\n➕ Create New Account");
        
        String accNumber = getValidInput(
            "Enter account number (10 digits): ", 
            InputValidator::validateAccountNumber
        );
        
        if (accountRepository.accountExists(accNumber)) {
            throw new IllegalArgumentException("Account number already exists");
        }
        
        String name = getValidInput(
            "Enter account holder name: ",
            InputValidator::validateName
        );
        
        String type = getValidInput(
            "Enter account type (SAVINGS/CURRENT): ",
            InputValidator::validateAccountType
        ).toUpperCase();
        
        String pin = getValidInput(
            "Create 4-digit PIN: ",
            s -> { 
                if (!s.matches("\\d{4}")) {
                    throw new IllegalArgumentException("PIN must be 4 digits");
                }
            }
        );
        
        BigDecimal balance = new BigDecimal(getValidInput(
            "Enter initial deposit: ",
            s -> InputValidator.validateAmount(new BigDecimal(s))
        ));

        BankAccount account = type.equals("SAVINGS") 
            ? new SavingsAccount(accNumber, name, pin, balance)
            : new CurrentAccount(accNumber, name, pin, balance);
            
        accountRepository.save(account);
        System.out.println("\n✅ Account created successfully!");
        displayAccountDetails(account);
        transactionRepository.logTransaction(accNumber, "ACCOUNT_CREATION", balance, LocalDateTime.now());
    }

    private static String getValidInput(String prompt, Consumer<String> validator) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                validator.accept(input);
                return input;
            } catch (IllegalArgumentException e) {
                System.out.println("\n⚠️ Error: " + e.getMessage());
            }
        }
    }
    
    private static void displayAccountDetails(BankAccount account) {
        System.out.println("\n📝 Account Details:");
        System.out.println("────────────────────");
        System.out.println("Number: " + account.getAccountNumber());
        System.out.println("Type: " + account.getAccountType());
        System.out.println("Holder: " + account.getAccountHolderName());
        System.out.printf("Balance: $%.2f%n", account.getBalance());
        System.out.println("────────────────────");
    }
    
    private static void viewAccount() throws AccountPersistenceException {
        System.out.println("\n👀 View Account Details");
        String accountNumber = getValidInput("Enter account number: ", InputValidator::validateAccountNumber);
        String pin = getValidInput("Enter PIN: ", s -> {});
        
        BankAccount account = authenticate(accountNumber, pin);
        if (account != null) {
            displayAccountDetails(account);
        }
    }

    private static void deposit() throws Exception {
        System.out.println("\n💰 Deposit Funds");
        String accountNumber = getValidInput("Enter account number: ", InputValidator::validateAccountNumber);
        
        BankAccount account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null) {
            System.out.println("\n⚠️ Account not found");
            return;
        }
        
        BigDecimal amount = new BigDecimal(getValidInput(
            "Enter deposit amount: ",
            s -> InputValidator.validateAmount(new BigDecimal(s))
        ));
        
        account.deposit(amount);
        accountRepository.updateBalance(account);
        System.out.printf("\n✅ Deposit successful! New balance: $%.2f%n", account.getBalance());
        transactionRepository.logTransaction(accountNumber, "DEPOSIT", amount, LocalDateTime.now());
    }

    private static void withdraw() throws Exception {
        System.out.println("\n💸 Withdraw Funds");
        String accountNumber = getValidInput("Enter account number: ", InputValidator::validateAccountNumber);
        String pin = getValidInput("Enter PIN: ", s -> {});
        
        BankAccount account = authenticate(accountNumber, pin);
        if (account == null) return;
        
        BigDecimal amount = new BigDecimal(getValidInput(
            "Enter withdrawal amount: ",
            s -> InputValidator.validateAmount(new BigDecimal(s))
        ));
        
        account.withdraw(amount);
        accountRepository.updateBalance(account);
        System.out.printf("\n✅ Withdrawal successful! New balance: $%.2f%n", account.getBalance());
        transactionRepository.logTransaction(accountNumber, "WITHDRAWAL", amount, LocalDateTime.now());
    }

    private static void transfer() throws Exception {
        System.out.println("\n🔀 Transfer Funds");
        String fromAccountNumber = getValidInput("Enter your account number: ", InputValidator::validateAccountNumber);
        String pin = getValidInput("Enter PIN: ", s -> {});
        
        try {
            BankAccount source = authenticate(fromAccountNumber, pin);
            if (source == null) return;
            
            String toAccountNumber = getValidInput("Enter recipient account number: ", InputValidator::validateAccountNumber);
            BankAccount destination = accountRepository.findByAccountNumber(toAccountNumber);
            
            if (destination == null) {
                System.out.println("\n⚠️ Recipient account not found");
                return;
            }
            
            if (fromAccountNumber.equals(toAccountNumber)) {
                System.out.println("\n⚠️ Cannot transfer to the same account");
                return;
            }
            
            BigDecimal amount = new BigDecimal(getValidInput(
                "Enter transfer amount: ",
                s -> InputValidator.validateAmount(new BigDecimal(s))
            );
            
            try {
                source.transfer(destination, amount);
                accountRepository.updateBalance(source);
                accountRepository.updateBalance(destination);
                
                System.out.println("\n✅ Transfer completed successfully!");
                System.out.printf("Your new balance: $%.2f%n", source.getBalance());
                transactionRepository.logTransaction(fromAccountNumber, "TRANSFER_OUT", amount, LocalDateTime.now());
                transactionRepository.logTransaction(toAccountNumber, "TRANSFER_IN", amount, LocalDateTime.now());
            } catch (MaximumAmountException e) {
                System.out.println("\n⚠️ Error: " + e.getMessage());
            } catch (InsufficientFundsException e) {
                System.out.println("\n⚠️ Error: " + e.getMessage());
            }
        } catch (AccountPersistenceException e) {
            System.out.println("\n⚠️ Error: " + e.getMessage());
        }
    }

    private static void applyInterest() throws Exception {
        System.out.println("\n📈 Apply Interest");
        String accountNumber = getValidInput("Enter savings account number: ", InputValidator::validateAccountNumber);
        String pin = getValidInput("Enter PIN: ", s -> {});
        
        BankAccount account = authenticate(accountNumber, pin);
        if (account == null) return;
        
        if (account instanceof SavingsAccount) {
            BigDecimal interest = ((SavingsAccount) account).applyInterest();
            accountRepository.updateBalance(account);
            System.out.printf("\n✅ Interest applied! Earned: $%.2f%n", interest);
            System.out.printf("New balance: $%.2f%n", account.getBalance());
            transactionRepository.logTransaction(accountNumber, "INTEREST", interest, LocalDateTime.now());
        } else {
            System.out.println("\n⚠️ Only savings accounts earn interest");
        }
    }

    private static void closeAccount() throws AccountPersistenceException {
        System.out.println("\n❌ Close Account");
        String accountNumber = getValidInput("Enter account number to close: ", InputValidator::validateAccountNumber);
        String pin = getValidInput("Enter PIN: ", s -> {});
        
        BankAccount account = authenticate(accountNumber, pin);
        if (account == null) return;
        
        displayAccountDetails(account);
        String confirm = getValidInput("Are you sure you want to close this account? (yes/no): ",
            s -> { 
                if (!s.equalsIgnoreCase("yes") && !s.equalsIgnoreCase("no")) {
                    throw new IllegalArgumentException("Please enter yes or no"); 
                }
            });
        
        if (confirm.equalsIgnoreCase("yes")) {
            accountRepository.delete(accountNumber);
            System.out.println("\n✅ Account closed successfully");
            transactionRepository.logTransaction(accountNumber, "ACCOUNT_CLOSED", account.getBalance(), LocalDateTime.now());
        } else {
            System.out.println("\nAccount closure cancelled");
        }
    }

    private static BankAccount authenticate(String accountNumber, String pin) throws AccountPersistenceException {
        BankAccount account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null) {
            System.out.println("\n⚠️ Account not found");
            return null;
        }
        
        if (!account.validatePin(pin)) {
            System.out.println("\n⚠️ Incorrect PIN");
            return null;
        }
        
        return account;
    }

    private static void exitApplication() {
        System.out.println("\nThank you for using our banking system!");
        System.out.println("Goodbye! 👋");
        scanner.close();
        System.exit(0);
    }
}