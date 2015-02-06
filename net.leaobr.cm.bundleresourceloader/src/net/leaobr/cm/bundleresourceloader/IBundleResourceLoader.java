package net.leaobr.cm.bundleresourceloader;

import org.eclipse.swt.graphics.Image;

public interface IBundleResourceLoader {
	public Image loadImage(Class<?> clazz, String path);
}
