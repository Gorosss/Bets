package gui;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class RegisterGUI extends JFrame {
	
	private JTextField userField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JTextField correoElectronicField;
	private JTextField tarjetaBancariaField;
	private JTextField NombreField;
	private JTextField apellidoField;

	private void customCloseWindow() {
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	public RegisterGUI() {
		
		getContentPane().setLayout(null);
		this.setSize(new Dimension(489, 408));
		setTitle("Register");
		
		userField = new JTextField();
		userField.setBounds(155, 91, 156, 20);
		getContentPane().add(userField);
		userField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(155, 135, 156, 20);
		getContentPane().add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(155, 166, 156, 23);
		getContentPane().add(passwordField_1);

		JLabel lblRegisteredSuccessfully = new JLabel("Registered Successfully / Error");
		lblRegisteredSuccessfully.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegisteredSuccessfully.setBounds(66, 282, 185, 32);
		getContentPane().add(lblRegisteredSuccessfully);
		lblRegisteredSuccessfully.setVisible(false);
		
		JButton jButtonClose = new JButton("Close");
        jButtonClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                customCloseWindow();
            }
        });
        jButtonClose.setBounds(new Rectangle(274, 419, 130, 30));
        jButtonClose.setBounds(28, 326, 113, 32);
        getContentPane().add(jButtonClose);
        
        correoElectronicField = new JTextField();
		correoElectronicField.setBounds(154, 204, 254, 20);
		getContentPane().add(correoElectronicField);
		correoElectronicField.setColumns(10);
		
		tarjetaBancariaField = new JTextField();
		tarjetaBancariaField.setBounds(154, 235, 254, 20);
		getContentPane().add(tarjetaBancariaField);
		tarjetaBancariaField.setColumns(10);
		
		NombreField = new JTextField();
		NombreField.setBounds(66, 44, 105, 20);
		getContentPane().add(NombreField);
		NombreField.setColumns(10);
		
		apellidoField = new JTextField();
		apellidoField.setBounds(253, 44, 177, 20);
		getContentPane().add(apellidoField);
		apellidoField.setColumns(10);
		
		
		JButton btnRegistrarse = new JButton("Register");
		btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//try {
					String name=NombreField.getText();
					String apellido=apellidoField.getText();
					String card=tarjetaBancariaField.getText();
					
					String correw=correoElectronicField.getText();
					
                    String nombreUsuario=userField.getText();
                    String password1= new String(passwordField.getPassword());
                    String password2= new String(passwordField_1.getPassword());
                    
                    
                    boolean error = false;
                    
                    if (nombreUsuario.length() < 1) {
                        System.out.println("Error. No username was entered. Code 1.");
                        error = true;
                    } else if (password1.length() < 1){
                        System.out.println("Error. No password was entered. Code 2.");
                        error = true;
                    } else if(!password1.equals(password2)) {
                        System.out.println("Error. Diferent password was entered. Code 3.");
                        error = true;
                    }else if (name.length() < 1){
                        System.out.println("Error. No name was entered. Code 2.");
                        error = true;
                    }else if (apellido.length() < 1){
                        System.out.println("Error. No surename was entered. Code 2.");
                        error = true;
                    }else if (card.length()!=16){
                        System.out.println("Error. No bank card was entered. Code 2.");
                        error = true;
                    }else if (correw.length() < 1){
                        System.out.println("Error. No correo was entered. Code 2.");
                        error = true;
                    }
                    
                    if (!error) {
                    	error = LoginRegisterGUI.getBusinessLogic().addUser(nombreUsuario, password1,correw,card,name,apellido);
                    	if (error) 
                    		lblRegisteredSuccessfully.setText("Successful registration.");
                    	else
                    		lblRegisteredSuccessfully.setText("Error. Try again.");		
                    } else {
                		lblRegisteredSuccessfully.setText("Error. Try again.");		
                    }
                    
                    lblRegisteredSuccessfully.setVisible(true);
                    System.out.println("\n\n");
				//} catch (Exception e2) {
                //    System.out.println("Error. Illegal Format.");
                //}
			}
		});
		btnRegistrarse.setBounds(329, 282, 100, 32);
		getContentPane().add(btnRegistrarse);
		
		JLabel lblNewLabel = new JLabel("Repeat Password");
		lblNewLabel.setBounds(28, 162, 143, 31);
		getContentPane().add(lblNewLabel);
		
		
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(28, 128, 86, 23);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNobreDeUsuario = new JLabel("Username");
		lblNobreDeUsuario.setBounds(28, 86, 113, 31);
		getContentPane().add(lblNobreDeUsuario);
		
		JLabel lblCorreoElectronico = new JLabel("Email");
		lblCorreoElectronico.setBounds(28, 207, 100, 14);
		getContentPane().add(lblCorreoElectronico);
		
		JLabel lblNumeroDeLa = new JLabel("Bank Card Number");
		lblNumeroDeLa.setBounds(28, 238, 161, 14);
		getContentPane().add(lblNumeroDeLa);
		
		JLabel lblNombre = new JLabel("Name");
		lblNombre.setBounds(10, 47, 46, 14);
		getContentPane().add(lblNombre);
		
		JLabel lblApellido = new JLabel("Surname");
		lblApellido.setBounds(197, 35, 46, 38);
		getContentPane().add(lblApellido);
		
		
		
		
	}
}
