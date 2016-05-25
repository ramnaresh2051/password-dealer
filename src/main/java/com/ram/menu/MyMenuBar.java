package com.ram.menu;

import java.awt.CardLayout;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.ram.PasswordManager;

public class MyMenuBar extends JFrame{
	private static CardLayout cardLayout;
	
	public static JMenuBar creaetMenuBar() {
		  JMenuBar menubar = new JMenuBar();
	      ImageIcon icon = new ImageIcon("exit.png");

	      JMenu file = new JMenu("Password Manager");
	      file.setMnemonic(KeyEvent.VK_F);
	      
	      JMenu profileItem = new JMenu("Profile");
	      profileItem.setMnemonic(KeyEvent.VK_E);
	      profileItem.setToolTipText("User Profile");
	      
	      JMenuItem changePassword = new JMenuItem("Change Password", icon);
	      changePassword.setMnemonic(KeyEvent.VK_E);
	      changePassword.setToolTipText("Change User Login Password");
	      
	      JMenuItem changeSecurityKey = new JMenuItem("Change SecutrityKey", icon);
	      changeSecurityKey.setMnemonic(KeyEvent.VK_E);
	      changeSecurityKey.setToolTipText("Change SecutrityKey");
	      
	      JMenuItem forgotPassword = new JMenuItem("Forgot Password", icon);
	      changeSecurityKey.setMnemonic(KeyEvent.VK_E);
	      changeSecurityKey.setToolTipText("Forgot Password");
	      
	      JMenuItem changeIdleTimeOut = new JMenuItem("Change Idle TimeOut", icon);
	      changeIdleTimeOut.setMnemonic(KeyEvent.VK_E);
	      changeIdleTimeOut.setToolTipText("Change Idle TimeOut");
	      
	      changeIdleTimeOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
//				JOptionPane.showMessageDialog(null, "Successfully Register");
				cardLayout = (CardLayout)PasswordManager.cards.getLayout();
				cardLayout.show(PasswordManager.cards,
						PasswordManager.updateIdleTimeOut.getName());
				
			}
		});
	      
	      forgotPassword.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
//					JOptionPane.showMessageDialog(null, "Successfully Register");
					cardLayout = (CardLayout)PasswordManager.cards.getLayout();
					cardLayout.show(PasswordManager.cards,
							PasswordManager.FORGOT_PASSWORD.getName());
					
				}
			});
	      
	      changePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
//				JOptionPane.showMessageDialog(null, "Successfully Register");
				cardLayout = (CardLayout)PasswordManager.cards.getLayout();
				cardLayout.show(PasswordManager.cards,
						PasswordManager.updateLoginPassword.getName());
				
			}
		});
	      
	      profileItem.add(changePassword);
	      profileItem.add(changeSecurityKey);
	      profileItem.add(changeIdleTimeOut);
	      profileItem.add(forgotPassword);
	      
	      JMenuItem settings = new JMenuItem("Settings", icon);
	      settings.setMnemonic(KeyEvent.VK_E);
	      settings.setToolTipText("User Settings");
	      
	      JMenuItem eMenuItem = new JMenuItem("Exit", icon);
	      eMenuItem.setMnemonic(KeyEvent.VK_E);
	      eMenuItem.setToolTipText("Exit application");
	      eMenuItem.addActionListener(new ActionListener() {
	          public void actionPerformed(ActionEvent event) {
	              System.exit(0);
	          }
	      });
	      file.add(profileItem);
	      file.add(settings);
	      file.add(eMenuItem);
	      menubar.add(file);
		return menubar;
	}

}
