package org.erp.accountancy.ui.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class ButtonsPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 927005940204070707L;
	private String[] buttonsText;
	protected JButton[] buttons;
	private String title;

	public ButtonsPanel(String[] buttosText, String title) {
		this.title = title;
		this.buttonsText = buttosText;
		buttons = new JButton[buttonsText.length];
		this.initButtons();
	}

	private void initButtons() {
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(buttonsText.length + 2, 1, 3, 5));
		// First, we add the title
		JPanel titlePanel = new JPanel();
		titlePanel.setSize(new Dimension(400, 50));
		JLabel titleLablel = new JLabel(title);
		titleLablel.setAlignmentX(CENTER_ALIGNMENT);
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 18);
		titleLablel.setFont(font);
		titlePanel.add(titleLablel);
		buttonsPanel.add(titlePanel);

		// Then we add the buttons
		int pos = 0;
		for (String buttonText : buttonsText) {
			JButton button = new JButton(buttonText);
			button.setAlignmentX(CENTER_ALIGNMENT);
			button.setPreferredSize(new Dimension(400, 50));
			buttonsPanel.add(button);
			buttons[pos++] = button;
			button.addActionListener(this);
		}

		this.add(buttonsPanel, BorderLayout.AFTER_LAST_LINE);
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setButtonsText(String[] buttons){
		this.buttonsText = buttons;
	}
	
}
