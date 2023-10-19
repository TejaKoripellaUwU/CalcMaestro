import javax.swing.JButton;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainCalc{
    private JFrame window;
    private String current_expression = "";
    private GridBagLayout GridBagLayoutGrid;
    private GridBagConstraints gbc;
    private JPanel numPanel = new JPanel();
    private Calculator calc;

    public MainCalc(Calculator c){
        calc = c;
        window = new JFrame("Calculator");
        window.setSize(1000, 800);  
        window.setPreferredSize(window.getSize()); 
        GridBagLayout layout = new GridBagLayout();
        gbc = new GridBagConstraints();    
        window.setLayout(layout);  
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  
        decorator();
        window.setVisible(false);
        }

    public void show(){
        window.setVisible(!(window.isVisible()));  
    }
    public static class Handler {
        private String curEquation;
        
        public ActionListener addString(String eq){
            return (ActionEvent e)->{
                curEquation += eq;
                System.out.println(curEquation)
            }
        }
        public ActionListener returnResult(){
            return (ActionEvent e)->{System.out.println(calc.answer())};
        }
    }

    private void decorator(){
        String[] ops= {"+","-","*","/"};
        for (int i = 0; i<ops.length;i++){
            gbc.gridx = i%4;
            gbc.gridy = (int) i/4;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            JButton button = new JButton(ops[i]);
            button.addActionListener(Handler.addString(ops[i]));
            window.add(button);

        }
        for (int i = 4; i<13;i++){
            gbc.gridx = i%4;
            gbc.gridy = (int) i/4;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            JButton button = new JButton(Integer.toString(i-3));
            button.addActionListener(Handler.addString(Integer.toString(i-3)));
            window.add(button,gbc);
        }

 

    }
}
