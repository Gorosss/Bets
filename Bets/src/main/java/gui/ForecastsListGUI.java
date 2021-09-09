package gui;

import businessLogic.BLFacade;

import com.toedter.calendar.JCalendar;

import domain.Forecast;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

import javax.swing.table.DefaultTableModel;


public class ForecastsListGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private final JLabel jLabelEvents = new JLabel("Forecasts"); 

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private JScrollPane scrollPaneEvents = new JScrollPane();
	

	private JTable tableForecasts= new JTable();

	private DefaultTableModel tableModelForecasts;

	
	private BLFacade facade=LoginRegisterGUI.getBusinessLogic();
	
	private String[] columnNamesEvents = new String[] {
		"ForecastN",
		"Forecast", 

	};
	
	protected Forecast selectedForecast;
	private final JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ForecastsListGUI.btnNewButton.text")); //$NON-NLS-1$ //$NON-NLS-2$
	private final JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ForecastsListGUI.btnClose.text")); //$NON-NLS-1$ //$NON-NLS-2$

	public ForecastsListGUI()
	{
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
		this.setSize(new Dimension(701, 318));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("ForecastsListGUI.this.title")); //$NON-NLS-1$ //$NON-NLS-2$
		jLabelEvents.setBounds(40, 23, 259, 16);
		this.getContentPane().add(jLabelEvents);

		this.jCalendar1.addPropertyChangeListener(new PropertyChangeListener()
		{
			public void propertyChange(PropertyChangeEvent propertychangeevent)
			{


						tableModelForecasts.setDataVector(null, columnNamesEvents);

						

						
						List<Forecast> forecasts=facade.getUserForecasts(facade.getLoggedUsername());

						if (forecasts==null || forecasts.isEmpty() ) jLabelEvents.setText("There are no forecasts");
						else {jLabelEvents.setText("Your forecasts:");
						for (Forecast f:forecasts){
							Vector<Object> row = new Vector<Object>();

							System.out.println("Events "+f);

							row.add(f.getForecastNumber());
							row.add(f.toString());
							
							System.out.println("Медведь просит есть");
							row.add(f); 
							tableModelForecasts.addRow(row);		
						}
						tableForecasts.getColumnModel().getColumn(0).setPreferredWidth(10);
						tableForecasts.getColumnModel().getColumn(1).setPreferredWidth(268);

					
						}
				
			} 
		});

		this.getContentPane().add(jCalendar1, null);
		
		scrollPaneEvents.setBounds(new Rectangle(40, 50, 598, 150));

		tableForecasts.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			
				btnNewButton.setVisible(true);
				int i=tableForecasts.getSelectedRow();
				Integer numeroForecast = (Integer)tableModelForecasts.getValueAt(i,0);
				selectedForecast=facade.getUserForecasts(facade.getLoggedUsername()).get(numeroForecast);
				System.out.println(selectedForecast.toString());

				
	
			}
		});

		scrollPaneEvents.setViewportView(tableForecasts);
		tableModelForecasts = new DefaultTableModel(null, columnNamesEvents);

		tableForecasts.setModel(tableModelForecasts);
		tableForecasts.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableForecasts.getColumnModel().getColumn(1).setPreferredWidth(268);

		this.getContentPane().add(scrollPaneEvents, null);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Double returnMoney=selectedForecast.getMyBet()*0.6;
				System.out.println(Math.round(returnMoney*100.0)/100.0);
				
				
				facade.paidUsers(facade.getLoggedUsername(),Math.round(returnMoney*100.0)/100.0);
				facade.deleteForecast(selectedForecast);
				btnNewButton.setVisible(false);

			}
		});
		btnNewButton.setBounds(479, 231, 159, 37);
		btnNewButton.setVisible(false);
		getContentPane().add(btnNewButton);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jButtonClose_actionPerformed(arg0);
			}
		});
		btnClose.setBounds(40, 245, 89, 23);
		
		getContentPane().add(btnClose);

	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
	
}
