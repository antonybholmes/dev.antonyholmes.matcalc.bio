package dev.antonyholmes.matcalc.toolbox.motifs;

import javax.swing.Box;

import org.jebtk.bioinformatics.Bio;
import dev.antonyholmes.modern.UI;
import dev.antonyholmes.modern.button.ModernButtonGroup;
import dev.antonyholmes.modern.button.ModernRadioButton;
import dev.antonyholmes.modern.dialog.ModernDialogTaskWindow;
import dev.antonyholmes.modern.panel.VBox;
import dev.antonyholmes.modern.window.ModernWindow;
import dev.antonyholmes.modern.window.WindowWidgetFocusEvents;

/**
 * Control which conservation scores are shown.
 * 
 * @author Antony Holmes
 *
 */
public class ExportMotifFastaDialog extends ModernDialogTaskWindow {
  private static final long serialVersionUID = 1L;

  private ModernRadioButton mCheckMotifs = new ModernRadioButton(Bio.ASSET_MOTIFS, true);

  private ModernRadioButton mCheckRegions = new ModernRadioButton("Regions");

  public ExportMotifFastaDialog(ModernWindow parent) {
    super(parent);

    setTitle("Export Motifs");

    setup();

    createUi();
  }

  private void setup() {
    addWindowListener(new WindowWidgetFocusEvents(mOkButton));

    setSize(320, 240);

    UI.centerWindowToScreen(this);
  }

  private final void createUi() {
    // this.getWindowContentPanel().add(new JLabel("Change " +
    // getProductDetails().getProductName() + " settings", JLabel.LEFT),
    // BorderLayout.PAGE_START);

    Box box = VBox.create();

    box.add(mCheckMotifs);

    box.add(UI.createVGap(10));

    box.add(mCheckRegions);

    setCard(box);

    ModernButtonGroup group = new ModernButtonGroup();

    group.add(mCheckMotifs);
    group.add(mCheckRegions);
  }

  public boolean getMotifWise() {
    return mCheckMotifs.isSelected();
  }
}
