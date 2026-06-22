package com.weedclient.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import com.weedclient.config.ConfigManager;
import com.weedclient.gui.components.ToggleButton;
import com.weedclient.module.ModuleManager;
import com.weedclient.module.Module;
import com.weedclient.util.LicenseManager;
import java.util.ArrayList;
import java.util.List;

public class VisualMenuScreen extends Screen {
    private static final int GRADIENT_START = 0xFF0D3D0D; // Темно-зеленый
    private static final int GRADIENT_END = 0xFF00FF00;   // Ярко-зеленый
    
    private int menuTab = 0;
    private List<ToggleButton> toggleButtons = new ArrayList<>();
    private float animationProgress = 0f;
    
    public VisualMenuScreen() {
        super(Text.literal("WeedClient Menu"));
    }

    @Override
    protected void init() {
        super.init();
        initializeButtons();
    }

    private void initializeButtons() {
        toggleButtons.clear();
        
        int startY = 120;
        int spacing = 45;
        int index = 0;
        
        for (Module module : ModuleManager.getModules()) {
            toggleButtons.add(new ToggleButton(
                this.width / 2 - 110, startY + (index * spacing), 220, 35,
                module.getName(), module.isEnabled(),
                (value) -> module.setEnabled(value)
            ));
            index++;
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (animationProgress < 1f) {
            animationProgress += delta * 0.05f;
        }
        
        drawEnhancedBackground(context);
        drawHeader(context);
        drawTabs(context, mouseX, mouseY);
        drawLicenseInfo(context);
        
        if (menuTab == 0) {
            drawFeaturesTab(context, mouseX, mouseY);
        } else if (menuTab == 1) {
            drawSettingsTab(context, mouseX, mouseY);
        } else {
            drawAboutTab(context, mouseX, mouseY);
        }
        
        drawFooter(context);
        super.render(context, mouseX, mouseY, delta);
    }

    private void drawEnhancedBackground(DrawContext context) {
        // Градиентный фон
        for (int y = 0; y < this.height; y++) {
            float progress = (float) y / this.height;
            int color = interpolateColor(GRADIENT_START, GRADIENT_END, progress);
            context.fill(0, y, this.width, y + 1, color);
        }
        
        // Оверлей
        context.fill(0, 0, this.width, this.height, 0x33000000);
    }

    private void drawHeader(DrawContext context) {
        int titleX = this.width / 2;
        int titleY = 15;
        
        // Большой логотип
        context.drawCenteredTextWithShadow(this.textRenderer,
            Text.literal("🌿 WEED CLIENT 🌿"),
            titleX, titleY, 0x00FF00);
        
        context.drawCenteredTextWithShadow(this.textRenderer,
            Text.literal("§7FunTime Edition v1.0.0"),
            titleX, titleY + 15, 0xAAAAAA);
    }

    private void drawLicenseInfo(DrawContext context) {
        String licenseStatus = LicenseManager.isLicenseValid() ? 
            "§a✓ Licensed" : "§c✗ Unlicensed";
        
        context.drawTextWithShadow(this.textRenderer,
            licenseStatus,
            this.width - 150, 10, 0xFFFFFF);
    }

    private void drawTabs(DrawContext context, int mouseX, int mouseY) {
        String[] tabNames = {"Features", "Settings", "About"};
        int tabWidth = 70;
        int tabHeight = 25;
        int startX = this.width / 2 - (tabWidth * 3 / 2);
        int tabY = 50;
        
        for (int i = 0; i < tabNames.length; i++) {
            int x = startX + (i * tabWidth);
            boolean isActive = menuTab == i;
            boolean isHovered = mouseX >= x && mouseX <= x + tabWidth && mouseY >= tabY && mouseY <= tabY + tabHeight;
            
            int bgColor = isActive ? 0xFF00FF00 : (isHovered ? 0xFF00AA00 : 0xFF005500);
            context.fill(x, tabY, x + tabWidth, tabY + tabHeight, bgColor);
            context.fill(x - 1, tabY - 1, x + tabWidth + 1, tabY, 0xFF00FF00);
            
            context.drawCenteredTextWithShadow(this.textRenderer,
                Text.literal("§0" + tabNames[i]),
                x + tabWidth / 2, tabY + tabHeight / 2 - 4, 0xFFFFFF);
        }
    }

    private void drawFeaturesTab(DrawContext context, int mouseX, int mouseY) {
        for (ToggleButton button : toggleButtons) {
            button.render(context, this.textRenderer, mouseX, mouseY);
        }
    }

    private void drawSettingsTab(DrawContext context, int mouseX, int mouseY) {
        int centerX = this.width / 2;
        int startY = 140;
        
        context.drawCenteredTextWithShadow(this.textRenderer,
            Text.literal("§a⚙️ Settings"),
            centerX, startY, 0x00FF00);
        
        context.drawCenteredTextWithShadow(this.textRenderer,
            Text.literal("§7More settings coming soon..."),
            centerX, startY + 50, 0xAAAAAA);
    }

    private void drawAboutTab(DrawContext context, int mouseX, int mouseY) {
        int centerX = this.width / 2;
        int startY = 100;
        
        context.drawCenteredTextWithShadow(this.textRenderer,
            Text.literal("§a§l🌿 WEED CLIENT v1.0.0 🌿"),
            centerX, startY, 0x00FF00);
        
        context.drawCenteredTextWithShadow(this.textRenderer,
            Text.literal("§7Minecraft 1.21.4 Fabric"),
            centerX, startY + 30, 0xAAAAAA);
        
        context.drawCenteredTextWithShadow(this.textRenderer,
            Text.literal("§7Visual Client for FunTime Anarchy"),
            centerX, startY + 50, 0xAAAAAA);
        
        String hwid = LicenseManager.getCurrentHWID().substring(0, 16) + "...";
        context.drawCenteredTextWithShadow(this.textRenderer,
            Text.literal("§7HWID: " + hwid),
            centerX, startY + 80, 0x00AA00);
        
        context.drawCenteredTextWithShadow(this.textRenderer,
            Text.literal("§7Made with 💚 by WeedClient Dev"),
            centerX, startY + 110, 0x00AA00);
    }

    private void drawFooter(DrawContext context) {
        context.drawCenteredTextWithShadow(this.textRenderer,
            Text.literal("§7Press ESC to close | Left Shift to toggle"),
            this.width / 2, this.height - 20, 0xAAAAAA);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        // Tab switching
        String[] tabNames = {"Features", "Settings", "About"};
        int tabWidth = 70;
        int tabHeight = 25;
        int startX = this.width / 2 - (tabWidth * 3 / 2);
        int tabY = 50;
        
        for (int i = 0; i < tabNames.length; i++) {
            int x = startX + (i * tabWidth);
            if (mouseX >= x && mouseX <= x + tabWidth && mouseY >= tabY && mouseY <= tabY + tabHeight) {
                menuTab = i;
                if (menuTab == 0) {
                    initializeButtons();
                }
                return true;
            }
        }
        
        // Toggle buttons
        for (ToggleButton toggleButton : toggleButtons) {
            if (toggleButton.isMouseOver(mouseX, mouseY)) {
                toggleButton.onClick();
                return true;
            }
        }
        
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private int interpolateColor(int startColor, int endColor, float progress) {
        int startA = (startColor >> 24) & 0xFF;
        int startR = (startColor >> 16) & 0xFF;
        int startG = (startColor >> 8) & 0xFF;
        int startB = startColor & 0xFF;

        int endA = (endColor >> 24) & 0xFF;
        int endR = (endColor >> 16) & 0xFF;
        int endG = (endColor >> 8) & 0xFF;
        int endB = endColor & 0xFF;

        int a = (int) (startA + (endA - startA) * progress);
        int r = (int) (startR + (endR - startR) * progress);
        int g = (int) (startG + (endG - startG) * progress);
        int b = (int) (startB + (endB - startB) * progress);

        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}
