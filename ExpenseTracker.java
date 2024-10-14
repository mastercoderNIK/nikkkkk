import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class ExpenseTracker extends JFrame {
    private JTextField amountField;
    private JTextField descriptionField;
    private JComboBox<String> categoryBox;
    private DefaultTableModel model;
    private JTable transactionTable;
    private JLabel totalIncomeLabel;
    private JLabel totalExpenseLabel;
    private JLabel balanceLabel;

    private double totalIncome = 0.0;
    private double totalExpenses = 0.0;

    public ExpenseTracker() {
        setTitle("Expense Tracker");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        amountField = new JTextField(10);
        descriptionField = new JTextField(20);
        String[] categories = {"Income", "Food", "Travel", "Shopping", "Utilities", "Others"};
        categoryBox = new JComboBox<>(categories);

        JButton addButton = new JButton("Add Transaction");
        addButton.addActionListener(new AddTransactionAction());

        // Labels for balance, income, and expenses
        totalIncomeLabel = new JLabel("Total Income: $0.00");
        totalExpenseLabel = new JLabel("Total Expenses: $0.00");
        balanceLabel = new JLabel("Balance: $0.00");

        // Table to display transactions
        model = new DefaultTableModel(new String[]{"Amount", "Description", "Category"}, 0);
        transactionTable = new JTable(model);

        // Report generation button
        JButton reportButton = new JButton("Generate Report");
        reportButton.addActionListener(new ReportAction());

        // Layout
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Amount:"));
        inputPanel.add(amountField);
        inputPanel.add(new JLabel("Description:"));
        inputPanel.add(descriptionField);
        inputPanel.add(new JLabel("Category:"));
        inputPanel.add(categoryBox);
        inputPanel.add(addButton);

        // Panel for displaying balance information
        JPanel balancePanel = new JPanel();
        balancePanel.setLayout(new GridLayout(3, 1));
        balancePanel.add(totalIncomeLabel);
        balancePanel.add(totalExpenseLabel);
        balancePanel.add(balanceLabel);

        // Add components to the frame
        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(transactionTable), BorderLayout.CENTER);
        add(balancePanel, BorderLayout.SOUTH);
        add(reportButton, BorderLayout.EAST);
    }

    // Action for adding a new transaction
    private class AddTransactionAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String amountText = amountField.getText();
            String description = descriptionField.getText();
            String category = (String) categoryBox.getSelectedItem();

            try {
                // Validate the amount is a valid number
                double amount = Double.parseDouble(amountText);

                if (!description.isEmpty()) {
                    // Add transaction to the table
                    model.addRow(new Object[]{amount, description, category});

                    // Update total income/expenses and balance
                    if (category.equals("Income")) {
                        totalIncome += amount;
                    } else {
                        totalExpenses += amount;
                    }

                    // Update labels
                    updateBalanceLabels();

                    // Clear input fields
                    amountField.setText("");
                    descriptionField.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Description cannot be empty!", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number for the amount!", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Method to update balance, income, and expenses labels
    private void updateBalanceLabels() {
        double balance = totalIncome - totalExpenses;
        totalIncomeLabel.setText("Total Income: $" + String.format("%.2f", totalIncome));
        totalExpenseLabel.setText("Total Expenses: $" + String.format("%.2f", totalExpenses));
        balanceLabel.setText("Balance: $" + String.format("%.2f", balance));
    }

    // Action for generating a report
    private class ReportAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            HashMap<String, Double> categoryTotals = new HashMap<>();

            // Calculate total expenses for each category
            for (int i = 0; i < model.getRowCount(); i++) {
                String category = (String) model.getValueAt(i, 2);
                double amount = (double) model.getValueAt(i, 0);

                if (!category.equals("Income")) {  // Ignore income in the expense report
                    categoryTotals.put(category, categoryTotals.getOrDefault(category, 0.0) + amount);
                }
            }

            // Generate a report
            StringBuilder report = new StringBuilder();
            report.append("Expense Report by Category:\n");
            for (String category : categoryTotals.keySet()) {
                report.append(category).append(": $").append(String.format("%.2f", categoryTotals.get(category))).append("\n");
            }

            // Show report in a dialog
            JOptionPane.showMessageDialog(null, report.toString(), "Expense Report", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ExpenseTracker().setVisible(true);
        });
    }
}
