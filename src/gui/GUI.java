package gui;

import draw.Bresenham;
import draw.Draw;
import main.DrawThread;
import main.Line;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.internal.SWTEventListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Created by Tristan-PC on 06.12.2015.
 */
public class GUI extends JFrame implements ActionListener{

    ArrayList<Line> lines = new ArrayList<>();
    ArrayList<Line> newLines = new ArrayList<>();

    Point previous;
    Point click = new Point(0, 0);

    Draw draw = new Draw(null);
    Bresenham bresenham = new Bresenham();
    DrawThread drawThread;

    JTextArea xTextArea;
    JTextArea yTextArea;

    public GUI() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) (screenSize.width*0.75), (int) (screenSize.height*0.75));

        setTitle("Bresenham Übung 3");

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel jPanel1 = new JPanel(new GridBagLayout());
        JPanel jPanel2 = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);

        //Draw draw = new Draw(coords);
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.weightx = 1;
        c.weighty = 1;
        jPanel1.add(draw, c);

            JLabel xLabel = new JLabel("x");
            c.gridx = 1;
            c.gridy = 0;
            c.insets = new Insets(5, 5, 5, 5);
            jPanel2.add(xLabel, c);

            JLabel yLabel = new JLabel("y");
            c.gridx = 2;
            c.gridy = 0;
            jPanel2.add(yLabel, c);

            xTextArea = new JTextArea();
            xTextArea.setPreferredSize(new Dimension(50, (int) (screenSize.height * 0.55)));
            xTextArea.setEditable(false);
            xTextArea.setBackground(Color.WHITE);
            c.gridx = 1;
            c.gridy = 1;
            //getX()
            xTextArea.append(click.x + "\n");
            jPanel2.add(xTextArea, c);

            yTextArea = new JTextArea();
            yTextArea.setPreferredSize(new Dimension(50, (int) (screenSize.height * 0.55)));
            yTextArea.setEditable(false);
            yTextArea.setBackground(Color.WHITE);
            c.gridx = 2;
            c.gridy = 1;
            //getY()
            yTextArea.append(click.y + "\n");
            jPanel2.add(yTextArea, c);

            Button bStart;
            bStart.setText("Start");
            c.gridx = 1;
            c.gridy = 2;
            jPanel2.add(bStart, c);

            Button bReset;
            bReset.setText("Reset");
            c.gridx = 2;
            c.gridy = 2;
            jPanel2.add(bReset, c);

        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.add(jPanel1, BorderLayout.WEST);
        mainPanel.add(jPanel2, BorderLayout.EAST);

        drawThread = new DrawThread(draw, xTextArea, yTextArea);
        
        draw.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
//                previous = click;
//                click = e.getPoint();
//                xTextArea.append(String.valueOf(click.getX()) + "\n");
//                yTextArea.append(String.valueOf(click.getY()) + "\n");
//                System.out.println("Punkt: " + e.getPoint());
//                //Bresenham bresenham = new Bresenham();
//                bresenham.setUp((int) previous.getX(), (int) previous.getY(), (int) click.getX(), (int) click.getY());
//                coords = bresenham.getCoords();
//                draw.setCoords(coords);
//                draw.repaint();

                System.out.println("Punkt: " + e.getPoint());
                previous = click;
                click = e.getPoint();
                Line line = new Line();
                //getX(), getY()
                line.setxPoint(click.x+"\n");
                line.setyPoint(click.y+"\n");
                bresenham.setUp((int) previous.x, (int) previous.y, (int) click.x, (int) click.y);
                line.setCoords(bresenham.getCoords());
                newLines.add(line);
                System.out.println("newLines: " + newLines.size());
                updateGui();
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
/*
        bStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawThread.start();
            }
        });*/
        
        Listener listener = new Listener() {
            public void handleEvent(Event event) {
            	if (event.widget == bStart) {
            		drawThread.start();
            	} else if (event.widget == bReset) {
            		drawThread.stop();
            		drawThread = new DrawThread(draw, xTextArea, yTextArea);
                    draw.removeAll();
                    draw.repaint();
                    newLines = new ArrayList<Line>();
                    xTextArea.setText(null);
                    yTextArea.setText(null);
            	}
            }
        };
/*
        bReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawThread.stop();
                drawThread = new DrawThread(draw, xTextArea, yTextArea);
                draw.removeAll();
                draw.repaint();
                newLines = new ArrayList<Line>();
                xTextArea.setText(null);
                yTextArea.setText(null);
            }
        });*/



        add(mainPanel);
        getContentPane().revalidate();

        setVisible(true);

    }
/*
    @Override
    public void actionPerformed(ActionEvent e) {
        //bresenham.setUp((int) previous.getX(), (int) previous.getY(), (int) click.getX(), (int) click.getY());
        //coords = bresenham.getCoords();
        //draw.setCoords(coords);
        //draw.repaint();
    }*/

    public void updateGui() {
        System.out.println("updateGui() "+newLines.size());
        if(newLines.size() > 0) {
            drawThread.addLines(newLines);
            for (Line line: newLines) {
                xTextArea.append(line.getxPoint());
                yTextArea.append(line.getyPoint());
            }
            newLines = new ArrayList<>();
        }
    }
}