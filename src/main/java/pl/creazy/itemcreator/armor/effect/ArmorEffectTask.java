package pl.creazy.itemcreator.armor.effect;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pl.creazy.creazylib.data.persistence.nbt.NbtEditor;
import pl.creazy.creazylib.task.TaskRun;
import pl.creazy.creazylib.task.constraints.Task;
import pl.creazy.itemcreator.constants.Keys;

@Task(run = TaskRun.TIMER, period = 20)
class ArmorEffectTask extends BukkitRunnable {
  @Override
  public void run() {
    Bukkit.getOnlinePlayers().forEach(player -> {
      var equipment = player.getEquipment();

      if (equipment == null) {
        return;
      }

      for (var armor : equipment.getArmorContents()) {
        if (armor == null || armor.getItemMeta() == null) {
          continue;
        }

        var effectaData = NbtEditor.of(armor).get(Keys.ARMOR_EFFECTS, ArmorEffectData.class);

        if (effectaData == null) {
          continue;
        }

        effectaData.applyAlwaysEffects(player);
      }
    });
  }
}
