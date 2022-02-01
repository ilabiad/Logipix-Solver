import javax.swing.*;
import java.awt.*;



public class DrawLogipix extends JPanel{
	
	
	int Offset = 0;
	int[][] puzzle;
	int rect_width = 30;
	int rect_height = 30;
	int number_w = 10;
	int number_h = 10;
	
	public DrawLogipix(int w, int h, int[][] p) {
		rect_width = w;
		rect_height = h;
		puzzle = p;
		number_w = p[0].length;
		number_h = p.length;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int x = Offset;
		int y = Offset;
		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
		String s;
		for(int i=0; i<number_h; i++) {
			x = Offset;
			for(int j=0; j<number_w; j++) {
				g.drawRect(x,y,rect_width, rect_height);
				if (puzzle[i][j] > 0) {
					g.setColor(Color.BLUE);
					g.setFont(new Font("TimesRoman", Font.BOLD, 17));
				}
				s = String.valueOf(puzzle[i][j]);
				g.drawString(s, x + rect_width/2 - s.length()*4, y + rect_height/2 + 4);
				g.setColor(Color.BLACK);
				g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
				x += rect_width;
			}
			y += rect_height;
		}
	}
}

