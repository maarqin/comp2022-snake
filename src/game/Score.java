package game;
import java.awt.*;

import javax.swing.*;

import java.io.InputStream;

/**
 * Classe JPanel do score que fica no canto superior direito do jogo.
 * 
 * @author mhadaniya
 * @version 20/05
 */
public class Score extends JPanel {

	private static final long serialVersionUID = -5492621870251449988L;
	private int score;
    private int life;
    private Font font;
    
    /**
     * Constructor for objects of class Score
     */
    public Score()
    {
        score = 0;
        life = 0;
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
     * Reset score-to-zero
     */
    public void resetScore() {
		this.score = 0;
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
