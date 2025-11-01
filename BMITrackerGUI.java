/**
 * BMITrackerGUI.java - Main GUI application for BMI Tracker
 * Demonstrates GUI programming with Swing
 */

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class BMITrackerGUI extends JFrame {
    // GUI Components
    private JTextField nameField, ageField, weightField, heightField;
    private JComboBox<String> genderCombo;
    private JButton calculateBtn, clearBtn, historyBtn;
    private JTextArea resultArea;
    private JLabel bmiLabel, categoryLabel;
    private HealthRecord healthRecord;
    
    /**
     * Constructor to setup GUI
     */
    public BMITrackerGUI() {
        // Frame setup
        setTitle("Personal Health BMI Tracker");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Initialize health record
        healthRecord = new HealthRecord("");
        
        // Setup UI
        initializeComponents();
        
        setVisible(true);
    }
    
    /**
     * Initialize all GUI components
     */
    private void initializeComponents() {
        // Main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 248, 255));
        
        // Title Panel
        JPanel titlePanel = createTitlePanel();
        
        // Input Panel
        JPanel inputPanel = createInputPanel();
        
        // Button Panel
        JPanel buttonPanel = createButtonPanel();
        
        // Result Panel
        JPanel resultPanel = createResultPanel();
        
        // Add panels to main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        
        JPanel southPanel = new JPanel(new BorderLayout(10, 10));
        southPanel.setBackground(new Color(240, 248, 255));
        southPanel.add(buttonPanel, BorderLayout.NORTH);
        southPanel.add(resultPanel, BorderLayout.CENTER);
        
        mainPanel.add(southPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    /**
     * Create title panel
     */
    private JPanel createTitlePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(70, 130, 180));
        panel.setBorder(new EmptyBorder(15, 10, 15, 10));
        
        JLabel titleLabel = new JLabel("🏥 Personal Health BMI Tracker");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        panel.add(titleLabel);
        return panel;
    }
    
    /**
     * Create input panel with form fields
     */
    private JPanel createInputPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 15));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new TitledBorder(new LineBorder(new Color(70, 130, 180), 2), 
                           "Enter Your Details", 
                           TitledBorder.LEFT, 
                           TitledBorder.TOP,
                           new Font("Arial", Font.BOLD, 14),
                           new Color(70, 130, 180)),
            new EmptyBorder(10, 10, 10, 10)
        ));
        
        // Name
        panel.add(createLabel("Name:"));
        nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(nameField);
        
        // Age
        panel.add(createLabel("Age:"));
        ageField = new JTextField();
        ageField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(ageField);
        
        // Gender
        panel.add(createLabel("Gender:"));
        String[] genders = {"Male", "Female", "Other"};
        genderCombo = new JComboBox<>(genders);
        genderCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(genderCombo);
        
        // Weight
        panel.add(createLabel("Weight (kg):"));
        weightField = new JTextField();
        weightField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(weightField);
        
        // Height
        panel.add(createLabel("Height (cm):"));
        heightField = new JTextField();
        heightField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(heightField);
        
        return panel;
    }
    
    /**
     * Create label with styling
     */
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(new Color(50, 50, 50));
        return label;
    }
    
    /**
     * Create button panel
     */
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panel.setBackground(new Color(240, 248, 255));
        
        // Calculate Button
        calculateBtn = createStyledButton("Calculate BMI", new Color(34, 139, 34));
        calculateBtn.addActionListener(e -> calculateBMI());
        
        // Clear Button
        clearBtn = createStyledButton("Clear", new Color(220, 20, 60));
        clearBtn.addActionListener(e -> clearFields());
        
        // History Button
        historyBtn = createStyledButton("View History", new Color(70, 130, 180));
        historyBtn.addActionListener(e -> showHistory());
        
        panel.add(calculateBtn);
        panel.add(clearBtn);
        panel.add(historyBtn);
        
        return panel;
    }
    
    /**
     * Create styled button
     */
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(140, 35));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
    
    /**
     * Create result panel
     */
    private JPanel createResultPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new TitledBorder(new LineBorder(new Color(70, 130, 180), 2), 
                           "Results", 
                           TitledBorder.LEFT, 
                           TitledBorder.TOP,
                           new Font("Arial", Font.BOLD, 14),
                           new Color(70, 130, 180)),
            new EmptyBorder(10, 10, 10, 10)
        ));
        
        // BMI Display Panel
        JPanel bmiPanel = new JPanel();
        bmiPanel.setLayout(new GridLayout(2, 1, 5, 5));
        bmiPanel.setBackground(Color.WHITE);
        
        bmiLabel = new JLabel("BMI: --", SwingConstants.CENTER);
        bmiLabel.setFont(new Font("Arial", Font.BOLD, 20));
        bmiLabel.setForeground(new Color(70, 130, 180));
        
        categoryLabel = new JLabel("Category: --", SwingConstants.CENTER);
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        bmiPanel.add(bmiLabel);
        bmiPanel.add(categoryLabel);
        
        // Result text area
        resultArea = new JTextArea(6, 40);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 13));
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        JScrollPane scrollPane = new JScrollPane(resultArea);
        
        panel.add(bmiPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Calculate BMI when button is clicked
     */
    private void calculateBMI() {
        try {
            // Validate and get input
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                throw new IllegalArgumentException("Please enter your name");
            }
            
            int age = Integer.parseInt(ageField.getText().trim());
            if (age <= 0 || age > 150) {
                throw new IllegalArgumentException("Please enter a valid age (1-150)");
            }
            
            String gender = (String) genderCombo.getSelectedItem();
            
            double weight = Double.parseDouble(weightField.getText().trim());
            if (weight <= 0 || weight > 500) {
                throw new IllegalArgumentException("Please enter a valid weight (0-500 kg)");
            }
            
            double height = Double.parseDouble(heightField.getText().trim());
            if (height <= 0 || height > 300) {
                throw new IllegalArgumentException("Please enter a valid height (0-300 cm)");
            }
            
            // Create BMI Calculator object
            BMICalculator calculator = new BMICalculator(name, age, gender, weight, height);
            
            // Update health record
            healthRecord = new HealthRecord(name);
            healthRecord.addRecord(weight, height, calculator.getBMI(), calculator.getCategory());
            
            // Display results
            displayResults(calculator);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Please enter valid numeric values for age, weight, and height", 
                "Input Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, 
                e.getMessage(), 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * Display BMI calculation results
     */
    private void displayResults(BMICalculator calculator) {
        // Update BMI and category labels
        bmiLabel.setText("BMI: " + calculator.getBMI());
        categoryLabel.setText("Category: " + calculator.getCategory());
        
        // Color code the category
        Color categoryColor;
        switch (calculator.getCategory()) {
            case "Underweight":
                categoryColor = new Color(255, 140, 0);
                break;
            case "Normal weight":
                categoryColor = new Color(34, 139, 34);
                break;
            case "Overweight":
                categoryColor = new Color(255, 165, 0);
                break;
            case "Obese":
                categoryColor = new Color(220, 20, 60);
                break;
            default:
                categoryColor = Color.BLACK;
        }
        categoryLabel.setForeground(categoryColor);
        
        // Display detailed information
        StringBuilder result = new StringBuilder();
        result.append("=== BMI Analysis ===\n\n");
        result.append(calculator.toString()).append("\n\n");
        result.append("Health Recommendation:\n");
        result.append(calculator.getHealthRecommendation()).append("\n\n");
        result.append("BMI Categories Reference:\n");
        result.append("• Underweight: BMI < 18.5\n");
        result.append("• Normal weight: BMI 18.5 - 24.9\n");
        result.append("• Overweight: BMI 25 - 29.9\n");
        result.append("• Obese: BMI ≥ 30\n");
        
        resultArea.setText(result.toString());
    }
    
    /**
     * Clear all input fields
     */
    private void clearFields() {
        nameField.setText("");
        ageField.setText("");
        weightField.setText("");
        heightField.setText("");
        genderCombo.setSelectedIndex(0);
        resultArea.setText("");
        bmiLabel.setText("BMI: --");
        categoryLabel.setText("Category: --");
        categoryLabel.setForeground(Color.BLACK);
    }
    
    /**
     * Show history of BMI records
     */
    private void showHistory() {
        if (healthRecord.getRecordCount() == 0) {
            JOptionPane.showMessageDialog(this, 
                "No records available yet. Calculate BMI first!", 
                "No History", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        StringBuilder history = new StringBuilder();
        history.append("=== BMI History ===\n\n");
        
        for (HealthRecord.RecordEntry entry : healthRecord.getRecords()) {
            history.append(entry.toString()).append("\n\n");
        }
        
        JTextArea textArea = new JTextArea(history.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        
        JOptionPane.showMessageDialog(this, 
            scrollPane, 
            "BMI History - " + healthRecord.getRecordCount() + " Record(s)", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Main method to run the application
     */
    public static void main(String[] args) {
        // Set system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Create and show GUI
        SwingUtilities.invokeLater(() -> new BMITrackerGUI());
    }
}