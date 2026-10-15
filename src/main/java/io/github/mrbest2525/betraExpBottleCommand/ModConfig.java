package io.github.mrbest2525.betraExpBottleCommand;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.concurrent.ThreadLocalRandom;

import static io.github.mrbest2525.betraExpBottleCommand.BetraExpBottleCommand.MODID;

public class ModConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(MODID + ModConfig.class);
    
    private static final File FILE = FabricLoader.getInstance().getConfigDir().resolve(MODID + ".json").toFile();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    public int costMin = 3;
    public int costMax = 11;
    
    public static ModConfig INSTANCE = new ModConfig();
    
    public static void load() {
        if (FILE.exists()) {
            try (FileReader reader = new FileReader(FILE)) {
                INSTANCE = GSON.fromJson(reader, ModConfig.class);
            } catch (Exception e) {
                LOGGER.error("Error occurred while loading config file.", e);
            }
        } else {
            LOGGER.info("Config file Not Found!");
            LOGGER.info("Config file creating...");
            save();
        }
    }
    
    public static void save() {
        try (FileWriter writer = new FileWriter(FILE)) {
            GSON.toJson(INSTANCE, writer);
        } catch (Exception e) {
            LOGGER.error("Error occurred while saving config file.", e);
        }
    }
    
    public int getRandomCost() {
        if (costMin >= costMax) return costMin;
        return ThreadLocalRandom.current().nextInt(costMin, costMax + 1);
    }
}
