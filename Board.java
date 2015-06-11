import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.io.File;
import java.io.IOException;

public class Board extends JPanel implements ActionListener {

    private Timer timer = new Timer(100, this);
    private Score score = new Score();
    private Font font;
    private boolean isPlaying = true;
    private boolean over = false;
	public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, SCALE = 15;
	private int direction;
	private Snake head;
	private int size = 15;
	private Queue<Snake> body = new Queue<Snake>();
	private BufferedImage img = null;
	
    /**
     * Construct of class
     * 
     */
    public Board() {

        addKeyListener(new TAdapter());
        setFocusable(true);        
        setDoubleBuffered(true);
        setBackground(new Color(146, 191, 2));
        
    	Object o = new Object();
        String fileLoc = o.getClass().getResource("/images/scene.png").getPath();    	
		try {
			img = ImageIO.read(new File(fileLoc));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        startNewGame();
    }
    
    /**
     * To initilize a new game
     */
    public void startNewGame() {
    	
    	score.resetScore();
        isPlaying = true;
        over = false;
        direction = LEFT;
        head = new Snake(27, 20, LEFT);
        body.removeAll();
        
        timer.start();
	}
    
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
    	
    	if( isPlaying ){
    		
    		body.add(head);
    		
    		int width = (int) (Game.WIDTH/SCALE);
    		int height = (int) (Game.HEIGHT/SCALE);
    		
	    	if( direction == RIGHT ){
	    		
	    		int x = head.getX();	
	    		x = ( (x + 2) > width ) ? 0 : x + 1;
	    		head = new Snake(x, head.getY(), RIGHT);

	    	} else if ( direction == LEFT ){
	    		
	    		int x = head.getX();	
	    		x = ( (x - 1) < 0 ) ? width-1 : x - 1;    		
	    		head = new Snake(x, head.getY(), LEFT);

	    	} else if ( direction == UP ){
	    		
	    		int y = head.getY();	
	    		y = ( (y - 1) < 0 ) ? height-3 : y - 1;
	    		head = new Snake(head.getX(), y, UP);
	    		
	    	} else {
	    		
	    		int y = head.getY();
	    		y = ( (y + 4) > height ) ? 0 : y + 1;
	    		head = new Snake(head.getX(), y, DOWN);
	    		
	    	}
	    	
	    	// Test whether the snake collides with itself	
    		over = iTsCollision(head);
    	}
    	
    	if( Queue.length > size ) body.remove();    	
    	
        repaint();  
    }

    /* (non-Javadoc)
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    public void paint(Graphics g) {
        super.paint(g);
                
        Graphics2D g2d = (Graphics2D)g;
        
        // Paint scene back
    	g2d.drawImage(img, 0, 0, null);
        
    	// Score and lives
    	g2d.setColor(Color.WHITE);

    	g2d.fillRect(44, 25, 130, 40);
    	g2d.fillRect(625, 25, 130, 40);
    	
        score.paintComponent(g);
                
        // Drawing snake pieces and head
        Snake aux = body.getSnake();
    	while (aux != null ) {
    		g2d.drawImage(rotate(aux.getBody(), Snake.a[aux.getOrientation()]), aux.getX() * SCALE, aux.getY() * SCALE, null);
    		aux = aux.getLast();
		}
    	
    	int fixUpBugLeftAndDownX = 0, fixUpBugLeftAndDownY = 0;
    	if( direction == LEFT ){
    		fixUpBugLeftAndDownX = -10;
    		fixUpBugLeftAndDownY = -15;
    	}
    	if( direction == DOWN ){
    		fixUpBugLeftAndDownX = -10;
    	}
		g2d.drawImage(rotate(head.getHead(), Snake.a[head.getOrientation()]), (head.getX() * SCALE)+fixUpBugLeftAndDownX,
				(head.getY() * SCALE)+fixUpBugLeftAndDownY, null);
		// End of drawing
		
		// Print a message to player
		if( over ){
	        g2d.setColor(Color.YELLOW);			
			g2d.drawString("Game over :(", (int) (Game.WIDTH/2.4), (int) (Game.HEIGHT/2.4));
			g2d.drawString("Press [Space/Enter] to restart", (int) (Game.WIDTH/2.4) - 100, (int) (Game.HEIGHT/2.4) + 35);
		}
		
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
        
    }
    
    /**
     * Method to rotate an image
     * 
     * @param image
     * @param angule
     * @return
     */
    public static BufferedImage rotate( BufferedImage image, double angule ){

    	double rotationRequired = Math.toRadians(angule);
    	double locationX = image.getWidth() / 2;
    	double locationY = image.getHeight() / 2;
    	AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
    	AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
    	
    	return op.filter(image, null);
    }

    /**
     * Method to check if node of head touch some piece of the snake
     * 
     * @return
     */
    private boolean iTsCollision(Snake head){
    	
        Snake aux = body.getSnake();
    	while ( aux != null ) {
    		if( (aux.getX() == head.getX()) 
    				&& (aux.getY() == head.getY()) ){
    			isPlaying = false;
    			return true;
    		}
    		aux = aux.getLast();
		}
    	return false;
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
	            case KeyEvent.VK_SPACE:
	            	if( over ) {
	            		startNewGame();
	            	} else {
	            		isPlaying = !isPlaying;
	            	}
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                	if ( direction != LEFT && isPlaying ) direction = RIGHT;
                	break;
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
            		if ( direction != RIGHT && isPlaying ) direction = LEFT;
                	break;
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
            		if ( direction != DOWN && isPlaying ) direction = UP;
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
            		if ( direction != UP && isPlaying ) direction = DOWN;
                    break;
            }
            
        }
    }
    
}