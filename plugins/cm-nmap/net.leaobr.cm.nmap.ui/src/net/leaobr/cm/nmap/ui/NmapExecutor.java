package net.leaobr.cm.nmap.ui;

import java.util.Map;

import net.leaobr.cm.execution.ExecutionContext;
import net.leaobr.cm.execution.IEnvironmentExecutor;

import org.nmap4j.Nmap4j;
import org.nmap4j.core.nmap.NMapExecutionException;
import org.nmap4j.core.nmap.NMapInitializationException;
import org.nmap4j.data.NMapRun;

public class NmapExecutor implements IEnvironmentExecutor {
	protected static Nmap4j nmap4j;
	protected static NMapRun nmapRun;
	protected static String nmapPath;

	@Override
	public Object execute(ExecutionContext context, Map<String, Object> arguments) throws Exception {
		nmap4j = new Nmap4j(NetworkViewPart.searchField.getText());
		nmap4j.addFlags("-sn -PR");
		nmap4j.includeHosts("192.168.1.0/24");
		try {
			nmap4j.execute();
			NetworkViewPart.viewer.refresh();
		} catch (NMapInitializationException e) {
			e.printStackTrace();
		} catch (NMapExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}

}
