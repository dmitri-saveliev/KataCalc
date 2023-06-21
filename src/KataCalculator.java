import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class KataCalculator {
    private static final int MIN_ARABIC_OPERAND_VALUE = 1;
    private static final int MAX_ARABIC_OPERAND_VALUE = 10;
    private static final Map<String, Integer> ROMAN_NUMBERS = new HashMap<>(){{
        put("I", 1);
        put("II", 2);
        put("III", 3);
        put("IV", 4);
        put("V", 5);
        put("VI", 6);
        put("VII", 7);
        put("VIII", 8);
        put("IX", 9);
        put("X", 10);
        put("XX", 20);
        put("XXX", 30);
        put("XL", 40);
        put("L", 50);
        put("LX", 60);
        put("LXX", 70);
        put("LXXX", 80);
        put("XC", 90);
        put("C", 100);
    }};

    private static final ArrayList<String> MATH_OPERATORS = new ArrayList<>(){{
        add("+");
        add("-");
        add("/");
        add("*");
    }};
    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        String s;
        while(!(s = sc.nextLine()).equals("bye")){
            try{
                System.out.println(calc(s));
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public static String calc(String input) throws Exception {
        String[] expression = input.split(" ");

        if(!isCorrectExpression(expression))
            throw new Exception("Введенная строка не является выражением," +
                    " либо выражение не соответствует заданному формату.");

        if(isCorrectRomanExpression(expression)) {
            return calculateRomanExpression(expression);
        }
        else if (isCorrectArabicExpression(expression)){
            return calculateArabicExpression(expression);
        }
        else{
            throw new Exception("Операнды переданного выражения не подходят под условия," +
                    " либо операнды имеют разный тип.");
        }
    }

    private static boolean isCorrectRomanExpression(String[] expression){
        if (!ROMAN_NUMBERS.containsKey(expression[0]) || !ROMAN_NUMBERS.containsKey(expression[2]))
            return false;

        int firstOperand = ROMAN_NUMBERS.get(expression[0]);
        int secondOperand = ROMAN_NUMBERS.get(expression[2]);

        return(inTargetRange(firstOperand)) && (inTargetRange(secondOperand));
    }

    private static boolean isCorrectArabicExpression(String[] expression){
        int firstOperand;
        int secondOperand;
        try {
            firstOperand = Integer.parseInt(expression[0]);
            secondOperand = Integer.parseInt(expression[2]);
        }catch (NumberFormatException e){
            return false;
        }

        return (inTargetRange(firstOperand)) && (inTargetRange(secondOperand));
    }

    private static boolean isCorrectExpression(String[] input){
        return (input.length == 3) && (MATH_OPERATORS.contains(input[1]));
    }

    private static boolean inTargetRange(int number){
        return (number >= MIN_ARABIC_OPERAND_VALUE) && (number <= MAX_ARABIC_OPERAND_VALUE);
    }

    private static String calculateRomanExpression(String[] expression) throws Exception {
        int firstOperand = ROMAN_NUMBERS.get(expression[0]);
        int secondOperand = ROMAN_NUMBERS.get(expression[2]);
        String operator = expression[1];

        int intResult = calculate(firstOperand, secondOperand, operator);

        if(intResult <= 0)
            throw new Exception("Результат выражения с римскими цифрами меньше либо равен нулю.");

        return toRomanNumber(intResult);
    }

    private static String calculateArabicExpression(String[] expression){
        int firstOperand = Integer.parseInt(expression[0]);
        int secondOperand = Integer.parseInt(expression[2]);
        String operator = expression[1];

        return "" + calculate(firstOperand, secondOperand, operator);
    }
    private static int calculate(int a, int b, String operator){
        return switch (operator) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "/" -> a / b;
            case "*" -> a * b;
            default -> throw new RuntimeException();
        };
    }

    private static String toRomanNumber(int intNumber){
        int intResultTens = intNumber - intNumber % 10;
        int intResultUnits = intNumber % 10;
        String romanResultTens = "";
        String romanResultUnits = "";

        for(Map.Entry<String, Integer> entry : ROMAN_NUMBERS.entrySet()){
            if(entry.getValue() == intResultTens) {
                romanResultTens = entry.getKey();
            }
            if(entry.getValue() == intResultUnits) {
                romanResultUnits = entry.getKey();
            }
        }

        return romanResultTens + romanResultUnits;
    }
}