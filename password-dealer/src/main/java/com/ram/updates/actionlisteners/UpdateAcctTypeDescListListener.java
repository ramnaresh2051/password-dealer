package com.ram.updates.actionlisteners;

import com.ram.constants.Constants;
import com.ram.dto.AccountDetailsFields;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UpdateAcctTypeDescListListener
  implements ActionListener
{
  private JSONArray jsonArray;
  private JComboBox<String> accountTypeList;
  private JComboBox<String> accountTypeDescriptionList;
  private JTextField userName;
  private JTextField password;
  
  public UpdateAcctTypeDescListListener(JSONArray jsonArray, JComboBox<String> accountTypeList, JComboBox<String> accountTypeDescriptionList, JTextField userName, JTextField password)
  {
    System.out.println("json :" + jsonArray);
    this.jsonArray = jsonArray;
    this.accountTypeList = accountTypeList;
    this.accountTypeDescriptionList = accountTypeDescriptionList;
    this.userName = userName;
    this.password = password;
  }
  
  public void actionPerformed(ActionEvent e)
  {
    String accountSelected = this.accountTypeList.getSelectedItem().toString();
    String accountDescSelected = "";
    if (this.accountTypeDescriptionList.getSelectedItem() != null) {
      accountDescSelected = this.accountTypeDescriptionList.getSelectedItem().toString();
    }
    if (!"Select".equalsIgnoreCase(accountDescSelected)) {
      try
      {
        AccountDetailsFields fields = findAccountDetails(accountSelected, accountDescSelected);
        System.out.println("fields : " + fields);
        this.userName.setText(fields.getUsername());
        this.userName.setEditable(false);
        this.password.setText(fields.getPassword());
      }
      catch (JSONException e1)
      {
        e1.printStackTrace();
      }
    }
  }
  
  private AccountDetailsFields findAccountDetails(String accountSelected, String accountDescSelected)
    throws JSONException
  {
    AccountDetailsFields accountDetailsFields = new AccountDetailsFields();
    for (int i = 0; i < this.jsonArray.length(); i++)
    {
      JSONObject jsonObject = this.jsonArray.getJSONObject(i);
      if ((jsonObject.has(Constants.ACCOUNT_TYPE_BE)) && 
        (jsonObject.get(Constants.ACCOUNT_TYPE_BE).toString().contains(accountSelected)) && 
        (jsonObject.get(Constants.ACCOUNT_DESC_BE).toString().equals(accountDescSelected)))
      {
        accountDetailsFields.setUsername(jsonObject.get(Constants.USER_NAME_BE).toString());
        accountDetailsFields.setPassword(jsonObject.get(Constants.PASSWORD_BE).toString());
      }
    }
    return accountDetailsFields;
  }
}
