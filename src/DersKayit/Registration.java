package DersKayit;

import java.time.LocalDateTime;

public class Registration {

	    private Student student;
	    private Course course;
	    private LocalDateTime registrationDate;
	    private String status; // "Registered", "Dropped" vb.

	    public Registration(Student student, Course course) {
	        this.student = student;
	        this.course = course;
	        this.registrationDate = LocalDateTime.now();
	        this.status = "Attempting";
	    }
	
}
