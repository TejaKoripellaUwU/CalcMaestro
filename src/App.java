import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {
        String input = "(5.1^2)";

        ArrayList<Double> outNumbers = new ArrayList<Double>();
        String coded = Engine.findNumbers(input, outNumbers);

        //System.out.println(coded);
        String output = Engine.pemdas(coded, outNumbers);
        
        System.out.println(output.length());

        // System.out.println(output.length());
        // System.out.println(outNumbers);

    }
}
