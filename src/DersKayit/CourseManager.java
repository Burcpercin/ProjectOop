package DersKayit;

import java.util.ArrayList;
import java.util.List;

public class CourseManager {
    
    private List<Courses> courseList = new ArrayList<>();

    // Ders ekleme metodu
    public void addCourse(Courses newCourse) {
        courseList.add(newCourse);
        System.out.println("Başarılı: " + newCourse.getCourseName() + " sisteme eklendi.");
    }

    // Ders listeleme metodu
    public void listCourses() {
        if (courseList.isEmpty()) {
            System.out.println("Sistemde henüz kayıtlı ders yok.");
        } else {
            System.out.println("\n--- MEVCUT DERSLER ---");
            for (Courses c : courseList) {
                System.out.println(c.toString());
            }
        }
    }

    //Öğrenci işlemleri için gerekecek
    public List<Courses> getCourseList() {
        return courseList;
    }
}