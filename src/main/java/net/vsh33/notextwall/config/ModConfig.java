package net.vsh33.notextwall.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ModConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    private static final Path CONFIG_PATH = Paths.get("config/notextwall.json");

    @Expose
    private int maxMessageLength = 100; // Default value
    @Expose
    private boolean showCancelMessage = true;

    // Getter and Setter
    public int getMaxMessageLength() {
        return maxMessageLength;
    }
    public boolean getShowCancelMessage() { return showCancelMessage; }


    public void setMaxMessageLength(int maxMessageLength) {
        this.maxMessageLength = maxMessageLength;
    }
    public void setShowCancelMessage(boolean showCancelMessage) { this.showCancelMessage = showCancelMessage; }

    // Load the configuration from file
    public static ModConfig load() {
        try {
            if (!Files.exists(CONFIG_PATH)) {
                save(new ModConfig());
            }
            return GSON.fromJson(new FileReader(CONFIG_PATH.toFile()), ModConfig.class);
        } catch (IOException e) {
            throw new RuntimeException("Error loading config file", e);
        }
    }

    // Save the configuration to file
    public static void save(ModConfig config) {
        try (Writer writer = new FileWriter(CONFIG_PATH.toFile())) {
            GSON.toJson(config, writer);
        } catch (IOException e) {
            throw new RuntimeException("Error saving config file", e);
        }
    }
}
