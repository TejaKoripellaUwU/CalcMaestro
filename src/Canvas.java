import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class Canvas extends JFrame{
    double[][] points;
    double domainEnd, domainStart;

    static double[] angle = new double[3];

    public Canvas(double[][] points, double domainStart, double domainEnd){
        this.points = points;
        this.domainStart = domainStart;
        this.domainEnd = domainEnd;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 1000);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void paint(Graphics g){
        Graphics2D g2D = (Graphics2D) g;
        for(int i = 0; i < this.points.length; i++){
            for(int k = 0; k < this.points.length; k++){
                double[] p = Util.rotate(new double[]{i+domainStart, k+domainStart, this.points[i][k]}, 0 * (Math.PI/180), 0 * (Math.PI/180), angle[2] * (Math.PI/180));
                //System.out.println(p[0] + " " + p[1] + " " + p[2]);
                //System.out.println(i + " " + k + " " + points[i][k]);
                g2D.setColor(new Color((int)(i/400*255), 0, (int)(k/400.0*255), 150));
            
                g2D.drawLine((int)p[0]+500, 1000 - (int) p[2], (int)p[0]+500, 1000 - (int) p[2]);
            }
            //g2D.drawLine((int)((double)i/this.points.size()*500), 500 - this.points.get(i).intValue(), (int)((double)i/this.points.size()*500), 500 - this.points.get(i).intValue());
        }
        g2D.clearRect(0, 0, 1000, 1000);
        angle[2] += 15;
        repaint();
    }

    
}
