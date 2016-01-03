package draw;

import org.eclipse.swt.graphics.Point;
//import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Tristan-PC on 09.12.2015.
 */
public class Bresenham {

    int x0;
    int y0;
    int x1;
    int y1;

    int dx;
    int dy;

    boolean xDirection;

    double error;

//    Map<Integer, Point> coords = new HashMap<>();
    ArrayList<Point> coords = new ArrayList<>();

    public Bresenham() {

//        coords.put(0, new Point(0, 0));
        coords.add(new Point(0, 0));

    }

    public void setUp(int x0, int y0, int x1, int y1){

        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;

        dx = x1 - x0;
//        System.out.println(dx);
        if(dx < 0) {
            dx = dx * -1;
//            System.out.println(dx);
        }

        dy = y1 - y0;
//        System.out.println(dy);
        if(dy < 0) {
            dy = dy * -1;
//            System.out.println(dy);
        }

        setDirection();
//        System.out.println(xDirection);
        setError();
//        System.out.println(error);

        calcCoord();

    }

    private void setDirection() {

        if(dx >= dy) {

            xDirection = true;

        } else {

            xDirection = false;

        }

    }

    private void setError() {

        if(xDirection) {

            error = (double) dx / 2;

        } else {

            error = (double) dy / 2;

        }

    }

    private void calcCoord() {

        int x = x0;
        int y = y0;

        if(xDirection) {
            for (int i = 1; i <= dx; i++) {
                if (x0 < x1 && y0 < y1) {
                    x++;
                    error = error - dy;
                    if (error < 0) {
                        y++;
                        error = error + dx;
                    }
                } else if (x0 < x1 && y0 == y1) {
                    x++;
                } else if (x0 < x1 && y0 > y1) {
                    x++;
                    error = error - dy;
                    if (error < 0) {
                        y--;
                        error = error + dx;
                    }
                } else if (x0 > x1 && y0 > y1) {
                    x--;
                    error = error - dy;
                    if (error < 0) {
                        y--;
                        error = error + dx;
                    }
                } else if (x0 > x1 && y0 == y1) {
                    x--;
                } else if (x0 > x1 && y0 < y1) {
                    x--;
                    error = error - dy;
                    if (error < 0) {
                        y++;
                        error = error + dx;
                    }
                }
//                coords.put(i, new Point(x, y));
                coords.add(new Point(x, y));
            }
        } else {
            for (int i = 1; i <= dy; i++) {
                if (x0 < x1 && y0 < y1) {
                    y++;
                    error = error - dx;
                    if (error < 0) {
                        x++;
                        error = error + dy;
                    }
                } else if (x0 < x1 && y0 > y1) {
                    y--;
                    error = error - dx;
                    if (error < 0) {
                        x++;
                        error = error + dy;
                    }
                } else if (x0 == x1 && y0 > y1) {
                    y--;
                } else if (x0 > x1 && y0 > y1) {
                    y--;
                    error = error - dx;
                    if (error < 0) {
                        x--;
                        error = error + dy;
                    }
                } else if (x0 > x1 && y0 < y1) {
                    y++;
                    error = error - dx;
                    if (error < 0) {
                        x--;
                        error = error + dy;
                    }
                } else if (x0 == x1 && y0 < y1) {
                    y++;
                }
//                coords.put(i, new Point(x, y));
                coords.add(new Point(x, y));
            }
        }
    }

    public ArrayList<Point> getCoords(){
        return coords;
    }

}
