package DersKayit;

public class GradeRecord {
    private int midterm; // Vize
    private int finalExam; // Final

    public GradeRecord(int midterm, int finalExam) {
        this.midterm = midterm;
        this.finalExam = finalExam;
    }

    // 1. Ortalama Hesapla (%40 Vize, %60 Final)
    public double calculateAverage() {
        return (midterm * 0.40) + (finalExam * 0.60);
    }

    // 2. Ortalamaya Göre Harf Notunu Bul
    public String getLetterGrade() {
        double avg = calculateAverage();
        if (avg >= 90) return "AA";
        else if (avg >= 85) return "BA";
        else if (avg >= 80) return "BB";
        else if (avg >= 75) return "CB";
        else if (avg >= 70) return "CC";
        else if (avg >= 65) return "DC";
        else if (avg >= 60) return "DD";
        else if (avg >= 50) return "FD";
        else return "FF";
    }

    // 3. Harf Notuna Göre Katsayı (GPA için lazım)
    public double getCoefficient() {
        String letter = getLetterGrade();
        switch (letter) {
            case "AA": return 4.0;
            case "BA": return 3.5;
            case "BB": return 3.0;
            case "CB": return 2.5;
            case "CC": return 2.0;
            case "DC": return 1.5;
            case "DD": return 1.0;
            default: return 0.0; // FF ve FD
        }
    }

    @Override
    public String toString() {
        return "Vize: " + midterm + " | Final: " + finalExam + 
               " | Ort: " + String.format("%.2f", calculateAverage()) + 
               " | Harf: " + getLetterGrade();
    }
}