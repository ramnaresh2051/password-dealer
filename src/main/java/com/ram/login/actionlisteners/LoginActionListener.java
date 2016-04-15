package com.ram.login.actionlisteners;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;

import com.ram.PasswordManager;
import com.ram.constants.Constants;

public class LoginActionListener implements ActionListener {
	private CardLayout cardLayout;
	private JTextField userName;
	private JPasswordField password;

	public LoginActionListener(JTextField userName, JPasswordField password) {
		this.userName = userName;
		this.password = password;

	}

	public void actionPerformed(ActionEvent e) {
		cardLayout = (CardLayout)PasswordManager.cards.getLayout();
		if (StringUtils.isNotEmpty(PasswordManager.loginUserName)
				&& StringUtils.isNotEmpty(PasswordManager.loginPassword)
				&& PasswordManager.loginUserName.equalsIgnoreCase(userName.getText())
				&& PasswordManager.loginPassword.equals(password.getText())) {
			PasswordManager.loginStatus = true;
			PasswordManager.idleTimeOutLong = System.currentTimeMillis();
			password.setText("");
			cardLayout.show(PasswordManager.cards,
					PasswordManager.newAccountDetails.getName());
		} else {
			PasswordManager.loginStatus = false;
			JOptionPane.showMessageDialog(null,
					Constants.LOGIN_ERROR_MSG);
			cardLayout.show(PasswordManager.cards,
					PasswordManager.loginCard.getName());
		}

	}

}
