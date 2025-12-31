package DersKayit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student implements Registrable {
    private String studentNumber; 
    private String name;
    private int gradeLevel;
    private List<Course> enrolledCourses;
    
    // Notları tutan yapı (Ders Kodu -> Not Kaydı)
    private Map<String, GradeRecord> courseGrades;

    public Student(String studentNumber, String name, int gradeLevel) {
        this.studentNumber = studentNumber;
        this.name = name;
        this.gradeLevel = gradeLevel;
        this.enrolledCourses = new ArrayList<>();
        this.courseGrades = new HashMap<>(); // Map başlatılıyor
    }

    // Not Ekleme Metodu
    public void addGrade(String courseCode, int midterm, int finalExam) {
        // Not sadece alınan derslere girilebilir
        GradeRecord record = new GradeRecord(midterm, finalExam);
        courseGrades.put(courseCode, record);
    }

    // GPA Hesaplama
    public double calculateGPA() {
        double totalPoints = 0.0;
        double totalCredits = 0.0;

        for (Course course : enrolledCourses) {
            // Eğer bu dersin notu girilmişse
            if (courseGrades.containsKey(course.getCode())) {
                GradeRecord grade = courseGrades.get(course.getCode());
                double coefficient = grade.getCoefficient(); // 4.0, 3.5 vs.
                int credit = course.getCredit();
                
                totalPoints += (coefficient * credit);
                totalCredits += credit;
            }
        }

        if (totalCredits == 0) return 0.0;
        return totalPoints / totalCredits;
    }
    
    // Öğrenci menüsünde notu göstermek için yardımcı metot
    public String getGradeDetails(String courseCode) {
        if(courseGrades.containsKey(courseCode)) {
            return courseGrades.get(courseCode).toString();
        }
        return "Not Girilmedi";
    }

    public double calculateTuition() { return 5000.0; }

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

    public void loadEnrolledCourse(Course course) {
        if (!enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
        }
    }

    public void dropCourse(Course course) {
        if (enrolledCourses.contains(course)) {
            enrolledCourses.remove(course);
            course.decrementEnrollment(); // Kontenjanı aç
            courseGrades.remove(course.getCode()); // Dersi bırakırsa notunu da sil
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