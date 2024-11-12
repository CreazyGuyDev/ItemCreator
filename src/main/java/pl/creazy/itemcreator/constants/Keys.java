package pl.creazy.itemcreator.constants;

import lombok.experimental.UtilityClass;
import org.bukkit.NamespacedKey;
import pl.creazy.creazylib.util.key.Key;
import pl.creazy.itemcreator.ItemCreator;

@UtilityClass
public class Keys {
  public static final NamespacedKey ARMOR_EFFECTS = createKey("armor_effects");

  private static NamespacedKey createKey(String key) {
    return Key.create(key, ItemCreator.class);
  }
}
