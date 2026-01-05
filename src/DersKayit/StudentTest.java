package DersKayit;

import org.junit.jupiter.api.Test;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void testGpaCalculation() {
        Student student = new Student("123", "Mehmet", 1, "Bilgisayar");
        
        // 5 Kredilik bir ders oluştur
        Course course1 = new Course("MAT101", "Math", "Hoca", "Mon", LocalTime.NOON, LocalTime.NOON.plusHours(2), 30, 1, 5, "Ortak");
        
        // Öğrenciye dersi ver (Not girmek için dersi alıyor olması şart.)
        student.loadEnrolledCourse(course1);
        
        // Not Gir: 100, 100 -> AA (4.0)
        student.addGrade("MAT101", 100, 100);
        
        // Tek ders olduğu için GPA direkt 4.0 olmalı
        assertEquals(4.0, student.calculateGPA());
    }

    @Test
    void testAddGradeForNonEnrolledCourse() {
        Student student = new Student("124", "Zeynep", 1, "Mimarlık");
        
        // Öğrencinin hiç dersi yok ama "FIZ101" kodlu derse not girmeye çalışıyoruz.
        // Sistem hata vermeli.
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            student.addGrade("FIZ101", 50, 50);
        });

        assertTrue(exception.getMessage().contains("bu dersi almıyor"));
    }
}