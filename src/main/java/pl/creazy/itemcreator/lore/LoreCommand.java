package pl.creazy.itemcreator.lore;

import org.bukkit.entity.Player;
import pl.creazy.creazylib.command.constraints.Args;
import pl.creazy.creazylib.command.constraints.Command;
import pl.creazy.creazylib.command.constraints.HasPermissions;
import pl.creazy.creazylib.util.text.Text;

import java.util.Arrays;
import java.util.Objects;

@Command("lore")
class LoreCommand {
  @Args("?s")
  @HasPermissions("itemcreator.lore")
  void setItemLore(Player sender, String rawLore) {
    var lore = Text.color(rawLore.replaceAll("/s", " ")).split(",");
    var item = sender.getInventory().getItemInMainHand();
    var meta = item.getItemMeta();
    Objects.requireNonNull(meta).setLore(Arrays.asList(lore));
    item.setItemMeta(meta);
  }
}
