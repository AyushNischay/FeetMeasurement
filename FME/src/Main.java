enum LengthUnit {

    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(0.0328084);

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

    // ✅ Conversion (from UC5)
    public static double convert(double value, LengthUnit source, LengthUnit target) {
        validate(value, source);
        if (target == null) throw new IllegalArgumentException("Target unit cannot be null");

        if (source == target) return value;

        double base = value * source.getConversionFactor();
        return base / target.getConversionFactor();
    }

    // ✅ UC6: ADD METHOD (Core Logic)
    public QuantityLength add(QuantityLength other) {
        if (other == null) {
            throw new IllegalArgumentException("Second operand cannot be null");
        }

        // Convert both to base unit (feet)
        double thisBase = this.value * this.unit.getConversionFactor();
        double otherBase = other.value * other.unit.getConversionFactor();

        // Add in base
        double sumBase = thisBase + otherBase;

        // Convert back to unit of first operand
        double resultValue = sumBase / this.unit.getConversionFactor();

        return new QuantityLength(resultValue, this.unit);
    }

    // ✅ Overloaded static method
    public static QuantityLength add(
            double v1, LengthUnit u1,
            double v2, LengthUnit u2,
            LengthUnit targetUnit) {

        validate(v1, u1);
        validate(v2, u2);

        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        double base1 = v1 * u1.getConversionFactor();
        double base2 = v2 * u2.getConversionFactor();

        double sumBase = base1 + base2;

        double result = sumBase / targetUnit.getConversionFactor();

        return new QuantityLength(result, targetUnit);
    }

    // ✅ Validation helper
    private static void validate(double value, LengthUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Value must be finite");
        }
    }

    // ✅ Equality (base comparison)
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
        return "Quantity(" + value + ", " + unit + ")";
    }
}
class QuantityMeasurementApp {

    public static void main(String[] args) {

        QuantityLength a = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength b = new QuantityLength(12.0, LengthUnit.INCHES);

        QuantityLength result = a.add(b);

        System.out.println(result); // Quantity(2.0, FEET)

        // Static method
        QuantityLength result2 = QuantityLength.add(
                12.0, LengthUnit.INCHES,
                1.0, LengthUnit.FEET,
                LengthUnit.INCHES
        );

        System.out.println(result2); // Quantity(24.0, INCHES)
    }
}