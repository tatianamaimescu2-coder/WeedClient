package com.weedclient;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import com.weedclient.util.LicenseManager;
import com.weedclient.module.ModuleManager;
import com.weedclient.module.impl.HUDModule;
import net.minecraft.client.gui.DrawContext;

public class WeedClientClient implements ClientModInitializer {
    public static KeyBinding openMenuKey;

    @Override
    public void onInitializeClient() {
        // Проверяем лицензию
        if (!LicenseManager.isLicenseValid()) {
            WeedClientMod.LOGGER.warn("[WeedClient] License is invalid! Mod may not work properly.");
        } else {
            WeedClientMod.LOGGER.info("[WeedClient] License valid. HWID: " + LicenseManager.getCurrentHWID().substring(0, 16) + "...");
        }
        
        // Регистрируем левый Shift
        openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.weedclient.open_menu",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_LEFT_SHIFT,
            "category.weedclient"
        ));

        // Событие каждый тик
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMenuKey.wasPressed()) {
                if (client.player != null) {
                    client.setScreen(new com.weedclient.gui.VisualMenuScreen());
                }
            }
            
            // Тики для модулей
            ModuleManager.onTick();
        });
        
        // HUD рендер
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            HUDModule.lastDrawContext = drawContext;
            ModuleManager.onRender();
        });

        WeedClientMod.LOGGER.info("WeedClient Client initialized!");
    }
}
