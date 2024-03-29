package dev.antonyholmes.matcalc.toolbox.regions.enhancers;

import dev.antonyholmes.modern.search.SortModel;

/**
 * Allows sort objects to be shared between entities that control how samples
 * and experiments are sorted.
 * 
 * @author Antony Holmes
 *
 */
public class EnhancerSortModel extends SortModel<Enhancer> {
  private static final long serialVersionUID = 1L;

  public EnhancerSortModel() {
    add(new SortSamplesByName());

    setDefault("ChIP-seq Type");
  }
}
