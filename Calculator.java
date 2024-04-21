import java.util.*;

public class Calculator {
    private static final Map<Character, Integer> romanNumerals = Map.of(
            'I', 1, 'V', 5, 'X', 10, 'L', 50, 'C', 100, 'D', 500, 'M', 1000);
    private static final List<String> romanNumeralsOrder = Arrays.asList(
            "I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение:");
        String input = scanner.nextLine();
        try {
            System.out.println(calculate(input));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static String calculate(String input) throws Exception {
        String[] parts = input.split(" ");
        if (parts.length != 3) {
            throw new Exception(
                    "throws Exception // Строка не является математической операцией или формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }

        boolean isRomanA = isRoman(parts[0]);
        boolean isRomanB = isRoman(parts[2]);
        boolean isArabicA = isArabic(parts[0]);
        boolean isArabicB = isArabic(parts[2]);

        if ((isRomanA && isArabicB) || (isArabicA && isRomanB)) {
            throw new Exception("throws Exception // Используются одновременно разные системы счисления");
        }

        boolean isRoman = isRomanA && isRomanB;
        boolean isArabic = isArabicA && isArabicB;

        if (!(isRoman || isArabic)) {
            throw new Exception("throws Exception // Числа должны быть либо оба римские, либо оба арабские");
        }

        int a = isRoman ? romanToArabic(parts[0]) : Integer.parseInt(parts[0]);
        int b = isRoman ? romanToArabic(parts[2]) : Integer.parseInt(parts[2]);

        if (a < 1 || a > 10 || b < 1 || b > 10) {
            throw new Exception("throws Exception // Числа должны быть в диапазоне от 1 до 10");
        }

        int result;
        switch (parts[1]) {
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            case "*":
                result = a * b;
                break;
            case "/":
                result = a / b;
                break;
            default:
                throw new Exception("throws Exception // Неверная операция");
        }

        if (isRoman) {
            if (result < 1) {
                throw new Exception(
                        "throws Exception // В римской системе нет отрицательных чисел");
            }
            return arabicToRoman(result);
        } else {
            return String.valueOf("Результат: " + result);
        }
    }

    private static boolean isRoman(String s) {
        return s.matches("[IVXLCDM]+");
    }

    private static boolean isArabic(String s) {
        return s.matches("\\d+");
    }

    private static int romanToArabic(String roman) {
        int result = 0;
        int prev = 0;
        for (int i = roman.length() - 1; i >= 0; i--) {
            int value = romanNumerals.get(roman.charAt(i));
            if (value < prev) {
                result -= value;
            } else {
                result += value;
            }
            prev = value;
        }
        return result;
    }

    private static String arabicToRoman(int number) {
        List<Integer> values = Arrays.asList(
                1000, 900, 500, 400,
                100, 90, 50, 40,
                10, 9, 5, 4, 1);
        List<String> symbols = Arrays.asList(
                "M", "CM", "D", "CD",
                "C", "XC", "L", "XL",
                "X", "IX", "V", "IV", "I");

        StringBuilder roman = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            while (number >= values.get(i)) {
                number -= values.get(i);
                roman.append(symbols.get(i));
            }
        }
        return roman.toString();
    }

}
