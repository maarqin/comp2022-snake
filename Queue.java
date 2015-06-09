
/**
 * @author thomaz
 * @version 2015-06-09
 * @param <E>
 */
public class Queue<E> {
    
    private Node head;
    public static int length;
    
    public Queue() {
        this.head = null;
        length = 0;
    }
    
    /**
     * Adding new piece of Snake
     * 
     * @param node
     */
    public void add(Node node) {
        if( isEmpty() ){
            head = node;
            head.setLast(null);
        } else {
            node.setLast(head);
            head = node;
        }
        length++;
    }
    
    /**
     * Removing the first element of the list
     * 
     */
    public void  remove() {
        Node aux = head;
        if( aux.getLast() == null ){
            head = null;
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
     * Verify if contain something in the list
     * 
     * @return
     */
    private boolean isEmpty() {
        return ( head == null ) ? true : false;
    }
}
