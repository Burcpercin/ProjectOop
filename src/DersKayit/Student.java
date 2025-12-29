package DersKayit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Student implements Registrable {
    private String studentNumber; 
    private String name;
    private int gradeLevel;
    
    // Encapsulation: Liste private, dışarıdan doğrudan erişilemez.
    private List<Course> enrolledCourses;

    public Student(String studentNumber, String name, int gradeLevel) {
        this.studentNumber = studentNumber;
        this.name = name;
        this.gradeLevel = gradeLevel;
        this.enrolledCourses = new ArrayList<>();
    }

    // Polimorfizm için metot
    public double calculateTuition() {
        return 5000.0;
    }

    @Override
    public void registerForCourse(Course course) {
        // Asıl kontroller Registration sınıfında yapılır, burası sadece listeye ekler.
        if (!enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
            System.out.println("Sistem Mesajı: " + course.getCode() + " dersi öğrenci profiline işlendi.");
        }
    }

    public void dropCourse(Course course) {
        if (enrolledCourses.contains(course)) {
            enrolledCourses.remove(course);
            System.out.println("Sistem Mesajı: " + course.getCode() + " dersi bırakıldı.");
        } else {
            System.out.println("Hata: Bu dersi zaten almıyorsunuz.");
        }
    }

    // Read-only liste döndürür (Güvenlik)
    public List<Course> getEnrolledCourses() {
        return Collections.unmodifiableList(enrolledCourses);
    }
    
    public String getName() { return name; }
    public int getGradeLevel() { return gradeLevel; }
    public String getStudentNumber() { return studentNumber; }
}