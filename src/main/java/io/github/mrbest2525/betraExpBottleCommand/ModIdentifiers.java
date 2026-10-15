package io.github.mrbest2525.betraExpBottleCommand;

import net.minecraft.resources.Identifier;

import static io.github.mrbest2525.betraExpBottleCommand.BetraExpBottleCommand.MODID;

public class ModIdentifiers {
    public static class Permission {
        public static final Identifier EXP_BOTTLE_COMMAND = Identifier.fromNamespaceAndPath(MODID, "expbottle");
        public static final Identifier EXP_BOTTLE_COMMAND_COUNT = Identifier.fromNamespaceAndPath(MODID, "expbottle.count");
        public static final Identifier EXP_BOTTLE_COMMAND_ALL = Identifier.fromNamespaceAndPath(MODID, "expbottle.all");
    }
}
