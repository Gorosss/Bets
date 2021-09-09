package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JLabel;

import businessLogic.BLFacade;

public class LoginRegisterGUI extends JFrame {

	private static JFrame frmBetsruins;
	private static BLFacade appFacadeInterface;
	private static JFrame frmLoginGUI;
	
	/**
	 * Launch the application.
	 */
	public static BLFacade getBusinessLogic() {
		return appFacadeInterface;
	}
	
	public static void setBussinessLogic (BLFacade afi) {
		appFacadeInterface=afi;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginRegisterGUI window = new LoginRegisterGUI();
					window.frmBetsruins.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void setVisibility(boolean pVisible) {
		frmBetsruins.setVisible(pVisible);
	}
	
	public static void setLoginVisibility(boolean pVisible) {
		frmLoginGUI.setVisible(pVisible);
	}

	public static void endAndCloseProgram() {
		getBusinessLogic().logout();
		frmBetsruins.dispatchEvent(new WindowEvent(frmBetsruins, WindowEvent.WINDOW_CLOSING));
	}
	
	/**
	 * Create the application.
	 */
	public LoginRegisterGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBetsruins = new JFrame();
		frmBetsruins.setVisible(true);
		frmBetsruins.setTitle("Bets&Ruins");
		frmBetsruins.setBounds(100, 100, 450, 300);
		frmBetsruins.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBetsruins.getContentPane().setLayout(null);
		
		JButton registerBtn = new JButton("REGISTER");
		registerBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		registerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame a = new RegisterGUI();
				a.setVisible(true);
			}
		});
		registerBtn.setBounds(223, 59, 197, 73);
		frmBetsruins.getContentPane().add(registerBtn);
		
		JButton loginBtn = new JButton("LOGIN");
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmLoginGUI = new LoginGUI();
				frmLoginGUI.setVisible(true);
			}
		});
		loginBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		loginBtn.setBounds(223, 145, 197, 73);
		frmBetsruins.getContentPane().add(loginBtn);
		
		JLabel welcomeLbl = new JLabel("Welcome to Bets&Ruins");
		welcomeLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		welcomeLbl.setBounds(141, 13, 167, 33);
		frmBetsruins.getContentPane().add(welcomeLbl);
		
		JLabel lblYouDontOwn = new JLabel("You don't own an account?");
		lblYouDontOwn.setBounds(12, 80, 167, 33);
		frmBetsruins.getContentPane().add(lblYouDontOwn);
		
		JLabel lblYouOwnAn = new JLabel("Do you already have an account?");
		lblYouOwnAn.setBounds(12, 174, 181, 16);
		frmBetsruins.getContentPane().add(lblYouOwnAn);
		
		JButton btnNewButton = new JButton("GUEST");
		btnNewButton.setFont(new Font("Tahoma", Font.ITALIC, 11));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame a = new FindQuestionsGUI();
				a.setVisible(true);
			}
		});
		btnNewButton.setBounds(223, 227, 197, 23);
		frmBetsruins.getContentPane().add(btnNewButton);
		
		JLabel lblIfYouJust = new JLabel("If you just want to see questions:");
		lblIfYouJust.setBounds(12, 231, 201, 14);
		frmBetsruins.getContentPane().add(lblIfYouJust);
	}
}
