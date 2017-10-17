package structures;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class PostOrderIterator<T> implements Iterator<T>{

	private final Deque<BinaryTreeNode<T>> stack; 
	private BinaryTreeNode<T> lastNode, node;
	
	public PostOrderIterator(BinaryTreeNode<T> root) {
		stack = new LinkedList<BinaryTreeNode<T>>();
		lastNode = null;
		node = root;
	}
	
	@Override
	public boolean hasNext() {
		return (!stack.isEmpty() || node != null);
	}

	@Override
	public T next() {
		BinaryTreeNode<T> poppyBoy = null;
	while (node != null) {
			stack.push(node);
			if (node.hasLeftChild()) {
			node = node.getLeftChild();
			} else {
			node = null;
			}

	}
		
	BinaryTreeNode<T> peekyBreeki = stack.peek();
	
	if (peekyBreeki.hasRightChild() && lastNode != peekyBreeki.getRightChild()) {
		
		node = peekyBreeki.getRightChild();
		return next();
	} 
	poppyBoy = stack.pop();
	lastNode = poppyBoy;		
		
		return poppyBoy.getData();
	}
	

	
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
	
	

}
