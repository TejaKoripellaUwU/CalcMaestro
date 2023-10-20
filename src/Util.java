public class Util {

    static double[][] xMatrix = new double[3][3];
    static double[][] zMatrix = new double[3][3];

    public static void init(){
        zMatrix[0][0] = Math.cos(0);
        zMatrix[0][1] = -Math.sin(0);
        zMatrix[1][0] = Math.sin(0);
        zMatrix[1][1] = Math.cos(0); 
        zMatrix[2][2] = 1;

        xMatrix[1][1] = Math.cos(0);
        xMatrix[1][2] = -Math.sin(0);
        xMatrix[2][1] = Math.sin(0);
        xMatrix[2][2] = Math.cos(0);
        xMatrix[0][0] = 1;
    }

    public static double[] rotate(double[] a, double yaw, double pitch, double roll){

        zMatrix[0][0] = Math.cos(roll);
        zMatrix[0][1] = -Math.sin(roll);
        zMatrix[1][0] = Math.sin(roll);
        zMatrix[1][1] = Math.cos(roll); 
        zMatrix[2][2] = 1;

        xMatrix[1][1] = Math.cos(pitch);
        xMatrix[1][2] = -Math.sin(pitch);
        xMatrix[2][1] = Math.sin(pitch);
        xMatrix[2][2] = Math.cos(pitch);
        xMatrix[0][0] = 1;

        double[] result = mulMatrix(zMatrix, a);
        result = mulMatrix(xMatrix, result);

        return result;
    }
    

    public static double[][] rotate(double[][] a, double yaw, double pitch, double roll){

        zMatrix[0][0] = Math.cos(roll);
        zMatrix[0][1] = -Math.sin(roll);
        zMatrix[1][0] = Math.sin(roll);
        zMatrix[1][1] = Math.cos(roll); 
        zMatrix[2][2] = 1;

        xMatrix[1][1] = Math.cos(pitch);
        xMatrix[1][2] = -Math.sin(pitch);
        xMatrix[2][1] = Math.sin(pitch);
        xMatrix[2][2] = Math.cos(pitch);
        xMatrix[0][0] = 1;

        double[][] result = mulMatrix(zMatrix, a);
        result = mulMatrix(xMatrix, result);

        return result;
    }



    public static double[] mulMatrix(double[][] a, double[] b){
        double[] result = new double[3];
        result[0] = a[0][0] * b[0] + a[0][1] * b[1] + a[0][2] * b[2];
        result[1] = a[1][0] * b[0] + a[1][1] * b[1] + a[1][2] * b[2];
        result[2] = a[2][0] * b[0] + a[2][1] * b[1] + a[2][2] * b[2];
        return result;
    }

    public static double[][] mulMatrix(double[][] rotator, double[][] tomul){
        
        double[][] result = new double[rotator.length][tomul[0].length];
        
        for(int i = 0; i < rotator.length; ++i){
            for(int j = 0; j < tomul[0].length; ++j){
                result[i][j] = rotator[i][0] * tomul[0][j] + rotator[i][1] * tomul[1][j] + rotator[i][2] * tomul[2][j];
            }
        }
        return result;
    }
}
