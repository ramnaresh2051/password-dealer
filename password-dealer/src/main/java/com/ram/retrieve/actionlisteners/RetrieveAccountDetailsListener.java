package com.ram.retrieve.actionlisteners;

import com.ram.constants.Constants;
import com.ram.dto.AccountDetailsFields;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RetrieveAccountDetailsListener
  implements ActionListener
{
  private JSONArray jsonArray;
  private JComboBox<String> accountTypeList;
  private JTextField accountDescription;
  private JTextField userName;
  private JTextField password;
  
  public RetrieveAccountDetailsListener(JSONArray jsonArray, JComboBox<String> accountTypeList, JTextField accountDescription, JTextField userName, JTextField password)
  {
    System.out.println("json :" + jsonArray);
    this.jsonArray = jsonArray;
    this.accountTypeList = accountTypeList;
    this.accountDescription = accountDescription;
    this.userName = userName;
    this.password = password;
  }
  
  public void actionPerformed(ActionEvent e)
  {
    System.out.println("Clicked on submit button : retreviewAccountPanel");
    System.out.println("Clicked on submit button test");
    
    String accountType = this.accountTypeList.getSelectedItem().toString();
    String accountDesc = this.accountDescription.getText();
    System.out.println("Account type :" + accountType);
    System.out.println("Account desc :" + this.accountDescription.getText());
    System.out.println("User Name :" + this.userName.getText());
    System.out.println("Password :" + this.password.getText());
    retreviewAccountDetails(accountType, accountDesc, this.userName, this.password);
  }
  
  private void retreviewAccountDetails(String accountType, String accountDesc, JTextField userName, JTextField password)
  {
    List<AccountDetailsFields> accountList = new ArrayList();
    boolean flag = true;
    
    System.out.println("retreviewAccountDetails");
    if ((!"".equalsIgnoreCase(accountType)) && (!"Select".equalsIgnoreCase(accountType)) && (!"".equalsIgnoreCase(accountDesc)) && (!"".equalsIgnoreCase(userName.getText())))
    {
      System.out.println("Three fields");
      System.out.println("Account type :" + accountType);
      System.out.println("Account desc :" + this.accountDescription.getText());
      System.out.println("User Name :" + userName.getText());
      accountList = getAccountDetailsForThree(accountType, this.accountDescription.getText(), userName.getText());
    }
    else if ((!"".equalsIgnoreCase(accountType)) && (!"Select".equalsIgnoreCase(accountType)) && (!"".equalsIgnoreCase(accountDesc)))
    {
      System.out.println("Two fields account type and account desc");
      System.out.println("Account type :" + accountType);
      System.out.println("Account desc :" + this.accountDescription.getText());
      accountList = getAcctDetailsForTypeAndDesc(accountType, this.accountDescription.getText());
    }
    else if ((!"".equalsIgnoreCase(accountType)) && (!"Select".equalsIgnoreCase(accountType)) && (!"".equalsIgnoreCase(userName.getText())))
    {
      System.out.println("Two fields account type and username");
      System.out.println("Account type :" + accountType);
      System.out.println("User Name :" + userName.getText());
      accountList = getAcctDetailsForTypeAndUName(accountType, userName.getText());
    }
    else if ((!"".equalsIgnoreCase(accountDesc)) && (!"".equalsIgnoreCase(userName.getText())))
    {
      System.out.println("Two fields accountDesc and username");
      System.out.println("Account type :" + accountType);
      System.out.println("User Name :" + userName.getText());
      accountList = getAcctDetailsForDescAndUName(accountDesc, userName.getText());
    }
    else if ((!"".equalsIgnoreCase(accountType)) && (!"Select".equalsIgnoreCase(accountType)))
    {
      System.out.println("One fields account type");
      System.out.println("Account type :" + accountType);
      accountList = getAcctDetailsForAcctType(accountType);
    }
    else if (!"".equalsIgnoreCase(accountDesc))
    {
      System.out.println("One field fields account desc");
      System.out.println("Account type :" + accountDesc);
      accountList = getAcctDetailsForDesc(this.accountDescription.getText());
    }
    else if (!"".equalsIgnoreCase(userName.getText()))
    {
      System.out.println("One field fields account desc");
      System.out.println("Account type :" + accountDesc);
      accountList = getAcctDetailsForUserName(userName.getText());
    }
    else
    {
      flag = false;
    }
    StringBuilder sb = new StringBuilder();
    int i = 1;
    if (flag) {
      for (AccountDetailsFields fields : accountList)
      {
        if (i != 1)
        {
          sb.append("----------------------------");
          sb.append("\n");
        }
        sb.append("Record # " + i);
        sb.append("\n");
        sb.append("AccountType : " + fields.getAccountType());
        sb.append("\n");
        sb.append("AccountDesc : " + fields.getAccountDesc());
        sb.append("\n");
        sb.append("UserName : " + fields.getUsername());
        sb.append("\n");
        sb.append("Password : " + fields.getPassword());
        sb.append("\n");
        i++;
      }
    } else {
      sb.append("Please select any option to search");
    }
    JOptionPane.showMessageDialog(null, sb);
  }
  
  private List<AccountDetailsFields> getAcctDetailsForUserName(String uName)
  {
    List<AccountDetailsFields> list = new ArrayList();
    try
    {
      for (int i = 0; i < this.jsonArray.length(); i++)
      {
        AccountDetailsFields accountDetailsFields = new AccountDetailsFields();
        boolean shouldAdd = false;
        System.out.println(this.jsonArray.getJSONObject(i));
        JSONObject jsonObject = this.jsonArray.getJSONObject(i);
        if (jsonObject.has(Constants.ACCOUNT_TYPE_BE))
        {
          System.out.println("Timing : " + i);
          if (jsonObject.get(Constants.USER_NAME_BE).toString().contains(uName)) {
            shouldAdd = true;
          }
        }
        if (shouldAdd)
        {
          accountDetailsFields.setAccountType((String)jsonObject.get(Constants.ACCOUNT_TYPE_BE));
          accountDetailsFields.setAccountDesc((String)jsonObject.get(Constants.ACCOUNT_DESC_BE));
          accountDetailsFields.setUsername((String)jsonObject.get(Constants.USER_NAME_BE));
          accountDetailsFields.setPassword((String)jsonObject.get(Constants.PASSWORD_BE));
          list.add(accountDetailsFields);
        }
      }
    }
    catch (JSONException e)
    {
      e.printStackTrace();
    }
    return list;
  }
  
  private List<AccountDetailsFields> getAcctDetailsForAcctType(String accountType)
  {
    List<AccountDetailsFields> list = new ArrayList();
    try
    {
      for (int i = 0; i < this.jsonArray.length(); i++)
      {
        AccountDetailsFields accountDetailsFields = new AccountDetailsFields();
        boolean shouldAdd = false;
        System.out.println(this.jsonArray.getJSONObject(i));
        JSONObject jsonObject = this.jsonArray.getJSONObject(i);
        if (jsonObject.has(Constants.ACCOUNT_TYPE_BE))
        {
          System.out.println("Timing : " + i);
          if (jsonObject.get(Constants.ACCOUNT_TYPE_BE).toString().contains(accountType)) {
            shouldAdd = true;
          }
        }
        if (shouldAdd)
        {
          accountDetailsFields.setAccountType((String)jsonObject.get(Constants.ACCOUNT_TYPE_BE));
          accountDetailsFields.setAccountDesc((String)jsonObject.get(Constants.ACCOUNT_DESC_BE));
          accountDetailsFields.setUsername((String)jsonObject.get(Constants.USER_NAME_BE));
          accountDetailsFields.setPassword((String)jsonObject.get(Constants.PASSWORD_BE));
          list.add(accountDetailsFields);
        }
      }
    }
    catch (JSONException e)
    {
      e.printStackTrace();
    }
    return list;
  }
  
  private List<AccountDetailsFields> getAcctDetailsForDescAndUName(String accountDesc, String uName)
  {
    List<AccountDetailsFields> list = new ArrayList();
    try
    {
      for (int i = 0; i < this.jsonArray.length(); i++)
      {
        AccountDetailsFields accountDetailsFields = new AccountDetailsFields();
        boolean shouldAdd = false;
        System.out.println(this.jsonArray.getJSONObject(i));
        JSONObject jsonObject = this.jsonArray.getJSONObject(i);
        if (jsonObject.has(Constants.ACCOUNT_DESC_BE))
        {
          System.out.println("Timing : " + i);
          if ((jsonObject.get(Constants.ACCOUNT_DESC_BE).toString().contains(accountDesc)) && 
            (jsonObject.get(Constants.USER_NAME_BE).toString().contains(uName))) {
            shouldAdd = true;
          }
        }
        if (shouldAdd)
        {
          accountDetailsFields.setAccountType((String)jsonObject.get(Constants.ACCOUNT_TYPE_BE));
          accountDetailsFields.setAccountDesc((String)jsonObject.get(Constants.ACCOUNT_DESC_BE));
          accountDetailsFields.setUsername((String)jsonObject.get(Constants.USER_NAME_BE));
          accountDetailsFields.setPassword((String)jsonObject.get(Constants.PASSWORD_BE));
          list.add(accountDetailsFields);
        }
      }
    }
    catch (JSONException e)
    {
      e.printStackTrace();
    }
    return list;
  }
  
  private List<AccountDetailsFields> getAcctDetailsForDesc(String accountDesc)
  {
    List<AccountDetailsFields> list = new ArrayList();
    try
    {
      for (int i = 0; i < this.jsonArray.length(); i++)
      {
        AccountDetailsFields accountDetailsFields = new AccountDetailsFields();
        boolean shouldAdd = false;
        System.out.println(this.jsonArray.getJSONObject(i));
        JSONObject jsonObject = this.jsonArray.getJSONObject(i);
        if (jsonObject.has(Constants.ACCOUNT_DESC_BE))
        {
          System.out.println("Timing : " + i);
          if (jsonObject.get(Constants.ACCOUNT_DESC_BE).toString().contains(accountDesc)) {
            shouldAdd = true;
          }
        }
        if (shouldAdd)
        {
          accountDetailsFields.setAccountType((String)jsonObject.get(Constants.ACCOUNT_TYPE_BE));
          accountDetailsFields.setAccountDesc((String)jsonObject.get(Constants.ACCOUNT_DESC_BE));
          accountDetailsFields.setUsername((String)jsonObject.get(Constants.USER_NAME_BE));
          accountDetailsFields.setPassword((String)jsonObject.get(Constants.PASSWORD_BE));
          list.add(accountDetailsFields);
        }
      }
    }
    catch (JSONException e)
    {
      e.printStackTrace();
    }
    return list;
  }
  
  private List<AccountDetailsFields> getAcctDetailsForTypeAndUName(String accountType, String userName)
  {
    List<AccountDetailsFields> list = new ArrayList();
    try
    {
      for (int i = 0; i < this.jsonArray.length(); i++)
      {
        AccountDetailsFields accountDetailsFields = new AccountDetailsFields();
        boolean shouldAdd = false;
        System.out.println(this.jsonArray.getJSONObject(i));
        JSONObject jsonObject = this.jsonArray.getJSONObject(i);
        if (jsonObject.has(Constants.ACCOUNT_TYPE_BE))
        {
          System.out.println("Timing : " + i);
          if ((jsonObject.get(Constants.ACCOUNT_TYPE_BE).toString().contains(accountType)) && 
            (jsonObject.get(Constants.USER_NAME_BE).toString().contains(userName))) {
            shouldAdd = true;
          }
        }
        if (shouldAdd)
        {
          accountDetailsFields.setAccountType((String)jsonObject.get(Constants.ACCOUNT_TYPE_BE));
          accountDetailsFields.setAccountDesc((String)jsonObject.get(Constants.ACCOUNT_DESC_BE));
          accountDetailsFields.setUsername((String)jsonObject.get(Constants.USER_NAME_BE));
          accountDetailsFields.setPassword((String)jsonObject.get(Constants.PASSWORD_BE));
          list.add(accountDetailsFields);
        }
      }
    }
    catch (JSONException e)
    {
      e.printStackTrace();
    }
    return list;
  }
  
  private List<AccountDetailsFields> getAcctDetailsForTypeAndDesc(String accountType, String accountDesc)
  {
    List<AccountDetailsFields> list = new ArrayList();
    try
    {
      for (int i = 0; i < this.jsonArray.length(); i++)
      {
        AccountDetailsFields accountDetailsFields = new AccountDetailsFields();
        boolean shouldAdd = false;
        System.out.println(this.jsonArray.getJSONObject(i));
        JSONObject jsonObject = this.jsonArray.getJSONObject(i);
        if (jsonObject.has(Constants.ACCOUNT_TYPE_BE))
        {
          System.out.println("Timing : " + i);
          if ((jsonObject.get(Constants.ACCOUNT_TYPE_BE).toString().contains(accountType)) && 
            (jsonObject.get(Constants.ACCOUNT_DESC_BE).toString().contains(accountDesc))) {
            shouldAdd = true;
          }
        }
        if (shouldAdd)
        {
          accountDetailsFields.setAccountType((String)jsonObject.get(Constants.ACCOUNT_TYPE_BE));
          accountDetailsFields.setAccountDesc((String)jsonObject.get(Constants.ACCOUNT_DESC_BE));
          accountDetailsFields.setUsername((String)jsonObject.get(Constants.USER_NAME_BE));
          accountDetailsFields.setPassword((String)jsonObject.get(Constants.PASSWORD_BE));
          list.add(accountDetailsFields);
        }
      }
    }
    catch (JSONException e)
    {
      e.printStackTrace();
    }
    return list;
  }
  
  private List<AccountDetailsFields> getAccountDetailsForThree(String accountType, String accountDesc, String userName)
  {
    List<AccountDetailsFields> list = new ArrayList();
    try
    {
      for (int i = 0; i < this.jsonArray.length(); i++)
      {
        AccountDetailsFields accountDetailsFields = new AccountDetailsFields();
        boolean shouldAdd = false;
        System.out.println(this.jsonArray.getJSONObject(i));
        JSONObject jsonObject = this.jsonArray.getJSONObject(i);
        if (jsonObject.has(Constants.ACCOUNT_TYPE_BE))
        {
          System.out.println("Timing : " + i);
          if ((jsonObject.get(Constants.ACCOUNT_TYPE_BE).toString().contains(accountType)) && 
            (jsonObject.get(Constants.ACCOUNT_DESC_BE).toString().contains(accountDesc)) && 
            (jsonObject.get(Constants.USER_NAME_BE).toString().contains(userName))) {
            shouldAdd = true;
          }
        }
        if (shouldAdd)
        {
          accountDetailsFields.setAccountType((String)jsonObject.get(Constants.ACCOUNT_TYPE_BE));
          accountDetailsFields.setAccountDesc((String)jsonObject.get(Constants.ACCOUNT_DESC_BE));
          accountDetailsFields.setUsername((String)jsonObject.get(Constants.USER_NAME_BE));
          accountDetailsFields.setPassword((String)jsonObject.get(Constants.PASSWORD_BE));
          list.add(accountDetailsFields);
        }
      }
    }
    catch (JSONException e)
    {
      e.printStackTrace();
    }
    return list;
  }
}
