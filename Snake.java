import java.awt.Image;
import javax.swing.*;

/**
 * Write a description of class Snake here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Snake extends JPanel {
	private Snake last;
	private String headFile = "head.png";
    private String bodyFile = "body.png";

    private int x;
    private int y;
    private Image head;
    private Image body;
    
    public Snake() {
    	this(0, 0);
    }
    
    /**
     * Main construct of this class
     * 
     * @param x
     * @param y
     */
    public Snake(int x, int y) {
//        ImageIcon h = new ImageIcon("images/"+this.getClass().getResource(headFile));
//        head = h.getImage();
//        
//        ImageIcon b = new ImageIcon("images/"+this.getClass().getResource(bodyFile));
//        body = b.getImage();        
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Return an instance of this class
     * 
     * @return
     */
    public Snake getPosition() {
		return new Snake(x, y);
	}
    
	/**
	 * Return the head of the Snake
	 * 
	 * @return
	 */
	public Image getHead() {
		return head;
	}

	/**
	 * Obtain the pieces of the body
	 * 
	 * @return
	 */
	public Image getBody() {
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
	
	/* (non-Javadoc)
	 * @see java.awt.Component#toString()
	 */
	public String toString(){
		String ret = "x:[" + x + "]; y[" + y + "]\n";
		return ret;
	}
	
}
