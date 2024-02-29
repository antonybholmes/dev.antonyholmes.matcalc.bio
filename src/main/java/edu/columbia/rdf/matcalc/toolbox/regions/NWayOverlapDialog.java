package edu.columbia.rdf.matcalc.toolbox.regions;

import java.nio.file.Path;
import java.util.List;

import javax.swing.Box;

import org.jebtk.bioinformatics.ui.external.ucsc.BedGraphGuiFileFilter;
import org.jebtk.bioinformatics.ui.external.ucsc.BedGuiFileFilter;
import org.jebtk.core.settings.SettingsService;
import org.jebtk.math.ui.external.microsoft.ExcelGuiFileFilter;
import dev.antonyholmes.modern.BorderService;
import dev.antonyholmes.modern.ModernComponent;
import dev.antonyholmes.modern.UI;
import dev.antonyholmes.modern.button.ModernButtonGroup;
import dev.antonyholmes.modern.button.ModernCheckSwitch;
import dev.antonyholmes.modern.button.ModernRadioButton;
import dev.antonyholmes.modern.button.ModernTwoStateWidget;
import dev.antonyholmes.modern.dialog.ModernDialogHelpWindow;
import dev.antonyholmes.modern.dialog.ModernMessageDialog;
import dev.antonyholmes.modern.event.ModernClickEvent;
import dev.antonyholmes.modern.event.ModernClickListener;
import dev.antonyholmes.modern.io.ChooseFilesPanel;
import dev.antonyholmes.modern.io.CsvGuiFileFilter;
import dev.antonyholmes.modern.io.TsvGuiFileFilter;
import dev.antonyholmes.modern.panel.VBox;
import dev.antonyholmes.modern.window.WindowWidgetFocusEvents;

import edu.columbia.rdf.matcalc.MainMatCalcWindow;

/**
 * Control which conservation scores are shown.
 * 
 * @author Antony Holmes
 *
 */
public class NWayOverlapDialog extends ModernDialogHelpWindow implements ModernClickListener {
  private static final long serialVersionUID = 1L;

  private ModernRadioButton mCheckOneWay = new ModernRadioButton("One way", true);

  private ModernRadioButton mCheckTwoWay = new ModernRadioButton("Two way");

  private ModernTwoStateWidget mCheckVenn = new ModernCheckSwitch("Show Venn diagram");

  private ModernTwoStateWidget mCheckBeginning = new ModernCheckSwitch("At beginning", true);

  private ChooseFilesPanel mChooseFilesPanel;

  public NWayOverlapDialog(MainMatCalcWindow parent) {
    super(parent, "org.matcalc.toolbox.bio.regions.nway.help.url");

    setTitle("Overlap");

    mChooseFilesPanel = new ChooseFilesPanel(parent, new AllRegionGuiFileFilter(), BedGuiFileFilter.INSTANCE,
        BedGraphGuiFileFilter.INSTANCE, CsvGuiFileFilter.INSTANCE, TsvGuiFileFilter.INSTANCE,
        ExcelGuiFileFilter.INSTANCE);

    setup();

    createUi();

    // Add the existing file from the current window as a convenience
    if (parent.getInputFile() != null) {
      mChooseFilesPanel.addFile(parent.getInputFile());
    }
  }

  private void setup() {
    addWindowListener(new WindowWidgetFocusEvents(mOkButton));

    new ModernButtonGroup(mCheckOneWay, mCheckTwoWay);

    if (SettingsService.getInstance().getBool("org.matcalc.toolbox.bio.regions.nway.one-way", true)) {
      mCheckOneWay.doClick();
    } else {
      mCheckTwoWay.doClick();
    }

    setSize(520, 520);

    UI.centerWindowToScreen(this);
  }

  private final void createUi() {
    ModernComponent content = new ModernComponent();

    Box box = VBox.create();
    // midSectionHeader("Options", box);
    box.add(mCheckOneWay);
    // box.add(UI.createVGap(5));
    box.add(mCheckTwoWay);
    mCheckVenn.setBorder(BorderService.getInstance().createLeftBorder(10));
    box.add(mCheckVenn);
    box.add(mCheckBeginning);

    content.setFooter(box);

    content.setBody(mChooseFilesPanel);

    setCard(content);
  }

  @Override
  public void clicked(ModernClickEvent e) {
    if (e.getSource().equals(mOkButton)) {
      if (getFiles().size() == 0) {
        ModernMessageDialog.createWarningDialog(mParent, "You must choose at least one file.");

        return;
      }

      SettingsService.getInstance().update("org.matcalc.toolbox.bio.regions.nway.one-way", mCheckOneWay.isSelected());
    }

    super.clicked(e);
  }

  public List<Path> getFiles() {
    return mChooseFilesPanel.getFiles();
  }

  /**
   * Returns true if one way overlap is selected.
   * 
   * @return
   */
  public boolean getOneWay() {
    return mCheckOneWay.isSelected();
  }

  public boolean getDrawVenn() {
    return mCheckVenn.isSelected();
  }

  public boolean getAtBeginning() {
    return mCheckBeginning.isSelected();
  }
}
