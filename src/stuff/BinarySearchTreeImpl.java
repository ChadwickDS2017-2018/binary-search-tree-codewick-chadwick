package stuff;

import java.util.Iterator;

import structures.BinarySearchTree;
import structures.BinaryTreeNode;
import structures.BinaryTreeNodeTest;
import structures.InOrderIterator;

public class BinarySearchTreeImpl<T extends Comparable<? super T>> implements BinarySearchTree<T> {
	int size = 0;
	BinaryTreeNode<T> root;

	public BinarySearchTreeImpl() {
		size = 0;
		root = null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return (root == null || size == 0);
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

	@Override
	public BinarySearchTree<T> add(T toAdd) {
		if (toAdd == null) {
			throw new NullPointerException();
		}
		
		
		if (isEmpty()) {
			root = new BinaryTreeNodeImpl<T>(null, toAdd, null);
		} else {
		root = add(toAdd,  root);
		}
		size++;
		return this;
	}
	
	private BinaryTreeNode<T> add(T toAdd, BinaryTreeNode<T> node) {
		BinaryTreeNode<T> leftTree = null;
		BinaryTreeNode<T> rightTree = null;
		BinaryTreeNode<T> toAddNode = new BinaryTreeNodeImpl<T>(null, toAdd, null);
		if (node.hasRightChild()) {
			rightTree = node.getRightChild();
		} if (node.hasLeftChild()) { 
			leftTree = node.getLeftChild();
		}
		
		
		if (toAdd.compareTo(node.getData()) > 0) {
			if (rightTree != null) {
				add(toAdd, rightTree);
			} else {
				node.setRightChild(toAddNode);
			}
		} else {
			if (leftTree != null) {
				add(toAdd, leftTree);
			} else {
				node.setLeftChild(toAddNode);
			}
		}
		//Go Down the Right Tree if toAdd > node.getData();
		return node;
	}

	@Override
	public boolean remove(T toRemove) {
		if (toRemove == null)
			throw new NullPointerException("Ping!");
		
		if (!isEmpty()) {
			if (toRemove.equals(root.getData())) {
				replaceRoot();
				size--;
				return true;
			} 
			
			else {
			BinaryTreeNode<T> foundNode = find(root, toRemove);
			BinaryTreeNode<T> foundParent = findParent(root, toRemove);
			
			if (foundNode == null || foundParent == null) {
				return false;
			}
			
			else {
				replace(foundNode, foundParent);
				size--;
				return true;
			}
			
			}
				
		}
		return false;
	}
	
	private BinaryTreeNode<T> find(BinaryTreeNode<T> startingNode, T toRemove) {
		if (toRemove.equals(startingNode.getData())) {
			return startingNode;
		} else {
			if (toRemove.compareTo(startingNode.getData()) > 0) {
				if (startingNode.hasRightChild()) {
					return find(startingNode.getRightChild(), toRemove);
				} else {
					return null;
				}
			} else {
				if (startingNode.hasLeftChild()) {
					return find(startingNode.getLeftChild(), toRemove);
				} else {
					return null;
				}
			}
		}
	}
	
	private BinaryTreeNode<T> findParent(BinaryTreeNode<T> startingNode, T toRemove) {
		if (startingNode.hasRightChild()) {
			if (toRemove.equals(startingNode.getRightChild().getData())) {
				return startingNode;
			}
		} else if (startingNode.hasLeftChild()) {
			if (toRemove.equals(startingNode.getLeftChild().getData())) {
				return startingNode;
			}
		}	
		
		else {
			if (toRemove.compareTo(startingNode.getData()) > 0) {
				if (startingNode.hasRightChild()) {
					return findParent(startingNode.getRightChild(), toRemove);
				}
			} else {
				if (startingNode.hasLeftChild()) {
					return findParent(startingNode.getLeftChild(), toRemove);
				}
			}
		}
		return null;
	}
	

	private void replace(BinaryTreeNode<T> node, BinaryTreeNode<T> parent) {
		
		if (node.hasRightChild() && !node.hasLeftChild()) {
			System.out.println("Standard Right");
			replaceNodeOneChild(node, parent);
		} else if (node.hasLeftChild() && !node.hasRightChild()) {
			System.out.println("Standard Left");
			replaceNodeOneChild(node, parent);
		} else if (node.hasRightChild() && node.hasLeftChild()) {
			System.out.println("Standard 2");
			replaceNodeTwoChildren(node.getRightChild(), node);
		} else {
			System.out.println("Standard Node");
			replaceNodeNoChildren(node, parent);
		}
	}

	private void replaceNodeOneChild(BinaryTreeNode<T> node, BinaryTreeNode<T> parent) {
		BinaryTreeNode<T> left = null, right = null;
		
		if (node.hasLeftChild()) {
			left = node.getLeftChild();
		} 
		
		if (node.hasRightChild()) {
			right = node.getRightChild();
		}
		
		if (parent.hasRightChild()) {
			if (parent.getRightChild() == node) {
				if (left != null && right == null) {
					parent.setRightChild(left);
				} else if (left == null && right != null){
					parent.setRightChild(right);
				}
			}
		} else if (parent.hasLeftChild()) {
			if (parent.getLeftChild() == node) {
				if (left != null && right == null) {
					parent.setLeftChild(left);
				} else if (left == null && right != null){
					parent.setLeftChild(right);
				}
			}
		}
		
		
	}
	
	private void replaceNodeTwoChildren(BinaryTreeNode<T> node, BinaryTreeNode<T> parent1) {
		BinaryTreeNode<T> current = node;
		BinaryTreeNode<T> parent = parent1;
		
		while(current.hasLeftChild()) {
			parent = current;
			current = current.getLeftChild();
		}
		
		current.setLeftChild(node.getLeftChild());
		if (node.getRightChild() != current) {
			parent.setLeftChild(current.getLeftChild());
			current.setRightChild(node.getRightChild());
		}
		System.out.println("RIGHT BEFORE I ERROR!");
	}

	
	private void replaceNodeNoChildren(BinaryTreeNode<T> node, BinaryTreeNode<T> parent) {
		if (parent.hasRightChild()) {
			if (parent.getRightChild() == node) {
			parent.setRightChild(null);
			}
		} else {
			if (parent.getLeftChild() == node) {
			parent.setLeftChild(null);
			}
		}
		
	}
	
	private void replaceRoot() { //Working as intended.
		BinaryTreeNode<T> left = null, right = null;
		if (root.hasRightChild()) {
			right = root.getRightChild();
		} 
		if (root.hasLeftChild()) {
			left = root.getLeftChild();
		}
		
		if (right != null && left == null) {
			root = right;
		} else if (left == null && right != null) {
			root = left;
		} else if (left != null && right != null) {
			BinaryTreeNode<T> current = root.getRightChild();
			BinaryTreeNode<T> parent = root;
			
			while(current.hasLeftChild()) {
				parent = current;
				current = current.getLeftChild();
			}
			
			current.setLeftChild(root.getLeftChild());
			if (root.getRightChild() != current) {
				parent.setLeftChild(current.getLeftChild());
				current.setRightChild(root.getRightChild());
			}
			System.out.println("Shoot yourself");
		} else if (left == null && right == null){
			root = null;
		}
	}
	
	
}

