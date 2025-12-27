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

    public double calculateTuition() {
        return 5000.0;
    }

    @Override
    public void registerForCourse(Course course) {
    // Ders kontrol ve ekleme
        if(enrolledCourses.contains(course)) {
            System.out.println("Zaten kayıtlısınız.");
            return;
        }
        this.enrolledCourses.add(course);
        System.out.println(course.getCourseCode() + " dersi eklendi.");
    }

    // Ders Çıkarma
    public void dropCourse(Course course) {
        if (enrolledCourses.contains(course)) {
            enrolledCourses.remove(course);
            System.out.println(course.getCourseCode() + " dersi bırakıldı.");
        } else {
            System.out.println("Bu dersi zaten almıyorsunuz.");
        }
    }

    public List<Course> getEnrolledCourses() {
        return Collections.unmodifiableList(enrolledCourses);
    }
    
    public String getName() { return name; }
    public int getGradeLevel() { return gradeLevel; }
    public String getStudentNumber() { return studentNumber; }
}