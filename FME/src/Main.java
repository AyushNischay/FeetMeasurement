enum Unit {
    FEET, INCH, YARD, CM
}
class QuantityLength {
    double value;
    Unit unit;
    static final double INCH_TO_FEET = 1.0 / 12.0;
    static final double YARD_TO_FEET = 3.0;
    static final double CM_TO_INCH = 0.393701;
    QuantityLength(double value, Unit unit) {
        this.value = value;
        this.unit = unit;
    }
    double toFeet() {
        switch (this.unit) {
            case FEET:
                return this.value;
            case INCH:
                return this.value * INCH_TO_FEET;
            case YARD:
                return this.value * YARD_TO_FEET;
            case CM:
                return (this.value * CM_TO_INCH) * INCH_TO_FEET;
            default:
                return 0;
        }
    }
    boolean equals(QuantityLength other) {
        return Double.compare(this.toFeet(), other.toFeet()) == 0;
    }
}
class QuantityMeasurementApp {
    public static boolean compare(double v1, Unit u1, double v2, Unit u2) {
        QuantityLength q1 = new QuantityLength(v1, u1);
        QuantityLength q2 = new QuantityLength(v2, u2);
        return q1.equals(q2);
    }
}
class main {
    public static void main(String[] args) {
        System.out.println("=== UC4 Length Comparison ===");
        System.out.println("1 Yard == 36 Inch: " +
                QuantityMeasurementApp.compare(1.0, Unit.YARD, 36.0, Unit.INCH));
        System.out.println("2 Yard == 6 Feet: " +
                QuantityMeasurementApp.compare(2.0, Unit.YARD, 6.0, Unit.FEET));
        System.out.println("100 CM == 1 Meter (approx in inches/feet logic): " +
                QuantityMeasurementApp.compare(100.0, Unit.CM, 39.3701, Unit.INCH));
    }
}