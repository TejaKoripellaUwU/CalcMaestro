package UI;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;

public class MainCalc extends JFrame{
    private JTextField txt;
    private GridBagLayout GridBagLayoutGrid;
    private GridBagConstraints gbc;

    public MainCalc(){
        super();
        GridBagLayoutGrid = new GridBagLayout();  
        gbc = new GridBagConstraints();  
        setLayout(GridBagLayoutGrid);  
        setTitle("Cuckulator");  

        GridBagLayout layout = new GridBagLayout();  
        this.setLayout(layout);  
        
        setSize(300, 300);  
        setPreferredSize(getSize());  
        setVisible(true);  
        setDefaultCloseOperation(EXIT_ON_CLOSE);  
        createNums();
        }
    private void createNums(){
        for (int i = 0; i<10;i++){
            gbc.gridx = i%3;
            gbc.gridy = (int) i/3;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            this.add(new JButton(Integer.toString(i)),gbc);
        }

    }
}
