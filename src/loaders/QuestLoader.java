package loaders;

import org.python.core.PyException;

public class QuestLoader {

	public static Quest loadQuest(String pyFile) throws PyException {
		/*
		String f = pyFile;
		Quest q = new Quest();

		if (!f.endsWith(".py")) {
			f = f + ".py";
		}

		try {
			PythonInterpreter.initialize(System.getProperties(),
					System.getProperties(), new String[0]);
			PythonInterpreter interp = new PythonInterpreter();
			interp.execfile(ResourceLoader.getFile(f).getAbsolutePath());
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
		return q;
		*/
		return null;
	}

	public static Quest getQuest(String fileName) {
		return null;
		//return loadQuest(fileName);
	}
}
