package net.leaobr.cm.execution;

import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.e4.core.di.annotations.Execute;

/**
 * Evaluate the existing extension points. This uses ISafeRunnable interface,
 * it protects the plug-in which defines the extension point from malfunction extensions.
 * If an extension throws an Exception, it will be caught by ISafeRunnable and the remaining
 * extensions will still get executed.
 */
public class EvaluateContributionsHandler {
	private static final String IENVEXECUTOR_ID =
			"net.leaobr.cm.execution.environment";

	@Execute
	public void execute(IExtensionRegistry registry) {
		evaluate(registry, null, null);
	}

	private void evaluate(IExtensionRegistry registry, ExecutionContext context, Map<String, Object> arguments) {
		IConfigurationElement[] config =
				registry.getConfigurationElementsFor(IENVEXECUTOR_ID);
		try {
			for (IConfigurationElement e : config) {
				System.out.println("Evaluating extension");
				final Object o =
						e.createExecutableExtension("class");
				if (o instanceof IEnvironmentExecutor) {
					executeExtension(o, context, arguments);
				}
			}
		} catch (CoreException ex) {
			System.out.println(ex.getMessage());
		}
	}

	private void executeExtension(final Object o, final ExecutionContext context, final Map<String, Object> arguments) {
		ISafeRunnable runnable = new ISafeRunnable() {
			@Override
			public void handleException(Throwable e) {
				System.out.println("Exception in client");
			}

			@Override
			public void run() throws Exception {
				((IEnvironmentExecutor) o).execute(context, arguments);
			}
		};
		SafeRunner.run(runnable);
	}
}
