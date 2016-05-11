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

public class UpdateLoginPasswdListnr implements ActionListener{
	
	private JSONArray jsonArray;
	private JTextField userName;
	private JPasswordField password;
	private JPasswordField newPassword;
	private JPasswordField retypeNewPasswd;

	public UpdateLoginPasswdListnr(JSONArray jsonArray, JTextField userName,
			JPasswordField password, JPasswordField newPassword, JPasswordField retypeNewPasswd) {
		this.jsonArray = jsonArray;
		this.userName = userName;
		this.password = password;
		this.newPassword = newPassword;
		this.retypeNewPasswd = retypeNewPasswd;
	}

	public void actionPerformed(ActionEvent e) {
		String uName = this.userName.getText();
		String uPass = this.password.getText();
		String uNewPass = this.newPassword.getText();
		String uRetypeNewPass = this.retypeNewPasswd.getText();

		String msg = doValidation(uName, uPass, uNewPass, uRetypeNewPass);
		
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
								uNewPass);
						jsonObject.put(Constants.LOGIN_ACCOUNT_USER_KEY, jsonObjectOne.get(Constants.LOGIN_ACCOUNT_USER_KEY));
						jsonObject.put(Constants.LOGIN_ACCOUNT_USER_TO, jsonObjectOne.get(Constants.LOGIN_ACCOUNT_USER_TO));
						jsonObject.put(Constants.USER_CREDENTIAL, true);
			            jsonArray.remove(i);
			            jsonArray.put(jsonObject);
			        }
			      }
			      DBManager.writeIntoFile(jsonArray.toString());
			      JOptionPane.showMessageDialog(null, "New Password Updated successfully");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(null, msg);
		}

	}

	private String doValidation(String uName, String uPass, String uNewPass, String uRetypeNewPass) {
		String msg = null;
		if (StringUtils.isEmpty(uName) || StringUtils.isEmpty(uPass)
				|| StringUtils.isEmpty(uNewPass) || StringUtils.isEmpty(uRetypeNewPass)) {
			msg = "All fields are Mandatory";
		}

		if (StringUtils.isEmpty(PasswordManager.loginUserName)
				&& StringUtils.isEmpty(PasswordManager.loginPassword)
				&& !PasswordManager.loginUserName.equalsIgnoreCase(userName.getText())
				&& !PasswordManager.loginPassword.equals(password.getText())) {
			msg = Constants.LOGIN_ERROR_MSG;
		} else if (!uNewPass.equals(uRetypeNewPass)){
		    msg = Constants.PASSWD_CHANGE_ERROR_MSG;
		}

		return msg;
	}

}
