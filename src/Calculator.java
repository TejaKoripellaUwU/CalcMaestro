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
            
            for(int index : Engine.variableIndices){
                Engine.numbers.set(index, i);
            }
            for (Callable<Double> func : seq){
                ans = func.call();
                
            }
            result.add(ans);
        }

        return result;
    }

    public double[][] range3d(String expression, double interval, double domainStart, double domainEnd) throws Exception {
        int size = (int) (domainEnd - domainStart + 1) / (int) interval;
        double[][] result = new double[size][size];
        String coded = parser.findNumbers(expression);

        int ogsize = Engine.numbers.size();
        ArrayList<Callable<Double>> seq = new ArrayList<>();
        parser.pemdasSimplify(coded, seq);

        Engine.numbers = new ArrayList<Double>(Engine.numbers.subList(0, ogsize));

        int outerIndex = 0;
        for (double i = domainStart; i <= domainEnd; i+=interval){
            int innerIndex = 0;
            for (double k = domainStart; k <= domainEnd; k+=interval){
                double ans = 0;
                Engine.numbers = new ArrayList<Double>(Engine.numbers.subList(0, ogsize));

                for(int index : Engine.xIndices){
                    Engine.numbers.set(index, i);
                }
                for(int l : Engine.zIndices){
                    Engine.numbers.set(l, k);
                }
                for(Callable<Double> func : seq){
                    ans = func.call();
                }
                result[outerIndex][innerIndex] = ans;
                innerIndex += 1;
            }
            outerIndex += 1;
        }

        return result;
    }
}
