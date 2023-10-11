import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class Canvas extends JFrame{
    ArrayList<Double> points;
    public Canvas(ArrayList<Double> points){
        this.points = points;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void paint(Graphics g){
        Graphics2D g2D = (Graphics2D) g;
        for(int i = 0; i < this.points.size(); i++){
            g2D.drawLine((int)((double)i/this.points.size()*500), 500 - this.points.get(i).intValue(), (int)((double)i/this.points.size()*500), 500 - this.points.get(i).intValue());
        }
    }
}
