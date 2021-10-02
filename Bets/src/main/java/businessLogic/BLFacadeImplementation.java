package businessLogic;
import java.util.ArrayList;
//hola
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.*;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {
	DataAccess dbManager;
	private static String loggedUser = null;				// Si es null, no se esta loggeado. Si es no nulo, este campo
															// contiene el username del usuario loggeado. 
	
	private String initialize="initialize";

	public BLFacadeImplementation()  {		
		System.err.println("Creating BLFacadeImplementation instance");
		ConfigXML c=ConfigXML.getInstance();
		
		if (c.getDataBaseOpenMode().equals(initialize)) {
		    dbManager=new DataAccess(c.getDataBaseOpenMode().equals(initialize));
			dbManager.initializeDB();
			dbManager.close();
			}
		
	}
	
    public BLFacadeImplementation(DataAccess da)  {
		
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c=ConfigXML.getInstance();
		
		if (c.getDataBaseOpenMode().equals(initialize)) {
			da.open(true);
			da.initializeDB();
			da.close();

		}
		dbManager=da;		
	}
	

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
   @WebMethod
   public Question createQuestion(Event event, String question, float betMinimum, int questionType,String pMultipliers) throws EventFinished, QuestionAlreadyExist{
	   
	    //The minimum bed must be greater than 0
		dbManager.open(false);
		Question qry=null;
		
	    
		if(new Date().compareTo(event.getEventDate())>0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));
				
		
		qry=dbManager.createQuestion(event,question,betMinimum, questionType, pMultipliers);		

		dbManager.close();
		
		return qry;
   };
	
	/**
	 * This method invokes the data access to retrieve the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
    @WebMethod	
	public Vector<Event> getEvents(Date date)  {
		dbManager.open(false);
		Vector<Event>  events=dbManager.getEvents(date);
		dbManager.close();
		return events;
	}

    
	/**
	 * This method invokes the data access to retrieve the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod public Vector<Date> getEventsMonth(Date date) {
		dbManager.open(false);
		Vector<Date>  dates=dbManager.getEventsMonth(date);
		dbManager.close();
		return dates;
	}
	
	
	public void close() {
		DataAccess dB4oManager=new DataAccess(false);

		dB4oManager.close();

	}

	/**
	 * This method invokes the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
    @WebMethod	
	 public void initializeBD(){
    	dbManager.open(false);
		dbManager.initializeDB();
		dbManager.close();
	}

    
    /**
     * Add user to de DB
     */
    @WebMethod
    public boolean addUser(String pUsername, String pUserPassword, String pUserEmail, String pBankCardNumber, String pUserRealName, String pUserSurname) {
    	dbManager.open(false);
    	Account a=new Account(pUsername, pUserPassword, false,pUserEmail,pBankCardNumber,pUserRealName,pUserSurname);
        if(dbManager.isUsernameAvailable(a)) {
            dbManager.storeAccount(new Account(pUsername, pUserPassword, false,pUserEmail,pBankCardNumber,pUserRealName,pUserSurname));
            dbManager.close();
            System.out.println("New Account created: " + pUsername);
            return true;
        }
        
        System.out.println("Account could not be created.");
        return false;
    }

    
    /**
     * @return True if the user logged successfully else not
     */
    @WebMethod
	public boolean tryToLogin(String pUsername, String pPassword) {
    	if (loggedUser == null) {
	    	dbManager.open(false);
	    	if(dbManager.tryToLogin(pUsername, pPassword)) {
	    		loggedUser = pUsername;
	    		dbManager.close();
	    		System.out.println("User " + pUsername + " has logged in successfully.");
	    		return true;
	    	} 
	    	System.out.println("Unsuccessful loggin by " + pUsername + " user.");
			dbManager.close();
	    	return false;
    	}
    	System.out.println("Someone is already logged in. To log in with another account reboot the application.");
    	return false;
	}
    
    
    /**
     * @return true if the logged user is admin
     */
    public boolean isAdmin() {
    	dbManager.open(false);
    	if (loggedUser != null && dbManager.isAdministrator(loggedUser)) {
    		dbManager.close();
    		return true;
    	}
    	dbManager.close();
    	return false;
    }
    
    
    /**
     * Add event to the DB
     */
    @WebMethod
    public void addEvent(Integer eventNumber, String description,Date eventDate) {
    	dbManager.open(false);
        Event event=new Event(eventNumber,description,eventDate);        
        dbManager.storeEvent(event);
        dbManager.close();
    }
    
    /**
     * @return the last event nuber that was added
     */
    @WebMethod
    public Integer lastEventNum() {
    	dbManager.open(false);
        ArrayList<Event> events=(ArrayList<Event>) dbManager.LastEventNumber();
        Event lastEvent=events.get(events.size()-1);
        dbManager.close();
        return lastEvent.getEventNumber();
    }
    
    
    /**
     * @return Question of the number gived
     */
    @WebMethod
    public Question getQuestionFromNumber(Integer numero) {
    	dbManager.open(false);
    	Question pregunta = dbManager.getQuestionFromNumber(numero);
    	dbManager.close();
    	return pregunta;
    }
    
    /**
     * Return the multiplers of a question from a number of the question
     */
    @WebMethod
    public ArrayList<Float> getQuestionMultiplerFromNumber(Integer numero) {
    	dbManager.open(false);
    	ArrayList<Float> multiplers = dbManager.getQuestionMultiplersFromNumber(numero);
    	dbManager.close();
    	return multiplers;
    }
    
    
    /**
     * Set the result of the Question
     */
    @WebMethod
    public void setQuestionResult(Integer numero, String result) {
    	dbManager.open(false);
    	dbManager.setQuestionResult(numero, result);
    	dbManager.close();

    }
    
    
    /**
     * Add a Forecast to the user forecast list, user forecasts
     */
    @WebMethod
    public void addUsersForecast(Forecast pForecast) {
    	dbManager.open(false);
    
    	Account cuenta = dbManager.getUser(loggedUser);
    	cuenta.addForecast(pForecast);
    }
    
    /**
     * Method to logout
     */
    @WebMethod
    public void logout() {
    	loggedUser = null;
    	customClose();
    }
    
    
    /**
     * Method to close a event
     */
    @WebMethod
    public void closeEvent(Event e) {
    	dbManager.open(false);
    	dbManager.closeEvent(e);
    	dbManager.close();

    }
    
    
    private void customClose() {
		dbManager.close();
		System.out.println("Database closed custom.");
	}
    
    /**
     * Return the logged user username
     */
    @WebMethod
    public String getLoggedUsername() {
    	return loggedUser;
    }
    
    /**
     * Add forecast to the DB
     */
    @WebMethod
    public void addForecast(Question pQuestion, double pBet, String pGuess, double win) {
    	dbManager.open(false);
    	Forecast f=new Forecast(pQuestion, pBet, pGuess,win);
    	Question q=dbManager.getQuestionFromNumber(pQuestion.getQuestionNumber());
    	f.setQuestion(q);
    	f.linkMultiplierToGuess();
    	addUsersForecast(f);
    	dbManager.storeForecast(f);
    	dbManager.close();
    }
    
    
    /**
     * Return forecasts of the user logged
     */
    @WebMethod
    public ArrayList<Forecast> getForecasts(Question pQuestion) {
    	ArrayList<Forecast> objs = null;
    	if (pQuestion != null) {
    		dbManager.open(false);
    		objs = dbManager.retrieveForecast(pQuestion, loggedUser);
    		dbManager.close();
    	}
    	return objs;
    }
    
    
    /**
     * Return true if the Question haver a result
     */
    @WebMethod
    public boolean questionHaveResult(Vector<Question> queries) {
    	for(Question q:queries) {
    		if(q.getResult()==null) {
    			return false;
    		}
    	}
    	
    	return true;
    }
    
    
    /**
     * Method to paid the user when they win a guess, checking all questions and forecast
     * 
     * @param queries of question that you want to check
     * @throws NullPointerException if the queries or question is null 
     */
    @WebMethod
    public void paidUsers(Vector<Question> queries) throws NullPointerException,RuntimeException {

    	dbManager.open(false);

    	if(queries==null) {
    		throw new NullPointerException("queries is null");
    	}

    	for(Question q:queries) {
    		if(q==null || q.getEvent()==null) {
    			throw new NullPointerException("There is a question that is null or has no event");
    		}
    	}

    	for(Question q:queries) {
    		ArrayList<Forecast> forecasts=(ArrayList<Forecast>) dbManager.getForecastsOfQuestion(q);
    		for(Forecast f:forecasts) {
    			if(q.getResult().equals(f.getMyGuess())) {
    				dbManager.paid(f.getUser(),f.getWin());
    			}
    		}

    	}
    	dbManager.close();
    }

    /**
     * Method to paid user when the cancel a forecast
     */
    @WebMethod
    public void paidUsers(String user,double money) {
    	
    	dbManager.open(false);
    	dbManager.paid(user,money);
    	dbManager.close();

    }
    
    
    /**
     * Method that return the user wallet
     * 
     */
    @WebMethod
    public double getUserWallet(String username) {
    	dbManager.open(false);
    	Account cuenta = dbManager.getUser(username);
    	dbManager.close();
    	return cuenta.getWallet();
    }
    
    /**
	 * Method to get user forecasts
     */
    @WebMethod
    public List<Forecast> getUserForecasts(String username) {
    	dbManager.open(false);
    	List<Forecast> forecasts = dbManager.getuserForecasts(username);
    	dbManager.close();
    	return forecasts;
    }
    /**
	 * Method to get user forecasts
     */
    @WebMethod
    public void restUserMoney(String username, Double money) {
    	dbManager.open(false);
    	dbManager.restMoneyToUser(username,money);
    	dbManager.close();
    }
    
    /**
	 * Method to delet a forecast
     */
    @WebMethod
    public void deleteForecast(Forecast forecast) {
    	dbManager.open(false);
    	dbManager.deleteForecast(forecast,loggedUser);
    	dbManager.close();
    }
    
    
    /**
	 * Method to get the actual user information
     */
	@WebMethod
    public String getAccountInfo(String info) {
    	dbManager.open(false);
    	Account cuenta = dbManager.getUser(loggedUser);
    	String s = null;
    	switch (info.toLowerCase()) {
		case "username":
			s = loggedUser;
			break;
		case "password":
			s = cuenta.getPassword();
			break;
		case "name":
			s = cuenta.getRealName();
			break;
		case "surname":
			s = cuenta.getSurname();
			break;
		case "card":
			s = cuenta.getUserBankCard();
			break;
		case "email":
			s = cuenta.getUserEmail();
			break;
		case "wallet":
			s = Double.toString(cuenta.getWallet());
			break;
		default:
			System.out.println("Error. La informacion deseada no esta contemplada. Codigo 12");
			break;
		} 
    	dbManager.close();
    	return s;
    }
    
}

