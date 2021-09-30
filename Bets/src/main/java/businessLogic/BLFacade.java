package businessLogic;

import java.util.Vector;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import domain.Question;
import domain.Event;
import domain.Forecast;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade  {
	
	/**
	 * Tries to add a user to the database
	 * 
	 * @param pUsername
	 * @param pUserPassword
	 * @param pUserEmail
	 * @param pBankCardNumber
	 * @param pUserRealName
	 * @param pUserSurname
	 * @return
	 */
	@WebMethod public boolean addUser(String pUsername, String pUserPassword, String pUserEmail, String pBankCardNumber, String pUserRealName, String pUserSurname);
	
	/**
	 * Has the login being successful?
	 * 
	 * @param pUsername username of the user that is trying to login
	 * @param pPassword password of account of the user that is trying to login
	 * @return Has the login being successful?
	 */
	@WebMethod public boolean tryToLogin(String pUsername, String pPassword);
	
	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished if current data is after data of the event
 	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
	@WebMethod Question createQuestion(Event event, String question, float betMinimum, int questionType,String pMultipliers) throws EventFinished, QuestionAlreadyExist;
	
	
	/**
	 * This method retrieves the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod public Vector<Event> getEvents(Date date);
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod public Vector<Date> getEventsMonth(Date date);
	
	/**
	 * This method calls the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod public void initializeBD();

	/**
	 * This method is used for knowing wheter if the current user is an admin or not.
	 * 
	 * @return is the currently logged user an Administrator?
	 */
	@WebMethod public boolean isAdmin();
	
	/**
	 * This method is used for loggin out of an account
	 */
	@WebMethod public void logout();
	
	/**
	 * This method returns the username of the currently logged user
	 * 
	 * @return username of the logged user
	 */
	@WebMethod public String getLoggedUsername();
	
	/**
	 * This method adds a Forecast to the database
	 * 
	 * @param pQuestion The question the bet is made on
	 * @param myBet The money the user wants to bet
	 * @param myGuess Answer of the user
	 */
	@WebMethod public void addForecast(Question pQuestion, double myBet, String myGuess,double win);
	
	/**
	 * This method gets user's Forecast of a certain Question
	 * 
	 * @param pQuestion The question we want to know the Forecast about
	 * @return The Forecast of the user about the specified question
	 */
	@WebMethod public ArrayList<Forecast> getForecasts(Question pQuestion);
	
	/**
	 * MEthod to add a event
	 * @param eventNumber
	 * @param description
	 * @param eventDate
	 */
	@WebMethod public void addEvent(Integer eventNumber, String description,Date eventDate);
	
	/**
	 * MEthod that retun the number of the last Question that was added to the DB
	 * @return
	 */
	@WebMethod public Integer lastEventNum();
	
	/**
	 * Method to a Question from the number of a Question
	 * @param numero
	 * @return
	 */
	@WebMethod public Question getQuestionFromNumber(Integer numero);
	
	/**
	 * Method to add a forecast to a user
	 * @param pForecast
	 */
	@WebMethod public void addUsersForecast(Forecast pForecast);
	
	/**
	 * Method that return the multplier of a Question
	 * @param numero
	 * @return
	 */
	@WebMethod public ArrayList<Float> getQuestionMultiplerFromNumber(Integer numero); 
	 
	/**
	 * Method to ser a result to a Question
	 * @param numero
	 * @param result
	 */
	@WebMethod public void setQuestionResult(Integer numero, String result);
	 
	/**
	 * Method to close a event
	 * @param e
	 */
	@WebMethod public void closeEvent(Event e);
	 
	/**
	 * Metho to know if a Question have set a result
	 * @param queries
	 * @return
	 */
	@WebMethod public boolean questionHaveResult(Vector<Question> queries);
	
	/**
	 * Method to paid a user
	 * @param queries
	 */
	@WebMethod public void paidUsers(Vector<Question> queries)throws NullPointerException,RuntimeException;
	
	/**
	 * Method to get the actual user information
	 * @param info
	 * @return
	 */
	@WebMethod public String getAccountInfo(String info);
	
	/**
	 * Method that return the user wallet
	 * @param username
	 * @return
	 */
	@WebMethod public double getUserWallet(String username);
	
	/**
	 * Method to rest money from a user wallet
	 * @param username
	 * @param money
	 */
	@WebMethod public void restUserMoney(String username, Double money) ;
	
	/**
	 * Method to get user forecasts
	 * @param username
	 * @return
	 */
	@WebMethod public List<Forecast> getUserForecasts(String username);
	 
	/**
	 * Method to paid a user
	 * @param user
	 * @param money
	 */
	@WebMethod public void paidUsers(String user,double money);
	
	/**
	 * Method to delet a forecast
	 * @param forecast
	 */
	@WebMethod public void deleteForecast(Forecast forecast);
}
