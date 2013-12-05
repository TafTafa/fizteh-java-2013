package ru.fizteh.fivt.students.kocurba.shell;

import java.io.IOException;
import java.util.Scanner;

import ru.fizteh.fivt.students.kocurba.shell.command.Command;

public class Shell<T> {

	public StateWrap<T> state;

	public Shell(StateWrap<T> startingState) {
		state = startingState;
	}

	public void interactiveMode(Command<T>[] commands) {
		Scanner inputScanner = new Scanner(System.in);
		CmdExecutor<T> executor = new CmdExecutor<T>(commands);

		System.out.print("$ ");

		String command = inputScanner.nextLine();
		while (true) {
			command = command.trim();

			try {

				String[] commandArgs = command.split("\\s+");
				executor.execute(state, commandArgs);

			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
			if (command.equals("exit")) {
				inputScanner.close();
				return;
			}
			System.out.print("$ ");
			command = inputScanner.nextLine();
		}

	}

	public void batchMode(String[] args, Command<T>[] com) {

		CmdExecutor<T> executor = new CmdExecutor<T>(com);

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < args.length - 1; ++i) {
			sb.append(args[i]).append(" ");
		}
		sb.append(args[args.length - 1]);
		sb.append(" ; ");

		String commands[] = sb.toString().split(";");
		for (String command : commands) {
			command = command.trim();

			try {
				String[] commandArgs = command.split("\\s+");
				executor.execute(state, commandArgs);

			} catch (IOException e) {
				System.err.println(e.getMessage());
				return;
			}
			if (command.equals("exit")) {
				return;
			}

		}

	}
}
