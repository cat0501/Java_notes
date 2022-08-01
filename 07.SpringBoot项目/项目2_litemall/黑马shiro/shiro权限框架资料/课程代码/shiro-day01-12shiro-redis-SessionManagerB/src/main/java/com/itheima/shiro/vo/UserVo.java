/*
 * <b>文件名</b>：UserVo.java
 *
 * 文件描述：
 *
 *
 * 2017年11月9日  上午10:13:25
 */

package com.itheima.shiro.vo;

import com.itheima.shiro.pojo.User;

/**
 * <b>类名：</b>UserVo.java<br>
 * <p><b>标题：</b>意真（上海）金融软件2.0</p>
 * <p><b>描述：</b>意真（上海）金融统一构建系统</p>
 * <p><b>版权声明：</b>Copyright (c) 2017</p>
 * <p><b>公司：</b>意真（上海）金融信息服务有限公司 </p>
 *
 * @author <font color='blue'>束文奇</font>
 * @version 1.0.1
 * @date 2017年11月9日 上午10:13:25
 * @Description
 */

public class UserVo extends User {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3745165122177843152L;

    /**
     * 当前拥有的角色Ids
     **/
    private String hasRoleIds;

    /**
     * 零时密码
     **/
    private String plainPassword;

    public String getHasRoleIds() {
        return hasRoleIds;
    }

    public void setHasRoleIds(String hasRoleIds) {
        this.hasRoleIds = hasRoleIds == null ? null : hasRoleIds.trim();
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword == null ? null : plainPassword.trim();
    }

}
