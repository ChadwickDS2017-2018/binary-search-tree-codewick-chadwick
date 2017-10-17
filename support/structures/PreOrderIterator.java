package structures;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class PreOrderIterator<T> implements Iterator<T> {
	
	private final Deque<BinaryTreeNode<T>> stack; 
	
	public PreOrderIterator(BinaryTreeNode<T> root){
		stack = new LinkedList<BinaryTreeNode<T>>();
		stack.push(root);
	}

	@Override
	public boolean hasNext() {
		return !stack.isEmpty();
	}

	@Override
	public T next() {
		
		BinaryTreeNode<T> toVisit = stack.pop();
		//Push and pop are from the same side, like a stack, therefore it is only wise to push all of the right children first.
		//So all of the pops can be the left as they are the newest
		
		//popping the toVisit, goes on to the newest Left Child, after all, that one is the one you want to go to.
		//Also by popping the left child, the discarded toVisit is added to the list in the correct way.
		if(toVisit.hasRightChild()) {stack.push(toVisit.getRightChild());}
	
		if(toVisit.hasLeftChild()) {stack.push(toVisit.getLeftChild());}

		return toVisit.getData();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
