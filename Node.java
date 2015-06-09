
/**
 * @author thomaz
 * @version 2015-06-09
 */
public class Node {

	private Snake data;
	private Node last;
	
	/**
	 * Construct a class with a snake data
	 * 
	 * @param data
	 */
	public Node(Snake data)
	{
		last = null;
		this.data = data;
	}

	public Snake getData() {
		return data;
	}
	
	public void setData(Snake data) {
		this.data = data;
	}
	
	public Node getLast() {
		return last;
	}
	
	public void setLast(Node last) {
		this.last = last;
	}
	
	
}
