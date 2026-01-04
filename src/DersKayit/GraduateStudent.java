package DersKayit;

public class GraduateStudent extends Student {
    private String thesisTopic;


    public GraduateStudent(String studentNumber, String name, int gradeLevel, String thesisTopic) {
        super(studentNumber, name, gradeLevel, thesisTopic);
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