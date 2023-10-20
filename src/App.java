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
        
        double interval = 1;
        double domainStart = -200;
        double domainEnd = 200;
        double[][] result = calc.range3d("sin(.1*x)*cos(.1*z)*25", interval, domainStart, domainEnd);
        // double[][] done = new double[1][1];
        // System.out.println("Starting Fast!");
        // long startTime = System.currentTimeMillis();
        // done = Util.mulMatrixFast(Util.xMatrix, result);
        // long endTime = System.currentTimeMillis();
        // System.out.println("Fast Done | Starting Slow! " + (endTime - startTime));
        // startTime = System.currentTimeMillis();
        // done = Util.mulMatrix(Util.xMatrix, result);
        // endTime = System.currentTimeMillis();
        // System.out.println("Slow Done " + (endTime - startTime));

        //System.out.println(don);

        // System.out.println(result.length + " " + result[0].length);
        // for (int i = 0; i < Util.xMatrix.length; i++){
        //     for (int j = 0; j < Util.xMatrix[0].length; j++){
        //         System.out.print((int) Util.xMatrix[i][j] + " ");   
        //     }
        //     System.out.println();
        // }
        // System.out.println();
        // for (int i = 0; i < result.length; i++){
        //     for (int j = 0; j < result[0].length; j++){
        //         System.out.print((int) result[i][j] + " ");   
        //     }
        //     System.out.println();
        // }
        // System.out.println();
        // for (int i = 0; i < done.length; i++){
        //     for (int j = 0; j < done[0].length; j++){
        //         System.out.print((int) done[i][j] + " ");   
        //     }
        //     System.out.println();
        // }


        Canvas canvas = new Canvas();
        canvas.realtimeGraph(result, interval, domainStart, domainEnd);
    }
}
