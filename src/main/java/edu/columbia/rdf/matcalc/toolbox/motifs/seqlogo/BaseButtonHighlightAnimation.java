package dev.antonyholmes.matcalc.toolbox.motifs.seqlogo;

import java.awt.Color;
import java.awt.Graphics2D;

import org.jebtk.bioinformatics.genomic.SequenceService;
import org.jebtk.core.ColorUtils;
import org.jebtk.core.Props;
import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.event.ChangeListener;
import org.jebtk.core.geom.IntRect;
import dev.antonyholmes.modern.ModernWidget;
import dev.antonyholmes.modern.button.ButtonFillAnimation;
import dev.antonyholmes.modern.theme.DrawUIService;

public class BaseButtonHighlightAnimation extends ButtonFillAnimation {
  private BaseButton mButton;

  public BaseButtonHighlightAnimation(BaseButton button) {
    super(button);

    mButton = button;

    // setFadeColor("fill", Color.WHITE);

    // When color changes, change with it
    SequenceService.getInstance().addChangeListener(button.getBase(), new ChangeListener() {

      @Override
      public void changed(ChangeEvent e) {
        setFadeColor();
      }
    });

    setFadeColor();
  }

  private void setFadeColor() {
    Color c1 = SequenceService.getInstance().getBaseColor(mButton.getBase());

    Color c2 = ColorUtils.tint(c1, 0.3);

    setFadeColor("fill", c1, c2);
  }

  @Override
  public void draw(ModernWidget c, Graphics2D g2, Props props) {
    if (getWidget().isEnabled()) {

      int x = (mButton.getWidth() - BaseButton.SIZE) / 2; // PADDING;
      int y = (mButton.getHeight() - BaseButton.SIZE) / 2;
      int w = BaseButton.SIZE; // - 1;

      // UIDrawService.getInstance().get("circle-fill").draw(g2, x, y, w,
      // w, SequenceService.getInstance().getBaseColor(mButton.getBase()));

      DrawUIService.getInstance().getRenderer("circle-fill").draw(g2, new IntRect(x, y, w, w), getFadeColor("fill"));
    }
  }
}
