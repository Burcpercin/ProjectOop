package DersKayit;

public class GradeRecord {
    private int midterm;
    private int finalExam;

    public GradeRecord(int midterm, int finalExam) {
        if (midterm < 0 || midterm > 100 || finalExam < 0 || finalExam > 100) {
            throw new IllegalArgumentException("Notlar 0-100 aralığında olmalıdır!");
        }
        
        this.midterm = midterm;
        this.finalExam = finalExam;
    }

    public double calculateAverage() {
        return (midterm * 0.40) + (finalExam * 0.60);
    }
    
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
            default: return 0.0;
        }
    }

    @Override
    public String toString() {
        return "Vize: " + midterm + " | Final: " + finalExam + 
               " | Ort: " + String.format("%.2f", calculateAverage()) + 
               " | Harf: " + getLetterGrade();
    }
}