package edu.columbia.rdf.matcalc.toolbox.regions;

import java.io.IOException;
import java.nio.file.Path;

import javax.swing.Box;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.jebtk.core.io.PathUtils;
import org.jebtk.core.settings.SettingsService;
import org.jebtk.math.external.microsoft.Excel;
import dev.antonyholmes.modern.BorderService;
import dev.antonyholmes.modern.ModernWidget;
import dev.antonyholmes.modern.UI;
import dev.antonyholmes.modern.button.ModernButtonGroup;
import dev.antonyholmes.modern.button.ModernCheckBox;
import dev.antonyholmes.modern.button.ModernRadioButton;
import dev.antonyholmes.modern.combobox.ModernComboBox;
import dev.antonyholmes.modern.dialog.ModernDialogTaskWindow;
import dev.antonyholmes.modern.event.ModernClickEvent;
import dev.antonyholmes.modern.event.ModernClickListener;
import dev.antonyholmes.modern.panel.HBox;
import dev.antonyholmes.modern.panel.VBox;
import dev.antonyholmes.modern.text.ModernAutoSizeLabel;
import dev.antonyholmes.modern.window.ModernWindow;
import dev.antonyholmes.modern.window.WindowWidgetFocusEvents;

/**
 * Control which conservation scores are shown.
 * 
 * @author Antony Holmes
 *
 */
public class OverlapDialog extends ModernDialogTaskWindow implements ModernClickListener {
  private static final long serialVersionUID = 1L;

  private ModernRadioButton mCheckOneWay = new ModernRadioButton("One way", true);

  private ModernRadioButton mCheckTwoWay = new ModernRadioButton("Two way");

  private ModernCheckBox mCheckVenn = new ModernCheckBox("Venn diagram", ModernWidget.LARGE_SIZE);

  private ModernCheckBox mCheckAddBeginning = new ModernCheckBox("Annotation at beginning",
      SettingsService.getInstance().getBool("regions.overlap.annotation.first-columns"), ModernWidget.EXTRA_LARGE_SIZE);

  private Path mFile1;

  private Path mFile2;

  private ModernComboBox mHeader1;

  private ModernComboBox mHeader2;

  public OverlapDialog(ModernWindow parent, Path file1, Path file2) {
    super(parent);

    mFile1 = file1;
    mFile2 = file2;

    setTitle("Overlap");

    setup();

    createUi();
  }

  private void setup() {
    addWindowListener(new WindowWidgetFocusEvents(mOkButton));

    setSize(480, 300);

    UI.centerWindowToScreen(this);
  }

  private final void createUi() {
    // this.getWindowContentPanel().add(new JLabel("Change " +
    // getProductDetails().getProductName() + " settings", JLabel.LEFT),
    // BorderLayout.PAGE_START);

    Box box = VBox.create();

    box.add(mCheckOneWay);

    box.add(UI.createVGap(10));

    box.add(mCheckTwoWay);

    mCheckVenn.setBorder(BorderService.getInstance().createLeftBorder(20));
    box.add(mCheckVenn);

    if (!PathUtils.getFileExt(mFile1).contains("bed")) {
      box.add(UI.createVGap(10));

      Box box2 = HBox.create();

      box2.add(new ModernAutoSizeLabel("Input file genomic location", ModernWidget.EXTRA_LARGE_SIZE));

      try {
        mHeader1 = new ModernComboBox(Excel.getHeader(mFile1), ModernWidget.EXTRA_LARGE_SIZE);
        box2.add(mHeader1);
      } catch (InvalidFormatException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }

      box.add(box2);
    }

    if (!PathUtils.getFileExt(mFile2).contains("bed")) {
      box.add(UI.createVGap(10));

      Box box2 = HBox.create();

      box2 = HBox.create();

      box2.add(new ModernAutoSizeLabel("Overlap file genomic location", ModernWidget.EXTRA_LARGE_SIZE));

      try {
        mHeader2 = new ModernComboBox(Excel.getHeader(mFile2), ModernWidget.EXTRA_LARGE_SIZE);

        box2.add(mHeader2);
      } catch (InvalidFormatException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }

      box.add(box2);
    }

    box.add(UI.createVGap(10));

    box.add(mCheckAddBeginning);

    setCard(box);

    new ModernButtonGroup(mCheckOneWay, mCheckTwoWay);
  }

  @Override
  public void clicked(ModernClickEvent e) {
    if (e.getMessage().equals(UI.BUTTON_OK)) {
      // Update whether to add overlap columns at start or end
      SettingsService.getInstance().update("regions.overlap.annotation.first-columns", mCheckAddBeginning.isSelected());
    }

    super.clicked(e);
  }

  public boolean getStrict() {
    return mCheckOneWay.isSelected();
  }

  public boolean getDrawVenn() {
    return mCheckVenn.isSelected();
  }

  public boolean getAddBeginning() {
    return mCheckAddBeginning.isSelected();
  }

  public int getHeader1() {
    return mHeader1 != null ? mHeader1.getSelectedIndex() : -1;
  }

  public int getHeader2() {
    return mHeader2 != null ? mHeader2.getSelectedIndex() : -1;
  }
}
