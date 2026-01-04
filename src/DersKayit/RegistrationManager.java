package DersKayit;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RegistrationManager {
    private Course course;
    private final String FILE_NAME = "registrations.csv";
    private final String USERS_FILE = "users.csv"; // İsimleri çekmek için lazım

    // Öğrenci derse kaydolunca dosyaya ekle
    public void saveRegistration(Student student, Course course) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            bw.write(student.getStudentNumber() + "," + course.getCode());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Kayıt dosyasına yazılamadı: " + e.getMessage());
        }
    }

    // Ders bırakılınca dosyadan sil
    public void removeRegistration(Student student, Course course) {
    	// Öğrenci hiç ders alıyormu?
    	if(!(student.getEnrolledCourses().contains(this.course))) {
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
    else {
    	System.out.println("Zaten hiçbir derse kayıtlı değilsiniz.");
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
                    foundCourse.incrementEnrollment();
                }
            }
        } catch (IOException e) {
            // Dosya yoksa sorun yok.
        }
    }

    // Bir dersteki öğrencileri listele
    public void printStudentsInCourse(String courseCode) {
        List<String> studentIds = new ArrayList<>();

        // 1. Adım: registrations.csv dosyasından bu dersi alanların ID'lerini bul
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",");
                if (data.length < 2) continue;
                
                // Eğer satırdaki ders kodu aradığımız kodsa, öğrenci ID'sini listeye al
                if (data[1].equalsIgnoreCase(courseCode)) {
                    studentIds.add(data[0]);
                }
            }
        } catch (IOException e) {
            System.out.println("Kayıt dosyası okunamadı.");
            return;
        }

        if (studentIds.isEmpty()) {
            System.out.println(">> Bu derse kayıtlı öğrenci yok.");
            return;
        }

        System.out.println("\n--- Dersi Alan Öğrenciler (" + courseCode + ") ---");
        
        // 2. Adım: users.csv dosyasından bu ID'lerin isimlerini bul ve yazdır
        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                // data[0] = username (ID), data[3] = name
                if (data.length > 3 && studentIds.contains(data[0])) {
                    System.out.println("ID: " + data[0] + " | İsim: " + data[3]);
                }
            }
        } catch (IOException e) {
            System.out.println("Kullanıcı listesi okunamadı.");
        }
        System.out.println("-------------------------------------------");
    }
}