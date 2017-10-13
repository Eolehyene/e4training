
package com.sii.rental.ui.views;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import com.opcoach.training.rental.Rental;
import com.opcoach.training.rental.RentalAgency;


public class RentalPart {

	private Label rentedObjectLabel;
	private Label lblRenteeName;
	private Label lblDateInfoFrom;
	private Label lblDateInfoTo;
	
	@PostConstruct
	public void createContent(Composite parent, RentalAgency injectedAgency) {
		parent.setLayout(new GridLayout(1,false));
		
		Group infoGroup = new Group(parent, SWT.NONE);
		infoGroup.setLayout(new GridLayout(2, false));
		infoGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		infoGroup.setText("Information");
		
		Label lblRentTo = new Label(infoGroup, SWT.NONE);
		lblRentTo.setText("Rent to :");
		
		lblRenteeName = new Label(infoGroup, SWT.NONE);
				
		Label lblItem = new Label(infoGroup, SWT.NONE);
		lblItem.setText("Rented Item :");
		
		
		rentedObjectLabel = new Label(infoGroup, SWT.BORDER);
		rentedObjectLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		
		Group dateGroup = new Group(parent, SWT.NONE);
		dateGroup.setLayout(new GridLayout(2, false));
		
		Label lblDateFrom = new Label(dateGroup, SWT.NONE);
		lblDateFrom.setText("From : ");
		
		lblDateInfoFrom = new Label(dateGroup, SWT.NONE);

		
		Label lblDateTo = new Label(dateGroup, SWT.NONE);
		lblDateTo.setText("To :");
		
		lblDateInfoTo = new Label(dateGroup, SWT.NONE);
		
		setRental(injectedAgency.getRentals().get(0));
	}

	@Focus
	public void onFocus() {
		
	}
	
	@Inject @Optional public void listentoSel(@Named(IServiceConstants.ACTIVE_SELECTION) Rental r) {
		if (r != null)
			setRental(r);
	}
	
	
	public void setRental(Rental r)
	{
		if (rentedObjectLabel != null) {
			rentedObjectLabel.setText(r.getRentedObject().getName());
			lblRenteeName.setText(r.getCustomer().getFirstName()  + " " + r.getCustomer().getLastName());
			lblDateInfoFrom.setText(r.getStartDate().toString());
			lblDateInfoTo.setText(r.getEndDate().toString());
		}
	}
}