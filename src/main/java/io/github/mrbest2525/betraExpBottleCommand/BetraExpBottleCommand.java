package io.github.mrbest2525.betraExpBottleCommand;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.permission.v1.PermissionPredicates;
import net.minecraft.commands.Commands;


public class BetraExpBottleCommand implements ModInitializer {
    
    public static final String MODID = BuildConfig.MODID;
    
    @Override
    public void onInitialize() {
        ModConfig.load();
        ServerLanguageManager.init();
        
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            var experienceBottleCommand = dispatcher.register(
                    Commands.literal("experiencebottle")
                            .requires(PermissionPredicates.require(ModIdentifiers.Permission.EXP_BOTTLE_COMMAND, true))
                            .then(
                                    Commands.argument("amount", IntegerArgumentType.integer(1))
                                            .requires(PermissionPredicates.require(ModIdentifiers.Permission.EXP_BOTTLE_COMMAND_COUNT, true))
                                            .executes(ExpBottleCommand::executeWithCount)
                            )
                            .then(
                                    Commands.literal("all")
                                            .requires(PermissionPredicates.require(ModIdentifiers.Permission.EXP_BOTTLE_COMMAND_ALL, true))
                                            .executes(ExpBottleCommand::executeAll)
                            )
            );
            
            dispatcher.register(
                    Commands.literal("expbottle")
                            .redirect(experienceBottleCommand)
            );
            
            dispatcher.register(
                    Commands.literal("bottle")
                            .redirect(experienceBottleCommand)
            );
            
            dispatcher.register(
                    Commands.literal("bt")
                            .redirect(experienceBottleCommand)
            );
        });
    }
    
}
