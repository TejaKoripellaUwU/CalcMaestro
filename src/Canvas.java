import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.*;

public class Canvas extends JFrame{
    double[][] points;
    double domainEnd, domainStart;

    static double[] angle = new double[3];

    double range = 0;
    double min = 0;

    double interval = 1;

    double inputPoints = 1;

    public Canvas(double[][] points, double interval, double domainStart, double domainEnd){
        this.points = points;
        this.interval = interval;

        inputPoints = ((domainEnd - domainStart) / interval) + 1;

        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;

        for(int i = 0; i < points.length; i++){
            for(int k = 0; k < points.length; k++){
                if(points[i][k] > max){
                    max = points[i][k];
                }
                if(points[i][k] < min){
                    min = points[i][k];
                }
            }
        }

        this.min = min;
        this.range = max - min;



        this.domainStart = domainStart;
        this.domainEnd = domainEnd;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 1000);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void paintagain(){
        this.repaint();
    }

    public void paint(Graphics g){
        Graphics2D g2D = (Graphics2D) g;
        g2D.clearRect(0, 0, 1000, 1000);
        for(int i = 0; i < this.points.length; i++){
            for(int k = 0; k < this.points.length; k++){
                double[] p = Util.rotate(new double[]{i*interval+domainStart, k*interval+domainStart, this.points[i][k]}, angle[1] * (Math.PI/180), -angle[1] * (Math.PI/180), -angle[2] * (Math.PI/180));
                //double alpha = (this.points[i][k]-min)/range;
                
                double alpha = 1;
                g2D.setColor(new Color((int)(i/inputPoints*255*alpha), (int)((this.points[i][k]-min)/range*255*alpha), (int)(k/inputPoints*255*alpha), 100));
            
                g2D.drawLine((int)p[0]+500, 1000 - (int) p[2], (int)p[0]+500, 1000 - (int) p[2]);
            }
            //g2D.drawLine((int)((double)i/this.points.size()*500), 500 - this.points.get(i).intValue(), (int)((double)i/this.points.size()*500), 500 - this.points.get(i).intValue());
        }
        angle[2] = (MouseInfo.getPointerInfo().getLocation().x - 500) / 1000.0 * 180;
        angle[1] = (MouseInfo.getPointerInfo().getLocation().y - 500) / 1000.0 * 180;


    }


}
