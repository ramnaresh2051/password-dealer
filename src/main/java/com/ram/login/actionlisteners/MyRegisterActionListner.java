package com.ram.login.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
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

public class MyRegisterActionListner implements ActionListener {
	private JSONArray jsonArray;
	private JTextField userName;
	private JPasswordField password;
	private JPasswordField confirmPassword;
	private JTextField securityKey;
	private JTextField idleTimeout;
	private JComboBox<String> securityQuestion_1;
	private JComboBox<String> securityQuestion_2;
	private JTextField securityAnswer_1;
	private JTextField securityAnswer_2;

	public MyRegisterActionListner(JSONArray jsonArray, JTextField userName,
			JPasswordField password, JPasswordField confirmPassword,
			JTextField securityKey, JTextField idleTimeout,
			JComboBox<String> securityQuestion_1, JComboBox<String> securityQuestion_2,
			JTextField securityAnswer_1, JTextField securityAnswer_2) {
		this.jsonArray = jsonArray;
		this.userName = userName;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.securityKey = securityKey;
		this.idleTimeout = idleTimeout;
		this.securityQuestion_1 = securityQuestion_1; 
		this.securityQuestion_2 = securityQuestion_2; 
		this.securityAnswer_1 = securityAnswer_1;
		this.securityAnswer_2 = securityAnswer_2;
	}

	public void actionPerformed(ActionEvent e) {
		String uName = this.userName.getText();
		String uPass = this.password.getText();
		String uCpass = this.confirmPassword.getText();
		String security = this.securityKey.getText();
		String idle = this.idleTimeout.getText();
		String sec_ques1 = this.securityQuestion_1.getSelectedItem().toString();
		String sec_ques2 = this.securityQuestion_2.getSelectedItem().toString();
		String sec_ans1 = this.securityAnswer_1.getText();
		String sec_ans2 = this.securityAnswer_2.getText();
		

		String msg = doValidation(uName, uPass, uCpass, security, idle, sec_ques1, sec_ques2, sec_ans1, sec_ans2);
		
		if (!PasswordManager.isRegistered) {
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
					jsonObject.put(Constants.IS_REGISTERED, "true");
					jsonObject.put(Constants.SEC_QUES_1, sec_ques1);
					jsonObject.put(Constants.SEC_QUES_2, sec_ques2);
					jsonObject.put(Constants.SEC_ANS_1, sec_ans1);
					jsonObject.put(Constants.SEC_ANS_2, sec_ans2);
					jsonArray.put(jsonObject);
					DBManager.writeIntoFile(jsonArray.toString());
					JOptionPane.showMessageDialog(null, "Successfully Registered \nApp will close now and open app again");
					System.exit(0);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(null, msg);
			}
		} else {
			JOptionPane.showMessageDialog(null, "You have already registered");
		}
	}

	private String doValidation(String uName, String uPass, String uCpass,
			String security, String idle, String sec_ques1, String sec_ques2, String sec_ans1, String sec_ans2) {
		String msg = null;
		if (StringUtils.isEmpty(uName) || StringUtils.isEmpty(uPass)
				|| StringUtils.isEmpty(uCpass)
				|| StringUtils.isEmpty(security)
				|| StringUtils.isEmpty(idle) || StringUtils.isEmpty(sec_ans1) || StringUtils.isEmpty(sec_ans2)) {
			msg = "All fields are Mandatory";
		} else if (!StringUtils.isNumeric(idle)) {
			msg = "Only numeric allowed for idle time out";
		}

		if (StringUtils.isNotEmpty(uCpass) && !uCpass.equalsIgnoreCase(uPass)) {
			msg = "Password and Confirm Passwords does not match";
		}
		
		if (StringUtils.equalsIgnoreCase(sec_ques1, "Select")) {
			msg = "Please select security question 1";
		} else if (StringUtils.equalsIgnoreCase(sec_ques2, "Select")) {
			msg = "Please select security question 2";
		}

		return msg;
	}

}
