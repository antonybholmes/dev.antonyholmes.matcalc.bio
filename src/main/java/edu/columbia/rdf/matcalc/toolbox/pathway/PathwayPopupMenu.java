package edu.columbia.rdf.matcalc.toolbox.pathway;

import java.awt.BorderLayout;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.Box;

import org.jebtk.core.collections.CollectionUtils;
import dev.antonyholmes.modern.ModernWidget;
import dev.antonyholmes.modern.UI;
import dev.antonyholmes.modern.button.ModernCheckBox;
import dev.antonyholmes.modern.menu.ModernPopupMenu;
import dev.antonyholmes.modern.panel.VBox;
import dev.antonyholmes.modern.scrollpane.ModernScrollPane;

public class PathwayPopupMenu extends ModernPopupMenu {
  private static final long serialVersionUID = 1L;

  private Map<String, ModernCheckBox> mCheckMap = new HashMap<String, ModernCheckBox>();

  public PathwayPopupMenu(Map<String, Path> pathwayMap) {
    setLayout(new BorderLayout());

    Box box = VBox.create();

    for (String name : CollectionUtils.sort(pathwayMap.keySet())) {
      ModernCheckBox menuItem = new ModernCheckBox(name, true);

      mCheckMap.put(name, menuItem);

      box.add(menuItem);
    }

    ModernScrollPane scrollPane = new ModernScrollPane(box);
    scrollPane.setBorder(ModernWidget.BORDER);

    add(scrollPane, BorderLayout.CENTER);

    UI.setSize(this, 320, 240);
  }

  public Set<String> getSelectedPathways() {
    Set<String> names = new HashSet<String>();

    for (String name : mCheckMap.keySet()) {
      if (mCheckMap.get(name).isSelected()) {
        names.add(name);
      }
    }

    return names;
  }
}
