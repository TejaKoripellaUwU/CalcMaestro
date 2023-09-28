import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.DoubleSupplier;
import java.util.function.Function;
import java.util.function.Supplier;

public class Engine {
    public static ArrayList<Callable<Double>> sequence = new ArrayList<>();
    public static ArrayList<Double> parameters = new ArrayList<>();

    private static char[] pemdasops = new char[] {'^','*','/','+','-'}; 
    public static TreeMap<Character, BiFunction<Double, Double, Double>> operator = new TreeMap<>();
    
    static int charskip = 200;

    public static boolean checkVariables(){
        return false; 
    }

    public static char intToChar(int input){
        //skipping to avoid digits and operators
        return (char) (input+charskip);
    } 

    public static int charToInt(char input){
        //skipping to avoid digits and operators
        return (int) (input-charskip);
    }

    public static ArrayList<String> tokenize(String input){
        ArrayList<String> tokens = new ArrayList<>();
        String currentNum = "";

        for(int i = 0; i < input.length(); i++){
            char currentChar = input.charAt(i);
            if (Character.isDigit(currentChar) || currentChar == '.'){
                currentNum += currentChar;
            }else{
                if (currentNum.length() > 0)
                    tokens.add(currentNum);
                    
                
                tokens.add(""+currentChar);
                currentNum = "";
            }
        }
        return tokens;
    }

    public static String pemdas(String input, ArrayList<Double> numbers){
        
        if (input.length() == 1){
            return input;
        }

        String full = input;
        String working = input;
        //parentheses
        int parenStart = -1; int parenEnd = -1;

        for (int i = 0; i < input.length(); i++){
            if (input.charAt(i)=='('){
                parenStart = i;
            }
            if(input.charAt(i)==')'){
                parenEnd = i;
                break;
            }
        }

    
        if(parenStart != -1 && parenEnd != -1){
            if(Math.abs(parenEnd - parenStart) <= 2){
                full = full.substring(0, parenStart) + full.substring(parenStart+1, parenEnd) + full.substring(parenEnd+1);
                return Engine.pemdas(full, numbers);
            }
            working = working.substring(parenStart+1, parenEnd);
        }

        int offset = parenStart+1;

        for (char op : pemdasops){
            int firstNumIndex = working.indexOf(op)-1;
            int secondNumIndex = working.indexOf(op)+1;
  

            if(firstNumIndex != -2 && secondNumIndex != 0){

                if(Character.isAlphabetic(working.charAt(firstNumIndex))){

                }
                //keep adding expression results? to list and then access them based on counter?
                //benchmark performance increase from just substitution in string
                double firstNum = numbers.get(charToInt(working.charAt(firstNumIndex)));
                double secondNum = numbers.get(charToInt(working.charAt(secondNumIndex)));
    
                //compute and save command
                double e = operator.get(op).apply(firstNum, secondNum);
                numbers.add(e);
                sequence.add(() -> operator.get(op).apply(firstNum, secondNum));
    
    
                full = full.substring(0, firstNumIndex+offset) + intToChar(numbers.size()-1) + full.substring(secondNumIndex+1+offset);
                
                return Engine.pemdas(full, numbers);
            }
        }


        return Engine.pemdas(full, numbers);
    }

    //static for now to make testing easier
    public static String findNumbers(String input, ArrayList<Double> outNumbers){
        input = input += " ";
        String currentNum = "";
        int start = 0;
        String output = "";
        for(int i=0; i<input.length(); i++){
            char currentChar = input.charAt(i);
            if(Character.isDigit(currentChar) || currentChar == '.'){
                currentNum += currentChar;
            }else{
                if (currentNum.length() > 0){
                    outNumbers.add(Double.parseDouble(currentNum));
                    output += input.substring(start, i-currentNum.length()) + intToChar(outNumbers.size()-1);
                    currentNum = "";
                    start = i;
                }else if(i == input.length()-1){
                    output += input.substring(start, i-currentNum.length());
                }
            }
        
        }

        return output;
    }

    static double multiply(double a, double b){
        return a * b;
    }

    public static double add(Double a, Double b){
        return a + b;
    }

    static double divide(double a, double b){
        return a / b;
    }

    static double subtract(double a, double b){
        return a - b;
    }

    static double exponent(double a, double b){
        return Math.pow(a, b);
    }

}
