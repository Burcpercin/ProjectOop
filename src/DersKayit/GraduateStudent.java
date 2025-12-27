package DersKayit;

public class GraduateStudent extends Student {
    private String thesisTopic;

    public GraduateStudent(String username, String name, int gradeLevel, String thesisTopic) {
        super(username, name, gradeLevel);
        this.thesisTopic = thesisTopic;
    }

    @Override
    public double calculateTuition() {
        return 8000.0; // Polimorfizm
    }
}