package DersKayit;

import java.io.*;

public class Authentication {
    private String csvFile = "users.csv";

    public String[] login(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",");
                if (data.length < 2) continue;

                if (data[0].equals(username) && data[1].equals(password)) {
                    return data;
                }
            }
        } catch (IOException e) {
            System.out.println("Kullanıcı dosyası okunamadı veya yok!");
        }
        return null;
    }

    public boolean isUserExists(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",");
                if (data.length > 0 && data[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
        }
        return false;
    }

    public boolean registerUser(String username, String password, String role, String name, String extra1, String extra2, String extra3) {
        if (isUserExists(username)) {
            System.out.println("Hata: Bu kullanıcı adı zaten kullanılıyor!");
            return false;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile, true))) {
            String line = String.format("%s,%s,%s,%s,%s,%s,%s", 
                    username, password, role, name, 
                    (extra1 == null || extra1.isEmpty() ? "-" : extra1), 
                    (extra2 == null || extra2.isEmpty() ? "-" : extra2),
                    (extra3 == null || extra3.isEmpty() ? "-" : extra3));
            
            bw.write(line);
            bw.newLine();
            return true;
        } catch (IOException e) {
            System.out.println("Kayıt hatası: " + e.getMessage());
            return false;
        }
    }

    public void listAllUsers() {
        System.out.println("\n--- SİSTEMDEKİ TÜM KULLANICILAR ---");
        // Tablo başlığına DEPARTMAN ekledik ve genişliği ayarladık
        System.out.printf("%-15s %-15s %-25s %-20s\n", "KULLANICI ADI", "ROL", "AD SOYAD", "DEPARTMAN");
        System.out.println("--------------------------------------------------------------------------------");

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",");
                if (data.length < 4) continue; 

                String role = data[2].toLowerCase();
                String dept = "-"; // Varsayılan olarak boş

                // Rolüne göre departmanın csv içindeki yeri değişiyor
                if (role.equals("student")) {
                    if (data.length > 6) dept = data[6]; 
                } else if (role.equals("instructor")) {
                    if (data.length > 5) dept = data[5]; 
                }

                System.out.printf("%-15s %-15s %-25s %-20s\n", data[0], data[2], data[3], dept);
            }
        } catch (IOException e) {
            System.out.println("Liste okunamadı.");
        }
        System.out.println("--------------------------------------------------------------------------------");
    }
}