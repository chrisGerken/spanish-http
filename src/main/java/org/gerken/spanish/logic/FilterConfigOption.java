package org.gerken.spanish.logic;

public abstract class FilterConfigOption {

	private FilterConfig	parent;
	private String 			id;
	private String 			label;
	private boolean			enabled;

	public FilterConfigOption(FilterConfig parent, String id, String label, boolean enabled) {
		super();
		this.parent = parent;
		this.id = id;
		this.label = label;
		this.enabled = enabled;
		parent.note(this);
	}

	public FilterConfig getParent() {
		return parent;
	}

	public String getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public abstract void setEnabled(boolean enabled);


}
