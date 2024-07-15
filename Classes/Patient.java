package Classes;
import java.io.*;

public class Patient {
    private String name;
    private int age;
    private String gender;
    private String contact;

    // Constructor
    public Patient(String name, int age, String gender, String contact) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.contact = contact;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getContact() {
        return contact;
    }

    // Method to save patient record to a file
    public void saveToFile(String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("Name: " + name);
            writer.println("Age: " + age);
            writer.println("Gender: " + gender);
            writer.println("Contact: " + contact);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
