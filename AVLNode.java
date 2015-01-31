/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adsproject;

/**
 *
 * @author sarfaraz
 */
public class AVLNode {
    int key;
    int val;
    AVLNode left;
    AVLNode right;

    AVLNode parent;
    //0 means root
    //1 means left child of it's parent
    //2 means right child of it's parent
    int childLR=0;
    
    //height of the tree
    int height = 0;
    //balance factor: diff b/w the heights of left and right subtrees
    int bf = 0;
    
    /**
     * Constructor
     * @param k
     * @param v
     */
    public AVLNode(int k, int v) {
        key = k;
        val = v;
        left = null;
        right = null;
    }
    
    /**
     * Outputs the AVLNode in an easy to read format
     * @param level
     * @param dir
     * @return
     */
    public String toStringVerbose(int level, String dir) {
        String s = "";
        for(int i=0; i<level;i++) {
            s+=".";
        }
        s+= key + " " + dir + "[h:" + height + "]" + "[bf:" + bf + "]" + "[parent:" + ((parent!=null)?parent.key:"null") + "]" + "\n";
        if(left != null) {
            s+=left.toStringVerbose(level+1, "[L]");
        }
        
        if(right != null) {
            s+=right.toStringVerbose(level+1, "[R]");
        }
        return s;
    }
    
    /**
     * searches whether the current node or any children contain the provided key k
     * @param k
     * @return
     */
    public Boolean contains(int k) {
        Boolean found = false;
        if( key == k ) {
            found = true;
        } else if( k < key && left != null) {
            found = left.contains(k);
        } else if( k > key && right != null ) {
            found = right.contains(k);
        }
        return found;
    }
    
    /**
     * outputs the in order traversal or the node
     * @return
     */
    public String inOrder() {
        String s = "";
        if(left!=null)
            s+=left.inOrder();
        s+=val+" ";
        if(right!=null)
            s+=right.inOrder();
        return s;
    }
    
    /**
     * outputs the post order traversal or the node
     * @return
     */
    public String postOrder() {
        String s = "";
        if(left!=null)
            s+=left.postOrder();
        if(right!=null)
            s+=right.postOrder();
        s+=val+" ";        
        return s;
    }
}
