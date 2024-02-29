package dev.antonyholmes.matcalc.bio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;

import org.jebtk.core.tree.CheckTreeNode;
import dev.antonyholmes.modern.ModernComponent;
import dev.antonyholmes.modern.button.ModernButton;
import dev.antonyholmes.modern.event.ModernClickEvent;
import dev.antonyholmes.modern.event.ModernClickListener;
import dev.antonyholmes.modern.panel.HSpacedBox;
import dev.antonyholmes.modern.ribbon.RibbonButton;
import dev.antonyholmes.modern.scrollpane.ModernScrollPane;
import dev.antonyholmes.modern.scrollpane.ScrollBarPolicy;
import dev.antonyholmes.modern.tree.ModernCheckTree;
import dev.antonyholmes.modern.tree.ModernCheckTreeMode;

/**
 * Control which conservation scores are shown.
 * 
 * @author Antony Holmes
 *
 */
public class GenomesPanel extends ModernComponent {
  private static final long serialVersionUID = 1L;

  private ModernCheckTree<String> mTree;

  private ModernButton mSelectAllButton = new RibbonButton("Select All");

  private boolean mCheckAll = true;

  public GenomesPanel() {
    this(ModernCheckTreeMode.MIN_ONE);
  }

  public GenomesPanel(ModernCheckTreeMode mode) {

    try {
      mTree = AnnotationService.getInstance().createTree(mode);

      ModernScrollPane scrollPane = new ModernScrollPane(mTree).setHorizontalScrollBarPolicy(ScrollBarPolicy.NEVER);

      setBody(scrollPane); // new ModernContentPanel(scrollPane));

      // Set a default
      ((CheckTreeNode<String>) mTree.getChildByPath("/ucsc/hg19/ucsc_refseq_hg19")).setChecked(true);
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (mode == ModernCheckTreeMode.MULTI || mode == ModernCheckTreeMode.MIN_ONE) {
      Box box = new HSpacedBox();
      box.setBorder(TOP_BORDER);
      box.add(mSelectAllButton);
      setFooter(box);
    }

    mSelectAllButton.addClickListener(new ModernClickListener() {

      @Override
      public void clicked(ModernClickEvent e) {
        mTree.setChecked(mCheckAll);

        mCheckAll = !mCheckAll;
      }
    });
  }

  public List<String> getGenomesIds() {
    List<CheckTreeNode<String>> nodes = mTree.getCheckedNodes();

    List<String> ret = new ArrayList<String>(nodes.size());

    for (CheckTreeNode<String> node : nodes) {
      ret.add(node.getName());
    }

    return ret;
  }

  public String getGenomeId() {
    List<String> genomes = getGenomesIds();

    if (genomes.size() > 0) {
      return genomes.get(0);
    } else {
      return null;
    }
  }
}
