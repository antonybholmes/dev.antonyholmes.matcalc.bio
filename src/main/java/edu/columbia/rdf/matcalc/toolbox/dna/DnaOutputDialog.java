package dev.antonyholmes.matcalc.toolbox.dna;

import java.awt.BorderLayout;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import javax.swing.Box;

import org.jebtk.bioinformatics.Bio;
import org.jebtk.bioinformatics.genomic.Sequence;
import org.jebtk.bioinformatics.ui.BioInfDialog;
import dev.antonyholmes.modern.AssetService;
import dev.antonyholmes.modern.UI;
import dev.antonyholmes.modern.button.ModernButton;
import dev.antonyholmes.modern.dialog.ModernDialogTaskWindow;
import dev.antonyholmes.modern.dialog.ModernMessageDialog;
import dev.antonyholmes.modern.event.ModernClickEvent;
import dev.antonyholmes.modern.event.ModernClickListener;
import dev.antonyholmes.modern.graphics.icons.SaveVectorIcon;
import dev.antonyholmes.modern.io.RecentFilesService;
import dev.antonyholmes.modern.panel.HBox;
import dev.antonyholmes.modern.panel.ModernPanel;
import dev.antonyholmes.modern.ribbon.RibbonButton;
import dev.antonyholmes.modern.scrollpane.ModernScrollPane;
import dev.antonyholmes.modern.text.ModernClipboardTextArea;
import dev.antonyholmes.modern.window.ModernWindow;
import dev.antonyholmes.modern.window.WindowWidgetFocusEvents;

public class DnaOutputDialog extends ModernDialogTaskWindow
    implements ModernClickListener {
  private static final long serialVersionUID = 1L;

  private ModernClipboardTextArea mTextArea = new ModernClipboardTextArea();

  private ModernButton mSaveButton = new RibbonButton(UI.MENU_SAVE,
      AssetService.getInstance().loadIcon(SaveVectorIcon.class, 16));

  private List<Sequence> mSequences;

  public DnaOutputDialog(ModernWindow parent, List<Sequence> sequences) {
    super(parent, false);

    mSequences = sequences;

    setSubTitle(Bio.ASSET_FASTA + " Output");

    createUi();

    setup();
  }

  private void setup() {
    StringBuilder buffer = new StringBuilder();

    for (Sequence sequence : mSequences) {
      buffer.append(">").append(sequence.getName()).append("\n");
      buffer.append(sequence.toString()).append("\n");
    }

    setResizable(true);

    mTextArea.setEditable(false);
    mTextArea.setText(buffer.toString());

    addWindowListener(new WindowWidgetFocusEvents(mOkButton));

    mSaveButton.addClickListener(this);

    setSize(640, 480);

    UI.centerWindowToScreen(this);
  }

  private final void createUi() {
    ModernPanel panel = new ModernPanel();

    panel.add(new ModernScrollPane(mTextArea), BorderLayout.CENTER);

    Box box = HBox.create();

    box.add(mSaveButton);
    box.setBorder(ModernPanel.TOP_BORDER);
    panel.setFooter(box);

    setCard(panel);
  }

  public final void clicked(ModernClickEvent e) {
    if (e.getMessage().equals(UI.MENU_SAVE)) {
      try {
        save();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    } else {
      super.clicked(e);
    }
  }

  private void save() throws IOException {
    Path file = BioInfDialog.saveFastaFile(getParentWindow(),
        RecentFilesService.getInstance().getPwd());

    if (file == null) {
      return;
    }

    Sequence.writeFasta(mSequences, file);

    ModernMessageDialog
        .createFileSavedDialog(getParentWindow(), getAppInfo().getName(), file);
  }
}
