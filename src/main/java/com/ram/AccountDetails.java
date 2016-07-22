package com.ram;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ram.constants.Constants;
import com.ram.db.DBManager;
import com.ram.login.actionlisteners.LoginActionListener;
import com.ram.login.actionlisteners.MyRegisterActionListner;
import com.ram.menu.actionlisteners.UpdateIdleTimeOutAcnListnr;
import com.ram.menu.actionlisteners.UpdateLoginPasswdListnr;
import com.ram.retrieve.actionlisteners.RetrieveAccountDetailsListener;
import com.ram.updates.actionlisteners.DeleteAccountDetailsListener;
import com.ram.updates.actionlisteners.UpdateAccountDetailsListener;
import com.ram.updates.actionlisteners.UpdateAcctTypeDescListListener;
import com.ram.updates.actionlisteners.UpdateAcctTypeListListener;
import com.ram.util.Util;

public final class AccountDetails extends JPanel {
	/**
 * 
 */
	private static final long serialVersionUID = 915985806182031011L;
	private final String name;
	
	private static JSONArray jsonArray = PasswordManager.jsonArray;

	public AccountDetails(String name, JComponent component, Color color) {
		super();
		this.name = name;
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setBackground(color);
		if (Constants.REGISTER.equals(name)) {
			registerPanel();
		}
		if (Constants.LOGIN.equals(name)) {
			loginPanel();
		}
		if (Constants.UPDATE_IDLE_TIME_OUT.equals(name)) {
			updateIdleTimeOut();
		}
		if (Constants.UPDATE_PASSWORD.equals(name)) {
			updateLoginPassword();
		}
		if (Constants.NEW_ACCOUNT_DETAILS.equals(name)) {
			createNewAccountPanel();
		}
		if (Constants.RETRIEVE_ACCOUNT_DETAILS.equals(name)) {
			retreviewAccountPanel();
		}
		if (Constants.UPDATE_ACCOUNT_DETAILS.equals(name)) {
			updateAccountPanel();
		}
		if (Constants.FORGOT_PASSWORD.equals(name)) {
			forgotPassword();
		}

	}

	private void forgotPassword() {
		System.out.println("updateIdleTimeOut");
		JPanel panel = new JPanel(new GridLayout(0, 2, 2, 2));
		final JTextField userName = new JTextField(15);
		final JTextField answer_1 = new JTextField(15);
		final JTextField answer_2 = new JTextField(15);
		final JButton retrievePassword = new JButton("Retireve Password");
		final JButton login = new JButton(Constants.LOGIN);
		panel.add(new JLabel(Constants.USER_NAME_UI));
		panel.add(userName);
		panel.add(new JLabel("Security Question 1:"));
		panel.add(new JLabel(PasswordManager.SEC_QUES_1));
		panel.add(new JLabel("Security Answer 1:"));
		panel.add(answer_1);
		panel.add(new JLabel("Security Question 2:"));
		panel.add(new JLabel(PasswordManager.SEC_QUES_2));
		panel.add(new JLabel("Security Answer 2:"));
		panel.add(answer_2);
		panel.add(login);
		panel.add(retrievePassword);
		
		retrievePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (PasswordManager.loginUserName.equalsIgnoreCase(userName
						.getText())
						&& PasswordManager.SEC_ANS_1.equalsIgnoreCase(answer_1
								.getText())
						&& PasswordManager.SEC_ANS_2.equalsIgnoreCase(answer_2
								.getText())) {
					JOptionPane.showMessageDialog(null, "Your password is : "+ PasswordManager.loginPassword);
				} else {
					JOptionPane.showMessageDialog(null, "Please try again");
				}
				
			}
		});
		
		login.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) PasswordManager.cards.getLayout();
				cardLayout.show(PasswordManager.cards, PasswordManager.loginCard.getName());
				
			}
		});
		
		add(panel);
		
	}

	private void updateIdleTimeOut() {
		System.out.println("updateIdleTimeOut");
		JPanel panel = new JPanel(new GridLayout(0, 2, 2, 2));
		final JTextField userName = new JTextField(15);
		final JPasswordField password = new JPasswordField(15);
		final JTextField idleTimeOut = new JTextField(15);
		idleTimeOut.setToolTipText("Time is in minutes");
		idleTimeOut.setText("Time is in minutes");
		panel.add(new JLabel(Constants.USER_NAME_UI));
		panel.add(userName);
		panel.add(new JLabel(Constants.PASSWORD_UI));
		panel.add(password);
		JLabel timeOut = new JLabel("Idle TimeOut : ?");
		timeOut.setToolTipText("Time out for user in idle case ... and it's in minutes");
		
		panel.add(timeOut);
		
		idleTimeOut.addFocusListener(new FocusListener() {
			
			public void focusLost(FocusEvent e) {
				
				
			}
			
			public void focusGained(FocusEvent e) {
				idleTimeOut.setText("");
			}
		});
		
		panel.add(idleTimeOut);
		
		
		JButton update = new JButton("Update");
		update.addActionListener(new UpdateIdleTimeOutAcnListnr(jsonArray,
				userName, password, 
						 idleTimeOut
						));
		
		JButton login = new JButton(Constants.LOGIN);
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) PasswordManager.cards.getLayout();
				cardLayout.show(PasswordManager.cards, PasswordManager.loginCard.getName());
			}
		});
		
		panel.add(login);
		panel.add(update);
		add(panel);
		
	}
	
	private void updateLoginPassword() {
		System.out.println("updateLoginPassword");
		JPanel panel = new JPanel(new GridLayout(0, 2, 2, 2));
		final JTextField userName = new JTextField(15);
		final JPasswordField password = new JPasswordField(15);
		final JPasswordField newPassword = new JPasswordField(15);
		final JPasswordField retypeNewPassword = new JPasswordField(15);
		
		panel.add(new JLabel(Constants.USER_NAME_UI));
		panel.add(userName);
		panel.add(new JLabel(Constants.PASSWORD_UI));
		panel.add(password);
		
		JLabel newPasswdLabel = new JLabel(Constants.NEW_PASSWORD_UI);
		newPasswdLabel.setToolTipText("Enter New Password");
		panel.add(newPasswdLabel);
		panel.add(newPassword);
		
		JLabel retypeNewPasswdLabel = new JLabel(Constants.RETYPE_NEW_PASSWORD_UI);
		retypeNewPasswdLabel.setToolTipText("Retype New Password");
		panel.add(retypeNewPasswdLabel);
		panel.add(retypeNewPassword);
		
		retypeNewPassword.addFocusListener(new FocusListener() {
			
			public void focusLost(FocusEvent e) {
				
				
			}
			
			public void focusGained(FocusEvent e) {
				retypeNewPassword.setText("");
			}
		});
		
		
		
		
		JButton update = new JButton("Update");
		update.addActionListener(new UpdateLoginPasswdListnr(jsonArray,
				userName, password, newPassword,
				retypeNewPassword
						));
		
		JButton login = new JButton("Login");
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) PasswordManager.cards.getLayout();
				cardLayout.show(PasswordManager.cards, PasswordManager.loginCard.getName());
			}
		});
		
		panel.add(login);
		panel.add(update);
		add(panel);
		
	}

	private void registerPanel() {
		System.out.println("registerPanel");
		JPanel panel = new JPanel(new GridLayout(0, 2, 2, 2));
		final JTextField userName = new JTextField(15);
		final JPasswordField password = new JPasswordField(15);
		final JPasswordField confirmPassword = new JPasswordField(15);
		final JTextField securityKey = new JTextField(15);
		final JTextField securityAnswer_1 = new JTextField(15);
		final JTextField securityAnswer_2 = new JTextField(15);
		securityKey.setToolTipText("This key will be used to encrypt the data while saving to file");
		final JTextField idleTimeOut = new JTextField(15);
		idleTimeOut.setToolTipText("Time is in minutes");
		idleTimeOut.setText("Time is in minutes");
		panel.add(new JLabel(Constants.USER_NAME_UI));
		panel.add(userName);
		panel.add(new JLabel(Constants.PASSWORD_UI));
		panel.add(password);
		panel.add(new JLabel("Confirm Password :"));
		panel.add(confirmPassword);
		panel.add(new JLabel("Security Key :"));
		panel.add(securityKey);
		JLabel timeOut = new JLabel("Idle TimeOut : ?");
		timeOut.setToolTipText("Time out for user in idle case ... and it's in minutes");
		
		panel.add(timeOut);
		
		idleTimeOut.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				
			}
			
			public void focusGained(FocusEvent e) {
				idleTimeOut.setText("");
			}
		});
		
		securityKey.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				
			}
			
			public void focusGained(FocusEvent e) {
				securityKey.setText("");
			}
		});
		
		panel.add(idleTimeOut);
		
		panel.add(new JLabel("Security Question 1 :"));

		final JComboBox<String> SECURITY_QUESTION_LIST_ONE = new JComboBox<String>(
				Constants.SECURITY_QUESTION_LIST_ONE);
		SECURITY_QUESTION_LIST_ONE.setSize(20, 10);
		SECURITY_QUESTION_LIST_ONE.setSelectedIndex(0);
		panel.add(SECURITY_QUESTION_LIST_ONE);
		
		panel.add(new JLabel("Security Answer 1 :"));
		panel.add(securityAnswer_1);
		
		panel.add(new JLabel("Security Question 2 :"));

		final JComboBox<String> SECURITY_QUESTION_LIST_TWO = new JComboBox<String>(
				Constants.SECURITY_QUESTION_LIST_TWO);
		SECURITY_QUESTION_LIST_TWO.setSelectedIndex(0);
		panel.add(SECURITY_QUESTION_LIST_TWO);
		
		panel.add(new JLabel("Security Answer 2 :"));
		panel.add(securityAnswer_2);
		
		
		
		JButton regiter = new JButton("Register");
		regiter.addActionListener(new MyRegisterActionListner(jsonArray,
				userName, password, confirmPassword
						, securityKey, idleTimeOut, SECURITY_QUESTION_LIST_ONE, 
						SECURITY_QUESTION_LIST_TWO, securityAnswer_1, securityAnswer_2
						));
		
		JButton login = new JButton("Login");
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) PasswordManager.cards.getLayout();
				cardLayout.show(PasswordManager.cards, PasswordManager.loginCard.getName());
			}
		});
		
		panel.add(login);
		panel.add(regiter);
		add(panel);

	}

	private void loginPanel() {
		System.out.println("loginPanel");
		JPanel panel = new JPanel(new GridLayout(0, 2, 2, 2));
		final JTextField userName = new JTextField(15);
		final JPasswordField password = new JPasswordField(15);
		panel.add(new JLabel(Constants.USER_NAME_UI));
		panel.add(userName);
		panel.add(new JLabel(Constants.PASSWORD_UI));
		panel.add(password);
		JButton submitDetails = new JButton("Login");
		
		
		submitDetails.addActionListener(new LoginActionListener(userName, password));
		
		JButton registerDetails = new JButton("Register");

		registerDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) PasswordManager.cards.getLayout();
				cardLayout.show(PasswordManager.cards, PasswordManager.registerCard.getName());
			}
		});

		panel.add(registerDetails);
		panel.add(submitDetails);
		add(panel);
	}

	public void createNewAccountPanel() {
		System.out.println("createNewAccountPanel");
		JPanel panel = new JPanel(new GridLayout(0, 2, 2, 2));

		String[] acctType = Constants.ACCOUNT_TYPE_ARRAY;

		final JComboBox<String> accountTypeList = new JComboBox<String>(
				acctType);
		accountTypeList.setSelectedIndex(0);

		final JTextField accountDescription = new JTextField(15);
		final JTextField userName = new JTextField(15);
		final JTextField password = new JTextField(15);

		panel.add(new JLabel(Constants.ACCOUNT_TYPE_UI));
		panel.add(accountTypeList);
		panel.add(new JLabel(Constants.ACCOUNT_DESC_UI));
		panel.add(accountDescription);
		panel.add(new JLabel(Constants.USER_NAME_UI));
		panel.add(userName);
		panel.add(new JLabel(Constants.PASSWORD_UI));
		panel.add(password);
		panel.add(new JLabel());
		JButton submitDetails = new JButton(Constants.SUBMIT_DETAILS);
		submitDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out
						.println("Clicked on submit button: Create New Account");
				boolean isTimedOut = Util.checkTimeOut(PasswordManager.idleTimeOut, PasswordManager.idleTimeOutLong);
				
				if (!isTimedOut) {
					String accountType = accountTypeList.getSelectedItem()
							.toString();
					String accountDes = accountDescription.getText();
					String uName = userName.getText();
					String uPassword = password.getText();
					System.out.println("Account type :" + accountType);
					System.out.println("Account desc :" + accountDes);
					System.out.println("User Name :" + uName);
					boolean fieldValidated = doValidationOfFields(accountType,
							accountDes, uName, uPassword);
					if (fieldValidated) {
						System.out.println("All fields are correct");
						try {
							JSONObject jsonObject = new JSONObject();
							jsonObject.put(Constants.ACCOUNT_TYPE_BE,
									accountType);
							jsonObject.put(Constants.ACCOUNT_DESC_BE,
									accountDes);
							jsonObject.put(Constants.USER_NAME_BE, uName);
							jsonObject.put(Constants.PASSWORD_BE, uPassword);
//							System.out.println("new user in json :"
//									+ jsonObject);
							jsonArray.put(jsonObject);
							DBManager.writeIntoFile(jsonArray.toString());
							password.setText("");
							JOptionPane.showMessageDialog(null, "Record inserted Successfully");
						} catch (JSONException e1) {
							e1.printStackTrace();
						}
					} else {
						System.out.println("Issue with fileds");
					}
				}
				
			}

			private boolean doValidationOfFields(String accountType,
					String accountDes, String uName, String uPassword) {
				boolean flag = true;
				if (accountType.equalsIgnoreCase("select")) {
					JOptionPane.showMessageDialog(null,
							"Please select Account Type");
					flag = false;
				} else if (accountDes.isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Please enter account description");
					flag = false;
				} else if (uName.isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Please enter User Name");
					flag = false;
				} else if (uPassword.isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Please enter your password");
					flag = false;
				}
				return flag;
			}
		});
		panel.add(submitDetails);
		add(panel);
	}

	public void retreviewAccountPanel() {
		System.out.println("retreviewAccountPanel");
		JPanel panel = new JPanel(new GridLayout(0, 2, 2, 2));

		String[] acctType = Constants.ACCOUNT_TYPE_ARRAY;

		JComboBox<String> accountTypeList = new JComboBox<String>(acctType);
		accountTypeList.setSelectedIndex(0);

		JTextField accountDescription = new JTextField(15);
		JTextField userName = new JTextField(15);
		JTextField password = new JTextField(15);

		panel.add(new JLabel(" Account Type :"));
		panel.add(accountTypeList);
		panel.add(new JLabel(" Account Description :"));
		panel.add(accountDescription);
		panel.add(new JLabel(" User Name :"));
		panel.add(userName);

		panel.add(new JLabel());
		JButton retrieveDetails = new JButton("Retrieve Info");
		panel.add(retrieveDetails);
		add(panel);

		RetrieveAccountDetailsListener retrieveAccountDetailsListener = new RetrieveAccountDetailsListener(
				jsonArray, accountTypeList, accountDescription, userName,
				password);
		retrieveDetails.addActionListener(retrieveAccountDetailsListener);
	}

	public void updateAccountPanel() {
		System.out.println("updateAccountPanel");
		JPanel panel = new JPanel(new GridLayout(0, 2, 2, 2));

		String[] acctType = Constants.ACCOUNT_TYPE_ARRAY;
		String[] acctTypeDescription = { "Select" };
		JTextField userName = new JTextField(15);
		JTextField password = new JTextField(15);

		JComboBox<String> accountTypeList = new JComboBox<String>(acctType);
		accountTypeList.setSelectedIndex(0);

		PasswordManager.model = new DefaultComboBoxModel<String>(acctTypeDescription);
		JComboBox<String> accountTypeDescriptionList = new JComboBox<String>(
				PasswordManager.model);
		accountTypeList.setSelectedIndex(0);

		panel.add(new JLabel("Account Type :"));
		panel.add(accountTypeList);
		panel.add(new JLabel("Account Description :"));
		panel.add(accountTypeDescriptionList);
		panel.add(new JLabel("User Name :"));
		panel.add(userName);
		panel.add(new JLabel("Password :"));
		panel.add(password);
		JButton deleteAccountDetails = new JButton("Delete Record");
		panel.add(deleteAccountDetails);
		JButton updateAccountDetails = new JButton("Update Details");
		panel.add(updateAccountDetails);
		add(panel);

		UpdateAcctTypeListListener acctTypeListListener = new UpdateAcctTypeListListener(
				jsonArray, accountTypeList, PasswordManager.model);
		accountTypeList.addActionListener(acctTypeListListener);

		UpdateAcctTypeDescListListener acctTypeDescListListener = new UpdateAcctTypeDescListListener(
				jsonArray, accountTypeList, accountTypeDescriptionList,
				userName, password);
		accountTypeDescriptionList
				.addActionListener(acctTypeDescListListener);

		UpdateAccountDetailsListener accountDetailsListener = new UpdateAccountDetailsListener(
				jsonArray, accountTypeList, accountTypeDescriptionList,
				userName, password);
		updateAccountDetails.addActionListener(accountDetailsListener);

		DeleteAccountDetailsListener deleteAccountDetailsListener = new DeleteAccountDetailsListener(
				jsonArray, accountTypeList, accountTypeDescriptionList,
				userName, password);
		deleteAccountDetails
				.addActionListener(deleteAccountDetailsListener);
	}

	public final String getName() {
		return this.name;
	}
}
