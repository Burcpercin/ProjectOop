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
                        
                        // Hoca Menüsüne Git
                        showInstructorMenu(scanner, courseCatalog, activeInstructor);
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

    public static void showInstructorMenu(Scanner scanner, CourseCatalog cm, Instructor instructor) {
        boolean sessionActive = true;

        while (sessionActive) {
            System.out.println("\n--- HOCA PANELİ: " + instructor.getName() + " ---");
            System.out.println("1. Yeni Ders Aç");
            System.out.println("2. Verdiğim Dersler");
            System.out.println("3. Çıkış Yap");

            System.out.print("Seçiminiz: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    try {
                        System.out.print("Ders Kodu: "); String code = scanner.nextLine();
                        System.out.print("Ders Adı: "); String name = scanner.nextLine();
                        System.out.print("Gün (Pazartesi vb.): "); String day = scanner.nextLine();
                        System.out.print("Başlangıç (SS:DD): "); LocalTime start = LocalTime.parse(scanner.nextLine());
                        System.out.print("Bitiş (SS:DD): "); LocalTime end = LocalTime.parse(scanner.nextLine());
                        System.out.print("Kontenjan: "); int cap = Integer.parseInt(scanner.nextLine());
                        System.out.print("Min Sınıf (1-4): "); int grade = Integer.parseInt(scanner.nextLine());

                        Course newCourse = new Course(code, name, instructor.getName(), day, start, end, cap, grade);
                        
                        // Hem hocanın listesine, hem kataloğa (CSV'ye) ekle
                        instructor.addCourseToTeach(newCourse);
                        cm.addCourse(newCourse);
                        System.out.println(">> Ders başarıyla oluşturuldu ve kaydedildi.");
                        
                    } catch (Exception e) {
                        System.out.println(">> Hata! Veri girişi yanlış (Saat formatı 09:30 gibi olmalı).");
                    }
                    break;

                case "2":
                    if (instructor.getGivenCourses().isEmpty()) {
                        System.out.println(">> Henüz verdiğiniz bir ders yok.");
                    } else {
                        System.out.println("\n--- VERDİĞİNİZ DERSLER ---");
                        for (Course c : instructor.getGivenCourses()) 
                            System.out.println(c);
                    }
                    break;

                case "3":
                    System.out.println("Oturum kapatılıyor...");
                    sessionActive = false;
                    break;

                default:
                    System.out.println("Geçersiz seçim! Lütfen 1-3 arası bir sayı girin.");
                    break;
            }
        }
    }
}