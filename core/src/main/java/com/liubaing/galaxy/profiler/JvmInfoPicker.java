package com.liubaing.galaxy.profiler;

import com.liubaing.galaxy.util.Constants;
import com.sun.management.OperatingSystemMXBean;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author heshuai
 * @version 15-7-8.
 */
public class JvmInfoPicker {

    private long uptime;
    private long processCpuTime;
    private GarbageCollectorMXBean youngGC;
    private GarbageCollectorMXBean fullGC;
    private static JvmInfoPicker instance = new JvmInfoPicker();

    private JvmInfoPicker() {
        List<GarbageCollectorMXBean> gcList = ManagementFactory.getGarbageCollectorMXBeans();
        if (gcList != null && !gcList.isEmpty()) {
            if (gcList.size() == 1) {
                this.youngGC = gcList.get(0);
            } else if (gcList.size() >= 2) {
                this.youngGC = gcList.get(0);
                this.fullGC = gcList.get(1);
            }
        }
    }

    public static JvmInfoPicker getInstance() {
        return instance;
    }

    public String getStartTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(ManagementFactory.getRuntimeMXBean().getStartTime());
    }

    public long getThreadCount() {
        return ManagementFactory.getThreadMXBean().getThreadCount();
    }

    public int getPid() {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        try {
            return Integer.parseInt(name.substring(0, name.indexOf(64)));
        } catch (Exception e) {
            return -1;
        }
    }


    public String getMemory() {
        OperatingSystemMXBean osbean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        StringBuilder sb = new StringBuilder();
        sb.append("Mem").append("=").append(humanReadableByteCount(osbean.getTotalPhysicalMemorySize(), true)).append(Constants.SEMICOLON_SEPARATOR);
        sb.append("Swap").append("=").append(humanReadableByteCount(osbean.getTotalSwapSpaceSize(), true)).append(Constants.SEMICOLON_SEPARATOR);
        sb.append("Heap").append("=").append(humanReadableByteCount(getMaxHeapMemoryUsage(), true)).append(Constants.HYPHEN).append(humanReadableByteCount(getHeapMemoryUsage(), true));
        return sb.toString();
    }

    private String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }


    public long getHeapMemoryUsage() {
        try {
            return ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed();
        } catch (Throwable var2) {
            return 0L;
        }
    }

    public long getMaxHeapMemoryUsage() {
        try {
            return ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getMax();
        } catch (Throwable var2) {
            return 0L;
        }
    }

    public String getHostName() {
        if (System.getenv("COMPUTERNAME") != null) {
            return System.getenv("COMPUTERNAME");
        } else {
            try {
                return InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException var4) {
                String host = var4.getMessage();
                if (host != null) {
                    int colon = host.indexOf(58);
                    if (colon > 0) {
                        return host.substring(0, colon);
                    }
                }
                return "UnknownHost";
            }
        }
    }

    public String getCpu() {
        OperatingSystemMXBean osbean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        long uptimeNow = ManagementFactory.getRuntimeMXBean().getUptime();
        long processCpuTimeNow = osbean.getProcessCpuTime();
        String cpu = "0.0";
        if (this.uptime > 0L && this.processCpuTime > 0L) {
            long l2 = uptimeNow - this.uptime;
            long l1 = processCpuTimeNow - this.processCpuTime;
            if (l2 > 0L) {
                float cpuValue = Math.min(99.0F, (float) l1 / ((float) l2 * 10000.0F * (float) osbean.getAvailableProcessors()));
                DecimalFormat df = new DecimalFormat("##0.0##");
                cpu = df.format((double) cpuValue);
            }
        }
        this.uptime = uptimeNow;
        this.processCpuTime = processCpuTimeNow;
        return cpu;
    }
}
