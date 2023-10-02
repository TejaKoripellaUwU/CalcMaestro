import java.util.ArrayList;

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

    public double[] range(String expression, double interval, double domainStart, double domainEnd){
        
        
        return null;
    }
}
