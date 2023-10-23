public class Params {


    // parameters for drawing and solving
    public static double domainStart = -10;
    public static double domainEnd = 10;
    public static double domain = domainEnd - domainStart;
    public static double maxRange = 20;
    public static double drawScale = 30;

    public static int drawAlpha = 255; //anything lower than 255 kills the performance
    public static int splochDim = 1;


    // parameters for solving speed and accuracy
    public static double interval = .2;
    public static double learningRate = 0.01;


    //unused graph equations
    public static String sphere = "x^2+y^2+z^2=125^2";
    //public static String heart = "((.01*x)^2+(9/4)*((.01*y)^2)+(.01*z)^2-1)^3-((.01*x)^2)*((.01*z)^3)-(9/200)*((.01*y)^2)*((.01*z)^3)=0+0";
    public static String donut = "z^2+(200-sqrt(x^2+y^2))^2=100^2";
    public static String paraboloid = "(.1*x)^2+(.1*z)^2=y+0";
    public static String cone = "((1*x)^2+(1*y)^2)^0.5=z+0";


}
