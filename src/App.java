import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import javax.print.attribute.standard.ColorSupported;


public class App{
    public static void main(String[] args) throws Exception {
        Engine.init();
        Util.init();
        Calculator calc = new Calculator();
        
        
        double interval = 1;

        double[][] result = calc.range3d("sqrt(150^2-x^2-z^2)", interval, -200, 200);

        double[][] done = Util.mulMatrix(Util.xMatrix, result);

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
        canvas.realtimeGraph(result, interval, -200, 200);
    }
}
