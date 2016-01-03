package main;

import org.eclipse.swt.graphics.Point;
//import java.awt.*;
import java.util.ArrayList;

/**
 * Created by BSU on 01.01.2016.
 */
public class Line {
    private String xPoint;
    private String yPoint;
    private ArrayList<Point> coords;

    public String getxPoint() {
        return xPoint;
    }

    public void setxPoint(String xPoint) {
        this.xPoint = xPoint;
    }

    public String getyPoint() {
        return yPoint;
    }

    public void setyPoint(String yPoint) {
        this.yPoint = yPoint;
    }

    public ArrayList<Point> getCoords() {
        return coords;
    }

    public void setCoords(ArrayList<Point> coords) {
        this.coords = coords;
    }
}
