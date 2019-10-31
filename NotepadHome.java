package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;

import javax.swing.AbstractAction;
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

import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;

public class NotepadHome extends JFrame {

	// private static JEditorPane editorPane;
	private StringBuffer buffer;
	private static JTextArea textArea;
	private static JTextArea lines;
	private static NotepadHome frame;
	private static JTabbedPane tabbedPane;
	private static Highlighter.HighlightPainter myHighlightPainter;

	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the frame.
	 */
	public NotepadHome() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1067, 804);
		this.setTitle("Notepad++");

		myHighlightPainter = new MyHighlightPainter(Color.yellow);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1051, 29);
		menuBar.setBackground(UIManager.getColor("CheckBox.background"));
		setJMenuBar(menuBar);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		tabbedPane.addTab("New tab 1", null, new JPanel().add(new RowHighlighter()), null);
		tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);

		JMenu mnFile = new JMenu("File");
		mnFile.setForeground(new Color(0, 0, 0));
		menuBar.add(mnFile);

		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				tabbedPane.addTab("New tab", null, new JPanel().add(new RowHighlighter()), null);
				tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
				for (int i = 0; i < tabbedPane.getTabCount(); i++) {
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
						for (int i = 0; i < tabbedPane.getTabCount(); i++) {
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
		JMenuItem explorer = new JMenuItem("Explorer");
		mntmopenFolder.add(explorer);
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
			public void actionPerformed(ActionEvent arg0) {

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

		JMenu mnSearch = new JMenu("Search");
		mnEdit.setForeground(new Color(0, 0, 0));
		menuBar.add(mnSearch);

		JMenuItem mntmFind = new JMenuItem("Find");
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
					} else {
						mntmUndo.setEnabled(false);
						mntmCopy.setEnabled(false);
						mntmPaste.setEnabled(false);
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
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});

	}

	public static void highlight(String searchResult) {
		JEditorPane editor = (JEditorPane) tabbedPane.getSelectedComponent();
		removeHighlights(editor);

		try {
			Highlighter hilite = editor.getHighlighter();
			Document doc = editor.getDocument();
			String text = doc.getText(0, doc.getLength());
			if (!text.toLowerCase().contains(searchResult.toLowerCase())) {
				FindDialog dialog = new FindDialog();
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
}
