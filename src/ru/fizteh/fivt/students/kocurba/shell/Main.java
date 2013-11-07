package ru.fizteh.fivt.students.kocurba.shell;

import java.io.File;
import java.io.IOException;

import ru.fizteh.fivt.students.kocurba.shell.command.Command;
import ru.fizteh.fivt.students.kocurba.shell.command.DirCommand;
import ru.fizteh.fivt.students.kocurba.shell.command.ExitCommand;
import ru.fizteh.fivt.students.kocurba.shell.command.MkdirCommand;
import ru.fizteh.fivt.students.kocurba.shell.command.PwdCommand;
import ru.fizteh.fivt.students.kocurba.shell.command.RmCommand;

/***
 * 
 * Task 02 - Shell
 * 
 * @author Alina Kotsurba
 * 
 */
public class Main {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {

		File workingDir = new File(System.getProperty("user.dir"));
		ShellState<File> state = new ShellState<File>(workingDir);
		Shell<File> shell = new Shell<File>(state);

		Command<File> pwd = new PwdCommand();

		Command<File> dir = new DirCommand();

		Command<File> mkdir = new MkdirCommand();

		Command<File> rm = new RmCommand();

		Command<File> exit = new ExitCommand();

		@SuppressWarnings("rawtypes")
		Command[] command = { pwd, dir, mkdir, rm, exit };

		if (0 != args.length) {
			shell.batchMode(args, command);
		} else {
			shell.interactiveMode(command);
		}
		System.exit(0);
	}
}
