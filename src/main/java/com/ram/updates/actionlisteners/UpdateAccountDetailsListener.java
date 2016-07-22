package com.ram.updates.actionlisteners;

import com.ram.PasswordManager;
import com.ram.constants.Constants;
import com.ram.db.DBManager;
import com.ram.util.Util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UpdateAccountDetailsListener
  implements ActionListener
{
  private JSONArray jsonArray;
  private JComboBox<String> accountTypeList;
  private JComboBox<String> accountTypeDescriptionList;
  private JTextField userName;
  private JTextField password;
  
  public UpdateAccountDetailsListener(JSONArray jsonArray, JComboBox<String> accountTypeList, JComboBox<String> accountTypeDescriptionList, JTextField userName, JTextField password)
  {
    this.jsonArray = jsonArray;
    this.accountTypeList = accountTypeList;
    this.accountTypeDescriptionList = accountTypeDescriptionList;
    this.userName = userName;
    this.password = password;
  }
  
  public void actionPerformed(ActionEvent e)
  {
    System.out.println("Clicked on submit button");
    if(Util.checkTimeOut(PasswordManager.idleTimeOut, PasswordManager.idleTimeOutLong)) {
   	 return;
    }
    String accountSelected = this.accountTypeList.getSelectedItem().toString();
    String accountDescSelected = this.accountTypeDescriptionList.getSelectedItem().toString();
    String userNameSelected = this.userName.getText();
    String passwordSelected = this.password.getText();
    
    System.out.println("Account type :" + accountSelected);
    System.out.println("Account desc :" + accountDescSelected);
    System.out.println("User Name :" + userNameSelected);
    try
    {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put(Constants.ACCOUNT_TYPE_BE, accountSelected);
      jsonObject.put(Constants.ACCOUNT_DESC_BE, accountDescSelected);
      jsonObject.put(Constants.USER_NAME_BE, userNameSelected);
      jsonObject.put(Constants.PASSWORD_BE, passwordSelected);
      for (int i = 0; i < this.jsonArray.length(); i++)
      {
        JSONObject jsonObjectOne = this.jsonArray.getJSONObject(i);
        if ((jsonObjectOne.has(Constants.ACCOUNT_TYPE_BE)) && 
          (jsonObjectOne.get(Constants.ACCOUNT_TYPE_BE).toString().equals(accountSelected)) && 
          (jsonObjectOne.get(Constants.ACCOUNT_DESC_BE).toString().equals(accountDescSelected)) && 
          (jsonObjectOne.get(Constants.USER_NAME_BE).toString().equals(userNameSelected)))
        {
          System.out.println("In if loop when condition matched");
          this.jsonArray.remove(i);
          this.jsonArray.put(jsonObject);
        }
      }
      DBManager.writeIntoFile(this.jsonArray.toString());
      JOptionPane.showMessageDialog(null, "Updated successfully");
    }
    catch (JSONException e1)
    {
      e1.printStackTrace();
    }
  }
}
