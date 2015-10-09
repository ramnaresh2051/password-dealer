package com.ram.login.actionlisteners;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.json.JSONArray;

public class LoginActionListener implements ActionListener {
	private JSONArray jsonArray;
	private CardLayout cardLayout;
	private JTextField userName;
	private JTextField password;
	private String newAccountName;
	private String loginCardName;

	public LoginActionListener(JSONArray jsonArray, CardLayout cardLayout,
			JTextField userName, JTextField password, String newAccountName, String loginCardName) {
		this.jsonArray = jsonArray;
		this.cardLayout = cardLayout;
		this.userName = userName;
		this.password = password;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("ram".equalsIgnoreCase(userName.getText()) && "Apple123".equals(password.getText())) {
//			cardLayout.show(this., newAccountName);
        } else {
           JOptionPane.showMessageDialog(null, "Please login first to perform this operation");
//     	   cardLayout.show(this, loginCardName);
//     	   loginStatus = false;
        }

	}

}
