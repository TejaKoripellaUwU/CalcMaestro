import javax.swing.JButton;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;

public class MainCalc{
    private JFrame window;
    private String current_expression = "";
    private GridBagLayout GridBagLayoutGrid;
    private GridBagConstraints gbc;
    private JPanel numPanel = new JPanel();
    private static Calculator calc;
    private static boolean is_graphing = false;
    private static ArrayList<String> hist = new ArrayList<String>();
    private static int history_iter = 1;
    public MainCalc(Calculator c){
        calc = c;
        System.out.println(calc.answer("1+1"));
        System.out.println("test");
        window = new JFrame("Calculator");
        window.setSize(1000, 800);  
        window.setPreferredSize(window.getSize()); 
        GridBagLayout layout = new GridBagLayout();
        gbc = new GridBagConstraints();    
        window.setLayout(layout);  
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  
        decorator();
        window.getContentPane().setBackground( Color.lightGray );
        window.setVisible(false);
        }

    public void show(){
        window.setVisible(!(window.isVisible()));  
    }
    public static class Handler {
        private static String curEquation = "";
        
        public static ActionListener addString(String eq, JTextArea ans){
            return (ActionEvent e)->{
                if((ans.getText().equals( "Error, please check expression") || (ans.getText().contains("-")))){
                    ans.setText(null);
                }
                curEquation += eq;
                ans.append(eq);
                System.out.println(curEquation);
            };
        }
        public static ActionListener returnResult(JTextArea ans, JTextArea mem ){
            
            System.out.println(curEquation);
            System.out.println('t');
            return (ActionEvent e)->{
                if (is_graphing == true){
                    
                }
                else{
                    try{
                        double calculation = calc.answer(curEquation);
                        hist.add(String.valueOf(calculation));
                        ans.append("\n" + "----\n" + calculation );
                        mem.append(curEquation + "=" + calculation + "\n");
                        curEquation = "";

                    }
                    catch(Exception IndexOutOfBoundsException){
                        ans.setText("Error, please check expression");
                        curEquation = "";

                        
                    }
                    
                }
                
                
               curEquation = "";
            };

        
        }
    }

    

    private void decorator() {
        String[] ops = { "+", "-", "*", "/" };
    
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints main_constr = new GridBagConstraints();
    
        JToggleButton modeSwitch = new JToggleButton("Normal Mode");
        modeSwitch.addItemListener(e -> {
            is_graphing = modeSwitch.isSelected(); 
            if (!is_graphing) {
                modeSwitch.setText("Normal Mode");
            } else {
                modeSwitch.setText("Graph Mode");
            }
        });
    
        main_constr.gridx = 0;
        main_constr.gridy = 0;
        main_constr.insets = new Insets(5, 5, 5, 5);
        main_constr.anchor = GridBagConstraints.EAST; 
        mainPanel.add(modeSwitch, main_constr);
    
       
        JTextArea answer = new JTextArea(3, 20); 
        answer.setLineWrap(true);
        answer.setWrapStyleWord(true);
        answer.setFont(new Font("Arial", Font.BOLD, 30));
        answer.setEditable(false);
    
        main_constr.gridx = 0;
        main_constr.gridy = 1;
        main_constr.anchor = GridBagConstraints.CENTER; 
        mainPanel.add(answer, main_constr);
    
     
        JPanel op_panel = new JPanel(new GridLayout(1, 4, 5, 5));
        for (String op : ops) {
            JButton button = new JButton(op);
            button.addActionListener(Handler.addString(op, answer));
            button.setBackground(new Color(216, 239, 200, 100)); 
            button.setPreferredSize(new Dimension(100, 50)); 
            op_panel.add(button);
        }
    
        main_constr.gridx = 0;
        main_constr.gridy = 2;
        main_constr.anchor = GridBagConstraints.CENTER;
        mainPanel.add(op_panel, main_constr);
    
       
        JPanel num_panel = new JPanel(new GridLayout(4, 3, 5, 5));
        for (int i = 1; i <= 9; i++) {
            JButton button = new JButton(Integer.toString(i));
            button.addActionListener(Handler.addString(Integer.toString(i), answer));
            button.setBackground(new Color(200, 235, 239, 120)); 
            button.setPreferredSize(new Dimension(100, 50)); 
            num_panel.add(button);
        }
        
       
        JTextArea history = new JTextArea("History:\n", 10, 20); 
        history.setFont(new Font("Arial", Font.PLAIN, 25)); 
        history.setEditable(false);

        JButton zero_button = new JButton("0");
        zero_button.addActionListener(Handler.addString("0", answer));
        zero_button.setBackground(new Color(200, 235, 239, 120)); 
        zero_button.setPreferredSize(new Dimension(100, 50)); 
        num_panel.add(zero_button);
    
        JButton equalButton = new JButton("=");
        equalButton.addActionListener(Handler.returnResult(answer,history));
        equalButton.setBackground(new Color(216, 239, 200, 100)); 
        equalButton.setPreferredSize(new Dimension(100, 50)); 
        num_panel.add(equalButton);
    
        main_constr.gridx = 0;
        main_constr.gridy = 3;
        main_constr.anchor = GridBagConstraints.WEST; 
        mainPanel.add(num_panel, main_constr);
    
       
    
        main_constr.gridx = 1;
        main_constr.gridy = 0;
        main_constr.gridheight = 4; 
        main_constr.anchor = GridBagConstraints.WEST; 
        main_constr.fill = GridBagConstraints.BOTH; 
        mainPanel.add(new JScrollPane(history), main_constr);
    
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH; 
        window.add(mainPanel, gbc);
    }
       // JTextArea history = new JTextArea(10,15);
        //history.setLineWrap(true);
        //history.setWrapStyleWord(true);
        //answer.setFont(new Font("Arial", Font.BOLD, 30));
        //answer.setEditable(false);

        //JScrollPane sp = new JScrollPane(history);

        //JButton button = new JButton("="); 
        //button.addActionListener(Handler.returnResult());
        //gbc.gridx += 1; 
        //window.add(button,gbc);
    //}
}

