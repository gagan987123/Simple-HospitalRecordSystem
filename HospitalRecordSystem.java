import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import Classes.Patient;
import Classes.Appointment;
import Classes.Doctor;
// Class to represent a patient

// Class to represent a doctor


// Class to represent an appointment


// Class to represent a hospital record system
public class HospitalRecordSystem {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static DateTimeFormatter compactFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    private List<Doctor> doctors = new ArrayList<>();
    private List<Patient> patients = new ArrayList<>();

    public HospitalRecordSystem() {
        JFrame frame = new JFrame("Hospital Record System");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JButton addPatientButton = new JButton("Add Patient");
        addPatientButton.setBounds(180, 20, 200, 30);
        frame.add(addPatientButton);

        JButton addDoctorButton = new JButton("Add Doctor");
        addDoctorButton.setBounds(180, 60, 200, 30);
        frame.add(addDoctorButton);

        JButton listPatientsButton = new JButton("List All Patients");
        listPatientsButton.setBounds(180, 100, 200, 30);
        frame.add(listPatientsButton);

        JButton listDoctorsButton = new JButton("List All Doctors");
        listDoctorsButton.setBounds(180, 140, 200, 30);
        frame.add(listDoctorsButton);

        JButton bookAppointmentButton = new JButton("Book Appointment");
        bookAppointmentButton.setBounds(180, 180, 200, 30);
        frame.add(bookAppointmentButton);

        JButton checkPatientAppointmentsButton = new JButton("Check Patient Appointments");
        checkPatientAppointmentsButton.setBounds(180, 220, 200, 30);
        frame.add(checkPatientAppointmentsButton);

        JButton checkDoctorAppointmentsButton = new JButton("Check Doctor Appointments");
        checkDoctorAppointmentsButton.setBounds(180, 260, 200, 30);
        frame.add(checkDoctorAppointmentsButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(180, 300, 200, 30);
        frame.add(exitButton);

        addPatientButton.addActionListener(e -> addPatient());
        addDoctorButton.addActionListener(e -> addDoctor());
        listPatientsButton.addActionListener(e -> listAllPatients());
        listDoctorsButton.addActionListener(e -> listAllDoctors());
        bookAppointmentButton.addActionListener(e -> bookAppointment());
        checkPatientAppointmentsButton.addActionListener(e -> checkPatientAppointmentsByDate());
        checkDoctorAppointmentsButton.addActionListener(e -> checkDoctorAppointmentsByDate());
        exitButton.addActionListener(e -> System.exit(0));

        frame.setVisible(true);
    }

    public void addPatient() {
        String name = JOptionPane.showInputDialog("Enter patient's name:");
        if (name == null || name.trim().isEmpty()) return;

        String ageStr = JOptionPane.showInputDialog("Enter patient's age:");
        int age;
        try {
            age = Integer.parseInt(ageStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid age. Please enter a number.");
            return;
        }

        String gender = JOptionPane.showInputDialog("Enter patient's gender:");
        if (gender == null || gender.trim().isEmpty()) return;

        String contact = JOptionPane.showInputDialog("Enter patient's contact:");
        if (contact == null || contact.trim().isEmpty()) return;

        // Check for duplicate patient
        if (isDuplicatePatient(name, age, gender, contact)) {
            JOptionPane.showMessageDialog(null, "Patient with same details already exists.");
            return;
        }

        Patient patient = new Patient(name, age, gender, contact);
        patients.add(patient);
        String fileName = "D:\\p\\" + patient.getName() + ".txt"; // Assuming patient name is unique
        patient.saveToFile(fileName);
        JOptionPane.showMessageDialog(null, "Patient added successfully!");
    }

    private boolean isDuplicatePatient(String name, int age, String gender, String contact) {
        for (Patient patient : patients) {
            if (patient.getName().equalsIgnoreCase(name) &&
                patient.getAge() == age &&
                patient.getGender().equalsIgnoreCase(gender) &&
                patient.getContact().equals(contact)) {
                return true;
            }
        }
        return false;
    }

    public void addDoctor() {
        String name = JOptionPane.showInputDialog("Enter doctor's name:");
        if (name == null || name.trim().isEmpty()) return;

        String specialization = JOptionPane.showInputDialog("Enter doctor's specialization:");
        if (specialization == null || specialization.trim().isEmpty()) return;

        String contact = JOptionPane.showInputDialog("Enter doctor's contact:");
        if (contact == null || contact.trim().isEmpty()) return;

        Doctor doctor = new Doctor(name, specialization, contact);
        doctors.add(doctor);
        String fileName = "D:\\d\\" + doctor.getName() + ".txt"; // Assuming doctor name is unique
        doctor.saveToFile(fileName);
        JOptionPane.showMessageDialog(null, "Doctor added successfully!");
    }

    public void listAllPatients() {
        StringBuilder sb = new StringBuilder();
        for (Patient patient : patients) {
            sb.append("Name: ").append(patient.getName()).append("\n");
            sb.append("Age: ").append(patient.getAge()).append("\n");
            sb.append("Gender: ").append(patient.getGender()).append("\n");
            sb.append("Contact: ").append(patient.getContact()).append("\n\n");
        }
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(null, scrollPane, "List All Patients", JOptionPane.INFORMATION_MESSAGE);
    }

    public void listAllDoctors() {
        StringBuilder sb = new StringBuilder();
        for (Doctor doctor : doctors) {
            sb.append("Name: ").append(doctor.getName()).append("\n");
            sb.append("Specialization: ").append(doctor.getSpecialization()).append("\n");
            sb.append("Contact: ").append(doctor.getContact()).append("\n\n");
        }
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(null, scrollPane, "List All Doctors", JOptionPane.INFORMATION_MESSAGE);
    }

    public void bookAppointment() {
        String patientName = JOptionPane.showInputDialog("Enter patient's name:");
        if (patientName == null || patientName.trim().isEmpty()) return;

        Patient patient = findPatient(patientName);
        if (patient == null) {
            JOptionPane.showMessageDialog(null, "Patient not found.");
            return;
        }

        String doctorName = JOptionPane.showInputDialog("Enter doctor's name:");
        if (doctorName == null || doctorName.trim().isEmpty()) return;

        Doctor doctor = findDoctor(doctorName);
        if (doctor == null) {
            JOptionPane.showMessageDialog(null, "Doctor not found.");
            return;
        }

        String dateTimeStr = JOptionPane.showInputDialog("Enter appointment date and time (yyyy-MM-dd HH:mm):");
        if (dateTimeStr == null || dateTimeStr.trim().isEmpty()) return;

        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(dateTimeStr, formatter);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "Invalid date and time format.");
            return;
        }

        if (doctor.hasOverlappingAppointment(dateTime)) {
            JOptionPane.showMessageDialog(null, "Doctor already has an appointment at this time.");
            return;
        }

        doctor.addAppointment(patientName, dateTime);
        Appointment appointment = new Appointment(doctor, patientName, dateTime);

        // Create or append to the appointment file for the doctor and date
        String dateStr = dateTime.format(compactFormatter);
        String appointmentFileName = String.format("D:\\appointments\\%s_%s.txt", doctor.getName(), dateStr);
        appointment.saveToFile(appointmentFileName);

        JOptionPane.showMessageDialog(null, "Appointment booked successfully!");
    }

    private Patient findPatient(String name) {
        for (Patient patient : patients) {
            if (patient.getName().equalsIgnoreCase(name)) {
                return patient;
            }
        }
        return null;
    }

    private Doctor findDoctor(String name) {
        for (Doctor doctor : doctors) {
            if (doctor.getName().equalsIgnoreCase(name)) {
                return doctor;
            }
        }
        return null;
    }

   // Method to check patient appointments with specific date input
public void checkPatientAppointmentsByDate() {
    String patientName = JOptionPane.showInputDialog("Enter patient's name:");
    if (patientName == null || patientName.trim().isEmpty()) return;

    String dateStr = JOptionPane.showInputDialog("Enter date (yyyy-MM-dd):");
    LocalDate date;
    try {
        date = LocalDate.parse(dateStr);
    } catch (DateTimeParseException e) {
        JOptionPane.showMessageDialog(null, "Invalid date format.");
        return;
    }

    StringBuilder sb = new StringBuilder();
    for (Doctor doctor : doctors) {
        List<Appointment> appointments = doctor.getAppointmentsForDate(date.atStartOfDay());
        for (Appointment appointment : appointments) {
            if (appointment.getPatientName().equalsIgnoreCase(patientName)) {
                sb.append("Doctor: ").append(doctor.getName())
                  .append(", Date and Time: ").append(appointment.getDateTime().format(formatter)).append("\n");
            }
        }
    }
    JOptionPane.showMessageDialog(null, sb.length() > 0 ? sb.toString() : "No appointments found for this patient.");
}
// Method to check doctor appointments for a specific date
public void checkDoctorAppointmentsByDate() {
    String doctorName = JOptionPane.showInputDialog("Enter doctor's name:");
    if (doctorName == null || doctorName.trim().isEmpty()) return;

    Doctor doctor = findDoctor(doctorName);
    if (doctor == null) {
        JOptionPane.showMessageDialog(null, "Doctor not found.");
        return;
    }

    String dateStr = JOptionPane.showInputDialog("Enter date (yyyy-MM-dd):");
    LocalDate date;
    try {
        date = LocalDate.parse(dateStr);
    } catch (DateTimeParseException e) {
        JOptionPane.showMessageDialog(null, "Invalid date format.");
        return;
    }

    StringBuilder sb = new StringBuilder();
    List<Appointment> appointments = doctor.getAppointmentsForDate(date.atStartOfDay());
    for (Appointment appointment : appointments) {
        sb.append("Patient: ").append(appointment.getPatientName())
          .append(", Date and Time: ").append(appointment.getDateTime().format(formatter)).append("\n");
    }
    JOptionPane.showMessageDialog(null, sb.length() > 0 ? sb.toString() : "No appointments found for this doctor on the selected date.");
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HospitalRecordSystem());
    }
}
