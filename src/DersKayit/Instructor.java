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

    public void syncCoursesFromCatalog(CourseCatalog catalog) {
        this.givenCourses.clear();
        for (Course c : catalog.getAllCourses()) {
            if (c.getInstructorName().equalsIgnoreCase(this.name)) {
                this.givenCourses.add(c);
            }
        }
    }

    public void addCourseToTeach(Course course) {
        givenCourses.add(course);
    }

    public String getName() { return name; }
    public List<Course> getGivenCourses() { return givenCourses; }
    public String getDepartment() { return department; }
}