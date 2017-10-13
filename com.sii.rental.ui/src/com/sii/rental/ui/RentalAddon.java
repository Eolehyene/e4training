package com.sii.rental.ui;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.IColorProvider;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.opcoach.e4.preferences.ScopedPreferenceStore;
import com.opcoach.training.rental.Customer;
import com.opcoach.training.rental.RentalAgency;
import com.opcoach.training.rental.core.helpers.RentalAgencyGenerator;

public class RentalAddon implements RentalUIConstants{
	
	private Map<String, Palette> paletteManager = new HashMap<>();
	
	@PostConstruct
	public void init (IEclipseContext ctx, IExtensionRegistry reg) {
		ctx.set(RentalAgency.class, RentalAgencyGenerator.createSampleAgency());
		ctx.set(RENTAL_UI_IMG_REGISTRY, getLocalImageRegistry());
		ScopedPreferenceStore ps = new ScopedPreferenceStore(InstanceScope.INSTANCE, PLUGIN_ID);
		ctx.set(RENTAL_UI_PREF_STORE, ps);
		getExtensions(reg, ctx);
		if (paletteManager != null) {
			ctx.set(PALETTE_MANAGER, paletteManager);
		}
		
		String palId = ps.getString(PREF_PALETTE);
		ctx.set(Palette.class, paletteManager.get(palId));
	}
	
	ImageRegistry getLocalImageRegistry()
	{
		// Get the bundle using the universal method to get it from the current class
		Bundle b = FrameworkUtil.getBundle(getClass());  
		
		// Create a local image registry
		ImageRegistry reg = new ImageRegistry();

		// Then fill the values...
		reg.put(IMG_CUSTOMER, ImageDescriptor.createFromURL(b.getEntry(IMG_CUSTOMER)));
		reg.put(IMG_RENTAL, ImageDescriptor.createFromURL(b.getEntry(IMG_RENTAL)));
		reg.put(IMG_RENTAL_OBJECT, ImageDescriptor.createFromURL(b.getEntry(IMG_RENTAL_OBJECT)));
		reg.put(IMG_AGENCY, ImageDescriptor.createFromURL(b.getEntry(IMG_AGENCY)));
		reg.put(IMG_COLLAPSE_ALL, ImageDescriptor.createFromURL(b.getEntry(IMG_COLLAPSE_ALL)));
		reg.put(IMG_EXPAND_ALL, ImageDescriptor.createFromURL(b.getEntry(IMG_EXPAND_ALL)));

		return reg;
	}
	
	@Inject
	@Optional
	void reactOnRentalEvent(@UIEventTopic("rental/copy") Customer c) {
		System.out.println("Customer " + c.getDisplayName() + " copied");
	}
	
	public void getExtensions(IExtensionRegistry reg, IEclipseContext ctx) {
		for (IConfigurationElement elt : reg.getConfigurationElementsFor("com.sii.rental.ui.palette")) {
			
			Bundle b = Platform.getBundle(elt.getNamespaceIdentifier());
			Class<?> clazz = null;
			try {
				clazz = b.loadClass(elt.getAttribute("paletteClass"));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidRegistryObjectException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("Found palette " + elt.getAttribute("id"));
			Palette pal = new Palette();
			pal.setId(elt.getAttribute("id"));
			pal.setName(elt.getAttribute("name"));
			pal.setProvider((IColorProvider)  ContextInjectionFactory.make(clazz, ctx));
			paletteManager.put(elt.getAttribute("id"), pal);
		} 
	}
	
	@Inject
	public void refreshPalettePreferences(@Preference(value=PREF_PALETTE) String paletteID, IEclipseContext ctx) {
		if (paletteManager != null)
			ctx.set(Palette.class, paletteManager.get(paletteID));
	}

}
