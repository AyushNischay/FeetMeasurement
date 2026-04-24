import java.util.*;
class QuantityMeasurementApp {
    public boolean compareFeet(double value1, double value2) {
        return value1 == value2;
    }
}
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter first value in feet: ");
        double value1 = sc.nextDouble();
        System.out.print("Enter second value in feet: ");
        double value2 = sc.nextDouble();
        QuantityMeasurementApp app = new QuantityMeasurementApp();
        boolean result = app.compareFeet(value1, value2);
        System.out.println("Are both values equal? " + result);
    }
}