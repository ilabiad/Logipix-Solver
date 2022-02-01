import javax.swing.*;
import java.awt.*;

public class DisplayLogipix {
	
	int[][] puzzle;
	
	public int width;
	public int height;
	
	JFrame frame;
	
	public DisplayLogipix() {
	}
	
	public void display(int[][] p, int w, int h) {
		puzzle = p;
		width  = w;
		height = h;
		display();
	}
	
	public void display(int[][] p) {
		puzzle = p;
		width  = 500;
		height = 500;
		display();
	}
	
	private void display() {
		frame = new JFrame();
		
		//Create stuff that will go on the frame
		//JPanel panel = new JPanel();
		//panel.setBorder(BorderFactory.createEmptyBorder(200,200,200,200));
		//panel.setLayout(new GridLayout(0,1));
		int rect_w = width / puzzle[0].length;
		int rect_h = height / puzzle.length;
		DrawLogipix grid = new DrawLogipix(rect_w, rect_h, puzzle);
		grid.setSize(width, height);

		//Add stuff on the frame
		//frame.add(panel, BorderLayout.CENTER);
		//panel.add(rect, BorderLayout.CENTER);
		frame.getContentPane().add(grid);
		
		//set frame parameters and show
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("new frame");
		frame.setSize((int)(width*1.2), (int)(height*1.2));
		//frame.pack();
		frame.setVisible(true);
	}
	
	public void drawBrokenLine(BrokenLine bl) {
		if (puzzle == null) {
			System.out.println("need a puzzle in order to draw");
			return;
		}
		if (frame == null)
			display();
		
		int[] pointX = new int[bl.line.length];
		int[] pointY = new int[bl.line.length];
		int rect_w = width / puzzle[0].length;
		int rect_h = height / puzzle.length;
		for (int i=0; i<bl.line.length; i++) {
			pointX[i] = bl.line[i].x * rect_w + rect_w / 2 + 5;
			pointY[i] = bl.line[i].y * rect_h + rect_h / 2 - 5;
		}
		
		JComponent pl = new JComponent() {
			@Override
			public void paintComponent(Graphics g) {
				//super.paintComponent(g);
				Graphics2D g2 = (Graphics2D)g;
				g2.setStroke(new BasicStroke(6));
				Color darkGreen = new Color(0,30,0,120);
				g.setColor(darkGreen);
				g.drawPolyline(pointX, pointY, bl.line.length);
				if (pointX.length == 1)
					g.fillOval(pointX[0] - 7, pointY[0] - 7, 15, 15);
				Color lightGreen = new Color(0,255,100,60);
				g.setColor(lightGreen);
				for (int i=0; i<bl.line.length; i++) {
					g.fillRect(bl.line[i].x * rect_w, bl.line[i].y * rect_h, rect_w, rect_h);
				} 
			}
		};
		frame.getContentPane().add(pl, 0);
		
		//frame.pack();
		frame.repaint();
		frame.revalidate();
	}
	
}