package DersKayit;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
            System.out.println("Kullanıcı dosyası okunamadı!");
        }
        return null;
    }
}