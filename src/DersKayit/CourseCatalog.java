package DersKayit;

import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CourseCatalog {
    private List<Course> allCourses;
    private final String FILE_NAME = "courses.csv";

    public CourseCatalog() {
        this.allCourses = new ArrayList<>();
        loadCoursesFromCSV();
    }

    public void addCourse(Course c) {
        allCourses.add(c);
        saveCourseToCSV(c); 
    }

    public List<Course> getAllCourses() { return allCourses; }

    public Course findCourseByCode(String code) {
        for (Course c : allCourses) {
            if (c.getCode().equalsIgnoreCase(code)) return c;
        }
        return null;
    }

    public void listCourses() {
        if (allCourses.isEmpty()) {
            System.out.println("Listelenecek ders yok.");
        } else {
            System.out.println("\n--- DERS KATALOĞU ---");
            for (Course c : allCourses) System.out.println(c);
        }
    }

    private void loadCoursesFromCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",");
                // Kredi eklendiği için artık en az 9 sütun olmalı
                if (data.length < 9) continue; 

                Course c = new Course(data[0], data[1], data[2], data[3], 
                        LocalTime.parse(data[4]), LocalTime.parse(data[5]), 
                        Integer.parseInt(data[6]), Integer.parseInt(data[7]),
                        Integer.parseInt(data[8])); // Kredi (Son sütun)
                allCourses.add(c);
            }
        } catch (IOException e) {
            System.out.println("Ders dosyası oluşturuluyor...");
        }
    }

    private void saveCourseToCSV(Course c) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            // Format: KOD,AD,HOCA,GÜN,BAŞLA,BİTİŞ,KAPASİTE,SINIF,KREDİ
            String line = String.format("%s,%s,%s,%s,%s,%s,%d,%d,%d",
                    c.getCode(), c.getName(), c.getInstructorName(), c.getDay(),
                    c.getStartTime(), c.getEndTime(), c.getCapacity(), c.getGradeLevel(),
                    c.getCredit()); // Kredi eklendi
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Dosya yazma hatası: " + e.getMessage());
        }
    }
}