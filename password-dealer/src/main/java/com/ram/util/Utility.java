package com.ram.util;


import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.ram.db.DBManager;

public class Utility {
  private static MyTree<String> myTree = new MyTree("PasswordManager");
  
  public static void main(String[] args)
  {
    String accountType = "Social";
    String accountDesc = "SBI";
    String userName = "ramnaresh_singh";
    createTree();
    Collection<MyTree<String>> element = searchElement(accountType, accountDesc, "");
    for (MyTree<String> tree : element) {
      System.out.println(tree.toString().contains("facebook"));
    }
  }
  
  private static Collection<MyTree<String>> searchElement(String accountType, String accountDesc, String userName)
  {
    Collection<MyTree<String>> element = new ArrayList();
    if (userName.isEmpty()) {
      element = myTree.getTree(accountType).getSubTrees();
    }
    return element;
  }
  
  private static void createTree() {
//    DBManager dbManager = new DBManager();
//    ArrayList<String> list = DBManager.readFromFile();
//    System.out.println("List Size :" + list.size());
//    Iterator<String> listIterator = list.iterator();
//    int j;
//    int i;
//    for (; listIterator.hasNext(); ) {
//      String[] commaSplit = ((String)listIterator.next()).split(",");
//      String firstElement = null;
//      String secondElement = null;
//      boolean flag = false;
//      String[] arrayOfString1;
//      j = (arrayOfString1 = commaSplit).length;i = 0; continue;String element = arrayOfString1[i];
//      firstElement = secondElement;
//      secondElement = element.substring(element.indexOf("=") + 1, element.length());
//      if (flag) {
//        myTree.addLeaf(firstElement, secondElement);
//      }
//      flag = true;i++;
//    }
  }
  
  public static JSONArray onStartUp() {
    List<String> list = new ArrayList();
    JSONArray jsonArray = null;
    list = DBManager.readFromFile();
    if (!list.isEmpty()) {
      try
      {
        jsonArray = new JSONArray((String)list.get(0));
      }
      catch (JSONException e)
      {
        e.printStackTrace();
      }
    } else {
      jsonArray = new JSONArray();
    }
    return jsonArray;
  }
}

