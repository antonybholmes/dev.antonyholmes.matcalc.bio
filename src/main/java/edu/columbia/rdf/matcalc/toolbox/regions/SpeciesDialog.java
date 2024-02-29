package edu.columbia.rdf.matcalc.toolbox.regions;

import javax.swing.Box;

import dev.antonyholmes.modern.UI;
import dev.antonyholmes.modern.button.ModernButtonGroup;
import dev.antonyholmes.modern.button.ModernRadioButton;
import dev.antonyholmes.modern.dialog.ModernDialogTaskWindow;
import dev.antonyholmes.modern.event.ModernClickListener;
import dev.antonyholmes.modern.panel.VBox;
import dev.antonyholmes.modern.text.ModernDialogHeadingLabel;
import dev.antonyholmes.modern.window.ModernWindow;
import dev.antonyholmes.modern.window.WindowWidgetFocusEvents;

/**
 * Control which conservation scores are shown.
 * 
 * @author Antony Holmes
 *
 */
public class SpeciesDialog extends ModernDialogTaskWindow implements ModernClickListener {
  private static final long serialVersionUID = 1L;

  private ModernRadioButton mCheckHuman = new ModernRadioButton("Human");

  private ModernRadioButton mCheckMouse = new ModernRadioButton("Mouse");

  public SpeciesDialog(ModernWindow parent, String name) {
    super(parent);

    setTitle(name);

    createUi();

    setup();
  }

  private void setup() {
    new ModernButtonGroup(mCheckHuman, mCheckMouse);

    mCheckHuman.doClick();

    addWindowListener(new WindowWidgetFocusEvents(mOkButton));

    setSize(360, 220);

    UI.centerWindowToScreen(this);
  }

  private final void createUi() {
    Box box = VBox.create();

    box.add(new ModernDialogHeadingLabel("Species"));
    box.add(UI.createVGap(10));
    box.add(mCheckHuman);
    box.add(UI.createVGap(5));
    box.add(mCheckMouse);

    setCard(box);
  }

  public boolean getHuman() {
    return mCheckHuman.isSelected();
  }
}
