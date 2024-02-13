public class MathOperations {

    public static double add(double a, double b) {
        return a + b;
    }

    public static double subtract(double a, double b) {
        return a - b;
    }

    public static double multiply(double a, double b) {
        return a * b;
    }

    public static double divide(double a, double b) {
        return a / b;
    }

    public static double modulo(double a, double b) {
        return a % b;
    }

    public static double power(double a, double b) {
        return Math.pow(a, b);
    }

    public static double absolute(double a) {
        return Math.abs(a);
    }

    public static void performOperation(int choice, double a, double b) {
        double result = 0;
        String operationType = "";

        switch (choice) {
            case 3:
                operationType = "addition";
                result = add(a, b);
                break;
            case 4:
                operationType = "subtraction";
                result = subtract(a, b);
                break;
            case 5:
                operationType = "multiplication";
                result = multiply(a, b);
                break;
            case 6:
                operationType = "division";
                result = divide(a, b);
                break;
            case 7:
                operationType = "modulo";
                result = modulo(a, b);
                break;
            case 8:
                operationType = "absolute";
                result = absolute(a);
                break;
            case 9:
                operationType = "power";
                result = power(a, b);
                break;
            default:
                System.out.println("Неверный выбор операции.");
                return;
        }

        System.out.println("Результат: " + result);
        DBhandler.saveOperationResult(operationType, a, b, result);
    }
}
