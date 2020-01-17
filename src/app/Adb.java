package app;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class Adb {
    public static String ExecWithResult(String cmd) {
        String ret = null;
        try {
            final Process proc = Runtime.getRuntime().exec(cmd);
            final InputStream inputStream = proc.getInputStream();
            proc.waitFor();
            ret = InputStreamToString(inputStream);
            proc.destroy();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static void Exec(String cmd) {
        try {
            final Process proc = Runtime.getRuntime().exec(cmd);
            proc.waitFor();
            proc.destroy();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public static void AdbStart() {
        Exec("adb start-server");
    }

    public static void AdbStop() {
        Exec("adb kill-server");
    }

    public static void AdbForward(String port1, String port2) {
        Exec("adb forward " + port1 + " " + port2);
    }

    public static String AdbDeviceBrand(String deviceID) {
        return ExecWithResult("adb -s " + deviceID + " shell getprop ro.product.brand");
    }

    public static String AdbDeviceModel(String deviceID) {
        return ExecWithResult("adb -s " + deviceID + " shell getprop ro.product.model");
    }

    public static String AdbDeviceCpu(String deviceID) {
        return ExecWithResult("adb -s " + deviceID + " shell getprop ro.product.cpu.abi");
    }

    public static String AdbDeviceManufacturer(String deviceID) {
        return ExecWithResult("adb -s " + deviceID + " shell getprop ro.product.manufacturer");
    }

    public static String[] InputStreamToArray(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream)).lines()
            .toArray(String[]::new);
    }

    public static String InputStreamToString(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream))
            .lines().collect(Collectors.joining("\n"));
    }

    public static boolean CheckAdbDevice() {
        try {
            final Process proc = Runtime.getRuntime().exec("adb devices");
            final InputStream inputStream = proc.getInputStream();
            proc.waitFor();
            final String[] inputString =  InputStreamToArray(inputStream);
            if (inputString.length > 1) {
                if (inputString[1].equals("")) {
                    return false;
                }
            }
            proc.destroy();
        } catch(final Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String GetAdbDevice() {
        try {
            final Process proc = Runtime.getRuntime().exec("adb devices");
            final InputStream inputStream = proc.getInputStream();
            proc.waitFor();
            final String[] inputString =  InputStreamToArray(inputStream);
            if (inputString.length > 1) {
                if (inputString[1].equals("")) {
                    return null;
                } else {
                    return inputString[1].substring(0, inputString[1].length()-6);
                }
            }
            proc.destroy();
        } catch(final Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}