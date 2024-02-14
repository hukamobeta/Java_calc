import java.io.IOException;
import java.sql.SQLException;
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
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                System.out.println("Введите первое число:");
                a = scanner.nextDouble();
                b = choice != 8 ? getSecondNumber(scanner) : 0;
                performMathOperation(choice, a, b);
                break;
            case 10:
                ExcelExporter exporter = new ExcelExporter();
                exporter.export();
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
        result = switch (operation) {
            case 3 -> {
                operationType = "addition";
                yield MathOperations.add(a, b);
            }
            case 4 -> {
                operationType = "subtraction";
                yield MathOperations.subtract(a, b);
            }
            case 5 -> {
                operationType = "multiplication";
                yield MathOperations.multiply(a, b);
            }
            case 6 -> {
                operationType = "division";
                yield MathOperations.divide(a, b);
            }
            case 7 -> {
                operationType = "modulo";
                yield MathOperations.modulo(a, b);
            }
            case 8 -> {
                operationType = "absolute";
                yield MathOperations.absolute(a);
            }
            case 9 -> {
                operationType = "power";
                yield MathOperations.power(a, b);
            }
            default -> result;
        };
        System.out.println("Результат: " + result);
        DBhandler.saveOperationResult(operationType, a, b, result);
    }
}
