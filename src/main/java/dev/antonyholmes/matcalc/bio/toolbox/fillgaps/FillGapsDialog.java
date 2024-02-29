package dev.antonyholmes.matcalc.bio.toolbox.fillgaps;

import java.awt.Dimension;
import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.Box;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.jebtk.core.collections.CollectionUtils;
import org.jebtk.math.external.microsoft.Excel;
import org.jebtk.math.ui.external.microsoft.ExcelDialog;
import dev.antonyholmes.modern.AssetService;
import dev.antonyholmes.modern.ModernWidget;
import dev.antonyholmes.modern.UI;
import dev.antonyholmes.modern.button.ModernButtonWidget;
import dev.antonyholmes.modern.combobox.ModernComboBox;
import dev.antonyholmes.modern.dialog.ModernDialogTaskWindow;
import dev.antonyholmes.modern.event.ModernClickEvent;
import dev.antonyholmes.modern.event.ModernClickListener;
import dev.antonyholmes.modern.io.RecentFilesService;
import dev.antonyholmes.modern.list.ModernList;
import dev.antonyholmes.modern.list.ModernListModel;
import dev.antonyholmes.modern.panel.HBox;
import dev.antonyholmes.modern.panel.ModernLineBorderPanel;
import dev.antonyholmes.modern.panel.ModernPanel;
import dev.antonyholmes.modern.panel.VBox;
import dev.antonyholmes.modern.ribbon.RibbonButton;
import dev.antonyholmes.modern.scrollpane.ModernScrollPane;
import dev.antonyholmes.modern.text.ModernAutoSizeLabel;
import dev.antonyholmes.modern.text.ModernClipboardNumericalTextField;
import dev.antonyholmes.modern.text.ModernNumericalTextField;
import dev.antonyholmes.modern.text.ModernSubHeadingLabel;
import dev.antonyholmes.modern.text.ModernTextBorderPanel;
import dev.antonyholmes.modern.window.ModernWindow;
import dev.antonyholmes.modern.window.WindowWidgetFocusEvents;

public class FillGapsDialog extends ModernDialogTaskWindow {
  private static final long serialVersionUID = 1L;

  private Map<String, Path> mBedFileMap = null;

  private Map<String, Map<String, String>> mDescriptionMap;

  private Map<String, String> mNameMap = new TreeMap<String, String>();

  private ModernComboBox mAnnotationCombo = new ModernComboBox(new Dimension(300, ModernWidget.WIDGET_HEIGHT));

  private ModernList<String> mSampleList = new ModernList<String>();

  private ModernButtonWidget mLoadButton = new RibbonButton("Load...", AssetService.getInstance().loadIcon("open", 16));

  private ModernNumericalTextField mMeanZeroField = new ModernClipboardNumericalTextField(
      FillGapsModule.SEGMENT_MEAN_ZERO);

  private List<String> mSamples = null;

  public FillGapsDialog(ModernWindow parent, Map<String, Path> bedFileMap,
      Map<String, Map<String, String>> descriptionMap) {
    super(parent);

    mBedFileMap = bedFileMap;
    mDescriptionMap = descriptionMap;

    for (String name : mBedFileMap.keySet()) {
      mNameMap.put(mDescriptionMap.get(name).get("description"), name);
    }

    setTitle("Fill Gaps");

    createUi();

    setup();
  }

  private void setup() {
    addWindowListener(new WindowWidgetFocusEvents(mOkButton));

    mLoadButton.addClickListener(new ModernClickListener() {

      @Override
      public void clicked(ModernClickEvent e) {
        try {
          loadSamples();
        } catch (InvalidFormatException | IOException e1) {
          e1.printStackTrace();
        }
      }
    });

    setResizable(true);

    setSize(720, 520);

    UI.centerWindowToScreen(this);
  }

  private final void createUi() {
    Box content = VBox.create();

    content.add(new ModernSubHeadingLabel("Annotation"));

    content.add(ModernPanel.createVGap());

    for (String name : mNameMap.keySet()) {
      mAnnotationCombo.addScrollMenuItem(name);
    }

    content.add(mAnnotationCombo);

    content.add(UI.createVGap(30));

    content.add(new ModernSubHeadingLabel("Samples"));

    content.add(ModernPanel.createVGap());

    Box box = HBox.create();

    ModernLineBorderPanel panel = new ModernLineBorderPanel(new ModernScrollPane(mSampleList), new Dimension(500, 200));
    panel.setAlignmentY(TOP_ALIGNMENT);
    box.add(panel);

    box.add(ModernWidget.createHGap());

    Box box2 = VBox.create();
    box2.setAlignmentY(TOP_ALIGNMENT);
    box2.add(mLoadButton);

    box.add(box2);

    content.add(box);

    content.add(UI.createVGap(30));

    box2 = HBox.create();
    box2.add(new ModernAutoSizeLabel("Mean Zero", 100));
    box2.add(new ModernTextBorderPanel(mMeanZeroField, 100));

    content.add(box2);

    // content.setBorder(BorderService.getInstance().createBorder(10));

    setCard(content);
  }

  private void loadSamples() throws IOException, InvalidFormatException {
    Path file = ExcelDialog.open(mParent).xlsx().getFile(RecentFilesService.getInstance().getPwd());

    if (file == null) {
      return;
    }

    mSamples = CollectionUtils.sort(CollectionUtils.unique(Excel.getTextFromFile(file, true)));

    ModernListModel<String> model = new ModernListModel<String>();

    for (String sample : mSamples) {
      model.addValue(sample);
    }

    mSampleList.setModel(model);
  }

  public String getAnnotation() {
    return mNameMap.get(mAnnotationCombo.getText());
  }

  public List<String> getSamples() {
    return mSamples;
  }

  public double getMeanZero() throws ParseException {
    return mMeanZeroField.getDouble();
  }
}
