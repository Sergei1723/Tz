import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        DataCalculator data = new DataCalculator();
        System.out.println(
                        "Пример: 1+10.\n" +
                        "Допускается использовать арабские или римские цифры.\n" +
                        "Не допускается использовать арабские и римские цифры вместе.");

        do {
            try {
                data.read();
            } catch (RuntimeException e) {
                System.err.println(e.getMessage());
                continue;
            }
            double result = Calculator.calculate(data.getVar1(), data.getVar2(), data.getOper());
            System.out.println(result);
        } while (true);
    }

    static class DataCalculator extends Main {

        private int x;
        private int y;
        private char operation;

        public void read() {

            Scanner scanner = new Scanner(System.in); // объявляем класс сканер
            ArabicToRoman arabicToRoman = new ArabicToRoman();
            String expression = scanner.nextLine(); // сканер считывает строку

            try {
                String[] blocks = expression.split("[+-/*]");
                String[] roman = {"X", "IX", "VIII", "VII", "VI", "V", "IV", "III", "II", "I"};

                boolean flag = false;
                for (String s : roman) {
                    if (s.equals(blocks[0]) || s.equals(blocks[1])) {
                        flag = true;
                    }
                    if (flag) {
                        x = arabicToRoman.romanToArab(blocks[0]);
                        y = arabicToRoman.romanToArab(blocks[1]);
                    } else {
                        x = Integer.parseInt(blocks[0]);
                        y = Integer.parseInt(blocks[1]);
                    }
                    operation = expression.charAt(blocks[0].length());
                }

                if ((x > 10 || x < 1) || (y > 10 || y < 1)) {
                    throw new IllegalArgumentException();
                }

            } catch (RuntimeException e) {
                throw new IllegalArgumentException("Неверный формат данных");
            }
        }

        public int getVar1() {
            return x;
        }

        public int getVar2() {
            return y;
        }

        public char getOper() {
            return operation;
        }
    }

    static class Calculator {
        private Calculator() {
        }

        public static double calculate(int number1, int number2, char operation) {
            int result;
            switch (operation) {
                case '+':
                    result = number1 + number2;
                    break;
                case '-':
                    result = number1 - number2;
                    break;
                case '*':
                    result = number1 * number2;
                    break;
                case '/':
                    result = number1 / number2;
                    break;
                default:
                    throw new IllegalArgumentException("Неверный знак операции");
            }
            return result;
        }
    }

    static class ArabicToRoman {
        static String[] Rome = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        static int[] Arab = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

        public int romanToArab(String rome) {

            StringBuilder romeNumber = new StringBuilder(rome);
            int arabNumber = 0, i = 0;
            // Проверяем переданную строку на наличие символов
            if (romeNumber.length() > 0) {
                while (true) {
                    do {
                        if (Rome[i].length() <= romeNumber.length()) {
                            /* Выделяем из строки подстроку и сравниваем со
                             * значением из массива Arab*/
                            if (Rome[i].equals(romeNumber.substring(0,
                                    Rome[i].length()))) {
                            /* После чего прибавляем число соответствующее
                             индексу элемента римской цифры из массива Arab*/
                                arabNumber += Arab[i];
                                // и удаляем из строки римскую цифру
                                romeNumber.delete(0, Rome[i].length());
                                if (romeNumber.length() == 0)
                                    return arabNumber;
                            } else
                                break;
                        } else
                            break;
                    } while (romeNumber.length() != 0);
                    i++;
                }
            }
            return 0;
        }
    }
}

