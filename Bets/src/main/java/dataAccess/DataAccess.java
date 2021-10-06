package dataAccess;

import java.util.ArrayList;
//hello
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;



//import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
//import javax.persistence.PersistenceException;
//import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

//import com.sun.xml.bind.v2.model.core.Element;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Account;
import domain.Event;
import domain.Forecast;
import domain.Question;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess  {
	protected static EntityManager  db;
	protected static EntityManagerFactory emf;


	ConfigXML c=ConfigXML.getInstance();

	public DataAccess(boolean initializeMode)  {

		System.out.println("Creating DataAccess instance => isDatabaseLocal: "+c.isDatabaseLocal()+" getDatabBaseOpenMode: "+c.getDataBaseOpenMode());

		open(initializeMode);

	}

	public DataAccess()  {	
		this(false);
	}

	/**
	 * This is the data access method that initializes the database with some events and questions.
	 * This method is invoked by the business logic (constructor of BLFacadeImplementation) when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	public void initializeDB(){

		db.getTransaction().begin();
		try {


//			Calendar today = Calendar.getInstance();
//
//			int month=today.get(Calendar.MONTH);
//			month+=1;
//			int year=today.get(Calendar.YEAR);
//			if (month==12) { month=0; year+=1;}  
//
//			Event ev1=new Event(1, "Atlético-Athleti", UtilDate.newDate(year,month,17));
//			Event ev2=new Event(2, "Eibar-Barcelona", UtilDate.newDate(year,month,17));
//			Event ev3=new Event(3, "Getafe-Celta", UtilDate.newDate(year,month,17));
//			Event ev4=new Event(4, "Alavés-Deportivo", UtilDate.newDate(year,month,17));
//			Event ev5=new Event(5, "Español-Villareal", UtilDate.newDate(year,month,17));
//			Event ev6=new Event(6, "Las Palmas-Sevilla", UtilDate.newDate(year,month,17));
//			Event ev7=new Event(7, "Malaga-Valencia", UtilDate.newDate(year,month,17));
//			Event ev8=new Event(8, "Girona-Leganés", UtilDate.newDate(year,month,17));
//			Event ev9=new Event(9, "Real Sociedad-Levante", UtilDate.newDate(year,month,17));
//			Event ev10=new Event(10, "Betis-Real Madrid", UtilDate.newDate(year,month,17));
//
//			Event ev11=new Event(11, "Atletico-Athletic", UtilDate.newDate(year,month,1));
//			Event ev12=new Event(12, "Eibar-Barcelona", UtilDate.newDate(year,month,1));
//			Event ev13=new Event(13, "Getafe-Celta", UtilDate.newDate(year,month,1));
//			Event ev14=new Event(14, "Alavés-Deportivo", UtilDate.newDate(year,month,1));
//			Event ev15=new Event(15, "Español-Villareal", UtilDate.newDate(year,month,1));
//			Event ev16=new Event(16, "Las Palmas-Sevilla", UtilDate.newDate(year,month,1));
//
//
//			Event ev17=new Event(17, "Málaga-Valencia", UtilDate.newDate(year,month+1,28));
//			Event ev18=new Event(18, "Girona-Leganés", UtilDate.newDate(year,month+1,28));
//			Event ev19=new Event(19, "Real Sociedad-Levante", UtilDate.newDate(year,month+1,28));
//			Event ev20=new Event(20, "Betis-Real Madrid", UtilDate.newDate(year,month+1,28));
//			
//
//			Question q1;
//			Question q2;
//			Question q3;
//			Question q4;
//			Question q5;
//			Question q6;
//
//		if (Locale.getDefault().equals(new Locale("es"))) {
//				q1=ev1.addQuestion("¿Quién ganará el partido?",1,1,"1.0,2.0,3.0");
//				q2=ev1.addQuestion("¿Quién meterá el primer gol?",2,2,"1.0,2.0,3.0");
//				q3=ev11.addQuestion("¿Quién ganará el partido?",1,1,"1.0,2.0,3.0");
//				q4=ev11.addQuestion("¿Cuántos goles se marcarán?",2,3,"1.0,2.0,3.0");
//				q5=ev17.addQuestion("¿Quién ganará el partido?",1,1,"1.0,2.0,3.0");
//				q6=ev17.addQuestion("¿Habrá goles en la primera parte?",2,2,"1.0,2.0,3.0");
//			}
//		else if (Locale.getDefault().equals(new Locale("en"))) {
//				q1=ev1.addQuestion("Who will win the match?",1, 1,"1.0,2.0,3.0");
//				q2=ev1.addQuestion("Who will score first?",2, 2,"1.0,2.0,3.0");
//				q3=ev11.addQuestion("Who will win the match?",1, 1,"1.0,2.0,3.0");
//				q4=ev11.addQuestion("How many goals will be scored in the match?",2, 3,"1.0,2.0,3.0");
//				q5=ev17.addQuestion("Who will win the match?",1, 1,"1.0,2.0,3.0");
//				q6=ev17.addQuestion("Will there be goals in the first half?",2, 2,"1.0,2.0,3.0");
//				
//			}			
//			else {
//				q1=ev1.addQuestion("Zeinek irabaziko du partidua?",1,1,"1.0,2.0,3.0");
//				q2=ev1.addQuestion("Zeinek sartuko du lehenengo gola?",2,2,"1.0,2.0,3.0");
//				q3=ev11.addQuestion("Zeinek irabaziko du partidua?",1,1,"1.0,2.0,3.0");
//				q4=ev11.addQuestion("Zenbat gol sartuko dira?",2,3,"1.0,2.0,3.0");
//				q5=ev17.addQuestion("Zeinek irabaziko du partidua?",1,1,"1.0,2.0,3.0");
//				q6=ev17.addQuestion("Golak sartuko dira lehenengo zatian?",2,2,"1.0,2.0,3.0");
//
//			}
//
//			db.persist(new Account("admin", "admin", true,"admin",null,null,null));
//			db.persist(new Account("user", "user", false,"user",null,null,null));
//			
//			db.persist(q1);
//			db.persist(q2);
//			db.persist(q3);
//			db.persist(q4);
//			db.persist(q5);
//			db.persist(q6);
//
//
//			db.persist(ev1);
//			db.persist(ev2);
//			db.persist(ev3);
//			db.persist(ev4);
//			db.persist(ev5);
//			db.persist(ev6);
//			db.persist(ev7);
//			db.persist(ev8);
//			db.persist(ev9);
//			db.persist(ev10);
//			db.persist(ev11);
//			db.persist(ev12);
//			db.persist(ev13);
//			db.persist(ev14);
//			db.persist(ev15);
//			db.persist(ev16);
//			db.persist(ev17);
//			db.persist(ev18);
//			db.persist(ev19);
//			db.persist(ev20);
//			

			db.getTransaction().commit();
			System.out.println("Db initialized");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
	public Question createQuestion(Event event, String question, float betMinimum, int questionType,String pMultipliers) throws  QuestionAlreadyExist {
		System.out.println(">> DataAccess: createQuestion=> event= "+event+" question= "+question+" betMinimum="+betMinimum);

		Event ev = db.find(Event.class, event.getEventNumber());
		if (ev.DoesQuestionExists(question)) throw new QuestionAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));

		
		db.getTransaction().begin();
		Question q = ev.addQuestion(question, betMinimum, questionType, pMultipliers);
		//db.persist(q);
		
		db.persist(ev); // db.persist(q) not required when CascadeType.PERSIST is added in questions property of Event class
		// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
		db.getTransaction().commit();
		return q;

	}

	/**
	 * This method retrieves from the database the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public Vector<Event> getEvents(Date date) {
		System.out.println(">> DataAccess: getEvents");
		Vector<Event> res = new Vector<Event>();	
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1",Event.class);   
		query.setParameter(1, date);
		List<Event> events = query.getResultList();
		for (Event ev:events){
			System.out.println(ev.toString());		 
			res.add(ev);
		}
		return res;
	}

	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	public Vector<Date> getEventsMonth(Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		Vector<Date> res = new Vector<Date>();	

		Date firstDayMonthDate= UtilDate.firstDayMonth(date);
		Date lastDayMonthDate= UtilDate.lastDayMonth(date);


		TypedQuery<Date> query = db.createQuery("SELECT DISTINCT ev.eventDate FROM Event ev WHERE ev.eventDate BETWEEN ?1 and ?2",Date.class);   
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		List<Date> dates = query.getResultList();
		for (Date d:dates){
			System.out.println(d.toString());		 
			res.add(d);
		}
		return res;
	}


	public void open(boolean initializeMode) {

		System.out.println("Opening DataAccess instance => isDatabaseLocal: "+c.isDatabaseLocal()+" getDatabBaseOpenMode: "+c.getDataBaseOpenMode());

		String fileName=c.getDbFilename();
		if (initializeMode) {
			fileName=fileName+";drop";
			System.out.println("Deleting the DataBase");
		}

		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			db = emf.createEntityManager();
		}

	}
	public boolean existQuestion(Event event, String question) {
		System.out.println(">> DataAccess: existQuestion=> event= "+event+" question= "+question);
		Event ev = db.find(Event.class, event.getEventNumber());
		return ev.DoesQuestionExists(question);

	}
	public void close(){
		if (db.isOpen())
			db.close();
		
		System.out.println("DataBase closed");
	}
	
	public void storeAccount(Account user) {
        db.getTransaction().begin();
        db.persist(user);
        db.getTransaction().commit();
	}
    
	public boolean isUsernameAvailable(Account acc) {

		Account cuenta = db.find(Account.class, acc.getUserName());

		
		if (cuenta != null) {
			System.out.println("*** " + acc.getUserName() + " *** username is already taken. Code 4.");
			return false;
		}else if(!isEmailAvailable(acc.getUserEmail())) {
			System.out.println("*** " + acc.getUserEmail() + " *** email is already taken. Code 4.");
			return false;
		}else {
			System.out.println("There are no account(s) with that username.");
			System.out.println("*** " + acc.getUserName() + " *** username is available.");
			return true;
			
		}
		
	}
	
	 public boolean isEmailAvailable(String pCorreo) {
		   ArrayList<Account>acc=(ArrayList<Account>) getAllUsers();
		   for(Account a:acc) {
			   if(a.getUserEmail().equals(pCorreo)) {
				   return false;
			   }
		   }
		   return true;
	 }
	
    public List<Account> getAllUsers() {
    	TypedQuery<Account> query = db.createQuery("SELECT a FROM Account a",Account.class);
        List<Account> accounts = query.getResultList();
        return accounts;
    }
	
	public boolean tryToLogin(String pUsername, String pPassword) { 
		Account obj = db.find(Account.class, pUsername); 
		if (obj == null) {
			System.out.println("Account doesn't exist.");
			return false;
		} else {
			if (obj.getPassword().equals(pPassword)) 
				return true;
		}
		System.out.println("Wrong Password.");
		return false;
	}
	
	public boolean isAdministrator(String pUsername) {
		Account obj = db.find(Account.class, pUsername); 
		if (obj == null) {
			return false;
		} else {
			if (obj.getAdmin()) {
				return true;
			}
		}
		return false;
	}
	
	public List<Event> LastEventNumber() {
        TypedQuery<Event> query = db.createQuery("SELECT e FROM Event e",Event.class);
        List<Event> events = query.getResultList();
        return events;
    }
	
	public void storeEvent(Event evento) {
		db.getTransaction().begin();
		db.persist(evento);
		db.getTransaction().commit();
	}
	
	public Question getQuestionFromNumber(Integer numero) {
		TypedQuery<Question> query = db.createQuery("SELECT p FROM Question p WHERE p.questionNumber= "+numero+"", Question.class);
		List<Question> questions = query.getResultList();
		if(questions.size()<1) {
			System.out.println("Error. Lista de preguntas nula");
			return null;
		}
		else {
			return questions.get(0);
		}
		
	}
	

		
	public List<Forecast> getuserForecasts(String user) {
		
		Account u =getUser(user);
		
		List<Forecast> forecasts = u.getAllUserForecast();
		
		if(forecasts.size()<1) {
			System.out.println("Error. Lista de preguntas nula");
			return null;
		}
		else {
			return forecasts;
			}			
				
	}
	
	
	public List<Forecast> getAllForecasts() {
		List<Forecast> forecasts=null;
		try {
			TypedQuery<Forecast> query = db.createQuery("SELECT f FROM Forecast f",Forecast.class);
	        forecasts = query.getResultList();
		} catch (Exception e) {
			//There is no forecast in DB
			return null;
		}
    	
        return forecasts;
    }
	
	
	
	/*
	 * This method returns a forecast list of the given question
	 * 
	 * @param question from which we are going to receive the forecast
	 * @return List of Forecasts
 	 * @throws NullPointerException if the question is null 
 	 * @throws RuntimeException if the question has no event
	 */
	public List<Forecast> getForecastsOfQuestion(Question question) throws NullPointerException,RuntimeException{
		
		if(question==null) {
			throw new NullPointerException("Question is null");
		}
		
		if(question.getEvent()==(null)) {
			throw new RuntimeException("Question have no an event");
		}
		
		ArrayList<Forecast> forecasts = (ArrayList<Forecast>) getAllForecasts();
		List<Forecast> forecastsOfQuestion=new ArrayList<Forecast>();
		
		if(forecasts ==null) {
			return forecastsOfQuestion;

		}
		else {
			for(Forecast f:forecasts) {
				if(f.getQuestion().getQuestionNumber()==question.getQuestionNumber()) {
					f.setQuestion(question);
					forecastsOfQuestion.add(f);
				}
			}			
			return forecastsOfQuestion;
		}
		
	}
	
	
	public ArrayList<Float> getQuestionMultiplersFromNumber(Integer numero) {
		TypedQuery<Question> query = db.createQuery("SELECT p FROM Question p WHERE p.questionNumber= "+numero+"", Question.class);
		List<Question> questions = query.getResultList();
		if(questions.size()<1) {
			System.out.println("Error. Lista de preguntas nula");
			return null;
		}
		else {
			ArrayList<Float> f=questions.get(0).getMultipliers();
			for(@SuppressWarnings("unused") Float asd:f) {
			}
			return f;
		}
		
	}
	
	public void setQuestionResult(Integer numero, String result) {
		db.getTransaction().begin();
		Question q= db.find(Question.class, numero);
		q.setResult(result);		
		db.getTransaction().commit();
	}
	
	public void deleteForecast(Forecast forecast,String loggedUser) {
		db.getTransaction().begin();
		Account a =db.find(Account.class, loggedUser);
		Forecast f= db.find(Forecast.class, forecast.getForecastNumber());
		a.deletIndexForecast(f);
		db.remove(f);		
		db.getTransaction().commit();
	}
	
	public void paid(String user, double win) {
		db.getTransaction().begin();
		Account u= db.find(Account.class, user);
		u.paid(win);		
		db.getTransaction().commit();
	}
	
	public void restMoneyToUser(String pUserName, Double money) {
		db.getTransaction().begin();
		Account u= db.find(Account.class, pUserName);
		u.rest(money);		
		db.getTransaction().commit();
	}
	
	public void closeEvent(Event e) {
		db.getTransaction().begin();
		Event evento= db.find(Event.class, e.getEventNumber());
		db.remove(evento);
		db.getTransaction().commit();
	}
	
	public void removeQuestion(Question Question) {
		db.getTransaction().begin();
		Question q= db.find(Question.class, Question.getQuestionNumber());
		db.remove(q);
		db.getTransaction().commit();
	}
	
	
	
	public Account getUser(String pUserName) {
		Account cuenta = db.find(Account.class, pUserName);
		return cuenta;
	}
	
	

	public void storeForecast(Forecast pForecast) {
		db.getTransaction().begin();
		db.persist(pForecast);
		db.getTransaction().commit();
	}
	
	
	public ArrayList<Forecast> retrieveForecast(Question pQuestion, String pUser) {
		TypedQuery<Forecast> res = db.createQuery("SELECT f FROM Forecast f WHERE f.question.questionNumber="+pQuestion.getQuestionNumber()+" and f.user="+pUser+"", Forecast.class);
		ArrayList<Forecast> fores = (ArrayList<Forecast>) res.getResultList();
		return fores;
	}
	
	public void removeAllForecast() {
		List<Forecast> forecasts=null;
		TypedQuery<Forecast> query = db.createQuery("SELECT f FROM Forecast f",Forecast.class);
        forecasts = query.getResultList();
        for(Forecast f: forecasts) {
        	System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        	db.getTransaction().begin();
    		db.remove(f);
    		db.getTransaction().commit();
        }
	}
	
	
}