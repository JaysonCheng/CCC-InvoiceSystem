/**
 * Author : Xinyi Zhu, Jin Seng Cheng
 * Date : 04/14/2019
 *Cinco Computer Consultants (CCC) project
 * 
 * This class is Node class which helps me to set up the linked list data structure 
 * Each Node has contain the actual data type and next node which make them connect them together 
 * This Class contains basic constructor and setter and getter
 */
package linkedList;

public class Node<T> {

	private T item;
	private Node<T> nextNode;

	public Node(T item) {
		this.item = item;
		this.nextNode = null;
	}

	public Node<T> getNextNode() {
		return nextNode;
	}

	public void setNextNode(Node<T> nextNode) {
		this.nextNode = nextNode;
	}

	// Return the actual data type in the node
	public T getItem() {
		return item;
	}

}