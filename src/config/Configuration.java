package config;

import structures.BinarySearchTree;
import structures.BinaryTreeNode;
import structures.BinaryTreeUtility;
import stuff.BinarySearchTreeImpl;
import stuff.BinaryTreeNodeImpl;
import stuff.BinaryTreeUtilityImpl;


/**
 * This class acts as a configuration file which tells the testing framework
 * which implementation you want us to use when we grade your assignment.
 * 
 * @author jddevaug
 * 
 */
public class Configuration {

	
	public static final String STUDENT_ID_NUMBER = "33811";
	

	public static <T> BinaryTreeNode<T> createBinaryTreeNode(BinaryTreeNode<T> left, T elem, BinaryTreeNode<T> right){
		return new BinaryTreeNodeImpl<T>(left, elem, right);
	}
	
	public static BinaryTreeUtility createBinaryTreeUtility(){
		return new BinaryTreeUtilityImpl();
	}
	
	public static <T extends Comparable<? super T>> BinarySearchTree<T> createBinarySearchTree(){
		return new BinarySearchTreeImpl<T>();
	}
	

}
