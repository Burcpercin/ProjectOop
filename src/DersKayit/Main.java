package DersKayit;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        Authentication authService = new Authentication();
        CourseCatalog courseCatalog = new CourseCatalog();
        RegistrationManager regManager = new RegistrationManager();
        GradeManager gradeManager = new GradeManager(); 

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

                        // Verileri Yükle
                        List<Student> tempStudentList = new ArrayList<>();
                        tempStudentList.add(activeStudent);
                        regManager.loadRegistrations(tempStudentList, courseCatalog);
                        gradeManager.loadGrades(tempStudentList);

                        showStudentMenu(scanner, courseCatalog, activeStudent, regManager);
                        break;

                    case "instructor":
                        String dept = userData[5];
                        Instructor activeInstructor = new Instructor(name, dept);
                        activeInstructor.syncCoursesFromCatalog(courseCatalog);
                        
                        showInstructorMenu(scanner, courseCatalog, activeInstructor, gradeManager, regManager);
                        break;
                    
                    case "admin":
                        showAdminMenu(scanner, authService, name);
                        break;


                    default:
                        System.out.println("Hata: Tanımsız rol!");
                        break;
                }
            } else {
                System.out.println("Hatalı kullanıcı adı veya şifre!");
            }
        }
    }

    // Admin Menü
    public static void showAdminMenu(Scanner scanner, Authentication auth, String adminName) {
        boolean sessionActive = true;
        while (sessionActive) {
            System.out.println("\n--- YÖNETİCİ PANELİ: " + adminName + " ---");
            System.out.println("1. Yeni Öğrenci Ekle");
            System.out.println("2. Yeni Öğretim Görevlisi Ekle");
            System.out.println("3. Yeni Admin Ekle");
            System.out.println("4. Tüm Kullanıcıları Listele");
            System.out.println("5. Çıkış");
            
            System.out.print("Seçim: ");
            String choice = scanner.nextLine();

            String uName, pass, name;

            switch (choice) {
                case "1": // Student EKLE
                    System.out.println("\n--- Yeni Öğrenci Kaydı ---");
                    System.out.print("Kullanıcı Adı (Öğrenci No): "); uName = scanner.nextLine();
                    if(auth.isUserExists(uName)) { System.out.println(">> Bu kullanıcı zaten var!"); break; }

                    System.out.print("Şifre: "); pass = scanner.nextLine();
                    System.out.print("Ad Soyad: "); name = scanner.nextLine();
                    System.out.print("Sınıf Seviyesi (1-4): "); String gradeLvl = scanner.nextLine();
                    System.out.print("Tip (undergrad/grad): "); String stdType = scanner.nextLine();

                    if(auth.registerUser(uName, pass, "student", name, gradeLvl, stdType)) {
                        System.out.println(">> Öğrenci başarıyla eklendi.");
                    }
                    break;

                case "2": // Instructor EKLE
                    System.out.println("\n--- Yeni Hoca Kaydı ---");
                    System.out.print("Kullanıcı Adı: "); uName = scanner.nextLine();
                    if(auth.isUserExists(uName)) { System.out.println(">> Bu kullanıcı zaten var!"); break; }

                    System.out.print("Şifre: "); pass = scanner.nextLine();
                    System.out.print("Ad Soyad: "); name = scanner.nextLine();
                    System.out.print("Departman: "); String dept = scanner.nextLine();
                    
                    // Hoca için 6. sütuna boş değer yerine "-" atıyoruz
                    if(auth.registerUser(uName, pass, "instructor", name, "-", dept)) {
                        System.out.println(">> Hoca başarıyla eklendi.");
                    }
                    break;
                
                case "3": // ADMIN EKLE
                    System.out.println("\n--- Yeni Admin Kaydı ---");
                    System.out.print("Kullanıcı Adı: "); uName = scanner.nextLine();
                    if(auth.isUserExists(uName)) { System.out.println(">> Bu kullanıcı zaten var!"); break; }
                    
                    System.out.print("Şifre: "); pass = scanner.nextLine();
                    System.out.print("Ad Soyad: "); name = scanner.nextLine();
                    
                    // Admin için son iki sütuna "-" atıyoruz
                    if(auth.registerUser(uName, pass, "admin", name, "-", "-")) {
                        System.out.println(">> Admin başarıyla eklendi.");
                    }
                    break;

                case "4": // LİSTELE
                    auth.listAllUsers();
                    break;

                case "5": sessionActive = false; break;
                default: System.out.println("Geçersiz seçim.");
            }
        }
    }

    // Student Menüsü 
    public static void showStudentMenu(Scanner scanner, CourseCatalog cm, Student student, RegistrationManager rm) {
        boolean sessionActive = true;
        while (sessionActive) {
            System.out.println("\n--- ÖĞRENCİ PANELİ: " + student.getName() + " ---");
            System.out.println("Toplam Kredi: " + student.calculateTotalCredits()); 
            System.out.printf("Güncel GPA: %.2f\n", student.calculateGPA()); 
            
            System.out.println("1. Dersleri Listele");
            System.out.println("2. Derse Kayıt Ol");
            System.out.println("3. Ders Bırak");
            System.out.println("4. Transkript");
            System.out.println("5. Çıkış Yap");
            
            System.out.print("Seçiminiz: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1": cm.listCourses(); break;
                case "2":
                    System.out.print("Ders Kodu: ");
                    String code = scanner.nextLine();
                    Course c = cm.findCourseByCode(code);
                    if (c != null) {
                        Registration reg = new Registration(student, c);
                        boolean success = reg.completeRegistration();
                        if(success) {
                            rm.saveRegistration(student, c);
                            System.out.println(">> Kayıt dosyasına işlendi.");
                        }
                    } else System.out.println(">> Ders bulunamadı.");
                    break;
                case "3":
                    System.out.print("Bırakılacak Ders Kodu: ");
                    String dropCode = scanner.nextLine();
                    Course toDrop = null;
                    for(Course enrolled : student.getEnrolledCourses()) {
                        if(enrolled.getCode().equalsIgnoreCase(dropCode)) toDrop = enrolled;
                    }
                    if(toDrop != null) {
                        student.dropCourse(toDrop);
                        rm.removeRegistration(student, toDrop);
                    } else System.out.println(">> Ders bulunamadı.");
                    break;
                case "4":
                    if(student.getEnrolledCourses().isEmpty()) System.out.println(">> Dersiniz yok.");
                    else {
                        System.out.println("\n--- DERSLERİM VE NOTLARIM ---");
                        for (Course enrolled : student.getEnrolledCourses()) {
                            System.out.println(enrolled.getCode() + " - " + enrolled.getName());
                            System.out.println("   Transkript: " + student.getGradeDetails(enrolled.getCode()));
                        }
                    }
                    break;
                case "5": sessionActive = false; break;
                default: System.out.println("Geçersiz seçim."); break;
            }
        }
    }

    // Instuctor Menüsü
    public static void showInstructorMenu(Scanner scanner, CourseCatalog cm, Instructor instructor, GradeManager gm, RegistrationManager rm) {
        boolean sessionActive = true;
        while (sessionActive) {
            System.out.println("\n--- ÖĞRETİM GÖREVLİSİ PANELİ: " + instructor.getName() + " ---");
            System.out.println("1. Yeni Ders Aç");
            System.out.println("2. Verdiğim Dersler");
            System.out.println("3. Not Girişi Yap");
            System.out.println("4. Çıkış");
            System.out.print("Seçim: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    try {
                        System.out.print("Kod: "); String code = scanner.nextLine();
                        System.out.print("Ad: "); String name = scanner.nextLine();
                        System.out.print("Gün: "); String day = scanner.nextLine();
                        System.out.print("Başla (SS:DD): "); LocalTime start = LocalTime.parse(scanner.nextLine());
                        System.out.print("Bitir (SS:DD): "); LocalTime end = LocalTime.parse(scanner.nextLine());
                        System.out.print("Kontenjan: "); int cap = Integer.parseInt(scanner.nextLine());
                        System.out.print("Min Sınıf: "); int grade = Integer.parseInt(scanner.nextLine());
                        System.out.print("Kredi (ECTS): "); int credit = Integer.parseInt(scanner.nextLine());

                        Course newCourse = new Course(code, name, instructor.getName(), day, start, end, cap, grade, credit);
                        instructor.addCourseToTeach(newCourse);
                        cm.addCourse(newCourse);
                        System.out.println(">> Ders başarıyla oluşturuldu.");
                    } catch (Exception e) {
                        System.out.println("Hata: " + e.getMessage());
                    }
                    break;
                case "2":
                    if(instructor.getGivenCourses().isEmpty()) System.out.println("Verdiğiniz ders yok.");
                    else for(Course c : instructor.getGivenCourses()) System.out.println(c);
                    break;
                    
                case "3":
                    System.out.print("Not girilecek ders kodu: ");
                    String code = scanner.nextLine();
                    
                    boolean isMyCourse = false;
                    for(Course c : instructor.getGivenCourses()) {
                        if(c.getCode().equalsIgnoreCase(code)) isMyCourse = true;
                    }
                    
                    if(!isMyCourse) {
                        System.out.println("Hata: Bu dersi siz vermiyorsunuz veya ders bulunamadı.");
                        break;
                    }

                    rm.printStudentsInCourse(code);

                    System.out.print("Öğrenci Numarası (ID): ");
                    String stdId = scanner.nextLine();
                    
                    try {
                        System.out.print("Vize Notu: ");
                        int vize = Integer.parseInt(scanner.nextLine());
                        System.out.print("Final Notu: ");
                        int fin = Integer.parseInt(scanner.nextLine());
    
                        gm.saveOrUpdateGrade(stdId, code, vize, fin);
                        System.out.println(">> Not başarıyla kaydedildi.");
                    } catch (NumberFormatException e) {
                        System.out.println("Hata: Notlar sayısal olmalıdır!");
                    }
                    break;
                    
                case "4": sessionActive = false; break;
                default: System.out.println("Geçersiz işlem.");
            }
        }
    }
}