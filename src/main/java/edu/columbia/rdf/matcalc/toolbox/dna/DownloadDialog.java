package dev.antonyholmes.matcalc.toolbox.dna;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.Box;

import org.jebtk.core.collections.IterMap;
import org.jebtk.core.collections.IterTreeMap;
import dev.antonyholmes.modern.ModernComponent;
import dev.antonyholmes.modern.UI;
import dev.antonyholmes.modern.button.CheckBox;
import dev.antonyholmes.modern.button.ModernCheckSwitch;
import dev.antonyholmes.modern.dialog.ModernDialogTaskWindow;
import dev.antonyholmes.modern.panel.VBox;
import dev.antonyholmes.modern.scrollpane.ModernScrollPane;
import dev.antonyholmes.modern.scrollpane.ScrollBarPolicy;
import dev.antonyholmes.modern.text.ModernAutoSizeLabel;
import dev.antonyholmes.modern.window.ModernWindow;
import dev.antonyholmes.modern.window.WindowWidgetFocusEvents;

public class DownloadDialog extends ModernDialogTaskWindow {
  private static final long serialVersionUID = 1L;

  private IterMap<String, CheckBox> mCheckMap = new IterTreeMap<String, CheckBox>();

  public DownloadDialog(ModernWindow parent) {
    super(parent);

    setTitle("Download");

    try {
      createUi();
    } catch (IOException e) {
      e.printStackTrace();
    }

    setup();
  }

  private void setup() {
    addWindowListener(new WindowWidgetFocusEvents(mOkButton));

    setSize(480, 300);

    UI.centerWindowToScreen(this);
  }

  private final void createUi() throws IOException {
    // this.getWindowContentPanel().add(new JLabel("Change " +
    // getProductDetails().getProductName() + " settings", JLabel.LEFT),
    // BorderLayout.PAGE_START);

    ModernComponent c = new ModernComponent();

    // sectionHeader("Genomes", box);

    // UI.setSize(mDnaPanel, 500, 150);
    // box.add(mDnaPanel);

    c.setHeader(new ModernComponent(new ModernAutoSizeLabel(
        "Select the genomes you would like to download:")).bottomBorder(20));

    Box box = VBox.create();

    for (Entry<String, GenomeDownload> name : GenomeDownloadService.getInstance()) {
      CheckBox checkBox = new ModernCheckSwitch(name.getKey());

      box.add(checkBox);

      mCheckMap.put(name.getKey(), checkBox);
    }

    c.setBody(new ModernScrollPane(box)
        .setHorizontalScrollBarPolicy(ScrollBarPolicy.NEVER));

    setCard(c);
  }

  public List<GenomeDownload> getDownloads() {
    List<GenomeDownload> ret = new ArrayList<GenomeDownload>(mCheckMap.size());

    for (Entry<String, CheckBox> item : mCheckMap) {
      if (item.getValue().isSelected()) {
        ret.add(GenomeDownloadService.getInstance().get(item.getKey()));
      }
    }

    return ret;
  }
}
