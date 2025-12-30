package DersKayit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Student implements Registrable {
    private String studentNumber; 
    private String name;
    private int gradeLevel;
    private List<Course> enrolledCourses;

    public Student(String studentNumber, String name, int gradeLevel) {
        this.studentNumber = studentNumber;
        this.name = name;
        this.gradeLevel = gradeLevel;
        this.enrolledCourses = new ArrayList<>();
    }

    public double calculateTuition() { return 5000.0; }

    // GPA Hesaplama taslağı (ders notu girme henüz yok).
    public int calculateTotalCredits() {
        int total = 0;
        for(Course c : enrolledCourses) {
            total += c.getCredit();
        }
        return total;
    }

    @Override
    public void registerForCourse(Course course) {
        if (!enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
            System.out.println(">> " + course.getCode() + " dersi profiline eklendi.");
        }
    }

    //  Program açılışında kayıtlı dersleri csv dosyasından çekmek için
    public void loadEnrolledCourse(Course course) {
        if (!enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
        }
    }

    public void dropCourse(Course course) {
        if (enrolledCourses.contains(course)) {
            enrolledCourses.remove(course);
            System.out.println(">> " + course.getCode() + " dersi bırakıldı.");
        } else {
            System.out.println(">> Hata: Bu dersi zaten almıyorsunuz.");
        }
    }

    public List<Course> getEnrolledCourses() {
        return Collections.unmodifiableList(enrolledCourses);
    }
    
    public String getName() { return name; }
    public int getGradeLevel() { return gradeLevel; }
    public String getStudentNumber() { return studentNumber; }
}