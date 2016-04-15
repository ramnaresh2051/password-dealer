package com.ram.menu.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ram.PasswordManager;
import com.ram.constants.Constants;
import com.ram.db.DBManager;

public class UpdateIdleTimeOutAcnListnr implements ActionListener{
	
	private JSONArray jsonArray;
	private JTextField userName;
	private JPasswordField password;
	private JTextField idleTimeout;

	public UpdateIdleTimeOutAcnListnr(JSONArray jsonArray, JTextField userName,
			JPasswordField password, JTextField idleTimeout) {
		this.jsonArray = jsonArray;
		this.userName = userName;
		this.password = password;
		this.idleTimeout = idleTimeout;
	}

	public void actionPerformed(ActionEvent e) {
		String uName = this.userName.getText();
		String uPass = this.password.getText();
		String idle = this.idleTimeout.getText();

		String msg = doValidation(uName, uPass, idle);
		
		if (StringUtils.isEmpty(msg)) {
			try {
				
				for (int i = 0; i < jsonArray.length(); i++) {
			        JSONObject jsonObjectOne = this.jsonArray.getJSONObject(i);
			        if ((jsonObjectOne.has(Constants.USER_CREDENTIAL)) && 
			          (jsonObjectOne.get(Constants.LOGIN_ACCOUNT_USER_NAME).toString().equals(PasswordManager.loginUserName)) && 
			          (jsonObjectOne.get(Constants.LOGIN_ACCOUNT_USER_PASS).toString().equals(PasswordManager.loginPassword))) {
			        	JSONObject jsonObject = new JSONObject();
						jsonObject.put(Constants.LOGIN_ACCOUNT_USER_NAME,
								PasswordManager.loginUserName);
						jsonObject.put(Constants.LOGIN_ACCOUNT_USER_PASS,
								PasswordManager.loginPassword);
						jsonObject.put(Constants.LOGIN_ACCOUNT_USER_KEY, jsonObjectOne.get(Constants.LOGIN_ACCOUNT_USER_KEY));
						jsonObject.put(Constants.LOGIN_ACCOUNT_USER_TO, idle);
						jsonObject.put(Constants.USER_CREDENTIAL, true);
			            jsonArray.remove(i);
			            jsonArray.put(jsonObject);
			        }
			      }
			      DBManager.writeIntoFile(jsonArray.toString());
			      JOptionPane.showMessageDialog(null, "Updated successfully");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(null, msg);
		}

	}

	private String doValidation(String uName, String uPass, String idle) {
		String msg = null;
		if (StringUtils.isEmpty(uName) || StringUtils.isEmpty(uPass)
				|| StringUtils.isEmpty(idle)) {
			msg = "All fields are Mandatory";
		}

		if (StringUtils.isNotEmpty(PasswordManager.loginUserName)
				&& StringUtils.isNotEmpty(PasswordManager.loginPassword)
				&& PasswordManager.loginUserName.equalsIgnoreCase(userName.getText())
				&& PasswordManager.loginPassword.equals(password.getText())) {
		} else {
			msg = Constants.LOGIN_ERROR_MSG;
		}

		return msg;
	}

}
