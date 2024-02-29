package edu.columbia.rdf.matcalc.toolbox.mutations;

import java.util.List;

import javax.swing.Box;

import org.jebtk.bioinformatics.genomic.Mutation;
import dev.antonyholmes.modern.UI;
import dev.antonyholmes.modern.button.CheckBox;
import dev.antonyholmes.modern.button.ModernCheckSwitch;
import dev.antonyholmes.modern.dialog.ModernDialogHelpWindow;
import dev.antonyholmes.modern.panel.HExpandBox;
import dev.antonyholmes.modern.panel.VBox;
import dev.antonyholmes.modern.spinner.ModernCompactSpinner;
import dev.antonyholmes.modern.window.ModernWindow;
import dev.antonyholmes.modern.window.WindowWidgetFocusEvents;

public class MutationsDialog extends ModernDialogHelpWindow {
  private static final long serialVersionUID = 1L;

  private CheckBox mCheckTrim5p = new ModernCheckSwitch("Trim 5'");

  private CheckBox mCheckAll = new ModernCheckSwitch("All mutations", true);

  private CheckBox mCheckWild = new ModernCheckSwitch("Wild type", true);

  private ModernCompactSpinner m5pField = new ModernCompactSpinner(0, 10000,
      2000, 1, false);

  private CheckBox mCheckTrim3p = new ModernCheckSwitch("Trim 3'");

  private ModernCompactSpinner m3pField = new ModernCompactSpinner(0, 10000,
      2000, 1, false);

  public MutationsDialog(ModernWindow parent, List<Mutation> mutations) {
    super(parent, "mutations.help.url");

    setTitle("Mutations");

    setup();

    createUi();
  }

  private void setup() {
    addWindowListener(new WindowWidgetFocusEvents(mOkButton));

    setSize(480, 260);

    UI.centerWindowToScreen(this);
  }

  private final void createUi() {
    Box box = VBox.create();

    box.add(new HExpandBox(mCheckTrim5p, m5pField));
    box.add(UI.createVGap(5));
    box.add(new HExpandBox(mCheckTrim3p, m3pField));
    box.add(UI.createVGap(5));
    box.add(mCheckAll);
    box.add(UI.createVGap(5));
    box.add(mCheckWild);
    // UI.setSize(mArrayCombo, ModernWidget.VERY_LARGE_SIZE);

    setCard(box);
  }

  public boolean all() {
    return mCheckAll.isSelected();
  }

  public boolean wild() {
    return mCheckWild.isSelected();
  }

  public int getTrim5p() {
    return mCheckTrim5p.isSelected() ? m5pField.getIntValue() : 0;
  }

  public int getTrim3p() {
    return mCheckTrim3p.isSelected() ? m3pField.getIntValue() : 0;
  }
}
