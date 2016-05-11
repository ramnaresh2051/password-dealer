package com.ram;

import java.awt.CardLayout;

import javax.swing.JPanel;

import com.ram.util.Util;

public final class CardPanel extends JPanel {
	public CardPanel() {
		super(new CardLayout());
		Util.createCards();
		add(PasswordManager.loginCard, PasswordManager.loginCard.getName());
		add(PasswordManager.newAccountDetails, PasswordManager.newAccountDetails.getName());
		add(PasswordManager.retreviewAccountDetails, PasswordManager.retreviewAccountDetails.getName());
		add(PasswordManager.updateAccountDetails, PasswordManager.updateAccountDetails.getName());
		add(PasswordManager.registerCard, PasswordManager.registerCard.getName());
		add(PasswordManager.updateIdleTimeOut, PasswordManager.updateIdleTimeOut.getName());
		add(PasswordManager.updateLoginPassword, PasswordManager.updateLoginPassword.getName());
	}
}
