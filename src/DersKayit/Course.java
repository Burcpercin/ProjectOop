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

    public Course(String code, String name, String instructorName, String day, 
                  LocalTime startTime, LocalTime endTime, int capacity, int gradeLevel) {
        this.code = code;
        this.name = name;
        this.instructorName = instructorName;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.capacity = capacity;
        this.gradeLevel = gradeLevel;
    }

    // Çakışma Kontrolü
    public boolean hasConflict(Course other) {
        if (!this.day.equalsIgnoreCase(other.day)) return false;
        // Saat aralığı çakışması mantığı:
        return this.startTime.isBefore(other.endTime) && other.startTime.isBefore(this.endTime);
    }

    // --- GETTER METOTLARI (CSV Yazma ve Kontroller İçin Tam Liste) ---
    public String getCode() { return code; }
    public String getName() { return name; }
    public String getInstructorName() { return instructorName; }
    public String getDay() { return day; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }
    public int getCapacity() { return capacity; }
    public int getGradeLevel() { return gradeLevel; }

    @Override
    public String toString() {
        return String.format("[%s] %s (Min Sınıf: %d) | %s %s-%s | Hoca: %s", 
                code, name, gradeLevel, day, startTime, endTime, instructorName);
    }
}