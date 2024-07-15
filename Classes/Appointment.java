package Classes;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {
    private Doctor doctor;
    private String patientName;
    private LocalDateTime dateTime;

    public Appointment(Doctor doctor, String patientName, LocalDateTime dateTime) {
        this.doctor = doctor;
        this.patientName = patientName;
        this.dateTime = dateTime;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public String getPatientName() {
        return patientName;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    // Method to save appointment information to a file
    public void saveToFile(String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            writer.println("Patient: " + patientName);
            writer.println("Date and Time: " + dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            writer.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}