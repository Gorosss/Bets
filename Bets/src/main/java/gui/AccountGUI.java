package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;

public class AccountGUI extends JFrame {
	public AccountGUI() {
		getContentPane().setLayout(null);
		setSize(392, 275);
		
		JLabel lblUserInformation = new JLabel("Account Information");
		lblUserInformation.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		lblUserInformation.setBounds(21, 21, 180, 29);
		getContentPane().add(lblUserInformation);
		
		JLabel lblNombreApellido = new JLabel("NOMBRE, APELLIDO");
		lblNombreApellido.setBounds(21, 61, 314, 23);
		getContentPane().add(lblNombreApellido);
		lblNombreApellido.setText(LoginRegisterGUI.getBusinessLogic().getAccountInfo("name") + " " + LoginRegisterGUI.getBusinessLogic().getAccountInfo("surname"));
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(21, 90, 79, 23);
		getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password: ");
		lblPassword.setBounds(21, 111, 79, 23);
		getContentPane().add(lblPassword);
		
		JLabel lblBankAccount = new JLabel("Bank Account:");
		lblBankAccount.setBounds(21, 145, 90, 23);
		getContentPane().add(lblBankAccount);
		
		JLabel lblWallet = new JLabel("Wallet:");
		lblWallet.setBounds(21, 168, 46, 14);
		getContentPane().add(lblWallet);
		
		JLabel lblEmail = new JLabel("EMAIL");
		lblEmail.setBounds(21, 200, 329, 23);
		getContentPane().add(lblEmail);
		lblEmail.setText(LoginRegisterGUI.getBusinessLogic().getAccountInfo("email"));
		
		JLabel lblUsername_1 = new JLabel("username");
		lblUsername_1.setBounds(90, 95, 260, 19);
		getContentPane().add(lblUsername_1);
		lblUsername_1.setText(LoginRegisterGUI.getBusinessLogic().getAccountInfo("username"));
		
		JLabel lblContrasena = new JLabel("contrasena");
		lblContrasena.setBounds(83, 115, 267, 14);
		getContentPane().add(lblContrasena);
		lblContrasena.setText(LoginRegisterGUI.getBusinessLogic().getAccountInfo("password"));
		
		JLabel lblDineros = new JLabel("dineros");
		lblDineros.setBounds(65, 168, 285, 14);
		getContentPane().add(lblDineros);
		lblDineros.setText(LoginRegisterGUI.getBusinessLogic().getAccountInfo("wallet"));
		
		JLabel lblDineroscuenta = new JLabel("dinerosCuenta");
		lblDineroscuenta.setBounds(98, 149, 252, 14);
		getContentPane().add(lblDineroscuenta);
		lblDineroscuenta.setText(LoginRegisterGUI.getBusinessLogic().getAccountInfo("card"));
		
		
	}
}
