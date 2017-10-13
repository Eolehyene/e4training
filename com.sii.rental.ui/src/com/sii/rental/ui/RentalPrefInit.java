package com.sii.rental.ui;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;

import com.opcoach.e4.preferences.ScopedPreferenceStore;

public class RentalPrefInit extends AbstractPreferenceInitializer implements RentalUIConstants {

	public RentalPrefInit() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initializeDefaultPreferences()  {
		IPreferenceStore ps = new ScopedPreferenceStore(InstanceScope.INSTANCE, PLUGIN_ID);

		ps.setDefault(PREF_PALETTE, "com.sii.rental.ui.DefaultPalette");
	}

}
