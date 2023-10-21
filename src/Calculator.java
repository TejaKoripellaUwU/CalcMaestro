import java.util.ArrayList;
import java.util.concurrent.Callable;

public class Calculator {

    Parser parser;
    public Calculator(){
        this.parser = new Parser();
    }

    public Double answer(String expression){
        Engine.numbers.clear();
        String coded = parser.findNumbers(expression);
        String codedAnswer = parser.pemdasSimplify(coded, null);
        return Engine.numbers.get(Parser.charToInt(codedAnswer.charAt(0)));
    }

    public ArrayList<Double> range2d(String expression, double interval, double domainStart, double domainEnd) throws Exception{
        ArrayList<Double> result = new ArrayList<>();
        String coded = parser.findNumbers(expression);
        int ogsize = Engine.numbers.size();
        ArrayList<Callable<Double>> seq = new ArrayList<>();
        parser.pemdasSimplify(coded, seq);
        Engine.numbers = new ArrayList<Double>(Engine.numbers.subList(0, ogsize));

        for (double i = domainStart; i <= domainEnd; i+=interval){
            double ans = 0;
            Engine.numbers = new ArrayList<Double>(Engine.numbers.subList(0, ogsize));
            
            for(int index : Engine.xIndices){
                Engine.numbers.set(index, i);
            }
            for (Callable<Double> func : seq){
                ans = func.call();
                
            }
            result.add(ans);
        }

        return result;
    }

    public double[][] range3d(String expression, double interval, double domainStart, double domainEnd, String mode) throws Exception {
        double acceptedTolerance = .1;
        

        
        int size = (int) ((domainEnd - domainStart) / interval) + 1;

        //double[][] result = new double[3][1];

        ArrayList<ArrayList<Double>> resultList = new ArrayList<>();
        resultList.add(new ArrayList<>());
        resultList.add(new ArrayList<>());
        resultList.add(new ArrayList<>());
        
        String coded = parser.findNumbers(expression);
        System.out.println(coded);

        String rightSide = "";
        String leftSide = "";
        leftSide = coded;
        if(coded.contains("=")){
            leftSide = coded.substring(0, coded.indexOf("="));
            rightSide = coded.substring(coded.indexOf("=")+1);
        }

        int ogsize = Engine.numbers.size();
        
        ArrayList<Callable<Double>> leftseq = new ArrayList<>();
        parser.pemdasSimplify(leftSide, leftseq);

        ArrayList<Callable<Double>> rightSeq = new ArrayList<>();
        parser.pemdasSimplify(rightSide, rightSeq);

        Engine.numbers = new ArrayList<Double>(Engine.numbers.subList(0, ogsize));

        double lr = .1;

        double diff = 1 / lr;
        double jrate = interval;

        double prevLeft = 0;
        double prevRight = 0;

        for (double i = domainStart; i <= domainEnd; i+=interval){
            for (double k = domainStart; k <= domainEnd; k+=interval){

                diff = 1;
                jrate = interval;
                prevLeft = 0;
                prevRight = 0;

                for(double j = domainStart; j <= domainEnd; j+=jrate){

                    

                    double leftans = 0;
                    double rightans = 0;
                    Engine.numbers = new ArrayList<Double>(Engine.numbers.subList(0, ogsize));

                    for(int index : Engine.xIndices){
                        Engine.numbers.set(index, i); //i
                    }
                    for(int l : Engine.zIndices){
                        Engine.numbers.set(l, k); //k
                    }
                    for(int l : Engine.yIndices){
                        Engine.numbers.set(l, j);
                    }
                    for(Callable<Double> func : leftseq){
                        leftans = func.call();
                    }
                    for(Callable<Double> func : rightSeq){
                        rightans = func.call();
                    }



                

                    diff = Math.abs(rightans - leftans);
                    jrate = diff*.001;
        


                    //System.out.println(jrate);

                    //jrate = 1;
                    if(diff <= acceptedTolerance){
                        resultList.get(0).add(i);
                        resultList.get(1).add(k);
                        resultList.get(2).add(j);
                        jrate = interval;
                    }
                    // jrate = interval;
                    // if(jrate < interval/10){
                    //     jrate = interval/10;
                    // }

                    // if(jrate <= acceptedTolerance ){
                    //     jrate = interval;
                    // }
                    //System.out.println(jrate);
                    //System.out.println(jrate);
                    prevLeft = leftans;
                    prevRight = rightans;
                } 
            }
        }
        double[][] result = new double[3][resultList.get(0).size()];
        for(int i = 0; i < 3; i++){
            for (int j = 0; j < result[0].length; j++){
                result[i][j] = resultList.get(i).get(j);
            }
        }
        resultList.clear();
        System.gc();
        // System.out.println(resultList);
        System.out.println("NumPoints " + result[0].length);
        return result;
    }
}
