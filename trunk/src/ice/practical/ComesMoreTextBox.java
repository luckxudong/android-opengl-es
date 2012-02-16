package ice.practical;

import ice.node.OverlayParent;

import javax.microedition.khronos.opengles.GL11;

/**
 * User: Jason
 * Date: 12-2-4
 * Time: 下午6:17
 */
public class ComesMoreTextBox extends OverlayParent<ComesMoreText> {

    public ComesMoreTextBox(int maxWidth, int eachLineHeight, long lineDuring) {
        this.width = maxWidth;
        this.eachLineHeight = eachLineHeight;
        this.lineDuring = lineDuring;
    }

    public void setTexts(String[] lines) {
        setTexts(lines, eachLineHeight / 4);
    }


    public void setTexts(String[] lines, int linesMargin) {
        ComesMoreText[] textLines = new ComesMoreText[lines.length];

        for (int i = 0; i < lines.length; i++) {
            textLines[i] = new ComesMoreText((int) width, eachLineHeight, lineDuring);
            textLines[i].setText(lines[i]);
            textLines[i].setPos(0, getHeight() - i * (linesMargin + eachLineHeight));
            textLines[i].setVisible(false);
        }

        addChildren(textLines);

        activeLineIndex = 0;
    }

    @Override
    protected void onDraw(GL11 gl) {

        int size = children.size();

        if (activeLineIndex < size) {
            for (int i = 0; i < size; i++) {

                if (i == activeLineIndex) {

                    ComesMoreText comesMoreText = children.get(i);

                    if (!comesMoreText.isVisible())
                        comesMoreText.setVisible(true);

                    if (comesMoreText.isFinished())
                        activeLineIndex++;

                    break;
                }


            }

        }

        super.onDraw(gl);
    }

    private long lineDuring;

    private int eachLineHeight;

    private int activeLineIndex;
}
