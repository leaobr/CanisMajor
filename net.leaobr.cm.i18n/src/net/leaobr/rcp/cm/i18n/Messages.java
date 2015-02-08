package net.leaobr.rcp.cm.i18n;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "net.leaobr.e4.rcp.cm.i18n.messages"; //$NON-NLS-1$
	public static String TodoOverviewPart_0;
	public static String TodoDeletionPart_0;
	// More string values....
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
