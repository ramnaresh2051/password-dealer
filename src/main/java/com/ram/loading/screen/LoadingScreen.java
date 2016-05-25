package com.ram.loading.screen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;

public class LoadingScreen extends JWindow{
    Panel panel = new Panel();
    public LoadingScreen() {
    	setSize(400,180);
        setLocationRelativeTo(null);
        panel.setBackground(Color.getHSBColor(.27f, .7143f, .76f));
        JProgressBar progressBar = new JProgressBar();
        progressBar.setSize(300, 100);
	    progressBar.setIndeterminate(true);
	    add(progressBar, BorderLayout.AFTER_LAST_LINE);
        add(panel);
	    setVisible(true);
    }
    
    public void stop() {
    	dispose();
    }
    
    public static void main(String[] args) {
    	LoadingScreen m = new LoadingScreen();
//        m.setSize(640,480);
//        m.setLocationRelativeTo(null);
//        m.setVisible(true);
    }

    class Panel extends JPanel {

        public void paintComponent(Graphics g){
        	 super.paintComponent(g);
             g.setFont(new Font("Verdana",Font.ITALIC,24));
             g.setColor(Color.WHITE);
             g.drawString("Password", 20, 50);
             g.setColor(Color.BLACK);
             g.setFont(new Font("Verdana",Font.BOLD,24));
             g.drawString("Manager", 80, 70);

             g.setFont(new Font("Verdana",Font.PLAIN,10));
             g.drawString("PasswordManager Free Edition v1.0.0", 150,150);
        }
    }

}
