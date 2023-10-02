import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.concurrent.Callable;


public class App{
    public static void main(String[] args) throws Exception {
        Engine.init();
        Calculator calc = new Calculator();

        ArrayList<Double> result = calc.range2d(".002*x", 100, 0, 50000);

        System.out.println(result);
    }
}
