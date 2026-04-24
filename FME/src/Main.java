enum Unit {
    FEET, INCH
}

class QuantityLength {
    double value;
    Unit unit;

    static final double INCH_TO_FEET = 1.0 / 12.0;

    QuantityLength(double value, Unit unit) {
        this.value = value;
        this.unit = unit;
    }

    double toFeet() {
        if (this.unit == Unit.FEET) {
            return this.value;
        } else {
            return this.value * INCH_TO_FEET;
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

        boolean result1 = QuantityMeasurementApp.compare(12.0, Unit.INCH, 1.0, Unit.FEET);
        boolean result2 = QuantityMeasurementApp.compare(24.0, Unit.INCH, 2.0, Unit.FEET);
        boolean result3 = QuantityMeasurementApp.compare(10.0, Unit.FEET, 120.0, Unit.INCH);

        System.out.println("12 inch == 1 feet: " + result1);
        System.out.println("24 inch == 2 feet: " + result2);
        System.out.println("10 feet == 120 inch: " + result3);
    }
}