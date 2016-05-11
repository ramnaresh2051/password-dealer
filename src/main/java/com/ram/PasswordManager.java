package com.ram;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.json.JSONArray;

import com.ram.util.Util;

public final class PasswordManager {
	public static CardPanel cards;
	public static AccountDetails loginCard;
	public static AccountDetails registerCard;
	public static AccountDetails newAccountDetails;
	public static AccountDetails retreviewAccountDetails;
	public static AccountDetails updateAccountDetails;
	public static AccountDetails updateIdleTimeOut;
	public static AccountDetails updateLoginPassword;
	public static JSONArray jsonArray;
	public static DefaultComboBoxModel<String> model = null;
	public static boolean loginStatus = false;
	public static String loginUserName = null;
	public static String loginPassword = null;
	public static String loginKey = null;
	public static String idleTimeOut = null;
	public static boolean isRegistered;
	public static long idleTimeOutLong = System.currentTimeMillis();

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				jsonArray = Util.onStartUp();
				Util.loadUserCredentials(jsonArray);
				Util.createAndShowGUI();
			}
		});
	}


	public static final class ControlPanel extends JPanel implements ActionListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 5581923570712892833L;
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
					
					boolean isTimedOut = Util.checkTimeOut(idleTimeOut, idleTimeOutLong);
					CardLayout cl = (CardLayout) cards.getLayout();
					
					if(!isTimedOut) {

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
			
			boolean isTimedOut = Util.checkTimeOut(idleTimeOut, idleTimeOutLong);
			if (!isTimedOut) {
				PasswordManager.idleTimeOutLong = System.currentTimeMillis();
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
