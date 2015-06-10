import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.io.File;

public class Board extends JPanel implements ActionListener {

    private Timer timer = new Timer(50, this);
    private Score score = new Score();
    private Font font;
    private boolean isPlaying = true;
    private boolean over = false;
	private static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, SCALE = 10;
	private int direction = DOWN;
	private Snake head;
	private int size = 10;
	private Queue<Snake> body = new Queue<Snake>();
	
    /**
     * Construct of class
     * 
     */
    public Board() {

        addKeyListener(new TAdapter());
        setFocusable(true);        
        setDoubleBuffered(true);
        setBackground(new Color(146, 191, 2));

        startNewGame();
    }
    
    /**
     * To initilize a new game
     */
    public void startNewGame() {
    	
    	score.resetScore();
        isPlaying = true;
        over = false;
        direction = DOWN;
        head = new Snake(40, 30);
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
	    		head = new Snake(x, head.getY());
	    		
	    	} else if ( direction == LEFT ){
	    		
	    		int x = head.getX();	
	    		x = ( (x - 1) < 0 ) ? width-1 : x - 1;    		
	    		head = new Snake(x, head.getY());
	    		
	    	} else if ( direction == UP ){
	    		
	    		int y = head.getY();	
	    		y = ( (y - 1) < 0 ) ? height-3 : y - 1;
	    		head = new Snake(head.getX(), y);
	    		
	    	} else {
	    		
	    		int y = head.getY();
	    		y = ( (y + 4) > height ) ? 0 : y + 1;
	    		head = new Snake(head.getX(), y);
	    		
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
        
        score.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D)g;
        
        g2d.setColor(Color.RED);

        // Drawing snake pieces 
        Snake aux = body.getSnake();
    	while (aux != null ) {
    		g.fillRect(aux.getX() * SCALE, aux.getY() * SCALE, SCALE, SCALE);    		
    		aux = aux.getLast();
		}
        g2d.setColor(Color.BLUE);
		g.fillRect(head.getX() * SCALE, head.getY() * SCALE, SCALE, SCALE);
		// End of drawing
		
		// Print a message to player
		if( over ){
			g2d.drawString("Game over", (int) (Game.WIDTH/2.4), (int) (Game.HEIGHT/2.4));
		}
		
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
        
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
                	if ( direction != LEFT ) direction = RIGHT;
                	break;
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
            		if ( direction != RIGHT ) direction = LEFT;
                	break;
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
            		if ( direction != DOWN ) direction = UP;
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
            		if ( direction != UP ) direction = DOWN;
                    break;
            }
            
        }
    }
    
}