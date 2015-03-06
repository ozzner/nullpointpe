/**
 * 
 */
package com.apprade.adapter;

/**
 * @author renzo
 *
 */
public class Adapter_SpinnerItem {
	private int icon;
	private String category;
	
	
	/**
	 * @param icon
	 * @param category
	 */
	public Adapter_SpinnerItem(int icon, String category) {
		super();
		this.icon = icon;
		this.category = category;
	}
	/**
	 * 
	 */
	public Adapter_SpinnerItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the icon
	 */
	public int getIcon() {
		return icon;
	}
	/**
	 * @param icon the icon to set
	 */
	public void setIcon(int icon) {
		this.icon = icon;
	}
	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	
	

}
