package pl.creazy.itemcreator.attribute;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.creazy.creazylib.command.TabCompleteHandler;
import pl.creazy.creazylib.command.constraints.Args;
import pl.creazy.creazylib.command.constraints.Command;
import pl.creazy.creazylib.command.constraints.HasPermissions;
import pl.creazy.creazylib.util.key.Key;
import pl.creazy.itemcreator.ItemCreator;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("UnstableApiUsage")
@Command("attribute")
class AttributeCommand implements TabCompleteHandler {
  @Args("?s ?e ?d ?e ?s")
  @HasPermissions("itemcreator.attribute")
  void setItemAttributeModifier(Player sender, String key, Attribute attribute, double amount, AttributeModifier.Operation operation, String slotGroup) {
    var item = sender.getInventory().getItemInMainHand();
    var modifier = new AttributeModifier(Key.create(key, ItemCreator.class), amount, operation, parseSlotGroup(slotGroup));
    var meta = item.getItemMeta();

    Objects.requireNonNull(meta).addAttributeModifier(attribute, modifier);

    item.setItemMeta(meta);
  }

  private EquipmentSlotGroup parseSlotGroup(String name) {
    return switch (name.toLowerCase()) {
      case "any" -> EquipmentSlotGroup.ANY;
      case "armor" -> EquipmentSlotGroup.ARMOR;
      case "chest" -> EquipmentSlotGroup.CHEST;
      case "feet" -> EquipmentSlotGroup.FEET;
      case "hand" -> EquipmentSlotGroup.HAND;
      case "head" -> EquipmentSlotGroup.HEAD;
      case "legs" -> EquipmentSlotGroup.LEGS;
      case "mainhand" -> EquipmentSlotGroup.MAINHAND;
      case "offhand" -> EquipmentSlotGroup.OFFHAND;
      default -> throw new RuntimeException("failed to parse slot group");
    };
  }

  @Override
  public @Nullable List<String> handleTabComplete(@NotNull Player player, @NotNull String[] args) {
    if (args.length == 1) {
      return List.of("enter unique key");
    }
    if (args.length == 2) {
      return Arrays.stream(Attribute.values())
          .map(Enum::name)
          .toList();
    }
    if (args.length == 3) {
      return List.of("Enter amount");
    }
    if (args.length == 4) {
      return Arrays.stream(AttributeModifier.Operation.values())
          .map(Enum::name)
          .toList();
    }
    if (args.length == 5) {
      return List.of("any", "armor", "chest", "feet", "hand", "head", "legs", "mainhand", "offhand");
    }
    return null;
  }
}
