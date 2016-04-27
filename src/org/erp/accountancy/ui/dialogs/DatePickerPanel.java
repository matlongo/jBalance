package org.erp.accountancy.ui.dialogs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.JFormattedTextField.AbstractFormatter;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class DatePickerPanel {

	private UtilDateModel model;
	private JDatePickerImpl datePicker;
	
	public DatePickerPanel() {
		//Setting the model
		model = new UtilDateModel();
		Calendar calendar = Calendar.getInstance();
		model.setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		model.setSelected(true);
		
		//Setting the properties
		Properties p = new Properties();
		p.put("text.today", "Hoy");
		p.put("text.month", "Mes");
		p.put("text.year", "Año");
		
		//Setting the datePicker
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
	}
	
	public JDatePickerImpl getDatePickerImpl(){
		return datePicker;
	}
	
	public UtilDateModel getModel(){
		return model;
	}
	
	
	
	/**
	 * This private class implements the formatter to be used by the JDatePickerImpl
	 * @author mathias
	 *
	 */
	private static class DateLabelFormatter extends AbstractFormatter {

		private static final long serialVersionUID = -6031301885332136666L;
		private String datePattern = "dd-MM-yyyy";
		private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

		@Override
		public Object stringToValue(String text) throws ParseException {
			return dateFormatter.parseObject(text);
		}

		@Override
		public String valueToString(Object value) throws ParseException {
			if (value != null) {
				Calendar cal = (Calendar) value;
				return dateFormatter.format(cal.getTime());
			}

			return "";
		}

	}

}
