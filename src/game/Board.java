package game;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import dao.DaoPlayers;

import java.io.InputStream;
import java.util.Random;

/**
 * @author thomaz
 *
 */
public class Board extends JPanel implements ActionListener {

	private static final long serialVersionUID = -5933370838331813168L;
	private static final int SPEED = 100;
	private Timer timer = new Timer(SPEED, this);
    private Score score = new Score();
    private Font font;
    private boolean isPlaying;
    private boolean over;
    private boolean endLife;
	public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, SCALE = 15;
	private int direction;
	private Snake head;
	private int size;
	private Queue<Snake> body = new Queue<Snake>();
	private BufferedImage img;
	private Point fries;
	public static final int LIM_ESQ = 3, LIM_CIM = 5, LIM_BAI = 35, LIM_DIR = 49;
	private long start, end;
	private boolean unLoop = false;
	private boolean moved = true;
	
    /**
     * Construct of class
     * 
     */
    public Board() {

        addKeyListener(new TAdapter());
        setFocusable(true);        
        setDoubleBuffered(true);
        setBackground(new Color(146, 191, 2));
        
        img = Game.loadImages("/images/scene.png");
        
        startNewGame();
    }
    
    /**
     * To initialize a new game
     * 
     */
    public void startNewGame() {
    	
    	timer.setDelay(SPEED);
    	score.reset();
    	
    	isPlaying = true;
        over = false;
        endLife = false;
        direction = LEFT;
    	unLoop = false;
    	moved = true;
        head = new Snake(LIM_DIR, LIM_BAI, LEFT);
        size = 3;
        body.removeAll();
        fries = Board.generatePlaceToFries();
        start = System.currentTimeMillis();
        
        timer.start();
   	}
    
    /**
     * Continue playing if exist life
     * 
     */
    public void resetGame() {
    	isPlaying = true;
        over = false;
        endLife = false;
    	direction = LEFT;
    	unLoop = false;
    	moved = true;
    	body.cleanAll();
        head = new Snake(LIM_DIR, LIM_BAI, LEFT);
        fries = Board.generatePlaceToFries();
	}
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
    	
    	if( isPlaying && !endLife ){
    		
    		body.add(head);
    		
	    	if( direction == RIGHT ){
	    		head = new Snake(head.getX() + 1, head.getY(), RIGHT);
	    		if( head.getX() > LIM_DIR ) endLife = true;
	    		
	    	} else if ( direction == LEFT ){    		
	    		head = new Snake(head.getX() - 1, head.getY(), LEFT);
	    		if( head.getX() < LIM_ESQ ) endLife = true;

	    	} else if ( direction == UP ){
	    		head = new Snake(head.getX(), head.getY() - 1, UP);
	    		if( head.getY() < LIM_CIM ) endLife = true;

	    	} else {
	    		head = new Snake(head.getX(), head.getY() + 1, DOWN);
	    		if( head.getY() > LIM_BAI ) endLife = true;

	    	}
	    	// Test whether the snake collides with itself
	    	iTsCollision(head);
	    	
	    	// Core of the game
	    	this.core();

	    	// Always remove tail of the snake, this works to move its
	    	if( Queue.length > size ) body.remove();    	
	    	
	    	// Found the French fries
	    	if( (fries.getX() == head.getX()) 
	    			&& (fries.getY() == head.getY()) 
	    			&& isPlaying ){
	    		size++;
	    		score.addScore(1);
	    		fries = Board.generatePlaceToFries();
	    		
	    		int modDelay = 0, modLife = 0, delay = 0;
	    		if( score.getScore() <= 60 ){
    				modDelay = 5;
    				modLife = 30;
    				delay = -5;
	    		} else if( score.getScore() <= 100 ){
    				modDelay = 10;
    				modLife = 20;
    				delay = -3;
	    		} else if( timer.getDelay() > 0 ){
    				modDelay = 20;
    				modLife = 10;
    				delay = -2;
	    		} else {
    				modDelay = -1;
    				modLife = 10;
	    		}
	    		
    			if( score.getScore() % modDelay == 0 ) reDelay(delay);
	    		if( score.getScore() % modLife == 0 ) score.setLives(1);
	    	}
	    	moved = true;
    	}
    	repaint();
    	
    }
    
    /**
     * Function to set new value to game's delay
     * 
     * @param sub
     */
    private void reDelay(int sub){
    	timer.setDelay(timer.getDelay() + sub);
    }

    /* (non-Javadoc)
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    public void paint(Graphics g) {
    	// Method to optimize yet
    	
    	// Record new last score when game's over
		if( over && !unLoop ){
			unLoop = true;
			end = System.currentTimeMillis();
			long time = (end - start)/1000;
			String nick = JOptionPane.showInputDialog(null, "Qual o seu nick?", "New score!", JOptionPane.INFORMATION_MESSAGE);
			int total = score.getScore();
			
			DaoPlayers p = new DaoPlayers();
			p.setNick(nick);
			p.setScore(total);
			p.setTime(time);
			
			// Save this nick
			if( !nick.equals("") &&
					!nick.equals(" ") ) DaoPlayers.doInsert(p);
			
			// Update the list of records
			score.setRanking(DaoPlayers.getRecords());
		}
		
    	super.paint(g);
                
        Graphics2D g2d = (Graphics2D)g;
              
        // Paint scene back
    	g2d.drawImage(img, 0, 0, null);
        
    	// Score and lives
    	g2d.fillRect(44, 25, 130, 40);
    	g2d.fillRect(625, 25, 130, 40);
    	
    	// Draw the fries
    	drawFries(g2d);
    	
    	g2d.setColor(Color.WHITE);
    	
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
    	} else if( direction == DOWN ){
    		fixUpBugLeftAndDownX = -10;
    	} else if( direction == UP ){
    		fixUpBugLeftAndDownY = -10;
    	}
		g2d.drawImage(rotate(head.getHead(), Snake.a[head.getOrientation()]), (head.getX() * SCALE)+fixUpBugLeftAndDownX,
				(head.getY() * SCALE)+fixUpBugLeftAndDownY, null);
		// End of drawing
		
		// Print a message or/and ranking to player
		if( !isPlaying ) score.doDrawRecords(g2d, over);
		
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
    private static BufferedImage rotate( BufferedImage image, double angule ){

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
    private void iTsCollision(Snake head){
    	
        Snake aux = body.getSnake();
    	while ( aux != null ) {
    		if( (aux.getX() == head.getX()) 
    				&& (aux.getY() == head.getY()) ){
    			endLife = true;
    		}
    		aux = aux.getLast();
		}
    }

    /**
     * Method to create new points (X, Y) in the garden
     * 
     * @return
     */
    private static Point generatePlaceToFries() {
    	int x = 0, y = 0;
    	
    	Random a = new Random();
    	do {
    		int valor = a.nextInt(100);
    		if( valor > LIM_ESQ 
    				&& valor < LIM_DIR 
    				&& x == 0 ){
    			x = valor;
    		}
    		valor = a.nextInt(100);
    		if( valor > LIM_CIM 
    				&& valor < LIM_BAI
    				&& y == 0 ){
    			y = valor;
    		}
		} while ( x == 0 || y == 0 );
    	
    	return new Point(x, y);
	}
    
    /**
     * Decides what to do with the game when "die"
     * 
     * @return
     */
    private void core(){
    	
    	if( endLife ){
    		score.setLives(-1);
    		if( score.getLives() == 0 ){
    			over = true;
    		}
    		isPlaying = false;
    	}
	}

    /**
     * Draw fries at the jungle
     * 
     * @param g2d
     */
    private void drawFries(Graphics2D g2d) {    	
		g2d.drawImage(Game.loadImages("/images/fries.png"), (int) fries.getX() * SCALE, (int)fries.getY() * SCALE, null);
	}
    
    /**
     * @param g
     */
    public void paintIntro(Graphics g) {
        if(isPlaying){
            isPlaying = false;
            Graphics2D g2d = (Graphics2D) g;
            try{
            	InputStream file = Score.class.getResourceAsStream("/fonts/vt_regular.ttf");   	
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
     */
    private class TAdapter extends KeyAdapter {

        /* (non-Javadoc)
         * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEvent)
         */
        public void keyPressed(KeyEvent e) {
            
            // Obtém o código da tecla
        	if( moved ){
	            int key = e.getKeyCode();
	            switch (key){ 
		            case KeyEvent.VK_ENTER:
		            case KeyEvent.VK_SPACE:
		            	if( over ) {
		            		startNewGame();
		            	} else {
		            		isPlaying = !isPlaying;
		            		if( endLife ) resetGame();
		            	}
	                    break;
	                case KeyEvent.VK_RIGHT:
	                case KeyEvent.VK_D:
	                	if ( direction != LEFT && isPlaying ) {
	                		direction = RIGHT;
	                		moved = false;
	                	}
	                	break;
	                case KeyEvent.VK_LEFT:
	                case KeyEvent.VK_A:
	            		if ( direction != RIGHT && isPlaying ) {
	            			direction = LEFT;
	            			moved = false;
	            		}
	                	break;
	                case KeyEvent.VK_UP:
	                case KeyEvent.VK_W:
	            		if ( direction != DOWN && isPlaying ) {
	            			direction = UP;
	            			moved = false;
	            		}
	                    break;
	                case KeyEvent.VK_DOWN:
	                case KeyEvent.VK_S:
	            		if ( direction != UP && isPlaying ) {
	            			direction = DOWN;
	            			moved = false;
	            		}
	                    break;
	            }
        	}
        }
    }
    
}