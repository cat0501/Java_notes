package com.itheima.shiro.vo;

/**
 * @description: 	 	资源树显示类
 */
public class TreeVo {

	/**本节点Id**/
	private String id;

	/**父节点Id**/
	private String pId;

	/**节点名称**/
	private String name;

	/**节点url**/
	private String file;

	/**节点是否被选中**/
	private Boolean checked;

	private Boolean isParent = Boolean.TRUE;

	private Boolean open = Boolean.FALSE;

	@Override
	public String toString() {
		return "TreeVo [checked=" + checked + ", file=" + file + ", id=" + id
				+ ", name=" + name + ", pId=" + pId + "]";
	}


	public TreeVo() {
		super();
	}


	/**
	 * ResourceTreeVo.
	 * @param id
	 * @param pId
	 * @param name
	 */
	public TreeVo(String id, String pId, String name) {
		this.id = id;
		this.pId = pId;
		this.name = name;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}


	public Boolean getIsParent() {
		return isParent;
	}


	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}


	public Boolean getOpen() {
		return open;
	}


	public void setOpen(Boolean open) {
		this.open = open;
	}

}
