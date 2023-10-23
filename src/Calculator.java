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


    public double[][] range(String expression, String mode) throws Exception {
        
        if(!expression.contains("=")){
            throw new Exception("missing equals");
        }


        double acceptedTolerance = .01;
        
        boolean x, y, z; x=false; y=false; z=false;

        if(expression.contains("x")){
            x = true;
        }
        if(expression.contains("y")){
            y = true;
        }
        if(expression.contains("z")){
            z = true;
        }
        

        ArrayList<ArrayList<Double>> resultList = new ArrayList<>();
        resultList.add(new ArrayList<>());
        resultList.add(new ArrayList<>());
        resultList.add(new ArrayList<>());

        //add +0 to each side to ensure it can detect the variables even if there are no operators
        expression = expression + "+0";
        int equalsIndex = expression.indexOf("=");
        expression = expression.substring(0, equalsIndex) + "+0" + expression.substring(equalsIndex);

        String coded = parser.findNumbers(expression);
        System.out.println(coded);

        String rightSide = "";
        String leftSide = "";
        leftSide = coded;

        
        leftSide = coded.substring(0, coded.indexOf("="));
        rightSide = coded.substring(coded.indexOf("=")+1);
        

        int ogsize = Engine.numbers.size();
        
        ArrayList<Callable<Double>> leftseq = new ArrayList<>();
        parser.pemdasSimplify(leftSide, leftseq);

        ArrayList<Callable<Double>> rightSeq = new ArrayList<>();
        parser.pemdasSimplify(rightSide, rightSeq);

        Engine.numbers = new ArrayList<Double>(Engine.numbers.subList(0, ogsize));

        double lr = .1;

        double diff = 1 / lr;
        double jrate = Params.interval;


        double iInterval = Params.interval;
        double kInterval = Params.interval;


        double xDomainStart = Params.domainStart;
        double zDomainStart = Params.domainStart;

        double xDomainEnd = Params.domainEnd;
        double zDomainEnd = Params.domainEnd;
        

        if(!x){
            xDomainEnd = 0;
            xDomainStart = 0;
        }

        if(!z){
            zDomainEnd = 0;
            zDomainStart = 0;
        }

        for (double i = xDomainStart; i <= xDomainEnd; i+=iInterval){
            for (double k = zDomainStart; k <= zDomainEnd; k+=kInterval){

                diff = 1;
                jrate = Params.interval;

                for(double j = Params.domainStart; j < 0; j+=jrate){
                    double leftans = 0;
                    double rightans = 0;
                    Engine.numbers = new ArrayList<Double>(Engine.numbers.subList(0, ogsize));

                    for(int index : Engine.xIndices){
                        Engine.numbers.set(index, i); //i
                    }
                    for(int index : Engine.zIndices){
                        Engine.numbers.set(index, k); //k
                    }
                    for(int index : Engine.yIndices){
                        Engine.numbers.set(index, j); //j
                    }
                    for(Callable<Double> func : leftseq){
                        leftans = func.call();
                    }
                    for(Callable<Double> func : rightSeq){
                        rightans = func.call();
                    }

                    diff = Math.abs(rightans - leftans);
                    jrate = diff*Params.learningRate;

                    //jrate = Params.interval;

                    if(jrate > Params.interval){
                        jrate = Params.interval;
                    }
                    //jrate = 1;
                    if(diff <= acceptedTolerance){

                        resultList.get(0).add(i);
                        resultList.get(1).add(k);
                        resultList.get(2).add(j);
                        jrate = Params.interval;
                        break;
                    }
                } 


                for(double j = Params.domainEnd; j >= 0; j-=jrate){
                    double leftans = 0;
                    double rightans = 0;
                    Engine.numbers = new ArrayList<Double>(Engine.numbers.subList(0, ogsize));

                    for(int index : Engine.xIndices){
                        Engine.numbers.set(index, i); //i
                    }
                    for(int index : Engine.zIndices){
                        Engine.numbers.set(index, k); //k
                    }
                    for(int index : Engine.yIndices){
                        Engine.numbers.set(index, j); //j
                    }
                    for(Callable<Double> func : leftseq){
                        leftans = func.call();
                    }
                    for(Callable<Double> func : rightSeq){
                        rightans = func.call();
                    }

                    diff = Math.abs(rightans - leftans);
                    jrate = diff*Params.learningRate;

                    //jrate = Params.interval;

                    if(jrate > Params.interval){
                        jrate = Params.interval;
                    }
                    //jrate = 1;
                    if(diff <= acceptedTolerance){

                        resultList.get(0).add(i);
                        resultList.get(1).add(k);
                        resultList.get(2).add(j);
                        jrate = Params.interval;
                        break;
                    }

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
