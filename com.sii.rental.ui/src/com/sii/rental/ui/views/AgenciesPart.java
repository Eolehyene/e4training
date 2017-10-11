
package com.sii.rental.ui.views;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;

import com.opcoach.training.rental.RentalAgency;

public class AgenciesPart {
	@Inject
	private ESelectionService selectionService;
	private TreeViewer tv;
	
	@PostConstruct
	public void createContent(Composite parent, RentalAgency injectedAgency, IEclipseContext ctx) {
		Collection<RentalAgency> listAgencies = new ArrayList<>();
		listAgencies.add(injectedAgency);
		tv = new TreeViewer(parent);
		
		
		RentalProvider provider = ContextInjectionFactory.make(RentalProvider.class, ctx);
		tv.setContentProvider(provider);
		tv.setLabelProvider(provider);
		tv.setInput(listAgencies);
		
		tv.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection sel = (IStructuredSelection) event.getSelection();
				selectionService.setSelection(sel.size() == 1 ? sel.getFirstElement() : sel.toArray());
			}
		});
		tv.expandAll();
	}

}