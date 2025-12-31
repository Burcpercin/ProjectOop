package DersKayit;

import java.time.LocalDateTime;

public class Registration {

    private Student student;
    private Course course;
    private LocalDateTime registrationDate;
    private String status;

    public Registration(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.registrationDate = LocalDateTime.now();
        this.status = "Attempting";
    }

    public boolean completeRegistration() {
    	
    	// Zaten ekli mi?
        if(student.getEnrolledCourses().contains(this.course)) {
            System.out.println("UYARI: Bu ders zaten listenizde.");
            this.status = "Failed - Already Enrolled";
            return false;
        }
        
        //  Kontenjan Dolu mu?
        if (course.isFull()) {
            System.out.println("HATA: Kontenjan dolu! (" + course.getCapacity() + " kişilik yer doldu.)");
            this.status = "Failed - Capacity Full";
            return false;
        }

        // Sınıf seviyesi
        if (student.getGradeLevel() < course.getGradeLevel()) {
            System.out.println("HATA: Sınıf seviyesi yetersiz. (Gereken: " + course.getGradeLevel() + ")");
            this.status = "Failed - Grade Level";
            return false;
        }

        // Çakışma
        for (Course enrolled : student.getEnrolledCourses()) {
            if (enrolled.hasConflict(this.course)) {
                System.out.println("HATA: Ders saati çakışıyor! (" + enrolled.getName() + " ile)");
                this.status = "Failed - Conflict";
                return false;
            }
        }
        
        // Eğer kontrollere takılmadıysa dersi ekle
        student.registerForCourse(course);
        course.incrementEnrollment(); // Dersin mevcudunu 1 artırıyoruz.
        
        this.status = "Success";
        return true;
    }
}