/*
 * <b>文件名</b>：UserServiceImpl.java
 *
 * 文件描述：
 *
 *
 * 2017-4-7  下午2:19:54
 */

package com.itheima.shiro.service.impl;

import com.itheima.shiro.constant.SuperConstant;
import com.itheima.shiro.core.adapter.UserAdapter;
import com.itheima.shiro.mapper.UserMapper;
import com.itheima.shiro.mapper.UserRoleMapper;
import com.itheima.shiro.pojo.User;
import com.itheima.shiro.pojo.UserExample;
import com.itheima.shiro.pojo.UserRole;
import com.itheima.shiro.pojo.UserRoleExample;
import com.itheima.shiro.service.UserService;
import com.itheima.shiro.utils.*;
import com.itheima.shiro.vo.UserVo;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.itheima.shiro.constant.SuperConstant.HASH_INTERATIONS;


/**
 * @Description 用户服务类
 */
@Log4j2
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    UserAdapter userAdapter;

    @Override
    public List<User> findUserList(UserVo userVo, Integer rows, Integer page) {
        UserExample userExample = this.userListExample(userVo);
        userExample.setOrderByClause("sort_no asc ");
        userExample.setPage(page);
        userExample.setRow(rows);
        return userMapper.selectByExample(userExample);
    }

    @Override
    public long countUserList(UserVo userVo) {
        UserExample userExample = this.userListExample(userVo);
        return userMapper.countByExample(userExample);
    }

    /**
     * <b>方法名：</b>：userListExample<br>
     * <b>功能说明：</b>：分页查询条件拼装<br>
     *
     * @param userVo
     * @return
     * @author <font color='blue'>束文奇</font>
     * @date 2017年11月9日 上午10:34:10
     */
    private UserExample userListExample(UserVo userVo) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if (!EmptyUtil.isNullOrEmpty(userVo.getLoginName())) {
            criteria.andLoginNameEqualTo(userVo.getLoginName());
        }
        if (!EmptyUtil.isNullOrEmpty(userVo.getRealName())) {
            criteria.andRealNameLike(userVo.getRealName());
        }
        if (!EmptyUtil.isNullOrEmpty(userVo.getEmail())) {
            criteria.andEmailEqualTo(userVo.getEmail());
        }
        return userExample;
    }

    @Override
    public UserVo getUserById(String id) {
        return BeanConv.toBean(userMapper.selectByPrimaryKey(id, null), UserVo.class);
    }

    @Override
    @Transactional
    public Boolean saveOrUpdateUser(UserVo userVo) throws IllegalAccessException, InvocationTargetException {
        Boolean flag = true;
        try {
            if (!EmptyUtil.isNullOrEmpty(userVo.getPlainPassword())) {
                entryptPassword(userVo);
            }
            User user = BeanConv.toBean(userVo, User.class);
            if (EmptyUtil.isNullOrEmpty(userVo.getId())) {
                user.setEnableFlag(SuperConstant.YES);
                userMapper.insert(user);
                userVo.setId(user.getId());

            } else {
                userMapper.updateByPrimaryKey(user);
                UserRoleExample userRoleExample = new UserRoleExample();
                userRoleExample.createCriteria().andUserIdEqualTo(user.getId());
                userRoleMapper.deleteByExample(userRoleExample);
            }
            bachUserRole(userVo);
        } catch (Exception e) {
            log.error("保存用户出错{}", ExceptionsUtil.getStackTraceAsString(e));
            flag = false;
        }
        return flag;
    }

    /**
     * <b>方法名：</b>：bachUserRole<br>
     * <b>功能说明：</b>：批量处理UserRole中间表<br>
     *
     * @author <font color='blue'>束文奇</font>
     * @date 2017-7-11 下午4:01:12
     */
    private void bachUserRole(UserVo userVo) {
        if (!EmptyUtil.isNullOrEmpty(userVo.getHasRoleIds())) {
            List<UserRole> list = new ArrayList<>();
            List<String> roleIdList = Arrays.asList(userVo.getHasRoleIds().split(","));
            for (String roleId : roleIdList) {
                UserRole userRole = new UserRole();
                userRole.setUserId(userVo.getId());
                userRole.setRoleId(roleId);
                userRole.setEnableFlag(SuperConstant.YES);
                list.add(userRole);
            }
            userRoleMapper.batchInsert(list);
        }
    }


    @Override
    public Boolean getUserByLoginNameOrMobilOrEmail(String loginName) {
        User user = this.getUserIdByLoginNameOrMobilOrEmail(loginName);
        if (!EmptyUtil.isNullOrEmpty(user)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public User getUserIdByLoginNameOrMobilOrEmail(String loginName) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria0 = userExample.createCriteria().andLoginNameEqualTo(loginName);
        UserExample.Criteria criteria1 = userExample.createCriteria().andMobilEqualTo(loginName);
        UserExample.Criteria criteria2 = userExample.createCriteria().andEmailEqualTo(loginName);
        userExample.or(criteria0);
        userExample.or(criteria1);
        userExample.or(criteria2);
        List<User> list = userMapper.selectByExample(userExample);
        if (list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public Boolean updateByIds(List<String> list, String enableFlag) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(list);
        User userHandler = new User();
        userHandler.setEnableFlag(enableFlag);
        int flag = userMapper.updateByExampleSelective(userHandler, userExample);
        if (flag > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void entryptPassword(UserVo userVo) {
        Map<String, String> map = DigestsUtil.entryptPassword(userVo.getPlainPassword());
        userVo.setSalt(map.get("salt"));
        userVo.setPassWord(map.get("password"));
    }

    @Override
    public List<String> findUserHasRoleIds(String id) {
        UserRoleExample userRoleExample = new UserRoleExample();
        userRoleExample.createCriteria().andUserIdEqualTo(id).andEnableFlagEqualTo(SuperConstant.YES);
        List<UserRole> userRoleList = userRoleMapper.selectByExample(userRoleExample);
        List<String> list = new ArrayList<>();
        userRoleList.forEach(n -> list.add(n.getRoleId()));
        return list;
    }

    @Override
    public Boolean saveNewPassword(String oldPassword, String plainPassword) throws IllegalAccessException, InvocationTargetException {
        //user对象
        User user = userMapper.selectByPrimaryKey(ShiroUserUtil.getShiroUserId(), null);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userVo, user);
        //对user中的salt进行散列
        oldPassword = DigestsUtil.sha1(oldPassword, user.getSalt());
        if (!user.getPassWord().equals(oldPassword)) {
            return false;
        }
        userVo.setPlainPassword(plainPassword);
        entryptPassword(userVo);
        try {
            user.setPassWord(userVo.getPassWord());
            user.setSalt(userVo.getSalt());
            userMapper.updateByPrimaryKey(user);
            return true;
        } catch (Exception e) {
            log.error("更新用户密码失败：{}", ExceptionsUtil.getStackTraceAsString(e));
            return false;
        }

    }

}
