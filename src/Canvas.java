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
    double domain;

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

        //draw x axis Blue
        double[] x = Util.rotate(new double[]{200, 0, 0}, angle[1]*(Math.PI/180), -angle[1] * (Math.PI/180), -angle[2] * (Math.PI/180));
        g2D.setColor(new Color(0, 0, 255, 255));
        g2D.drawLine((int)0+500, 1000 - ((int) 0+500), (int)x[0]+500, 1000 - ((int) x[2]+500));

        //draw z axis Red
        double[] z = Util.rotate(new double[]{0, 200, 0}, angle[1]*(Math.PI/180), -angle[1] * (Math.PI/180), -angle[2] * (Math.PI/180));
        g2D.setColor(new Color(255, 0, 0, 255));
        g2D.drawLine((int)0+500, 1000 - ((int) 0+500), (int)z[0]+500, 1000 - ((int) z[2]+500));

        //draw y axis Green
        double[] y = Util.rotate(new double[]{0, 0, 200}, angle[1]*(Math.PI/180), -angle[1] * (Math.PI/180), -angle[2] * (Math.PI/180));
        g2D.setColor(new Color(0, 255, 0, 255));
        g2D.drawLine((int)0+500, 1000 - ((int) 0+500), (int)y[0]+500, 1000 - ((int) y[2]+500));

        double[][] p = Util.rotate(this.points, angle[1]*(Math.PI/180), -angle[1] * (Math.PI/180), -angle[2] * (Math.PI/180));


        for(int i = 0; i < this.points.length; i++){
            for(int k = 0; k < this.points[0].length; k++){
                if (this.points[2][k] == 0.0)
                    continue;
                
                g2D.setColor(new Color((int)((this.points[1][k]-domainStart)/domain*255), (int)((this.points[2][k]+min)/range*255), (int)((this.points[2][k]-domainStart)/domain*255), 255));
                g2D.fillRect((int)p[0][k]+500, 1000 - ((int)p[2][k]+500), 3, 3);
            }
        }

    

        // for(int i = 0; i < this.points.length; i++){
        //     for(int k = 0; k < this.points[0].length; k++){
        //         if (this.points[2][k] == 0.0)
        //             continue;
                
        //         g2D.setColor(new Color((int)((this.points[1][k]-domainStart)/domain*255), (int)((this.points[2][k]+min)/range*255), (int)((this.points[2][k]-domainStart)/domain*255), 255));
        //         g2D.fillRect((int)points[0][k]+500, 1000 - ((int)points[2][k]+500), 3, 3);
        //     }
        // }

        if(true || prevMouseX != MouseInfo.getPointerInfo().getLocation().x || prevMouseY != MouseInfo.getPointerInfo().getLocation().y){
            prevMouseX = MouseInfo.getPointerInfo().getLocation().x;
            angle[2] = (MouseInfo.getPointerInfo().getLocation().x - 500) / 1000.0 * 360;
            prevMouseY = MouseInfo.getPointerInfo().getLocation().y;
            angle[1] = (MouseInfo.getPointerInfo().getLocation().y - 500) / 1000.0 * 360;
            this.repaint();
        }
    }

    public void realtimeGraph(double[][] points, double interval, double domainStart, double domainEnd) throws InterruptedException{
        this.points = points;
        this.interval = interval;
        this.domainStart = domainStart;
        this.domainEnd = domainEnd;
        this.domain = domainEnd - domainStart;
        this.inputPoints = this.points[0].length;

        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;

        for(int i = 0; i < points[0].length; i++){
            if(points[2][i] > max){
                max = points[2][i];
            }
            if(points[2][i] < min){
                min = points[2][i];
            }
        }

        this.min = min;
        this.range = max - min;

        System.out.println(this.range);
        this.repaint();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        this.repaint();
    }
}
