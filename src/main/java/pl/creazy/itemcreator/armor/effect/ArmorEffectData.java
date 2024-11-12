package pl.creazy.itemcreator.armor.effect;

import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import pl.creazy.creazylib.util.math.Numbers;
import pl.creazy.itemcreator.effect.SerializableEffect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArmorEffectData implements Serializable {
  private final Map<SerializableEffect, Double> damageEffects = new HashMap<>();
  private final List<SerializableEffect> alwaysEffects = new ArrayList<>();

  public void setDamageEffect(@NotNull SerializableEffect effect, Double percentChance) {
    damageEffects.put(effect, percentChance);
  }

  public void setAlwaysEffect(@NotNull SerializableEffect effect) {
    alwaysEffects.removeIf(other -> other.getEffectName().equals(effect.getEffectName()));
    alwaysEffects.add(effect);
  }

  public void applyDamageEffects(@NotNull LivingEntity entity) {
    damageEffects.forEach((effect, chance) -> {
      if (Numbers.percent(chance)) {
        effect.intoEffect().apply(entity);
      }
    });
  }

  public void applyAlwaysEffects(@NotNull LivingEntity entity) {
    alwaysEffects.forEach(effect -> effect.intoEffect().apply(entity));
  }
}
