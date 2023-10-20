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


    public static String[][] pemdasops = new String[][] {{"sin","cos","tan","arcsin","arccos","arctan","sqrt","abs","ln"},{"^"}, {"*","/"}, {"+","-"}}; 
    public static TreeMap<String, BiFunction<Integer, Integer, Double>> operators = new TreeMap<>();
    
    static int charskip = 200;

    public static void init(){
        Engine.operators.put("sin", Engine::sine);
        Engine.operators.put("cos", Engine::cosine);
        Engine.operators.put("tan", Engine::tangent);
        Engine.operators.put("arcsin", Engine::arcsine);
        Engine.operators.put("arcos", Engine::arccosine);
        Engine.operators.put("arctan", Engine::arctangent);
        Engine.operators.put("sqrt", Engine::squareroot);
        Engine.operators.put("abs", Engine::absolute);
        Engine.operators.put("ln", Engine::ln);
        Engine.operators.put("^", Engine::exponent);
        Engine.operators.put("*", Engine::multiply);
        Engine.operators.put("/", Engine::divide);
        Engine.operators.put("+", Engine::add);
        Engine.operators.put("-", Engine::subtract);

    }

    static double sine(int a, int b){
        double num2 = numbers.get(b);
        double out = Math.sin(num2);
        numbers.add(out);
        return out;
    }

      static double cosine(int a, int b){
        double num2 = numbers.get(b);
        double out = Math.cos(num2);
        numbers.add(out);
        return out;
    }

      static double tangent(int a, int b){
        double num2 = numbers.get(b);
        double out = Math.tan(num2);
        numbers.add(out);
        return out;
    }
        static double arcsine(int a, int b){
        double num2 = numbers.get(b);
        double out = Math.asin(num2);
        numbers.add(out);
        return out;
    }
        static double arccosine(int a, int b){
        double num2 = numbers.get(b);
        double out = Math.acos(num2);
        numbers.add(out);
        return out;
    }
    static double arctangent(int a, int b){
        double num2 = numbers.get(b);
        double out = Math.atan(num2);
        numbers.add(out);
        return out;
    }
    static double squareroot(int a, int b){
        double num2 = numbers.get(b);
        double out = Math.sqrt(num2);
        numbers.add(out);
        return out;
    }
    static double absolute(int a, int b){
        double num2 = numbers.get(b);
        double out = Math.abs(num2);
        numbers.add(out);
        return out;
    }
    static double ln(int a, int b){
        double num2 = numbers.get(b);
        double out = Math.log(num2);
        numbers.add(out);
        return out;
    }
    //functions end
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
