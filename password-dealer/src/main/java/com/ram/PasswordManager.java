package com.ram;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ram.constants.Constants;
import com.ram.db.DBManager;
import com.ram.login.actionlisteners.MyRegisterActionListner;
import com.ram.menu.MyMenuBar;
import com.ram.retrieve.actionlisteners.RetrieveAccountDetailsListener;
import com.ram.updates.actionlisteners.DeleteAccountDetailsListener;
import com.ram.updates.actionlisteners.UpdateAccountDetailsListener;
import com.ram.updates.actionlisteners.UpdateAcctTypeDescListListener;
import com.ram.updates.actionlisteners.UpdateAcctTypeListListener;
import com.ram.util.Utility;

public final class PasswordManager {
	private static CardPanel cards;
	private static AccountDetails loginCard;
	private static AccountDetails registerCard;
	private static AccountDetails newAccountDetails;
	private static AccountDetails retreviewAccountDetails;
	private static AccountDetails updateAccountDetails;
	private static JSONArray jsonArray;
	private static DefaultComboBoxModel<String> model = null;
	private static boolean loginStatus = false;
	private static String loginUserName = null;
	private static String loginPassword = null;
	private static String loginKey = null;
	private static String idleTimeOut = null;
	private static long idleTimeOutLong = System.currentTimeMillis();

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				jsonArray = Utility.onStartUp();
				loadUserCredentials();
				createAndShowGUI();
			}

			private void loadUserCredentials() {
				try{
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						if (jsonObject.has(Constants.USER_CREDENTIAL)) {
							loginUserName = jsonObject.getString(Constants.LOGIN_ACCOUNT_USER_NAME);
							loginPassword = jsonObject.getString(Constants.LOGIN_ACCOUNT_USER_PASS);
							loginKey = jsonObject.getString(Constants.LOGIN_ACCOUNT_USER_KEY);
							idleTimeOut = jsonObject.getString(Constants.LOGIN_ACCOUNT_USER_TO);
						}
					}
				}catch (JSONException jsonE) {
					jsonE.printStackTrace();
				}
				
			}
		});
	}

	private static void createAndShowGUI() {
		JFrame frame = new JFrame(Constants.PASSWORD_MANAGER);
		frame.setSize(580, 280);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(
				new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		cards = new CardPanel();
		frame.getContentPane().add(cards);
		frame.getContentPane().add(new ControlPanel());
		frame.setJMenuBar(MyMenuBar.creaetMenuBar());

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private static void createCards() {
		registerCard = new AccountDetails(Constants.REGISTER, new JLabel(
				"This is a register card"), Color.WHITE);
		loginCard = new AccountDetails(Constants.LOGIN, new JLabel(
				"This is card login"), Color.WHITE);
		newAccountDetails = new AccountDetails(Constants.NEW_ACCOUNT_DETAILS,
				new JLabel("This is card one"), Color.DARK_GRAY);
		retreviewAccountDetails = new AccountDetails(
				Constants.RETRIEVE_ACCOUNT_DETAILS, new JLabel(
						"This is card two"), Color.LIGHT_GRAY);
		updateAccountDetails = new AccountDetails(
				Constants.UPDATE_ACCOUNT_DETAILS, new JLabel(
						"This is card three"), Color.GRAY);
	}

	private static final class AccountDetails extends JPanel {
		/**
	 * 
	 */
		private static final long serialVersionUID = 915985806182031011L;
		private final String name;

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
			if (Constants.NEW_ACCOUNT_DETAILS.equals(name)) {
				createNewAccountPanel();
			}
			if (Constants.RETRIEVE_ACCOUNT_DETAILS.equals(name)) {
				retreviewAccountPanel();
			}
			if (Constants.UPDATE_ACCOUNT_DETAILS.equals(name)) {
				updateAccountPanel();
			}

		}

		private void registerPanel() {
			System.out.println("registerPanel");
			JPanel panel = new JPanel(new GridLayout(0, 2, 2, 2));
			final JTextField userName = new JTextField(15);
			final JPasswordField password = new JPasswordField(15);
			final JPasswordField confirmPassword = new JPasswordField(15);
			final JTextField securityKey = new JTextField(15);
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
				
				@Override
				public void focusLost(FocusEvent e) {
					
					
				}
				
				@Override
				public void focusGained(FocusEvent e) {
					idleTimeOut.setText("");
				}
			});
			
			panel.add(idleTimeOut);
			
			
			JButton regiter = new JButton("Register");
			regiter.addActionListener(new MyRegisterActionListner(jsonArray,
					userName, password, confirmPassword
							, securityKey, idleTimeOut
							));
			
			JButton login = new JButton("Login");
			login.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					CardLayout cardLayout = (CardLayout) cards.getLayout();
					cardLayout.show(cards, loginCard.getName());
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
			submitDetails.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("Clicked on submit button: login");
					idleTimeOutLong = System.currentTimeMillis();
					String uName = userName.getText();
					String uPassword = password.getText();
					System.out.println("User Name :" + uName);
					CardLayout cardLayout = (CardLayout) cards.getLayout();
					if (uName.equalsIgnoreCase(loginUserName)
							&& uPassword.equals(loginPassword)) {
						cardLayout.show(cards, newAccountDetails.getName());
						loginStatus = true;
					} else {
						JOptionPane.showMessageDialog(null,
								"UserId or Password is not correct");
						cardLayout.show(cards, loginCard.getName());
						loginStatus = false;
					}
				}
			});

			JButton registerDetails = new JButton("Register");

			registerDetails.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					CardLayout cardLayout = (CardLayout) cards.getLayout();
					cardLayout.show(cards, registerCard.getName());
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
					boolean isTimedOut = Utility.checkTimeOut(idleTimeOut, idleTimeOutLong);
					CardLayout cl = (CardLayout) cards.getLayout();
					if (isTimedOut) {
						JOptionPane.showMessageDialog(null,
								"Your session timed out... please login again");
						cl.show(cards,
								loginCard.getName());
						
					} else {
						idleTimeOutLong = System.currentTimeMillis();
						String accountType = accountTypeList.getSelectedItem()
								.toString();
						String accountDes = accountDescription.getText();
						String uName = userName.getText();
						String uPassword = password.getText();
						System.out.println("Account type :" + accountType);
						System.out.println("Account desc :" + accountDes);
						System.out.println("User Name :" + uName);
						System.out.println("Password :" + uPassword);
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
								System.out.println("new user in json :"
										+ jsonObject);
								jsonArray.put(jsonObject);
								DBManager.writeIntoFile(jsonArray.toString());
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

			model = new DefaultComboBoxModel<String>(acctTypeDescription);
			JComboBox<String> accountTypeDescriptionList = new JComboBox<String>(
					model);
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
					jsonArray, accountTypeList, model);
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

	private static final class CardPanel extends JPanel {
		public CardPanel() {
			super(new CardLayout());
			createCards();
			add(loginCard, loginCard.getName());
			add(newAccountDetails, newAccountDetails.getName());
			add(retreviewAccountDetails, retreviewAccountDetails.getName());
			add(updateAccountDetails, updateAccountDetails.getName());
			add(registerCard, registerCard.getName());
		}
	}

	private static final class ControlPanel extends JPanel implements
			ActionListener {
		private static JButton newAccountDetailsBtn;
		private static JButton retreviewAccountDetailsBtn;
		private static JButton updateAccountDetailsBtn;
		private static JButton showButton;

		public ControlPanel() {
			super();
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			add(createJRadioButtonPanel());
		}

		private final JPanel createJRadioButtonPanel() {
			JPanel panel = new JPanel();

			newAccountDetailsBtn = new JButton(newAccountDetails.getName());
			newAccountDetailsBtn.setSelected(true);
			newAccountDetailsBtn.setActionCommand("new");
			newAccountDetailsBtn.addActionListener(this);

			retreviewAccountDetailsBtn = new JButton(
					retreviewAccountDetails.getName());
			retreviewAccountDetailsBtn.setActionCommand("retreview");
			retreviewAccountDetailsBtn.addActionListener(this);

			updateAccountDetailsBtn = new JButton(
					updateAccountDetails.getName());
			updateAccountDetailsBtn.setActionCommand("update");
			updateAccountDetailsBtn.addActionListener(this);

			panel.add(newAccountDetailsBtn);
			panel.add(retreviewAccountDetailsBtn);
			panel.add(updateAccountDetailsBtn);
			return panel;
		}

		private final JPanel createJButtonPanel() {
			JPanel panel = new JPanel();

			showButton = new JButton("Show");
			showButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					boolean isTimedOut = Utility.checkTimeOut(idleTimeOut, idleTimeOutLong);
					CardLayout cl = (CardLayout) cards.getLayout();
					
					
					if (isTimedOut) {
						
						cl.show(cards,
								loginCard.getName());
						
					} else {
						if (ControlPanel.newAccountDetailsBtn.isSelected()) {
							cl.show(cards,
									ControlPanel.newAccountDetailsBtn.getText());
						} else if (ControlPanel.retreviewAccountDetailsBtn
								.isSelected()) {
							cl.show(cards, ControlPanel.retreviewAccountDetailsBtn
									.getText());
						} else if (ControlPanel.updateAccountDetailsBtn
								.isSelected()) {
							cl.show(cards,
									ControlPanel.updateAccountDetailsBtn.getText());
						}
					}
					
					
					
					
				}
			});
			panel.add(showButton);

			return panel;
		}

		public void actionPerformed(ActionEvent e) {
			CardLayout cardLayout = (CardLayout) cards.getLayout();
			
			boolean isTimedOut = Utility.checkTimeOut(idleTimeOut, idleTimeOutLong);
			CardLayout cl = (CardLayout) cards.getLayout();
			if (isTimedOut) {
				JOptionPane.showMessageDialog(null,
						"Your session timed out... please login again");
				cl.show(cards,
						loginCard.getName());
				
			} else {
				if (e.getActionCommand().equals("new")) {
					System.out.println("new");
					newAccountDetailsBtn.setSelected(true);
					retreviewAccountDetailsBtn.setSelected(false);
					updateAccountDetailsBtn.setSelected(false);
					if (loginStatus) {
						cardLayout.show(cards, newAccountDetailsBtn.getText());
					} else {
						JOptionPane.showMessageDialog(null,
								"Please login first to perform this operation");
						cardLayout.show(cards, loginCard.getName());
						loginStatus = false;
					}
				} else if (e.getActionCommand().equals("retreview")) {
					System.out.println("retreview");
					newAccountDetailsBtn.setSelected(false);
					retreviewAccountDetailsBtn.setSelected(true);
					updateAccountDetailsBtn.setSelected(false);
					if (loginStatus) {
						cardLayout
								.show(cards, retreviewAccountDetailsBtn.getText());
					} else {
						JOptionPane.showMessageDialog(null,
								"Please login first to perform this operation");
						cardLayout.show(cards, loginCard.getName());
						loginStatus = false;
					}
				} else if (e.getActionCommand().equals("update")) {
					System.out.println("update");
					newAccountDetailsBtn.setSelected(false);
					retreviewAccountDetailsBtn.setSelected(false);
					updateAccountDetailsBtn.setSelected(true);
					if (loginStatus) {
						cardLayout.show(cards, updateAccountDetailsBtn.getText());
					} else {
						JOptionPane.showMessageDialog(null,
								"Please login first to perform this operation");
						cardLayout.show(cards, loginCard.getName());
						loginStatus = false;
					}
				}
			}
			
		}
	}
}
