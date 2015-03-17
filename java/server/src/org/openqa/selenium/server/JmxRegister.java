package org.openqa.selenium.server;

import org.weakref.jmx.MBeanExporter;

import java.lang.management.ManagementFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

/**
 * This centralizes logic for registering Selenium objects for JMX monitoring.
 */
public final class JmxRegister {
  private static final Logger log = Logger.getLogger(JmxRegister.class.getName());
  private final MBeanExporter exporter;

  public JmxRegister() {
    if(System.getProperty("com.sun.management.jmxremote") != null) {
      exporter = new MBeanExporter(ManagementFactory.getPlatformMBeanServer());
    } else {
      exporter = null;
    }
  }

  /**
   * Registers an object with JMX simply.
   * No-op when JMX is disabled
   * Errors are disregarded
   *
   * @param name the JMX ObjectName formatted key for the object
   * @param obj the object to register
   */
  public void maybeRegister(final String name, final Object obj) {
    try {
      maybeRegister(new ObjectName(name), obj);
    } catch (MalformedObjectNameException e) {
      log.warning("Failing to register malformed ObjectName with JMX: " + name);
      log.log(Level.FINEST, e.getMessage(), e);
    }
  }

  /**
   * Registers an object with JMX simply.
   * No-op when JMX is disabled
   * Errors are disregarded
   *
   * @param objectName the JMX key for the object
   * @param obj the object to register
   */
  public void maybeRegister(final ObjectName objectName, final Object obj) {
    if(exporter != null) {
      exporter.export(objectName, obj);
    }
  }

  /**
   * Unregisters an object with JMX simply.
   * No-op when JMX is disabled
   * Errors are disregarded
   *
   * @param name the JMX ObjectName formatted key for the object
   */
  public void maybeUnregister(final String name) {
    try {
      maybeUnregister(new ObjectName(name));
    } catch (MalformedObjectNameException e) {
      log.warning("Failing to register malformed ObjectName with JMX: " + name);
      log.log(Level.FINEST, e.getMessage(), e);
    }
  }

  /**
   * Unregisters an object with JMX simply.
   * No-op when JMX is disabled
   * Errors are disregarded
   *
   * @param objectName the JMX ObjectName formatted key for the object
   */
  public void maybeUnregister(final ObjectName objectName) {
    if(exporter != null) {
      exporter.unexport(objectName);
    }
  }
}


