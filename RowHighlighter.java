package notepadview;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JEditorPane;
import javax.swing.text.BadLocationException;

class RowHighlighter extends JEditorPane
{
public RowHighlighter() {
    setOpaque(false);
  }
  @Override
  protected void paintComponent(Graphics g) {
    g.setColor(getBackground());
    g.fillRect(0, 0, getWidth(), getHeight());
    try {
      Rectangle rect = modelToView(getCaretPosition());
      if (rect != null) {
        g.setColor(Color.pink);
        g.fillRect(0, rect.y, getWidth(), rect.height);
      }
    } catch (BadLocationException e) {
      System.out.println(e);
    }
    super.paintComponent(g);
  }

  @Override
  public void repaint(long tm, int x, int y, int width, int height) {
    super.repaint(tm, 0, 0, getWidth(), getHeight());
  }
  
}




