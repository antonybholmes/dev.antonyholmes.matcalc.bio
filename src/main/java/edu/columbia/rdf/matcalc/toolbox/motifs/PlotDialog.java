package dev.antonyholmes.matcalc.toolbox.motifs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.Box;

import org.jebtk.core.collections.IterHashMap;
import org.jebtk.core.collections.IterMap;
import dev.antonyholmes.modern.UI;
import dev.antonyholmes.modern.button.CheckBox;
import dev.antonyholmes.modern.button.ModernCheckSwitch;
import dev.antonyholmes.modern.button.ModernTwoStateWidget;
import dev.antonyholmes.modern.dialog.ModernDialogTaskWindow;
import dev.antonyholmes.modern.event.ModernClickEvent;
import dev.antonyholmes.modern.event.ModernClickListener;
import dev.antonyholmes.modern.panel.VBox;
import dev.antonyholmes.modern.scrollpane.ModernScrollPane;
import dev.antonyholmes.modern.scrollpane.ScrollBarPolicy;
import dev.antonyholmes.modern.window.ModernWindow;
import dev.antonyholmes.modern.window.WindowWidgetFocusEvents;

public class PlotDialog extends ModernDialogTaskWindow {
  private static final long serialVersionUID = 1L;

  // private ModernCheckBox mCheckPeakWidth =
  // new ModernCheckBox("Peak widths only");

  private CheckBox mCheckAll = new ModernCheckSwitch("All", true);

  private IterMap<String, ModernTwoStateWidget> mSelectedMap = new IterHashMap<String, ModernTwoStateWidget>();

  private List<String> mMutations;

  public PlotDialog(ModernWindow parent, List<String> mutations) {
    super(parent);

    mMutations = mutations;

    setTitle("Select Motifs");

    createUi();

    addWindowListener(new WindowWidgetFocusEvents(mOkButton));

    setResizable(true);

    setSize(480, 360);

    UI.centerWindowToScreen(this);

    mCheckAll.addClickListener(new ModernClickListener() {

      @Override
      public void clicked(ModernClickEvent e) {
        for (Entry<String, ModernTwoStateWidget> item : mSelectedMap) {
          item.getValue().setSelected(mCheckAll.isSelected());
        }
      }
    });

  }

  public void createUi() {

    Box box = new VBox();

    box.add(mCheckAll);

    for (String mutation : mMutations) {
      CheckBox check = new ModernCheckSwitch(mutation, true);

      box.add(check);

      mSelectedMap.put(mutation, check);
    }

    ModernScrollPane scrollPane = new ModernScrollPane(box).setHorizontalScrollBarPolicy(ScrollBarPolicy.NEVER);

    setCard(scrollPane);
  }

  public List<String> getMutations() {
    List<String> ret = new ArrayList<String>(mSelectedMap.size());

    for (String m : mMutations) {
      if (mSelectedMap.get(m).isSelected()) {
        ret.add(m);
      }
    }

    return ret;
  }

}
