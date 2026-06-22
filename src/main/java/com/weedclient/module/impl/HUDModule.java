package com.weedclient.module.impl;

import com.weedclient.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class HUDModule extends Module {
    private static final MinecraftClient client = MinecraftClient.getInstance();
    public static DrawContext lastDrawContext;

    public HUDModule() {
        super("HUD", "📊 Information display");
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onTick() {
    }

    @Override
    public void onRender() {
        if (client.player != null && lastDrawContext != null) {
            renderHUD();
        }
    }

    private void renderHUD() {
        int x = 10;
        int y = 10;

        // FPS
        int fps = MinecraftClient.getInstance().getCurrentFps();
        lastDrawContext.drawTextWithShadow(client.textRenderer,
            "§aFPS: §f" + fps, x, y, 0xFFFFFF);

        // Coordinates
        double px = client.player.getX();
        double py = client.player.getY();
        double pz = client.player.getZ();
        lastDrawContext.drawTextWithShadow(client.textRenderer,
            String.format("§aXYZ: §f%.1f %.1f %.1f", px, py, pz), x, y + 12, 0xFFFFFF);

        // Direction
        String direction = getDirection(client.player.getYaw());
        lastDrawContext.drawTextWithShadow(client.textRenderer,
            "§aDir: §f" + direction, x, y + 24, 0xFFFFFF);

        // Health
        float health = client.player.getHealth();
        lastDrawContext.drawTextWithShadow(client.textRenderer,
            String.format("§aHP: §f%.1f", health), x, y + 36, 0xFFFFFF);
    }

    private String getDirection(float yaw) {
        yaw = yaw % 360;
        if (yaw < 0) yaw += 360;

        if (yaw < 45 || yaw > 315) return "S";
        if (yaw < 135) return "W";
        if (yaw < 225) return "N";
        return "E";
    }
}
