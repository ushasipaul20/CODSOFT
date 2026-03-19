import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Random;

// Bank Account Class
class BankAccount {
    private String accountNumber;
    private String gmail;
    private String phone;
    private double balance;

    public BankAccount(String accountNumber, String gmail, String phone) {
        this.accountNumber = accountNumber;
        this.gmail = gmail;
        this.phone = phone;
        this.balance = 0;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String deposit(double amount) {
        if (amount <= 0) return "Invalid amount!";
        balance += amount;
        return "Deposit successful!";
    }

    public String withdraw(double amount) {
        if (amount <= 0) return "Invalid amount!";
        if (amount > balance) return "Insufficient balance!";
        balance -= amount;
        return "Withdrawal successful!";
    }

    public double getBalance() {
        return balance;
    }
}

// Main ATM GUI
public class ATMSystemSwing extends JFrame implements ActionListener {

    private static HashMap<String, BankAccount> accounts = new HashMap<>();
    private BankAccount currentAccount;

    private JTextField gmailField, phoneField, accField, amountField;
    private JTextArea display;

    private JButton registerBtn, loginBtn, depositBtn, withdrawBtn, balanceBtn, logoutBtn;

    // Generate random account number
    private String generateAccountNumber() {
        Random rand = new Random();
        return "AC" + (100000 + rand.nextInt(900000)); // e.g., AC123456
    }

    public ATMSystemSwing() {
        setTitle("ATM System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Registration fields
        add(new JLabel("Gmail:"));
        gmailField = new JTextField(15);
        add(gmailField);

        add(new JLabel("Phone:"));
        phoneField = new JTextField(10);
        add(phoneField);

        registerBtn = new JButton("Register");
        add(registerBtn);

        // Login field
        add(new JLabel("Account No:"));
        accField = new JTextField(10);
        add(accField);

        loginBtn = new JButton("Login");
        add(loginBtn);

        // Amount
        add(new JLabel("Amount:"));
        amountField = new JTextField(10);
        add(amountField);

        // ATM buttons
        depositBtn = new JButton("Deposit");
        withdrawBtn = new JButton("Withdraw");
        balanceBtn = new JButton("Check Balance");
        logoutBtn = new JButton("Logout");

        add(depositBtn);
        add(withdrawBtn);
        add(balanceBtn);
        add(logoutBtn);

        // Display
        display = new JTextArea(8, 40);
        display.setEditable(false);
        add(new JScrollPane(display));

        // Listeners
        registerBtn.addActionListener(this);
        loginBtn.addActionListener(this);
        depositBtn.addActionListener(this);
        withdrawBtn.addActionListener(this);
        balanceBtn.addActionListener(this);
        logoutBtn.addActionListener(this);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // REGISTER
        if (e.getSource() == registerBtn) {
            String gmail = gmailField.getText();
            String phone = phoneField.getText();

            if (gmail.isEmpty() || phone.isEmpty()) {
                display.setText("Enter Gmail and Phone!");
                return;
            }

            String accNo = generateAccountNumber();

            BankAccount newAcc = new BankAccount(accNo, gmail, phone);
            accounts.put(accNo, newAcc);

            display.setText("Account Created!\nYour Account No: " + accNo);
        }

        // LOGIN
        else if (e.getSource() == loginBtn) {
            String accNo = accField.getText();

            if (accounts.containsKey(accNo)) {
                currentAccount = accounts.get(accNo);
                display.setText("Login successful!");
            } else {
                display.setText("Invalid Account Number!");
            }
        }

        // CHECK LOGIN
        else if (currentAccount == null) {
            display.setText("Please login first!");
            return;
        }

        // DEPOSIT
        else if (e.getSource() == depositBtn) {
            try {
                double amt = Double.parseDouble(amountField.getText());
                display.setText(currentAccount.deposit(amt));
            } catch (Exception ex) {
                display.setText("Enter valid amount!");
            }
        }

        // WITHDRAW
        else if (e.getSource() == withdrawBtn) {
            try {
                double amt = Double.parseDouble(amountField.getText());
                display.setText(currentAccount.withdraw(amt));
            } catch (Exception ex) {
                display.setText("Enter valid amount!");
            }
        }

        // BALANCE
        else if (e.getSource() == balanceBtn) {
            display.setText("Balance: " + currentAccount.getBalance());
        }

        // LOGOUT
        else if (e.getSource() == logoutBtn) {
            currentAccount = null;
            display.setText("Logged out!");
        }

        amountField.setText("");
    }

    public static void main(String[] args) {
        new ATMSystemSwing();
    }
}
