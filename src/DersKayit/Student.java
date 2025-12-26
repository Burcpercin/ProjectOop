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

        if(enrolledCourses.contains(course)) {
            System.out.println("Zaten kayıtlısınız.");
            return;
        }
        this.enrolledCourses.add(course);
        System.out.println(course.getCourseCode() + " dersi eklendi.");
    }

}