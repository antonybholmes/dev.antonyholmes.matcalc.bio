package dev.antonyholmes.matcalc.toolbox.genes;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;

import org.jebtk.bioinformatics.genomic.Genome;

import dev.antonyholmes.matcalc.bio.GenomesPanel;
import dev.antonyholmes.modern.UI;
import dev.antonyholmes.modern.dialog.ModernDialogHelpWindow;
import dev.antonyholmes.modern.panel.HBox;
import dev.antonyholmes.modern.panel.HExpandBox;
import dev.antonyholmes.modern.panel.ModernPanel;
import dev.antonyholmes.modern.panel.VBox;
import dev.antonyholmes.modern.spinner.ModernCompactSpinner;
import dev.antonyholmes.modern.text.ModernAutoSizeLabel;
import dev.antonyholmes.modern.window.ModernWindow;
import dev.antonyholmes.modern.window.WindowWidgetFocusEvents;

/**
 * Control which conservation scores are shown.
 * 
 * @author Antony Holmes
 *
 */
public class TSSAnnotationDialog extends ModernDialogHelpWindow {
  private static final long serialVersionUID = 1L;

  private ModernCompactSpinner mTextExt5p = new ModernCompactSpinner(0, 100000, 2000, 1000, false);

  private ModernCompactSpinner mTextExt3p = new ModernCompactSpinner(0, 100000, 2000, 1000, false);

  // private SpeciesCombo mSpeciesCombo;

  private GenomesPanel mGenomesPanel = new GenomesPanel();

  public TSSAnnotationDialog(ModernWindow parent) {
    super(parent, "geneannotation.help.url");

    setTitle("TSS Annotation");

    createUi();

    setup();
  }

  private void setup() {
    addWindowListener(new WindowWidgetFocusEvents(mOkButton));

    setSize(640, 500);

    UI.centerWindowToScreen(this);
  }

  private final void createUi() {
    // this.getWindowContentPanel().add(new JLabel("Change " +
    // getProductDetails().getProductName() + " settings", JLabel.LEFT),
    // BorderLayout.PAGE_START);

    Box box2;

    Box box = VBox.create();
    sectionHeader("Genome", box);

    UI.setSize(mGenomesPanel, 600, 240);
    box.add(mGenomesPanel);

    midSectionHeader("Buffer", box);

    box2 = HBox.create();
    box2.add(new ModernAutoSizeLabel("5p"));
    box2.add(ModernPanel.createHGap());
    box2.add(mTextExt5p);
    // box2.add(ModernPanel.createHGap());
    // box2.add(new ModernAutoSizeLabel("bp"));
    box2.add(UI.createHGap(20));
    box2.add(new ModernAutoSizeLabel("3p"));
    box2.add(ModernPanel.createHGap());
    box2.add(mTextExt3p);
    box2.add(ModernPanel.createHGap());
    box2.add(new ModernAutoSizeLabel("bp"));
    box.add(new HExpandBox("Promoter", box2));

    setCard(box);
  }

  public int getExt5p() {
    return Math.abs(mTextExt5p.getIntValue());
  }

  public int getExt3p() {
    return Math.abs(mTextExt3p.getIntValue());
  }

  public List<Genome> getGenomes() {
    List<String> genomeIds = mGenomesPanel.getGenomesIds();

    List<Genome> ret = new ArrayList<Genome>(genomeIds.size());

    for (String g : genomeIds) {
      ret.add(Genome.fromId(g));
    }

    return ret;
  }
}
