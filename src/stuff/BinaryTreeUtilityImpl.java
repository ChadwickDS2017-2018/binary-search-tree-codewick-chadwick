package stuff;

import java.util.Iterator;

import structures.BinaryTreeNode;
import structures.BinaryTreeUtility;
import structures.InOrderIterator;
import structures.PostOrderIterator;
import structures.PreOrderIterator;

public class BinaryTreeUtilityImpl<T> implements BinaryTreeUtility{
	int depth = 0;

	@Override
	public <T> Iterator<T> getPreOrderIterator(BinaryTreeNode<T> root) {
		if (root == null)
			throw new NullPointerException("root is null");

		return new PreOrderIterator<T>(root);
	}
	

	@Override
	public <T> Iterator<T> getInOrderIterator(BinaryTreeNode<T> root) {
		if (root == null)
			throw new NullPointerException("root is null");
		
		return new InOrderIterator<T>(root);
	}

	@Override
	public <T> Iterator<T> getPostOrderIterator(BinaryTreeNode<T> root) {
		if (root == null)
			throw new NullPointerException("root is null");
		
		
		return new PostOrderIterator<T>(root);
	}

	@Override
	public <T> int getDepth(BinaryTreeNode<T> root) {
		if (root == null)
			throw new NullPointerException("root is null, depth does not exist anymore");
		return getDepth(root, 0);
	}
	
	private <T> int getDepth(BinaryTreeNode<T> root, int depth) {
		//Call Recursive function on math.max()
		int nextDepth = depth;
		if (root.hasRightChild() && root.hasLeftChild()){
			return Math.max(getDepth(root.getRightChild(), nextDepth + 1), getDepth(root.getLeftChild(), nextDepth + 1));
		} else if (root.hasLeftChild()) {
			return getDepth(root.getLeftChild(), nextDepth + 1);
		} else if (root.hasRightChild()) {
			return getDepth(root.getRightChild(), nextDepth + 1);
		} 
		return nextDepth;
	}

	//Compare the depths of right and left...
	@Override
	public <T> boolean isBalanced(BinaryTreeNode<T> root, int tolerance) {
		if (root == null)
			throw new NullPointerException("root is null");
		if (tolerance < 0)
			throw new IllegalArgumentException("nodes cannot be " + tolerance +" distance apart or less");
		
		
		if (diff(getDepthRight(root, 0), getDepthLeft(root, 0)) <= tolerance) {
			if (root.hasLeftChild() && !root.hasRightChild()) {
				return isBalancedR(root.getLeftChild(), tolerance);
			}
			else if (root.hasRightChild() && !root.hasLeftChild()) {
				return isBalancedR(root.getRightChild(), tolerance);
			}
			else if (root.hasRightChild() && root.hasLeftChild()) {
				if (isBalancedR(root.getRightChild(), tolerance) && isBalancedR(root.getLeftChild(), tolerance)) {
					return true;
				} else {
					return false;
				}
			} else {
				return true;
			}
			
		}
		
		return false;
	}
	
	private <T> boolean isBalancedR(BinaryTreeNode<T> root, int tolerance) {
		//Compare the current node to depth of the entire branch,
		//Go through the entire branch until something returns false;
		if (diff(getDepthLeft(root, 0), getDepthRight(root, 0)) > tolerance) {
			return false;
		}
		
		if (root.hasRightChild() && !root.hasLeftChild()) {
			if (diff(getDepth(root.getRightChild()) + 1, 0) > tolerance) {
				return false;
		}

			
		} else if (root.hasLeftChild() && !root.hasRightChild()) {
			//Check at each turn if balanced or not...
			if (diff(getDepth(root.getLeftChild()) + 1, 0) > tolerance) {
				return false;
			}
			
		} else if (root.hasRightChild() && root.hasLeftChild()) {
			if (diff(getDepthLeft(root, 0), getDepthRight(root, 0)) > tolerance) {
				return false;
			}
		}
		
		return true;

	}

	private int diff (int a, int b) {
		return Math.abs(a - b);
	}
	
	
	
	private <T> int getDepthRight(BinaryTreeNode<T> root, int depth) {
		if (depth == 0 && root.hasRightChild() == false) {
			return depth;
		}
		
		if (depth == 0 && root.hasRightChild()) {
			return getDepthRight(root.getRightChild(), depth+1);
		}
		
		else if (depth != 0 && root.hasRightChild() && root.hasLeftChild()){
			return Math.max(getDepth(root.getRightChild(), depth + 1), getDepth(root.getLeftChild(), depth + 1));
			
		} else if (root.hasLeftChild() && depth != 0) {
			return getDepth(root.getLeftChild(), depth + 1);
		} else if (root.hasRightChild() && depth != 0) {
			return getDepth(root.getRightChild(), depth + 1);
		}
		return depth;
	}
	
	private <T> int getDepthLeft(BinaryTreeNode<T> root, int depth) {
		if (depth == 0 && root.hasRightChild() == false) {
			return depth;
		}
		
		if (depth == 0 && root.hasLeftChild()) {
			return getDepthRight(root.getLeftChild(), depth+1);
		}
		
		else if (root.hasRightChild() && depth != 0 && root.hasLeftChild()){
			return Math.max(getDepth(root.getRightChild(), depth + 1), getDepth(root.getLeftChild(), depth + 1));
		} else if (root.hasLeftChild() && depth != 0) {
			return getDepth(root.getLeftChild(), depth + 1);
		} else if (root.hasRightChild() && depth != 0) {
			return getDepth(root.getRightChild(), depth + 1);
		} 
		

		return depth;
	}
	@Override
	public <T extends Comparable<? super T>> boolean isBST(BinaryTreeNode<T> root) {
		if (root == null)
			throw new NullPointerException("Binary Tree Does not exist");
		return isBSTR(root);
	}
	
	private <T extends Comparable<? super T>> boolean isBSTR(BinaryTreeNode<T> root) {
		
		if (root.hasRightChild()) {
			
			if (root.getRightChild().getData().compareTo(root.getData()) > 0) {
				return isBSTR(root.getRightChild());
			}
				
			return false;
		}
	
		if (root.hasLeftChild()) {
			if (root.getLeftChild().getData().compareTo(root.getData()) < 0) {
				return isBSTR(root.getLeftChild());
			}
			return false;
		} 
		
		
		
		if (!root.hasLeftChild() && !root.hasRightChild()) {
			return true;
		}
		
		return false;
	}

}
