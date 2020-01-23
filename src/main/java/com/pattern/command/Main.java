package com.pattern.command;

public class Main {
	public static void main(String[] args) {
		Receiver receiver = new Receiver();
		ConcreteCommand command = new ConcreteCommand(receiver);
		Invoker invoker = new Invoker(command);
		
		invoker.doAction();
	}
}
