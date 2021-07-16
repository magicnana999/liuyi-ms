package com.creolophus.liuyi.common.id;

import java.net.NetworkInterface;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Enumeration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author magicnana
 * @date 2019/10/18 下午2:56
 */
public class MachineInfo {

  private static final Logger logger = LoggerFactory.getLogger(MachineInfo.class);


  private static final int LOW_ORDER_THREE_BYTES = 0x00ffffff;

  private static final int MACHINE_IDENTIFIER;
  private static final short PROCESS_IDENTIFIER;

  static {
    try {
      MACHINE_IDENTIFIER = createMachineIdentifier();
      PROCESS_IDENTIFIER = createProcessIdentifier();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static int getMachineIdentifier() {
    return MACHINE_IDENTIFIER;
  }

  public static short getProcessIdentifier() {
    return PROCESS_IDENTIFIER;

  }

  private static int createMachineIdentifier() {
    // build a 2-byte machine piece based on NICs info
    int machinePiece;
    try {
      StringBuilder sb = new StringBuilder();
      Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
      while (e.hasMoreElements()) {
        NetworkInterface ni = e.nextElement();
        sb.append(ni.toString());
        byte[] mac = ni.getHardwareAddress();
        if (mac != null) {
          ByteBuffer bb = ByteBuffer.wrap(mac);
          try {
            sb.append(bb.getChar());
            sb.append(bb.getChar());
            sb.append(bb.getChar());
          } catch (BufferUnderflowException shortHardwareAddressException) { //NOPMD
            // mac with less than 6 bytes. continue
          }
        }
      }
      machinePiece = sb.toString().hashCode();
    } catch (Throwable t) {
      // exception sometimes happens with IBM JVM, use random
      machinePiece = (new SecureRandom().nextInt());
      logger.warn(
          "Failed to get machine identifier from network interface, using random number instead",
          t);
    }
    machinePiece = machinePiece & LOW_ORDER_THREE_BYTES;
    return machinePiece;
  }

  // Creates the process identifier.  This does not have to be unique per class loader because
  // NEXT_COUNTER will provide the uniqueness.
  private static short createProcessIdentifier() {
    short processId;
    try {
      String processName = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
      if (processName.contains("@")) {
        processId = (short) Integer.parseInt(processName.substring(0, processName.indexOf('@')));
      } else {
        processId = (short) java.lang.management.ManagementFactory.getRuntimeMXBean().getName()
            .hashCode();
      }

    } catch (Throwable t) {
      processId = (short) new SecureRandom().nextInt();
      logger.warn("Failed to get process identifier from JMX, using random number instead", t);
    }

    return processId;
  }
}
