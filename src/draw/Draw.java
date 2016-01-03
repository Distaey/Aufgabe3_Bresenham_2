package draw;

import org.eclipse.swt.graphics.Point;

import javax.swing.*;
//import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Tristan-PC on 06.12.2015.
 */
public class Draw extends JPanel {

    int drawWidth;
    int drawHeight;
//    Map<Integer, Point> coords = new HashMap<>();
    ArrayList<Point> coords = new ArrayList<>();


    public Draw(ArrayList<Point> coords) {

        this.coords = coords;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(new Dimension((int) (screenSize.width*0.6), (int) (screenSize.height*0.65)));
        drawWidth = (int) (screenSize.width*0.6);
        drawHeight = (int) (screenSize.height*0.65);

        setVisible(true);

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setBackground(Color.WHITE);
        g2.setColor(Color.BLACK);
        g2.drawRect(0, 0, drawWidth - 1, drawHeight - 1);

        if(coords != null && coords.size() > 0) {
//            System.out.println(coords.size());
//            System.out.println((int) coords.get(0).getX() + "" + (int) coords.get(0).getY());
            for(int i = 0; i<coords.size(); i++) {
                g2.drawRect((int) coords.get(i).x, (int) coords.get(i).y, 1, 1);
            }
        }

    }

    public void setCoords(ArrayList<Point> coords) {
        this.coords = coords;
    }

    /*
    public void paint(Graphics g) {

        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setBackground(Color.WHITE);
        g2.setColor(Color.BLACK);
        g2.drawRect(0, 0, drawWidth, drawHeight);

    }
    */

}