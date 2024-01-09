package org.gerken.spanish.logic;

public class AllVerbOption extends FilterConfigOption {

	public AllVerbOption(FilterConfig parent, String id, boolean enabled) {
		super(parent, id, "All Verbs", enabled);
	}

	@Override
	public void setEnabled(boolean enabled) {
		getParent().includeAllVerbs(enabled);
	}

}
