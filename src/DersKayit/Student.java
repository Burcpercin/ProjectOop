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
    private String department;
    
    private List<Course> enrolledCourses;
    private Map<String, GradeRecord> courseGrades;

    public Student(String studentNumber, String name, int gradeLevel, String department) {
        this.studentNumber = studentNumber;
        this.name = name;
        this.gradeLevel = gradeLevel;
        this.department = department;
        this.enrolledCourses = new ArrayList<>();
        this.courseGrades = new HashMap<>(); 
    }

    // Not Giriş Kısıtlaması
    public void addGrade(String courseCode, int midterm, int finalExam) {
        boolean isTakingCourse = false;
        for(Course c : enrolledCourses) {
            if(c.getCode().equalsIgnoreCase(courseCode)) {
                isTakingCourse = true;
                break;
            }
        }

        if (isTakingCourse) {
            GradeRecord record = new GradeRecord(midterm, finalExam);
            courseGrades.put(courseCode, record);
        } else {
            throw new IllegalArgumentException("Hata: Öğrenci (" + this.name + ") bu dersi almıyor.");
        }
    }

    public double calculateGPA() {
        double totalPoints = 0.0;
        double totalCredits = 0.0;
        for (Course course : enrolledCourses) {
            if (courseGrades.containsKey(course.getCode())) {
                GradeRecord grade = courseGrades.get(course.getCode());
                totalPoints += (grade.getCoefficient() * course.getCredit());
                totalCredits += course.getCredit();
            }
        }
        if (totalCredits == 0) return 0.0;
        return totalPoints / totalCredits;
    }
    
    public String getGradeDetails(String courseCode) {
        if(courseGrades.containsKey(courseCode)) {
            return courseGrades.get(courseCode).toString();
        }
        return "Not Girilmedi";
    }

    public double calculateTuition() { return 5000.0; }

    public int calculateTotalCredits() {
        int total = 0;
        for(Course c : enrolledCourses) total += c.getCredit();
        return total;
    }

    @Override
    public void registerForCourse(Course course) {
        if (!enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
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
            course.decrementEnrollment(); 
            courseGrades.remove(course.getCode()); 
            System.out.println(">> " + course.getCode() + " dersi bırakıldı.");
        } else {
            System.out.println(">> Hata: Bu dersi zaten almıyorsunuz.");
        }
    }

    public List<Course> getEnrolledCourses() { return Collections.unmodifiableList(enrolledCourses); }
    public String getName() { return name; }
    public int getGradeLevel() { return gradeLevel; }
    public String getStudentNumber() { return studentNumber; }
    public String getDepartment() { return department; }
}