package com.itheima.shiro.vo;

/**	                                                                                                 
 * @description: 		复选框
 */
public class ComboboxVo {
	
	/**传递值**/
	private String id;
	
	/**显示值**/
	private String text;
	
	/**是否选择**/
	private Boolean selected;
	
	@Override
	public String toString() {
		return "ComboboxVo [id=" + id + ", selected=" + selected + ", text="
				+ text + "]";
	}

	public ComboboxVo() {
		super();
	}

	public ComboboxVo(String id, String text) {
		this.id = id;
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
	
	

}
