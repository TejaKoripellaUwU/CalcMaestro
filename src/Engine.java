import java.util.ArrayList;

public class Engine {

    int MAXCOUNT = 20; // increase later

    public static boolean checkVariables(){
        return false; 
    }

    public static char intToChar(int input){
        //add right skipping to avoid digits and operators
        return (char) input;
    } 

    public static int charToInt(char input){
        //add right skipping to avoid digits and operators
        return (int) input;
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
        
        if(Math.abs(parenEnd - parenStart) <= 2){
            full = full.substring(0, parenStart) + full.substring(parenEnd+1);
            //return
        }

        if(parenStart != -1 && parenEnd != -1){
            working = working.substring(parenStart+1, parenEnd);
        }

        int offset = parenStart+1;

        //exponents
        int firstNumIndex = working.indexOf("^")-1;
        int secondNumIndex = working.indexOf("^")+1;
        double firstNum = numbers.get(charToInt(working.charAt(firstNumIndex)));
        double secondNum = numbers.get(charToInt(working.charAt(secondNumIndex)));


        
        //compute and save command
        double e = Math.pow(firstNum, secondNum);
        numbers.add(e);


        //System.out.println(full.substring(0, firstNumIndex));
        full = full.substring(0, firstNumIndex+offset) + intToChar(numbers.size()-1) + full.substring(secondNumIndex+1+offset);
        
        
        return full;
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
}
