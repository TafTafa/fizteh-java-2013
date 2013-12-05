package ru.fizteh.fivt.students.kocurba.shell.command;

import java.io.IOException;

import ru.fizteh.fivt.students.kocurba.shell.StateWrap;

public interface Command<T> {

	public int getArgCount();

	public String getCommandName();

	public void executeCommand(StateWrap<T> state, String[] arguments)
			throws IOException;
}
