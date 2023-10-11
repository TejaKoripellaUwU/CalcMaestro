import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.concurrent.Callable;


public class App{
    public static void main(String[] args) throws Exception {
        Engine.init();
        Calculator calc = new Calculator();
        
  
        ArrayList<Double> result = calc.range2d("sin(.1*x)*25+250", .01, 0, 500);
        Canvas canvas = new Canvas(result);
    }
}
