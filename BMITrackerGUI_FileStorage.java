/**
 * BMITrackerGUI_FileStorage.java - Main GUI with File-based Storage
 * Demonstrates file I/O operations with GUI
 * Data stored in: C:\yomp
 */

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

public class BMITrackerGUI_FileStorage extends JFrame {
    // GUI Components
    private JTextField nameField, ageField, weightField, heightField;
    private JComboBox<String> genderCombo, userCombo;
    private JButton calculateBtn, clearBtn, historyBtn, statsBtn, deleteBtn;
    private JButton loadUserBtn, exportBtn, openFolderBtn;
    private JTextArea resultArea;
    private JLabel bmiLabel, categoryLabel, statusLabel;
    
    // File Manager
    private FileManager fileManager;
    
    /**
     * Constructor to setup GUI
     */
    public BMITrackerGUI_FileStorage() {
        // Initialize file manager
        fileManager = new FileManager();
        
        // Frame setup
        setTitle("Personal Health BMI Tracker - File Storage");
        setSize(700, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Setup UI
        initializeComponents();
        refreshUserCombo();
        
        setVisible(true);
    }
    
    /**
     * Initialize all GUI components
     */
    private void initializeComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(240, 248, 255));
        
        // Title Panel
        JPanel titlePanel = createTitlePanel();
        
        // User Selection Panel
        JPanel userPanel = createUserSelectionPanel();
        
        // Input Panel
        JPanel inputPanel = createInputPanel();
        
        // Button Panel
        JPanel buttonPanel = createButtonPanel();
        
        // Result Panel
        JPanel resultPanel = createResultPanel();
        
        // Status Panel
        JPanel statusPanel = createStatusPanel();
        
        // Arrange panels
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(new Color(240, 248, 255));
        topPanel.add(titlePanel, BorderLayout.NORTH);
        topPanel.add(userPanel, BorderLayout.CENTER);
        
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(new Color(240, 248, 255));
        centerPanel.add(inputPanel, BorderLayout.NORTH);
        centerPanel.add(buttonPanel, BorderLayout.CENTER);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(resultPanel, BorderLayout.SOUTH);
        
        // Add status panel at the bottom
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(statusPanel, BorderLayout.SOUTH);
        
        add(mainPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Create title panel
     */
    private JPanel createTitlePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(70, 130, 180));
        panel.setBorder(new EmptyBorder(15, 10, 15, 10));
        
        JLabel titleLabel = new JLabel("🏥 BMI Tracker - File Storage System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        panel.add(titleLabel);
        return panel;
    }
    
    /**
     * Create user selection panel
     */
    private JPanel createUserSelectionPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBackground(new Color(230, 240, 250));
        panel.setBorder(new TitledBorder(new LineBorder(new Color(70, 130, 180), 2), 
                       "Load Existing User", TitledBorder.LEFT, TitledBorder.TOP,
                       new Font("Arial", Font.BOLD, 13), new Color(70, 130, 180)));
        
        JLabel label = new JLabel("Select User:");
        label.setFont(new Font("Arial", Font.BOLD, 13));
        
        userCombo = new JComboBox<>();
        userCombo.setFont(new Font("Arial", Font.PLAIN, 13));
        userCombo.setPreferredSize(new Dimension(200, 30));
        
        loadUserBtn = createStyledButton("Load User", new Color(70, 130, 180));
        loadUserBtn.setPreferredSize(new Dimension(120, 30));
        loadUserBtn.addActionListener(e -> loadSelectedUser());
        
        panel.add(label);
        panel.add(userCombo);
        panel.add(loadUserBtn);
        
        return panel;
    }
    
    /**
     * Create input panel
     */
    private JPanel createInputPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 12));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new TitledBorder(new LineBorder(new Color(70, 130, 180), 2), 
                           "Enter Details", TitledBorder.LEFT, TitledBorder.TOP,
                           new Font("Arial", Font.BOLD, 14), new Color(70, 130, 180)),
            new EmptyBorder(10, 10, 10, 10)
        ));
        
        panel.add(createLabel("Name:"));
        nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(nameField);
        
        panel.add(createLabel("Age:"));
        ageField = new JTextField();
        ageField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(ageField);
        
        panel.add(createLabel("Gender:"));
        String[] genders = {"Male", "Female", "Other"};
        genderCombo = new JComboBox<>(genders);
        genderCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(genderCombo);
        
        panel.add(createLabel("Weight (kg):"));
        weightField = new JTextField();
        weightField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(weightField);
        
        panel.add(createLabel("Height (cm):"));
        heightField = new JTextField();
        heightField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(heightField);
        
        return panel;
    }
    
    /**
     * Create label
     */
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        return label;
    }
    
    /**
     * Create button panel
     */
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3, 8, 8));
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(new EmptyBorder(10, 40, 10, 40));
        
        calculateBtn = createStyledButton("Calculate & Save", new Color(34, 139, 34));
        calculateBtn.addActionListener(e -> calculateAndSaveBMI());
        
        clearBtn = createStyledButton("Clear", new Color(220, 20, 60));
        clearBtn.addActionListener(e -> clearFields());
        
        historyBtn = createStyledButton("View History", new Color(70, 130, 180));
        historyBtn.addActionListener(e -> showHistory());
        
        statsBtn = createStyledButton("Statistics", new Color(255, 140, 0));
        statsBtn.addActionListener(e -> showStatistics());
        
        deleteBtn = createStyledButton("Delete Records", new Color(178, 34, 34));
        deleteBtn.addActionListener(e -> deleteRecords());
        
        exportBtn = createStyledButton("Export Report", new Color(106, 90, 205));
        exportBtn.addActionListener(e -> exportUserReport());
        
        JButton refreshBtn = createStyledButton("Refresh Users", new Color(0, 128, 128));
        refreshBtn.addActionListener(e -> refreshUserCombo());
        
        openFolderBtn = createStyledButton("Open Data Folder", new Color(46, 139, 87));
        openFolderBtn.addActionListener(e -> openDataFolder());
        
        JButton deleteUserBtn = createStyledButton("Delete User", new Color(139, 0, 0));
        deleteUserBtn.addActionListener(e -> deleteUser());
        
        panel.add(calculateBtn);
        panel.add(historyBtn);
        panel.add(statsBtn);
        panel.add(clearBtn);
        panel.add(deleteBtn);
        panel.add(exportBtn);
        panel.add(refreshBtn);
        panel.add(openFolderBtn);
        panel.add(deleteUserBtn);
        
        return panel;
    }
    
    /**
     * Create styled button
     */
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 11));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
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
                           "Results", TitledBorder.LEFT, TitledBorder.TOP,
                           new Font("Arial", Font.BOLD, 14), new Color(70, 130, 180)),
            new EmptyBorder(10, 10, 10, 10)
        ));
        
        JPanel bmiPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        bmiPanel.setBackground(Color.WHITE);
        
        bmiLabel = new JLabel("BMI: --", SwingConstants.CENTER);
        bmiLabel.setFont(new Font("Arial", Font.BOLD, 20));
        bmiLabel.setForeground(new Color(70, 130, 180));
        
        categoryLabel = new JLabel("Category: --", SwingConstants.CENTER);
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        bmiPanel.add(bmiLabel);
        bmiPanel.add(categoryLabel);
        
        resultArea = new JTextArea(5, 40);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 12));
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        
        JScrollPane scrollPane = new JScrollPane(resultArea);
        
        panel.add(bmiPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Create status panel
     */
    private JPanel createStatusPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(new Color(220, 230, 240));
        panel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        
        statusLabel = new JLabel("✓ File Storage Ready - Data: C:\\yomp");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        statusLabel.setForeground(new Color(0, 128, 0));
        
        panel.add(statusLabel);
        return panel;
    }
    
    /**
     * Calculate and save BMI to file
     */
    private void calculateAndSaveBMI() {
        try {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                throw new IllegalArgumentException("Please enter your name");
            }
            
            int age = Integer.parseInt(ageField.getText().trim());
            if (age <= 0 || age > 150) {
                throw new IllegalArgumentException("Please enter valid age (1-150)");
            }
            
            String gender = (String) genderCombo.getSelectedItem();
            
            double weight = Double.parseDouble(weightField.getText().trim());
            if (weight <= 0 || weight > 500) {
                throw new IllegalArgumentException("Please enter valid weight (0-500 kg)");
            }
            
            double height = Double.parseDouble(heightField.getText().trim());
            if (height <= 0 || height > 300) {
                throw new IllegalArgumentException("Please enter valid height (0-300 cm)");
            }
            
            // Create BMI Calculator
            BMICalculator calculator = new BMICalculator(name, age, gender, weight, height);
            
            // Save to file
            fileManager.saveUser(name, age, gender);
            boolean saved = fileManager.saveBMIRecord(name, weight, height, 
                                                     calculator.getBMI(), calculator.getCategory());
            
            if (saved) {
                displayResults(calculator);
                updateStatus("✓ BMI calculated and saved to C:\\yomp!", new Color(0, 128, 0));
                refreshUserCombo();
            } else {
                updateStatus("✗ Error saving to file!", new Color(255, 0, 0));
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Please enter valid numeric values!", 
                "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), 
                "Validation Error", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * Display results
     */
    private void displayResults(BMICalculator calculator) {
        bmiLabel.setText("BMI: " + calculator.getBMI());
        categoryLabel.setText("Category: " + calculator.getCategory());
        
        Color categoryColor;
        switch (calculator.getCategory()) {
            case "Underweight": categoryColor = new Color(255, 140, 0); break;
            case "Normal weight": categoryColor = new Color(34, 139, 34); break;
            case "Overweight": categoryColor = new Color(255, 165, 0); break;
            case "Obese": categoryColor = new Color(220, 20, 60); break;
            default: categoryColor = Color.BLACK;
        }
        categoryLabel.setForeground(categoryColor);
        
        StringBuilder result = new StringBuilder();
        result.append("=== BMI Analysis ===\n\n");
        result.append(calculator.toString()).append("\n\n");
        result.append("Health Recommendation:\n");
        result.append(calculator.getHealthRecommendation()).append("\n\n");
        result.append("BMI Categories:\n");
        result.append("• Underweight: BMI < 18.5\n");
        result.append("• Normal weight: BMI 18.5 - 24.9\n");
        result.append("• Overweight: BMI 25 - 29.9\n");
        result.append("• Obese: BMI ≥ 30\n");
        
        resultArea.setText(result.toString());
    }
    
    /**
     * Clear all fields
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
     * Load selected user from combo box
     */
    private void loadSelectedUser() {
        String selectedUser = (String) userCombo.getSelectedItem();
        if (selectedUser == null || selectedUser.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please select a user from the list!", 
                "No User Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        FileManager.UserDetails user = fileManager.getUserDetails(selectedUser);
        if (user != null) {
            nameField.setText(user.getName());
            ageField.setText(String.valueOf(user.getAge()));
            genderCombo.setSelectedItem(user.getGender());
            updateStatus("✓ User loaded: " + user.getName(), new Color(0, 128, 0));
        } else {
            JOptionPane.showMessageDialog(this, 
                "Error loading user details!", 
                "Load Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Show history of BMI records
     */
    private void showHistory() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a name or load a user!", 
                "No User", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        ArrayList<FileManager.BMIRecord> records = fileManager.getUserRecords(name);
        
        if (records.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No records found for " + name + "!", 
                "No History", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        StringBuilder history = new StringBuilder();
        history.append("=== BMI History for ").append(name).append(" ===\n\n");
        history.append("Total Records: ").append(records.size()).append("\n\n");
        
        int count = 1;
        for (FileManager.BMIRecord record : records) {
            history.append("Record #").append(count++).append("\n");
            history.append(record.toString()).append("\n\n");
        }
        
        JTextArea textArea = new JTextArea(history.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(550, 400));
        
        JOptionPane.showMessageDialog(this, 
            scrollPane, 
            "BMI History - " + records.size() + " Record(s)", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Show statistics
     */
    private void showStatistics() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a name or load a user!", 
                "No User", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        FileManager.BMIStatistics stats = fileManager.getUserStatistics(name);
        
        if (stats == null) {
            JOptionPane.showMessageDialog(this, 
                "No statistics available for " + name + "!", 
                "No Data", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        StringBuilder statsText = new StringBuilder();
        statsText.append("=== Statistics for ").append(name).append(" ===\n\n");
        statsText.append(stats.toString());
        
        JTextArea textArea = new JTextArea(statsText.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JOptionPane.showMessageDialog(this, 
            textArea, 
            "BMI Statistics", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Delete records
     */
    private void deleteRecords() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a name!", 
                "No User", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete all records for " + name + "?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean deleted = fileManager.deleteUserRecords(name);
            if (deleted) {
                JOptionPane.showMessageDialog(this, 
                    "All records deleted for " + name + "!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                updateStatus("✓ Records deleted for " + name, new Color(0, 128, 0));
            } else {
                JOptionPane.showMessageDialog(this, 
                    "No records found to delete!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Delete user completely
     */
    private void deleteUser() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a name or load a user!", 
                "No User", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete user '" + name + "' and ALL their records?\nThis action cannot be undone!", 
            "Confirm Delete User", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean deleted = fileManager.deleteUser(name);
            if (deleted) {
                JOptionPane.showMessageDialog(this, 
                    "User '" + name + "' and all records deleted successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                refreshUserCombo();
                updateStatus("✓ User deleted: " + name, new Color(0, 128, 0));
            } else {
                JOptionPane.showMessageDialog(this, 
                    "User not found or error deleting!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Export user report
     */
    private void exportUserReport() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a name or load a user!", 
                "No User", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String fileName = name.replaceAll("[^a-zA-Z0-9]", "_") + "_Report.txt";
        boolean exported = fileManager.exportUserData(name, fileName);
        
        if (exported) {
            JOptionPane.showMessageDialog(this, 
                "Report exported successfully!\nFile: " + fileName + "\nLocation: C:\\yomp", 
                "Export Successful", JOptionPane.INFORMATION_MESSAGE);
            updateStatus("✓ Report exported: " + fileName, new Color(0, 128, 0));
        } else {
            JOptionPane.showMessageDialog(this, 
                "Error exporting report or no data available!", 
                "Export Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Open data folder
     */
    private void openDataFolder() {
        try {
            Desktop.getDesktop().open(new File("C:\\yomp"));
            updateStatus("✓ Opened data folder", new Color(0, 128, 0));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Could not open folder!\nPath: C:\\yomp", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Refresh user combo box
     */
    private void refreshUserCombo() {
        userCombo.removeAllItems();
        ArrayList<String> users = fileManager.getAllUsers();
        for (String user : users) {
            userCombo.addItem(user);
        }
        updateStatus("✓ User list refreshed (" + users.size() + " users)", new Color(0, 128, 0));
    }
    
    /**
     * Update status label
     */
    private void updateStatus(String message, Color color) {
        statusLabel.setText(message);
        statusLabel.setForeground(color);
    }
    
    /**
     * Main method
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> new BMITrackerGUI_FileStorage());
    }
}