package com.itheima.shiro.vo;

import com.itheima.shiro.utils.ToString;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


/**	                                                                                                 
 * @description: 		选择框树显示
 */
@Getter
@Setter
public class ComboTreeVo extends ToString {
	
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

	public ComboTreeVo() {
	}

	public ComboTreeVo(String id, String parentId, String text) {
		this.id = id;
		this.parentId = parentId;
		this.text = text;
	}
	
}
