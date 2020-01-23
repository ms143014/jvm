package com.rpc;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Scanner;

class RpcProviderTest {
	public static void main(String[] args) {
		new Thread(()->{
			try {
				RpcProvider.pubilsh("localhost", 8088);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}) .start();
	}
}
class RpcConsumerTest{
	public static void main(String[] args) {
		RpcConsumer<EchoService> rpcConsumer = new RpcConsumer<>();
		EchoService echoService = rpcConsumer.consumer(EchoServiceImpl.class, new InetSocketAddress("localhost", 8088));
		System.out.println(echoService.echo("Oh shit!!!"));
		try(Scanner scanner = new Scanner(System.in)){
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				System.out.println(echoService.echo(line));
			}
		}
	}
}