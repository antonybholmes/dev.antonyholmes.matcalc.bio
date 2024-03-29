package dev.antonyholmes.matcalc.bio.toolbox.probeslocations;

import org.jebtk.math.matrix.DataFrame;
import dev.antonyholmes.modern.ModernWidget;
import dev.antonyholmes.modern.UI;
import dev.antonyholmes.modern.combobox.ModernComboBox;
import dev.antonyholmes.modern.dialog.ModernDialogTaskWindow;
import dev.antonyholmes.modern.panel.MatrixPanel;
import dev.antonyholmes.modern.text.ModernAutoSizeLabel;
import dev.antonyholmes.modern.window.ModernWindow;

import dev.antonyholmes.matcalc.MatrixRowAnnotationCombo;

public class ProbeLocationsDialog extends ModernDialogTaskWindow {
  private static final long serialVersionUID = 1L;

  private DataFrame mMatrix;

  private MatrixRowAnnotationCombo mHeaderCombo;

  private ModernComboBox mAnnotationCombo;

  public ProbeLocationsDialog(ModernWindow parent, DataFrame matrix) {
    super(parent);

    setTitle("Probe Locations");

    mMatrix = matrix;

    setup();

    createUi();
  }

  private void setup() {
    setSize(360, 240);

    UI.centerWindowToScreen(this);
  }

  private final void createUi() {
    // this.getWindowContentPanel().add(new JLabel("Change " +
    // getProductDetails().getProductName() + " settings", JLabel.LEFT),
    // BorderLayout.PAGE_START);

    int[] rows = { ModernWidget.WIDGET_HEIGHT };
    int[] cols = { 100, 200 };

    MatrixPanel panel = new MatrixPanel(rows, cols, ModernWidget.PADDING, ModernWidget.PADDING);

    panel.add(new ModernAutoSizeLabel("ID"));

    mHeaderCombo = new MatrixRowAnnotationCombo(mMatrix);

    panel.add(mHeaderCombo);

    panel.add(new ModernAutoSizeLabel("Annotation"));

    mAnnotationCombo = new ProbeLocationsCombo();

    panel.add(mAnnotationCombo);

    setContent(panel);
  }

  public String getRowAnnotation() {
    return mHeaderCombo.getText();
  }

  public String getAnnotation() {
    return mAnnotationCombo.getText();
  }
}
