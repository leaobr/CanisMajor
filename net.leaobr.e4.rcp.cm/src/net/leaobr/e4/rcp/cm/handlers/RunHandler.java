package net.leaobr.e4.rcp.cm.handlers;

import net.leaobr.e4.rcp.cm.parts.NetworkViewPart;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.widgets.Shell;
import org.nmap4j.Nmap4j;
import org.nmap4j.core.nmap.NMapExecutionException;
import org.nmap4j.core.nmap.NMapInitializationException;

public class RunHandler {
	@Execute
	public void execute(EPartService partService,
			IWorkbench workbench, Shell shell) {
		NetworkViewPart.nmap4j = new Nmap4j(NetworkViewPart.searchField.getText());
		NetworkViewPart.nmap4j.addFlags("-sn -PR");
		NetworkViewPart.nmap4j.includeHosts("192.168.1.0/24");
		try {
			NetworkViewPart.nmap4j.execute();
			NetworkViewPart.viewer.refresh();
		} catch (NMapInitializationException e) {
			e.printStackTrace();
		} catch (NMapExecutionException e) {
			e.printStackTrace();
		}
	}
}
