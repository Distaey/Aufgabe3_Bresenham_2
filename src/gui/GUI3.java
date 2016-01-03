package gui;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Drawable;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.GCData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Text;

import draw.Bresenham;
import main.DrawThread;
import main.Line;
import org.eclipse.swt.widgets.Canvas;

public class GUI3 extends Shell {
	private Text xTextArea;
	private Text yTextArea;
	private Text txtX;
	private Text txtY;
	
	Bresenham bresenham = new Bresenham();
	Point previous;
    Point click = new Point(0, 0);
	ArrayList<Line> newLines = new ArrayList<>();
	DrawThread drawThread;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			GUI3 shell = new GUI3(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public GUI3(Display display) {
		super(display, SWT.SHELL_TRIM);
		
		Button sBtn = new Button(this, SWT.NONE);
		sBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		sBtn.setBounds(618, 526, 75, 25);
		sBtn.setText("Start");
		
		Button rBtn = new Button(this, SWT.NONE);
		rBtn.setBounds(699, 526, 75, 25);
		rBtn.setText("Reset");
		
		xTextArea = new Text(this, SWT.MULTI | SWT.BORDER);
		xTextArea.setText("0\n");
		xTextArea.setEditable(false);
		xTextArea.setBounds(617, 41, 76, 479);
		
		yTextArea = new Text(this, SWT.MULTI | SWT.BORDER);
		yTextArea.setText("0\n");
		yTextArea.setEditable(false);
		yTextArea.setBounds(698, 41, 76, 481);
		
		txtX = new Text(this, SWT.BORDER);
		txtX.setEditable(false);
		txtX.setText("x");
		txtX.setBounds(617, 10, 76, 21);
		
		txtY = new Text(this, SWT.BORDER);
		txtY.setEditable(false);
		txtY.setText("y");
		txtY.setBounds(698, 10, 76, 21);
		
		Canvas canvas = new Canvas(this, SWT.BORDER);
		canvas.setBounds(10, 10, 602, 541);
		createContents();
		
		drawThread = new DrawThread(canvas, xTextArea, yTextArea);
		
		canvas.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent e) {}
			public void mouseUp(MouseEvent e) {
				System.out.println("Punkt x: " + e.x);
				System.out.println("Punkt y: " + e.y);
				
				Point p = new Point(e.x, e.y);
                previous = click;
                click = p;
                
                Line line = new Line();
                line.setxPoint(click.x+"\n");
                line.setyPoint(click.y+"\n");
                
                bresenham.setUp((int) previous.x, (int) previous.y, (int) click.x, (int) click.y);
                
                line.setCoords(bresenham.getCoords());
                newLines.add(line);

                System.out.println("newLines: " + newLines.size());
                
                updateGui();
			}
			public void mouseDoubleClick(MouseEvent arg0) {}
		});
		
		sBtn.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent e) {}
			public void mouseUp(MouseEvent e) {
				System.out.println("Start");
				if(!drawThread.isRunning()) {
					drawThread.start();
				}
			}
			public void mouseDoubleClick(MouseEvent e) {}
		});
		
		rBtn.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent e) {}
			public void mouseUp(MouseEvent e) {
				System.out.println("End");
				drawThread.stop();
				newLines = new ArrayList<>();
				bresenham = new Bresenham();
				click = new Point(0, 0);
				xTextArea.append("0\n");
				yTextArea.append("0\n");
			}
			public void mouseDoubleClick(MouseEvent e) {}
		});
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Aufgabe 3 - Bresenham");
		setSize(800, 600);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	public void updateGui() {
        if(newLines.size() > 0) {
    		System.out.println("updateGui() "+newLines.size());
            drawThread.addLines(newLines);
            for (Line line: newLines) {
                xTextArea.append(line.getxPoint());
                yTextArea.append(line.getyPoint());
            }
            newLines = new ArrayList<>();
        }
    }
}
