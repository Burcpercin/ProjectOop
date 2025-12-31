package DersKayit;

import java.io.*;
import java.util.List;

public class GradeManager {
    private final String FILE_NAME = "grades.csv";

    // Notu kaydeder veya günceller
    public void saveOrUpdateGrade(String studentId, String courseCode, int midterm, int finalExam) {
        // Önce eski kaydı (varsa) dosyadan temizlememiz lazım ki çift kayıt olmasın.
        File inputFile = new File(FILE_NAME);
        File tempFile = new File("grades_temp.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String targetKey = studentId + "," + courseCode; // Aradığımız satır başı

            // Dosyayı satır satır oku
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                // Eğer bu satır, bizim güncellemek istediğimiz dersin notuysa, onu yazma (atla)
                if (currentLine.startsWith(targetKey)) continue; 
                
                writer.write(currentLine);
                writer.newLine();
            }
            
            // Şimdi güncel notu en sona ekle
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

        // Temp dosyayı asıl dosya yap
        if (inputFile.exists()) inputFile.delete();
        tempFile.renameTo(inputFile);
    }

    // Program açılınca notları yükler
    public void loadGrades(List<Student> students) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",");
                if (data.length < 4) continue;

                String sId = data[0];
                String cCode = data[1];
                int mid = Integer.parseInt(data[2]);
                int fin = Integer.parseInt(data[3]);

                // İlgili öğrenciyi bul ve notunu ekle
                for (Student s : students) {
                    if (s.getStudentNumber().equals(sId)) {
                        s.addGrade(cCode, mid, fin);
                        break;
                    }
                }
            }
        } catch (IOException e) {
            // Dosya henüz yoksa sorun yok
        }
    }
}