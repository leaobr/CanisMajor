package net.leaobr.e4.rcp.cm.parts;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.nmap4j.Nmap4j;
import org.nmap4j.core.nmap.NMapExecutionException;
import org.nmap4j.core.nmap.NMapInitializationException;
import org.nmap4j.data.NMapRun;
import org.nmap4j.data.host.Hostnames;
import org.nmap4j.data.nmaprun.Host;
import org.nmap4j.data.nmaprun.hostnames.Hostname;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

public class NetworkViewPart implements ITreeContentProvider {
	private TreeViewer viewer;
	private Image image;
	private Nmap4j nmap4j;
	private NMapRun nmapRun;

	@PostConstruct
	public void createControls(Composite parent) {
		createImage();
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		nmap4j = new Nmap4j("/usr/local");
		nmap4j.addFlags("-sn -PR");
		nmap4j.includeHosts("192.168.1.0/24");
		try {
			nmap4j.execute();
		} catch (NMapInitializationException e) {
			e.printStackTrace();
		} catch (NMapExecutionException e) {
			e.printStackTrace();
		}
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setInput(new Host());
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
			if (!nmap4j.hasError()) {
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
}
