import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.concurrent.Callable;


public class App{
    public static void main(String[] args) throws Exception {
        Engine.init();
        Calculator calc = new Calculator();
        
        // System.out.println(calc.answer("1+1"));
        double interval = 5;

        double[][] result = calc.range3d("sqrt(150^2-x^2-z^2)", interval, -200, 200);

        Canvas canvas = new Canvas();
        canvas.realtimeGraph(result, interval, -200, 200);
    }
}
