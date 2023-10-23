import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Canvas extends JFrame implements ActionListener{
    public double[][] points;
    double[][] transformedPoints;

    double domainEnd, domainStart;
    double domain;

    static double[] angle = new double[3];

    double range = 0;
    double min = 0;

    double interval = 1;

    double inputPoints = 1;

    double fps = 0;
    long prevTime = 0;
    long time = 0;

    Image dbImage;
    Graphics dbGraphics;

    boolean is3DPlane = false;

    public Canvas(){

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 1000);
        this.setLocationRelativeTo(null);
        this.setVisible(true);


        this.repaint();

        transformedPoints = new double[1][1];
       
    }

    public void drawPoints(Graphics2D g2D){

        
        if(is3DPlane){
            try {
                transformedPoints = Util.rotate(this.points, angle[1]*(Math.PI/180), -angle[1] * (Math.PI/180), -angle[2] * (Math.PI/180));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }else{
            transformedPoints = this.points;
        }

        for(int k = 0; k < this.points[0].length; k++){
            if(is3DPlane){
                int green = (int)((this.points[2][k]-min)/range*255);
                green = green > 255 ? 255 : green;
                g2D.setColor(new Color((int)((this.points[1][k]-domainStart)/domain*255), green, (int)((this.points[0][k]-domainStart)/domain*255)));
            }else{
                g2D.setColor(new Color(255, 255, 255));
            }
            
            
            
            int dim = 3;
            //perspective?
            //g2D.fillRect((int)(p[0][k]*((50/(-p[1][k]+500)))+500-dim/2), 1000 - ((int)(p[2][k]*((50/(-p[1][k]+500)))+500+dim/2)), dim, dim);
        
            //ortho?
            g2D.fillRect((int)(transformedPoints[0][k]+500-dim/2), 1000 - ((int)(transformedPoints[2][k]+500+dim/2)), dim, dim);
        }
        
    }

    public void paintGraph(Graphics g){
                
        Graphics2D g2D = (Graphics2D)g;

        g2D.setColor(new Color(0, 0, 0));
        g2D.fillRect(0, 0, 1000, 1000);

        if(this.points != null)
            this.drawPoints(g2D);

        

        //draw x axis Blue
        double[] x = Util.rotate(new double[]{200, 0, 0}, angle[1]*(Math.PI/180), -angle[1] * (Math.PI/180), -angle[2] * (Math.PI/180));
        g2D.setColor(new Color(100, 100, 255, 255));
        g2D.drawLine((int)0+500, 1000 - ((int) 0+500), (int)x[0]+500, 1000 - ((int) x[2]+500));
        g2D.drawString("X", (int)x[0]+500, 1000 - ((int) x[2]+500));


        //draw y axis Green
        double[] y = Util.rotate(new double[]{0, 0, 200}, angle[1]*(Math.PI/180), -angle[1] * (Math.PI/180), -angle[2] * (Math.PI/180));
        g2D.setColor(new Color(0, 255, 0, 255));
        g2D.drawLine((int)0+500, 1000 - ((int) 0+500), (int)y[0]+500, 1000 - ((int) y[2]+500));
        g2D.drawString("Y", (int)y[0]+500, 1000 - ((int) y[2]+500));
        

        //draw z axis Red
        if(is3DPlane){
            double[] z = Util.rotate(new double[]{0, 200, 0}, angle[1]*(Math.PI/180), -angle[1] * (Math.PI/180), -angle[2] * (Math.PI/180));
            g2D.setColor(new Color(255, 0, 0, 255));
            g2D.drawLine((int)0+500, 1000 - ((int) 0+500), (int)z[0]+500, 1000 - ((int) z[2]+500));
            g2D.drawString("Z", (int)z[0]+500, 1000 - ((int) z[2]+500));
        }

        if((time-prevTime) != 0){
            g2D.drawString(""+1000/(time - prevTime), 100, 100);
        }    
        
    }

    public void paint(Graphics g){
        prevTime = time;
        time = System.currentTimeMillis();

        dbImage = createImage(1000, 1000);
        dbGraphics = dbImage.getGraphics();

            

        paintGraph(dbGraphics);
        g.drawImage(dbImage, 0, 0, this);


        if(is3DPlane){
            angle[2] = (MouseInfo.getPointerInfo().getLocation().x - 500) / 1000.0 * 360;
            angle[1] = (MouseInfo.getPointerInfo().getLocation().y - 500) / 1000.0 * 360;
        }
        this.repaint();
    }


    public void setGraph(double[][] points, double interval, double domainStart, double domainEnd){
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
        if (Double.isInfinite(this.range)){
            this.range = 600;
        }
        
        System.out.println(this.range);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        this.repaint();
    }
}
