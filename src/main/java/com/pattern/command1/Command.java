package com.pattern.command1;

public interface Command {
	public void execute();
	public static abstract class AbstractCommand implements Command{
		private Action action;
		public AbstractCommand(Action action) {
			super();
			this.action = action;
		};
	}
	public static class PlayCommand extends AbstractCommand{
		public PlayCommand(Action action) {
			super(action);
		}
		@Override
		public void execute() {
			super.action.play();
		}
	}
	public static class AcclerateCommand extends AbstractCommand{
		public AcclerateCommand(Action action) {
			super(action);
		}
		@Override
		public void execute() {
			super.action.accelerate();
		}
	}
	public static class DeclerateCommand extends AbstractCommand{
		public DeclerateCommand(Action action) {
			super(action);
		}
		@Override
		public void execute() {
			super.action.decelerate();
		}
	}
	public static class PauseCommand extends AbstractCommand{
		public PauseCommand(Action action) {
			super(action);
		}
		@Override
		public void execute() {
			super.action.pause();
		}
	}
	public static class RewindCommand extends AbstractCommand{
		public RewindCommand(Action action) {
			super(action);
		}
		@Override
		public void execute() {
			super.action.rewind();
		}
	}
	public static class StopCommand extends AbstractCommand{
		public StopCommand(Action action) {
			super(action);
		}
		@Override
		public void execute() {
			super.action.stop();
		}
	}
}
