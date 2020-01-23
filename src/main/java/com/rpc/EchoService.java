package com.rpc;

public interface EchoService {
	public String echo(String ping);
}
class EchoServiceImpl implements EchoService{
	@Override
	public String echo(String ping) {
		return ping != null? ping + "--> I am ok.": " I am ok";
	}
}
