/**
 * Author : Xinyi Zhu, Jin Seng Cheng
 * Date : 04/10/2019
 * Cinco Computer Consultants (CCC) project
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 *
 * This class is a linked list class which contain the head of the list and 
 * the size of the list and some supporting method for convince  
 *
 */
package linkedList;

import java.util.Comparator;
import java.util.Iterator;

public class MyLinkedList<T> implements Iterable<T> {

	private Node<T> head;
	private int size;
	Comparator<T> c;

	public void setC(Comparator<T> c) {
		this.c = c;
	}

	/**
	 * This function returns the size of the list, the number of elements currently
	 * stored in it.
	 * 
	 * @return
	 */
	public int getSize() {
		return this.size;
	}

	/**
	 * This function clears out the contents of the list, making it an empty list.
	 */
	public void clear() {
		this.head = null;
		this.size = 0;
	}

	/**
	 * This method adds the given instance to the beginning of the list.
	 * 
	 * add on empty
	 * 
	 * @param t
	 */
	public void insertAtHead(T item) {
		// create a new node
		Node<T> newHead = new Node<>(item);
		newHead.setNextNode(this.head);
		// set this new node as new head
		this.head = newHead;
		// increase the size of this list
		this.size++;
	}

	/**
	 * This method adds the given instance to the end of the list.
	 * 
	 * 
	 * add if not empty
	 * 
	 * @param item
	 */
	public void insertToEnd(T item) {
		// if this is the first node in the list, then call insert at head function
		if (this.size == 0) {
			this.insertAtHead(item);
		} else {
			// create a new node
			Node<T> newHead = new Node<>(item);
			// set the last one next node is the new node
			this.getNodeAtIndex(this.getSize() - 1).setNextNode(newHead);
			// increase the size
			this.size++;
		}
	}

	/**
	 * This is a private helper method that returns a corresponding to the given
	 * position. Implementing this method is optional but may help you with other
	 * methods.
	 * 
	 * @param position
	 * @return
	 */
	private Node<T> getNodeAtIndex(int index) {
		// error check for users if put node out of bound
		if (index >= this.size || index < 0) {
			System.out.println("index out of bounds");
			throw new IllegalArgumentException("index out of bounds");
		}

		Node<T> currentNode = this.head;
		for (int i = 0; i < index; i++) {
			// go to the next node(the index node):
			currentNode = currentNode.getNextNode();
		}
		return currentNode;
	}

	/**
	 * This is function that can retrieve the actual data in the node by using the
	 * function getNodeAtIndex and get his data
	 * 
	 * @param index
	 * @return
	 */
	public T getItemAtIndex(int index) {
		return this.getNodeAtIndex(index).getItem();
	}

	/**
	 * This is function that take the data and index and insert the data at the
	 * specific location
	 * 
	 * @param item index
	 * @return
	 */
	private void insertAtIndex(T item, int index) {
		// error check for users if put node out of bound
		if (index > this.size || index < 0) {
			System.out.println("index out of bounds");
			throw new IllegalArgumentException("index out of bounds");
		}
		// if it it wants insert at the first then call insertAtHead function
		if (index == 0) {
			this.insertAtHead(item);
			return;
		}

		// 1. Create a new node
		Node<T> newNode = new Node<>(item);
		// 2. Find the node u at index - 1 (the one right before index)
		Node<T> u = this.getNodeAtIndex(index - 1);
		// 3. make the new node point to u's next node
		newNode.setNextNode(u.getNextNode());
		// 4. make u point to the new node
		u.setNextNode(newNode);
		this.size++;
		return;
	}

	/**
	 * This is helper function that print the whole linked list to help user to
	 * check
	 * 
	 * 
	 * @return
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Node<T> currentNode = this.head;
		// using a loop to print each data
		while (currentNode != null) {
			sb.append(currentNode.getItem() + ", ");
			currentNode = currentNode.getNextNode();
		}
		return sb.toString();
	}

	/**
	 * This method removes the {@link Truck} from the given <code>position</code>,
	 * indices start at 0. Implicitly, the remaining elements' indices are reduced.
	 * 
	 * @param position
	 */
	public void remove(int position) {
		// error check for users if put node out of bound
		if (position >= this.size || position < 0) {
			System.out.println("index out of bounds");
			throw new IndexOutOfBoundsException("index out of bounds");
		}
		// delete the first one and only one left
		if (position == 0 && this.size == 1) {
			// then just clear out the entire list
			this.clear();
			// just delete the first one
		} else if (position == 0) {
			this.head = getNodeAtIndex(1);
			// delete the last one
		} else if (position == this.size - 1) {
			this.getNodeAtIndex(position - 1).setNextNode(null);
			// delete the middle one
		} else {
			// put the position - 1 and position + 1 's node together
			this.getNodeAtIndex(position - 1).setNextNode(getNodeAtIndex(position + 1));
		}
		this.size--;
	}

	/**
	 * This is helper function that can make this list iterable learn from Bourke's
	 * code
	 * 
	 */
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			Node<T> current = head;

			/**
			 * This method check if the node has the next node or not
			 */
			@Override
			public boolean hasNext() {
				if (current == null)
					return false;
				else
					return true;
			}

			/**
			 * This method is to get the next node
			 */
			@Override
			public T next() {
				T item = current.getItem();
				current = current.getNextNode();
				return item;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException("not implemented");
			}
		};
	}

	/**
	 * This method adds the given instance where it is belongs to
	 * 
	 * 
	 * 
	 * 
	 * @param item
	 * @param Comparator
	 */
	public void add(T item) {
		// get the final size in case the list will increase size
		final int size = this.size;
		// if the list is empty then just insert the first element
		if (this.size == 0) {
			this.insertAtHead(item);
			// compare with the first node, if item less than the head, insert at head
		} else if (c.compare(item, this.getItemAtIndex(0)) <= 0) {
			this.insertAtHead(item);
			// compare with the last node, if item greater than the head, insert at end
		} else if (c.compare(item, this.getItemAtIndex(size - 1)) >= 0) {
			this.insertToEnd(item);
			// compare with the middle nodes, the item will found it greater than previous
			// one, but less then next one
		} else {
			// loop through the from the second one to the second last one
			for (int i = 0; i < size - 1; i++) {
				// once the item will found it greater than previous
				// one, but less then next one, insert at that place
				if (c.compare(item, this.getItemAtIndex(i)) >= 0 && c.compare(item, this.getItemAtIndex(i + 1)) < 0) {
					this.insertAtIndex(item, i + 1);
					// after insert, I just break out of the loop
					break;
				}
			}
		}
	}

}
