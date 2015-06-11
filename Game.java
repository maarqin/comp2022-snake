import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Game extends JFrame {

	private static final long serialVersionUID = 6349947094032773733L;
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
     * @param uri
     * @return
     */
    public static BufferedImage loadImages(String uri) {
    	BufferedImage img = null;

    	Object o = new Object();
        String file = o.getClass().getResource(uri).getPath();    	
		try {
			img = ImageIO.read(new File(file));
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
