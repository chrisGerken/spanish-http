package org.gerken.spanish.logic;

public class VerbOption extends FilterConfigOption {

	private String verb;
	
	public VerbOption(FilterConfig parent, String id, String label, String verb, boolean enabled) {
		super(parent, id, label, enabled);
		this.verb = verb;
	}

	public String getVerb() {
		return verb;
	}

	@Override
	public void setEnabled(boolean enabled) {
		getParent().includeVerb(verb, enabled);
		
	}

}
