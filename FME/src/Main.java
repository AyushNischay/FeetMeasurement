enum LengthUnit {

    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(0.0328084); // 1 cm = 0.0328084 feet

    private final double conversionFactor;

    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double getConversionFactor() {
        return conversionFactor;
    }
}

final class QuantityLength {

    private final double value;
    private final LengthUnit unit;

    public QuantityLength(double value, LengthUnit unit) {
        validate(value, unit);
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public LengthUnit getUnit() {
        return unit;
    }
    public QuantityLength convertTo(LengthUnit targetUnit) {
        double converted = convert(this.value, this.unit, targetUnit);
        return new QuantityLength(converted, targetUnit);
    }
    public static double convert(double value, LengthUnit source, LengthUnit target) {
        validate(value, source);
        if (target == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        // Same unit optimization
        if (source == target) return value;

        // Convert to base (feet)
        double baseValue = value * source.getConversionFactor();

        // Convert to target
        return baseValue / target.getConversionFactor();
    }

    // ✅ Private validation helper
    private static void validate(double value, LengthUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Value must be finite");
        }
    }

    // ✅ Equality based on base unit
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof QuantityLength)) return false;

        QuantityLength other = (QuantityLength) obj;

        double thisBase = this.value * this.unit.getConversionFactor();
        double otherBase = other.value * other.unit.getConversionFactor();

        return Math.abs(thisBase - otherBase) < 1e-6;
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }
}
class QuantityMeasurementApp {

    // Method 1: Raw values
    public static void demonstrateLengthConversion(
            double value, LengthUnit from, LengthUnit to) {

        double result = QuantityLength.convert(value, from, to);
        System.out.println(value + " " + from + " = " + result + " " + to);
    }

    // Method 2: Existing object
    public static void demonstrateLengthConversion(
            QuantityLength length, LengthUnit to) {

        QuantityLength converted = length.convertTo(to);
        System.out.println(length + " = " + converted);
    }

    public static void main(String[] args) {

        demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCHES);
        demonstrateLengthConversion(3.0, LengthUnit.YARDS, LengthUnit.FEET);
        demonstrateLengthConversion(36.0, LengthUnit.INCHES, LengthUnit.YARDS);

        QuantityLength length = new QuantityLength(1.0, LengthUnit.CENTIMETERS);
        demonstrateLengthConversion(length, LengthUnit.INCHES);
    }
}