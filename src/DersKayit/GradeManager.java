package DersKayit;

import java.io.*;
import java.util.List;

public class GradeManager {
    private final String FILE_NAME = "grades.csv";

    // Notu kaydeder veya günceller
    public void saveOrUpdateGrade(String studentId, String courseCode, int midterm, int finalExam) {
        
        // 1. GÜVENLİK KONTROLÜ: 0-100 Aralığı
        // Eğer notlar bu aralıkta değilse, hata fırlat ve işlemi durdur.
        if (midterm < 0 || midterm > 100 || finalExam < 0 || finalExam > 100) {
            throw new IllegalArgumentException("Notlar 0 ile 100 arasında olmalıdır!");
        }

        File inputFile = new File(FILE_NAME);
        File tempFile = new File("grades_temp.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String targetKey = studentId + "," + courseCode; // Aradığımız satır başı
            String currentLine;
            
            // Dosyayı satır satır oku
            while ((currentLine = reader.readLine()) != null) {
                // Eğer bu satır güncelleyeceğimiz not ise, onu atla (yazma)
                if (currentLine.startsWith(targetKey)) continue; 
                writer.write(currentLine);
                writer.newLine();
            }
            
            // Yeni notu dosyanın en sonuna ekle
            String newEntry = studentId + "," + courseCode + "," + midterm + "," + finalExam;
            writer.write(newEntry);
            writer.newLine();

        } catch (FileNotFoundException e) {
            // Dosya yoksa ilk kez oluşturuluyordur, direkt yazalım.
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
                String newEntry = studentId + "," + courseCode + "," + midterm + "," + finalExam;
                bw.write(newEntry);
                bw.newLine();
                return; // Temp dosyayla işimiz yok
            } catch (IOException io) {
                System.out.println("Dosya oluşturma hatası: " + io.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Dosya güncelleme hatası: " + e.getMessage());
        }

        // Temp dosyayı asıl dosya yap (Eski dosyayı sil, yenisinin adını değiştir)
        if (inputFile.exists()) inputFile.delete();
        tempFile.renameTo(inputFile);
    }

    // Program açılınca notları yükler
    public void loadGrades(List<Student> students) {
        File file = new File(FILE_NAME);
        if (!file.exists()) return; // Dosya yoksa işlem yapma

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",");
                if (data.length < 4) continue;

                // trim() ile boşlukları temizliyoruz (Örn: " CS101 " -> "CS101")
                String sId = data[0].trim();
                String cCode = data[1].trim();
                
                try {
                    int mid = Integer.parseInt(data[2].trim());
                    int fin = Integer.parseInt(data[3].trim());

                    // İlgili öğrenciyi bul
                    for (Student s : students) {
                        if (s.getStudentNumber().equals(sId)) {
                            try {
                                // Notu ekle
                                s.addGrade(cCode, mid, fin);
                            } catch (IllegalArgumentException e) {
                                // Eğer öğrenci dersi almıyorsa buraya düşer, program çökmez ama uyarır.
                                System.out.println("UYARI: " + s.getName() + " (" + cCode + ") dersini almadığı için not yüklenemedi.");
                            }
                            break;
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("HATA: grades.csv içinde hatalı sayı formatı: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Dosya okuma hatası: " + e.getMessage());
        }
    }
}