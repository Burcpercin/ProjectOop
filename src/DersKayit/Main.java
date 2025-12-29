package DersKayit;

import java.time.LocalTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Authentication authService = new Authentication();
        
        // Katalog başlarken 'courses.csv' dosyasını okur ve hazırlar
        CourseCatalog courseCatalog = new CourseCatalog();

        System.out.println("=== ÖĞRENCİ DERS KAYIT SİSTEMİ ===");

        while (true) {
            System.out.print("\nKullanıcı Adı: ");
            String user = scanner.nextLine();
            System.out.print("Şifre: ");
            String pass = scanner.nextLine();

            String[] userData = authService.login(user, pass);

            if (userData != null) {
                String role = userData[2];
                String name = userData[3];
                System.out.println("Giriş Başarılı! Hoşgeldin " + name);

                if (role.equals("student")) {
                    int grade = Integer.parseInt(userData[4]);
                    String type = userData[5];
                    
                    Student activeStudent;
                    if (type.equalsIgnoreCase("grad")) {
                        activeStudent = new GraduateStudent(user, name, grade, "Tez Konusu");
                    } else {
                        activeStudent = new Student(user, name, grade);
                    }
             // eklenecek       showStudentMenu(scanner, courseCatalog, activeStudent);

                }
            }
        }
    }
}