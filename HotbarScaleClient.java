package com.alihan.hotbarscale;

import net.fabricmc.api.ClientModInitializer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class HotbarScaleClient implements ClientModInitializer {
    private static float scale = 1.0f;
    private static final Path CONFIG = Path.of("config", "hotbar-scale.json");

    @Override
    public void onInitializeClient() {
        try {
            Files.createDirectories(CONFIG.getParent());
            if (!Files.exists(CONFIG)) {
                Files.writeString(CONFIG, "{\n  \"scale\": 1.5\n}\n", StandardCharsets.UTF_8);
            }
            String text = Files.readString(CONFIG, StandardCharsets.UTF_8);
            Matcher matcher = Pattern.compile("\\\"scale\\\"\\s*:\\s*([0-9]+(?:\\.[0-9]+)?)").matcher(text);
            if (matcher.find()) {
                float parsed = Float.parseFloat(matcher.group(1));
                scale = Math.max(0.5f, Math.min(3.0f, parsed));
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("[Hotbar Scale] Could not read config; using 1.0");
            scale = 1.0f;
        }
    }

    public static float getScale() { return scale; }
}
