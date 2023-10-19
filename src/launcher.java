import javax.swing.SwingUtilities;
public class launcher {

    public static void main(String[] args) {
        //thread safety
        Calculator calc = new Calculator();
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                UI.MainCalc main = new UI.MainCalc();
                main.show();
            }
        });
    }

    
}
