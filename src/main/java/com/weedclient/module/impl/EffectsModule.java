package com.weedclient.module.impl;

import com.weedclient.module.Module;
import net.minecraft.client.MinecraftClient;

public class EffectsModule extends Module {
    private static final MinecraftClient client = MinecraftClient.getInstance();

    public EffectsModule() {
        super("Effects", "⚡ Visual effects");
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
        if (client.player != null) {
            // Рендерим эффекты
        }
    }
}
