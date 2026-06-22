package com.weedclient.module.impl;

import com.weedclient.module.Module;
import net.minecraft.client.MinecraftClient;

public class FullbrightModule extends Module {
    private static final MinecraftClient client = MinecraftClient.getInstance();
    private double previousGamma = 1.0;

    public FullbrightModule() {
        super("Fullbright", "💡 Full brightness");
    }

    @Override
    public void onEnable() {
        if (client.options != null) {
            previousGamma = client.options.getGamma().getValue();
            client.options.getGamma().setValue(16.0);
        }
    }

    @Override
    public void onDisable() {
        if (client.options != null) {
            client.options.getGamma().setValue(previousGamma);
        }
    }

    @Override
    public void onTick() {
    }

    @Override
    public void onRender() {
    }
}
