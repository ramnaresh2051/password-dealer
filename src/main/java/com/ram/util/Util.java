package com.ram.util;


import java.awt.CardLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ram.AccountDetails;
import com.ram.CardPanel;
import com.ram.PasswordManager;
import com.ram.PasswordManager.ControlPanel;
import com.ram.constants.Constants;
import com.ram.db.DBManager;
import com.ram.menu.MyMenuBar;

public class Util {
	
  private static CardLayout cardLayout;	
	
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

	public static void loadUserCredentials(JSONArray jsonArray) {
		try{
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				if (jsonObject.has(Constants.USER_CREDENTIAL)) {
					PasswordManager.loginUserName = jsonObject.getString(Constants.LOGIN_ACCOUNT_USER_NAME);
					PasswordManager.loginPassword = jsonObject.getString(Constants.LOGIN_ACCOUNT_USER_PASS);
					PasswordManager.loginKey = jsonObject.getString(Constants.LOGIN_ACCOUNT_USER_KEY);
					PasswordManager.idleTimeOut = jsonObject.getString(Constants.LOGIN_ACCOUNT_USER_TO);
				}
			}
		}catch (JSONException jsonE) {
			jsonE.printStackTrace();
		}
		
	}

	public static boolean checkTimeOut(String idleTimeOut, long lastActed) {
		boolean isTimedOut = false;
		long idle = Integer.parseInt(idleTimeOut) * 60 * 1000;
		long timeDiff = System.currentTimeMillis() - lastActed;
		
		if (timeDiff > idle) {
			isTimedOut = true;
			showLoginScreen();
		} else {
			isTimedOut = false;
			PasswordManager.idleTimeOutLong = System.currentTimeMillis();
		}
		
		return isTimedOut;
	}
	
	public static void showLoginScreen() {
		cardLayout = (CardLayout)PasswordManager.cards.getLayout();
		JOptionPane.showMessageDialog(null,
				Constants.TIME_OUT_ERROR_MSG);
		cardLayout.show(PasswordManager.cards,
				PasswordManager.loginCard.getName());
	}
	
	public static void createAndShowGUI() {
		JFrame frame = new JFrame(Constants.PASSWORD_MANAGER);
		frame.setSize(580, 280);
		frame.setIconImage(new ImageIcon("/Users/ramnaresh/Documents/personal_docs/AA-350.jpg").getImage());

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(
				new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		PasswordManager.cards = new CardPanel();
		frame.getContentPane().add(PasswordManager.cards);
		frame.getContentPane().add(new ControlPanel());
		frame.setJMenuBar(MyMenuBar.creaetMenuBar());

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public static void createCards() {
		PasswordManager.registerCard = new AccountDetails(Constants.REGISTER, new JLabel(
				"This is a register card"), Color.WHITE);
		PasswordManager.loginCard = new AccountDetails(Constants.LOGIN, new JLabel(
				"This is card login"), Color.WHITE);
		PasswordManager.newAccountDetails = new AccountDetails(Constants.NEW_ACCOUNT_DETAILS,
				new JLabel("This is card one"), Color.DARK_GRAY);
		PasswordManager.retreviewAccountDetails = new AccountDetails(
				Constants.RETRIEVE_ACCOUNT_DETAILS, new JLabel(
						"This is card two"), Color.LIGHT_GRAY);
		PasswordManager.updateAccountDetails = new AccountDetails(
				Constants.UPDATE_ACCOUNT_DETAILS, new JLabel(
						"This is card three"), Color.GRAY);
		PasswordManager.updateLoginPassword = new AccountDetails(
				Constants.UPDATE_PASSWORD, new JLabel(
						"This is card update login password"), Color.WHITE);
		PasswordManager.updateIdleTimeOut = new AccountDetails(
				Constants.UPDATE_IDLE_TIME_OUT, new JLabel(
						"This is card update time out"), Color.WHITE);
	}
}

