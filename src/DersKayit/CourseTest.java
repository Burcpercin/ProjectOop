package DersKayit;

import org.junit.jupiter.api.Test;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;

class CourseTest {

    @Test
    void testCourseConflict() {
        // Pazartesi 10:00 - 12:00 arası bir ders
        Course c1 = new Course("CS101", "Intro", "Ali Hoca", "Pazartesi", 
                               LocalTime.of(10, 0), LocalTime.of(12, 0), 30, 1, 5, "Bilgisayar");

        // Pazartesi 11:00 - 13:00 arası başka bir ders var (Çakışma olmalı)
        Course c2 = new Course("CS102", "Advanced", "Veli Hoca", "Pazartesi", 
                               LocalTime.of(11, 0), LocalTime.of(13, 0), 30, 1, 5, "Bilgisayar");

        // Salı günü aynı saatte ders (Gün farklı çakışmaz.)
        Course c3 = new Course("MATH101", "Math", "Ayşe Hoca", "Salı", 
                               LocalTime.of(10, 0), LocalTime.of(12, 0), 30, 1, 5, "Bilgisayar");

        // Testler
        assertTrue(c1.hasConflict(c2), "Pazartesi 10-12 ile 11-13 çakışmalıydı!");
        assertFalse(c1.hasConflict(c3), "Farklı günlerdeki dersler çakışmamalı!");
    }

    @Test
    void testCapacityLimit() {
        // Kapasitesi sadece 1 olan ders
        Course c = new Course("CS101", "Test", "Hoca", "Mon", LocalTime.NOON, LocalTime.NOON.plusHours(1), 1, 1, 5, "Genel");
        
        assertFalse(c.isFull()); // Başta boş
        
        c.incrementEnrollment(); // 1 kişi ekledik
        assertTrue(c.isFull());  // Şimdi dolu olmalı
    }
}