package net.leaobr.rcp.cm.lifecycle;

import java.io.PrintStream;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

@SuppressWarnings("restriction")
public class Manager {

	@PostContextCreate
	public void postContextCreate(@Preference IEclipsePreferences prefs,
			IApplicationContext appContext, Display display) {
		initializeConsole();
	}

	private void initializeConsole() {
		final MessageConsole console = new MessageConsole("System Output", null);
		console.setWaterMarks(500000, 1000000);
		ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[] { console });
		MessageConsoleStream stream = console.newMessageStream();
		System.setOut(new PrintStream(stream));
		System.setErr(new PrintStream(stream));
	}
}