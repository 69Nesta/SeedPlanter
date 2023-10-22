package fr.nesta.seedplanter;

import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

import static fr.nesta.seedplanter.Main.materialEnchants;

public class CustomEnchants {
    public static final Enchantment SEEDPLANTER = new EnchantmentWrapper("seedplanter", "SeedPlanter", 1, materialEnchants);

    public static void register() {
        boolean registred = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(SEEDPLANTER);

        if (!registred) {
            registerEnchantment(SEEDPLANTER);

        }

    }

    public static void registerEnchantment(Enchantment enchantment) {
        boolean registred = true;

        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
            Enchantment.registerEnchantment(enchantment);

        } catch (Exception e){
            registred = false;
            e.printStackTrace();
        }
        if (registred) {
            // can add log
        }
    }
}
