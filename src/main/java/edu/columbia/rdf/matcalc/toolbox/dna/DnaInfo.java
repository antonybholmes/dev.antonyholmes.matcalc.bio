package dev.antonyholmes.matcalc.toolbox.dna;

import org.jebtk.core.AppVersion;
import dev.antonyholmes.modern.AssetService;
import dev.antonyholmes.modern.help.GuiAppInfo;

public class DnaInfo extends GuiAppInfo {

  public DnaInfo() {
    super("DNA", new AppVersion(3), "Copyright (C) 2014-2016 Antony Holmes",
        AssetService.getInstance().loadIcon(DnaIcon.class, 128),
        "Extract DNA sequences.");
  }

}
