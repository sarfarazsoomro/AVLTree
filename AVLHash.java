/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adsproject;

/**
 *
 * @author ssoomro
 */
public class AVLHash {
    AVLTree[] AVLarr;
    int s;
    
    /**
     * Constructor, takes si which is the number of buckets the Hash should have
     * @param si
     */
    public AVLHash(int si) {
        s = si;
        if( si%2 == 0 ) {
            s=si+1;
        }
        AVLarr =  new AVLTree[s];
    }
    
    /**
     * Insert the given key/value pair
     * @param k
     * @param v
     */
    public void insert(int k, int v) {
        //x would have the correct position of the AVLTree in which k is to be 
        //inserted
        int x = xhash(k);
        if(AVLarr[x]!=null) {
            AVLarr[x].insert(k,v);
        } else {
            AVLarr[x] = new AVLTree(k,v);
        }
    }
    
    /**
     * searches for key k
     * @param k
     * @return
     */
    public boolean search(int k) {
        //x would have the correct position of the AVLTree in which k is to be 
        //inserted
        int x = xhash(k);
        if(AVLarr[x]!=null) {
            return AVLarr[x].search(k);
        } else {
            return false;
        }
    }    
    
    /**
     * hashCode function is simply the abs of the provided int key
     * @param k
     * @return 
     */
    private int xhashCode(int k) {
        return (k>=0)?k:(k*-1);
    }
    
    /**
     * hash is simple the hashCode mod s, determines which bucket the key k goes into
     * @param k
     * @return 
     */
    private int xhash(int k) {
        return xhashCode(k)%s;
    }
    
    /**
     * Outputs the AVLHash in an easy to read format
     * @return
     */
    public String toStringVerbose() {
        String s = "";
        for(int i=0; i<AVLarr.length;i++) {
            s += "tree "+i+" count:"+AVLarr[i].count+"\n";
        }
        return s;
    }
    
    /**
     * outputs the in order traversal or the AVLHash
     * @return
     */
    public String inOrder() {
        String st="";
        for(int i=0;i<s;i++) {
            st+=AVLarr[i].inOrder()+"\n";
        }
        return st;
    }
    
    /**
     * outputs the post order traversal or the AVLHash
     * @return
     */
    public String postOrder() {
        String st="";
        for(int i=0;i<s;i++) {
            st+=AVLarr[i].postOrder()+"\n";
        }
        return st;
    }    
}
