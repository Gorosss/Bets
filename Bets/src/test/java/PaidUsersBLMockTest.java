import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Vector;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.Event;
import domain.Forecast;
import domain.Question;

class PaidUsersBLMockTest {
	DataAccess dataAccess = Mockito.mock(DataAccess.class);

	BLFacade sut = new BLFacadeImplementation(dataAccess);
	
	private Event ev = new Event();
	
	
	@DisplayName("Test 1: queries is null")
	@Test
	void testPaidUsersVectorOfQuestion1() {		

		Vector<Question> queries = null;

		try {

			assertThrows(NullPointerException.class, ()-> sut.paidUsers(queries));

		} catch (RuntimeException e) {
			fail("Mock DataAccess should not raise the exception RuntimeException");
		}
	}
	
	@DisplayName("Test 2: Querie have a null question")
	@Test
	void testPaidUsersVectorOfQuestion2() {
		try {


			Question q = null;
			ArrayList<Question> qList = new ArrayList<Question>();
			qList.add(q);
			Vector<Question> queries = new Vector<Question>(qList);

			try {
				
				Mockito.verify(dataAccess,Mockito.times(0))
				.getForecastsOfQuestion(Mockito.any(Question.class));
				Mockito.verify(dataAccess,Mockito.times(0))
				.paid(Mockito.anyString(), Mockito.anyDouble());

				assertThrows(NullPointerException.class, ()-> sut.paidUsers(queries));

			} catch (NullPointerException e) {
				fail("Mock DataAccess should not raise the exception NullPointerException");
			}
		} catch (RuntimeException e) {
			fail("Mock DataAccess should not raise the exception RuntimeException");	
		}

	}
	
	@DisplayName("Test 3: Question do not have event")
	@Test
	void testPaidUsersVectorOfQuestion3() {
		try {

			Question q = new Question();
			ArrayList<Question> qList = new ArrayList<Question>();
			qList.add(q);
			Vector<Question> queries = new Vector<Question>(qList);

			try {

				Mockito.verify(dataAccess,Mockito.times(0))
				.getForecastsOfQuestion(Mockito.any(Question.class));
				Mockito.verify(dataAccess,Mockito.times(0))
				.paid(Mockito.anyString(), Mockito.anyDouble());

				assertThrows(NullPointerException.class, ()-> sut.paidUsers(queries));

			} catch (NullPointerException e) {
				fail("Mock DataAccess should not raise the exception NullPointerException");
			}
		} catch (RuntimeException e) {
			fail("Mock DataAccess should not raise the exception RuntimeException");	
		}

	}
	
	@DisplayName("Test 4: Empty querie")
	@Test
	void testPaidUsersVectorOfQuestion4() {		

		Vector<Question> queries = new Vector<Question>();;

		try {

			sut.paidUsers(queries);
			
			Mockito.verify(dataAccess,Mockito.times(0))
			.getForecastsOfQuestion(Mockito.any(Question.class));
			Mockito.verify(dataAccess,Mockito.times(0))
			.paid(Mockito.anyString(), Mockito.anyDouble());

		}catch (NullPointerException e) {
			fail("Mock DataAccess should not raise the exception NullPointerException");

		} catch (RuntimeException e) {
			fail("Mock DataAccess should not raise the exception RuntimeException");
		}
	}

	@DisplayName("Test 5: Question do not have any forecast")
	@Test
	void testPaidUsersVectorOfQuestion5() {
		try {

			Question q = new Question();
			q.setEvent(ev);
			ArrayList<Question> qList = new ArrayList<Question>();
			qList.add(q);
			Vector<Question> queries = new Vector<Question>(qList);
			
			ArrayList<Forecast> fList = new ArrayList<Forecast>();
			
			try {



				ArgumentCaptor<Question> qCaptor = ArgumentCaptor.forClass(Question.class);

				Mockito.doReturn(fList).when(dataAccess).getForecastsOfQuestion(qCaptor.capture());

				sut.paidUsers(queries);

				Mockito.verify(dataAccess,Mockito.times(1))
				.getForecastsOfQuestion(Mockito.any(Question.class));
				
				Mockito.verify(dataAccess,Mockito.times(0))
				.paid(Mockito.anyString(), Mockito.anyDouble());

				assertEquals(q, qCaptor.getValue());

			} catch (NullPointerException e) {
				fail("Mock DataAccess should not raise the exception NullPointerException");
			}
		} catch (RuntimeException e) {
			fail("Mock DataAccess should not raise the exception RuntimeException");	
		}

	}
	
	

	
	
	@DisplayName("Test 6: Fail forecast")
	@Test
	void testPaidUsersVectorOfQuestion6() {
		try {


			Question q = new Question();
			q.setResult("2");
			q.setEvent(ev);
			ArrayList<Question> qList = new ArrayList<Question>();
			qList.add(q);
			Vector<Question> queries = new Vector<Question>(qList);	
			
			Forecast f= new Forecast(q, 2, "1", 2,"Jon");
			ArrayList<Forecast> fList = new ArrayList<Forecast>();
			fList.add(f);

			try {

				ArgumentCaptor<Question> qCaptor = ArgumentCaptor.forClass(Question.class);

				Mockito.doReturn(fList).when(dataAccess).getForecastsOfQuestion(qCaptor.capture());

				sut.paidUsers(queries);

				Mockito.verify(dataAccess,Mockito.times(1))
				.getForecastsOfQuestion(Mockito.any(Question.class));
				
				Mockito.verify(dataAccess,Mockito.times(0))
				.paid(Mockito.anyString(), Mockito.anyDouble());
				
				
				assertEquals(q, qCaptor.getValue());

			} catch (NullPointerException e) {
				fail("Mock DataAccess should not raise the exception NullPointerException");
			}
		} catch (RuntimeException e) {
			fail("Mock DataAccess should not raise the exception RuntimeException");	
		}

	}
	
	@DisplayName("Test 7: Gues forecast")
	@Test
	void testPaidUsersVectorOfQuestion7() {
		try {


			Question q = new Question();
			q.setResult("2");
			q.setEvent(ev);
			Question q2 = new Question();
			q2.setResult("2");
			q2.setEvent(ev);
			ArrayList<Question> qList = new ArrayList<Question>();
			qList.add(q);
			qList.add(q2);
			
			Vector<Question> queries = new Vector<Question>(qList);
			
			
			Forecast f= new Forecast(q, 2, "1", 2,"Jon");
			Forecast f1= new Forecast(q2, 2, "2", 2,"Alfred");
			Forecast f2= new Forecast(q2, 2, "2", 2,"James");
			ArrayList<Forecast> fList = new ArrayList<Forecast>();
			ArrayList<Forecast> fList2 = new ArrayList<Forecast>();

			fList.add(f);
			fList2.add(f1);
			fList2.add(f2);

			try {

				Mockito.doReturn(fList).when(dataAccess).getForecastsOfQuestion(q);
				Mockito.doReturn(fList2).when(dataAccess).getForecastsOfQuestion(q2);

				sut.paidUsers(queries);

				Mockito.verify(dataAccess,Mockito.times(2))
				.getForecastsOfQuestion(Mockito.any(Question.class));
				
				Mockito.verify(dataAccess,Mockito.times(2))
				.paid(Mockito.anyString(), Mockito.anyDouble());
						

			} catch (NullPointerException e) {
				fail("Mock DataAccess should not raise the exception NullPointerException");
			}
		} catch (RuntimeException e) {
			fail("Mock DataAccess should not raise the exception RuntimeException");	
		}

	}
	

}
