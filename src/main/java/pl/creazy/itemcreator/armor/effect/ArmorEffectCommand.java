package pl.creazy.itemcreator.armor.effect;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.creazy.creazylib.command.TabCompleteHandler;
import pl.creazy.creazylib.command.constraints.Args;
import pl.creazy.creazylib.command.constraints.Command;
import pl.creazy.creazylib.command.constraints.HasPermissions;
import pl.creazy.creazylib.data.persistence.nbt.NbtEditor;
import pl.creazy.creazylib.util.mc.Mc;
import pl.creazy.itemcreator.constants.Keys;
import pl.creazy.itemcreator.effect.SerializableEffect;

import java.util.List;
import java.util.function.Consumer;

@Command("armoreffect")
class ArmorEffectCommand implements TabCompleteHandler {
  @Args("damage ?s ?i ?i ?d")
  @HasPermissions("itemcreator.armoreffect.damage")
  void addDamageEffect(Player sender, String effectName, int duration, int amplifier, double percentChance) {
    updateArmorEffectData(sender, effectData -> {
      effectData.setDamageEffect(new SerializableEffect(effectName, duration, amplifier), percentChance);
    });
  }

  @Args("always ?s ?i")
  @HasPermissions("itemcreator.armoreffect.always")
  void addAlwaysEffect(Player sender, String effectName, int amplifier) {
    updateArmorEffectData(sender, effectData -> {
      effectData.setAlwaysEffect(new SerializableEffect(effectName, 25, amplifier));
    });
  }

  private void updateArmorEffectData(Player player, Consumer<ArmorEffectData> consumer) {
    var item = player.getInventory().getItemInMainHand();
    var nbt = NbtEditor.of(item);
    var armorEffectData = nbt.find(Keys.ARMOR_EFFECTS, ArmorEffectData.class).orElse(new ArmorEffectData());
    consumer.accept(armorEffectData);
    nbt.set(Keys.ARMOR_EFFECTS, armorEffectData);
    nbt.save();
  }

  @Override
  public @Nullable List<String> handleTabComplete(@NotNull Player player, @NotNull String[] args) {
    if (args.length == 2) {
      return Mc.EFFECTS.stream()
          .map(effect -> effect.getKey().getKey())
          .toList();

    }
    if (args.length == 3) {
      if ("damage".equals(args[0])) {
        return List.of("Enter duration");
      }
      if ("always".equals(args[0])) {
        return List.of("Enter amplifier");
      }
    }
    if (args.length == 4) {
      if ("damage".equals(args[0])) {
        return List.of("Enter amplifier");
      }
    }
    if (args.length == 5) {
      if ("damage".equals(args[0])) {
        return List.of("Enter percent chance");
      }
    }
    return null;
  }
}
