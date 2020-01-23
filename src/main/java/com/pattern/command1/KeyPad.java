package com.pattern.command1;

public class KeyPad implements Action{
	private Command playCommand;
	private Command accelerateCommand;
	private Command decelerateCommand;
	private Command pauseCommand;
	private Command rewindCommand;
	private Command stopCommand;
	public Command getPlayCommand() {
		return playCommand;
	}
	public void setPlayCommand(Command playCommand) {
		this.playCommand = playCommand;
	}
	public Command getAccelerateCommand() {
		return accelerateCommand;
	}
	public void setAccelerateCommand(Command accelerateCommand) {
		this.accelerateCommand = accelerateCommand;
	}
	public Command getDecelerateCommand() {
		return decelerateCommand;
	}
	public void setDecelerateCommand(Command decelerateCommand) {
		this.decelerateCommand = decelerateCommand;
	}
	public Command getPauseCommand() {
		return pauseCommand;
	}
	public void setPauseCommand(Command pauseCommand) {
		this.pauseCommand = pauseCommand;
	}
	public Command getRewindCommand() {
		return rewindCommand;
	}
	public void setRewindCommand(Command rewindCommand) {
		this.rewindCommand = rewindCommand;
	}
	public Command getStopCommand() {
		return stopCommand;
	}
	public void setStopCommand(Command stopCommand) {
		this.stopCommand = stopCommand;
	}
	@Override
	public void play() {
		playCommand.execute();
	}
	@Override
	public void accelerate() {
		accelerateCommand.execute();
	}
	@Override
	public void decelerate() {
		decelerateCommand.execute();
	}
	@Override
	public void pause() {
		pauseCommand.execute();
	}
	@Override
	public void rewind() {
		rewindCommand.execute();
	}
	@Override
	public void stop() {
		stopCommand.execute();
	}
}
