import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.Timer;
import java.util.concurrent.Callable;

import javax.print.attribute.standard.ColorSupported;


public class App{
    public static void main(String[] args) throws Exception {
        Engine.init();
        Util.init();
        Calculator calc = new Calculator();
        
        double interval = 10;
        double domainStart = -200;
        double domainEnd = 200;
        double[][] result1 = calc.range3d("sqrt(150^2-x^2-z^2)", interval, domainStart, domainEnd);
        //double[][] result2 = calc.range3d("(0-1)*sqrt(150^2-x^2-z^2)", interval, domainStart, domainEnd);


        Canvas canvas = new Canvas();
        canvas.realtimeGraph(result1, interval, domainStart, domainEnd);
    }
}
