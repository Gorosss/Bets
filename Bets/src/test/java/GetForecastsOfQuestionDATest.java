

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Account;
import domain.Event;
import static org.junit.jupiter.api.Assertions.*;
import domain.Forecast;
import domain.Question;
import utility.TestUtilityDataAccess;

class GetForecastsOfQuestionDATest {

	static DataAccess sut = new DataAccess(ConfigXML.getInstance().getDataBaseOpenMode().equals("initialize"));;
	static TestUtilityDataAccess testDA = new TestUtilityDataAccess();
	
	private Event ev;
	
	@Test
	@DisplayName("Test 1: Quesion is null")
	void testGetForecastsOfQuestion1() {
		
		try {
			
			Question q=null;
			assertThrows(NullPointerException.class, () -> sut.getForecastsOfQuestion(q));
			
		} catch (RuntimeException e) {
			// if the program goes to this point fail
			fail();
		}		
	}
	
	@Test
	@DisplayName("Test 2: Question is not in the DB")
	void testGetForecastsOfQuestion2() {
		
		String queryText = "QueryNotInDB";
		Float betMinimum = 10f;
		int questionType= 2;
		String multipliers="2,3";
		
		Question q = new Question(queryText, betMinimum, ev, questionType, multipliers);
		
		try {

			assertThrows(RuntimeException.class, () -> sut.getForecastsOfQuestion(q));

		}catch (NullPointerException e) {
			// if the program goes to this point fail
			fail();
		}
	}
	
	@Test
	@DisplayName("Test 3: No forecast in DB")
	void testGetForecastsOfQuestion3() {
	
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = sdf.parse("05/10/2022");
			String eventText = "Event Text";
			String queryText = "Query Text";
			Float betMinimum = 100f;
			Integer evNum=1;

			testDA.open();
			ev = testDA.addEventWithQuestion(evNum,eventText, oneDate, queryText, betMinimum);
			testDA.close();
			
			Question q=ev.getQuestions().get(0);
			
	
			List<Forecast> fList=sut.getForecastsOfQuestion(q);
			
			
			ArrayList<Forecast> fListExpected=new ArrayList<Forecast>();
			
			
			assertEquals(fListExpected, fList);
			
		} catch (NullPointerException e) {
			// if the program goes to this point fail
			fail();
		}catch (ParseException e) {
			// if the program goes to this point fail
			fail();
		}catch (RuntimeException e) {
			// if the program goes to this point fail
			fail();
		}finally {
			// Remove the created objects in the database (cascade removing)
			testDA.open();
		
			boolean b = testDA.removeEvent(ev);
			
			System.out.println("Removed event " + b);
			testDA.close();
		}
		
	}
	
	
	
	@Test
	@DisplayName("Test 4: Case with one forecast")
	void testGetForecastsOfQuestion4() {
		
		Account user= new Account("Jon", "pass", false, "jon@gmail.com", "cardNum", "Jon", "Gorostegui");
		
		Forecast f= new Forecast(null, 2, "2", 2,user.getUserName());
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = sdf.parse("05/10/2022");
			String eventText = "Event Text";
			String queryText = "Query Text";
			Float betMinimum = 100f;
			Integer evNum=1;

			testDA.open();
			ev = testDA.addEventWithQuestion(evNum,eventText, oneDate, queryText, betMinimum);
			testDA.close();
			
			Question q=ev.getQuestions().get(0);		
	
			f.setQuestion(q);
			user.addForecast(f);
			
			testDA.open();
			testDA.storeForecast(f);
			testDA.storeAccount(user);
			testDA.close();
			
			ArrayList<Forecast> fList=(ArrayList<Forecast>) sut.getForecastsOfQuestion(q);

			ArrayList<Forecast> fListExpected=new ArrayList<Forecast>();
			fListExpected.add(f);
			
		
			assertTrue(fListExpected.get(0).getForecastNumber() == fList.get(0).getForecastNumber());
			
			
		}catch (NullPointerException e) {
			// if the program goes to this point fail
			fail();
		}catch (ParseException e) {
			// if the program goes to this point fail
			fail();
		}catch (RuntimeException e) {
			// if the program goes to this point fail
			fail();
		}finally {
			testDA.open();
			boolean a = testDA.removeEvent(ev);
			boolean b = testDA.deleteForecastAndUser(f, f.getUser());
			testDA.close();
			System.out.println("Finally " + a +" "+ b);
		}
		
	}
	
	
	@Test
	@DisplayName("Test 5: More than one forecast")
	void testGetForecastsOfQuestion5() {
		Account user= new Account("Jon", "pass", false, "jon@gmail.com", "cardNum", "Jon", "Gorostegui");
		
		Forecast f=new Forecast(null, 2, "2", 2,user.getUserName());
		Forecast f2=new Forecast(null, 3, "1", 6,user.getUserName());
		Forecast f3=new Forecast(null, 3, "1", 6,user.getUserName());

		
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = sdf.parse("05/10/2022");
			String eventText = "Event Text";
			String queryText = "Query Text";
			Float betMinimum = 2f;
			int questionType= 2;
			String multipliers="2,3";
			Integer evNum=1;

			testDA.open();
			ev = testDA.addEventWithQuestion(evNum,eventText, oneDate, queryText, 0.0f);
			testDA.close();
			
			Question q = ev.getQuestions().get(0);
			Question q2 = new Question(queryText, betMinimum, ev, questionType, multipliers);
			
			
			f.setQuestion(q);
			f2.setQuestion(q);
			f3.setQuestion(q2);
			user.addForecast(f);
			user.addForecast(f2);
			user.addForecast(f3);
			
			testDA.open();
			testDA.storeForecast(f);
			testDA.storeForecast(f2);
			testDA.storeForecast(f3);
			testDA.storeAccount(user);
			testDA.close();
			
			List<Forecast> fList=sut.getForecastsOfQuestion(q);
			List<Forecast> fListExpected=new ArrayList<Forecast>();
			fListExpected.add(f);
			fListExpected.add(f2);
						
			assertTrue(fListExpected.get(0).getForecastNumber() == fList.get(0).getForecastNumber());
			assertTrue(fListExpected.get(1).getForecastNumber() == fList.get(1).getForecastNumber());
			
			
			
			
		}catch (NullPointerException e) {
			// if the program goes to this point fail
			fail();
		}catch (ParseException e) {
			// if the program goes to this point fail
			fail();
		}catch (RuntimeException e) {
			// if the program goes to this point fail
			fail();
		}finally {
			testDA.open();	
			boolean a = testDA.deleteForecastAndUser(f, f.getUser());
			boolean a2 = testDA.deleteForecastAndUser(f2, f.getUser());
			boolean a3 = testDA.deleteForecastAndUser(f3, f.getUser());

			boolean b = testDA.removeEvent(ev);						
			testDA.close();
			System.out.println("Finally " +a+" "+a2+" "+a3+" "+b);
		}
		
	}

}
