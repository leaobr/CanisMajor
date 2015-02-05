package net.leaobr.e4.rcp.cm.parts;

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
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;
import org.nmap4j.Nmap4j;
import org.nmap4j.data.NMapRun;
import org.nmap4j.data.host.Hostnames;
import org.nmap4j.data.nmaprun.Host;
import org.nmap4j.data.nmaprun.hostnames.Hostname;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

public class NetworkViewPart implements ITreeContentProvider {
	public static TreeViewer viewer;
	private Image image;
	public static Text searchField;
	private Button searchBtn;
	private String nmapPath;
	public static Nmap4j nmap4j;
	public static NMapRun nmapRun;

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
		innerTop.setLayout(new GridLayout(2, false));
		innerTop.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		searchField = new Text(innerTop, SWT.SINGLE | SWT.BORDER | SWT.CANCEL);
		searchField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		searchBtn = new Button(innerTop, SWT.NONE);
		searchBtn.setText("...");
		searchBtn.addSelectionListener(new NmapPathInitializer(parent));
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
		viewer.setInput(new Host());
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
		return nmapRun.getHosts().toArray();
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

	@PreDestroy
	public void dispose() {
		image.dispose();
	}

	class ViewContentProvider implements ITreeContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}

		@Override
		public void dispose() {
		}

		@Override
		public Object[] getElements(Object inputElement) {
			List<Object> children = new ArrayList<Object>();
			if (nmap4j != null) {
				nmapRun = nmap4j.getResult();
				children.addAll(nmapRun.getHosts());
			}//TODO see what happens when nmap4j has errors
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
			Host host = (Host) element;
			Hostnames hostNames = host.getHostnames();
			Hostname hostName = hostNames != null ? hostNames.getHostname() : null;
			return hostName == null ? host.getAddresses().get(0).getAddr() : hostName.getName();
		}

		public Image getImage(Object element) {
			return image;
		}
	}

	class NmapPathInitializer implements SelectionListener {

		private final Composite parent;

		public NmapPathInitializer(Composite parent) {
			this.parent = parent;
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			FileDialog fd = new FileDialog(parent.getShell(), SWT.OPEN);
			fd.setText("Select File Path");
			fd.setFilterNames(new String[] { "All Files (*.*)" });
			fd.setFilterExtensions(new String[] { "*.*" });
			nmapPath = fd.open();
			if (nmapPath == null) return;
			searchField.setText(nmapPath);
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
		}

	}
}
