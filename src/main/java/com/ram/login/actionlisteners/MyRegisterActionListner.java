package com.ram.login.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ram.constants.Constants;
import com.ram.db.DBManager;

public class MyRegisterActionListner implements ActionListener {
	private JSONArray jsonArray;
	private JTextField userName;
	private JPasswordField password;
	private JPasswordField confirmPassword;
	private JTextField securityKey;
	private JTextField idleTimeout;

	public MyRegisterActionListner(JSONArray jsonArray, JTextField userName,
			JPasswordField password, JPasswordField confirmPassword,
			JTextField securityKey, JTextField idleTimeout) {
		this.jsonArray = jsonArray;
		this.userName = userName;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.securityKey = securityKey;
		this.idleTimeout = idleTimeout;
	}

	public void actionPerformed(ActionEvent e) {
		String uName = this.userName.getText();
		String uPass = this.password.getText();
		String uCpass = this.confirmPassword.getText();
		String security = this.securityKey.getText();
		String idle = this.idleTimeout.getText();

		String msg = doValidation(uName, uPass, uCpass, security, idle);
		
		if (StringUtils.isEmpty(msg)) {
			try {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put(Constants.LOGIN_ACCOUNT_USER_NAME,
						uName);
				jsonObject.put(Constants.LOGIN_ACCOUNT_USER_PASS,
						uPass);
				jsonObject.put(Constants.LOGIN_ACCOUNT_USER_KEY, security);
				jsonObject.put(Constants.LOGIN_ACCOUNT_USER_TO, idle);
				jsonObject.put(Constants.USER_CREDENTIAL, true);
				jsonArray.put(jsonObject);
				DBManager.writeIntoFile(jsonArray.toString());
				JOptionPane.showMessageDialog(null, "Successfully Register");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(null, msg);
		}

	}

	private String doValidation(String uName, String uPass, String uCpass,
			String security, String idle) {
		String msg = null;
		if (StringUtils.isEmpty(uName) || StringUtils.isEmpty(uPass)
				|| StringUtils.isEmpty(uCpass)
				|| StringUtils.isEmpty(security)
				|| StringUtils.isEmpty(idle)) {
			msg = "All fields are Mandatory";
		}

		if (StringUtils.isNotEmpty(uCpass) && !uCpass.equalsIgnoreCase(uPass)) {
			msg = "Password and Confirm Passwords does not match";
		}

		return msg;
	}

}
