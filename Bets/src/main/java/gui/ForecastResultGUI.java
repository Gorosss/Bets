package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;

import com.toedter.calendar.JCalendar;
import domain.Question;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;

import javax.swing.table.DefaultTableModel;
import com.toedter.components.JSpinField;


public class ForecastResultGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private final JLabel jLabelQueries = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries")); 
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events")); 

	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;
	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JScrollPane scrollPaneQueries = new JScrollPane();
	private JLabel lblAddForecast = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateForecastGUI.lblAddForecast.text"));
	private JButton btnBet = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateForecastGUI.btnBet.text"));
	private JToggleButton buttonOne = new JToggleButton(ResourceBundle.getBundle("Etiquetas").getString("CreateForecastGUI.toggleButton.text"));
	private JToggleButton buttonX = new JToggleButton(ResourceBundle.getBundle("Etiquetas").getString("CreateForecastGUI.tglbtnX.text"));
	private JToggleButton buttonTwo = new JToggleButton(ResourceBundle.getBundle("Etiquetas").getString("CreateForecastGUI.toggleButton_2.text"));
	private JSpinField numericAnswerSP = new JSpinField();
	
	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private JTable tableEvents= new JTable();
	private JTable tableQueries = new JTable();

	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelQueries;
	
	private Question selectedQuestion;
	private String selectedAnswer;

	
	private String[] columnNamesEvents = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("EventN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Event"), 

	};
	private String[] columnNamesQueries = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("QueryN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Query")

	};
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final JTextField stringAnswer = new JTextField();

	public ForecastResultGUI()
	{
		//stringAnswer.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateForecastGUI.textField.text")); //$NON-NLS-1$ //$NON-NLS-2$
		
		//stringAnswer.setColumns(10);
		try
		{
			jbInit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	
	private void jbInit() throws Exception
	{

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		stringAnswer.setBounds(new Rectangle(451, 272, 167, 25));
		jLabelQueries.setBounds(40, 213, 389, 14);
		jLabelEvents.setBounds(295, 19, 259, 16);

		this.getContentPane().add(stringAnswer, null);
		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelQueries);
		this.getContentPane().add(jLabelEvents);
		jButtonClose.setFont(new Font("Tahoma", Font.PLAIN, 20));

		jButtonClose.setBounds(new Rectangle(40, 420, 130, 30));

		jButtonClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				jButton2_actionPerformed(e);
			}
		});

		this.getContentPane().add(jButtonClose, null);


		jCalendar1.setBounds(new Rectangle(40, 50, 225, 150));

		BLFacade facade = LoginRegisterGUI.getBusinessLogic();
		datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar1.getDate());
		CreateQuestionGUI.paintDaysWithEvents(jCalendar1,datesWithEventsCurrentMonth);

		// Code for JCalendar
		this.jCalendar1.addPropertyChangeListener(new PropertyChangeListener()
		{
			public void propertyChange(PropertyChangeEvent propertychangeevent)
			{

				if (propertychangeevent.getPropertyName().equals("locale"))
				{
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
				}
				else if (propertychangeevent.getPropertyName().equals("calendar"))
				{
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
//					jCalendar1.setCalendar(calendarAct);
					Date firstDay=UtilDate.trim(new Date(jCalendar1.getCalendar().getTime().getTime()));

					 
					
					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);
					
					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) {
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolvería 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}						
						
						jCalendar1.setCalendar(calendarAct);

						BLFacade facade = LoginRegisterGUI.getBusinessLogic();

						datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar1.getDate());
					}



					CreateQuestionGUI.paintDaysWithEvents(jCalendar1,datesWithEventsCurrentMonth);
													
					

					try {
						tableModelEvents.setDataVector(null, columnNamesEvents);
						tableModelEvents.setColumnCount(3); // another column added to allocate ev objects

						BLFacade facade=LoginRegisterGUI.getBusinessLogic();

						Vector<domain.Event> events=facade.getEvents(firstDay);

						if (events.isEmpty() ) jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")+ ": "+dateformat1.format(calendarAct.getTime()));
						else jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events")+ ": "+dateformat1.format(calendarAct.getTime()));
						for (domain.Event ev:events){
							Vector<Object> row = new Vector<Object>();

							System.out.println("Events "+ev);

							row.add(ev.getEventNumber());
							row.add(ev.getDescription());
							row.add(ev); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,2)
							tableModelEvents.addRow(row);		
						}
						tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
						tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
						tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(2)); // not shown in JTable
					} catch (Exception e1) {

						jLabelQueries.setText(e1.getMessage());
					}

				}
			} 
		});

		this.getContentPane().add(jCalendar1, null);
		
		scrollPaneEvents.setBounds(new Rectangle(292, 50, 346, 150));
		scrollPaneQueries.setBounds(new Rectangle(40, 233, 389, 116));

		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=tableEvents.getSelectedRow();
				domain.Event ev=(domain.Event)tableModelEvents.getValueAt(i,2); // obtain ev object
				Vector<Question> queries=ev.getQuestions();

				tableModelQueries.setDataVector(null, columnNamesQueries);

				if (queries.isEmpty())
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("NoQueries")+": "+ev.getDescription());
				else 
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedEvent")+" "+ev.getDescription());

				for (domain.Question q:queries){
					Vector<Object> row = new Vector<Object>();

					row.add(q.getQuestionNumber());
					row.add(q.getQuestion());
					tableModelQueries.addRow(row);	
				}
				tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
				tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);
			}
		});

		scrollPaneEvents.setViewportView(tableEvents);
		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);

		
		
		tableEvents.setModel(tableModelEvents);
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
		tableQueries.addMouseListener(new MouseAdapter() {	
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int i = tableQueries.getSelectedRow();	
				Integer numeroPregunta = (Integer)tableQueries.getValueAt(i, 0);
				selectedQuestion = facade.getQuestionFromNumber(numeroPregunta);
				if(selectedQuestion==null) {
					System.out.println("La pregunta ha devuelto null");
				}
				else {
					System.out.println("Se ha conseguido la pregunta");
				}
				int tipo = selectedQuestion.getQuestionType();
				switch(tipo) {
				case 1:
					System.out.println("TIPO 1");
					lblAddForecast.setVisible(true);
					btnBet.setVisible(true);
					buttonOne.setVisible(true);
					buttonX.setVisible(true);
					buttonTwo.setVisible(true);
					numericAnswerSP.setVisible(false);
					stringAnswer.setVisible(false);

					break;
				case 2:
					System.out.println("TIPO 2");
					lblAddForecast.setVisible(true);
					btnBet.setVisible(true);
					buttonOne.setVisible(true);
					buttonX.setVisible(false);
					buttonTwo.setVisible(true);
					numericAnswerSP.setVisible(false);
					stringAnswer.setVisible(false);

					break;
				case 3:
					System.out.println("TIPO 3");
					numericAnswerSP.setMinimum(0);
					lblAddForecast.setVisible(true);
					btnBet.setVisible(true);
					buttonOne.setVisible(false);
					buttonX.setVisible(false);
					buttonTwo.setVisible(false);
					numericAnswerSP.setVisible(true);
					stringAnswer.setVisible(false);

					break;
				case 4:
					System.out.println("TIPO 4");
					lblAddForecast.setVisible(true);
					btnBet.setVisible(true);
					buttonOne.setVisible(false);
					buttonX.setVisible(false);
					buttonTwo.setVisible(false);
					numericAnswerSP.setVisible(false);
					stringAnswer.setVisible(true);

					break;
				}
			}
		});
		

		scrollPaneQueries.setViewportView(tableQueries);
		tableModelQueries = new DefaultTableModel(null, columnNamesQueries);

		tableQueries.setModel(tableModelQueries);
		tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);

		this.getContentPane().add(scrollPaneEvents, null);
		this.getContentPane().add(scrollPaneQueries, null);
		
		
		lblAddForecast.setVisible(false);
		lblAddForecast.setBounds(443, 233, 195, 16);
		getContentPane().add(lblAddForecast);
		
			
		
		
		btnBet.setVisible(false);
		btnBet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean error = true;
				switch (selectedQuestion.getQuestionType()) {
					case 1:
						if (buttonOne.isSelected()) {
							selectedAnswer = "1";
							error = false;
						} else if(buttonX.isSelected()) {
							selectedAnswer = "x";
							error = false;
						} else if(buttonTwo.isSelected()) {
							selectedAnswer = "2";
							error = false;
						}
						break;
					case 2:
						if (buttonOne.isSelected()) {
							selectedAnswer = "1";
							error = false;
						} else if(buttonTwo.isSelected()) {
							selectedAnswer = "2";
							error = false;
						}
						break;
					case 3:
						selectedAnswer =  Float.toString(numericAnswerSP.getValue());
						error = false;
						break;
					case 4:
						if (stringAnswer.getText() != null && stringAnswer.getText() != "") {
							selectedAnswer=stringAnswer.getText();
							error = false;
						}
						break;
					default:
						System.out.println("Question not selected.");
						break;
				}
				System.out.println("User answer: " + selectedAnswer);

				if (!error)
					LoginRegisterGUI.getBusinessLogic().setQuestionResult(selectedQuestion.getQuestionNumber(), selectedAnswer);
				else 
					System.out.println("Error. Bet input format illegal. Code 8.");
				
			}
		});
		btnBet.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnBet.setBounds(451, 319, 167, 30);
		getContentPane().add(btnBet);
		
		
		buttonGroup.add(buttonOne);
		buttonOne.setVisible(false);
		buttonOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		buttonOne.setFont(new Font("Tahoma", Font.PLAIN, 26));
		buttonOne.setBounds(441, 253, 53, 51);
		getContentPane().add(buttonOne);
		
		
		buttonGroup.add(buttonX);
		buttonX.setVisible(false);
		buttonX.setFont(new Font("Tahoma", Font.PLAIN, 26));
		buttonX.setBounds(508, 253, 53, 51);
		getContentPane().add(buttonX);
		
		
		buttonGroup.add(buttonTwo);
		buttonTwo.setVisible(false);
		buttonTwo.setFont(new Font("Tahoma", Font.PLAIN, 26));
		buttonTwo.setBounds(572, 253, 53, 51);
		getContentPane().add(buttonTwo);
		
		
		numericAnswerSP.setBounds(501, 266, 53, 31);
		getContentPane().add(numericAnswerSP);
		numericAnswerSP.setVisible(false);
		
		getContentPane().add(stringAnswer);
		stringAnswer.setVisible(false);
		
		

	}

	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
