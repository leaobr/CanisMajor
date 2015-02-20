package net.leaobr.cm.mitm.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

public class NetworkViewPart implements ITreeContentProvider {
	public static TreeViewer viewer;
	private Image image;

	@PostConstruct
	public void createControls(Composite parent) {
		createImage();
		FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
		fillLayout.spacing = IDialogConstants.VERTICAL_MARGIN;
		parent.setLayout(fillLayout);
		Composite outer = new Composite(parent, SWT.NONE);
		FormLayout formLayout = new FormLayout();
		outer.setLayout(formLayout);

		Composite innerTop = new Composite(outer, SWT.NONE);
		innerTop.setLayout(new GridLayout(1, false));
		innerTop.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		FormData fData = new FormData();
		fData = new FormData();
		fData.top = new FormAttachment(0);
		fData.bottom = new FormAttachment(0, 50);
		fData.right = new FormAttachment(100);
		fData.left = new FormAttachment(0);
		innerTop.setLayoutData(fData);

		Composite innerBottom = new Composite(outer, SWT.VERTICAL);
		innerBottom.setLayout(new FillLayout(SWT.HORIZONTAL));
		viewer = new TreeViewer(innerBottom, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		//innerBottom.setLayout(fillLayout);
		fData = new FormData();
		fData.top = new FormAttachment(innerTop);
		fData.left = new FormAttachment(0);
		fData.right = new FormAttachment(100);
		fData.bottom = new FormAttachment(100);
		innerBottom.setLayoutData(fData);
	}

	private void createImage() {
		Bundle bundle = FrameworkUtil.getBundle(ViewLabelProvider.class);
		URL url = FileLocator.find(bundle, new Path("icons/computer.png"), null);
		ImageDescriptor imageDcr = ImageDescriptor.createFromURL(url);
		this.image = imageDcr.createImage();
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return null;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		return null;
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return false;
	}

	@Focus
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	@Override
	@PreDestroy
	public void dispose() {
		image.dispose();
	}

	class ViewContentProvider implements ITreeContentProvider {
		@Override
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}

		@Override
		public void dispose() {
		}

		@Override
		public Object[] getElements(Object inputElement) {
			List<Object> children = new ArrayList<Object>();
			return children.toArray();
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			return null;
		}

		@Override
		public Object getParent(Object element) {
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			return false;
		}

	}

	class ViewLabelProvider extends LabelProvider {
		@Override
		public String getText(Object element) {
			return null;
		}

		@Override
		public Image getImage(Object element) {
			return image;
		}
	}
}
