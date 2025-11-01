# Personal Health BMI Tracker with File Storage

A comprehensive Java application for tracking Body Mass Index (BMI) with a graphical user interface and file-based storage system built using Object-Oriented Programming principles.

*Data Storage Location:* C:\yomp

## 📋 Project Overview

This mini-project demonstrates core OOP concepts including:
- *Encapsulation*: Private attributes with getter/setter methods
- *Inheritance*: BMICalculator extends Person class
- *Polymorphism*: Method overriding (toString methods)
- *Abstraction*: Hiding complex BMI calculation logic
- *GUI Programming*: Interactive Swing-based interface

## 🎯 Features

- ✅ Calculate BMI based on weight and height
- ✅ Categorize BMI (Underweight, Normal, Overweight, Obese)
- ✅ Personalized health recommendations
- ✅ Track BMI history with timestamps
- ✅ *File-based storage in C:\yomp directory*
- ✅ Load existing users
- ✅ View statistics (average, min, max BMI)
- ✅ Export detailed reports to text files
- ✅ Delete records and users
- ✅ Open data folder directly from GUI
- ✅ Clean and intuitive GUI interface
- ✅ Input validation and error handling
- ✅ Color-coded BMI categories

## 📁 File Structure


BMITracker/
│
├── Person.java                      # Base class with personal information
├── BMICalculator.java               # BMI calculation and health analysis
├── FileManager.java                 # File I/O operations and data management
├── BMITrackerGUI_FileStorage.java   # Main GUI application with file storage
└── README.md                        # Project documentation

Data Storage (Auto-created):
C:\yomp\
├── users.txt                        # User information
└── [username]_records.txt           # Individual user BMI records


## 🔧 Requirements

- Java Development Kit (JDK) 8 or higher
- Any Java IDE (Eclipse, IntelliJ IDEA, NetBeans) or command line

## 🚀 How to Run

### Using Command Line:

1. *Compile all Java files:*
bash
javac Person.java BMICalculator.java HealthRecord.java BMITrackerGUI.java


2. *Run the application:*
bash
java BMITrackerGUI


### Using IDE:

1. Create a new Java project
2. Add all four .java files to the project
3. Run BMITrackerGUI.java as the main class

## 💻 Usage Instructions

1. *Enter Personal Details:*
   - Name
   - Age (1-150)
   - Gender (Male/Female/Other)
   - Weight in kg (0-500)
   - Height in cm (0-300)

2. *Calculate BMI:*
   - Click "Calculate BMI" button
   - View your BMI score and category
   - Read personalized health recommendations

3. *View History:*
   - Click "View History" to see all previous BMI calculations
   - Each record shows date, time, and BMI details

4. *Clear Form:*
   - Click "Clear" to reset all fields

## 📊 BMI Categories

| Category | BMI Range |
|----------|-----------|
| Underweight | < 18.5 |
| Normal weight | 18.5 - 24.9 |
| Overweight | 25 - 29.9 |
| Obese | ≥ 30 |

## 🎓 OOP Concepts Demonstrated

### 1. *Encapsulation*
- Private variables in Person and BMICalculator classes
- Public getter/setter methods with validation

### 2. *Inheritance*
- BMICalculator extends Person class
- Inherits attributes and methods from parent class

### 3. *Polymorphism*
- Overridden toString() methods in all classes
- Method overloading in setter methods

### 4. *Abstraction*
- Private helper methods (calculateBMI, getBMICategory)
- User interacts with simple public interface

### 5. *Composition*
- HealthRecord contains RecordEntry objects
- GUI contains multiple component objects

## 🎨 GUI Features

- Modern color scheme with blue theme
- Responsive button hover effects
- Color-coded BMI categories for quick identification
- Scrollable results and history panels
- Professional border styling
- User-friendly error messages

## 📝 Sample Output


=== BMI Analysis ===

Name: John Doe, Age: 25, Gender: Male
Weight: 70.0 kg
Height: 175.0 cm
BMI: 22.86
Category: Normal weight

Health Recommendation:
Maintain your current healthy lifestyle!


## 🔍 Code Highlights

- *Input Validation*: Prevents invalid data entry
- *Error Handling*: Try-catch blocks for robust operation
- *Date Tracking*: Automatic timestamp for each record
- *ArrayList Usage*: Dynamic storage of health records
- *Inner Classes*: RecordEntry nested within HealthRecord

## 🤝 Contributing

This is a student project for educational purposes. Feel free to enhance features such as:
- BMI trends graphing
- Export records to file
- Multiple user profiles
- BMI goal tracking
- Additional health metrics

## 📄 License

This project is created for educational purposes as part of an Object-Oriented Programming course.

## 👨‍💻 Author

Created as a mini-project demonstrating OOP concepts in Java

---

*Note*: This application is for educational and informational purposes only. Always consult with healthcare professionals for medical advice.
