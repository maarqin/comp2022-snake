package game;
import java.awt.*;

import javax.swing.*;

import dao.DaoPlayers;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Classe JPanel do score que fica no canto superior direito do jogo.
 * 
 * @author mhadaniya
 * @version 20/05
 */
public class Score extends JPanel {
	
	private static final long serialVersionUID = 8190305740368996871L;
	private int score;
    private int life;
    private Font font;
    private ArrayList<DaoPlayers> ranking;

    /**
     * Constructor for objects of class Score
     */
    public Score(){
    	this.reset();
    }
   
     /**
     * Este método soma o número de pontos ao score.
     * 
     * @param  points   número de pontos 
     * @return     void
     */
    public void addScore(int points){
        this.score = this.score + points;
    }
    
    /**
    * Este método subtrai o número de pontos ao score.
    * 
    * @param  points   número de pontos 
    * @return     void
    */
    public void subScore(int points){
        this.score = this.score - points;
    }

    /**
     * Obtain the score actual
     * 
     * @return
     */
    public int getScore() {
		return score;
	}

    /**
     * Get quantity of lives
     * 
     * @return
     */
    public int getLives() {
		return this.life;
	}
    
    /**
     * Set new ranking
     * 
     * @return
     */
    public void setRanking(ArrayList<DaoPlayers> ranking) {
    	this.ranking = ranking;
	}
    
    /**
     * Get quantity of lives
     * 
     * @return
     */
    public void setLives(int life) {
    	this.life += life;
	}
    
    /**
     * Reset data
     */
    public void reset() {
    	score = 0;
        life = 3;
        ranking = DaoPlayers.getRecords();
	}
    
    /**
     * Draw ranking of the players
     * 
     */
    public void doDrawRecords(Graphics g, boolean over) {
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setColor(Color.BLACK);
        g2d.fillRect(243, 100, 310, 370);
        
        g2d.setColor(Color.WHITE);
        g2d.drawString("Ranking", 360, 140);
        
        int y = 180, position = 1;
        int[] x = {260, 300, 460, 505};
        
        for (DaoPlayers player : ranking) {
        	g2d.drawString(position + "º", x[0], y);
        	g2d.drawString(player.getNick() + "", x[1], y);
        	g2d.drawString(player.getScore() + "", x[2], y);
        	g2d.drawString(player.getTime() + "", x[3], y);
        	y += 30;
        	position++;
		}

        // If player is died
		if( over ){
	        g2d.setColor(Color.YELLOW);			
			g2d.drawString("Game over buddy :(", 320, (int) (Game.HEIGHT-110));
			g2d.drawString("Press [Space/Enter] to restart", 250, (int) (Game.HEIGHT-80));
		}
	}
    
    /**
    * Este método desenha o score 
    * 
    * @param  Graphics g    
    * @return     void
    */
    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        try{ 
        	InputStream file = Score.class.getResourceAsStream("/fonts/vt_regular.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, file);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
            font = font.deriveFont(Font.PLAIN,25);
            g2d.setFont(font);
            
        } catch (Exception e){
            System.out.println(e.toString());
        }   
        g2d.drawString("Score:" + this.score, 50, 52);
        g2d.drawString("Lives:" + this.life, 630, 52);
    }

    /**
    * Override do método que pinta este componente, pois ele é um JPanel.
    * 
    * @param  Graphics g    
    * @return     void
    */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
    
}
