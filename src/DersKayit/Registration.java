package DersKayit;

import java.time.LocalDateTime;

public class Registration {

    private Student student;
    private Course course;

    public Registration(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    public boolean completeRegistration() {
    	
    	// Bölüm kontrolü
        boolean isCommonCourse = course.getDepartment().equalsIgnoreCase("Ortak");
        
        if (!isCommonCourse && !course.getDepartment().equalsIgnoreCase(student.getDepartment())) {
            System.out.println("HATA: Bölüm uyuşmazlığı!");
            System.out.println("      Öğrenci Bölümü: " + student.getDepartment());
            System.out.println("      Ders Bölümü:    " + course.getDepartment());
            return false;
        }
        
        //  Sınıf seviyesi kontrolü
        if (student.getGradeLevel() < course.getGradeLevel()) {
            System.out.println("HATA: Sınıf seviyesi yetersiz. (Gereken: " + course.getGradeLevel() + ")");
            return false;
        }

        // Zaten ekli mi?
        if(student.getEnrolledCourses().contains(this.course)) {
            System.out.println("UYARI: Bu ders zaten listenizde.");
            return false;
        }

        // Kontenjan Dolu mu?
        if (course.isFull()) {
            System.out.println("HATA: Kontenjan dolu! (" + course.getCapacity() + " kişilik yer doldu.)");
            return false;
        }

        // Çakışma kontrolü
        for (Course enrolled : student.getEnrolledCourses()) {
            if (enrolled.hasConflict(this.course)) {
                System.out.println("HATA: Ders saati çakışıyor! (" + enrolled.getName() + " ile)");
                return false;
            }
        }
        
        // Kayıt Başarılı
        student.registerForCourse(course);
        course.incrementEnrollment();
        
        return true;
    }
}