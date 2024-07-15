package Classes;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;


public class Doctor {
    private String name;
    private String specialization;
    private String contact;
    private List<Appointment> appointments = new ArrayList<>();

    // Constructor
    public Doctor(String name, String specialization, String contact) {
        this.name = name;
        this.specialization = specialization;
        this.contact = contact;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getContact() {
        return contact;
    }

    // Method to save doctor record to a file
    public void saveToFile(String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("Name: " + name);
            writer.println("Specialization: " + specialization);
            writer.println("Contact: " + contact);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to add appointment
    public void addAppointment(String patientName, LocalDateTime dateTime) {
        appointments.add(new Appointment(this, patientName, dateTime));
    }

    // Method to check if doctor has overlapping appointments
    public boolean hasOverlappingAppointment(LocalDateTime newDateTime) {
        for (Appointment appointment : appointments) {
            if (appointment.getDateTime().equals(newDateTime) ||
                (appointment.getDateTime().isBefore(newDateTime.plusHours(1)) &&
                appointment.getDateTime().isAfter(newDateTime.minusHours(1)))) {
                return true;
            }
        }
        return false;
    }

    // Get appointments for a specific date
    public List<Appointment> getAppointmentsForDate(LocalDateTime date) {
        List<Appointment> appointmentsForDate = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getDateTime().toLocalDate().equals(date.toLocalDate())) {
                appointmentsForDate.add(appointment);
            }
        }
        return appointmentsForDate;
    }
}
