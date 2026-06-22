package com.weedclient.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Enumeration;

public class HWIDUtil {
    /**
     * Генерирует уникальный HWID на основе MAC адреса и hostname
     */
    public static String generateHWID() {
        try {
            StringBuilder hwid = new StringBuilder();
            
            // MAC адрес
            hwid.append(getMacAddress());
            
            // Hostname
            hwid.append("_");
            hwid.append(InetAddress.getLocalHost().getHostName());
            
            // Хешируем для безопасности
            return hashString(hwid.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return "UNKNOWN";
        }
    }

    /**
     * Получает MAC адрес первого активного сетевого интерфейса
     */
    private static String getMacAddress() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                if (!ni.isLoopback() && ni.isUp()) {
                    byte[] mac = ni.getHardwareAddress();
                    if (mac != null) {
                        StringBuilder sb = new StringBuilder();
                        for (byte b : mac) {
                            sb.append(String.format("%02X", b));
                        }
                        return sb.toString();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "00:00:00:00:00:00";
    }

    /**
     * SHA-256 хеширование
     */
    private static String hashString(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    /**
     * Проверяет валидность HWID
     */
    public static boolean validateHWID(String hwid) {
        if (hwid == null || hwid.isEmpty()) return false;
        // HWID должен быть SHA-256 хешем (64 символа)
        return hwid.length() == 64 && hwid.matches("[a-f0-9]+");
    }
}
