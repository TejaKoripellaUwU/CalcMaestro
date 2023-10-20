import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;

public class Parser {

    private static int charskip = 200;

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
        
        for (String[] ops : Engine.pemdasops){
            
            int earliestOpIndex = Integer.MAX_VALUE;
            String operator = "";

            for (String op : ops){
                int opindex = working.indexOf(op);
                if (opindex == -1){
                    continue;
                }
                if (opindex < earliestOpIndex){
                    earliestOpIndex = opindex;
                    operator = op;
                }
            }

            if (ops.length == 1){
                if(working.indexOf(ops[0]) == -1){
                    continue;
                }
                operator = ops[0];
            }

            int firstNumIndex = operator.length() > 1 ? working.indexOf(operator) : working.indexOf(operator)-1;
            int secondNumIndex = working.indexOf(operator)+operator.length();
            if(firstNumIndex != -2 && secondNumIndex != 0){
                
                //keep adding expression results? to list and then access them based on counter?
                //benchmark performance increase from just substitution in string
                int firstListNumIndex = charToInt(working.charAt(firstNumIndex));
                int secondListNumIndex = charToInt(working.charAt(secondNumIndex));

                
                //compute and save command
                Engine.operators.get(operator).apply(firstListNumIndex, secondListNumIndex);
                if(outSequence != null){
                    BiFunction<Integer, Integer, Double> function = Engine.operators.get(operator);
                    outSequence.add(() -> function.apply(firstListNumIndex, secondListNumIndex));
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
            }
            else if(Character.isAlphabetic(currentChar)){
                if(currentChar == 'p' && expression.charAt(i+1) == 'i'){
                    Engine.numbers.add(Math.PI);
                    output += expression.substring(start, i) + intToChar(Engine.numbers.size()-1);
                    currentNum = "";
                    start = i+2;
                }
                if(currentChar == 'e'){
                    Engine.numbers.add(Math.E);
                    output += expression.substring(start, i) + intToChar(Engine.numbers.size()-1);
                    currentNum = "";
                    start = i+1;
                }
                else if(currentChar == 'x' || currentChar == 'z'){
                    Engine.numbers.add(0.0);
                    if(currentChar == 'x')
                        Engine.xIndices.add(Engine.numbers.size()-1);
                    else
                        Engine.zIndices.add(Engine.numbers.size()-1);
                    output += expression.substring(start, i) + intToChar(Engine.numbers.size()-1);
                    currentNum = "";
                    start = i+1;
                }
            }
            
            else{
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
