package notepadview;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JFileChooser;

public class SaveAs {

	public void saveAs(JEditorPane text) {

		JFileChooser fileChoose = new JFileChooser(); 
		int n = fileChoose.showSaveDialog(fileChoose);

		if (n == javax.swing.JFileChooser.APPROVE_OPTION) {
			java.io.File sFile = fileChoose.getSelectedFile();

			FileWriter writer = null;
			try {
				BufferedWriter bufw = new BufferedWriter(new FileWriter(fileChoose.getSelectedFile().getAbsolutePath()));
				
				bufw.write(text.getText());
				writer = new FileWriter(sFile);
				text.write(writer);
				bufw.close(); 
			} catch (IOException ex) {

			} finally {
				if (writer != null) {
					try {
						writer.close();
					} catch (IOException x) {
					}
				}
			}
		}
	}

	

}