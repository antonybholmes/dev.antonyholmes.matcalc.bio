package edu.columbia.rdf.matcalc.toolbox.motifs.seqlogo;

import org.jebtk.bioinformatics.genomic.SequenceService;
import dev.antonyholmes.modern.UI;
import dev.antonyholmes.modern.button.ModernButton;
import dev.antonyholmes.modern.dialog.ModernDialogHelpWindow;
import dev.antonyholmes.modern.dialog.ModernDialogStatus;
import dev.antonyholmes.modern.dialog.ModernMessageDialog;
import dev.antonyholmes.modern.event.ModernClickEvent;
import dev.antonyholmes.modern.event.ModernClickListener;
import dev.antonyholmes.modern.graphics.color.ColorSwatchButton;
import dev.antonyholmes.modern.panel.HExpandBox;
import dev.antonyholmes.modern.panel.VBox;
import dev.antonyholmes.modern.ribbon.RibbonButton;
import dev.antonyholmes.modern.window.ModernWindow;
import dev.antonyholmes.modern.window.WindowWidgetFocusEvents;

/**
 * Options for motif searching.
 * 
 * @author Antony Holmes
 *
 */
public class BaseDialog extends ModernDialogHelpWindow {
  private static final long serialVersionUID = 1L;

  private ColorSwatchButton mColorAButton;

  private ColorSwatchButton mColorCButton;

  private ColorSwatchButton mColorGButton;

  private ColorSwatchButton mColorTButton;

  private ColorSwatchButton mColorNButton;

  public BaseDialog(ModernWindow parent) {
    super(parent, "org.matcalc.toolbox.bio.motifs.seqlogo.dna-bases.help.url");

    setTitle("DNA Bases");

    setup();

    createUi();
  }

  private void setup() {
    addWindowListener(new WindowWidgetFocusEvents(mOkButton));

    setSize(400, 380);

    UI.centerWindowToScreen(this);
  }

  private final void createUi() {
    mColorAButton = new ColorSwatchButton(mParent, SequenceService.getInstance().getBaseAColor());

    mColorCButton = new ColorSwatchButton(mParent, SequenceService.getInstance().getBaseCColor());

    mColorGButton = new ColorSwatchButton(mParent, SequenceService.getInstance().getBaseGColor());

    mColorTButton = new ColorSwatchButton(mParent, SequenceService.getInstance().getBaseTColor());

    mColorNButton = new ColorSwatchButton(mParent, SequenceService.getInstance().getBaseNColor());

    VBox box = VBox.create();

    // sectionHeader("Base Color", box);

    box.add(new HExpandBox("A", mColorAButton));
    box.add(UI.createVGap(5));
    box.add(new HExpandBox("C", mColorCButton));
    box.add(UI.createVGap(5));
    box.add(new HExpandBox("G", mColorGButton));
    box.add(UI.createVGap(5));
    box.add(new HExpandBox("T", mColorTButton));
    box.add(UI.createVGap(5));
    box.add(new HExpandBox("N", mColorNButton));
    box.add(UI.createVGap(40));

    ModernButton button = new RibbonButton("Defaults");
    button.addClickListener(new ModernClickListener() {

      @Override
      public void clicked(ModernClickEvent e) {
        resetToDefaults();
      }
    });

    box.add(button);

    setCard(box);
  }

  /**
   * Reset the colors to their defaults.
   */
  private void resetToDefaults() {
    ModernDialogStatus status = ModernMessageDialog.createOkCancelWarningDialog(mParent,
        "The base colors will be reset to their default values.");

    if (status == ModernDialogStatus.OK) {
      SequenceService.getInstance().reset();

      setColors();
    }
  }

  /**
   * Change the button color.
   */
  private void setColors() {
    mColorAButton.setSelectedColor(SequenceService.getInstance().getBaseAColor());
    mColorCButton.setSelectedColor(SequenceService.getInstance().getBaseCColor());
    mColorGButton.setSelectedColor(SequenceService.getInstance().getBaseGColor());
    mColorTButton.setSelectedColor(SequenceService.getInstance().getBaseTColor());
    mColorNButton.setSelectedColor(SequenceService.getInstance().getBaseNColor());
  }

  @Override
  public final void clicked(ModernClickEvent e) {
    if (e.getMessage().equals(UI.BUTTON_OK)) {
      SequenceService.getInstance().setBaseAColor(mColorAButton.getSelectedColor());
      SequenceService.getInstance().setBaseCColor(mColorCButton.getSelectedColor());
      SequenceService.getInstance().setBaseGColor(mColorGButton.getSelectedColor());
      SequenceService.getInstance().setBaseTColor(mColorTButton.getSelectedColor());
      SequenceService.getInstance().setBaseNColor(mColorNButton.getSelectedColor());
    }

    super.clicked(e);
  }
}
