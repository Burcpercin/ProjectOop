package DersKayit;

import java.util.ArrayList;
import java.util.List;

public class Instructor {
    private String name;
    private String department;
    private List<Courses> givenCourses;

    public Instructor(String name, String department) {
        this.name = name;
        this.department = department;
        this.givenCourses = new ArrayList<>();
    }

  
}