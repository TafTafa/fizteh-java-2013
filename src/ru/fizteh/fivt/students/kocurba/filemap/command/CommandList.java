package ru.fizteh.fivt.students.kocurba.filemap.command;

import ru.fizteh.fivt.students.kocurba.shell.command.Command;

public class CommandList {

	public static Command<State> putCommand = new PutCommand();
	public static Command<State> getCommand = new GetCommand();
	public static Command<State> removeCommand = new RemoveCommand();
	public static Command<State> sizeCommand = new SizeCommand();
	public static Command<State> commitCommand = new CommitCommand();
	public static Command<State> rollbackCommand = new RollbackCommand();
	public static Command<State> exitCommand = new ExitCommand();
	@SuppressWarnings("rawtypes")
	public static Command[] commands = { putCommand, getCommand, removeCommand,
			exitCommand };

	@SuppressWarnings("unchecked")
	public static Command<State>[] getCommands() {
		return commands;
	}

}
