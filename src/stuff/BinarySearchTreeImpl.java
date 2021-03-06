package stuff;

import java.util.Iterator;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import structures.BinarySearchTree;
import structures.BinaryTreeNode;
import structures.BinaryTreeNodeTest;
import structures.InOrderIterator;

public class BinarySearchTreeImpl<T extends Comparable<? super T>> implements BinarySearchTree<T> {
    int size = 0;
    public BinaryTreeNode<T> root;

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
                BinaryTreeNode<T> foundParent = findParent(null, root, toRemove);
                if (foundNode == null || (foundParent == null && !toRemove.equals(root.getData()))) {
                    return false;
                }

                else {
                    replace(foundNode, foundParent);
                    size--;
                    return true;
                }

            }

        } else {
            return false;
        }
    }

    private void replaceNodeTwoChildren(BinaryTreeNode<T> node) {
        BinaryTreeNode<T> current = node.getRightChild();
        BinaryTreeNode<T> parent = node;
        
        while(current.hasLeftChild()) {
            parent = current;
            current = current.getLeftChild();
        }
        
        if (node.hasRightChild() && node.getRightChild().equals(current)) {
           if (node.hasLeftChild()) {
               current.setLeftChild(node.getLeftChild());
           } else {
               current.setLeftChild(null);
           }
           node = current;
           
           System.out.println("YET");
           System.out.println(node.getData());
           System.out.println(node.getLeftChild().getData());
        } else {
            //Why?
            if (current.hasLeftChild()) {
                parent.setLeftChild(current.getRightChild());
            } else {
                parent.setLeftChild(null);
            }
            
            if (node.hasRightChild()) {
                current.setRightChild(node.getRightChild());
            } else {
                current.setRightChild(null);
            }
            if (node.hasLeftChild()) {
                current.setLeftChild(node.getLeftChild());
            } else {
                current.setLeftChild(null);
            }
            node = current;
        }
        
        }

    private void replaceNodeNoChildren(BinaryTreeNode<T> node, BinaryTreeNode<T> parent) {
        if (parent.hasLeftChild() && parent.getLeftChild().equals(node)) {
            parent.setLeftChild(null);
        } 
        
        if (parent.hasRightChild() && parent.getRightChild().equals(node)) {
            parent.setRightChild(null);
        }
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

    private BinaryTreeNode<T> findParent(BinaryTreeNode<T> parent, BinaryTreeNode<T> current, T toRemove) {
        if (toRemove.equals(current.getData())) {
            return parent;
        } else {
            if (toRemove.compareTo(current.getData()) > 0) {
                if (current.hasRightChild()) {
                    return findParent(current, current.getRightChild(), toRemove);
                } else {
                    return null;
                }
            } else {
                if (current.hasLeftChild()) {
                    return findParent(current, current.getLeftChild(), toRemove);
                } else {
                    return null;
                }
            }
        }
    }


    private void replace(BinaryTreeNode<T> node, BinaryTreeNode<T> parent) {
        if (!node.hasLeftChild() && !node.hasRightChild() && node.hasNoChildren()) {
            System.out.println("No Nodes");
            replaceNodeNoChildren(node, parent);
        }
        else if (node.hasLeftChild() && !node.hasRightChild()) {
            System.out.println("Only Left");
            replaceNodeOneChild(node, parent);
        } else if (!node.hasLeftChild() && node.hasRightChild()) {
            System.out.println("Only Right");
            replaceNodeOneChild(node, parent);
        } else {
            System.out.println("Two Nodes");
            replaceNodeTwoChildren(node); //Already Checked, Already Good
        }
    }

    private void replaceNodeOneChild(BinaryTreeNode<T> node, BinaryTreeNode<T> parent) {
        if (parent.hasLeftChild() && parent.getLeftChild().equals(node)) {
            //Where is the node?
            if (node.hasRightChild()) {
                parent.setLeftChild(node.getRightChild());
            } else {
                parent.setLeftChild(node.getLeftChild());
            }
        }
        
        if (parent.hasRightChild() && parent.getRightChild().equals(node)) {
            //Where is the node?
            if (node.hasRightChild()) {
                parent.setRightChild(node.getRightChild()); 
            } else {
                parent.setRightChild(node.getLeftChild()); 
            }
        }


    }

    private void replaceRoot() {
        if (root.hasNoChildren()) {
            root = null;
        } else if (root.hasLeftChild() && !root.hasRightChild()) {
            root = root.getLeftChild();
        } else if (!root.hasLeftChild() && root.hasRightChild()) {
            root = root.getRightChild();
        } else {
            BinaryTreeNode<T> current = root.getRightChild();
            BinaryTreeNode<T> parent = root;
            
            while(current.hasLeftChild()) {
                parent = current;
                current = current.getLeftChild();
            }
            
            if (root.hasRightChild() && root.getRightChild().equals(current)) {
               if (root.hasLeftChild()) {
                   current.setLeftChild(root.getLeftChild());
               } else {
                   current.setLeftChild(null);
               }
               root = current;
               
               System.out.println("YET");
               System.out.println(root.getData());
               System.out.println(root.getLeftChild().getData());
            } else {
                //Why?
                if (current.hasLeftChild()) {
                    parent.setLeftChild(current.getRightChild());
                } else {
                    parent.setLeftChild(null);
                }
                
                if (root.hasRightChild()) {
                    current.setRightChild(root.getRightChild());
                } else {
                    current.setRightChild(null);
                }
                if (root.hasLeftChild()) {
                    current.setLeftChild(root.getLeftChild());
                } else {
                    current.setLeftChild(null);
                }
                root = current;
            }
        }
    }

}

