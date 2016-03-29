package com.ram.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class MyTree<T>
{
  private T head;
  private ArrayList<MyTree<T>> leafs = new ArrayList();
  private MyTree<T> parent = null;
  private HashMap<T, MyTree<T>> locate = new HashMap();
  private static final int indent = 2;
  
  public MyTree(T head)
  {
    this.head = head;
    this.locate.put(head, this);
  }
  
  public void addLeaf(T root, T leaf)
  {
    if (this.locate.containsKey(root)) {
      ((MyTree)this.locate.get(root)).addLeaf(leaf);
    } else {
      addLeaf(root).addLeaf(leaf);
    }
  }
  
  public MyTree<T> addLeaf(T leaf)
  {
    MyTree<T> t = new MyTree(leaf);
    this.leafs.add(t);
    t.parent = this;
    t.locate = this.locate;
    this.locate.put(leaf, t);
    return t;
  }
  
  public MyTree<T> setAsParent(T parentRoot)
  {
    MyTree<T> t = new MyTree(parentRoot);
    t.leafs.add(this);
    this.parent = t;
    t.locate = this.locate;
    t.locate.put(this.head, this);
    t.locate.put(parentRoot, t);
    return t;
  }
  
  public T getHead()
  {
    return (T)this.head;
  }
  
  public MyTree<T> getTree(T element)
  {
    return (MyTree)this.locate.get(element);
  }
  
  public MyTree<T> getParent()
  {
    return this.parent;
  }
  
  public Collection<T> getSuccessors(T root)
  {
    Collection<T> successors = new ArrayList();
    MyTree<T> tree = getTree(root);
    if (tree != null) {
      for (MyTree<T> leaf : tree.leafs) {
        successors.add(leaf.head);
      }
    }
    return successors;
  }
  
  public Collection<MyTree<T>> getSubTrees()
  {
    return this.leafs;
  }
  
  public static <T> Collection<T> getSuccessors(T of, Collection<MyTree<T>> in)
  {
    for (MyTree<T> tree : in) {
      if (tree.locate.containsKey(of)) {
        return tree.getSuccessors(of);
      }
    }
    return new ArrayList();
  }
  
  public String toString()
  {
    return printTree(0);
  }
  
  private String printTree(int increment)
  {
    String s = "";
    String inc = "";
    for (int i = 0; i < increment; i++) {
      inc = inc + " ";
    }
    s = inc + this.head;
    for (MyTree<T> child : this.leafs) {
      s = s + "\n" + child.printTree(increment + 2);
    }
    return s;
  }
}
