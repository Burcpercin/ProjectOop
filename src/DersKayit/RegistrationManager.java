package DersKayit;

import java.io.*;
import java.util.List;

public class RegistrationManager {
    private final String FILE_NAME = "registrations.csv";

    public void saveRegistration(Student student, Course course) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            bw.write(student.getStudentNumber() + "," + course.getCode());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Kayıt dosyasına yazılamadı: " + e.getMessage());
        }
    }

    public void removeRegistration(Student student, Course course) {
        File inputFile = new File(FILE_NAME);
        File tempFile = new File("registrations_temp.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String lineToRemove = student.getStudentNumber() + "," + course.getCode();
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.trim().equals(lineToRemove)) continue;
                writer.write(currentLine);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Silme işleminde hata: " + e.getMessage());
        }

        if (inputFile.delete()) {
            tempFile.renameTo(inputFile);
        }
    }

    public void loadRegistrations(List<Student> allStudents, CourseCatalog catalog) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",");
                if (data.length < 2) continue;

                String studentUser = data[0];
                String courseCode = data[1];

                Student foundStudent = null;
                for (Student s : allStudents) {
                    if (s.getStudentNumber().equals(studentUser)) {
                        foundStudent = s;
                        break;
                    }
                }

                Course foundCourse = catalog.findCourseByCode(courseCode);

                if (foundStudent != null && foundCourse != null) {
                    foundStudent.loadEnrolledCourse(foundCourse);
                    
                    // Dosyadan eski kaydı yüklerken, dersin mevcut sayısını da artırıyoruz.
                    // Böylece program açıldığında kontenjan bilgisi doğru gelir.
                    foundCourse.incrementEnrollment();
                }
            }
        } catch (IOException e) {
            // Dosya yoksa sorun yok.
        }
    }
}