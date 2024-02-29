package edu.columbia.rdf.matcalc.toolbox.probes;

import java.nio.file.Path;

import javax.swing.Box;

import dev.antonyholmes.modern.ModernWidget;
import dev.antonyholmes.modern.UI;
import dev.antonyholmes.modern.dialog.ModernDialogHelpWindow;
import dev.antonyholmes.modern.panel.HExpandBox;
import dev.antonyholmes.modern.panel.VBox;
import dev.antonyholmes.modern.window.ModernWindow;
import dev.antonyholmes.modern.window.WindowWidgetFocusEvents;

public class ProbesDialog extends ModernDialogHelpWindow {
  private static final long serialVersionUID = 1L;

  private ArrayCombo mArrayCombo = new ArrayCombo();

  public ProbesDialog(ModernWindow parent) {
    super(parent, "probes.help.url");

    setTitle("Probes");

    setup();

    createUi();
  }

  private void setup() {
    addWindowListener(new WindowWidgetFocusEvents(mOkButton));

    setSize(480, 200);

    UI.centerWindowToScreen(this);
  }

  private final void createUi() {
    Box box = VBox.create();

    box.add(new HExpandBox("Array", mArrayCombo));

    UI.setSize(mArrayCombo, ModernWidget.VERY_LARGE_SIZE);

    setContent(box);
  }

  public Path getFile() {
    return mArrayCombo.getFile();
  }
}
