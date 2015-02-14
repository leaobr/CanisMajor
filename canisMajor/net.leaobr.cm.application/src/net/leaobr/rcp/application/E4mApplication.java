package net.leaobr.rcp.application;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.internal.workbench.swt.E4Application;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

@SuppressWarnings("restriction")
public class E4mApplication implements IApplication {

	private static E4mApplication instance;

	public static E4mApplication getInstance() {
		return instance;
	}

	private Integer exitRet = IApplication.EXIT_OK;
	private E4Application e4app;

	public void setRestart() {
		exitRet = IApplication.EXIT_RESTART;
	}

	@Override
	public Object start(IApplicationContext context) throws Exception {
		instance = this;
		e4app = new E4Application();
		E4Workbench workbench = e4app.createE4Workbench(context, e4app.getApplicationDisplay());
		workbench.createAndRunUI(workbench.getApplication());
		e4app.start(context);
		return exitRet;
	}

	@Execute
	public void execute(IWorkbench workbench) { //http://stackoverflow.com/questions/12869859/howto-restart-an-e4-rcp-application
		E4mApplication.getInstance().setRestart();
		workbench.close();
	}

	@Override
	public void stop() {
		e4app.stop();
	}
}
