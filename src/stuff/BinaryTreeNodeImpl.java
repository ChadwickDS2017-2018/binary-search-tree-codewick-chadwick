package stuff;

import structures.BinaryTreeNode;

public class BinaryTreeNodeImpl<T> implements BinaryTreeNode<T>{

	
	BinaryTreeNode<T> left, right;
	T contents;
	
	public BinaryTreeNodeImpl(BinaryTreeNode<T> left, T contents, BinaryTreeNode<T> right) {
		this.left = left;
		this.contents = contents;
		this.right = right;
	}
	
	@Override
	public T getData() {
		// TODO Auto-generated method stub
		return contents; 
	}

	@Override
	public void setData(T data) {
		if (data == null)
			throw new NullPointerException("Data Is Null");
		// TODO Auto-generated method stub
		contents = data;
		
	}

	@Override
	public boolean hasLeftChild() {
		// TODO Auto-generated method stub
		return (left != null);
	}

	@Override
	public boolean hasRightChild() {
		// TODO Auto-generated method stub
		return (right != null);
	}

	@Override
	public BinaryTreeNode<T> getLeftChild() {
		// TODO Auto-generated method stub
		if (!hasLeftChild())
			throw new IllegalStateException("No Left Child");
		return left;
	}

	@Override
	public BinaryTreeNode<T> getRightChild() {
		if (!hasRightChild())
			throw new IllegalStateException("No Right Child");
		return right;
	}

	@Override
	public void setLeftChild(BinaryTreeNode<T> left) {
		if (left == null)
			throw new NullPointerException("Left Parameter is Null");
		this.left = left;
		
	}

	@Override
	public void setRightChild(BinaryTreeNode<T> right) {
		if (right == null)
			throw new NullPointerException("Right Parameter is Null");
		this.right = right;
	}

}
