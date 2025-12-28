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
        // Sınıf seviyesi yeterlimi?
        if (student.getGradeLevel() < course.getGradeLevel()) {
            System.out.println("HATA: Sınıf seviyesi yetersiz. (Gereken: " + course.getGradeLevel() + ")");
            this.status = "Failed - Grade Level";
            return false;
        }

        // Ders saatleri çakışıyor mu?
        for (Course enrolled : student.getEnrolledCourses()) {
            if (enrolled.hasConflict(this.course)) {
                System.out.println("HATA: Ders saati çakışıyor! (" + enrolled.getCourseName() + " ile)");
                this.status = "Failed - Conflict";
                return false;
            }
        }
        
        // Zaten ekli mi?
        if(student.getEnrolledCourses().contains(this.course)) {
            System.out.println("UYARI: Bu ders zaten listenizde.");
            this.status = "Failed - Already Enrolled";
            return false;
        }

        // Eğer kontrollere takılmaadıysa dersi ekle
        student.registerForCourse(course);
        this.status = "Success";
        return true;
    }
}