import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.DoubleSupplier;
import java.util.function.Function;

public class App {
    public static void main(String[] args) throws Exception {
        Engine.operator.put('^', Engine::exponent);
        Engine.operator.put('*', Engine::multiply);
        Engine.operator.put('/', Engine::divide);
        Engine.operator.put('+', Engine::add);
        Engine.operator.put('-', Engine::subtract);
        
        double step = 0.1;

        String input = "2+2";

        ArrayList<Double> outNumbers = new ArrayList<Double>();
        String coded = Engine.findNumbers(input, outNumbers);

        Engine.pemdas(coded, outNumbers);
        
        //System.out.println(Engine.sequence.size());
        //trainslated
        // if(output.length() == 1)
        //     System.out.println(outNumbers.get(output.charAt(0)));

        for(Callable supplier : Engine.sequence){
            System.out.println(supplier.call());
        }



    }
}
