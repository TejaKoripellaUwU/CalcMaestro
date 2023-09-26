import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;

public class App {
    public static void main(String[] args) throws Exception {
        String input = "(3^2*(4+6))/(5-1)";

        ArrayList<Double> outNumbers = new ArrayList<Double>();
        String coded = Engine.findNumbers(input, outNumbers);

        //System.out.println(coded);

        String output = Engine.pemdas(coded, outNumbers);
        

        //trainslated
        if(output.length() == 1)
            System.out.println(outNumbers.get(output.charAt(0)));

    }
}
