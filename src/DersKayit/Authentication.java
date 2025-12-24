package DersKayit;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Authentication {

	private String csvFile = "users.csv";

	    // Bu metod kullanıcı adı ve şifreyi alır, eşleşme varsa rolü ve adı döner
	    public String[] login(String username, String password) {
	        String line = "";
	        String splitBy = ",";

	        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
	            // Dosyanın sonuna gelene kadar satır satır okuma döngüsü.
	            while ((line = br.readLine()) != null) {
	                String[] data = line.split(splitBy);

	                // CSV'den gelen kullanıcı adı ve şifre, girilenle aynı mı?
	                if (data[0].equals(username) && data[1].equals(password)) {
	                    return data; // Kullanıcıyı bilgileri doğruysa tüm bilgileri döndürür.
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return null; // Kullanıcı verisi yoksa boş döner.
	    }
	}

