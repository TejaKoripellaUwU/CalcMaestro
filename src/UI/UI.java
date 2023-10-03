import javax.swing.Action;
import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.event.AncestorListener;

public class UI implements ActionListener{
    public UI(Engine engy){
        JFrame frame = new JFrame();
        frame.setSize(500, 600);    

        JTextField input = new JTextField("Enter Equation");
        input.setBounds(200,200, 100, 30);
        input.addActionListener(this);
        frame.add(input);


        frame.setLayout(null);
        frame.setVisible(true);
        
    }
    public void actionPerformed(ActionEvent e){
        
    }
}
