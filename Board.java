import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.io.File;
import java.util.LinkedList;

public class Board extends JPanel implements ActionListener {

    private Timer timer;
    private Score score;
    private Font font;
    private boolean isPlaying = true;
	private static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
	private int direction = DOWN;
	private Point head;
	public Queue<Node> tail = new Queue<Node>();
       
    /**
     * Construct of this class
     * 
     */
    public Board() {

        addKeyListener(new TAdapter());
        setFocusable(true);        
        setDoubleBuffered(true);
        setBackground(Color.WHITE);

        score = new Score();
        
        head = new Point();
        
        timer = new Timer(400, this);
        timer.start();

    }

    /* (non-Javadoc)
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    public void paint(Graphics g) {
        super.paint(g);
        
        score.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D)g; 

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
        
    }

    /**
     * @param g
     */
    public void paintIntro(Graphics g) {
        if(isPlaying){
            isPlaying = false;
            Graphics2D g2d = (Graphics2D) g;
            try{
                File file = new File("fonts/VT323-Regular.ttf");
                font = Font.createFont(Font.TRUETYPE_FONT, file);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(font);
                font = font.deriveFont(Font.PLAIN,40);
                g2d.setFont(font);
            }catch (Exception e){
                System.out.println(e.toString());
            }   
            g2d.drawString("S N A K E: " + this.score, 300, 300);
        }
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        	
    	if( isPlaying ){
    		
	    	if( direction == RIGHT ){
	    		
	    	} else if ( direction == LEFT ){
	    		
	    	} else if ( direction == UP ){
	    		
	    	} else {
	    		
	    	}
    	}
    	
    	
        repaint();  
    }
    
    /**
     * Class of keys
     * 
     * @author thomaz
     *
     */
    private class TAdapter extends KeyAdapter {

        /* (non-Javadoc)
         * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEvent)
         */
        public void keyPressed(KeyEvent e) {
            
            // Obtém o código da tecla
            int key =  e.getKeyCode();

            switch (key){ 
                case KeyEvent.VK_ENTER:
                	isPlaying = !isPlaying;
                    break;
                case KeyEvent.VK_RIGHT:
                	if ( direction != LEFT ) direction = RIGHT;
                	break;
                case KeyEvent.VK_LEFT:
            		if ( direction != RIGHT ) direction = LEFT;
                	break;
                case KeyEvent.VK_UP:
            		if ( direction != DOWN ) direction = UP;
                    break;
                case KeyEvent.VK_DOWN:
            		if ( direction != UP ) direction = DOWN;
                    break;
            }
            
        }
    }
    
}