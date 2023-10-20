import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Canvas extends JFrame implements ActionListener{
    double[][] points;
    double domainEnd, domainStart;

    static double[] angle = new double[3];

    double range = 0;
    double min = 0;

    double interval = 1;

    double inputPoints = 1;


    int prevMouseX = 0;
    int prevMouseY = 0;

    Timer timer;

    boolean painting = false;

    public Canvas(){

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 1000);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

       timer = new Timer(200, this);
       
    }


    public void paint(Graphics g){
        Graphics2D g2D = (Graphics2D) g;
        g2D.clearRect(0, 0, 1000, 1000);

        if (this.points == null){
            painting = false;
            return; 
        }
            

        painting = true;

        //draw axis
        double[] x = Util.rotate(new double[]{200, 0, 0}, angle[1]*(Math.PI/180), -angle[1] * (Math.PI/180), -angle[2] * (Math.PI/180));
        g2D.setColor(new Color(0, 0, 255, 255));
        g2D.drawLine((int)0+500, 1000 - ((int) 0+500), (int)x[0]+500, 1000 - ((int) x[2]+500));

        //draw axis
        double[] y = Util.rotate(new double[]{0, 200, 0}, angle[1]*(Math.PI/180), -angle[1] * (Math.PI/180), -angle[2] * (Math.PI/180));
        g2D.setColor(new Color(255, 0, 0, 255));
        g2D.drawLine((int)0+500, 1000 - ((int) 0+500), (int)y[0]+500, 1000 - ((int) y[2]+500));

        //draw axis
        double[] z = Util.rotate(new double[]{0, 0, 200}, angle[1]*(Math.PI/180), -angle[1] * (Math.PI/180), -angle[2] * (Math.PI/180));
        g2D.setColor(new Color(0, 255, 0, 255));
        g2D.drawLine((int)0+500, 1000 - ((int) 0+500), (int)z[0]+500, 1000 - ((int) z[2]+500));

        for(int i = 0; i < this.points.length; i++){
            for(int k = 0; k < this.points.length; k++){
                double[] p = Util.rotate(new double[]{i*interval+domainStart, k*interval+domainStart, this.points[i][k]}, angle[1]*(Math.PI/180), -angle[1] * (Math.PI/180), -angle[2] * (Math.PI/180));
                
                double alpha = 1;
                g2D.setColor(new Color((int)(i/inputPoints*255*alpha), (int)((this.points[i][k]-this.min)/range*255*alpha), (int)(k/inputPoints*255*alpha), 100));
                //g2D.drawLine((int)p[0]+500, 1000 - ((int) p[2] + 500), (int)p[0]+500, 1000 - ((int) p[2] + 500));

                g2D.drawRect((int)p[0]+500, 1000 - ((int) p[2] + 500), 3, 3);
            }
        }

        prevMouseX = MouseInfo.getPointerInfo().getLocation().x;
        angle[2] = (MouseInfo.getPointerInfo().getLocation().x - 500) / 1000.0 * 360;
        prevMouseY = MouseInfo.getPointerInfo().getLocation().y;
        angle[1] = (MouseInfo.getPointerInfo().getLocation().y - 500) / 1000.0 * 360;

        this.repaint();
    }

    public void realtimeGraph(double[][] points, double interval, double domainStart, double domainEnd) throws InterruptedException{
        this.points = points;
        this.interval = interval;
        this.domainStart = domainStart;
        this.domainEnd = domainEnd;

        this.inputPoints = ((domainEnd - domainStart) / this.interval) + 1;

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
        this.repaint();
        // while(true){
        //     //Thread.sleep(100);

        //     if(prevMouseX != MouseInfo.getPointerInfo().getLocation().x || prevMouseY != MouseInfo.getPointerInfo().getLocation().y){

        //         // this.repaint();
        //     }
            
        // }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        this.repaint();
    }
}
