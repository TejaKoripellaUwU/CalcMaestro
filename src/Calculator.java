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
    private String[] function_list = {".","^","(",")","sqrt","abs","sin","cos","tan","arcsin","arcos","arctan","ln","x","y","z","=",}; // contains almost all funcs to be made into buttons
    private JFrame window;  
    private String current_expression = "";
    private GridBagLayout GridBagLayoutGrid;
    private GridBagConstraints gbc;
    private JPanel numPanel = new JPanel();
    private static Calculator calc; //calculator object to accept input and solve problems
    private static boolean is_graphing = false;
    private static ArrayList<String> hist = new ArrayList<String>();
    private static int history_iter = 0;
    public static Canvas canvas; // main canvad for graphing 

    public MainCalc(Calculator c){ 
        calc = c;
        canvas.is3DPlane = false; //sets mode for graphing 
        System.out.println(calc.answer("1+1"));
        System.out.println("test");
        window = new JFrame("Calculator"); //creates main container 
        window.setSize(1000, 800);  
        window.setPreferredSize(window.getSize()); 
        GridBagLayout layout = new GridBagLayout(); //creates layout manager for main container 
        gbc = new GridBagConstraints();    
        window.setLayout(layout);  
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  
        decorator();
        window.getContentPane().setBackground(Color.lightGray);
        window.setVisible(false);
        }

    public void show(){
        window.setVisible(!(window.isVisible()));  //used to launch project from launcher.java
    }
    public static class Handler {  //creates ActionListeners to be binded to buttons, giving them functionalties
        private static String curEquation = ""; //tracks current user input to be given to calc
        

        public static ActionListener rmString( JTextArea ans){ 
            return (ActionEvent e)->{
                if((!(curEquation.equals(""))) && (!(ans.getText().contains("----")) )){
                    curEquation = curEquation.substring(0, curEquation.length() -1);
                    ans.setText(curEquation);

                }
            };
        }

        public static ActionListener clrString( JTextArea ans){
            return (ActionEvent e)->{
                    ans.setText("");
                    curEquation = "";
                    if(is_graphing){
                        canvas.points = null;
                    }
                };
            };
        

        public static ActionListener addString(String eq, JTextArea ans){
            return (ActionEvent e)->{
                if((ans.getText().equals( "Error, please check expression") || (ans.getText().contains("----")))){
                    ans.setText(null);
                }
                curEquation += eq;
                ans.append(eq);
                System.out.println(curEquation);
            };
        }
        public static ActionListener returnResult(JTextArea ans, JTextArea mem ){
            
            System.out.println(curEquation);
            return (ActionEvent e)->{
                if (is_graphing == true){
                    try{
                        double[][] result = calc.range(curEquation, "");
                        canvas.setGraph(result, Params.interval);
                    }
                   

                    catch(Exception IndexOutOfBoundsException){
                        ans.setText("Error, please check graph equation.");
                        curEquation = "";
                    }
                }
                
                else{
                    try{
                        double calculation = calc.answer(curEquation);
                        hist.add(String.valueOf(calculation));
                        ans.append("\n" + "----\n" + calculation ); // this block of code handles displaying output in the answer field
                        if (history_iter < 12){
                            mem.append(curEquation + "=" + calculation + "\n");
                            history_iter = history_iter + 1;
                        }
                        else{
                            int new_index = mem.getText().indexOf('\n');
                            String result = mem.getText().substring(new_index + 1);
                            mem.setText(result);
                            mem.append(curEquation + "=" + calculation + "\n");

                        }
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

    

    private void decorator() { //adds all major elements to ui, divides them in panels, and uses handler() to give them functions  
        String[] ops = { "+", "-", "*", "/" };
    
        JPanel mainPanel = new JPanel(new GridBagLayout()); // main panel for elements
        GridBagConstraints main_constr = new GridBagConstraints(); //sub layout manager for elements
    
        JToggleButton modeSwitch = new JToggleButton("Normal Mode");
        JToggleButton modeSwitch2 = new JToggleButton("2D Graphing");
        modeSwitch.addItemListener(e -> { //sets whether graphing is on on or not
            is_graphing = modeSwitch.isSelected();
            if (!is_graphing) { 
                modeSwitch2.setVisible(false);
                canvas.setVisible(false);
                canvas.dispose();
                canvas = null;
                modeSwitch.setText("Normal Mode");
            } 
            else {
                canvas = new Canvas(); 
                modeSwitch2.setVisible(true);
                modeSwitch.setText("Graph Mode");

            }
        });
    
        main_constr.gridx = 0;
        main_constr.gridy = 0;   
        main_constr.insets = new Insets(5, 5, 5, 5);
        main_constr.anchor = GridBagConstraints.EAST;
        mainPanel.add(modeSwitch, main_constr);
   
    
        main_constr.gridx = 0;
        main_constr.anchor = GridBagConstraints.WEST;
        mainPanel.add(modeSwitch2, main_constr);
        modeSwitch2.setVisible(false);

        modeSwitch2.addItemListener(e ->{ //sets whether graphing 2d or 3d
            canvas.is3DPlane = modeSwitch2.isSelected();
            if (!canvas.is3DPlane) {
                canvas.angle = new double[3];
                System.out.println("e pluribus unum");
                modeSwitch2.setText("2D Graphing");
            } 
            else {
                modeSwitch2.setText("3D Graphing");
            }
        });



        JTextArea answer = new JTextArea(3, 20);  //creates answer field 
        answer.setLineWrap(true);
        answer.setWrapStyleWord(true);
        answer.setFont(new Font("Arial", Font.BOLD, 30));
        answer.setEditable(false);
    
        main_constr.gridx = 0;
        main_constr.gridy = 1;
        main_constr.anchor = GridBagConstraints.CENTER;
        mainPanel.add(answer, main_constr);
    
        JPanel op_panel = new JPanel(new GridLayout(1, 4, 5, 5)); //creates ops buttons 
        for (String op : ops) {
            JButton button = new JButton(op);
            button.addActionListener(Handler.addString(op, answer));
            button.setBackground(new Color(216, 239, 200));
            button.setPreferredSize(new Dimension(100, 50));
            op_panel.add(button);
        }
    
        main_constr.gridx = 0;
        main_constr.gridy = 2;
        main_constr.anchor = GridBagConstraints.CENTER;
        mainPanel.add(op_panel, main_constr);
    
        JPanel num_panel = new JPanel(new GridLayout(5, 6, 5, 5));  //creates num panels
        for (int i = 0; i <= 26; i++) {
            JButton button;
            if (i <= 9) { //creates ints 
                button = new JButton(Integer.toString(i));
                button.addActionListener(Handler.addString(Integer.toString(i), answer));
            } 
            else if (9<i && i<=26){ //creates funcs 
                button = new JButton((function_list[(i-10)]));
                button.addActionListener(Handler.addString((function_list[(i-10)]), answer));
                
            }
            else{
                button = new JButton(); //creates empty buttons 
            }
            button.setBackground(new Color(200, 235, 239));
            button.setPreferredSize(new Dimension(75, 40)); 
            num_panel.add(button);
        }
    
        JTextArea history = new JTextArea("History:\n", 10, 20); //creates history text area
        history.setFont(new Font("Arial", Font.PLAIN, 25));
        history.setEditable(false);
        



        JButton rm_button = new JButton("rm"); //creates removal button 
        rm_button.addActionListener(Handler.rmString(answer));
        rm_button.setBackground(new Color(216, 239, 200));
        rm_button.setPreferredSize(new Dimension(75, 40)); 
        num_panel.add(rm_button);

        JButton clr_button = new JButton("clr"); //creates clear button 
        clr_button.addActionListener(Handler.clrString(answer));
        clr_button.setBackground(new Color(216, 239, 200));
        clr_button.setPreferredSize(new Dimension(75, 40)); 
        num_panel.add(clr_button);
        
        JButton ans_button = new JButton("ans"); //creates answer button
        ans_button.addActionListener(Handler.returnResult(answer, history));
        ans_button.setBackground(new Color(216, 239, 200));
        ans_button.setPreferredSize(new Dimension(75, 40)); 
        num_panel.add(ans_button);
    
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

