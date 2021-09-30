package gui;

import javax.swing.*;

//import businessLogic.BLFacade;

import java.awt.Font;
//import java.awt.GridLayout;
import java.awt.event.WindowEvent;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UserMainGUI extends JFrame {
	
	private void customCloseWindow() {
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	public UserMainGUI() {
		getContentPane().setLayout(null);
		setSize(450, 280);
		setTitle("User Main Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel lblMainMenu = new JLabel("Main Menu");
		lblMainMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblMainMenu.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblMainMenu.setBounds(115, 25, 185, 29);
		getContentPane().add(lblMainMenu);
		
		JButton btnCreateForecasts = new JButton("Create Forecasts");
		btnCreateForecasts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame a = new CreateForecastGUI();
				a.setVisible(true);
			}
		});
		btnCreateForecasts.setBounds(22, 139, 123, 36);
		getContentPane().add(btnCreateForecasts);
		
		JButton btnQueryQuestions = new JButton("Query Questions");
		btnQueryQuestions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new FindQuestionsGUI();
				a.setVisible(true);
			}
		});
		btnQueryQuestions.setBounds(171, 86, 113, 42);
		getContentPane().add(btnQueryQuestions);
		
		JButton btnQueryEvents = new JButton("Query Events");
		btnQueryEvents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new FindEventsGUI();
				a.setVisible(true);
			}
		});
		btnQueryEvents.setBounds(171, 139, 113, 36);
		getContentPane().add(btnQueryEvents);
		
		JLabel lblWelcomeBackUsername = new JLabel("Welcome back, " + LoginRegisterGUI.getBusinessLogic().getLoggedUsername());
		lblWelcomeBackUsername.setFont(new Font("Tahoma", Font.ITALIC, 14));
		lblWelcomeBackUsername.setBounds(22, 202, 303, 21);
		getContentPane().add(lblWelcomeBackUsername);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginRegisterGUI.getBusinessLogic().logout();
				LoginRegisterGUI.endAndCloseProgram();
				setVisible(false);
				customCloseWindow();
			}
		});
		btnClose.setBounds(335, 211, 89, 23);
		getContentPane().add(btnClose);
		
		JButton btnLogOut = new JButton("Log out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LoginRegisterGUI.getBusinessLogic().logout();
				LoginRegisterGUI.setLoginVisibility(true);
				setVisible(false);
			}
		});
		btnLogOut.setBounds(335, 187, 89, 23);
		getContentPane().add(btnLogOut);
		
		JButton btnAccount = new JButton("Account");
		btnAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame a = new AccountGUI();
				a.setVisible(true);
			}
		});
		btnAccount.setBounds(335, 11, 89, 23);
		getContentPane().add(btnAccount);
		
		JButton btnNewButton = new JButton("Your Forecasts");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame a = new ForecastsListGUI();
				a.setVisible(true);
			}
		});
		btnNewButton.setBounds(22, 85, 123, 43);
		getContentPane().add(btnNewButton);
	}
}
