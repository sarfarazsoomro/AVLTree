/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adsproject;

/**
 *
 * @author sarfaraz
 */
public class AVLTree {
    AVLNode t;
    int count = 0;
    /**
     * Constructor
     * @param k: key
     * @param v: value
     */
    public AVLTree(int k, int v) {
        t = new AVLNode(k,v);
        count++;
    }
    
    /**
     * Insert a key value pair
     * @param k: key
     * @param v: value
     */
    public void insert(int k, int v) {
        insertNd(k, v);
        count++;
    }
    
    /**
     * Outputs the tree structure in easy to read format
     * @param level
     * @param dir
     * @return
     */
    public String toStringVerbose(int level, String dir) {
        return t.toStringVerbose(level, dir);
    }
    
   /**
     * Insert a new key/value pair into the tree.
     * Most of the heavy lifting done here
     * @param k: key
     * @param v: value
     */
    public void insertNd(int k, int v) {
        //since we may be switching nodes, keep the reference to the node is a new var
        AVLNode temp = t;
        Boolean found = false;
        //search for the node where k/v need to be inserted. Node temp being changed
        while(!found) {
            if( k < temp.key ) {
                //left subtree
                if( temp.left == null )
                    found = true;
                else {
                    temp = temp.left;
                }
            } else if( k > temp.key ) {
                //right subtree
                if( temp.right == null )
                    found = true;
                else {
                    temp = temp.right;
                }                
            }
        }
        
        //now we have the node where k needs to be inserted either as a left or
        //a right subtree
        if( k < temp.key ) {
            temp.left = new AVLNode(k,v);
            temp.left.parent = temp;
        } else if( k > temp.key ) {
            temp.right = new AVLNode(k,v);
            temp.right.parent = temp;
        }
        
        //now going up fixing heights
        while( temp != null ) 
        {
            //new height is the max of height of two subtrees plus 1
            if( ((temp.left!=null)?temp.left.height:0) >= ((temp.right!=null)?temp.right.height:0) ) {
                temp.height = ((temp.left!=null)?temp.left.height:0) + 1;
            } else {
                temp.height = ((temp.right!=null)?temp.right.height:0) + 1;
            }
            //calculate balance factor of temp as well.
            if( temp.left == null ) {
                temp.bf = 0-temp.height;
            } else if( temp.right == null ) {
                temp.bf = temp.height;
            } else {
                temp.bf = temp.left.height - temp.right.height;
            }
            
            //if an imbalance is detected 
            //note that as soon as an imbalance is detected i.e. bf=2 or bf=-2
            //it is going to be fixed and prevented from progressing upwards in
            //the tree.
            //between here and going up one level, we need to fix the imbalance
            if( temp.bf == 2 ) {
                //LL or LR imbalance
                if( temp.parent == null ) {
                    //root node
                    if( temp.left.left !=null && temp.left.left.contains(k)) {
                        //LL Imbalance
                        //fix by rotating
                        AVLNode A = temp;
                        AVLNode B = temp.left;

                        B.parent = null;

                        t=B;

                        A.left = B.right;
                        if( A.left != null ) {
                            A.left.parent = A;
                        }
                        B.right = A;
                        A.parent = B;

                        A.height = getHeight(A);
                        B.height = getHeight(B);

                        A.bf = getBF(A);
                        B.bf = getBF(B);
                    } else if (temp.left.right.contains(k) ) {
                        //LR Imbalance
                        //rotation follows
                        AVLNode A = temp;
                        AVLNode B = temp.left;
                        AVLNode C = temp.left.right;
                        
                        B.parent = C;
                        C.parent = null;
                        A.parent = C;
                        
                        B.right = C.left;
                        if(B.right != null) {
                            B.right.parent = B;
                        }
                        A.left  = C.right;
                        if( A.left != null ) {
                            A.left.parent = A;
                        }
                        
                        C.left = B;
                        C.right = A;
                        
                        t=C;

                        A.height = getHeight(A);
                        B.height = getHeight(B);
                        C.height = getHeight(C);
                        A.bf = getBF(A);
                        B.bf = getBF(B);
                        C.bf = getBF(C);
                    }
                } else {
                    //not root
                    if(temp.left.left !=null && temp.left.left.contains(k)) {
                        //LL
                        //keeps track of whether temp is a left or a right child of its parent
                        Boolean lChild = true;
                        AVLNode tempParent = temp.parent;
                        //since this is a LL or LR imbalance, we know that 
                        if( tempParent.left == temp ) {
                            lChild = true;
                        } else if( tempParent.right == temp ) {
                            lChild = false;
                        }
                        //works but need to update heights and balance factors here.
                        AVLNode A = temp;
                        AVLNode B = temp.left;

                        B.parent = tempParent;

                        //need to figure out whether B goes into the left or right of the temp parent
                        if(lChild)
                            tempParent.left = B;
                        else
                            tempParent.right = B;

                        A.left = B.right;
                        if(A.left!=null) {
                            A.left.parent = A;
                        }
                        
                        B.right = A;
                        A.parent = B;

                        A.height = getHeight(A);
                        B.height = getHeight(B);

                        A.bf = getBF(A);
                        B.bf = getBF(B);
                    } else if (temp.left.right.contains(k)) {
                        //LR Imbalance
                        //keeps track of whether temp is a left or a right child of its parent
                        Boolean lChild = true;
                        AVLNode tempParent = temp.parent;
                        //since this is a LL or LR imbalance, we know that 
                        if( tempParent.left == temp ) {
                            lChild = true;
                        } else if( tempParent.right == temp ) {
                            lChild = false;
                        }
                        //works but need to update heights and balance factors here.
                        AVLNode A = temp;
                        AVLNode B = temp.left;
                        AVLNode C = temp.left.right;
                        
                        B.parent = C;
                        A.parent = C;
                        
                        A.left = C.right;
                        if( A.left!=null ) {
                            A.left.parent = A;
                        }
                        B.right = C.left;
                        if(B.right !=null) {
                            B.right.parent = B;
                        }
                        
                        C.left = B;
                        C.right = A;
                        C.parent = tempParent;
                        //need to figure out where B goes into the left or right of the temp parent
                        if(lChild)
                            tempParent.left = C;
                        else
                            tempParent.right = C;

                        A.height = getHeight(A);
                        B.height = getHeight(B);
                        C.height = getHeight(C);
                        A.bf = getBF(A);
                        B.bf = getBF(B);
                        C.bf = getBF(C);
                    }
                }
            } else if( temp.bf == -2 ) {
                //RR or RL
                if( temp.parent == null ) {
                    //root node
                    if(temp.right.right !=null && temp.right.right.contains(k)) {
                        //RR Imbalance
                        AVLNode A = temp;
                        AVLNode B = temp.right;

                        B.parent = null;

                        t=B;
                        A.right = B.left;
                        if(A.right!=null) {
                            A.right.parent = A;
                        }
                        B.left = A;
                        A.parent = B;
                        
                        A.height = getHeight(A);
                        B.height = getHeight(B);

                        A.bf = getBF(A);
                        B.bf = getBF(B);
                    } else if (temp.right.left.contains(k)) {
                        //RL Imbalance
                        AVLNode A = temp;
                        AVLNode B = temp.right;
                        AVLNode C = temp.right.left;
                        
                        B.parent = C;
                        C.parent = null;
                        A.parent = C;
                        
                        A.right = C.left;                        
                        if( A.right!=null ) {
                            A.right.parent = A;
                        }
                        B.left = C.right;
                        if(B.left!=null) {
                            B.left.parent = B;
                        }
                        
                        C.left = A;
                        C.right = B;

                        t=C;
                        
                        
                        A.height = getHeight(A);
                        B.height = getHeight(B);
                        C.height = getHeight(C);
                        
                        A.bf = getBF(A);
                        B.bf = getBF(B);
                        C.bf = getBF(C);
                    }
                } else {
                    if(temp.right.right!=null && temp.right.right.contains(k)) {
                        //keeps track of whether temp is a left or a right child of its parent
                        Boolean lChild = true;
                        AVLNode tempParent = temp.parent;
                        //since this is a LL or LR imbalance, we know that 
                        if( tempParent.left == temp ) {
                            lChild = true;
                        } else if( tempParent.right == temp ) {
                            lChild = false;
                        }
                        //works but need to update heights and balance factors here.
                        AVLNode A = temp;
                        AVLNode B = temp.right;

                        B.parent = tempParent;

                        //need to figure out where B goes into the left or right of the temp parent
                        if(lChild)
                            tempParent.left = B;
                        else
                            tempParent.right = B;

                        A.right = B.left;
                        if( A.right!=null ) {
                            A.right.parent = A;
                        }
                        B.left = A;
                        A.parent = B;

                        A.height = getHeight(A);
                        B.height = getHeight(B);

                        A.bf = getBF(A);
                        B.bf = getBF(B);
                    } else if (temp.right.left.contains(k)) {
                        //RL Imbalance
                        //keeps track of whether temp is a left or a right child of its parent
                        Boolean lChild = true;
                        AVLNode tempParent = temp.parent;
                        //since this is a RR or RL imbalance, we know that 
                        if( tempParent.left == temp ) {
                            lChild = true;
                        } else if( tempParent.right == temp ) {
                            lChild = false;
                        }
                        //works but need to update heights and balance factors here.
                        AVLNode A = temp;
                        AVLNode B = temp.right;
                        AVLNode C = temp.right.left;
                        
                        B.parent = C;
                        A.parent = C;
                        
                        A.right = C.left;                        
                        if( A.right!=null ) {
                            A.right.parent = A;
                        }
                        B.left = C.right;
                        if(B.left!=null) {
                            B.left.parent = B;
                        }
                        
                        C.left = A;
                        C.right = B;
                        C.parent = tempParent;
                        //need to figure out where B goes into the left or right of the temp parent
                        if(lChild)
                            tempParent.left = C;
                        else
                            tempParent.right = C;

                        A.height = getHeight(A);
                        B.height = getHeight(B);
                        C.height = getHeight(C);
                        A.bf = getBF(A);
                        B.bf = getBF(B);
                        C.bf = getBF(C);                        
                    }
                }
            }
            temp = temp.parent;
        }
    }
    
    /**
     * Computes the height of the given AVLNode
     * @param A: An AVLNode
     * @return
     */
    public int getHeight(AVLNode A) {
        int lh=0;
        int rh=0;
        
        if(A.left==null && A.right==null) {
            return 0;
        }else if( A.left != null ) {
            lh = A.left.height;
        }
        if(A.right != null) {
            rh = A.right.height;
        }
        
        if(lh >= rh) {
            return lh+1;
        } else {
            return rh+1;
        }
    }
    
    /**
     * Computes the balance factor of the given AVLNode
     * @param A: an AVLNode
     * @return
     */
    public int getBF(AVLNode A) {
        int bf = 0;
        if( A.left == null ) {
            bf = 0-A.height;
        } else if( A.right == null ) {
            bf = A.height;
        } else {
            bf = A.left.height - A.right.height;
        }
        return bf;
    }
    
    /**
     * search for key k
     * @param k
     * @return
     */
    public Boolean search(int k) {
        return t.contains(k);
    }
    
    /**
     * Output inorder traversal of the tree
     * @return
     */
    public String inOrder() {
        return t.inOrder();
    }

    /**
     * Output post traversal of the tree
     * @return
     */
    public String postOrder() {
        return t.postOrder();
    }
}
