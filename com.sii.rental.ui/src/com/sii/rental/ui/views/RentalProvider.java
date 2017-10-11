package com.sii.rental.ui.views;

import java.rmi.registry.Registry;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.opcoach.training.rental.Customer;
import com.opcoach.training.rental.Rental;
import com.opcoach.training.rental.RentalAgency;
import com.opcoach.training.rental.RentalObject;
import com.sii.rental.ui.RentalUIConstants;

public class RentalProvider extends LabelProvider implements ITreeContentProvider, IColorProvider, RentalUIConstants {
	@Inject @Named(RENTAL_UI_IMG_REGISTRY)
	private ImageRegistry registry;
	
	public RentalProvider() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof Collection<?>) {
			return ((Collection<?>) inputElement).toArray();
		}
		return null;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof RentalAgency) {
			RentalAgency a =  (RentalAgency) parentElement;
			return new Node[] {new Node(Node.CUSTOMERS, a), new Node(Node.RENTAL, a), new Node(Node.RENTAL_OBJECT, a)};
		}
		else if (parentElement instanceof Node) {
			return ((Node) parentElement).getChildren();
		}
		return null;
	}

	@Override
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof RentalAgency) {
			return registry.get(IMG_AGENCY);
		}
		if (element instanceof Customer) {
			return registry.get(IMG_CUSTOMER);
		}
		if (element instanceof Rental) {
			return registry.get(IMG_RENTAL);
		}
		if (element instanceof RentalObject) {
			return registry.get(IMG_RENTAL_OBJECT);
		}
		return super.getImage(element);
	}

	@Override
	public String getText(Object element) {
		if (element instanceof RentalAgency) {
			return ((RentalAgency) element).getName();
		}
		if (element instanceof Customer) {
			return ((Customer) element).getFirstName() + " " + ((Customer) element).getLastName();
		}
		if (element instanceof Rental) {
			return ((Rental)element).toString();
		}
		if (element instanceof RentalObject) {
			return ((RentalObject) element).getName();
		}
			
		return super.getText(element);
	}

	@Override
	public Color getForeground(Object element) {
		if (element instanceof Customer) {
			return Display.getCurrent().getSystemColor(SWT.COLOR_DARK_MAGENTA);
		}
		if (element instanceof Rental) {
			return Display.getCurrent().getSystemColor(SWT.COLOR_DARK_BLUE);
		}
		if (element instanceof RentalObject) {
			return Display.getCurrent().getSystemColor(SWT.COLOR_MAGENTA);
		}		
		return null;
	}

	@Override
	public Color getBackground(Object element) {
		// TODO Auto-generated method stub
		return null;
	}
	

}

class Node {
	static final String RENTAL_OBJECT = "Rental Object";
	static final String RENTAL = "Rental";
	static final String CUSTOMERS = "Customers";
	private String label;
	RentalAgency ra;
	public Node(String label, RentalAgency ra) {
		super();
		this.label = label;
		this.ra = ra;
	}
	
	public Object[] getChildren()
	{
		if (label == CUSTOMERS)
		{
			return ra.getCustomers().toArray();
		}
		else if (label == RENTAL) {
			return ra.getRentals().toArray();
		}
		else if (label == RENTAL_OBJECT) {
			return ra.getObjectsToRent().toArray();
		}
		return null;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return label;
	}
}
