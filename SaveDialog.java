package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SaveDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SaveDialog dialog = new SaveDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SaveDialog() {
		this.setTitle("Save");
		setBounds(100, 100, 323, 125);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		this.setAlwaysOnTop(true);
		this.setLocation(800, 150);
		{
			JLabel lblNewLabel = new JLabel("Do you want to Save? ");
			lblNewLabel.setBounds(24, 11, 181, 31);
			contentPanel.add(lblNewLabel);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton yesButton = new JButton("YES");
				yesButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						SaveAs sa = new SaveAs();
						JEditorPane j = NotepadHome.getTabbedPane();
						sa.saveAs(j);
					}
				});
				yesButton.setActionCommand("YES");
				buttonPane.add(yesButton);
				getRootPane().setDefaultButton(yesButton);
			}
			{
				JButton noButton = new JButton("NO");
				noButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						NotepadHome.close();
						dispose();
					}
				});
				noButton.setActionCommand("NO");
				buttonPane.add(noButton);
				getRootPane().setDefaultButton(noButton);
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
