package com.jvm;

import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.Arrays;

import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;


public class MemoryTest0 {

	public static void main(String[] args) {
		try {
			JMXServiceURL jmxUrl = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://127.0.0.1:9930/jmxrmi");
			JMXConnector connector = JMXConnectorFactory.connect(jmxUrl);
			MBeanServerConnection connection = connector.getMBeanServerConnection();
			System.out.println(connection);
			
			//OperatingSystemMXBean、ThreadMXBean
			MemoryMXBean memBean = ManagementFactory.newPlatformMXBeanProxy(connection, ManagementFactory.MEMORY_MXBEAN_NAME, MemoryMXBean.class);
			
			{
				Arrays.asList("PS MarkSweep", "PS Scavenge", "ParNew").forEach(gName->{
					try {
						String key = ManagementFactory.GARBAGE_COLLECTOR_MXBEAN_DOMAIN_TYPE+",name="+gName;
						if(connection.isRegistered(new ObjectName(key))) {
							GarbageCollectorMXBean bean = ManagementFactory.newPlatformMXBeanProxy(connection, key, GarbageCollectorMXBean.class);
							System.out.printf(
									"%s, 收集%d次, 用时:%d毫秒\n",
									bean.getName(),
									bean.getCollectionCount(),
									bean.getCollectionTime()
							);
						}
					} catch (IOException e) {
						e.printStackTrace();
					} catch (MalformedObjectNameException e) {
						e.printStackTrace();
					}
				});
			}
			{
				System.out.println("============");
				MemoryPoolMXBean bean = ManagementFactory.newPlatformMXBeanProxy(connection, ManagementFactory.MEMORY_POOL_MXBEAN_DOMAIN_TYPE+",name=PS Survivor Space", MemoryPoolMXBean.class);
				System.out.println(bean.getUsage());
				System.out.println(bean.getMemoryManagerNames()[0]);
				System.out.println(bean.getMemoryManagerNames()[1]);
				System.out.println(bean.getPeakUsage());
				System.out.println(bean);
			}
			
			MemoryUsage heap = memBean.getHeapMemoryUsage();
			System.out.println(heap.getUsed());
			System.out.println(memBean.getNonHeapMemoryUsage());
			
			//Eden区
			//https://www.iteye.com/topic/1117196
			
		} catch (IOException  e) {
			e.printStackTrace();
		}
	}

}
