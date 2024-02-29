package dev.antonyholmes.matcalc.toolbox.regions;

import org.jebtk.core.AppVersion;
import dev.antonyholmes.modern.AssetService;
import dev.antonyholmes.modern.help.GuiAppInfo;

public class RegionsInfo extends GuiAppInfo {

  public RegionsInfo() {
    super("Regions", new AppVersion(28), "Copyright (C) 2014-${year} Antony Holmes",
        AssetService.getInstance().loadIcon(RegionsIcon.class, 128), "Annotate genomic regions.");
  }

}
