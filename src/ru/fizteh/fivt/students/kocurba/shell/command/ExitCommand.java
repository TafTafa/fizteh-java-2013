package ru.fizteh.fivt.students.kocurba.shell.command;

import java.io.File;
import java.io.IOException;

import ru.fizteh.fivt.students.kocurba.shell.ShellState;

public class ExitCommand implements Command<File> {

	@Override
	public int getArgCount() {
		return 0;
	}

	@Override
	public String getCommandName() {
		return "exit";
	}

	@Override
	public void executeCommand(ShellState<File> state, String[] arguments)
			throws IOException {
	}

}