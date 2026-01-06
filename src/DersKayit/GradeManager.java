package DersKayit;

import java.io.*;
import java.util.List;

public class GradeManager {
    private final String FILE_NAME = "grades.csv";

    // Notu kaydeder veya günceller
    public void saveOrUpdateGrade(String studentId, String courseCode, int midterm, int finalExam) {
        
        // Eğer not 0'dan küçükse veya 100'den büyükse İŞLEMİ DURDUR.
        if (midterm < 0 || midterm > 100 || finalExam < 0 || finalExam > 100) {
            throw new IllegalArgumentException("Notlar 0 ile 100 arasında olmalıdır!");
        }
        
        File inputFile = new File(FILE_NAME);
        File tempFile = new File("grades_temp.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String targetKey = studentId + "," + courseCode;
            String currentLine;
            
            // Mevcut dosyayı satır satır oku
            while ((currentLine = reader.readLine()) != null) {
                // Eğer güncelleyeceğimiz dersin eski notu varsa, onu dosyaya yazma (atla)
                if (currentLine.startsWith(targetKey)) continue; 
                writer.write(currentLine);
                writer.newLine();
            }
            
            // Yeni ve doğru olan notu dosyanın en sonuna ekle
            String newEntry = studentId + "," + courseCode + "," + midterm + "," + finalExam;
            writer.write(newEntry);
            writer.newLine();

        } catch (FileNotFoundException e) {
            // Dosya hiç yoksa oluştur ve ilk kaydı yaz
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
                String newEntry = studentId + "," + courseCode + "," + midterm + "," + finalExam;
                bw.write(newEntry);
                bw.newLine();
                return; 
            } catch (IOException io) {
                System.out.println("Dosya oluşturma hatası: " + io.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Dosya güncelleme hatası: " + e.getMessage());
        }

        // Geçici dosyayı asıl dosya yap (Verileri güncelle)
        if (inputFile.exists()) inputFile.delete();
        tempFile.renameTo(inputFile);
    }

    // Program açılınca notları yükler (Hata kontrollü versiyon)
    public void loadGrades(List<Student> students) {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",");
                if (data.length < 4) continue;

                String sId = data[0].trim();
                String cCode = data[1].trim();
                
                try {
                    int mid = Integer.parseInt(data[2].trim());
                    int fin = Integer.parseInt(data[3].trim());

                    for (Student s : students) {
                        if (s.getStudentNumber().equals(sId)) {
                            try {
                                s.addGrade(cCode, mid, fin);
                            } catch (IllegalArgumentException e) {
                                System.out.println("UYARI: " + s.getName() + " (" + cCode + ") dersini almadığı için not yüklenemedi.");
                            }
                            break;
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("HATA: grades.csv içinde bozuk not verisi: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Dosya okuma hatası: " + e.getMessage());
        }
    }
}