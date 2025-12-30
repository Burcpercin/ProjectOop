package DersKayit;

import java.io.*;
import java.util.List;

public class RegistrationManager {
    private final String FILE_NAME = "registrations.csv";

    // Öğrenci derse kaydolunca dosyaya ekle
    public void saveRegistration(Student student, Course course) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            // Format: KullanıcıAdı,DersKodu
            bw.write(student.getStudentNumber() + "," + course.getCode());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Kayıt dosyasına yazılamadı: " + e.getMessage());
        }
    }

    // Ders bırakılınca dosyadan sil
    public void removeRegistration(Student student, Course course) {
        File inputFile = new File(FILE_NAME);
        File tempFile = new File("registrations_temp.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String lineToRemove = student.getStudentNumber() + "," + course.getCode();
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.trim().equals(lineToRemove)) continue; // Silinecek satırı atla
                writer.write(currentLine);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Silme işleminde hata: " + e.getMessage());
        }

        // Eski dosyayı sil, yenisinin adını değiştir
        if (inputFile.delete()) {
            tempFile.renameTo(inputFile);
        }
    }

    // Program açılışında kayıtları geri yükle
    public void loadRegistrations(List<Student> allStudents, CourseCatalog catalog) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",");
                if (data.length < 2) continue;

                String studentUser = data[0];
                String courseCode = data[1];

                // Öğrenciyi ve Dersi bul
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
                }
            }
        } catch (IOException e) {
            // Dosya yoksa sorun yok, ilk kez çalışıyordur.
        }
    }
}