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
        
        double interval = 3;
        double domainStart = -150;
        double domainEnd = 150;
        double[][] result1 = calc.range3d("((.01*x)^2+(9/4)*((.01*y)^2)+(.01*z)^2-1)^3-((.01*x)^2)*((.01*z)^3)-(9/200)*((.01*y)^2)*((.01*z)^3)=0", interval, domainStart, domainEnd, "");


        Canvas canvas = new Canvas();
        canvas.realtimeGraph(result1, interval, domainStart, domainEnd);
    }
}
