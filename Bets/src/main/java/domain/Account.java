package domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity 
public class Account {

	@Id
	private String userName;
	private String userRealName;
	private String userSurname;
	private String userPassword;
	private ArrayList<Forecast> userForecasts;
	private String userEmail;
	private String bankCardNumber;
	private double userWallet;
	private boolean isAdmin;


	public Account(String pUserName, String pUserPassword, boolean pIsAdmin, String pUserEmail, String pBankCardNumber, String pUserRealName, String pUserSurname) {
		super();
		this.userName = pUserName;
		this.userPassword = pUserPassword;
		this.userWallet = 0;
		this.isAdmin = pIsAdmin;
		this.userForecasts = new ArrayList<Forecast>();
		this.userEmail = pUserEmail;
		this.bankCardNumber = pBankCardNumber;
		this.userRealName = pUserRealName;
		this.userSurname = pUserSurname;
		this.userWallet=10;
	}

	public String getSurname() {
		return userSurname;
	}
	
	public String getRealName() {
		return userRealName;
	}
	
	public String getUserBankCard() {
		return bankCardNumber;
	}
	
	public String getUserEmail() {
		return userEmail;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getPassword() {
		return userPassword;
	}
	
	public double getWallet() {
		return userWallet;
	}
	
	public void paid(double money) {
		userWallet=+money;
	}
	
	public void rest(double money) {
		userWallet=userWallet-money;
	}
	
	
	public boolean getAdmin() {
		return isAdmin;
	}
	
	public boolean haveEnoughWallet(double pDelta) {
		return (userWallet + pDelta) > 0;
	}
	
	public boolean changeWallet(double pDelta) {
		if(userWallet + pDelta < 0) {
			return false;
		}
		this.userWallet += pDelta;
		return true;
	}
	
	public void deletIndexForecast(Forecast f) {
		userForecasts.remove(userForecasts.indexOf(f));
	}
	
	public void addForecast(Forecast pForecast) {
		
		//	ESTAS COMPROBACIONES LAS HE PUESTO AQUI PERO HAY QUE HACERLAS EN businessLogic
		//	HAY QUE DESPLAZARLAS A ALGUNA CLASE DE businessLogic, Y QUE ESA CLASE ACCEDA A 
		//	LOS DATOS DE LA CUENTA DE TURNO PARA VER SI SE PUEDE HACER O NO EL PRONOSTICO
		//	SI DESDE LA CLASE DE businessLogic SE DECIDE QUE SI QUE SE PUEDE HACER EL PRONOSTICO, 
		//	SE ***ACTUALIZA*** LA CUENTA DE TURNO ***EN LA BASE DE DATOS DIRECTAMENTE*** AÑADIENDOLE
		//  EL PRONOSTICO LEGAL MEDIANTE ESTE METODO
		
		userForecasts.add(pForecast);
		
		
	}
	
	public List<Forecast> getAllUserForecast () {
		return userForecasts;
	}
}
