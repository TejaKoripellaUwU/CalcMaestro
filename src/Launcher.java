import javax.swing.SwingUtilities;
public class Launcher {

    public static void main(String[] args) {
        //thread safety
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                Engine.init();
                Calculator calc = new Calculator();
                MainCalc main = new MainCalc(calc);
                main.show();
            }
        });
    }

    
}
