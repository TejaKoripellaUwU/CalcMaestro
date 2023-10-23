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
        String heart = "((.01*x)^2+(9/4)*((.01*y)^2)+(.01*z)^2-1)^3-((.01*x)^2)*((.01*z)^3)-(9/200)*((.01*y)^2)*((.01*z)^3)=0+0";
        String donut = "z^2+(200-sqrt(x^2+y^2))^2=100^2";
        String paraboloid = "(.1*x)^2+(.1*z)^2=y+0";
        String cone = "((1*x)^2+(1*y)^2)^0.5=z+0";

        Calculator calc = new Calculator();
        
        double interval = 10;
        double domainStart = -300;
        double domainEnd = 300;



        double[][] result1 = calc.range(sphere, interval, domainStart, domainEnd, "");
    }
}
