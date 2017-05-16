package com.mckesson.eig.roi.conversion.util;

import java.io.Console;

public class ConsoleUtil {

	public static String readLine(String prompt) {
		Console console = System.console();
		if(console != null) {
			return console.readLine(prompt);
		} else {
			System.out.print(prompt);
			return new java.util.Scanner(System.in).nextLine();
		}
	}
	
	public static String readPassword(String prompt) {
		Console console = System.console();
		if(console != null) {
			return new String(console.readPassword(prompt));
		} else {
			System.out.print(prompt);
			return new java.util.Scanner(System.in).nextLine();
		}
	}
}
