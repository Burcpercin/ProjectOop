package DersKayit;

import java.util.ArrayList;
import java.util.List;

public class Instructor {
    private String name;
    private String department;
    private List<Course> givenCourses;

    public Instructor(String name, String department) {
        this.name = name;
        this.department = department;
        this.givenCourses = new ArrayList<>();
    }

    public void addCourseToTeach(Course course) {
        // Hoca için sadece zaman kontrolü yapılır
        for (Course c : givenCourses) {
            if (c.hasConflict(course)) {
                System.out.println("HATA: Bu tarihte başka dersiniz var!");
                return;
            }
        }
        givenCourses.add(course);
        System.out.println("Ders programınıza eklendi: " + course.getCourseName());
    }

    public String getName() { return name; }
}