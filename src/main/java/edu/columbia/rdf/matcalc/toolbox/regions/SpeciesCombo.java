package dev.antonyholmes.matcalc.toolbox.regions;

import java.io.IOException;

import org.jebtk.bioinformatics.genomic.Genome;

import dev.antonyholmes.matcalc.bio.AnnotationService;
import dev.antonyholmes.modern.UI;
import dev.antonyholmes.modern.combobox.ModernComboBox;

public class SpeciesCombo extends ModernComboBox {

  private static final long serialVersionUID = 1L;

  public SpeciesCombo() throws IOException {
    for (Genome g : AnnotationService.getInstance().genomes()) {
      addMenuItem(g.mName);
    }

    UI.setSize(this, VERY_LARGE_SIZE);
  }

}
