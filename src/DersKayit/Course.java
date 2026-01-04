package DersKayit;

import java.time.LocalTime;

public class Course {
    private String code;
    private String name;
    private String instructorName;
    private String day;
    private LocalTime startTime;
    private LocalTime endTime;
    private int capacity;
    private int gradeLevel;
    private int credit; 
    
    // Dersin Bölümü 
    private String department;
    
    private int currentEnrollment = 0; 

    public Course(String code, String name, String instructorName, String day, 
                  LocalTime startTime, LocalTime endTime, int capacity, int gradeLevel, int credit, String department) {
        this.code = code;
        this.name = name;
        this.instructorName = instructorName;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.capacity = capacity;
        this.gradeLevel = gradeLevel;
        this.credit = credit; 
        this.department = department; 
        this.currentEnrollment = 0;
    }

    public boolean isFull() { return currentEnrollment >= capacity; }

    public void incrementEnrollment() { if (currentEnrollment < capacity) currentEnrollment++; }

    public void decrementEnrollment() { if (currentEnrollment > 0) currentEnrollment--; }

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
    public int getCurrentEnrollment() { return currentEnrollment; }
    public String getDepartment() { return department; } // YENİ Getter

    @Override
    public String toString() {
        return String.format("[%s] %s (%s) | Kr:%d Sın:%d | %s %s-%s | Kapasite: %d/%d | Hoca: %s", 
                code, name, department, credit, gradeLevel, day, startTime, endTime, currentEnrollment, capacity, instructorName);
    }
}