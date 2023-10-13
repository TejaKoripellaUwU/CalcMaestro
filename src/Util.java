public class Util {

    static double[][] rotationMatrix = new double[3][3];

    static double[][] xMatrix = new double[3][3];
    static double[][] zMatrix = new double[3][3];
    // static double[][] yMatrix = new double[3][3]

    public static double[] rotate(double[] a, double yaw, double pitch, double roll){
        rotationMatrix[0][0] = Math.cos(pitch) * Math.cos(roll);
        rotationMatrix[0][1] = Math.sin(yaw) * Math.sin(pitch) * Math.cos(roll) - Math.cos(yaw) * Math.sin(roll);
        rotationMatrix[0][2] = Math.cos(yaw) * Math.sin(pitch) * Math.cos(roll) + Math.sin(yaw) * Math.sin(roll);

        rotationMatrix[1][0] = Math.cos(pitch) * Math.sin(roll);
        rotationMatrix[1][1] = Math.sin(yaw) * Math.sin(pitch) * Math.sin(roll) + Math.cos(yaw) * Math.cos(roll);
        rotationMatrix[1][2] = Math.cos(yaw) * Math.sin(pitch) * Math.sin(roll) - Math.sin(yaw) * Math.cos(roll);

        rotationMatrix[2][0] = -Math.sin(pitch);
        rotationMatrix[2][1] = Math.sin(yaw) * Math.cos(pitch);
        rotationMatrix[2][2] = Math.cos(yaw) * Math.cos(pitch);

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

        //result = mulMatrix(rotationMatrix, a);

        return result;
    }

    public static double[] mulMatrix(double[][] a, double[] b){
        double[] result = new double[3];
        result[0] = a[0][0] * b[0] + a[0][1] * b[1] + a[0][2] * b[2];
        result[1] = a[1][0] * b[0] + a[1][1] * b[1] + a[1][2] * b[2];
        result[2] = a[2][0] * b[0] + a[2][1] * b[1] + a[2][2] * b[2];
        return result;
    }

    public static double[][] mulMatrix(double[][] a, double[][] b){
        double[][] result = new double[a.length][b[0].length];
        for(int i = 0; i < a.length; i++){
            double ans = 0;
            for (int j = 0; j < b[0].length; j++){
                for(int k = 0; k < b.length; k++){
                    ans += a[i][k] * b[k][j];
                }
                result[i][j] = ans;
            }
        }
        return result;
    }
}
