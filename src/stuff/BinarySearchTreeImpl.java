package stuff;

import java.util.Iterator;

import structures.BinarySearchTree;
import structures.BinaryTreeNode;
import structures.InOrderIterator;

public class BinarySearchTreeImpl<T extends Comparable<? super T>> implements BinarySearchTree<T> {
	int size = 0;
	BinaryTreeNode<T> root;
	
	public BinarySearchTreeImpl() {
		size = 0;
		root = null;
	}

	@Override
	public BinarySearchTree<T> add(T toAdd) {
		if (toAdd == null)
			throw new NullPointerException("Adderthing is null");
		if (isEmpty()) {
			root = new BinaryTreeNodeImpl<T>(null, toAdd, null);
		}
		
		BinaryTreeNode<T> current = root;
		size++;
		return add(toAdd, current);
		
//		BinaryTreeNode<T> temp = new BinaryTreeNodeImpl<T> (null, toAdd, null);
//		T comparableElement = toAdd;
//		if (isEmpty())
//		      root = temp;
//		else {
//		      BinaryTreeNode<T> current = root;
//		      boolean added = false;
//		      while (!added)
//		      {
//		         if (toAdd.compareTo(current.getData()) < 0)
//		         {
//		            if (!current.hasLeftChild())
//		            {
//		               current.setLeftChild(temp);
//		               added = true;
//		               System.out.println("Added " + toAdd);
//		            }
//		            else
//		               current = current.getLeftChild();
//		} else {
//		            if (!current.hasRightChild())
//		            {
//		               current.setRightChild(temp);
//		               added = true;
//		               System.out.println("Added " + toAdd);
//		            }
//		            else
//		               current = current.getRightChild();
//		} 
//		        }
//		}
		   
		//size++; 
		//213567
		//4213567
		//return add(toAdd, root);
		}
		
	
	private BinarySearchTree<T> add(T toAdd, BinaryTreeNode<T> node) {
	
		if (toAdd.compareTo(node.getData()) < 0) {
			if (!node.hasLeftChild()) {
				BinaryTreeNode<T> temp = new BinaryTreeNodeImpl<T> (null, toAdd, null);
				node.setLeftChild(temp);
				System.out.println("added " + toAdd);
				return this;
			} else {
				add(toAdd, node.getLeftChild());
			}
		} else {
			if (!node.hasRightChild()) {
				BinaryTreeNode<T> temp = new BinaryTreeNodeImpl<T> (null, toAdd, null);
				node.setRightChild(temp);
				System.out.println("added " + toAdd);
			} else {
				add(toAdd, node.getRightChild());
			}
		}
		return this;
	}
	
	@Override
	public boolean contains(T toFind) {
		if (toFind == null)
			throw new NullPointerException("Containerthing is null");
		if (isEmpty())
			return false;
		return contains(toFind, root);
	}
	
	public boolean contains(T toFind, BinaryTreeNode<T> current) {
		if (current.getData().equals(toFind)) {
			return true;
		}
		
		if (current.hasLeftChild()) {
			return contains(toFind, current.getLeftChild());
		}
		else if (current.hasRightChild()) {
			return contains(toFind, current.getRightChild());
		}
		return false;
	}

	

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return (root == null);
	}

	@Override
	public T getMinimum() {
		if (isEmpty())
			throw new IllegalStateException("Minimum is empty");
		
		return getMinimum(root, root.getData());
	}
	
	private T getMinimum(BinaryTreeNode<T> node, T lowest) {
		// if root.getData < lowest, update value.
		
		if (node != null) {
			if (node.getData().compareTo(lowest) < 0) {
				lowest = node.getData();
			}
		}
		
		if (node.hasLeftChild()) {
			//Traverse down left branch
			
			return getMinimum(node.getLeftChild(), lowest);
		}
		
		else if (node.hasRightChild()) {
			//Traverse down right branch
			return getMinimum(node.getRightChild(), lowest);
		}
		
		return lowest;
	}

	@Override
	public T getMaximum() {
		if (isEmpty())
			throw new IllegalStateException("Maximum is empty");
		
		return getMaximum(root, root.getData());
	}
	
	private T getMaximum(BinaryTreeNode<T> node, T largest) {
		
		if (node != null) {
			if (node.getData().compareTo(largest) > 0) {
				largest = node.getData();
			}
		}
		
		
		if (node.hasRightChild()) {
			return getMaximum(node.getRightChild(), largest);
		} else if (node.hasLeftChild()) {
			return getMaximum(node.getLeftChild(), largest);
		}
		
		return largest;
	}

	@Override
	public BinaryTreeNode<T> toBinaryTreeNode() {
		if (isEmpty())
			throw new IllegalStateException("Node thing is empty");
		return null;
	}

	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return new InOrderIterator<T>(root);
	}
	
	@Override
	public boolean remove(T toRemove) {
		return false;
	}
}

	