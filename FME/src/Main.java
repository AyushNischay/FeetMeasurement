class QuantityMeasurementApp {

    public enum LengthUnit {
        FEET(1.0),
        INCHES(12.0),
        YARDS(3.0),
        CENTIMETERS(0.0328084);

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }

        public double fromFeet(double feetValue) {
            return feetValue / toFeetFactor;
        }
    }

    public static class Length {
        private final double value;
        private final LengthUnit unit;

        public Length(double value, LengthUnit unit) {
            this.value = value;
            this.unit = unit;
        }

        private double toFeet() {
            return unit.toFeet(value);
        }

        public Length convertTo(LengthUnit targetUnit) {
            double baseFeet = this.toFeet();
            return new Length(targetUnit.fromFeet(baseFeet), targetUnit);
        }

        // UC6 method (keep it)
        public Length add(Length other) {
            double sumFeet = this.toFeet() + other.toFeet();
            return new Length(this.unit.fromFeet(sumFeet), this.unit);
        }

        // 🔥 UC7 CORE METHOD
        public Length add(Length other, LengthUnit targetUnit) {
            if (other == null || targetUnit == null) {
                throw new IllegalArgumentException("Invalid input");
            }

            double sumFeet = this.toFeet() + other.toFeet();
            double result = targetUnit.fromFeet(sumFeet);

            return new Length(result, targetUnit);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Length other = (Length) obj;
            return Double.compare(this.toFeet(), other.toFeet()) == 0;
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    public static void main(String[] args) {

        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCHES);

        System.out.println(l1.add(l2, LengthUnit.FEET));    // 2 FEET
        System.out.println(l1.add(l2, LengthUnit.INCHES));  // 24 INCHES
        System.out.println(l1.add(l2, LengthUnit.YARDS));   // ~0.667 YARDS
    }
}

