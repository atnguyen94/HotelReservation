import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calendar implements ChangeListener {
	
	public enum MONTHS {
		January, February, March, April, May, June, July, August, September, October, November, December;
	}
	
	public enum DAYS {
		Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday;
	}

	private CalendarM model;
	private DAYS[] days = DAYS.values();
	private MONTHS[] months = MONTHS.values();
	private int highlight = -1;
	private int maximum;

	private JFrame frame = new JFrame("Calendar");
	private JPanel monthViewPanel = new JPanel();
	private JLabel monthLabel = new JLabel();
	private JButton next = new JButton(">");
	private JButton prev = new JButton("<");
	private JTextPane dayTextPane = new JTextPane();
	private ArrayList<JButton> dayButtons = new ArrayList<JButton>();
}
