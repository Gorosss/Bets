package gui;

import javax.swing.JFrame;
import com.toedter.calendar.JCalendar;
import domain.Question;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;

import javax.swing.table.DefaultTableModel;

public class LoginGUI extends JFrame {
	
	private JTextField txt_username;
	private JPasswordField txtpw_userpassword;
	private JLabel lblLoginSucessful;
	
	private void customCloseWindow() {
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	public LoginGUI() {
		getContentPane().setLayout(null);
		setTitle("Login");
		this.setSize(new Dimension(479, 306));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		txt_username = new JTextField();
		txt_username.setBounds(212, 48, 159, 20);
		getContentPane().add(txt_username);
		txt_username.setColumns(10);
		
		JLabel lblUsuario = new JLabel("Username");
		lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsuario.setHorizontalAlignment(SwingConstants.LEFT);
		lblUsuario.setBounds(53, 42, 123, 29);
		getContentPane().add(lblUsuario);
		
		JLabel lblContrasea = new JLabel("Password");
		lblContrasea.setHorizontalAlignment(SwingConstants.LEFT);
		lblContrasea.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblContrasea.setBounds(53, 91, 113, 24);
		getContentPane().add(lblContrasea);
		
		JButton btnIniciarSesin = new JButton("Login");
		btnIniciarSesin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					
					String str_username = txt_username.getText();
					String str_password = new String(txtpw_userpassword.getPassword());
					
					boolean error = false;

					if (str_username.length() < 1) {
						System.out.println("Error. No username was entered.");
						error = true;
					} else if (str_password.length() < 1){
						System.out.println("Error. No password was entered.");
						error = true;
					}
					
					if (!error) {
						error = LoginRegisterGUI.getBusinessLogic().tryToLogin(str_username, str_password);
						if (error) {
							//lblLoginSucessful.setText("Logged in successfully.");
							if (LoginRegisterGUI.getBusinessLogic().isAdmin()) {
								// ABRIR GUI DE LOS ADMINS
								AdminMainGUI menu = new AdminMainGUI();
								menu.setVisible(true);
							} else {
								// ABRIR GUI DE LOS USUARIOS
								UserMainGUI menu = new UserMainGUI();
								menu.setVisible(true);
							}
						}
						//customCloseWindow();
						setVisible(false);
						LoginRegisterGUI.setVisibility(false);
						//LoginRegisterGUI.setVisibility(false);
					} else { 
						lblLoginSucessful.setText("Failed to log in.");  
					}
					System.out.println("\n\n");
					
				} catch (Exception e) {
					System.out.println("Error. Illegal Login Format. Code 7.");
				} finally {
				}
				
				
			}
		});
		
		btnIniciarSesin.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnIniciarSesin.setBounds(53, 163, 141, 44);
		getContentPane().add(btnIniciarSesin);
		
		JButton btnRegistrarse = new JButton("Register");
		btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame r = new RegisterGUI();
				r.setVisible(true);
			}
		});
		btnRegistrarse.setFont(new Font("Tahoma", Font.ITALIC, 12));
		btnRegistrarse.setBounds(241, 178, 145, 29);
		getContentPane().add(btnRegistrarse);
		
		JLabel lblNoTienesCuenta = new JLabel("You don't have an account?");
		lblNoTienesCuenta.setHorizontalAlignment(SwingConstants.CENTER);
		lblNoTienesCuenta.setBounds(241, 142, 165, 29);
		getContentPane().add(lblNoTienesCuenta);
		
		lblLoginSucessful = new JLabel("");
		lblLoginSucessful.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblLoginSucessful.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoginSucessful.setBounds(53, 218, 333, 32);
		getContentPane().add(lblLoginSucessful);
		
		txtpw_userpassword = new JPasswordField();
		txtpw_userpassword.setBounds(212, 95, 159, 20);
		getContentPane().add(txtpw_userpassword);
	}
}
