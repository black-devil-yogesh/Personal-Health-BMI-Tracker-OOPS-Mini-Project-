/**
 * Person.java - Base class representing a person with health attributes
 * Demonstrates encapsulation and data hiding
 */

public class Person {
    // Private attributes (Encapsulation)
    private String name;
    private int age;
    private String gender;
    
    /**
     * Constructor to initialize Person object
     * @param name Person's name
     * @param age Person's age
     * @param gender Person's gender
     */
    public Person(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
    
    // Getter methods
    public String getName() {
        return name;
    }
    
    public int getAge() {
        return age;
    }
    
    public String getGender() {
        return gender;
    }
    
    // Setter methods with validation
    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        }
    }
    
    public void setAge(int age) {
        if (age > 0 && age < 150) {
            this.age = age;
        }
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    @Override
    public String toString() {
        return "Name: " + name + ", Age: " + age + ", Gender: " + gender;
    }
}