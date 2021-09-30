package domain;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Question implements Serializable {
	
	private static int numberOfQuestions = 0;
	
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer questionNumber;
	private String question; 
	private float betMinimum;
	private String result;
	@XmlIDREF
	private Event event;
	private int questionType;				//1 --> Con opciones 1, x, 2
											//2 --> Con opciones SI y NO
											//3 --> Con opcion de marcar numeros
											//4 --> Con opcion a escribir
	
	
	private ArrayList<Float> multipliers=new ArrayList<Float>();

	public Question(){
		super();
	}
	
	public Question(String query, float betMinimum, Event event, int questionType,String pMultipliers) {
		super();
		this.questionNumber = Question.numberOfQuestions++;
		this.question = query;
		this.betMinimum=betMinimum;
		this.event = event;
		this.questionType = questionType;
		addMultipliers(pMultipliers);
		
	}

	public Question(Integer qNum,String query, float betMinimum, Event event, int questionType,String pMultipliers) {
		super();
		this.questionNumber = qNum;
		this.question = query;
		this.betMinimum=betMinimum;
		this.event = event;
		this.questionType = questionType;
		addMultipliers(pMultipliers);
		
	}

	/**
	*	Adds multipliers to the multipliers ArrayList
	*/
	public void addMultipliers(String pMultipliers){
			String[] mults = pMultipliers.split(",");		
			for(String s : mults) {
				multipliers.add(Float.parseFloat(s));
			}	

	}

	/**
	*	Get the question type 
	*
	* @return Type of the question
	*/
	public int getQuestionType() {
		return questionType;
	}

	/**
	* Get multipliers array
	*	
	* @return Winning multipliers
	*/
	public ArrayList<Float> getMultipliers() {
		return multipliers;
	}



	
	/**
	 * Get the  number of the question
	 * 
	 * @return the question number
	 */
	public Integer getQuestionNumber() {
		return questionNumber;
	}

	/**
	 * Set the bet number to a question
	 * 
	 * @param questionNumber to be setted
	 */
	public void setQuestionNumber(Integer questionNumber) {
		this.questionNumber = questionNumber;
	}


	/**
	 * Get the question description of the bet
	 * 
	 * @return the bet question
	 */

	public String getQuestion() {
		return question;
	}
	
	/**
	 * Set the question description of the bet
	 * 
	 * @param question to be setted
	 */	
	public void setQuestion(String question) {
		this.question = question;
	}



	/**
	 * Get the minimun ammount of the bet
	 * 
	 * @return the minimum bet ammount
	 */
	
	public float getBetMinimum() {
		return betMinimum;
	}


	/**
	 * Get the minimun ammount of the bet
	 * 
	 * @param  betMinimum minimum bet ammount to be setted
	 */

	public void setBetMinimum(float betMinimum) {
		this.betMinimum = betMinimum;
	}



	/**
	 * Get the result of the  query
	 * 
	 * @return the the query result
	 */
	public String getResult() {
		return result;
	}



	/**
	 * Get the result of the  query
	 * 
	 * @param result of the query to be setted
	 */
	
	public void setResult(String result) {
		this.result = result;
	}



	/**
	 * Get the event associated to the bet
	 * 
	 * @return the associated event
	 */
	public Event getEvent() {
		return event;
	}



	/**
	 * Set the event associated to the bet
	 * 
	 * @param event to associate to the bet
	 */
	public void setEvent(Event event) {
		this.event = event;
	}

	
	@Override
	public String toString(){
		return questionNumber+";"+question+";"+Float.toString(betMinimum);
	}
	
}