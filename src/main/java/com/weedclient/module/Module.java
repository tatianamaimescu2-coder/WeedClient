package com.weedclient.module;

public abstract class Module {
    protected String name;
    protected String description;
    protected boolean enabled = false;
    protected int key = -1; // Keybind

    public Module(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Вызывается когда модуль включается
     */
    public abstract void onEnable();

    /**
     * Вызывается когда модуль выключается
     */
    public abstract void onDisable();

    /**
     * Вызывается каждый тик (для обновлений)
     */
    public abstract void onTick();

    /**
     * Вызывается при рендере (для визуальных эффектов)
     */
    public abstract void onRender();

    public void setEnabled(boolean enabled) {
        if (this.enabled != enabled) {
            this.enabled = enabled;
            if (enabled) {
                onEnable();
            } else {
                onDisable();
            }
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
