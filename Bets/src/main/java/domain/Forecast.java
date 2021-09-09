package domain;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import gui.LoginRegisterGUI;

@Entity
public class Forecast {
	
	private static int numberOfForecasts = 0;
	
	@Id
	private int forecastNumber;
	private Question question;		// Pregunta a la que el pronostico esta relacionado
	private double myBet;			// Lo que yo he apostado
	private double win;
	private String user;			// Usuario que ha hecho el forecast
	private String myGuess;			// Lo que yo creo q va a pasar . Para determinar si el pronostico 
									// ha sido acertado, comparar con el attributo result de la pregunta
									// asociada. Ambos campos (result y este mismo) son strings para 
									// almacenar "1", "X" ó "2".
	private float multiplier;
	
	public Forecast(Question pQuestion, double pBet, String pGuess, double win) {
		this.forecastNumber = Forecast.numberOfForecasts++;
		this.question = pQuestion;
		this.myBet = pBet;
		this.myGuess = pGuess;
		this.user = LoginRegisterGUI.getBusinessLogic().getLoggedUsername();
		this.win=win;
	}
	
	

	public int getForecastNumber() {
		return forecastNumber;
	}
	
	public double getMyBet() {
		return myBet;
	}
		
	public Question getQuestion() {
		return question;
	}
	
	public void setQuestion(Question q) {
		this.question=q;
	}
	
	
	
	public String getMyGuess() {
		return myGuess;
	}
	
	public void setMyGuess (String pMyGuess) {
		this.myGuess = pMyGuess;
	}
	
	public String getUser() {
		return user;
	}
	
	public double getWin() {
		return this.win;
	}
	
	public boolean won() {
		return myGuess.equals(question.getResult());
	}

	public void linkMultiplierToGuess(){
		ArrayList<Float> qMultipliers = this.question.getMultipliers();
		
		System.out.println(this.question.toString());
		
		System.out.println(qMultipliers.get(0));
		switch(question.getQuestionType()) {
			case 1:
			case 2:
				switch(this.myGuess){
					case "1":
						this.multiplier = qMultipliers.get(0);
						break;
					case "x":
						this.multiplier = qMultipliers.get(1);
						break;
					case "2":
						this.multiplier = qMultipliers.get(2);
						break;
					default:
						System.out.println("ERROR. Este mensaje no se tiene que imprimir. Codigo 10");	
						break;
				}
				break;
			case 3: 
			case 4:
				this.multiplier=qMultipliers.get(0);
				break;
			default: 
				System.out.println("ERROR. Este mensaje no se tiene que imprimir. Codigo 11"); 
				break;
		}
	}
	
	@Override
	public String toString() {
		return question.getEvent()+"-"+question.getQuestion() + ": Your guess: " + myGuess + "; Your bet: " + myBet;
	}
}
