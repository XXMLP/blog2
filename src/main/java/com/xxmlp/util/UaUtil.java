package com.xxmlp.util;

import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

public class UaUtil {


    public static String getDeviceType( String agentString) {
        // ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        UserAgent userAgent = UserAgent.parseUserAgentString(agentString);
        OperatingSystem operatingSystem = userAgent.getOperatingSystem(); // 操作系统信息
        eu.bitwalker.useragentutils.DeviceType deviceType = operatingSystem.getDeviceType(); // 设备类型

        switch (deviceType) {
            case COMPUTER:
                return "PC";
            case TABLET: {
                if (agentString.contains("Android")) return "Android Pad";
                if (agentString.contains("iOS")) return "iPad";
                return "Unknown";
            }
            case MOBILE: {
                if (agentString.contains("Android")) return "Android";
                if (agentString.contains("iOS")) return "IOS";
                return "Unknown";
            }
            default:
                return "Unknown";
        }

    }

}
