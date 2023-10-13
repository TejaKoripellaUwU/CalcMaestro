import java.util.ArrayList;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;


public class Engine {
    public static ArrayList<Callable<Double>> sequence = new ArrayList<>();
    public static ArrayList<Double> parameters = new ArrayList<>();

    public static ArrayList<Double> numbers = new ArrayList<>();

    public static ArrayList<Integer> variableIndices = new ArrayList<>();
    public static ArrayList<Integer> xIndices = new ArrayList<>();
    public static ArrayList<Integer> zIndices = new ArrayList<>();


    public static String[][] pemdasops = new String[][] {{"sin", "cos"}, {"^"}, {"*","/"}, {"+","-"}}; 
    public static TreeMap<String, BiFunction<Integer, Integer, Double>> operators = new TreeMap<>();
    
    static int charskip = 200;

    public static void init(){
        Engine.operators.put("sin", Engine::sin);
        Engine.operators.put("cos", Engine::sin);
        Engine.operators.put("^", Engine::exponent);
        Engine.operators.put("*", Engine::multiply);
        Engine.operators.put("/", Engine::divide);
        Engine.operators.put("+", Engine::add);
        Engine.operators.put("-", Engine::subtract);
    }

    static double sin(int a, int b){
        double num2 = numbers.get(b);
        double out = Math.sin(num2);
        numbers.add(out);
        return out;
    }
    static double cos(int a, int b){
        double num2 = numbers.get(b);
        double out = Math.cos(num2);
        numbers.add(out);
        return out;
    }
    static double multiply(int a, int b){
        double num1 = numbers.get(a);
        double num2 = numbers.get(b);
        double out = num1 * num2;
        numbers.add(out);
        return out;
    }
    static double divide(int a, int b){
        double num1 = numbers.get(a);
        double num2 = numbers.get(b);
        double out = num1 / num2;
        numbers.add(out);
        return out;
    }
    static double add(int a, int b){
        double num1 = numbers.get(a);
        double num2 = numbers.get(b);
        double out = num1 + num2;
        numbers.add(out);
        return out;
    }
    static double subtract(int a, int b){
        double num1 = numbers.get(a);
        double num2 = numbers.get(b);
        double out = num1 - num2;
        numbers.add(out);
        return out;
    }
    static double exponent(int a, int b){
        double num1 = numbers.get(a);
        double num2 = numbers.get(b);
        double out = Math.pow(num1,  num2);
        numbers.add(out);
        return out;
    }


}
