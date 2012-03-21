package utils;

import java.awt.*;
import java.awt.font.*;
import java.awt.image.*;
import java.awt.geom.*;
import javax.swing.*;

public class GraphPanel extends JPanel {
	public static final int PAD = 20;
	
	public static final int topPad = 20;
	public static final int leftPad = 50;
	public static final int rightPad = 20;
	public static final int bottomPad = 40;
	
	public static final int unitSize = 5;
	
	public static final long serialVersionUID = 16L;
	
	protected double [] data;
	
	public GraphPanel(double data[]) {		
		this.data = data;
	}
	
	public RenderedImage getImage() {
		int width = this.getWidth();
		int height = this.getHeight();

		// Create a buffered image in which to draw
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// Create a graphics contents on the buffered image
		Graphics2D g2d = bufferedImage.createGraphics();

		// Draw graphics
		paintGraph(g2d);

		// Graphics context no longer needed so dispose it
		g2d.dispose();

		return bufferedImage;
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintGraph((Graphics2D)g); 
    }
    
    protected void paintGraph(Graphics2D g2) {
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
		
		// Get frame size
		int w = this.getWidth();
        int h = this.getHeight();
        
        // White background
        g2.setPaint(Color.white);
        g2.fill(new Rectangle2D.Double(0,0,w,h));
        
        // Draw axis
        g2.setPaint(Color.black);
        // Draw ordinate.
        g2.draw(new Line2D.Double(leftPad, topPad, leftPad, h-bottomPad));
        // Draw abcissa.
        g2.draw(new Line2D.Double(leftPad, h-bottomPad, w-rightPad, h-bottomPad));
        
        // Draw labels.
        Font font = g2.getFont();
        FontRenderContext frc = g2.getFontRenderContext();
        LineMetrics lm = font.getLineMetrics("0", frc);
        float sh = lm.getAscent() + lm.getDescent();
        
        // Ordinate label.
        String s = "Steps";
        float sy = PAD + ((h - 2*PAD) - s.length()*sh)/2 + lm.getAscent();
        for(int i = 0; i < s.length(); i++) {
            String letter = String.valueOf(s.charAt(i));
            float sw = (float)font.getStringBounds(letter, frc).getWidth();
            float sx = (PAD - sw)/2;
            g2.drawString(letter, sx, sy);
            sy += sh;
        }
        
        // Abcissa label.
        s = "Episode";
        sy = h - PAD + (PAD - sh)/2 + lm.getAscent();
        float sw = (float)font.getStringBounds(s, frc).getWidth();       
        float sx = (w - sw)/2;
        g2.drawString(s, sx, sy);
        
        
        double xInc = (double)(w - leftPad - rightPad)/(data.length);
        double scale = (double)(h - topPad - bottomPad)/getMax();
               
        // Draw lines.
        g2.setPaint(Color.blue.darker());
        for(int i = 0; i < data.length-1; i++) {
            double x1 = leftPad + (i+1)*xInc;
            double y1 = h - bottomPad - scale*data[i];
            double x2 = leftPad + (i+2)*xInc;
            double y2 = h - bottomPad - scale*data[i+1];
            g2.draw(new Line2D.Double(x1, y1, x2, y2));
        }
        
        // Mark data points.
        g2.setPaint(Color.red);
        for(int i = 0; i < data.length; i++) {
            double x = leftPad + (i+1)*xInc;
            double y = h - bottomPad - scale*data[i];
            g2.fill(new Ellipse2D.Double(x-2, y-2, 4, 4));
        }
        
        g2.setPaint(Color.black);
        
        // Draw Abcissa units
        int nbUnit = (w - leftPad - rightPad) / 40; // Trail of 40 px
        int dUnit = (data.length)/nbUnit;
        int v = 0;
        while (v < data.length) {
			double x = leftPad + v*xInc;
			double y = h - bottomPad;
			g2.draw(new Line2D.Double(x, y, x, y + unitSize));
			
			s = Integer.toString(v);
			sy = h - bottomPad + (topPad - sh)/2 + lm.getAscent() + unitSize;
			sw = (float)font.getStringBounds(s, frc).getWidth(); 
			sx = (float)x - sw/2;
			g2.drawString(s, sx, sy);
			
			v += dUnit;
		}
		g2.draw(new Line2D.Double(w-rightPad, h-bottomPad, w-rightPad, h-bottomPad + unitSize));
		
		// Draw Ordinate units
		double max = getMax();
        nbUnit = (h - topPad - bottomPad) / 30; // Trail of 40 px
        dUnit = (int)(max/nbUnit);
        v = 0;
        while (v < max) {			
			double x = leftPad;
			double y = h - bottomPad - v*scale;
			g2.draw(new Line2D.Double(x - unitSize, y, x, y));
			
			
			s = Integer.toString(v);
			sw = (float)font.getStringBounds(s, frc).getWidth(); 
			sh = (float)font.getStringBounds(s, frc).getHeight(); 
			sx = (float)x - sw - unitSize - 1;
			sy = (float)y + sh/3;
			g2.drawString(s, sx, sy);
			
			v += dUnit;
			 
		}
		g2.draw(new Line2D.Double(leftPad - unitSize, topPad, leftPad, topPad));
	}

    private double getMax() {
        double max = Double.MIN_VALUE;
        for(int i = 0; i < data.length; i++) {
            if(data[i] > max)
                max = data[i];
        }
        return max;
    }
}
