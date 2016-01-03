package main;

import draw.Draw;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Drawable;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.GCData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Text;

import javax.swing.*;
//import java.awt.*;
import java.util.ArrayList;

/**
 * Created by BSU on 01.01.2016.
 */
public class DrawThread implements Runnable {
    private Thread runThread;
    private boolean running = false;
    private boolean paused = false;

    private ArrayList<Point> drawn = new ArrayList<>();
    private ArrayList<Line> newLines = new ArrayList<>();
    private ArrayList<Line> newLineBuffer = new ArrayList<>();
    private Canvas canvas;
    Text xTextArea;
    Text yTextArea;

    public DrawThread(Canvas canvas, Text xTextArea, Text yTextArea) {
        this.canvas = canvas;
        this.xTextArea = xTextArea;
        this.yTextArea = yTextArea;
        canvas.addPaintListener(new PaintListener() {
        	public void paintControl(PaintEvent e) {
        	}
        });
    }

    public void start() {
        running = true;
        paused = false;
        System.out.println("DrawThread start()");
        if(runThread == null || !runThread.isAlive()){
            runThread = new Thread(this);
        	System.out.println("DrawThread start() new Thread");}
        else if(runThread.isAlive())
            throw new IllegalStateException("Thread already started.");
        runThread.start();
    }

    public void stop() {
        if(runThread == null)
            throw new IllegalStateException("Thread not started.");
        synchronized (runThread) {
            try {
                running = false;
                runThread.notify();
                runThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void pause() {
        if(runThread == null)
            throw new IllegalStateException("Thread not started.");
        synchronized (runThread) {
            paused = true;
        }
    }

    public void resume() {
        if(runThread == null)
            throw new IllegalStateException("Thread not started.");
        synchronized (runThread) {
            paused = false;
            runThread.notify();
        }
    }

    public void run() {
    	System.out.println("DrawThread run()");
        long sleep = 0, before;
        while(running) {
            while (newLines.size() > 0){
            	System.out.println("DrawThread run() while(running) while()");
                Line line = newLines.get(0);
                for (int i = 0; i < line.getCoords().size(); i++) {
                    Point point = line.getCoords().get(i);
                    drawn.add(point);
                    
                    System.out.println(point.x + " " + point.y);
                    canvas.addPaintListener(listener);
                    //draw.setCoords(drawn);
                    //draw.repaint();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                    }
                }
                newLines.remove(0);

                xTextArea.setText(null);
                yTextArea.setText(null);
                for(Line thisLine: newLines) {
                    xTextArea.append(thisLine.getxPoint());
                    yTextArea.append(thisLine.getyPoint());
                }
            }
            if(newLineBuffer.size() > 0) {
                newLines.addAll(newLineBuffer);
                newLineBuffer = new ArrayList<>();
                System.out.println("newLines (Thread): " + newLines.size());
            }

            synchronized (runThread) {
                if(paused) {
                    try {
                        runThread.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        paused = false;
    }

    public void addLines(ArrayList<Line> lines) {
        newLineBuffer.addAll(lines);
        System.out.println("newLineBuffer: " + newLineBuffer.size());
    }

    public boolean isRunning() {
        return running;
    }
}
