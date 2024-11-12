package pl.creazy.itemcreator.armor.effect;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import pl.creazy.creazylib.data.persistence.nbt.NbtEditor;
import pl.creazy.creazylib.listener.constraints.EventHandlers;
import pl.creazy.itemcreator.constants.Keys;

@EventHandlers
class ArmorEffectListener implements Listener {
  @EventHandler
  void handleArmorDamageEffects(EntityDamageEvent event) {
    if (!(event.getEntity() instanceof LivingEntity entity)) {
      return;
    }

    var equipment = entity.getEquipment();

    if (equipment == null) {
      return;
    }

    for (var armor : equipment.getArmorContents()) {
      if (armor == null || armor.getItemMeta() == null) {
        continue;
      }

      var effectData = NbtEditor.of(armor).get(Keys.ARMOR_EFFECTS, ArmorEffectData.class);

      if (effectData == null) {
        continue;
      }

      effectData.applyDamageEffects(entity);
    }
  }
}
