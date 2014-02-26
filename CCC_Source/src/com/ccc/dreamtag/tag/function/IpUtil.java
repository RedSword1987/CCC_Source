package com.ccc.dreamtag.tag.function;

import java.net.InetAddress;

/**
 * @author RedSword(416448382@qq.com)
 * @date 2013-11-10 22:21:33
 */
public class IpUtil {
    public static Long convertIpaddr2Long(String str) {
        InetAddress addr = null;
        try {
            addr = InetAddress.getByName(str);
        } catch (Exception e) {
            return Long.valueOf(-1);
        }
        return convertBytes2Long(addr.getAddress());
    }

    private static Long convertBytes2Long(byte[] addr) {
        int address = 0;
        if (addr != null) {
            if (addr.length == 4) {
                address = addr[3] & 0xFF;
                address |= ((addr[2] << 8) & 0xFF00);
                address |= ((addr[1] << 16) & 0xFF0000);
                address |= ((addr[0] << 24) & 0xFF000000);
            }
        }
        return Long.valueOf(address);
    }

    public static String convertLong2Ipaddr(String ipaddrL) {
        return convertLong2Ipaddr(Long.valueOf(ipaddrL));
    }

    public static String convertLong2Ipaddr(Long ipaddr) {
        byte[] bytes = convertLong2Bytes(ipaddr);
        InetAddress addr = null;
        try {
            addr = InetAddress.getByAddress(bytes);
        } catch (Exception e) {
            return null;
        }
        return addr.getHostAddress();
    }

    private static byte[] convertLong2Bytes(Long value) {
        byte[] addr = new byte[4];
        addr[0] = (byte) ((value >>> 24) & 0xFF);
        addr[1] = (byte) ((value >>> 16) & 0xFF);
        addr[2] = (byte) ((value >>> 8) & 0xFF);
        addr[3] = (byte) (value & 0xFF);
        return addr;
    }

}
