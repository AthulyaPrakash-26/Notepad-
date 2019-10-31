package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FindDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			FindDialog dialog = new FindDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public FindDialog() {
		this.setTitle("Find");
		setBounds(100, 100, 450, 177);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		this.setAlwaysOnTop(true);
		this.setLocation(800, 150);

		textField = new JTextField();
		textField.setBounds(32, 47, 231, 20);
		contentPanel.add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(32, 78, 167, 20);
		contentPanel.add(lblNewLabel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton findButton = new JButton("Find");
				findButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						lblNewLabel.setText("");
						if ("".equals(textField.getText()) || textField.getText() == null) {
							lblNewLabel.setText("Empty String");
							lblNewLabel.setVisible(true);
						} else {
							try {
								NotepadHome.highlight(textField.getText());
								lblNewLabel.setText("String Not Found");
							} catch (Exception ex) {

							}

						}
					}
				});
				buttonPane.add(findButton);
				findButton.setActionCommand("Find");
				getRootPane().setDefaultButton(findButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
