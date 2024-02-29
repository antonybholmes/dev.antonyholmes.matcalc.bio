package dev.antonyholmes.matcalc.toolbox.genes.info;

import org.jebtk.math.matrix.DataFrame;
import dev.antonyholmes.modern.ModernWidget;
import dev.antonyholmes.modern.UI;
import dev.antonyholmes.modern.combobox.ModernComboBox;
import dev.antonyholmes.modern.dialog.ModernDialogTaskWindow;
import dev.antonyholmes.modern.panel.MatrixPanel;
import dev.antonyholmes.modern.text.ModernAutoSizeLabel;
import dev.antonyholmes.modern.window.ModernWindow;

import dev.antonyholmes.matcalc.toolbox.ColumnsCombo;

public class GeneInfoDialog extends ModernDialogTaskWindow {
  private static final long serialVersionUID = 1L;

  private DataFrame mMatrix;

  private ModernComboBox mColumnsCombo;

  public GeneInfoDialog(ModernWindow parent, DataFrame matrix) {
    super(parent);

    setTitle("Gene Info");

    mMatrix = matrix;

    setup();

    createUi();
  }

  private void setup() {
    setSize(600, 200);

    UI.centerWindowToScreen(this);
  }

  private final void createUi() {
    // this.getWindowContentPanel().add(new JLabel("Change " +
    // getProductDetails().getProductName() + " settings", JLabel.LEFT),
    // BorderLayout.PAGE_START);

    int[] rows = { ModernWidget.WIDGET_HEIGHT };
    int[] cols = { 100, 400 };

    MatrixPanel panel = new MatrixPanel(rows, cols, ModernWidget.PADDING,
        ModernWidget.PADDING);

    panel.add(new ModernAutoSizeLabel("Column"));

    mColumnsCombo = new ColumnsCombo(mMatrix);
    panel.add(mColumnsCombo);

    setCard(panel);

  }

  public int getColumnIndex() {
    return mColumnsCombo.getSelectedIndex();
  }
}
