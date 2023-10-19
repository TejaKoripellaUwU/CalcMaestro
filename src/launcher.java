import javax.swing.SwingUtilities;
public class Launcher {

    public static void main(String[] args) {
        //thread safety
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                UI.MainCalc main = new UI.MainCalc();
                main.show();
            }
        });
    }

    
}
