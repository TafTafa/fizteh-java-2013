package ru.fizteh.fivt.students.kocurba.shell.command;

import java.io.File;
import java.io.IOException;

import ru.fizteh.fivt.students.kocurba.shell.ShellState;

public class PwdCommand implements Command<File> {

	@Override
	public int getArgCount() {
		return 0;
	}

	@Override
	public String getCommandName() {
		return "pwd";
	}

	@Override
	public void executeCommand(ShellState<File> state, String[] arguments)
			throws IOException {

		try {
			System.out.println(state.getState().getCanonicalPath());
		} catch (IOException ex) {
			throw new IOException("pwd : getCanonicalPath error");
		}
	}
}