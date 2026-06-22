package com.weedclient.module;

import com.weedclient.module.impl.*;
import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    private static final List<Module> modules = new ArrayList<>();

    static {
        // Регистрируем все модули
        registerModule(new FullbrightModule());
        registerModule(new ParticlesModule());
        registerModule(new EffectsModule());
        registerModule(new HUDModule());
        registerModule(new AnimationsModule());
        registerModule(new ColorModule());
    }

    public static void registerModule(Module module) {
        modules.add(module);
    }

    public static List<Module> getModules() {
        return modules;
    }

    public static Module getModuleByName(String name) {
        return modules.stream()
                .filter(m -> m.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public static void onTick() {
        modules.forEach(m -> {
            if (m.isEnabled()) {
                m.onTick();
            }
        });
    }

    public static void onRender() {
        modules.forEach(m -> {
            if (m.isEnabled()) {
                m.onRender();
            }
        });
    }

    public static void toggleModule(String name) {
        Module module = getModuleByName(name);
        if (module != null) {
            module.setEnabled(!module.isEnabled());
        }
    }
}
