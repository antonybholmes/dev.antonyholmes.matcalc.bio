package dev.antonyholmes.matcalc.toolbox.genes;

import java.io.IOException;
import java.util.List;

import org.jebtk.bioinformatics.genomic.Genome;
import dev.antonyholmes.modern.AssetService;
import dev.antonyholmes.modern.button.ModernDropDownButton2;
import dev.antonyholmes.modern.dialog.ModernDialogStatus;
import dev.antonyholmes.modern.dialog.ModernMessageDialog;
import dev.antonyholmes.modern.event.ModernClickEvent;
import dev.antonyholmes.modern.event.ModernClickListener;
import dev.antonyholmes.modern.graphics.icons.ModernIcon;
import dev.antonyholmes.modern.help.ModernMenuHelpItem;
import dev.antonyholmes.modern.menu.ModernPopupMenu2;
import dev.antonyholmes.modern.menu.ModernTwoLineMenuItem;
import dev.antonyholmes.modern.ribbon.RibbonLargeDropDownButton2;

import dev.antonyholmes.matcalc.MainMatCalcWindow;
import dev.antonyholmes.matcalc.toolbox.Module;

public class GeneAnnotationModule extends Module implements ModernClickListener {

  private static final ModernIcon ICON = AssetService.getInstance().loadIcon("genes", 24);
  private MainMatCalcWindow mWindow;
  private ModernDropDownButton2 mButton;

  @Override
  public String getName() {
    return "Gene Annotation";
  }

  @Override
  public void init(MainMatCalcWindow window) {
    mWindow = window;

    ModernPopupMenu2 popup = new ModernPopupMenu2();

    popup.addMenuItem(new ModernTwoLineMenuItem("Genes", "Annotate regions for overlapping genes.", ICON));
    popup.addMenuItem(new ModernTwoLineMenuItem("TSS", "Annotate for gene TSS within a region", ICON));

    // popup.addMenuItem(new ModernMenuSeparator());

    popup.addMenuItem(
        new ModernMenuHelpItem("Help with annotating regions...", "geneannotation.help.url").setTextOffset(48));

    mButton = new RibbonLargeDropDownButton2(ICON, popup);
    mButton.setToolTip("Annotate", "Annotate genomic regions.");

    mWindow.getRibbon().getToolbar("Genomic").getSection("Annotation").add(mButton);

    mButton.addClickListener(this);
  }

  @Override
  public void clicked(ModernClickEvent e) {
    if (e.getMessage().equals("Genes")) {
      try {
        annotate();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    } else if (e.getMessage().equals("TSS")) {
      try {
        tssAnnotate();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    } else {
      // Do nothing
    }
  }

  private void annotate() throws IOException {
    if (mWindow.getCurrentMatrix() == null) {
      return;
    }

    AnnotationDialog dialog = new AnnotationDialog(mWindow);

    dialog.setVisible(true);

    if (dialog.getStatus() == ModernDialogStatus.CANCEL) {
      return;
    }

    List<Genome> genomes = dialog.getGenomes();

    if (genomes.size() == 0) {
      ModernMessageDialog.createWarningDialog(mWindow, "You must select at least one annotation set.");

      annotate();

      return;
    }

    AnnotateTask task = new AnnotateTask(mWindow, genomes.get(0), dialog.getShowOverlappingGenes(),
        dialog.getClosestList(), dialog.getExt5p(), dialog.getExt3p());

    // task.execute();

    task.doInBackground();
    task.done();
  }

  private void tssAnnotate() throws IOException {
    if (mWindow.getCurrentMatrix() == null) {
      return;
    }

    TSSAnnotationDialog dialog = new TSSAnnotationDialog(mWindow);

    dialog.setVisible(true);

    if (dialog.getStatus() == ModernDialogStatus.CANCEL) {
      return;
    }

    List<Genome> genomes = dialog.getGenomes();

    TSSAnnotateTask task = new TSSAnnotateTask(mWindow, genomes.get(0), dialog.getExt5p(), dialog.getExt3p());

    // task.execute();

    task.doInBackground();
    task.done();
  }
}
