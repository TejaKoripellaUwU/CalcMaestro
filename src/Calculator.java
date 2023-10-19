import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
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
        // int ogsize = Engine.numbers.size();
        ArrayList<Callable<Double>> seq = new ArrayList<>();
        parser.pemdasSimplify(coded, seq);
        //ArrayList<Double> og = (ArrayList<Double>) Engine.numbers.clone();

        for (double i = domainStart; i <= domainEnd; i+=interval){
            double ans = 0;
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
}
