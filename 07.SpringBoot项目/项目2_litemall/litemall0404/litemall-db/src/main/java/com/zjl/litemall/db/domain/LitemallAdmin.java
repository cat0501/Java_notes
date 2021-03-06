package com.zjl.litemall.db.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Database Table Remarks:
 *   管理员表
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table litemall_admin
 *
 * @mbg.generated do_not_delete_during_merge
 * @project https://github.com/itfsw/mybatis-generator-plugin
 */
public class LitemallAdmin {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table litemall_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public static final Boolean IS_DELETED = Deleted.IS_DELETED.value();

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table litemall_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public static final Boolean NOT_DELETED = Deleted.NOT_DELETED.value();

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_admin.id
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    private Integer id;

    /**
     * Database Column Remarks:
     *   管理员名称
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_admin.username
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    private String username;

    /**
     * Database Column Remarks:
     *   管理员密码
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_admin.password
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    private String password;

    /**
     * Database Column Remarks:
     *   最近一次登录IP地址
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_admin.last_login_ip
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    private String lastLoginIp;

    /**
     * Database Column Remarks:
     *   最近一次登录时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_admin.last_login_time
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    private LocalDateTime lastLoginTime;

    /**
     * Database Column Remarks:
     *   头像图片
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_admin.avatar
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    private String avatar;

    /**
     * Database Column Remarks:
     *   创建时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_admin.add_time
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    private LocalDateTime addTime;

    /**
     * Database Column Remarks:
     *   更新时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_admin.update_time
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    private LocalDateTime updateTime;

    /**
     * Database Column Remarks:
     *   逻辑删除
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_admin.deleted
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    private Boolean deleted;

    /**
     * Database Column Remarks:
     *   角色列表
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_admin.role_ids
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    private Integer[] roleIds;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_admin.id
     *
     * @return the value of litemall_admin.id
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_admin.id
     *
     * @param id the value for litemall_admin.id
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_admin.username
     *
     * @return the value of litemall_admin.username
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_admin.username
     *
     * @param username the value for litemall_admin.username
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_admin.password
     *
     * @return the value of litemall_admin.password
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_admin.password
     *
     * @param password the value for litemall_admin.password
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_admin.last_login_ip
     *
     * @return the value of litemall_admin.last_login_ip
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_admin.last_login_ip
     *
     * @param lastLoginIp the value for litemall_admin.last_login_ip
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_admin.last_login_time
     *
     * @return the value of litemall_admin.last_login_time
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_admin.last_login_time
     *
     * @param lastLoginTime the value for litemall_admin.last_login_time
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_admin.avatar
     *
     * @return the value of litemall_admin.avatar
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_admin.avatar
     *
     * @param avatar the value for litemall_admin.avatar
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_admin.add_time
     *
     * @return the value of litemall_admin.add_time
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public LocalDateTime getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_admin.add_time
     *
     * @param addTime the value for litemall_admin.add_time
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_admin.update_time
     *
     * @return the value of litemall_admin.update_time
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_admin.update_time
     *
     * @param updateTime the value for litemall_admin.update_time
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public void andLogicalDeleted(boolean deleted) {
        setDeleted(deleted ? Deleted.IS_DELETED.value() : Deleted.NOT_DELETED.value());
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_admin.deleted
     *
     * @return the value of litemall_admin.deleted
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_admin.deleted
     *
     * @param deleted the value for litemall_admin.deleted
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_admin.role_ids
     *
     * @return the value of litemall_admin.role_ids
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public Integer[] getRoleIds() {
        return roleIds;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_admin.role_ids
     *
     * @param roleIds the value for litemall_admin.role_ids
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public void setRoleIds(Integer[] roleIds) {
        this.roleIds = roleIds;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", IS_DELETED=").append(IS_DELETED);
        sb.append(", NOT_DELETED=").append(NOT_DELETED);
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", lastLoginIp=").append(lastLoginIp);
        sb.append(", lastLoginTime=").append(lastLoginTime);
        sb.append(", avatar=").append(avatar);
        sb.append(", addTime=").append(addTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", deleted=").append(deleted);
        sb.append(", roleIds=").append(roleIds);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        LitemallAdmin other = (LitemallAdmin) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getLastLoginIp() == null ? other.getLastLoginIp() == null : this.getLastLoginIp().equals(other.getLastLoginIp()))
            && (this.getLastLoginTime() == null ? other.getLastLoginTime() == null : this.getLastLoginTime().equals(other.getLastLoginTime()))
            && (this.getAvatar() == null ? other.getAvatar() == null : this.getAvatar().equals(other.getAvatar()))
            && (this.getAddTime() == null ? other.getAddTime() == null : this.getAddTime().equals(other.getAddTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getDeleted() == null ? other.getDeleted() == null : this.getDeleted().equals(other.getDeleted()))
            && (Arrays.equals(this.getRoleIds(), other.getRoleIds()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getLastLoginIp() == null) ? 0 : getLastLoginIp().hashCode());
        result = prime * result + ((getLastLoginTime() == null) ? 0 : getLastLoginTime().hashCode());
        result = prime * result + ((getAvatar() == null) ? 0 : getAvatar().hashCode());
        result = prime * result + ((getAddTime() == null) ? 0 : getAddTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getDeleted() == null) ? 0 : getDeleted().hashCode());
        result = prime * result + (Arrays.hashCode(getRoleIds()));
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public static LitemallAdmin.Builder builder() {
        return new LitemallAdmin.Builder();
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table litemall_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public static class Builder {
        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_admin
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private LitemallAdmin obj;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_admin
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder() {
            this.obj = new LitemallAdmin();
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column litemall_admin.id
         *
         * @param id the value for litemall_admin.id
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder id(Integer id) {
            obj.setId(id);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column litemall_admin.username
         *
         * @param username the value for litemall_admin.username
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder username(String username) {
            obj.setUsername(username);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column litemall_admin.password
         *
         * @param password the value for litemall_admin.password
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder password(String password) {
            obj.setPassword(password);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column litemall_admin.last_login_ip
         *
         * @param lastLoginIp the value for litemall_admin.last_login_ip
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder lastLoginIp(String lastLoginIp) {
            obj.setLastLoginIp(lastLoginIp);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column litemall_admin.last_login_time
         *
         * @param lastLoginTime the value for litemall_admin.last_login_time
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder lastLoginTime(LocalDateTime lastLoginTime) {
            obj.setLastLoginTime(lastLoginTime);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column litemall_admin.avatar
         *
         * @param avatar the value for litemall_admin.avatar
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder avatar(String avatar) {
            obj.setAvatar(avatar);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column litemall_admin.add_time
         *
         * @param addTime the value for litemall_admin.add_time
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder addTime(LocalDateTime addTime) {
            obj.setAddTime(addTime);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column litemall_admin.update_time
         *
         * @param updateTime the value for litemall_admin.update_time
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder updateTime(LocalDateTime updateTime) {
            obj.setUpdateTime(updateTime);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column litemall_admin.deleted
         *
         * @param deleted the value for litemall_admin.deleted
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder deleted(Boolean deleted) {
            obj.setDeleted(deleted);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column litemall_admin.role_ids
         *
         * @param roleIds the value for litemall_admin.role_ids
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder roleIds(Integer[] roleIds) {
            obj.setRoleIds(roleIds);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_admin
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public LitemallAdmin build() {
            return this.obj;
        }
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table litemall_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public enum Deleted {
        NOT_DELETED(new Boolean("0"), "未删除"),
        IS_DELETED(new Boolean("1"), "已删除");

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_admin
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final Boolean value;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_admin
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String name;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_admin
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        Deleted(Boolean value, String name) {
            this.value = value;
            this.name = name;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_admin
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Boolean getValue() {
            return this.value;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_admin
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Boolean value() {
            return this.value;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_admin
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getName() {
            return this.name;
        }
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table litemall_admin
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public enum Column {
        id("id", "id", "INTEGER", false),
        username("username", "username", "VARCHAR", false),
        password("password", "password", "VARCHAR", true),
        lastLoginIp("last_login_ip", "lastLoginIp", "VARCHAR", false),
        lastLoginTime("last_login_time", "lastLoginTime", "TIMESTAMP", false),
        avatar("avatar", "avatar", "VARCHAR", false),
        addTime("add_time", "addTime", "TIMESTAMP", false),
        updateTime("update_time", "updateTime", "TIMESTAMP", false),
        deleted("deleted", "deleted", "BIT", false),
        roleIds("role_ids", "roleIds", "VARCHAR", false);

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_admin
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String BEGINNING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_admin
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String ENDING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_admin
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String column;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_admin
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final boolean isColumnNameDelimited;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_admin
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String javaProperty;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_admin
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String jdbcType;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_admin
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String value() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_admin
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getValue() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_admin
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJavaProperty() {
            return this.javaProperty;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_admin
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJdbcType() {
            return this.jdbcType;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_admin
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        Column(String column, String javaProperty, String jdbcType, boolean isColumnNameDelimited) {
            this.column = column;
            this.javaProperty = javaProperty;
            this.jdbcType = jdbcType;
            this.isColumnNameDelimited = isColumnNameDelimited;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_admin
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_admin
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_admin
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public static Column[] excludes(Column ... excludes) {
            ArrayList<Column> columns = new ArrayList<>(Arrays.asList(Column.values()));
            if (excludes != null && excludes.length > 0) {
                columns.removeAll(new ArrayList<>(Arrays.asList(excludes)));
            }
            return columns.toArray(new Column[]{});
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_admin
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getEscapedColumnName() {
            if (this.isColumnNameDelimited) {
                return new StringBuilder().append(BEGINNING_DELIMITER).append(this.column).append(ENDING_DELIMITER).toString();
            } else {
                return this.column;
            }
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_admin
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getAliasedEscapedColumnName() {
            return this.getEscapedColumnName();
        }
    }
}