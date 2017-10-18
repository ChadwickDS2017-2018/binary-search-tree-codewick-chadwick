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
		size++;
		if (isEmpty()) {
			root = new BinaryTreeNodeImpl<T>(null, toAdd, null);
			
			return this;
		} else {
			
			BinaryTreeNode<T> current = root;
			return add(toAdd, current);
		}
	}


	private BinarySearchTree<T> add(T toAdd, BinaryTreeNode<T> node) {

		if (toAdd.compareTo(node.getData()) < 0) {
			if (!node.hasLeftChild()) {
				BinaryTreeNode<T> temp = new BinaryTreeNodeImpl<T> (null, toAdd, null);
				node.setLeftChild(temp);
			} else {
				add(toAdd, node.getLeftChild());
			}
		} else {
			if (!node.hasRightChild()) {
				BinaryTreeNode<T> temp = new BinaryTreeNodeImpl<T> (null, toAdd, null);
				node.setRightChild(temp);
				return this;
			} else {
				add(toAdd, node.getRightChild());
			}
		}
		return this;
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
		return root;
	}

	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return new InOrderIterator<T>(root);
	}

	@Override
	public boolean remove(T toRemove) {
		if (isEmpty()) {
			return false;
		}
		else {
			if (toRemove.equals(root.getData())) {
				root = replacement(root);
				size--;
				return true;
			}
			else {
				if (toRemove.compareTo(root.getData()) < 0) {
					if (root.hasLeftChild()) {
						return remove(toRemove, root.getLeftChild(), root);
					}
				} else {
					if (root.hasRightChild()) {
						return remove(toRemove, root.getRightChild(), root);
					}

				}
			}
		}
		return false;
	}

	private boolean remove(T target, BinaryTreeNode<T> current, BinaryTreeNode<T> parent) {
		if(current == null)
			return false;
		
		if (target.equals(current.getData())) {
				BinaryTreeNode<T> temp = replacement(current);
				if (parent.getRightChild() == current) {
					parent.setRightChild(temp);
				} else {
					parent.setLeftChild(temp);
				}
				size--;
				return true;
			}
			else {
				
				if (target.compareTo(current.getData()) < 0) {
					return remove(target, current.getLeftChild(), current);
				} else {
					return remove(target, current.getRightChild(), current);
				}
			}
			
	}

	private BinaryTreeNode<T> replacement(BinaryTreeNode<T> node) {
		BinaryTreeNode<T> result = null;

		if (!node.hasLeftChild() && !node.hasRightChild()) {
			result = null;
		} else if (node.hasLeftChild() && !node.hasRightChild()) {
			result = node.getLeftChild();
		} else if (!node.hasLeftChild() && node.hasRightChild()) {
			result = node.getRightChild();
		} else {
			BinaryTreeNode<T> current = node.getRightChild();
			BinaryTreeNode<T> parent = node;

			while (current.hasLeftChild()) {
				parent = current;
				current = current.getLeftChild();
			}

			current.setLeftChild(node.getLeftChild());
			if (!node.getRightChild().equals(current)) {
				if (current.hasRightChild()) {
				parent.setLeftChild(current.getRightChild());
				}
				current.setRightChild(node.getRightChild());
			}
			result = current;
		}	
		return result;
	}


	@Override
	public boolean contains(T toFind) {
		if (toFind == null) {
			throw new NullPointerException();
		}
		return contains(toFind, root);
	}

	public boolean contains(T toFind, BinaryTreeNode<T> branch) {
		if (branch != null) {
			if (toFind.equals(branch.getData())) {
				return true;
			} else {
				if(toFind.compareTo(branch.getData()) < 0) {
					if (branch.hasLeftChild()) 
						return contains(toFind, branch.getLeftChild());
					else
						return false;
				}
				else {
					if (branch.hasRightChild())
						return contains(toFind, branch.getRightChild());
					else
						return false;
				}
			}

		}
		return false;
	}
}

