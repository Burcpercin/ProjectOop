package DersKayit;

import java.time.LocalTime;

public class Course {
    private String code;
    private String name;
    private String instructorName;
    private String day;
    private LocalTime startTime;
    private LocalTime endTime;
    private int capacity;       // Toplam kontenjan
    private int gradeLevel;
    private int credit; 
    
    // Mevcut öğrenci sayısı
    private int currentEnrollment = 0; 

    public Course(String code, String name, String instructorName, String day, 
                  LocalTime startTime, LocalTime endTime, int capacity, int gradeLevel, int credit) {
        this.code = code;
        this.name = name;
        this.instructorName = instructorName;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.capacity = capacity;
        this.gradeLevel = gradeLevel;
        this.credit = credit; 
        this.currentEnrollment = 0; // Başlangıçta 0
    }

    
    // Kontenjan doldu mu?
    public boolean isFull() {
        return currentEnrollment >= capacity;
    }

    // Öğrenci eklendiğinde sayıyı artır
    public void incrementEnrollment() {
        if (currentEnrollment < capacity) {
            currentEnrollment++;
        }
    }

    // Öğrenci dersi bırakırsa sayıyı azalt
    public void decrementEnrollment() {
        if (currentEnrollment > 0) {
            currentEnrollment--;
        }
    }


    public boolean hasConflict(Course other) {
        if (!this.day.equalsIgnoreCase(other.day)) return false;
        return this.startTime.isBefore(other.endTime) && other.startTime.isBefore(this.endTime);
    }

    // Getterlar
    public String getCode() { return code; }
    public String getName() { return name; }
    public String getInstructorName() { return instructorName; }
    public String getDay() { return day; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }
    public int getCapacity() { return capacity; }
    public int getGradeLevel() { return gradeLevel; }
    public int getCredit() { return credit; } 
    public int getCurrentEnrollment() { return currentEnrollment; } // Yeni getter

    @Override
    public String toString() {
        // Çıktıda artık kontenjan durumunu da (Örn: [5/20]) görüyoruz
        return String.format("[%s] %s (Kr:%d Sın:%d) | %s %s-%s | Kapasite: %d/%d | Hoca: %s", 
                code, name, credit, gradeLevel, day, startTime, endTime, currentEnrollment, capacity, instructorName);
    }
}