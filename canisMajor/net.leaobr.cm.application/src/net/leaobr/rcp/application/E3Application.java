package net.leaobr.rcp.application;

import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.osgi.framework.Bundle;

@SuppressWarnings("restriction")
public class E3Application implements IApplication {

	@Override
	public Object start(IApplicationContext context) throws Exception {
		PlatformUI.getPreferenceStore().setValue(IWorkbenchPreferenceConstants.SHOW_PROGRESS_ON_STARTUP, true);

		Display display = PlatformUI.createDisplay();
		try {
			//ProjectManager.getDefaultInstance().refresh();
			int returnCode = PlatformUI.createAndRunWorkbench(display, new ApplicationWorkbenchAdvisor());
			if (returnCode == PlatformUI.RETURN_RESTART) {
				return IApplication.EXIT_RESTART;
			}
		} finally {
			display.dispose();
		}

		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		if (Workbench.getInstance().isRunning()) {
			final IWorkbench workbench = (IWorkbench) PlatformUI.getWorkbench();
			final Display display = Workbench.getInstance().getDisplay();
			display.syncExec(new Runnable() {
				@Override
				public void run() {
					if (!display.isDisposed())
						workbench.close();
				}
			});
		}
	}

	private class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

		@Override
		public String getInitialWindowPerspectiveId() {
			return "test.rcp.perspective";
		}

		@Override
		public void initialize(IWorkbenchConfigurer configurer) {
			super.initialize(configurer);
			// inserted: register workbench adapters
			IDE.registerAdapters();

			// inserted: register images for rendering explorer view
			final String ICONS_PATH = "icons/full/";
			final String PATH_OBJECT = ICONS_PATH + "obj16/";
			Bundle ideBundle = Platform.getBundle(IDEWorkbenchPlugin.IDE_WORKBENCH);
			declareWorkbenchImage(configurer, ideBundle,
					IDE.SharedImages.IMG_OBJ_PROJECT, PATH_OBJECT + "prj_obj.gif", true);
			declareWorkbenchImage(configurer, ideBundle,
					IDE.SharedImages.IMG_OBJ_PROJECT_CLOSED, PATH_OBJECT + "cprj_obj.gif", true);
		}

		private void declareWorkbenchImage(IWorkbenchConfigurer configurer_p,
				Bundle ideBundle, String symbolicName, String path, boolean shared) {
			URL url = ideBundle.getEntry(path);
			ImageDescriptor desc = ImageDescriptor.createFromURL(url);
			configurer_p.declareImage(symbolicName, desc, shared);
		}

	}
}
