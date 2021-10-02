import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.Event;
import domain.Question;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;


class CreateQuestionBLMockTest {
	DataAccess dataAccess = Mockito.mock(DataAccess.class);
	Event mockedEvent = Mockito.mock(Event.class);

	BLFacade sut = new BLFacadeImplementation(dataAccess);

	@DisplayName("sut.createQuestion: The event has one question with a queryText.")
	@Test
	void test1() {
		try {
			// define paramaters
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = sdf.parse("05/10/2022");

			try {
				// configure Mock
				Mockito.doReturn(oneDate).when(mockedEvent).getEventDate();
				Mockito.when(dataAccess.createQuestion(Mockito.any(Event.class), Mockito.any(String.class),	Mockito.any(Float.class), Mockito.any(int.class), Mockito.any(String.class))).thenThrow(QuestionAlreadyExist.class);

				// invoke System Under Test (sut)
				String queryText = "Query Text";
				Float betMinimum = 2f;
				int questionType= 2;
				String multipliers="2,3";
				
				
				assertThrows(QuestionAlreadyExist.class, ()-> sut.createQuestion(mockedEvent, queryText, betMinimum,questionType,multipliers));

			} catch (QuestionAlreadyExist e) {
				// if the program goes to this point fail, the first createQuestion of Mock
				fail("Not possible");
			} 
		} catch (ParseException e) {
			fail("It should be correct: check the date format");
		}

	}

	@Test
	@DisplayName("sut.createQuestion: The event has NOT a question with a queryText.")
	void test2() {
		try {
			// define paramaters
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = sdf.parse("05/10/2022");

			// configure Mock
			Mockito.doReturn(oneDate).when(mockedEvent).getEventDate();
			String queryText = "Query Text";
			Float betMinimum = 2f;
			int questionType= 2;
			String multipliers="2,3";
			try {
				Mockito.doReturn(new Question(queryText, betMinimum, mockedEvent,questionType,multipliers)).when(dataAccess).createQuestion(Mockito.any(Event.class),
						Mockito.any(String.class), Mockito.any(Integer.class),Mockito.any(Integer.class), Mockito.any(String.class));

				// invoke System Under Test (sut)
				sut.createQuestion(mockedEvent, queryText, betMinimum,questionType,multipliers);

				// verify the results
				ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
				ArgumentCaptor<String> questionStringCaptor = ArgumentCaptor.forClass(String.class);
				ArgumentCaptor<Float> betMinimunCaptor = ArgumentCaptor.forClass(Float.class);
				ArgumentCaptor<Integer> questionTypeCaptor = ArgumentCaptor.forClass(Integer.class);
				ArgumentCaptor<String> multipliersStringCaptor = ArgumentCaptor.forClass(String.class);
				

				Mockito.verify(dataAccess, Mockito.times(1)).createQuestion(eventCaptor.capture(),
						questionStringCaptor.capture(), betMinimunCaptor.capture(),questionTypeCaptor.capture(),multipliersStringCaptor.capture());

				assertEquals(mockedEvent, eventCaptor.getValue());
				assertEquals(queryText, questionStringCaptor.getValue());
				assertEquals(betMinimum, betMinimunCaptor.getValue());

			} catch (QuestionAlreadyExist e) {
				fail("Mock DataAccess should not raise the exception QuestionAlreadyExist");
			} catch (EventFinished e) {
				fail("Mock DataAccess should not raise the exception EventFinished");
			}
		} catch (ParseException e) {
			fail("It should be correct: check the date format");
		}
	}

	@Test
	@DisplayName(" sut.createQuestion: The event is null.")
	void test3() {

		try {
			// define paramaters
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = sdf.parse("05/10/2022");

			// configure Mock
			Mockito.doReturn(oneDate).when(mockedEvent).getEventDate();
			try {
				String queryText = "Query Text";
				Float betMinimum = 2f;
				int questionType= 2;
				String multipliers="2,3";
				Mockito.doReturn(null).when(dataAccess).createQuestion(
						Mockito.any(Event.class), Mockito.any(String.class), Mockito.any(Integer.class),Mockito.any(Integer.class), Mockito.any(String.class));

				// invoke System Under Test (sut)
				Question q = sut.createQuestion(mockedEvent, queryText, betMinimum,questionType,multipliers);

				assertNull(q);

			} catch (QuestionAlreadyExist e) {
				fail("Mock DataAccess should not raise the exception QuestionAlreadyExist");
			} catch (EventFinished e) {
				fail("Mock DataAccess should not raise the exception EventFinished");
			}
		} catch (ParseException e) {
			fail("It should be correct: check the date format");
		}
	}
}
