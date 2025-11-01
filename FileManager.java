/**
 * FileManager.java - Handles file-based storage of BMI records
 * Demonstrates file I/O operations and data persistence
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class FileManager {
    private static final String DATA_DIRECTORY = "C:\\yomp";
    private static final String USER_FILE = "users.txt";
    private static final String RECORDS_EXTENSION = "_records.txt";
    
    /**
     * Constructor - Creates data directory if it doesn't exist
     */
    public FileManager() {
        File directory = new File(DATA_DIRECTORY);
        if (!directory.exists()) {
            if (directory.mkdir()) {
                System.out.println("Data directory created: " + DATA_DIRECTORY);
            }
        }
    }
    
    /**
     * Save or update user information
     * Format: name|age|gender|createdDate
     */
    public boolean saveUser(String name, int age, String gender) {
        File userFile = new File(DATA_DIRECTORY, USER_FILE);
        ArrayList<String> users = new ArrayList<>();
        boolean userExists = false;
        
        // Read existing users
        if (userFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 4 && parts[0].equals(name)) {
                        // Update existing user
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        line = name + "|" + age + "|" + gender + "|" + parts[3];
                        userExists = true;
                    }
                    users.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        
        // Add new user if doesn't exist
        if (!userExists) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String newUser = name + "|" + age + "|" + gender + "|" + sdf.format(new Date());
            users.add(newUser);
        }
        
        // Write all users back to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile))) {
            for (String user : users) {
                writer.write(user);
                writer.newLine();
            }
            System.out.println("User saved successfully: " + name);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Save BMI record for a user
     * Format: date|weight|height|bmi|category
     */
    public boolean saveBMIRecord(String name, double weight, double height, 
                                  double bmi, String category) {
        File recordFile = new File(DATA_DIRECTORY, name + RECORDS_EXTENSION);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String timestamp = sdf.format(new Date());
        
        String record = timestamp + "|" + weight + "|" + height + "|" + bmi + "|" + category;
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(recordFile, true))) {
            writer.write(record);
            writer.newLine();
            System.out.println("BMI record saved for: " + name);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Get all records for a specific user
     */
    public ArrayList<BMIRecord> getUserRecords(String name) {
        ArrayList<BMIRecord> records = new ArrayList<>();
        File recordFile = new File(DATA_DIRECTORY, name + RECORDS_EXTENSION);
        
        if (!recordFile.exists()) {
            System.out.println("No records found for user: " + name);
            return records;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(recordFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 5) {
                    BMIRecord record = new BMIRecord(
                        parts[0], // timestamp
                        Double.parseDouble(parts[1]), // weight
                        Double.parseDouble(parts[2]), // height
                        Double.parseDouble(parts[3]), // bmi
                        parts[4] // category
                    );
                    records.add(record);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return records;
    }
    
    /**
     * Get all user names
     */
    public ArrayList<String> getAllUsers() {
        ArrayList<String> users = new ArrayList<>();
        File userFile = new File(DATA_DIRECTORY, USER_FILE);
        
        if (!userFile.exists()) {
            return users;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 1) {
                    users.add(parts[0]); // Add user name
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return users;
    }
    
    /**
     * Get user details
     */
    public UserDetails getUserDetails(String name) {
        File userFile = new File(DATA_DIRECTORY, USER_FILE);
        
        if (!userFile.exists()) {
            return null;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 4 && parts[0].equals(name)) {
                    return new UserDetails(
                        parts[0], // name
                        Integer.parseInt(parts[1]), // age
                        parts[2], // gender
                        parts[3] // created date
                    );
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Delete all records for a user
     */
    public boolean deleteUserRecords(String name) {
        File recordFile = new File(DATA_DIRECTORY, name + RECORDS_EXTENSION);
        
        if (recordFile.exists()) {
            if (recordFile.delete()) {
                System.out.println("All records deleted for: " + name);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Delete user from users file
     */
    public boolean deleteUser(String name) {
        File userFile = new File(DATA_DIRECTORY, USER_FILE);
        ArrayList<String> users = new ArrayList<>();
        boolean userFound = false;
        
        if (!userFile.exists()) {
            return false;
        }
        
        // Read all users except the one to delete
        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 1 && !parts[0].equals(name)) {
                    users.add(line);
                } else if (parts[0].equals(name)) {
                    userFound = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        
        if (!userFound) {
            return false;
        }
        
        // Write back remaining users
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile))) {
            for (String user : users) {
                writer.write(user);
                writer.newLine();
            }
            
            // Also delete user's records file
            deleteUserRecords(name);
            
            System.out.println("User deleted: " + name);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Get statistics for a user
     */
    public BMIStatistics getUserStatistics(String name) {
        ArrayList<BMIRecord> records = getUserRecords(name);
        
        if (records.isEmpty()) {
            return null;
        }
        
        double totalBMI = 0;
        double minBMI = Double.MAX_VALUE;
        double maxBMI = Double.MIN_VALUE;
        double minWeight = Double.MAX_VALUE;
        double maxWeight = Double.MIN_VALUE;
        
        for (BMIRecord record : records) {
            totalBMI += record.getBmi();
            minBMI = Math.min(minBMI, record.getBmi());
            maxBMI = Math.max(maxBMI, record.getBmi());
            minWeight = Math.min(minWeight, record.getWeight());
            maxWeight = Math.max(maxWeight, record.getWeight());
        }
        
        double avgBMI = totalBMI / records.size();
        
        return new BMIStatistics(
            records.size(),
            avgBMI,
            minBMI,
            maxBMI,
            minWeight,
            maxWeight
        );
    }
    
    /**
     * Export user data to a formatted text file
     */
    public boolean exportUserData(String name, String exportFileName) {
        UserDetails user = getUserDetails(name);
        ArrayList<BMIRecord> records = getUserRecords(name);
        BMIStatistics stats = getUserStatistics(name);
        
        if (user == null || records.isEmpty()) {
            return false;
        }
        
        File exportFile = new File(DATA_DIRECTORY, exportFileName);
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(exportFile))) {
            writer.write("================================================");
            writer.newLine();
            writer.write("        BMI TRACKER - USER REPORT");
            writer.newLine();
            writer.write("================================================");
            writer.newLine();
            writer.newLine();
            
            writer.write("User Information:");
            writer.newLine();
            writer.write("  Name: " + user.getName());
            writer.newLine();
            writer.write("  Age: " + user.getAge());
            writer.newLine();
            writer.write("  Gender: " + user.getGender());
            writer.newLine();
            writer.write("  Member Since: " + user.getCreatedAt());
            writer.newLine();
            writer.newLine();
            
            if (stats != null) {
                writer.write("Statistics:");
                writer.newLine();
                writer.write("  Total Records: " + stats.getTotalRecords());
                writer.newLine();
                writer.write("  Average BMI: " + String.format("%.2f", stats.getAvgBMI()));
                writer.newLine();
                writer.write("  BMI Range: " + String.format("%.2f - %.2f", stats.getMinBMI(), stats.getMaxBMI()));
                writer.newLine();
                writer.write("  Weight Range: " + String.format("%.1f - %.1f kg", stats.getMinWeight(), stats.getMaxWeight()));
                writer.newLine();
                writer.newLine();
            }
            
            writer.write("BMI Records History:");
            writer.newLine();
            writer.write("------------------------------------------------");
            writer.newLine();
            
            int count = 1;
            for (BMIRecord record : records) {
                writer.write("Record #" + count++);
                writer.newLine();
                writer.write("  Date: " + record.getTimestamp());
                writer.newLine();
                writer.write("  Weight: " + record.getWeight() + " kg");
                writer.newLine();
                writer.write("  Height: " + record.getHeight() + " cm");
                writer.newLine();
                writer.write("  BMI: " + String.format("%.2f", record.getBmi()));
                writer.newLine();
                writer.write("  Category: " + record.getCategory());
                writer.newLine();
                writer.write("------------------------------------------------");
                writer.newLine();
            }
            
            writer.write("\nReport generated on: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
            writer.newLine();
            
            System.out.println("Data exported successfully to: " + exportFileName);
            return true;
            
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Get the data directory path
     */
    public String getDataDirectory() {
        return new File(DATA_DIRECTORY).getAbsolutePath();
    }
    
    // Inner class for BMI Record
    public static class BMIRecord {
        private String timestamp;
        private double weight;
        private double height;
        private double bmi;
        private String category;
        
        public BMIRecord(String timestamp, double weight, double height, 
                        double bmi, String category) {
            this.timestamp = timestamp;
            this.weight = weight;
            this.height = height;
            this.bmi = bmi;
            this.category = category;
        }
        
        // Getters
        public String getTimestamp() { return timestamp; }
        public double getWeight() { return weight; }
        public double getHeight() { return height; }
        public double getBmi() { return bmi; }
        public String getCategory() { return category; }
        
        @Override
        public String toString() {
            return String.format("[%s] Weight: %.1f kg, Height: %.1f cm, BMI: %.2f (%s)",
                    timestamp, weight, height, bmi, category);
        }
    }
    
    // Inner class for User Details
    public static class UserDetails {
        private String name;
        private int age;
        private String gender;
        private String createdAt;
        
        public UserDetails(String name, int age, String gender, String createdAt) {
            this.name = name;
            this.age = age;
            this.gender = gender;
            this.createdAt = createdAt;
        }
        
        // Getters
        public String getName() { return name; }
        public int getAge() { return age; }
        public String getGender() { return gender; }
        public String getCreatedAt() { return createdAt; }
        
        @Override
        public String toString() {
            return String.format("Name: %s, Age: %d, Gender: %s, Member Since: %s",
                    name, age, gender, createdAt);
        }
    }
    
    // Inner class for Statistics
    public static class BMIStatistics {
        private int totalRecords;
        private double avgBMI;
        private double minBMI;
        private double maxBMI;
        private double minWeight;
        private double maxWeight;
        
        public BMIStatistics(int totalRecords, double avgBMI, double minBMI, 
                           double maxBMI, double minWeight, double maxWeight) {
            this.totalRecords = totalRecords;
            this.avgBMI = avgBMI;
            this.minBMI = minBMI;
            this.maxBMI = maxBMI;
            this.minWeight = minWeight;
            this.maxWeight = maxWeight;
        }
        
        // Getters
        public int getTotalRecords() { return totalRecords; }
        public double getAvgBMI() { return avgBMI; }
        public double getMinBMI() { return minBMI; }
        public double getMaxBMI() { return maxBMI; }
        public double getMinWeight() { return minWeight; }
        public double getMaxWeight() { return maxWeight; }
        
        @Override
        public String toString() {
            return String.format("Total Records: %d\nAverage BMI: %.2f\n" +
                               "BMI Range: %.2f - %.2f\nWeight Range: %.1f - %.1f kg",
                               totalRecords, avgBMI, minBMI, maxBMI, minWeight, maxWeight);
        }
    }
}