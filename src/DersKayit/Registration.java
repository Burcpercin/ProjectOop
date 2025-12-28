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
	    
	    public boolean completeRegistration() {
	        // 1. Sınıf seviyesi kontrolü
	        if (student.getGradeLevel() < course.getGradeLevel()) {
	            System.out.println("HATA: Sınıf seviyesi yetersiz.");
	            this.status = "Failed";
	            return false;
	        }
	        else {
	        	return true;
	        }
	    }
}