package DersKayit;

import java.time.LocalTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Authentication authService = new Authentication();
        
        // Katalog, program açılınca 'courses.csv' dosyasını okur
        CourseCatalog courseCatalog = new CourseCatalog();

        System.out.println("=== ÖĞRENCİ DERS KAYIT SİSTEMİ ===");

        while (true) {
            System.out.print("\nKullanıcı Adı: ");
            String user = scanner.nextLine();
            System.out.print("Şifre: ");
            String pass = scanner.nextLine();

            String[] userData = authService.login(user, pass);

            if (userData != null) {
                String role = userData[2]; // student veya instructor
                String name = userData[3];
                System.out.println(">> Giriş Başarılı! Hoşgeldin " + name);

              
                switch (role.toLowerCase()) {
                    case "student":
                        int grade = Integer.parseInt(userData[4]);
                        String type = userData[5];
                        
                        Student activeStudent;
                        if (type.equalsIgnoreCase("grad")) {
                            activeStudent = new GraduateStudent(user, name, grade, "Tez Konusu");
                        } else {
                            activeStudent = new Student(user, name, grade);
                        }
                        // Öğrenci Menüsüne Git
                        showStudentMenu(scanner, courseCatalog, activeStudent);
                        break;

                    case "instructor":
                        String dept = userData[5];
                        Instructor activeInstructor = new Instructor(name, dept);
                        
                        // Hoca girdiği an, katalogdaki dersleri kendine çeker (Senkronizasyon)
                        activeInstructor.syncCoursesFromCatalog(courseCatalog);
                        
                       // eklenecek showInstructorMenu(scanner, courseCatalog, activeInstructor);
                        break;

                    default:
                        System.out.println("Hata: Tanımsız kullanıcı rolü!");
                        break;
                }
            } else {
                System.out.println("Hatalı kullanıcı adı veya şifre! Tekrar deneyin.");
            }
        }
    }

    // --- 2. SWITCH CASE: ÖĞRENCİ MENÜ SEÇİMLERİ ---
    public static void showStudentMenu(Scanner scanner, CourseCatalog cm, Student student) {
        boolean sessionActive = true;

        while (sessionActive) {
            System.out.println("\n--- ÖĞRENCİ PANELİ: " + student.getName() + " ---");
            System.out.println("1. Dersleri Listele");
            System.out.println("2. Derse Kayıt Ol");
            System.out.println("3. Ders Bırak");
            System.out.println("4. Ders Programım");
            System.out.println("5. Çıkış Yap");
            
            System.out.print("Seçiminiz: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    cm.listCourses();
                    break;

                case "2":
                    System.out.print("Kayıt olunacak Ders Kodu: ");
                    String code = scanner.nextLine();
                    Course c = cm.findCourseByCode(code);
                    
                    if (c != null) {
                        Registration reg = new Registration(student, c);
                        boolean success = reg.completeRegistration();
                        if(success) System.out.println(">> İşlem Başarılı.");
                    } else {
                        System.out.println(">> Hata: Ders bulunamadı.");
                    }
                    break;

                case "3":
                    System.out.print("Bırakılacak Ders Kodu: ");
                    String dropCode = scanner.nextLine();
                    Course toDrop = null;
                    
                    // Öğrencinin aldığı dersler içinde ara
                    for(Course enrolled : student.getEnrolledCourses()) {
                        if(enrolled.getCode().equalsIgnoreCase(dropCode)) toDrop = enrolled;
                    }
                    
                    if(toDrop != null) student.dropCourse(toDrop);
                    else System.out.println(">> Listenizde bu kodla bir ders yok.");
                    break;

                case "4":
                    if(student.getEnrolledCourses().isEmpty()) {
                        System.out.println(">> Henüz hiç dersiniz yok.");
                    } else {
                        System.out.println("\n--- ALDIĞINIZ DERSLER ---");
                        for (Course enrolled : student.getEnrolledCourses()) 
                            System.out.println(enrolled);
                    }
                    break;

                case "5":
                    System.out.println("Oturum kapatılıyor...");
                    sessionActive = false; // Döngüden çıkar, ana ekrana döner
                    break;

                default:
                    System.out.println("Geçersiz seçim! Lütfen 1-5 arası bir sayı girin.");
                    break;
            }
        }
    }
}