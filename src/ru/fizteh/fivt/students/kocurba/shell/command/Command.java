package ru.fizteh.fivt.students.kocurba.shell.command;

import java.io.IOException;

import ru.fizteh.fivt.students.kocurba.shell.ShellState;

public interface Command<T> {

	public int getArgCount();

	public String getCommandName();

	public void executeCommand(ShellState<T> state, String[] arguments)
			throws IOException;
}
