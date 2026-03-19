import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ResultSystemSwing extends JFrame implements ActionListener {

    private JTextField nameField, subjectCountField;
    private JPanel subjectsPanel;
    private JTextField[] marksFields;

    private JTextArea resultArea;
    private JButton generateBtn;

    public ResultSystemSwing() {
        setTitle("Student Result Portal 🎓");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top Panel
        JPanel topPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        topPanel.setBorder(BorderFactory.createTitledBorder("Student Info"));

        topPanel.add(new JLabel("Student Name:"));
        nameField = new JTextField();
        topPanel.add(nameField);

        topPanel.add(new JLabel("Number of Subjects:"));
        subjectCountField = new JTextField();
        topPanel.add(subjectCountField);

        JButton createBtn = new JButton("Create Subjects");
        topPanel.add(createBtn);

        add(topPanel, BorderLayout.NORTH);

        // Center Panel (Subjects)
        subjectsPanel = new JPanel();
        subjectsPanel.setLayout(new GridLayout(0, 2, 5, 5));
        subjectsPanel.setBorder(BorderFactory.createTitledBorder("Enter Marks"));

        add(new JScrollPane(subjectsPanel), BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        generateBtn = new JButton("Generate Result");
        bottomPanel.add(generateBtn, BorderLayout.NORTH);

        resultArea = new JTextArea(6, 40);
        resultArea.setEditable(false);
        bottomPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);

        // Actions
        createBtn.addActionListener(e -> createSubjects());
        generateBtn.addActionListener(this);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Create dynamic subject fields
    private void createSubjects() {
        subjectsPanel.removeAll();

        int count;
        try {
            count = Integer.parseInt(subjectCountField.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Enter valid number!");
            return;
        }

        marksFields = new JTextField[count];

        for (int i = 0; i < count; i++) {
            subjectsPanel.add(new JLabel("Subject " + (i + 1) + ":"));
            marksFields[i] = new JTextField();
            subjectsPanel.add(marksFields[i]);
        }

        subjectsPanel.revalidate();
        subjectsPanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (marksFields == null) {
            resultArea.setText("Please create subjects first!");
            return;
        }

        int total = 0;

        for (int i = 0; i < marksFields.length; i++) {
            try {
                int marks = Integer.parseInt(marksFields[i].getText());

                if (marks < 0 || marks > 100) {
                    resultArea.setText("Marks must be between 0 and 100!");
                    return;
                }

                total += marks;
            } catch (Exception ex) {
                resultArea.setText("Enter valid marks!");
                return;
            }
        }

        double average = (double) total / marksFields.length;

        // Grade logic
        String grade;
        if (average >= 90) grade = "A+";
        else if (average >= 75) grade = "A";
        else if (average >= 60) grade = "B";
        else if (average >= 50) grade = "C";
        else grade = "F";

        // Display
        resultArea.setText(
                "🎓 Student Name: " + nameField.getText() +
                "\n📊 Total Marks: " + total +
                "\n📈 Percentage: " + average + "%" +
                "\n🏆 Grade: " + grade
        );
    }

    public static void main(String[] args) {
        new ResultSystemSwing();
    }
}
