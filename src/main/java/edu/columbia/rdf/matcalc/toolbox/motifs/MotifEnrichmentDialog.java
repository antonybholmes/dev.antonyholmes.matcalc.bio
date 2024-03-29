package dev.antonyholmes.matcalc.toolbox.motifs;

import java.util.List;

import org.jebtk.bioinformatics.motifs.Motif;
import org.jebtk.bioinformatics.ui.motifs.MotifModel;
import org.jebtk.bioinformatics.ui.motifs.MotifsTreePanel;
import org.jebtk.bioinformatics.ui.motifs.SeqLogoCanvas;
import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.settings.SettingsService;
import org.jebtk.graphplot.figure.Figure;
import org.jebtk.graphplot.figure.FigurePanel;
import dev.antonyholmes.modern.ModernWidget;
import dev.antonyholmes.modern.UI;
import dev.antonyholmes.modern.Validation;
import dev.antonyholmes.modern.ValidationException;
import dev.antonyholmes.modern.button.ModernButtonGroup;
import dev.antonyholmes.modern.button.ModernCheckBox;
import dev.antonyholmes.modern.button.ModernRadioButton;
import dev.antonyholmes.modern.combobox.ModernComboBox;
import dev.antonyholmes.modern.dialog.ModernDialogHelpWindow;
import dev.antonyholmes.modern.event.ModernClickEvent;
import dev.antonyholmes.modern.event.ModernSelectionListener;
import dev.antonyholmes.modern.panel.HExpandBox;
import dev.antonyholmes.modern.panel.VBox;
import dev.antonyholmes.modern.scrollpane.ModernScrollPane;
import dev.antonyholmes.modern.scrollpane.ScrollBarPolicy;
import dev.antonyholmes.modern.spinner.ModernCompactSpinner;
import dev.antonyholmes.modern.window.ModernWindow;
import dev.antonyholmes.modern.window.WindowService;
import dev.antonyholmes.modern.window.WindowWidgetFocusEvents;

import dev.antonyholmes.matcalc.MainMatCalcWindow;
import dev.antonyholmes.matcalc.MatCalcWindowCombo;

/**
 * Options for motif searching.
 * 
 * @author Antony Holmes
 *
 */
public class MotifEnrichmentDialog extends ModernDialogHelpWindow {
  private static final long serialVersionUID = 1L;

  // private static final Dimension MOTIF_CHECKBOX_SIZE =
  // new Dimension(200, ModernWidget.WIDGET_HEIGHT);

  private ModernRadioButton mCheckVs = new ModernRadioButton("Foreground vs Background", true,
      ModernWidget.EXTRA_LARGE_SIZE);

  private ModernRadioButton mCheckRandomBackground = new ModernRadioButton("Random Background");

  // private ModernTextField mExt5pField =
  // new
  // ModernNumericalTextField(SettingsService.getInstance().getInt("motifs.search.5p-extension"));

  // private ModernTextField mExt3pField =
  // new
  // ModernNumericalTextField(SettingsService.getInstance().getInt("motifs.search.3p-extension"));

  private ModernCompactSpinner mSensField = new ModernCompactSpinner(0, 1,
      SettingsService.getInstance().getDouble("motifs.minimum-sensitivity"), 0.1);

  private ModernCompactSpinner mSpecField = new ModernCompactSpinner(0, 1,
      SettingsService.getInstance().getDouble("motifs.minimum-specificity"), 0.1);

  private ModernCompactSpinner mThresholdField = new ModernCompactSpinner(0, 1,
      SettingsService.getInstance().getDouble("motifs.motif-threshold"), 0.1);

  private ModernCheckBox mCheckMainVariants = new ModernCheckBox("Main gene variants");

  private ModernCheckBox mCheckPeakWidths = new ModernCheckBox("Peak widths only");

  // private ModernCheckBox mCheckAll = new ModernCheckBox("Select All");

  // private Map<ModernCheckBox, Motif> mMotifMap =
  // new HashMap<ModernCheckBox, Motif>();

  // private List<Motif> mMotifs;

  private MotifsTreePanel mMotifsPanel;

  private MotifModel mMotifsModel = new MotifModel();

  private SeqLogoCanvas mCanvasPanel;

  // private GroupComboBox mForegroundCombo;

  // private GroupComboBox mBackgroundCombo;

  // private ModernComboBox mForegroundCombo = new MatCalcWindowCombo();

  private ModernComboBox mBackgroundCombo = new MatCalcWindowCombo();

  /*
   * private class CheckAllEvents implements ModernClickListener {
   * 
   * @Override public void clicked(ModernClickEvent e) { for (ModernCheckBox
   * checkBox : mMotifMap.keySet()) {
   * checkBox.setSelected(mCheckAll.isSelected()); } }
   * 
   * }
   */

  private class MotifEvents implements ModernSelectionListener {

    @Override
    public void selectionAdded(ChangeEvent e) {
      Motif motif = mMotifsModel.getSelected();

      if (motif != null) {
        mCanvasPanel.setMotif(motif);
      }
    }

    @Override
    public void selectionRemoved(ChangeEvent e) {
      // TODO Auto-generated method stub

    }

  }

  public MotifEnrichmentDialog(ModernWindow parent) {
    super(parent, "org.matcalc.toolbox.bio.motifs.enrichment.help.url");

    setTitle("Motif Enrichment");

    setup();

    createUi();
  }

  private void setup() {
    addWindowListener(new WindowWidgetFocusEvents(mOkButton));

    mMotifsModel.addSelectionListener(new MotifEvents());

    ModernButtonGroup group = new ModernButtonGroup();

    group.add(mCheckVs);
    group.add(mCheckRandomBackground);

    setSize(900, 760);

    UI.centerWindowToScreen(this);
  }

  private final void createUi() {
    // this.getWindowContentPanel().add(new JLabel("Change " +
    // getProductDetails().getProductName() + " settings", JLabel.LEFT),
    // BorderLayout.PAGE_START);

    VBox box = VBox.create();

    sectionHeader("Motifs", box);

    mMotifsPanel = new MotifsTreePanel(mParent, mMotifsModel);

    UI.setSize(mMotifsPanel, 800, 220);

    box.add(mMotifsPanel);

    box.add(UI.createVGap(5));

    mCanvasPanel = new SeqLogoCanvas();

    ModernScrollPane scrollPane = new ModernScrollPane(
        new FigurePanel(new Figure("Seq Logo Canvas").addSubFigure(mCanvasPanel)))
            .setVerticalScrollBarPolicy(ScrollBarPolicy.NEVER);

    UI.setSize(scrollPane, 800, 180);

    box.add(scrollPane);

    /*
     * Box box2 = Box.createVerticalBox();
     * 
     * box2.add(mCheckAll);
     * 
     * 
     * for (Motif m : mMotifs) { ModernCheckBox checkBox = new
     * ModernCheckBox(m.getName() + " (" + m.getId() + ")");
     * 
     * Ui.setSize(checkBox, MOTIF_CHECKBOX_SIZE);
     * 
     * box2.add(checkBox);
     * 
     * mMotifMap.put(checkBox, m); }
     * 
     * ModernScrollPane scrollPane = new ModernScrollPane(box2);
     * 
     * Ui.setSize(scrollPane, new Dimension(500, 200));
     * 
     * box.add(scrollPane);
     * 
     */

    midSectionHeader("Search Parameters", box);

    // Box box2 = HBox.create();
    // box2.add(new ModernAutoSizeLabel("Mode", 200));
    // box2.add(mCheckVs);
    // box2.add(mCheckRandomBackground);
    // box.add(box2);

    // box.add(UI.createVGap(5));

    box.add(new HExpandBox("Background", mBackgroundCombo));
    box.add(UI.createVGap(5));
    box.add(new HExpandBox("Motif threshold", mThresholdField));
    box.add(UI.createVGap(5));
    box.add(new HExpandBox("Minimum sensitivity", mSensField));
    box.add(UI.createVGap(5));
    box.add(new HExpandBox("Minimum specificity", mSpecField));

    // box.add(UI.createVGap(5));
    // box.add(mCheckMainVariants);
    // box.add(UI.createVGap(5));
    // box.add(mCheckPeakWidths);

    setCard(box);

    mBackgroundCombo.setSelectedIndex(1);
  }

  @Override
  public final void clicked(ModernClickEvent e) {
    if (e.getMessage().equals(UI.BUTTON_OK)) {
      try {
        // Validation.validateAsInt("5' extension", mExt5pField.getText());
        // Validation.validateAsInt("3' extension", mExt3pField.getText());
        Validation.validateAsDouble("Minimum sensitivity", mSensField.getText(), 0, 1);
        Validation.validateAsDouble("Minimum specificity", mSpecField.getText(), 0, 1);
        Validation.validateAsDouble("Motif threshold", mThresholdField.getText(), 0, 1);
        Validation.validateSelection("motifs", getMotifs());

        SettingsService.getInstance().update("motifs.minimum-sensitivity", mSensField.getText());
        SettingsService.getInstance().update("motifs.minimum-specificity", mSpecField.getText());
        SettingsService.getInstance().update("motifs.motif-threshold", mThresholdField.getText());
        // SettingsService.getInstance().update("motifs.search.5p-extension",
        // mExt5pField.getText());
        // SettingsService.getInstance().update("motifs.search.3p-extension",
        // mExt5pField.getText());

      } catch (ValidationException ex) {
        Validation.showValidationError(mParent, ex);

        return;
      }
    }

    super.clicked(e);
  }

  public List<Motif> getMotifs() {
    return mMotifsPanel.getSelectedMotifs();

    /*
     * List<Motif> ret = new ArrayList<Motif>();
     * 
     * for (ModernCheckBox checkBox : mMotifMap.keySet()) { if
     * (checkBox.isSelected()) { ret.add(mMotifMap.get(checkBox)); } }
     * 
     * return ret;
     */
  }

  // public int getExt5p() {
  // return Integer.parseInt(mExt5pField.getText());
  // }

  public double getMinSensitivity() {
    return mSensField.getValue();
  }

  public double getMinSpecificity() {
    return mSpecField.getValue();
  }

  // public int getExt3p() {
  // return Integer.parseInt(mExt3pField.getText());
  // }

  public double getThreshold() {
    return mThresholdField.getValue();
  }

  /*
   * public boolean getUseMainVariants() { return mCheckMainVariants.isSelected();
   * }
   */

  // public MainMatCalcWindow getForegroundGroup() {
  // return
  // (MainMatCalcWindow)WindowService.getInstance().findByName(mForegroundCombo.getText());
  // }

  public MainMatCalcWindow getBackgroundGroup() {
    return (MainMatCalcWindow) WindowService.getInstance().findByName(mBackgroundCombo.getText());
  }

  /*
   * public boolean getUsePeakWidths() { return mCheckPeakWidths.isSelected(); }
   */

  /**
   * Whether to use foreground vs background searching or random background.
   * 
   * @return
   */
  public boolean useForeVsBackMode() {
    return mCheckVs.isSelected();
  }
}
