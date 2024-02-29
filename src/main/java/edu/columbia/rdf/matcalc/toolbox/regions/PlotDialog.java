package edu.columbia.rdf.matcalc.toolbox.regions;

import javax.swing.Box;

import org.jebtk.bioinformatics.genomic.Genome;
import dev.antonyholmes.modern.ModernWidget;
import dev.antonyholmes.modern.UI;
import dev.antonyholmes.modern.combobox.ModernComboBox;
import dev.antonyholmes.modern.dialog.ModernDialogTaskWindow;
import dev.antonyholmes.modern.panel.HExpandBox;
import dev.antonyholmes.modern.panel.HSpacedBox;
import dev.antonyholmes.modern.panel.VBox;
import dev.antonyholmes.modern.spinner.ModernCompactSpinner;
import dev.antonyholmes.modern.text.ModernAutoSizeLabel;
import dev.antonyholmes.modern.window.ModernWindow;
import dev.antonyholmes.modern.window.WindowWidgetFocusEvents;

/**
 * Control which conservation scores are shown.
 * 
 * @author Antony Holmes
 *
 */
public class PlotDialog extends ModernDialogTaskWindow {
  private static final long serialVersionUID = 1L;

  private ModernCompactSpinner mFieldStart = new ModernCompactSpinner(-100, -1, -4, 1, false);

  private ModernCompactSpinner mFieldEnd = new ModernCompactSpinner(1, 100, 4, 1, false);

  private ModernCompactSpinner mFieldBin = new ModernCompactSpinner(1, 1000, 100, 1, false);

  private ModernComboBox mUnitsCombo = new UnitsComboBox();

  private ModernComboBox mBinUnitsCombo = new UnitsComboBox();

  public PlotDialog(ModernWindow parent) {
    super(parent);

    setTitle("Plot Options");

    createUi();

    setup();
  }

  private void setup() {
    addWindowListener(new WindowWidgetFocusEvents(mOkButton));

    setSize(480, 240);

    UI.centerWindowToScreen(this);
  }

  private final void createUi() {
    Box box = VBox.create();

    box.add(
        new HExpandBox("Range", new HSpacedBox(mFieldStart, new ModernAutoSizeLabel("to"), mFieldEnd, mUnitsCombo)));

    box.add(UI.createVGap(10));

    box.add(new HExpandBox("Bins", new HSpacedBox(mFieldBin, mBinUnitsCombo)));

    setCard(box);

    mUnitsCombo.setSelectedIndex(1);

    UI.setSize(mUnitsCombo, ModernWidget.SMALL_SIZE);
    UI.setSize(mBinUnitsCombo, ModernWidget.SMALL_SIZE);
  }

  public double getStart() {
    return mFieldStart.getValue();
  }

  public double getEnd() {
    return mFieldEnd.getValue();
  }

  public int getUnits() {
    switch (mUnitsCombo.getSelectedIndex()) {
    case 2:
      return 1000000;
    case 1:
      return 1000;
    default:
      return 1;
    }
  }

  public double getBinSize() {
    return mFieldBin.getValue();
  }

  public int getBinUnits() {
    switch (mBinUnitsCombo.getSelectedIndex()) {
    case 2:
      return 1000000;
    case 1:
      return 1000;
    default:
      return 1;
    }
  }

  public Genome getGenome() {
    return Genome.HG19;
  }
}
