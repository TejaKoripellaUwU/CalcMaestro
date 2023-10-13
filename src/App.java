import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.concurrent.Callable;


public class App{
    public static void main(String[] args) throws Exception {
        Engine.init();
        Calculator calc = new Calculator();
        
        double interval = 2;

        //double[][] result = calc.range3d("sin(.01*x)*25+sin(.01*z)*25+250", 1, -200, 200);
        double[][] result = calc.range3d("sqrt(150^2-x^2-z^2)+500", interval, -200, 200);
        //double[][] result = calc.range3d("(.1*x)^2", 1, -200, 200);
        //double[][] result = calc.range3d("0*x+(.1*z)^2+100", 1, -200, 200);

        Canvas canvas = new Canvas(result, interval, -200, 200);
        while(true){
            Thread.sleep(100);
            canvas.paintagain();
        }
    }
}
