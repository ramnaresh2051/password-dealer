package com.ram.updates.actionlisteners;

import com.ram.PasswordManager;
import com.ram.constants.Constants;
import com.ram.util.Util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UpdateAcctTypeListListener
  implements ActionListener
{
  private JSONArray jsonArray;
  private JComboBox<String> accountTypeList;
  private DefaultComboBoxModel model;
  
  public UpdateAcctTypeListListener(JSONArray jsonArray, JComboBox<String> accountTypeList, DefaultComboBoxModel model)
  {
    this.jsonArray = jsonArray;
    this.accountTypeList = accountTypeList;
    this.model = model;
  }
  
  public void actionPerformed(ActionEvent e)
  {
	  if(Util.checkTimeOut(PasswordManager.idleTimeOut, PasswordManager.idleTimeOutLong)) {
	    	 return;
	   }
    String accountSelected = this.accountTypeList.getSelectedItem().toString();
    if (!"Select".equalsIgnoreCase(accountSelected)) {
      try
      {
        List<String> list = findAccountDesc(accountSelected);
        System.out.println("Account desc for selected account type" + list);
        
        this.model.removeAllElements();
        for (String values : list) {
          this.model.addElement(values);
        }
      }
      catch (JSONException e1)
      {
        e1.printStackTrace();
      }
    }
  }
  
  private List<String> findAccountDesc(String accountSelected)
    throws JSONException
  {
    List<String> list = new ArrayList();
    for (int i = 0; i < this.jsonArray.length(); i++)
    {
      JSONObject jsonObject = this.jsonArray.getJSONObject(i);
      if ((jsonObject.has(Constants.ACCOUNT_TYPE_BE)) && 
        (jsonObject.get(Constants.ACCOUNT_TYPE_BE).toString().contains(accountSelected))) {
        list.add(jsonObject.get(Constants.ACCOUNT_DESC_BE).toString());
      }
    }
    return list;
  }
}
