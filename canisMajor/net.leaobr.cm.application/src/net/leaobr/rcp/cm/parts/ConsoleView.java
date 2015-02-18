/*
 * #%L
 * ORQA Console
 * %%
 * Copyright (C) 2010 - 2014 IB BOOST LTD
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package net.leaobr.rcp.cm.parts;

import java.io.PrintStream;

import org.eclipse.ui.IStartup;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public class ConsoleView implements IStartup {
	@Override
	public void earlyStartup() {
		final MessageConsole console = new MessageConsole("System Output", null);
		console.setWaterMarks(500000, 1000000);
		ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[] { console });
		MessageConsoleStream stream = console.newMessageStream();
		System.setOut(new PrintStream(stream));
		System.setErr(new PrintStream(stream));
		stream.setActivateOnWrite(true);
	}
}
