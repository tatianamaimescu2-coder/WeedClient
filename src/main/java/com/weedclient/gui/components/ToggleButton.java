package com.weedclient.gui.components;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.Text;

public class ToggleButton {
    private int x, y, width, height;
    private String label;
    private boolean toggled;
    private Runnable onToggle;
    private float animationProgress = 0f;

    public ToggleButton(int x, int y, int width, int height, String label, boolean toggled, java.util.function.Consumer<Boolean> onToggle) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.label = label;
        this.toggled = toggled;
        this.onToggle = () -> onToggle.accept(!toggled);
    }

    public void render(DrawContext context, TextRenderer textRenderer, int mouseX, int mouseY) {
        boolean isHovered = isMouseOver(mouseX, mouseY);
        
        if (isHovered && animationProgress < 1f) {
            animationProgress += 0.1f;
        } else if (!isHovered && animationProgress > 0f) {
            animationProgress -= 0.1f;
        }

        // Draw background
        int bgColor = toggled ? 0xFF00FF00 : 0xFF00AA00;
        if (isHovered) {
            bgColor = interpolateColor(bgColor, 0xFFFFFF00, animationProgress);
        }
        
        context.fill(x, y, x + width, y + height, bgColor);

        // Draw border
        int borderColor = toggled ? 0xFF00FF00 : 0xFF00AA00;
        context.fill(x - 2, y - 2, x + width + 2, y, borderColor);
        context.fill(x - 2, y + height, x + width + 2, y + height + 2, borderColor);
        context.fill(x - 2, y, x, y + height, borderColor);
        context.fill(x + width, y, x + width + 2, y + height, borderColor);

        // Draw toggle switch
        int switchX = x + width - 45;
        int switchY = y + height / 2 - 8;
        int switchWidth = 40;
        int switchHeight = 16;
        
        int switchBgColor = toggled ? 0xFF00FF00 : 0xFF555555;
        context.fill(switchX, switchY, switchX + switchWidth, switchY + switchHeight, switchBgColor);
        
        int dotX = toggled ? switchX + switchWidth - 12 : switchX + 2;
        context.fill(dotX, switchY + 2, dotX + 12, switchY + switchHeight - 2, 0xFFFFFFFF);

        // Draw label
        int textColor = 0xFF000000;
        context.drawTextWithShadow(textRenderer, label, x + 15, y + height / 2 - 4, textColor);

        // Draw status
        String status = toggled ? "§aON" : "§cOFF";
        context.drawTextWithShadow(textRenderer, status, x + width - 35, y + height / 2 - 4, 0xFFFFFFFF);
    }

    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public void onClick() {
        toggled = !toggled;
        onToggle.run();
    }

    private int interpolateColor(int startColor, int endColor, float progress) {
        int startR = (startColor >> 16) & 0xFF;
        int startG = (startColor >> 8) & 0xFF;
        int startB = startColor & 0xFF;

        int endR = (endColor >> 16) & 0xFF;
        int endG = (endColor >> 8) & 0xFF;
        int endB = endColor & 0xFF;

        int r = (int) (startR + (endR - startR) * progress);
        int g = (int) (startG + (endG - startG) * progress);
        int b = (int) (startB + (endB - startB) * progress);

        return 0xFF000000 | (r << 16) | (g << 8) | b;
    }
}
