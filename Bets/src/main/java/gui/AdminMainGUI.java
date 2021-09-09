package gui;

import javax.swing.*;

import domain.Event;
//import businessLogic.BLFacade;

import java.awt.Color;
import java.awt.Font;
//import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminMainGUI extends JFrame {
	
	private void customCloseWindow() {
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	public AdminMainGUI() {
		getContentPane().setLayout(null);
		setSize(500, 300);
		setTitle("Admin Main Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblAdministratorMainMenu = new JLabel("ADMINISTRATOR Main Menu");
		lblAdministratorMainMenu.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblAdministratorMainMenu.setBounds(111, 26, 250, 32);
		getContentPane().add(lblAdministratorMainMenu);
		
		JButton btnQueryEvent = new JButton("Query Event");
		btnQueryEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new FindEventsGUI();
				a.setVisible(true);
			}
		});
		btnQueryEvent.setBounds(26, 90, 115, 32);
		getContentPane().add(btnQueryEvent);
		
		JButton btnCreateEvent = new JButton("Create Event");
		btnCreateEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new CreateEventsGUI();
				a.setVisible(true);
			}
		});
		btnCreateEvent.setBounds(26, 133, 115, 32);
		getContentPane().add(btnCreateEvent);
		
		JButton btnQueryQuestions = new JButton("Query Questions");
		btnQueryQuestions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new FindQuestionsGUI();
				a.setVisible(true);
			}
		});
		btnQueryQuestions.setBounds(171, 90, 115, 32);
		getContentPane().add(btnQueryQuestions);
		
		JButton btnCreateQuestion = new JButton("Create Question");
		btnCreateQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new CreateQuestionGUI(new Vector<Event>());
				a.setVisible(true);
			}
		});
		btnCreateQuestion.setBounds(171, 133, 115, 32);
		getContentPane().add(btnCreateQuestion);
		
		JLabel lblWelcomeBackUsername = new JLabel("Welcome back, " + LoginRegisterGUI.getBusinessLogic().getLoggedUsername());
		lblWelcomeBackUsername.setFont(new Font("Tahoma", Font.ITALIC, 14));
		lblWelcomeBackUsername.setHorizontalAlignment(SwingConstants.LEFT);
		lblWelcomeBackUsername.setBounds(26, 189, 242, 32);
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
		btnClose.setBounds(371, 196, 89, 23);
		getContentPane().add(btnClose);
		
		JButton btnLogOut = new JButton("Log out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginRegisterGUI.getBusinessLogic().logout();
				LoginRegisterGUI.setLoginVisibility(true);
				setVisible(false);
			}
		});
		btnLogOut.setBounds(272, 196, 89, 23);
		getContentPane().add(btnLogOut);
		
		JButton ForecastResult = new JButton("ForecastResult");
		ForecastResult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame a = new ForecastResultGUI();
				a.setVisible(true);
				
			}
		});
		ForecastResult.setBounds(306, 90, 121, 32);
		getContentPane().add(ForecastResult);
		
		JButton btnNewButton = new JButton("Close event");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame a = new CloseEventGUI();
				a.setVisible(true);
			}
		});
		btnNewButton.setBounds(316, 138, 111, 23);
		getContentPane().add(btnNewButton);
	}
}
