package utility;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import domain.Account;
import domain.Event;
import domain.Forecast;
import domain.Question;

public class TestUtilityDataAccess {
	protected  EntityManager  db;
	protected  EntityManagerFactory emf;

	ConfigXML  c=ConfigXML.getInstance();


	public TestUtilityDataAccess()  {		
		System.out.println("Creating TestDataAccess instance");

		open();		
	}

	
	public void open(){
		
		System.out.println("Opening TestDataAccess instance ");

		String fileName=c.getDbFilename();
		
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
	public void close(){
		db.close();
		System.out.println("DataBase closed");
	}

	public boolean removeEvent(Event ev) {
		System.out.println(">> DataAccessTest: removeEvent");
		Event e = db.find(Event.class, ev.getEventNumber());
		if (e!=null) {
			db.getTransaction().begin();
			db.remove(e);
			db.getTransaction().commit();
			return true;
		} else 
		return false;
    }
		
	
		public Event addEventWithQuestion(Integer evNum,String desc, Date d, String question, float qty) {
			System.out.println(">> DataAccessTest: addEvent");
			Event ev=null;
			int questionType= 2;
			String multipliers="2,3";
			
				db.getTransaction().begin();
				try {
				    ev=new Event(evNum,desc,d);
				    ev.addQuestion(question,  qty, questionType,multipliers);
					db.persist(ev);
					db.getTransaction().commit();
				}
				catch (Exception e){
					e.printStackTrace();
				}
				return ev;
	    }
		
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
		
		public boolean existQuestion(Event event, String question) {
			System.out.println(">> DataAccess: existQuestion=> event= "+event+" question= "+question);
			Event ev = db.find(Event.class, event.getEventNumber());
			return ev.DoesQuestionExists(question);
			
		}
		
		public void removeQuestion(Question Question) {
			db.getTransaction().begin();
			Question q= db.find(Question.class, Question.getQuestionNumber());
			db.remove(q);
			db.getTransaction().commit();
		}
		

		public void storeForecast(Forecast pForecast) {
			db.getTransaction().begin();
			db.persist(pForecast);
			db.getTransaction().commit();
		}
		public boolean deleteForecastAndUser(Forecast forecast,String loggedUser) {
			db.getTransaction().begin();
			Account a =db.find(Account.class, loggedUser);
			Forecast f= db.find(Forecast.class, forecast.getForecastNumber());
			a.deletIndexForecast(f);
			db.remove(f);	
			if(a.getAllUserForecast().isEmpty()) {
			db.remove(a);}
			db.getTransaction().commit();
			return true;
		}
		
		public void storeAccount(Account user) {
	        db.getTransaction().begin();
	        db.persist(user);
	        db.getTransaction().commit();
		}
		
		
}

