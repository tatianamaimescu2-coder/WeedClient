package com.weedclient.module.impl;

import com.weedclient.module.Module;
import net.minecraft.client.MinecraftClient;

public class ParticlesModule extends Module {
    private static final MinecraftClient client = MinecraftClient.getInstance();

    public ParticlesModule() {
        super("Particles", "🎨 Enhanced particles");
    }

    @Override
    public void onEnable() {
        // Включаем максимум частиц
    }

    @Override
    public void onDisable() {
        // Возвращаем дефолтные настройки
    }

    @Override
    public void onTick() {
        if (client.player != null) {
            // Логика для частиц
        }
    }

    @Override
    public void onRender() {
    }
}
