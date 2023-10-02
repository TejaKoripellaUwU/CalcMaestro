import java.util.ArrayList;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;

public class Parser {

    private static int charskip = 200;

    public Parser(){

    }

    public static char intToChar(int input){
        //skipping to avoid digits and operators
        return (char) (input+charskip);
    } 

    public static int charToInt(char input){
        //skipping to avoid digits and operators
        return (int) (input-charskip);
    }


    public String pemdasSimplify(String input, ArrayList<Callable<Double>> outSequence){
        if (input.length() == 1){
            return input;
        }
        //System.out.println(Engine.numbers);

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
                return this.pemdasSimplify(full, outSequence);
            }
            working = working.substring(parenStart+1, parenEnd);
        }

        int offset = parenStart+1;

        for (char op : Engine.pemdasops){
            int firstNumIndex = working.indexOf(op)-1;
            int secondNumIndex = working.indexOf(op)+1;
  

            if(firstNumIndex != -2 && secondNumIndex != 0){

                //keep adding expression results? to list and then access them based on counter?
                //benchmark performance increase from just substitution in string
                int firstListNumIndex = charToInt(working.charAt(firstNumIndex));
                int secondListNumIndex = charToInt(working.charAt(secondNumIndex));

    
                //compute and save command
                Engine.operators.get(op).apply(firstListNumIndex, secondListNumIndex);
                if(outSequence != null){
                    outSequence.add(() -> Engine.operators.get(op).apply(firstListNumIndex, secondListNumIndex));
                }
    
                full = full.substring(0, firstNumIndex+offset) + intToChar(Engine.numbers.size()-1) + full.substring(secondNumIndex+1+offset);
                
                return this.pemdasSimplify(full, outSequence);
            }
        }


        return this.pemdasSimplify(full, outSequence);
    }

    public String findNumbers(String expression){
        expression = expression += " ";
        String currentNum = "";
        int start = 0;
        String output = "";
        for(int i=0; i<expression.length(); i++){
            char currentChar = expression.charAt(i);
            if(Character.isDigit(currentChar) || currentChar == '.'){
                currentNum += currentChar;
            }else{
                if (currentNum.length() > 0){
                    Engine.numbers.add(Double.parseDouble(currentNum));
                    output += expression.substring(start, i-currentNum.length()) + intToChar(Engine.numbers.size()-1);
                    currentNum = "";
                    start = i;
                }else if(i == expression.length()-1){
                    output += expression.substring(start, i-currentNum.length());
                }
            }
        }

        return output;
    }
}
