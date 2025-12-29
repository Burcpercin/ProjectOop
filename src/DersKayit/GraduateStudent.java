package DersKayit;

public class GraduateStudent extends Student {
    private String thesisTopic;

    // Main'den gelen username'i, Student sınıfındaki studentNumber yerine gönderiyoruz.
    public GraduateStudent(String studentNumber, String name, int gradeLevel, String thesisTopic) {
        super(studentNumber, name, gradeLevel);
        this.thesisTopic = thesisTopic;
    }

    @Override
    public double calculateTuition() {
        return 8000.0; // Yüksek Lisans harcı
    }
    
    public String getThesisTopic() {
        return thesisTopic;
    }
}