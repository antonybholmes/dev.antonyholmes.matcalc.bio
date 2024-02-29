package edu.columbia.rdf.matcalc.toolbox.dna;

import java.nio.file.Path;

import org.jebtk.bioinformatics.genomic.Genome;
import dev.antonyholmes.modern.ModernComponent;
import dev.antonyholmes.modern.ModernWidget;
import dev.antonyholmes.modern.UI;
import dev.antonyholmes.modern.dialog.ModernDialogHelpWindow;
import dev.antonyholmes.modern.event.ModernClickEvent;
import dev.antonyholmes.modern.io.ChooseFilePanel;
import dev.antonyholmes.modern.io.RecentFilesService;
import dev.antonyholmes.modern.panel.HExpandBox;
import dev.antonyholmes.modern.panel.VBoxAutoWidth;
import dev.antonyholmes.modern.text.ModernAutoSizeLabel;
import dev.antonyholmes.modern.text.ModernTextBorderPanel;
import dev.antonyholmes.modern.text.ModernTextField;
import dev.antonyholmes.modern.window.ModernWindow;
import dev.antonyholmes.modern.window.WindowWidgetFocusEvents;

public class EncodeDialog extends ModernDialogHelpWindow {
  private static final long serialVersionUID = 1L;

  private ChooseFilePanel mChooseFilePanel;

  private ModernTextField mNameField = new ModernTextField(Genome.GRCH38.getAssembly());

  public EncodeDialog(ModernWindow parent) {
    super(parent, "dna.encode.help.url");

    setTitle("Encode DNA");

    setup();

    createUi();

  }

  private void setup() {
    mChooseFilePanel = new ChooseFilePanel(getParentWindow(), true);
    mChooseFilePanel.setFile(RecentFilesService.getInstance().getPwd());

    addWindowListener(new WindowWidgetFocusEvents(mOkButton));

    setSize(600, 300);

    UI.centerWindowToScreen(this);
  }

  private final void createUi() {
    // this.getWindowContentPanel().add(new JLabel("Change " +
    // getProductDetails().getProductName() + " settings", JLabel.LEFT),
    // BorderLayout.PAGE_START);

    ModernComponent box = VBoxAutoWidth.create();

    UI.setSize(mNameField, 200);

    box.add(new HExpandBox(UI.ASSET_NAME, new ModernTextBorderPanel(mNameField)));
    box.add(UI.createVGap(40));
    box.add(new ModernAutoSizeLabel(
        "Choose the directory containing FASTA files (*.fa.gz)"));
    box.add(ModernWidget.createVGap());
    box.add(mChooseFilePanel);

    setCard(box);
  }

  @Override
  public void clicked(ModernClickEvent e) {
    if (e.getMessage().equals(UI.BUTTON_OK)) {

    }

    super.clicked(e);
  }

  public String getGenome() {
    return mNameField.getText(); // Genome.GRCH38;
  }

  public Path getDir() {
    return mChooseFilePanel.getFile();
  }
}
