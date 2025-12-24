package DersKayit;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
       Authentication authService = new Authentication();
        
        // --- 1. GİRİŞ EKRANI ---
        System.out.println("=== ÖĞRENCİ BİLGİ SİSTEMİNE HOŞGELDİNİZ ===");
        
        while (true) { //Ana döngü
            System.out.print("Kullanıcı Adı: ");
            String user = scanner.nextLine();
            
            System.out.print("Şifre: ");
            String pass = scanner.nextLine();
            
            // CSV kontrolü yapılıyor
            String[] userData = authService.login(user, pass);
            
            if (userData != null) {
                System.out.println("Giriş Başarılı! Hoşgeldin " + userData[3]);
                String role = userData[2]; // instructor veya student
                
                // Role göre menü aç
                if (role.equals("instructor")) {
                    showInstructorMenu(scanner);
                } else if (role.equals("student")) {
                    showStudentMenu(scanner);
                }
                break;
            } else {
                System.out.println("Hatalı kullanıcı adı veya şifre! Tekrar dene.\n");
            }
        }
    }

    // --- ÖĞRETMEN MENÜSÜ ---
    public static void showInstructorMenu(Scanner scanner) {
        System.out.println("\n--- ÖĞRETMEN PANELİ ---");
        System.out.println("1. Ders Ekle");
        System.out.println("2. Öğrenci Listele");
        System.out.println("3. Çıkış");
        // Buraya switch-case ile seçim işlemleri gelecek
        int secim = scanner.nextInt();
    }

    // --- ÖĞRENCİ MENÜSÜ ---
    public static void showStudentMenu(Scanner scanner) {
        System.out.println("\n--- ÖĞRENCİ PANELİ ---");
        System.out.println("1. Derslere Göz At");
        System.out.println("2. Derse Kayıt Ol");
        System.out.println("3. Ders Programımı Gör");
    }
}