package com.ram.util;


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import org.apache.axis.encoding.Base64;
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
	
  private static String STATIC_KEY = "Bar12345Bar12345";
  
  static  byte[]  key = "!@#$!@#$%^&**&^%".getBytes();
	final static String algorithm="AES";
  
  public static JSONArray onStartUp() {
    List<String> list = new ArrayList();
    JSONArray jsonArray = null;
    list = DBManager.readFromFile();
    if (!list.isEmpty()) {
      try
      {
//    	String key = decryptData((String)list.get(1));
//    	System.out.println(key);
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
					PasswordManager.isRegistered = Boolean.parseBoolean(jsonObject.getString(Constants.IS_REGISTERED));
					PasswordManager.SEC_QUES_1 = jsonObject.getString(Constants.SEC_QUES_1);
					PasswordManager.SEC_QUES_2 = jsonObject.getString(Constants.SEC_QUES_2);
					PasswordManager.SEC_ANS_1 = jsonObject.getString(Constants.SEC_ANS_1);
					PasswordManager.SEC_ANS_2 = jsonObject.getString(Constants.SEC_ANS_2);
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
		frame.setSize(696, 400);
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
	
	public static void showProgressBar() {
		JFrame frame = new JFrame(Constants.PASSWORD_MANAGER);
		frame.setSize(300, 24);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		Container content = frame.getContentPane();
	    JProgressBar progressBar = new JProgressBar();
	    progressBar.setIndeterminate(true);
	    content.add(progressBar, BorderLayout.CENTER);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public static void createCards() {
		PasswordManager.registerCard = new AccountDetails(Constants.REGISTER, new JLabel(
				"This is a register card"), Color.ORANGE);
		PasswordManager.loginCard = new AccountDetails(Constants.LOGIN, new JLabel(
				"This is card login"), MyColor.getCommonColor());
		PasswordManager.newAccountDetails = new AccountDetails(Constants.NEW_ACCOUNT_DETAILS,
				new JLabel("This is card one"), Color.DARK_GRAY);
		PasswordManager.retreviewAccountDetails = new AccountDetails(
				Constants.RETRIEVE_ACCOUNT_DETAILS, new JLabel(
						"This is card two"), MyColor.getCommonColor());
		PasswordManager.updateAccountDetails = new AccountDetails(
				Constants.UPDATE_ACCOUNT_DETAILS, new JLabel(
						"This is card three"), Color.GRAY);
		PasswordManager.updateLoginPassword = new AccountDetails(
				Constants.UPDATE_PASSWORD, new JLabel(
						"This is card update login password"), MyColor.getCommonColor());
		PasswordManager.updateIdleTimeOut = new AccountDetails(
				Constants.UPDATE_IDLE_TIME_OUT, new JLabel(
						"This is card update time out"), MyColor.getCommonColor());
		PasswordManager.FORGOT_PASSWORD = new AccountDetails(
				Constants.FORGOT_PASSWORD, new JLabel(
						"This is card update time out"), MyColor.getCommonColor());
	}
	
	public static String encrypt(String data) {

	    byte[] dataToSend = data.getBytes();
	    Cipher c = null;
	    try {
	        c = Cipher.getInstance(algorithm);
	    } catch (NoSuchAlgorithmException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (NoSuchPaddingException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    SecretKeySpec k =  new SecretKeySpec(key, algorithm);
	    try {
	        c.init(Cipher.ENCRYPT_MODE, k);
	    } catch (InvalidKeyException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    byte[] encryptedData = "".getBytes();
	    try {
	        encryptedData = c.doFinal(dataToSend);
	    } catch (IllegalBlockSizeException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (BadPaddingException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    String encryptedByteValue =    new Base64().encode(encryptedData);
	    return  new String(encryptedByteValue);//.toString();
	}

	public static String decrypt(String data){

	    byte[] encryptedData  = new Base64().decode(data);
	    Cipher c = null;
	    try {
	        c = Cipher.getInstance(algorithm);
	    } catch (NoSuchAlgorithmException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (NoSuchPaddingException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    SecretKeySpec k =
	            new SecretKeySpec(key, algorithm);
	    try {
	        c.init(Cipher.DECRYPT_MODE, k);
	    } catch (InvalidKeyException e1) {
	        // TODO Auto-generated catch block
	        e1.printStackTrace();
	    }
	    byte[] decrypted = null;
	    try {
	        decrypted = c.doFinal(encryptedData);
	    } catch (IllegalBlockSizeException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (BadPaddingException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    return new String(decrypted);
	}
	
}

class MyColor {
	public static java.awt.Color getCommonColor() {
		return new java.awt.Color(196, 119, 57);
	}
}

