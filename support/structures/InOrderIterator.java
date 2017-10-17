package structures;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class InOrderIterator<T> implements Iterator<T> {
	private final Deque<BinaryTreeNode<T>> stack;
	private BinaryTreeNode<T> front;
	public InOrderIterator(BinaryTreeNode<T> root) {
		stack = new LinkedList<BinaryTreeNode<T>>();
		front = root;
	}
	@Override
	public boolean hasNext() {
		
		return !stack.isEmpty() || front != null;
	}


	@Override
	public T next() {
		while (front != null) {
			stack.push(front);
			if (front.hasLeftChild()) {
				front = front.getLeftChild();
			}
			else {
				front = null;
			}
		}
		
		BinaryTreeNode<T> whilelooplol = stack.pop();
		if (whilelooplol.hasRightChild()) {
			front = whilelooplol.getRightChild();
		} else {
			front = null;
		}

		return whilelooplol.getData();

	}


}
