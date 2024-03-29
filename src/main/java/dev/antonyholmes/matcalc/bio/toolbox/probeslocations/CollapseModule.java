package dev.antonyholmes.matcalc.bio.toolbox.probeslocations;

import java.io.IOException;
import java.text.ParseException;

import org.jebtk.math.matrix.DataFrame;
import org.jebtk.math.matrix.MatrixGroup;
import org.jebtk.math.matrix.utils.MatrixOperations;
import dev.antonyholmes.modern.AssetService;
import dev.antonyholmes.modern.dialog.ModernDialogStatus;
import dev.antonyholmes.modern.event.ModernClickEvent;
import dev.antonyholmes.modern.event.ModernClickListener;
import dev.antonyholmes.modern.ribbon.RibbonLargeButton;

import dev.antonyholmes.matcalc.MainMatCalcWindow;
import dev.antonyholmes.matcalc.toolbox.Module;
import dev.antonyholmes.matcalc.toolbox.core.collapse.CollapseDialog;
import dev.antonyholmes.matcalc.toolbox.core.collapse.CollapseType;

public class CollapseModule extends Module implements ModernClickListener {
  private MainMatCalcWindow mWindow;

  @Override
  public String getName() {
    return "Collapse";
  }

  @Override
  public void init(MainMatCalcWindow window) {
    mWindow = window;

    RibbonLargeButton button = new RibbonLargeButton("Collapse Rows",
        AssetService.getInstance().loadIcon("collapse", 32), "Collapse Rows",
        "Collapse rows by annotation, e.g. probe ids.");
    button.addClickListener(this);
    mWindow.getRibbon().getToolbar("Bioinformatics").getSection("Collapse Rows").add(button);
  }

  @Override
  public void clicked(ModernClickEvent e) {
    try {
      collapse();
    } catch (IOException ex) {
      ex.printStackTrace();
    } catch (ParseException e1) {
      e1.printStackTrace();
    }
  }

  private void collapse() throws IOException, ParseException {
    DataFrame m = mWindow.getCurrentMatrix();

    CollapseDialog dialog = new CollapseDialog(mWindow, m, mWindow.getGroups());

    dialog.setVisible(true);

    if (dialog.getStatus() == ModernDialogStatus.CANCEL) {
      return;
    }

    MatrixGroup group1 = dialog.getGroup1();
    MatrixGroup group2 = dialog.getGroup2();

    String collapseName = dialog.getCollapseName();
    CollapseType collapseType = dialog.getCollapseType();

    DataFrame c = null;

    switch (collapseType) {
    case MAX:
      c = MatrixOperations.collapseMax(m, collapseName);
      break;
    case MIN:
      c = MatrixOperations.collapseMin(m, collapseName);
      break;
    case MAX_STDEV:
      c = MatrixOperations.collapseMaxStdDev(m, collapseName);
      break;
    case MAX_MEAN:
      c = MatrixOperations.collapseMaxMean(m, collapseName);
      break;
    case MAX_MEDIAN:
      c = MatrixOperations.collapseMaxMedian(m, collapseName);
      break;
    case MAX_TSTAT:
      c = MatrixOperations.addTStat(m, group1, group2);
      mWindow.history().addToHistory("Add T-Stats", c);
      c = MatrixOperations.collapseMaxTStat(c, collapseName);
      break;
    default:
      c = m;
      break;
    }

    mWindow.history().addToHistory("Collapse rows", c);
  }
}
