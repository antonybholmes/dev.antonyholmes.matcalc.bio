package dev.antonyholmes.matcalc.toolbox.annotation;

import java.awt.Dimension;
import java.util.Map;

import javax.swing.Box;

import org.jebtk.core.settings.Setting;
import org.jebtk.core.settings.SettingsService;
import org.jebtk.core.text.TextUtils;
import dev.antonyholmes.modern.ModernWidget;
import dev.antonyholmes.modern.UI;
import dev.antonyholmes.modern.button.ModernCheckBox;
import dev.antonyholmes.modern.combobox.ModernComboBox;
import dev.antonyholmes.modern.event.ModernClickEvent;
import dev.antonyholmes.modern.event.ModernClickListener;
import dev.antonyholmes.modern.event.ModernStateEvent;
import dev.antonyholmes.modern.event.ModernStateListener;
import dev.antonyholmes.modern.panel.HBox;
import dev.antonyholmes.modern.panel.VBox;
import dev.antonyholmes.modern.text.ModernAutoSizeLabel;

public class AnnotationPanel extends VBox {

  private static final long serialVersionUID = 1L;

  private static final Dimension FIRST_COMBO_SIZE = new Dimension(60,
      ModernWidget.WIDGET_HEIGHT);

  private ModernCheckBox mCheckEnabled = new ModernCheckBox();

  private ModernCheckBox mCheckAll = new ModernCheckBox("All features");

  private ModernCheckBox mCheckAlphabetical = new ModernCheckBox(
      "Alphabetical");

  private ModernCheckBox mCheckCount = new ModernCheckBox("Count");

  private ModernCheckBox mCheckFirstNFeatures = new ModernCheckBox("First");

  /*
   * Report the locations rather than the features themselves
   */
  private ModernCheckBox mCheckLocations = new ModernCheckBox("Locations");

  private ModernCheckBox mCheckCondense = new ModernCheckBox("Condense");

  private ModernComboBox mFirstCombo = new ModernComboBox();

  private String mName;

  private static final String ROOT_SETTING = "biomatcalc.modules.annotation.";

  private class StateEvents implements ModernStateListener {
    private String mSetting;

    public StateEvents(String annotationName, String name) {
      mSetting = ROOT_SETTING + annotationName + "." + name + ".selected";
    }

    @Override
    public void stateChanged(ModernStateEvent e) {
      SettingsService.getInstance().update(mSetting,
          ((ModernCheckBox) e.getSource()).isSelected());
    }
  }

  public AnnotationPanel(Map<String, String> trackAttributes) {
    mName = trackAttributes.get("name");

    // mCheckEnabled.setFont(ModernWidget.SUB_HEADING_FONT);
    mCheckEnabled.setText(trackAttributes.get("description"));
    mCheckEnabled.addStateListener(new StateEvents(mName, "enabled"));
    mCheckEnabled.setSelected(getIsSelected(mName, "enabled"));

    add(mCheckEnabled);

    Box cBox = HBox.create();

    cBox.setBorder(ModernWidget.INSET_1);

    Box rBox = VBox.create();

    rBox.setAlignmentY(TOP_ALIGNMENT);

    rBox.add(mCheckAll);
    rBox.add(ModernWidget.createVGap());
    rBox.add(mCheckAlphabetical);
    rBox.add(ModernWidget.createVGap());
    rBox.add(mCheckCount);

    cBox.add(rBox);
    cBox.add(UI.createHGap(20));

    rBox = VBox.create();

    rBox.setAlignmentY(TOP_ALIGNMENT);

    Box box2 = HBox.create();

    box2.add(mCheckFirstNFeatures);
    UI.setSize(mFirstCombo, FIRST_COMBO_SIZE);
    box2.add(mFirstCombo);
    box2.add(UI.createHGap(10));
    box2.add(new ModernAutoSizeLabel("features"));

    rBox.add(box2);

    rBox.add(ModernWidget.createVGap());

    rBox.add(mCheckLocations);

    rBox.add(ModernWidget.createVGap());

    rBox.add(mCheckCondense);

    cBox.add(rBox);

    add(cBox);

    // if (trackAttributes.containsKey("condense")) {
    // mCheckCondense.setSelected(trackAttributes.get("condense").contains(TextUtils.TRUE));
    // }

    mFirstCombo.addMenuItem("10");
    mFirstCombo.addMenuItem("25");
    mFirstCombo.addMenuItem("50");
    mFirstCombo.addMenuItem("100");

    /*
     * mCheckEnabled.addClickListener(new ModernClickListener() {
     * 
     * @Override public void clicked(ModernClickEvent e) {
     * mCheckAll.setSelected(mCheckEnabled.isSelected());
     * mCheckAlphabetical.setSelected(mCheckEnabled.isSelected());
     * mCheckCount.setSelected(mCheckEnabled.isSelected());
     * mCheckFirstXFeatures.setSelected(mCheckEnabled.isSelected());
     * mCheckCondense.setSelected(mCheckEnabled.isSelected()); }});
     */

    setup("all", mCheckAll);
    setup("alphabetical", mCheckAlphabetical);
    setup("count", mCheckCount);
    setup("first_n", mCheckFirstNFeatures);
    setup("condense", mCheckCondense);
    setup("locations", mCheckLocations);
  }

  private void setup(String name, final ModernCheckBox checkBox) {
    checkBox.addClickListener(new ModernClickListener() {

      @Override
      public void clicked(ModernClickEvent e) {
        if (checkBox.isSelected()) {
          mCheckEnabled.setSelected(true);
        }
      }
    });

    checkBox.addStateListener(new StateEvents(mName, name));

    checkBox.setSelected(getIsSelected(mName, name));
  }

  @Override
  public String getName() {
    return mName;
  }

  public boolean getAddFeatures() {
    return mCheckEnabled.isSelected();
  }

  public boolean getAddAll() {
    return mCheckAll.isSelected();
  }

  public boolean getAddAlphabetical() {
    return mCheckAlphabetical.isSelected();
  }

  public boolean getAddCount() {
    return mCheckCount.isSelected();
  }

  public boolean getAddFirstN() {
    return mCheckFirstNFeatures.isSelected();
  }

  public boolean getAddLocations() {
    return mCheckLocations.isSelected();
  }

  public boolean getCondense() {
    return mCheckCondense.isSelected();
  }

  public int getFirstNCount() {
    int c = TextUtils.parseInt(mFirstCombo.getText());

    return c;
  }

  private static final boolean getIsSelected(String annotationName,
      String name) {
    Setting setting = SettingsService.getInstance()
        .getSetting(ROOT_SETTING + annotationName + "." + name + ".selected");

    if (setting != null) {
      return setting.getBool();
    } else {
      return false;
    }
  }
}
