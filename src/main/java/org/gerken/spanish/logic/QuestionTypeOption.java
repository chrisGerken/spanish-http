package org.gerken.spanish.logic;

public class QuestionTypeOption extends FilterConfigOption {

	private String type;
	
	public QuestionTypeOption(FilterConfig parent, String id, String label, String type, boolean enabled) {
		super(parent, id, label, enabled);
		this.type = type;
	}

	public String getType() {
		return type;
	}

	@Override
	public void setEnabled(boolean enabled) { 
		getParent().includeQuestionType(type, enabled);
		
	}

}
