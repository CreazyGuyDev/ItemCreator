package pl.creazy.itemcreator.effect;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;
import pl.creazy.creazylib.util.mc.Mc;

import java.io.Serializable;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
public class SerializableEffect implements Serializable {
  private final String effectName;
  private final int duration;
  private final int amplifier;

  public @NotNull PotionEffect intoEffect() {
    return new PotionEffect(Objects.requireNonNull(Mc.getPotionEffectTypeFromString(effectName)), duration, amplifier);
  }
}
