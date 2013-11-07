package ru.fizteh.fivt.students.kocurba.multifilehashmap.command;

import ru.fizteh.fivt.students.kocurba.filemap.command.State;
import ru.fizteh.fivt.students.kocurba.shell.command.Command;

public class CommandList {

	public static Command<State> createCommand = new CreateCommand();
	public static Command<State> dropCommand = new DropCommand();
	public static Command<State> useCommand = new UseCommand();
	@SuppressWarnings("rawtypes")
	public static Command[] commands = {
			ru.fizteh.fivt.students.kocurba.filemap.command.CommandList.putCommand,
			ru.fizteh.fivt.students.kocurba.filemap.command.CommandList.getCommand,
			ru.fizteh.fivt.students.kocurba.filemap.command.CommandList.removeCommand,
			ru.fizteh.fivt.students.kocurba.filemap.command.CommandList.sizeCommand,
			ru.fizteh.fivt.students.kocurba.filemap.command.CommandList.commitCommand,
			ru.fizteh.fivt.students.kocurba.filemap.command.CommandList.rollbackCommand,
			ru.fizteh.fivt.students.kocurba.filemap.command.CommandList.exitCommand,
			createCommand, dropCommand, useCommand };

	@SuppressWarnings("unchecked")
	public static Command<State>[] getCommands() {
		return commands;
	}

}
