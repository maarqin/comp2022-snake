package game;
import java.awt.image.BufferedImage;

import javax.swing.*;

/**
 * Write a description of class Snake here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Snake extends JPanel {

	private static final long serialVersionUID = 7671191111417041871L;
	private Snake last;
	private String headFile = "/images/head.png";
    private String bodyFile = "/images/body.png";
    public static final double[] a = {90, 270, 0, 180};
    
    private int x;
    private int y;
    private int orientation;
    
	private BufferedImage head = null;
	private BufferedImage body = null;
    
    public Snake() {
    	this(0, 0, Board.DOWN);
    }
    
    /**
     * Main construct of this class
     * 
     * @param x
     * @param y
     */
    public Snake(int x, int y, int orientation) {
    	
    	head = Game.loadImages(headFile);
    	body = Game.loadImages(bodyFile);
    	
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }

    public int getX() {
        return x;
    }

    public Snake setX(int x) {
        this.x = x;
        return this;
    }
    
    public int getY() {
        return y;
    }
    
    public Snake setY(int y) {
        this.y = y;
        return this;
    }

    /**
     * Return an instance of this class
     * 
     * @return
     */
    public Snake getPosition() {
		return new Snake(x, y, orientation);
	}
    
	/**
	 * Return the head of the Snake
	 * 
	 * @return
	 */
	public BufferedImage getHead() {
		return head;
	}

	/**
	 * Obtain the pieces of the body
	 * 
	 * @return
	 */
	public BufferedImage getBody() {
		return body;
	}
	
    /**
     * Return the last position of a {@link Snake}
     * 
     * @return
     */
    public Snake getLast() {
		return last;
	}

	/**
	 * Put a {@link Snake} in the last position
	 * 
	 * @param last
	 */
	public void setLast(Snake last) {
		this.last = last;
	}
	
	/**
	 * Method to return an orientation
	 * 
	 * @return
	 */
	public int getOrientation() {
		return orientation;
	}

	/**
	 * Method to set an orientation
	 * 
	 * @param orientation
	 */
	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#toString()
	 */
	public String toString(){
		String ret = "x[" + x + "]; y[" + y + "]\n";
		return ret;
	}
	
}
