package jvm;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.stream.IntStream;

import org.junit.Test;

import com.jvm.ThreadUtils;

public class SocketChannelTest {
	@Test
	public void server0() throws IOException{
		final String serverReponseText = "pong..";
		
		try(ServerSocketChannel serverChannel = ServerSocketChannel.open()){
			serverChannel.bind(new InetSocketAddress("192.168.100.2", 8088));
			serverChannel.configureBlocking(false);
			
			ByteBuffer buffer = ByteBuffer.wrap(serverReponseText.getBytes());
			
			while(true) {
				System.out.println("等待连接...");
				SocketChannel clientChannel = serverChannel.accept();
				if(clientChannel == null) {
					int seconds = 10;
					IntStream.range(0, seconds).forEach(i->{
						ThreadUtils.sleep(1000); //60秒
						System.out.println((seconds-i) + "秒后才醒过来");
					});
				}else {
					System.out.println("Incoming connection from " + clientChannel.socket().getRemoteSocketAddress());
					buffer.rewind();
					clientChannel.write(buffer);
					ThreadUtils.sleep(1000);
//					clientChannel.close();
				}
			}
		}
	}
	@Test
	public void clientTest0() throws IOException {
		IntStream.range(0, 1).forEach(i->{
			try {
				SocketChannel channel = SocketChannel.open();
				boolean connected = channel.connect(new InetSocketAddress("192.168.100.2", 8088));
				System.out.println("是否已经连接:"+ connected);
				System.out.println("finishConnect:"+channel.finishConnect());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		});
		ThreadUtils.sleep(1000*1000);
	}
	@Test
	public void hello() {
		try(ServerSocketChannel serverChannel = ServerSocketChannel.open()){
			//设置为non-blocking模式
			serverChannel.configureBlocking(false);
			serverChannel.bind(new InetSocketAddress(8088));
			try(Selector selector = Selector.open()){
				serverChannel.register(selector, SelectionKey.OP_ACCEPT|SelectionKey.OP_READ|SelectionKey.OP_WRITE);
				while(true) {
					System.out.println(System.currentTimeMillis());
					if(selector.select() > 0) { //形成阻塞，等待客户端连接过来
						
					}
				}
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
