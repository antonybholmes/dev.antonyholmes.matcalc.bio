package edu.columbia.rdf.matcalc.toolbox.regions;

import javax.swing.Box;

import org.jebtk.bioinformatics.genomic.Genome;
import org.jebtk.core.settings.SettingsService;
import dev.antonyholmes.modern.UI;
import dev.antonyholmes.modern.Validation;
import dev.antonyholmes.modern.ValidationException;
import dev.antonyholmes.modern.button.ModernCheckSwitch;
import dev.antonyholmes.modern.button.ModernTwoStateWidget;
import dev.antonyholmes.modern.dialog.ModernDialogHelpWindow;
import dev.antonyholmes.modern.event.ModernClickEvent;
import dev.antonyholmes.modern.panel.HExpandBox;
import dev.antonyholmes.modern.panel.ModernPanel;
import dev.antonyholmes.modern.panel.VBox;
import dev.antonyholmes.modern.spinner.ModernCompactSpinner;
import dev.antonyholmes.modern.text.ModernAutoSizeLabel;
import dev.antonyholmes.modern.window.ModernWindow;
import dev.antonyholmes.modern.window.WindowWidgetFocusEvents;

import edu.columbia.rdf.matcalc.bio.AnnotationSidePanel;

/**
 * Control which conservation scores are shown.
 * 
 * @author Antony Holmes
 *
 */
public class StitchDialog extends ModernDialogHelpWindow {
  private static final long serialVersionUID = 1L;

  private ModernCompactSpinner mDistanceField = new ModernCompactSpinner(0, 100000, 2000,
      SettingsService.getInstance().getInt("regions.max-stitch-distance"), false);

  private ModernCompactSpinner mTss5pExt = new ModernCompactSpinner(0, 100000, 2000, 1000, false);

  private ModernCompactSpinner mTss3pExt = new ModernCompactSpinner(0, 100000, 2000, 1000, false);

  private ModernTwoStateWidget mCheckTssExclusion = new ModernCheckSwitch("TSS exclusion", true);

  private AnnotationSidePanel mGenomesPanel = new AnnotationSidePanel();

  public StitchDialog(ModernWindow parent) {
    super(parent, "org.matcalc.toolbox.bio.regions.stitch.help.url");

    setTitle("Stitch");

    createUi();

    setup();
  }

  private void setup() {
    setResizable(true);

    addWindowListener(new WindowWidgetFocusEvents(mOkButton));

    setSize(640, 400);

    UI.centerWindowToScreen(this);
  }

  private final void createUi() {
    // this.getWindowContentPanel().add(new JLabel("Change " +
    // getProductDetails().getProductName() + " settings", JLabel.LEFT),
    // BorderLayout.PAGE_START);
    Box box = VBox.create();

    sectionHeader("Distance", box);

    box.add(
        new HExpandBox("Maximum Distance", mDistanceField, ModernPanel.createHGap(), new ModernAutoSizeLabel("bp")));

    box.add(UI.createVGap(10));

    box.add(mCheckTssExclusion);

    box.add(ModernPanel.createVGap());

    Box box2 = VBox.create();

    box2.add(new HExpandBox("5' exclusion", mTss5pExt, ModernPanel.createHGap(), new ModernAutoSizeLabel("bp")));

    box2.add(ModernPanel.createVGap());

    box2.add(new HExpandBox("3' exclusion", mTss3pExt, ModernPanel.createHGap(), new ModernAutoSizeLabel("bp")));

    box.add(box2);

    setCard(box);

    getTabsPane().tabs().left().add("Genomes", mGenomesPanel, 200, 100, 400);
  }

  @Override
  public void clicked(ModernClickEvent e) {
    if (e.getMessage().equals(UI.BUTTON_OK)) {

      try {
        Validation.validateAsInt("Maximum Distance", mDistanceField.getText());
        Validation.validateAsInt("TSS 5' extension", mTss5pExt.getText());
        Validation.validateAsInt("TSS 3' extension", mTss3pExt.getText());
      } catch (ValidationException ex) {
        ex.printStackTrace();

        Validation.showValidationError(mParent, ex);

        return;
      }

      SettingsService.getInstance().update("regions.max-stitch-distance", mDistanceField.getIntValue());
    }

    super.clicked(e);
  }

  public int getDistance() {
    return mDistanceField.getIntValue();
  }

  public int getTss5p() {
    return mTss5pExt.getIntValue();
  }

  public int getTss3p() {
    return mTss3pExt.getIntValue();
  }

  public boolean getTssExclusion() {
    return mCheckTssExclusion.isSelected();
  }

  public Genome getGenome() {
    return mGenomesPanel.getGenome();
  }
}
