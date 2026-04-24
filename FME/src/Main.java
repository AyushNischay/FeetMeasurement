class Feet {
    double value;

    Feet(double value) {
        this.value = value;
    }

    boolean equals(Feet other) {
        return this.value == other.value;
    }
}

class Inches {
    double value;

    Inches(double value) {
        this.value = value;
    }

    boolean equals(Inches other) {
        return this.value == other.value;
    }
}

class QuantityMeasurementApp {

    public static boolean compareFeet(double v1, double v2) {
        Feet f1 = new Feet(v1);
        Feet f2 = new Feet(v2);
        return f1.equals(f2);
    }

    public static boolean compareInches(double v1, double v2) {
        Inches i1 = new Inches(v1);
        Inches i2 = new Inches(v2);
        return i1.equals(i2);
    }
}

class main {
    public static void main(String[] args) {

        double feet1 = 10.0;
        double feet2 = 10.0;

        double inch1 = 12.0;
        double inch2 = 12.0;

        boolean feetResult = QuantityMeasurementApp.compareFeet(feet1, feet2);
        boolean inchResult = QuantityMeasurementApp.compareInches(inch1, inch2);

        System.out.println("Feet comparison result: " + feetResult);
        System.out.println("Inches comparison result: " + inchResult);
    }
}