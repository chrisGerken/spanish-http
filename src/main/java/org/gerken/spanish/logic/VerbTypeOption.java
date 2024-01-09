package org.gerken.spanish.logic;

public class VerbTypeOption extends FilterConfigOption {

	private String type;
	
	public VerbTypeOption(FilterConfig parent, String id, String label, String type, boolean enabled) {
		super(parent, id, label, enabled);
		this.type = type;
	}

	public String getType() {
		return type;
	}

	@Override
	public void setEnabled(boolean enabled) {
		getParent().includeVerbType(type, enabled);
	}

}
