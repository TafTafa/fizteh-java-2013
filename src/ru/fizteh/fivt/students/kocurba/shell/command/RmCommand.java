package ru.fizteh.fivt.students.kocurba.shell.command;

import java.io.File;
import java.io.IOException;

import ru.fizteh.fivt.students.kocurba.shell.ShellState;

public class RmCommand implements Command<File> {

	@Override
	public int getArgCount() {
		return 1;
	}

	@Override
	public String getCommandName() {
		return "rm";
	}

	private void recursiveRm(File folder) throws IOException { // toRemove,
																// toDelete
		if (folder.isDirectory()) {
			File[] files = folder.listFiles();
			for (File f : files) {
				recursiveRm(f);
			}
			folder.delete();
		} else {
			if (!folder.delete()) {
				throw new IOException("File delete error");
			}
		}
	}

	@Override
	public void executeCommand(ShellState<File> state, String[] arguments)
			throws IOException {

		File file = new File(state.getState().getAbsolutePath() + "/"
				+ arguments[1]);
		recursiveRm(file);

	}
}
