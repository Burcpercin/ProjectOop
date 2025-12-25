package DersKayit;

import java.util.ArrayList;
import java.util.List;

public class CourseManager {
    private List<Courses> allCourses = new ArrayList<>();

    public void addCourse(Courses c) {
        allCourses.add(c);
        // İleride buraya 'courses.csv'ye yazma kodu eklenecek
    }

    public void listCourses() {
        if (allCourses.isEmpty()) {
            System.out.println("Sistemde açık ders yok.");
        } else {
            System.out.println("\n--- AÇILAN DERSLER ---");
            for (Courses c : allCourses) {
                System.out.println(c);
            }
        }
    }

    public Courses findCourseByCode(String code) {
        for (Courses c : allCourses) {
            if (c.getCourseCode().equalsIgnoreCase(code)) {
                return c;
            }
        }
        return null;
    }
}