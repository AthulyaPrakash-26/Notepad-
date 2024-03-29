package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JEditorPane;

import javax.swing.JFileChooser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;

public class NotepadHome extends JFrame {

	private StringBuffer buffer;
	private static NotepadHome frame;
	private static JTabbedPane tabbedPane;
	private static Highlighter.HighlightPainter myHighlightPainter;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new NotepadHome();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public NotepadHome() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1067, 804);
		this.setTitle("Notepad++");

		myHighlightPainter = new MyHighlightPainter(Color.green);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1051, 29);
		menuBar.setBackground(UIManager.getColor("CheckBox.background"));
		setJMenuBar(menuBar);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		JScrollPane sp1 = new JScrollPane(tabbedPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		getContentPane().add(sp1, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		tabbedPane.addTab("New tab ", null, new JPanel().add(new RowHighlighter()), null);
		tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);

		JMenu mnFile = new JMenu("File");
		mnFile.setForeground(new Color(0, 0, 0));
		menuBar.add(mnFile);

		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		mntmNew.setIcon(new ImageIcon("C:\\Users\\1026808\\eclipse-workspace\\Notepad\\src\\view"));
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.addTab("New tab", null, new JPanel().add(new RowHighlighter()), null);
				tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
				for (int i = 1; i < tabbedPane.getTabCount(); i++) {
					tabbedPane.getTabComponentAt(0);
					TabClose tabHeader = new TabClose(tabbedPane, i);
					tabHeader.apply();
				}
			}
		});
		mnFile.add(mntmNew);

		JMenuItem mntmOpen = new JMenuItem("open");
		mntmOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File("."));
				fileChooser.setAcceptAllFileFilterUsed(false);
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					String filePath = fileChooser.getSelectedFile().getPath();
					Path path = Paths.get(fileChooser.getSelectedFile().getPath());
					Path fileName = path.getFileName();
					try {
						FileInputStream fr = new FileInputStream(filePath);
						InputStreamReader isr = new InputStreamReader(fr, "UTF-8");
						BufferedReader reader = new BufferedReader(isr);
						buffer = new StringBuffer();

						String line = null;
						while ((line = reader.readLine()) != null) {

							buffer.append("\n" + line);
						}

						reader.close();
						RowHighlighter rh = new RowHighlighter();
						tabbedPane.addTab(fileName.toString(), null, new JPanel().add(rh), null);
						tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
						rh.setText(buffer.toString());
						for (int i = 1; i < tabbedPane.getTabCount(); i++) {
							TabClose tabHeader = new TabClose(tabbedPane, i);
							tabHeader.apply();
						}

					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}

		});

		mnFile.add(mntmOpen);

		JMenu mntmopenFolder = new JMenu("open Containing Folder");
		mnFile.add(mntmopenFolder);

		JMenuItem cmd = new JMenuItem("cmd");
		cmd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Runtime.getRuntime().exec(new String[] { "cmd", "/K", "Start" });
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		mntmopenFolder.add(cmd);

		JMenuItem mntmSaveAs = new JMenuItem("Save As");
		mntmSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SaveAs sa = new SaveAs();
				sa.saveAs((JEditorPane) tabbedPane.getSelectedComponent());
				dispose();
			}
		});
		mntmSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		mnFile.add(mntmSaveAs);

		JMenuItem mntmClose = new JMenuItem("Close");
		mntmClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
		mntmClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selectedIndex = tabbedPane.getSelectedIndex();
				while (selectedIndex > 0) {
					tabbedPane.remove(selectedIndex);
				}
			}
		});
		mnFile.add(mntmClose);

		JMenuItem mntmCloseAll = new JMenuItem("Close All");
		mntmCloseAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				while (tabbedPane.getTabCount() > 1) {
					tabbedPane.remove(1);
				}

			}
		});
		mnFile.add(mntmCloseAll);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);

		JMenu mnEdit = new JMenu("Edit");
		mnEdit.setForeground(new Color(0, 0, 0));
		menuBar.add(mnEdit);

		JMenuItem mntmUndo = new JMenuItem("Undo");
		mntmUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
		JEditorPane j = (JEditorPane) tabbedPane.getSelectedComponent();
		j.setFont(new Font("Cambria", 0, 15));
		final UndoManager editManager = new UndoManager();
		j.getDocument().addUndoableEditListener(new UndoableEditListener() {

			public void undoableEditHappened(UndoableEditEvent e) {
				editManager.addEdit(e.getEdit());
			}

		});
		mntmUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (editManager.canUndo()) {
					editManager.undo();
				}
			}
		});
		mnEdit.add(mntmUndo);

		JMenuItem mntmRedo = new JMenuItem("Redo");
		mntmRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK));
		mntmRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (editManager.canRedo()) {
					editManager.redo();
				}
			}
		});
		mnEdit.add(mntmRedo);

		JMenuItem mntmCut = new JMenuItem("Cut");
		mntmCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		mnEdit.add(mntmCut);
		mntmCut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JEditorPane j = (JEditorPane) tabbedPane.getSelectedComponent();
				j.cut();
			}
		});

		JMenuItem mntmCopy = new JMenuItem("Copy");
		mntmCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		mnEdit.add(mntmCopy);

		mntmCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JEditorPane j = (JEditorPane) tabbedPane.getSelectedComponent();
				j.copy();
			}
		});

		JMenuItem mntmPaste = new JMenuItem("Paste");
		mntmPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
		mnEdit.add(mntmPaste);
		mntmPaste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JEditorPane j = (JEditorPane) tabbedPane.getSelectedComponent();
				j.paste();
			}
		});

		JMenuItem mntmDelete = new JMenuItem("Delete");
		mntmDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JEditorPane j = (JEditorPane) tabbedPane.getSelectedComponent();
				j.replaceSelection(" ");
			}
		});
		mnEdit.add(mntmDelete);
		JMenuItem mntmSelect = new JMenuItem("SelectAll");
		mntmSelect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		mntmSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JEditorPane j = (JEditorPane) tabbedPane.getSelectedComponent();
				j.selectAll();
			}
		});
		mnEdit.add(mntmSelect);

		JMenu mnCase = new JMenu("Convert Case To");
		mnEdit.add(mnCase);

		JMenuItem mntmUpper = new JMenuItem("Upper Case");
		mntmUpper.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JEditorPane j = (JEditorPane) tabbedPane.getSelectedComponent();
				int start = j.getSelectionStart();
				int end = j.getSelectionEnd();
				String str = j.getText();
				String subString = str.substring(start, end);
				String text = subString.toUpperCase();

				String startText = j.getText().substring(0, start);
				String endText = j.getText().substring(end, j.getText().length());
				j.setText(startText + text + endText);

			}
		});
		mnCase.add(mntmUpper);

		JMenuItem mntmLower = new JMenuItem("Lower Case");
		mntmLower.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JEditorPane j = (JEditorPane) tabbedPane.getSelectedComponent();
				int start = j.getSelectionStart();
				int end = j.getSelectionEnd();
				String str = j.getText();
				String subString = str.substring(start, end);
				String text = subString.toLowerCase();

				String startText = j.getText().substring(0, start);
				String endText = j.getText().substring(end, j.getText().length());
				j.setText(startText + text + endText);

			}
		});
		mnCase.add(mntmLower);

		JMenu mnSearch = new JMenu("Search");
		mnEdit.setForeground(new Color(0, 0, 0));
		menuBar.add(mnSearch);

		JMenuItem mntmFind = new JMenuItem("Find");
		mntmFind.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
		mntmFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FindDialog dialog = new FindDialog();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		mnSearch.add(mntmFind);
		panel.setLayout(null);

		j.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					if (editManager.canUndo()) {
						mntmUndo.setEnabled(true);
						mntmCopy.setEnabled(true);
						mntmPaste.setEnabled(true);
						mntmSelect.setEnabled(true);
						mntmDelete.setEnabled(true);
					} else {
						mntmUndo.setEnabled(false);
						mntmCopy.setEnabled(false);
						mntmPaste.setEnabled(false);
						mntmSelect.setEnabled(false);
						mntmDelete.setEnabled(false);
					}
					if (editManager.canRedo()) {
						mntmRedo.setEnabled(true);
					} else {
						mntmRedo.setEnabled(false);
					}

					JPopupMenu popup = new JPopupMenu();
					popup.add(mntmUndo);
					popup.add(mntmRedo);
					popup.add(mntmCopy);
					popup.add(mntmPaste);
					popup.add(mntmSelect);
					popup.add(mntmDelete);
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});

	}

	public static void close() {
		JEditorPane editor = (JEditorPane) tabbedPane.getSelectedComponent();
		String text = editor.getText();
		if (text != null) {
			int count = tabbedPane.getSelectedIndex();
			tabbedPane.remove(count);
		}

	}

	public static void highlight(String searchResult) throws Exception {
		JEditorPane editor = (JEditorPane) tabbedPane.getSelectedComponent();
		removeHighlights(editor);

		try {
			Highlighter hilite = editor.getHighlighter();
			Document doc = editor.getDocument();
			String text = doc.getText(0, doc.getLength());
			if (!text.toLowerCase().contains(searchResult.toLowerCase())) {
				throw new Exception("String Not Found");
			}
			int pos = 0;
			while ((pos = text.toUpperCase().indexOf(searchResult.toUpperCase(), pos)) >= 0) {
				hilite.addHighlight(pos, pos + searchResult.length(), myHighlightPainter);
				pos += searchResult.length();
			}

		} catch (BadLocationException e) {
		}
	}

	public static void removeHighlights(JEditorPane editor) {
		Highlighter hilite = editor.getHighlighter();
		Highlighter.Highlight[] hilites = hilite.getHighlights();

		for (int i = 0; i < hilites.length; i++) {
			if (hilites[i].getPainter() instanceof MyHighlightPainter) {
				hilite.removeHighlight(hilites[i]);
			}
		}
	}

	class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter {

		public MyHighlightPainter(Color color) {
			super(color);
		}
	}

	public static JEditorPane getTabbedPane() {
		return (JEditorPane) tabbedPane.getSelectedComponent();
	}
}
