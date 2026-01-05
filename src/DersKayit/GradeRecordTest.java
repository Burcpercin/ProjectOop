package DersKayit;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GradeRecordTest {

    // 1. Senaryo: Geçerli notlarla ortalama ve harf notu hesaplama
    @Test
    void testValidGradeCalculation() {
        // Vize: 50, Final: 60
        // Ortalama = (50 * 0.4) + (60 * 0.6) = 20 + 36 = 56.0
        GradeRecord record = new GradeRecord(50, 60);

        assertEquals(56.0, record.calculateAverage(), "Ortalama yanlış hesaplandı!");
        assertEquals("FD", record.getLetterGrade(), "Harf notu yanlış!"); // 50-60 arası FD idi
    }

    // 2. Senaryo: Yüksek not (AA) testi
    @Test
    void testHighGrade() {
        GradeRecord record = new GradeRecord(100, 90); 
        // Ort: 40 + 54 = 94 -> AA
        
        assertEquals("AA", record.getLetterGrade());
        assertEquals(4.0, record.getCoefficient());
    }

    // 3. Senaryo: Kısıtlama Testi (0-100 Kuralı)
    // Bu test, yanlış not girildiğinde sistemin hata verip vermediğini kontrol eder.
    @Test
    void testInvalidGradeThrowsException() {
        // 101 notu girilirse "IllegalArgumentException" fırlatmalı
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new GradeRecord(101, 50);
        });

        assertEquals("Notlar 0 ile 100 arasında olmalıdır!", exception.getMessage());

        // Negatif sayı testi
        assertThrows(IllegalArgumentException.class, () -> {
            new GradeRecord(50, -5);
        });
    }
}