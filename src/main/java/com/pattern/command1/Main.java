package com.pattern.command1;

public class Main {
	public static void main(String[] args) {
		
		KeyPad keyPad = new KeyPad();
		{
			AudioPlayer audioPlayer = new AudioPlayer();
			keyPad.setPlayCommand(new Command.PlayCommand(audioPlayer));
			keyPad.setAccelerateCommand(new Command.AcclerateCommand(audioPlayer));
			keyPad.setDecelerateCommand(new Command.DeclerateCommand(audioPlayer));
			keyPad.setPauseCommand(new Command.PauseCommand(audioPlayer));
			keyPad.setRewindCommand(new Command.RewindCommand(audioPlayer));
			keyPad.setStopCommand(new Command.StopCommand(audioPlayer));
		}
		System.out.println();
	}
}
