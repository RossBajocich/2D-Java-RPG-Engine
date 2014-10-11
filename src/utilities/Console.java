package utilities;

public class Console {

	public static enum in {
		ERROR, INFO,
	}

	private static boolean print = true, logging;

	public static void log(String msg, in i) {
		String header = "";
		if (i == in.ERROR) {
			header = "ERROR >>> ";
		} else if (i == in.INFO) {
			header = "INFO: ";
		}
		if (print) {
			System.out.println(header + msg);
		}
		if (logging) {
			// logFile.write(header + msg); TODO something along these lines
		}
	}

	public static void setLogging(boolean state) {
		logging = state;
	}

	public static void setPrintable(boolean state) {
		print = state;
	}
}
