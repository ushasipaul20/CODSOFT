import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GuessingGameSwing extends JFrame implements ActionListener {

    private int randomNumber;
    private int attemptsLeft;
    private int maxAttempts = 7;
    private int score = 0;
    private int roundsPlayed = 0;

    private JTextField guessField;
    private JTextArea display;

    private JButton guessBtn, newGameBtn, exitBtn;

    public GuessingGameSwing() {
        setTitle("Number Guessing Game 🎯");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Guess a number (1-100):"));

        guessField = new JTextField(10);
        add(guessField);

        guessBtn = new JButton("Guess");
        newGameBtn = new JButton("New Game");
        exitBtn = new JButton("Exit");

        add(guessBtn);
        add(newGameBtn);
        add(exitBtn);

        display = new JTextArea(10, 30);
        display.setEditable(false);
        add(new JScrollPane(display));

        guessBtn.addActionListener(this);
        newGameBtn.addActionListener(this);
        exitBtn.addActionListener(this);

        startNewGame();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Start a new round
    private void startNewGame() {
        Random rand = new Random();
        randomNumber = rand.nextInt(100) + 1;
        attemptsLeft = maxAttempts;
        roundsPlayed++;

        display.setText("🎮 New Game Started!\nYou have " + maxAttempts + " attempts.\n");
        guessField.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // EXIT
        if (e.getSource() == exitBtn) {
            System.exit(0);
        }

        // NEW GAME
        if (e.getSource() == newGameBtn) {
            startNewGame();
            return;
        }

        // GUESS BUTTON
        if (e.getSource() == guessBtn) {
            String input = guessField.getText();

            if (input.isEmpty()) {
                display.append("⚠️ Enter a number!\n");
                return;
            }

            int guess;

            try {
                guess = Integer.parseInt(input);
            } catch (Exception ex) {
                display.append("❌ Invalid input! Enter numbers only.\n");
                return;
            }

            if (guess < 1 || guess > 100) {
                display.append("⚠️ Enter number between 1 and 100.\n");
                return;
            }

            attemptsLeft--;

            // CORRECT GUESS
            if (guess == randomNumber) {
                score += attemptsLeft + 1; // better score for fewer attempts
                display.append("✅ Correct! You guessed it!\n");
                display.append("🏆 Score: " + score + "\n");

                int option = JOptionPane.showConfirmDialog(
                        this,
                        "You won! Play again?",
                        "Game Over",
                        JOptionPane.YES_NO_OPTION
                );

                if (option == JOptionPane.YES_OPTION) {
                    startNewGame();
                } else {
                    System.exit(0);
                }
            }

            // TOO HIGH
            else if (guess > randomNumber) {
                display.append("📉 Too High!\n");
            }

            // TOO LOW
            else {
                display.append("📈 Too Low!\n");
            }

            display.append("Attempts left: " + attemptsLeft + "\n");

            // OUT OF ATTEMPTS
            if (attemptsLeft == 0 && guess != randomNumber) {
                display.append("❌ Game Over! Number was: " + randomNumber + "\n");

                int option = JOptionPane.showConfirmDialog(
                        this,
                        "Out of attempts! Play again?",
                        "Game Over",
                        JOptionPane.YES_NO_OPTION
                );

                if (option == JOptionPane.YES_OPTION) {
                    startNewGame();
                } else {
                    System.exit(0);
                }
            }

            guessField.setText("");
        }
    }

    public static void main(String[] args) {
        new GuessingGameSwing();
    }
}
