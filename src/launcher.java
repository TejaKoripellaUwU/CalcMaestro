import javax.swing.SwingUtilities;
public class Launcher {

    public static void main(String[] args) {
        //thread safety
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                MainCalc main = new MainCalc(new Calculator());
                main.show();
            }
        });
    }

    
}
