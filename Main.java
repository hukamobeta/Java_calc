import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Выберите операцию:");
            System.out.println("1. Вывести все таблицы из MySQL.");
            System.out.println("2. Создать таблицу в MySQL.");
            System.out.println("3. Сложение чисел.");
            System.out.println("4. Вычитание чисел.");
            System.out.println("5. Умножение чисел.");
            System.out.println("6. Деление чисел.");
            System.out.println("7. Деление чисел по модулю (остаток).");
            System.out.println("8. Возведение числа в модуль.");
            System.out.println("9. Возведение числа в степень.");
            System.out.println("10. Сохранить все данные из MySQL в Excel и вывести на экран.");
            System.out.println("0. Выход");

            choice = scanner.nextInt();
            handleUserChoice(choice, scanner);
        } while (choice != 0);
    }

    private static void handleUserChoice(int choice, Scanner scanner) {
        double a, b;
        switch (choice) {
            case 1:
                DBhandler.showAllTables();
                break;
            case 2:
                System.out.println("Введите название операции для создания таблицы (addition, subtraction, multiplication, division, modulo, absolute, power):");
                String operationType = scanner.next();
                DBhandler.createOperationTable(operationType);
                break;
            case 3: // Сложение
            case 4: // Вычитание
            case 5: // Умножение
            case 6: // Деление
            case 7: // Модуль (остаток от деления)
            case 8: // Модуль числа
            case 9: // Возведение в степень
                System.out.println("Введите первое число:");
                a = scanner.nextDouble();
                b = choice != 8 ? getSecondNumber(scanner) : 0;
                performMathOperation(choice, a, b);
                break;
            case 10:
                System.out.println("Тут могла быть ваша реклама");
                break;
            case 0:
                System.out.println("Выход из программы...");
                break;
            default:
                System.out.println("Неверный выбор. Попробуйте снова.");
        }
    }

    private static double getSecondNumber(Scanner scanner) {
        System.out.println("Введите второе число:");
        return scanner.nextDouble();
    }

    private static void performMathOperation(int operation, double a, double b) {
        double result = 0;
        String operationType = "";
        switch (operation) {
            case 3:
                operationType = "addition";
                result = MathOperations.add(a, b);
                break;
            case 4:
                operationType = "subtraction";
                result = MathOperations.subtract(a, b);
                break;
            case 5:
                operationType = "multiplication";
                result = MathOperations.multiply(a, b);
                break;
            case 6:
                operationType = "division";
                result = MathOperations.divide(a, b);
                break;
            case 7:
                operationType = "modulo";
                result = MathOperations.modulo(a, b);
                break;
            case 8:
                operationType = "absolute";
                result = MathOperations.absolute(a);
                break;
            case 9:
                operationType = "power";
                result = MathOperations.power(a, b);
                break;
        }
        System.out.println("Результат: " + result);
        DBhandler.saveOperationResult(operationType, a, b, result);
    }
}
