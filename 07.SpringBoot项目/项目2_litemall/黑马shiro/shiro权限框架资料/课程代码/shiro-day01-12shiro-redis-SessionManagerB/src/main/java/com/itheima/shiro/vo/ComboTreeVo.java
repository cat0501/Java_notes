package com.itheima.shiro.vo;

import java.util.List;


/**	                                                                                                 
 * @description: 		选择框树显示
 */
public class ComboTreeVo {
	
	/**传递的Id**/
	private String id;
	
	/**父Id**/
	private String parentId;
	
	/**显示的内容**/
	private String text;
	
	/**是否节点展开**/
	private String state;
	
	/**是否选择**/
	private Boolean checked;
	
	private List<ComboTreeVo> children;
	
	@Override
	public String toString() {
		return "ComboTreeVo [checked=" + checked + ", children=" + children
				+ ", id=" + id + ", parentId=" + parentId + ", state=" + state
				+ ", text=" + text + "]";
	}

	public ComboTreeVo(String id, String parentId, String text) {
		this.id = id;
		this.parentId = parentId;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<ComboTreeVo> getChildren() {
		return children;
	}

	public void setChildren(List<ComboTreeVo> children) {
		this.children = children;
	}
	
}
