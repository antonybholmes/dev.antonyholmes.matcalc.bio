package edu.columbia.rdf.matcalc.toolbox.motifs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.Box;

import org.jebtk.bioinformatics.ui.genome.RegionsTextArea;
import org.jebtk.bioinformatics.ui.groups.GroupComboBox;
import org.jebtk.bioinformatics.ui.groups.GroupsModel;
import dev.antonyholmes.modern.AssetService;
import dev.antonyholmes.modern.BorderService;
import dev.antonyholmes.modern.UI;
import dev.antonyholmes.modern.button.ModernButtonWidget;
import dev.antonyholmes.modern.dialog.ModernDialogTaskWindow;
import dev.antonyholmes.modern.event.ModernClickEvent;
import dev.antonyholmes.modern.event.ModernClickListener;
import dev.antonyholmes.modern.graphics.color.ColorSwatchButton;
import dev.antonyholmes.modern.graphics.icons.RunVectorIcon;
import dev.antonyholmes.modern.panel.HBox;
import dev.antonyholmes.modern.panel.ModernPanel;
import dev.antonyholmes.modern.panel.VBox;
import dev.antonyholmes.modern.ribbon.RibbonPanelButton;
import dev.antonyholmes.modern.scrollpane.ModernScrollPane;
import dev.antonyholmes.modern.scrollpane.ScrollBarPolicy;
import dev.antonyholmes.modern.text.ModernAutoSizeLabel;
import dev.antonyholmes.modern.text.ModernClipboardTextField;
import dev.antonyholmes.modern.text.ModernTextBorderPanel;
import dev.antonyholmes.modern.text.ModernTextField;
import dev.antonyholmes.modern.window.ModernWindow;
import dev.antonyholmes.modern.window.WindowWidgetFocusEvents;

public class BackgroundDialog extends ModernDialogTaskWindow {
  private static final long serialVersionUID = 1L;

  private static final Dimension NAME_FIELD_SIZE = new Dimension(300, 24);

  ModernButtonWidget mCreateButton = new RibbonPanelButton("Create",
      AssetService.getInstance().loadIcon(RunVectorIcon.class, 32));

  private GroupComboBox mForegroundCombo;

  private GroupsModel mRegionsModel;

  private ColorSwatchButton mColorButton;

  private ModernTextField mNameField = new ModernClipboardTextField(UI.ASSET_NAME);

  private RegionsTextArea mTextArea = new RegionsTextArea();

  private class CreateEvents implements ModernClickListener {

    @Override
    public void clicked(ModernClickEvent e) {
      createBackground();
    }

  }

  public BackgroundDialog(ModernWindow parent, GroupsModel regionGroupsModel) {
    super(parent);

    mRegionsModel = regionGroupsModel;

    setTitle("Motif Search");

    setup();

    createUi();
  }

  private void setup() {
    addWindowListener(new WindowWidgetFocusEvents(mOkButton));

    mCreateButton.addClickListener(new CreateEvents());

    setSize(800, 700);

    UI.centerWindowToScreen(this);
  }

  private final void createUi() {
    mColorButton = new ColorSwatchButton(mParent, Color.RED);
    mForegroundCombo = new GroupComboBox(mRegionsModel);

    ModernPanel box = new ModernPanel();

    Box box2 = VBox.create();

    Box box3 = HBox.create();
    box3.add(new ModernAutoSizeLabel(UI.ASSET_GROUP, 100));
    box3.add(mForegroundCombo);
    box2.add(box3);
    box2.add(ModernPanel.createVGap());

    box3 = HBox.create();
    box3.add(new ModernAutoSizeLabel(UI.ASSET_NAME, 100));
    box3.add(new ModernTextBorderPanel(mNameField, NAME_FIELD_SIZE));
    box2.add(box3);
    box2.add(ModernPanel.createVGap());

    box3 = HBox.create();
    box3.add(new ModernAutoSizeLabel(UI.ASSET_COLOR, 100));
    box3.add(mColorButton);
    box2.add(box3);

    box2.setBorder(ModernPanel.RIGHT_BORDER);

    ModernPanel panel = new ModernPanel();

    panel.add(box2, BorderLayout.CENTER);
    panel.add(mCreateButton, BorderLayout.LINE_END);
    panel.setBorder(BorderService.getInstance().createBottomBorder(10));
    box.add(panel, BorderLayout.PAGE_START);

    ModernScrollPane scrollPane = new ModernScrollPane(mTextArea);
    scrollPane.setVerticalScrollBarPolicy(ScrollBarPolicy.ALWAYS);

    box.add(scrollPane, BorderLayout.CENTER);

    setCard(box);
  }

  private void createBackground() {

  }

  public String getName() {
    return this.mNameField.getText();
  }

  public Color getColor() {
    return mColorButton.getSelectedColor();
  }

  public List<String> getRegions() {
    return this.mTextArea.getLines();
  }
}
