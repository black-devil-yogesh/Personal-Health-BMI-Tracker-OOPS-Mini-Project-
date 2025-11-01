/**
 * BMICalculator.java - Class for BMI calculations
 * Demonstrates Inheritance from Person class
 */

public class BMICalculator extends Person {
    // Private attributes
    private double weight; // in kg
    private double height; // in cm
    private double bmi;
    private String category;
    
    /**
     * Constructor to initialize BMICalculator object
     * @param name Person's name
     * @param age Person's age
     * @param gender Person's gender
     * @param weight Weight in kg
     * @param height Height in cm
     */
    public BMICalculator(String name, int age, String gender, double weight, double height) {
        super(name, age, gender); // Call parent constructor
        this.weight = weight;
        this.height = height;
        calculateBMI();
    }
    
    /**
     * Calculate BMI and determine category
     */
    private void calculateBMI() {
        if (height > 0) {
            double heightInMeters = height / 100.0;
            this.bmi = weight / (heightInMeters * heightInMeters);
            this.category = getBMICategory();
        }
    }
    
    /**
     * Determine BMI category based on calculated BMI
     * @return BMI category string
     */
    private String getBMICategory() {
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi >= 18.5 && bmi < 25) {
            return "Normal weight";
        } else if (bmi >= 25 && bmi < 30) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }
    
    /**
     * Get health recommendation based on BMI category
     * @return Health recommendation string
     */
    public String getHealthRecommendation() {
        switch (category) {
            case "Underweight":
                return "Consider increasing caloric intake and consult a nutritionist.";
            case "Normal weight":
                return "Maintain your current healthy lifestyle!";
            case "Overweight":
                return "Consider regular exercise and a balanced diet.";
            case "Obese":
                return "Consult a healthcare professional for personalized advice.";
            default:
                return "Please check your input values.";
        }
    }
    
    // Getter methods
    public double getWeight() {
        return weight;
    }
    
    public double getHeight() {
        return height;
    }
    
    public double getBMI() {
        return Math.round(bmi * 100.0) / 100.0;
    }
    
    public String getCategory() {
        return category;
    }
    
    // Setter methods with recalculation
    public void setWeight(double weight) {
        if (weight > 0) {
            this.weight = weight;
            calculateBMI();
        }
    }
    
    public void setHeight(double height) {
        if (height > 0) {
            this.height = height;
            calculateBMI();
        }
    }
    
    @Override
    public String toString() {
        return super.toString() + 
               "\nWeight: " + weight + " kg" +
               "\nHeight: " + height + " cm" +
               "\nBMI: " + getBMI() +
               "\nCategory: " + category;
    }
}