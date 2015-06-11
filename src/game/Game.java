package game;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Game extends JFrame {

	private static final long serialVersionUID = 2452445843043196771L;
	public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    
    public Game(){
        
        // Adiciona o JPanel do jogo
        add(new Board());
        // Define a saida da aplicação
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Define o tamanho da janela
        setSize(new Dimension(WIDTH,HEIGHT));
        // A localização
        setLocationRelativeTo(null);
        // O titulo da janela
        setTitle("Snake");
        // Impede o redimensionamento da janela
        setResizable(false);
        // Mostra a janela
        setVisible(true);
        
    }
    
    /**
     * Function response to load all images of project
     * 
     * @param url
     * @return
     */
    public static BufferedImage loadImages(String url) {
    	BufferedImage img = null;
    	
    	URL file = Game.class.getResource(url);
   		try {
			img = ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return img;
	}
    
    /**
     * 
     * 
     * @param args
     */
    public static void main(String args[]){
        new Game();
    }
    
}
