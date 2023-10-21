import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.Timer;
import java.util.concurrent.Callable;

import javax.print.attribute.standard.ColorSupported;


public class App{
    public static void main(String[] args) throws Exception {
        Engine.init();
        Util.init();

        String sphere = "x^2+y^2+z^2=125^2";
        String heart = "((.01*x)^2+(9/4)*((.01*y)^2)+(.01*z)^2-1)^3-((.01*x)^2)*((.01*z)^3)-(9/200)*((.01*y)^2)*((.01*z)^3)=0";

        Calculator calc = new Calculator();
        
        double interval = 2.5;
        double domainStart = -150;
        double domainEnd = 150;



        double[][] result1 = calc.range3d(sphere, interval, domainStart, domainEnd, "");


        Canvas canvas = new Canvas();
        canvas.realtimeGraph(result1, interval, domainStart, domainEnd);
    }
}
