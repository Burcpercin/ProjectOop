package DersKayit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;

class RegistrationTest {

    private Student computerStudent;
    private Student psychologyStudent;
    private Course compCourse;
    private Course psychCourse;
    private Course commonCourse;

    @BeforeEach
    void setUp() {
        // Test verilerini sıfırla
        computerStudent = new Student("100", "Ali", 1, "Bilgisayar");
        psychologyStudent = new Student("101", "Ayşe", 1, "Psikoloji");

        // Bilgisayar Bölümü Dersi
        compCourse = new Course("CS101", "Java", "Hoca A", "Mon", 
                                LocalTime.of(10,0), LocalTime.of(12,0), 30, 1, 5, "Bilgisayar");
        
        // Psikoloji Bölümü Dersi
        psychCourse = new Course("PSY101", "Giriş", "Hoca B", "Tue", 
                                 LocalTime.of(10,0), LocalTime.of(12,0), 30, 1, 5, "Psikoloji");
        
        // Ortak Ders
        commonCourse = new Course("HIST101", "Tarih", "Hoca C", "Wed", 
                                  LocalTime.of(14,0), LocalTime.of(16,0), 30, 1, 3, "Ortak");
    }

    // 1. Test: Bölüm Kontrolü (Başarılı ve Başarısız)
    @Test
    void testDepartmentMismatch() {
        // Senaryo 1: Bilgisayar öğrencisi Bilgisayar dersini alabilmeli
        Registration reg1 = new Registration(computerStudent, compCourse);
        assertTrue(reg1.completeRegistration(), "Kendi bölümünün dersini alamadı!");

        // Senaryo 2: Bilgisayar öğrencisi Psikoloji dersini slmsmslı
        Registration reg2 = new Registration(computerStudent, psychCourse);
        assertFalse(reg2.completeRegistration(), "Bölüm uyuşmazlığına rağmen kayıt yaptı!");

        // Senaryo 3: Herkes "Ortak" dersi alabilmeli
        Registration reg3 = new Registration(computerStudent, commonCourse);
        assertTrue(reg3.completeRegistration(), "Ortak dersi alamadı!");
    }

    // 2. Test: Kontenjan Doluysa
    @Test
    void testCapacityFull() {
        // Kapasitesi 1 olan mini bir ders yapalım
        Course miniCourse = new Course("TEST", "Test", "Hoca", "Fri", LocalTime.NOON, LocalTime.NOON.plusHours(1), 1, 1, 3, "Bilgisayar");
        
        // 1 kişi ekleyip dolduralım
        miniCourse.incrementEnrollment(); 
        
        // Şimdi Ali kayıt olmaya çalışsın
        Registration reg = new Registration(computerStudent, miniCourse);
        assertFalse(reg.completeRegistration(), "Dolu derse kayıt yaptı!");
    }

    // 3. Test: Sınıf Seviyesi (Grade Level) Yetersizse
    @Test
    void testGradeLevelInsufficient() {
        // 4. Sınıf bir ders oluşturalım
        Course seniorCourse = new Course("CS400", "Bitirme Projesi", "Hoca", "Fri", LocalTime.NOON, LocalTime.NOON.plusHours(2), 30, 4, 10, "Bilgisayar");
        
        // Ali henüz 1. sınıf
        Registration reg = new Registration(computerStudent, seniorCourse);
        
        assertFalse(reg.completeRegistration(), "1. sınıf öğrencisi 4. sınıf dersini alamamalı!");
    }
}