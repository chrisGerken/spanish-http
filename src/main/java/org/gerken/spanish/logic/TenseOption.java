package org.gerken.spanish.logic;

public class TenseOption extends FilterConfigOption {

	private String tense;
	
	public TenseOption(FilterConfig parent, String id, String label, String tense, boolean enabled) {
		super(parent, id, label, enabled);
		this.tense = tense;
	}

	public String getTense() {
		return tense;
	}

	@Override
	public void setEnabled(boolean enabled) { 
		getParent().includeTense(tense, enabled);
		
	}

}
