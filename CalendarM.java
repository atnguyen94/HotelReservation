import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CalendarM {

	private int maximum;
	private int selectedDate;
	private HashMap<String, ArrayList<Event>> eventMap = new HashMap<>();
	private ArrayList<ChangeListener> listeners = new ArrayList<>();
	private GregorianCalendar calendar = new GregorianCalendar();
	private boolean monthChanged = false;
	
	public CalendarM() {
		maximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		selectedDate = calendar.get(Calendar.DATE);
		inputEvents();
	}
	
	public void attach(ChangeListener l) {
		listeners.add(l);
	}
}
