package DersKayit;

import java.time.LocalTime;

public class Courses {
    private String courseCode;
    private String courseName;
    private String instructorName;
    private String day;
    private LocalTime startTime;
    private LocalTime endTime;
    private int capacity;
    private int gradeLevel;

    public Courses(String courseCode, String courseName, String instructorName, 
                   String day, LocalTime startTime, LocalTime endTime, int capacity, int gradeLevel) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.instructorName = instructorName;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.capacity = capacity;
        this.gradeLevel = gradeLevel;
    }
    // Gün-saat çakışma kontrolü.
    public boolean hasConflict(Courses otherCourse) {
        if (!this.day.equalsIgnoreCase(otherCourse.day)) return false;
        return this.startTime.isBefore(otherCourse.endTime) && 
               otherCourse.startTime.isBefore(this.endTime);
    }

    public String getCourseCode() { return courseCode; }
    public String getCourseName() { return courseName; }
    public int getGradeLevel() { return gradeLevel; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }

    @Override
    public String toString() {
        return String.format("[%s] %s (Sınıf: %d) | %s %s-%s | Hoca: %s", 
                courseCode, courseName, gradeLevel, day, startTime, endTime, instructorName);
    }
}

