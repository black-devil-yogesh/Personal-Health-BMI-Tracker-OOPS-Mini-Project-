/**
 * HealthRecord.java - Class to store and manage health records
 * Demonstrates data management and collection handling
 */

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class HealthRecord {
    private String name;
    private ArrayList<RecordEntry> records;
    
    /**
     * Inner class to represent a single health record entry
     */
    public static class RecordEntry {
        private Date date;
        private double weight;
        private double height;
        private double bmi;
        private String category;
        
        public RecordEntry(double weight, double height, double bmi, String category) {
            this.date = new Date();
            this.weight = weight;
            this.height = height;
            this.bmi = bmi;
            this.category = category;
        }
        
        public String getFormattedDate() {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            return sdf.format(date);
        }
        
        public double getWeight() { return weight; }
        public double getHeight() { return height; }
        public double getBMI() { return bmi; }
        public String getCategory() { return category; }
        
        @Override
        public String toString() {
            return String.format("[%s] Weight: %.1f kg, Height: %.1f cm, BMI: %.2f (%s)",
                    getFormattedDate(), weight, height, bmi, category);
        }
    }
    
    /**
     * Constructor
     * @param name Person's name
     */
    public HealthRecord(String name) {
        this.name = name;
        this.records = new ArrayList<>();
    }
    
    /**
     * Add a new record entry
     */
    public void addRecord(double weight, double height, double bmi, String category) {
        records.add(new RecordEntry(weight, height, bmi, category));
    }
    
    /**
     * Get all records
     */
    public ArrayList<RecordEntry> getRecords() {
        return records;
    }
    
    /**
     * Get total number of records
     */
    public int getRecordCount() {
        return records.size();
    }
    
    /**
     * Clear all records
     */
    public void clearRecords() {
        records.clear();
    }
    
    /**
     * Get latest record
     */
    public RecordEntry getLatestRecord() {
        if (records.isEmpty()) {
            return null;
        }
        return records.get(records.size() - 1);
    }
}