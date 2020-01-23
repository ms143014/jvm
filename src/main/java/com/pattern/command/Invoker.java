package com.pattern.command;

public class Invoker {
	private Command command;
	public Invoker(Command command) {
		super();
		this.command = command;
	}
	public void doAction() {
		command.execute();
	}
}
