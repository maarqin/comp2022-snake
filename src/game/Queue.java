package game;

/**
 * @author thomaz
 * @version 2015-06-09
 * @param <E>
 */
public class Queue<E> {
    
	private Snake snake;
    public static int length;
    
    public Queue() {
        this.snake = null;
        length = 0;
    }
    
    /**
     * Adding new piece of Snake
     * 
     * @param node
     */
    public void add(Snake node) {
        if( isEmpty() ){
        	snake = node;
        	snake.setLast(null);
        } else {
            node.setLast(snake);
            snake = node;
        }
        length++;
    }
    
    /**
     * Removing the first element of the list
     * 
     */
    public void  remove() {
    	Snake aux = snake;
        if( aux.getLast() == null ){
        	snake = null;
            return;
        }
        while( aux.getLast() != null ){
            if( aux.getLast().getLast() == null ) {
                aux.setLast(null);
                break;
            }
            aux = aux.getLast();
        }
        length--;
    }
    
    /**
     * Remove all datas
     */
    public void removeAll() {
        this.snake = null;
        length = 0;
    }
    
    /**
     * Hide tail' snake when it die
     * 
     */
    public void cleanAll() {
    	Snake aux = snake;
        if( aux.getLast() == null ){
        	snake.setX(-1).setY(-1);
            return;
        }
        while( aux.getLast() != null ){
        	aux.setX(-1).setY(-1);
            aux = aux.getLast();
        }
	}
    
    /**
     * Return result until then
     * 
     * @return
     */
    public Snake getSnake() {
		return snake;
	}

    /**
     * Verify if contain something in the list
     * 
     * @return
     */
    private boolean isEmpty() {
        return ( snake == null ) ? true : false;
    }
    
}
