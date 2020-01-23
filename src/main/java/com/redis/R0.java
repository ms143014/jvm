package com.redis;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.apache.commons.lang3.StringUtils;

public class R0 {
	public void setString() throws Exception {
		Socket socket = new Socket();
		socket.setReuseAddress(true);
		socket.setKeepAlive(true);
		socket.setTcpNoDelay(true);
		socket.setSoLinger(true, 0);
		socket.connect(new InetSocketAddress("ecs.180830.com", 6379), 3000);
		socket.setSoTimeout(3000);
		OutputStream os = socket.getOutputStream();
		InputStream is = socket.getInputStream();
		byte[] buffer = new byte[1024];
		
        os.write("AUTH redisecs\r\n".getBytes());
        os.flush();
        
        
        int offset = is.read(buffer);
        System.out.println(new String(buffer,0, offset));
        
        os.write(("lrange coursesx 10 20\r\n").getBytes());
        os.flush();
        
        /*String[]args = {"*3", "$3", "set", "$5", "namex", "$8", "deadpool"};
        os.write((StringUtils.join(args, "\r\n") + "\r\n").getBytes());
        os.flush();*/
        
        read(is);
        
//        os.write("get t0\r\n".getBytes());
//        os.flush();
//        
//        read(is);
        		
        is.close();
        os.close();
        socket.close();
        
	}
	public static void read(InputStream is) throws IOException {
		byte[]buffer = new byte[1024];
		int offset = is.read(buffer);
        System.out.println(new String(buffer,0, offset));
	}
}
