package io.github.mrbest2525.betraExpBottleCommand;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static io.github.mrbest2525.betraExpBottleCommand.BetraExpBottleCommand.MODID;

public class ServerLanguageManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    private static final Path LANG_DIR = FabricLoader.getInstance().getConfigDir().resolve(MODID).resolve("lang");
    private static final Gson GSON = new Gson();
    
    private static final Map<String, Map<String, String>> LANGUAGES = new HashMap<>();
    
    public static void init() {
        File dir = LANG_DIR.toFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        FabricLoader.getInstance().getModContainer(MODID).ifPresent(modContainer -> {
            Path internalLangPath = modContainer.findPath("assets/" + MODID + "/lang").orElse(null);
            
            if (internalLangPath != null && Files.exists(internalLangPath)) {
                try (Stream<Path> paths = Files.walk(internalLangPath, 1)) {
                    paths.filter(Files::isRegularFile)
                            .filter(p -> p.toString().endsWith(".json"))
                            .forEach(p -> {
                                String fileName = p.getFileName().toString();
                                File targetFile = LANG_DIR.resolve(fileName).toFile();
                                
                                if (!targetFile.exists()) {
                                    try (InputStream is = Files.newInputStream(p)) {
                                        Files.copy(is, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                                        LOGGER.info("Automatically generated the default language file: {}", fileName);
                                    } catch (Exception e) {
                                        LOGGER.error("Failed to copy the language file {}: {}", fileName, e.getMessage());
                                    }
                                }
                            });
                } catch (Exception e) {
                    LOGGER.error("Failed to scan the language directory in the mod: {}", e.getMessage());
                }
            } else {
                LOGGER.warn("Language directory not found in the mod: assets/{}/lang", MODID);
            }
        });
    }
    
    public static Component translatable(String locale, String key, Object... args) {
        String cleanLocale = locale.toLowerCase();
        
        Map<String, String> langMap = LANGUAGES.computeIfAbsent(cleanLocale, ServerLanguageManager::loadLangFile);
        
        if (!langMap.containsKey(key) && !cleanLocale.equals("en_us")) {
            langMap = LANGUAGES.computeIfAbsent("en_us", ServerLanguageManager::loadLangFile);
        }
        
        String format = langMap.getOrDefault(key, key);
        
        try {
            return Component.literal(String.format(format, args));
        } catch (Exception e) {
            LOGGER.error("An error occurred during the translation formatting process. Key: {}", key, e);
            return Component.literal(format);
        }
    }
    
    private static Map<String, String> loadLangFile(String locale) {
        File file = LANG_DIR.resolve(locale + ".json").toFile();
        if (!file.exists()) {
            return new HashMap<>();
        }
        
        try (FileReader reader = new FileReader(file)) {
            java.lang.reflect.Type type = new TypeToken<Map<String, String>>(){}.getType();
            Map<String, String> loaded = GSON.fromJson(reader, type);
            LOGGER.info("Loaded the server-side language file: {}.json", locale);
            return loaded != null ? loaded : new HashMap<>();
        } catch (Exception e) {
            LOGGER.error("Failed to load the language file: {}", file.getName(), e);
            return new HashMap<>();
        }
    }
}
