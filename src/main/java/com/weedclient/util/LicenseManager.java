package com.weedclient.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LicenseManager {
    private static final Path LICENSE_DIR = Paths.get("./WeedClient/licenses");
    private static final Path LICENSE_FILE = LICENSE_DIR.resolve("license.txt");
    
    private static String currentHWID;
    private static boolean licenseValid = false;

    static {
        try {
            Files.createDirectories(LICENSE_DIR);
            currentHWID = HWIDUtil.generateHWID();
            loadLicense();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Загружает лицензию из файла
     */
    private static void loadLicense() {
        try {
            if (Files.exists(LICENSE_FILE)) {
                String licenseContent = Files.readString(LICENSE_FILE);
                String[] parts = licenseContent.trim().split(":");
                
                if (parts.length == 2) {
                    String storedHWID = parts[0];
                    String licenseKey = parts[1];
                    
                    // Проверяем HWID
                    if (storedHWID.equals(currentHWID)) {
                        licenseValid = validateLicenseKey(licenseKey, currentHWID);
                    } else {
                        System.out.println("[WeedClient] HWID mismatch! License invalid.");
                        licenseValid = false;
                    }
                }
            } else {
                // Создаем пустой файл лицензии
                createDefaultLicense();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Создает дефолтный файл лицензии
     */
    private static void createDefaultLicense() {
        try {
            String defaultLicense = currentHWID + ":" + generateDefaultKey(currentHWID);
            Files.writeString(LICENSE_FILE, defaultLicense);
            licenseValid = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Генерирует лицензионный ключ на основе HWID
     */
    private static String generateDefaultKey(String hwid) {
        // Генерируем простой ключ на основе HWID
        return hwid.substring(0, 16).toUpperCase() + "-" +
               hwid.substring(16, 32).toUpperCase() + "-" +
               hwid.substring(32, 48).toUpperCase() + "-" +
               hwid.substring(48, 64).toUpperCase();
    }

    /**
     * Проверяет валидность лицензионного ключа
     */
    private static boolean validateLicenseKey(String licenseKey, String hwid) {
        String expectedKey = generateDefaultKey(hwid);
        return licenseKey.equals(expectedKey);
    }

    /**
     * Проверяет активна ли лицензия
     */
    public static boolean isLicenseValid() {
        return licenseValid;
    }

    /**
     * Получает текущий HWID
     */
    public static String getCurrentHWID() {
        return currentHWID;
    }

    /**
     * Сохраняет новую лицензию
     */
    public static void saveLicense(String licenseKey) {
        try {
            String licenseContent = currentHWID + ":" + licenseKey;
            Files.writeString(LICENSE_FILE, licenseContent);
            licenseValid = validateLicenseKey(licenseKey, currentHWID);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
