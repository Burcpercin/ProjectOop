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

    // Hoca giriş yapınca derslerini katalogdan çeker 
    public void syncCoursesFromCatalog(CourseCatalog catalog) {
        this.givenCourses.clear();
        for (Course c : catalog.getAllCourses()) {
            // İsim eşleşiyorsa bu ders hocanındır
            if (c.getInstructorName().equalsIgnoreCase(this.name)) {
                this.givenCourses.add(c);
            }
        }
    }

    public void addCourseToTeach(Course course) {
        // Sadece yerel listeye ekler, CSV kaydını Catalog yapar.
        givenCourses.add(course);
    }

    public String getName() { return name; }
    public List<Course> getGivenCourses() { return givenCourses; }
}