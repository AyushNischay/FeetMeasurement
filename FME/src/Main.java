import java.util.Objects;
enum WeightUnit {
    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);
    private final double toKgFactor;
    WeightUnit(double toKgFactor) {
        this.toKgFactor = toKgFactor;
    }
    public double convertToBaseUnit(double value) {
        return value * toKgFactor; // to kilograms
    }
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / toKgFactor;
    }
    public double getConversionFactor() {
        return toKgFactor;
    }
}
class QuantityWeight {

    private final double value;
    private final WeightUnit unit;

    public QuantityWeight(double value, WeightUnit unit) {
        if (unit == null || !Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value or unit");
        }
        this.value = value;
        this.unit = unit;
    }

    // ---------------- Conversion ----------------
    public QuantityWeight convertTo(WeightUnit targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        double base = this.unit.convertToBaseUnit(this.value);
        double converted = targetUnit.convertFromBaseUnit(base);

        return new QuantityWeight(converted, targetUnit);
    }

    // ---------------- Equality ----------------
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        QuantityWeight that = (QuantityWeight) obj;
        double thisBase = this.unit.convertToBaseUnit(this.value);
        double thatBase = that.unit.convertToBaseUnit(that.value);
        return Math.abs(thisBase - thatBase) < 1e-6;
    }
    @Override
    public int hashCode() {
        return Objects.hash(unit.convertToBaseUnit(value));
    }
    // ---------------- Addition (UC6 style) ----------------
    public QuantityWeight add(QuantityWeight other) {
        return add(this, other, this.unit);
    }
    // ---------------- Addition (UC7 style) ----------------
    public static QuantityWeight add(QuantityWeight q1, QuantityWeight q2, WeightUnit targetUnit) {

        if (q1 == null || q2 == null || targetUnit == null) {
            throw new IllegalArgumentException("Invalid input");
        }
        double baseSum =
                q1.unit.convertToBaseUnit(q1.value)
                        + q2.unit.convertToBaseUnit(q2.value);
        double result = targetUnit.convertFromBaseUnit(baseSum);
        return new QuantityWeight(result, targetUnit);
    }
    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
    // Getters (optional for tests)
    public double getValue() {
        return value;
    }
    public WeightUnit getUnit() {
        return unit;
    }
}
class QuantityMeasurementApp {
    public static void main(String[] args) {
        QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1000.0, WeightUnit.GRAM);
        System.out.println(w1.equals(w2));
        // true
        System.out.println(w1.convertTo(WeightUnit.GRAM));
        // Quantity(1000.0, GRAM)
        System.out.println(
                QuantityWeight.add(w1, w2, WeightUnit.KILOGRAM)
        );
        // Quantity(2.0, KILOGRAM)
        System.out.println(
                QuantityWeight.add(w1, w2, WeightUnit.GRAM)
        );
        // Quantity(2000.0, GRAM)
    }
}