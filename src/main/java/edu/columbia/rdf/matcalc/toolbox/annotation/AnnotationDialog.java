package dev.antonyholmes.matcalc.toolbox.annotation;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.Box;

import org.jebtk.core.collections.CollectionUtils;
import dev.antonyholmes.modern.BorderService;
import dev.antonyholmes.modern.UI;
import dev.antonyholmes.modern.button.ModernCheckBox;
import dev.antonyholmes.modern.button.ModernRadioButton;
import dev.antonyholmes.modern.dialog.ModernDialogContentPanel;
import dev.antonyholmes.modern.dialog.ModernDialogMultiCardWindow;
import dev.antonyholmes.modern.dialog.ModernDialogTaskType;
import dev.antonyholmes.modern.event.ModernClickListener;
import dev.antonyholmes.modern.panel.VBox;
import dev.antonyholmes.modern.scrollpane.ModernScrollPane;
import dev.antonyholmes.modern.tabs.SegmentTabsPanel;
import dev.antonyholmes.modern.tabs.SideTabsPanel;
import dev.antonyholmes.modern.tabs.TabsModel;
import dev.antonyholmes.modern.window.ModernWindow;
import dev.antonyholmes.modern.window.WindowWidgetFocusEvents;

public class AnnotationDialog extends ModernDialogMultiCardWindow
    implements ModernClickListener {
  private static final long serialVersionUID = 1L;

  private Map<String, Path> mBedFileMap;

  private Map<String, Map<String, String>> mDescriptionMap;

  private List<AnnotationPanel> mPanels = new ArrayList<AnnotationPanel>();

  private ModernCheckBox mCheckOverlapping = new ModernCheckBox("Overlapping",
      true);

  private ModernRadioButton mCheckClosest = new ModernRadioButton("Closest");

  // private ModernCheckBox mCheckWithin = new ModernCheckBox("Within", true);

  public AnnotationDialog(ModernWindow parent, Map<String, Path> bedFileMap,
      Map<String, Map<String, String>> descriptionMap) {
    super(parent, "Annotation", ModernDialogTaskType.OK_CANCEL);

    mBedFileMap = bedFileMap;
    mDescriptionMap = descriptionMap;

    createUi();

    setup();
  }

  private void setup() {
    // new ModernButtonGroup(mCheckOverlapping, mCheckClosest);

    addWindowListener(new WindowWidgetFocusEvents(mOkButton));

    setResizable(true);

    setSize(800, 600);

    UI.centerWindowToScreen(this);
  }

  private final void createUi() {
    //TabsModel genomeTabsModel = new TabsModel();

    Set<String> genomeTabNames = new TreeSet<String>();

    for (String name : mDescriptionMap.keySet()) {
      String tabName = "Other";

      if (mDescriptionMap.get(name).containsKey("genome")) {
        tabName = mDescriptionMap.get(name).get("genome");
      }

      genomeTabNames.add(tabName);
    }

    for (String genome : CollectionUtils.sortCaseInsensitive(genomeTabNames)) {
      TabsModel sourceTabsModel = new TabsModel();
      Map<String, Box> sourceTabNames = new TreeMap<String, Box>();

      for (String name : mDescriptionMap.keySet()) {
        String tabName = "Other";

        if (mDescriptionMap.get(name).containsKey("source")) {
          tabName = mDescriptionMap.get(name).get("source");
        }

        sourceTabNames.put(tabName, new VBox());
      }

      for (String name : mBedFileMap.keySet()) {
        String tabName = "Other";

        if (mDescriptionMap.get(name).containsKey("source")) {
          tabName = mDescriptionMap.get(name).get("source");
        }

        String g = "Other";

        if (mDescriptionMap.get(name).containsKey("genome")) {
          g = mDescriptionMap.get(name).get("genome");
        }

        if (g.equals(genome)) {
          AnnotationPanel panel = new AnnotationPanel(
              mDescriptionMap.get(name));

          mPanels.add(panel);

          sourceTabNames.get(tabName).add(panel);
          sourceTabNames.get(tabName).add(UI.createVGap(20));
        }
      }

      for (String tabName : CollectionUtils
          .sortCaseInsensitive(sourceTabNames.keySet())) {
        ModernScrollPane scrollPane = new ModernScrollPane(
            sourceTabNames.get(tabName));

        sourceTabsModel.addTab(tabName, scrollPane);
      }

      SegmentTabsPanel topTabs = new SegmentTabsPanel(sourceTabsModel, 100, 10,
          true); // TopTabsPanel;

      topTabs.setBorder(BorderService.getInstance().createLeftBorder(20));

      // Add to the genome model
      addTab(genome, topTabs);

      // Ensure first tab visible
      sourceTabsModel.changeTab(0);
    }

    // TextTabs genomeTabs = new TextTabsCentered(genomeTabsModel);

    // Ui.setSize(genomeTabs, ModernWidget.MAX_SIZE_24,
    // BorderService.getInstance().createBottomBorder(20));

    // ModernComponent c = new ModernComponent();
    // c.setHeader(genomeTabs);
    // c.setBody(new TabsViewPanel(genomeTabsModel));
    // c.setBorder(ModernWidget.BORDER);

    // setBody(c);

    //setBody(new SideTabsPanel(genomeTabsModel));

    //genomeTabsModel.changeTab(0);
    
    getTabsModel().changeTab(0);
  }

  public List<AnnotationPanel> getPanels() {
    return Collections.unmodifiableList(mPanels);
  }

  public boolean getClosestMode() {
    return mCheckClosest.isSelected();
  }
}
