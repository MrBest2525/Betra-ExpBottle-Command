package io.github.mrbest2525.betraExpBottleCommand;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ExpBottleCommand {
    public static void giveItem(ServerPlayer player, ItemStack itemStack, int count) {
        int maxStackSize = itemStack.getMaxStackSize();
        while (count > 0) {
            int stackSize = Math.min(count, maxStackSize);
            count -= stackSize;
            ItemStack copyToGive = itemStack.copyWithCount(stackSize);
            boolean added = player.getInventory().add(copyToGive);
            if (!added || !copyToGive.isEmpty()) {
                ItemEntity drop = player.drop(copyToGive, false);
                if (drop != null) {
                    drop.setNoPickUpDelay();
                    drop.setTarget(player.getUUID());
                }
            }
        }
        player.containerMenu.broadcastChanges();
    }
    
    public static int executeWithCount(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) {
            context.getSource().sendFailure(ServerLanguageManager.translatable("en_us", LangKeys.SOURCE_IS_NOT_PLAYER));
            return 0;
        }
        
        int amount = context.getArgument("amount", Integer.class);
        
        int giveCount = 0;
        int totalCost = 0;
        int lastPlayerLevel = player.experienceLevel;
        float lastPlayerProgress = player.experienceProgress;
        int lastPlayerTotalExperience = player.totalExperience;
        while (giveCount < amount) {
            int lastCost = ModConfig.INSTANCE.getRandomCost();
            player.giveExperiencePoints(-lastCost);
            if (player.experienceLevel <= 0 && player.experienceProgress <= 0) {
                player.experienceLevel =  lastPlayerLevel;
                player.experienceProgress = lastPlayerProgress;
                player.totalExperience = lastPlayerTotalExperience;
                break;
            }
            lastPlayerLevel = player.experienceLevel;
            lastPlayerProgress = player.experienceProgress;
            lastPlayerTotalExperience = player.totalExperience;
            
            totalCost += lastCost;
            giveCount++;
        }
        
        giveItem(player, new ItemStack(Items.EXPERIENCE_BOTTLE), giveCount);
        
        final int sendTotalCost = totalCost;
        final int sendGiveCount = giveCount;
        context.getSource().sendSuccess(() -> ServerLanguageManager.translatable(player.clientInformation().language(), LangKeys.COMMAND_SUCCESS_AMOUNT, sendTotalCost, sendGiveCount), false);
        return 1;
    }
    
    public static int executeAll(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) {
            context.getSource().sendFailure(ServerLanguageManager.translatable("en_us", LangKeys.SOURCE_IS_NOT_PLAYER));
            return 0;
        }
        
        if (player.experienceLevel <= 0 && player.experienceProgress <= 0) {
            context.getSource().sendFailure(ServerLanguageManager.translatable(player.clientInformation().language(), LangKeys.NOT_ENOUGH_EXPERIENCE_POINTS));
            return 0;
        }
        
        int giveCount = 0;
        int totalCost = 0;
        int lastPlayerLevel = player.experienceLevel;
        float lastPlayerProgress = player.experienceProgress;
        int lastPlayerTotalExperience = player.totalExperience;
        while (true) {
            int lastCost = ModConfig.INSTANCE.getRandomCost();
            player.giveExperiencePoints(-lastCost);
            if (player.experienceLevel <= 0 && player.experienceProgress <= 0) {
                player.experienceLevel =  lastPlayerLevel;
                player.experienceProgress = lastPlayerProgress;
                player.totalExperience = lastPlayerTotalExperience;
                break;
            }
            lastPlayerLevel = player.experienceLevel;
            lastPlayerProgress = player.experienceProgress;
            lastPlayerTotalExperience = player.totalExperience;
            
            totalCost += lastCost;
            giveCount++;
        }
        
        giveItem(player, new ItemStack(Items.EXPERIENCE_BOTTLE), giveCount);
        
        final int sendTotalCost = totalCost;
        final int sendGiveCount = giveCount;
        context.getSource().sendSuccess(() -> ServerLanguageManager.translatable(player.clientInformation().language(), LangKeys.COMMAND_SUCCESS_AMOUNT, sendTotalCost, sendGiveCount), false);
        return 1;
    }
}
