package gui;

import javax.swing.JFrame;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDayChooser;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Event;


import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Component;
import javax.swing.SwingConstants;
import java.awt.Font;

public class CreateEventsGUI extends JFrame{
	
	private JTextField txtNombreEvento;
	
	private JLabel jLabelListOfEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ListEvents"));
	
	private JComboBox<Event> jComboBoxEvents = new JComboBox<Event>();
	DefaultComboBoxModel<Event> modelEvents = new DefaultComboBoxModel<Event>();
	
	private JButton jButtonCreate = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateQuery"));

	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAct = null;
	private Calendar calendarAnt = null;
	
	
	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();
	
	public CreateEventsGUI() {
		
		this.setSize(new Dimension(610, 409));
		setTitle("CreateEvent - Crear evento");
		
		getContentPane().setLayout(null);
		
		jLabelListOfEvents.setBounds(new Rectangle(275, 16, 277, 20));
		this.getContentPane().add(jLabelListOfEvents, null);
		
		jComboBoxEvents.setModel(modelEvents);
		jComboBoxEvents.setBounds(new Rectangle(275, 47, 250, 20));
		this.getContentPane().add(jComboBoxEvents, null);
	
		txtNombreEvento = new JTextField();
		txtNombreEvento.setToolTipText("");
		txtNombreEvento.setBounds(83, 213, 225, 27);
		getContentPane().add(txtNombreEvento);
		txtNombreEvento.setColumns(10);
		
		JLabel lblEvento = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateEventsGUI.lblEvento.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblEvento.setBounds(21, 213, 81, 27);
		getContentPane().add(lblEvento);
		
		JLabel lblEquipos = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateEventsGUI.lblEquipos.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblEquipos.setHorizontalAlignment(SwingConstants.CENTER);
		lblEquipos.setBounds(108, 191, 189, 27);
		getContentPane().add(lblEquipos);
		
		JLabel lblEligeDia = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateEventsGUI.lblEligeDia.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblEligeDia.setBounds(10, 11, 125, 14);
		getContentPane().add(lblEligeDia);
		
		JLabel lblFechaIncorrecta = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateEventsGUI.lblFechaIncorrecta.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblFechaIncorrecta.setHorizontalAlignment(SwingConstants.CENTER);
		lblFechaIncorrecta.setForeground(Color.RED);
		lblFechaIncorrecta.setBounds(89, 263, 234, 14);
		getContentPane().add(lblFechaIncorrecta);
		lblFechaIncorrecta.setVisible(false);
		
		JLabel lblPartidoNoIntroducido = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateEventsGUI.lblPartidoNoIntroducido.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblPartidoNoIntroducido.setHorizontalAlignment(SwingConstants.CENTER);
		lblPartidoNoIntroducido.setForeground(Color.RED);
		lblPartidoNoIntroducido.setBounds(65, 251, 215, 14);
		getContentPane().add(lblPartidoNoIntroducido);
		lblPartidoNoIntroducido.setVisible(false);
		
		JLabel lblEventoCreado = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateEventsGUI.lblEventoCreado.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblEventoCreado.setHorizontalAlignment(SwingConstants.CENTER);
		lblEventoCreado.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		lblEventoCreado.setForeground(new Color(0, 128, 0));
		lblEventoCreado.setBounds(108, 332, 169, 27);
		getContentPane().add(lblEventoCreado);
		lblEventoCreado.setVisible(false);

			
		jCalendar1 = new JCalendar();
		jCalendar1.setBounds(new Rectangle(40, 50, 225, 150));
		jCalendar1.setBounds(10, 31, 225, 150);
		getContentPane().add(jCalendar1);
		
		BLFacade facade = LoginRegisterGUI.getBusinessLogic();
		datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar1.getDate());
		paintDaysWithEvents(jCalendar1,datesWithEventsCurrentMonth);
		
		this.jCalendar1.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					System.out.println("calendarAnt: "+calendarAnt.getTime());
					System.out.println("calendarAct: "+calendarAct.getTime());
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
					
					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);
					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) { 
							// Si en JCalendar estÃ¡ 30 de enero y se avanza al mes siguiente, devolverÃ­a 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este cÃ³digo se dejarÃ¡ como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}
						
						jCalendar1.setCalendar(calendarAct);
						
						BLFacade facade = LoginRegisterGUI.getBusinessLogic();

						datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar1.getDate());
					}



					paintDaysWithEvents(jCalendar1,datesWithEventsCurrentMonth);

					//	Date firstDay = UtilDate.trim(new Date(jCalendar.getCalendar().getTime().getTime()));
					Date firstDay = UtilDate.trim(calendarAct.getTime());

					try {
						BLFacade facade = LoginRegisterGUI.getBusinessLogic();

						Vector<domain.Event> events = facade.getEvents(firstDay);

						if (events.isEmpty())
							jLabelListOfEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")
									+ ": " + dateformat1.format(calendarAct.getTime()));
						else
							jLabelListOfEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events") + ": "
									+ dateformat1.format(calendarAct.getTime()));
						jComboBoxEvents.removeAllItems();
						System.out.println("Events " + events);

						for (domain.Event ev : events)
							modelEvents.addElement(ev);
						jComboBoxEvents.repaint();

						if (events.size() == 0)
							jButtonCreate.setEnabled(false);
						else
							jButtonCreate.setEnabled(true);

					} catch (Exception e1) {

						lblPartidoNoIntroducido.setVisible(true);
					}

				}
			}
		});
		
		
		
		JButton BotonCrearEvento = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateEventsGUI.BotonCrearEvento.text")); //$NON-NLS-1$ //$NON-NLS-2$
		BotonCrearEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				

				
				Date dateToday = new Date();  
				Date selectedDate=jCalendar1.getDate();

				String nomEvento = txtNombreEvento.getText();
				
				if(dateToday.after(selectedDate)) {
					lblFechaIncorrecta.setVisible(true);
					lblPartidoNoIntroducido.setVisible(false);
					lblEventoCreado.setVisible(false);

				}else if(nomEvento.length()<1){
					lblPartidoNoIntroducido.setVisible(true);
					lblFechaIncorrecta.setVisible(false);
					lblEventoCreado.setVisible(false);

				}else {
					lblFechaIncorrecta.setVisible(false);
					lblPartidoNoIntroducido.setVisible(false);
					lblEventoCreado.setVisible(true);

					Integer numEvento = LoginRegisterGUI.getBusinessLogic().lastEventNum()+1;

					txtNombreEvento.setText(null);


					JYearChooser year=jCalendar1.getYearChooser();
					JMonthChooser month=jCalendar1.getMonthChooser();
					JDayChooser day=jCalendar1.getDayChooser();

					LoginRegisterGUI.getBusinessLogic().addEvent(numEvento, nomEvento,UtilDate.newDate(year.getYear(),month.getMonth(),day.getDay()));

					
					datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar1.getDate());
					paintDaysWithEvents(jCalendar1,datesWithEventsCurrentMonth);
					
					
				}
			}	
		});
		BotonCrearEvento.setBounds(79, 283, 201, 40);
		getContentPane().add(BotonCrearEvento);
		
		JButton jButtonClose = new JButton("Close");
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButton2_actionPerformed(e);
			}
		});
		jButtonClose.setBounds(new Rectangle(274, 419, 130, 30));
		jButtonClose.setBounds(434, 288, 130, 30);
		getContentPane().add(jButtonClose);
		
	
		
		
		
		
		
		
	}
	protected void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);			
	}
	
	public static void paintDaysWithEvents(JCalendar jCalendar,Vector<Date> datesWithEventsCurrentMonth) {
		// For each day with events in current month, the background color for that day is changed.

		
		Calendar calendar = jCalendar.getCalendar();
		
		int month = calendar.get(Calendar.MONTH);
		int today=calendar.get(Calendar.DAY_OF_MONTH);
		int year=calendar.get(Calendar.YEAR);
		
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int offset = calendar.get(Calendar.DAY_OF_WEEK);

		if (Locale.getDefault().equals(new Locale("es")))
			offset += 4;
		else
			offset += 5;
		
		
	 	for (Date d:datesWithEventsCurrentMonth){

	 		calendar.setTime(d);
	 		System.out.println(d);
	 		

			
			// Obtain the component of the day in the panel of the DayChooser of the
			// JCalendar.
			// The component is located after the decorator buttons of "Sun", "Mon",... or
			// "Lun", "Mar"...,
			// the empty days before day 1 of month, and all the days previous to each day.
			// That number of components is calculated with "offset" and is different in
			// English and Spanish
//			    		  Component o=(Component) jCalendar.getDayChooser().getDayPanel().getComponent(i+offset);; 
			Component o = (Component) jCalendar.getDayChooser().getDayPanel()
					.getComponent(calendar.get(Calendar.DAY_OF_MONTH) + offset);
			o.setBackground(Color.CYAN);
	 	}
	 	
 			calendar.set(Calendar.DAY_OF_MONTH, today);
	 		calendar.set(Calendar.MONTH, month);
	 		calendar.set(Calendar.YEAR, year);

	 	
	}
	
	
}
