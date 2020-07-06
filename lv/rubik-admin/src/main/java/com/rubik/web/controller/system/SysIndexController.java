package com.rubik.web.controller.system;

import com.rubik.common.annotation.Log;
import com.rubik.common.config.Global;
import com.rubik.common.core.controller.BaseController;
import com.rubik.common.core.domain.AjaxResult;
import com.rubik.common.enums.BusinessType;
import com.rubik.common.utils.StringUtils;
import com.rubik.framework.shiro.service.SysPasswordService;
import com.rubik.framework.util.ShiroUtils;
import com.rubik.system.domain.Integration;
import com.rubik.system.domain.SysMenu;
import com.rubik.system.domain.SysUser;
import com.rubik.system.service.ISysLogininforService;
import com.rubik.system.service.ISysMenuService;
import com.rubik.system.service.ISysUserService;
import com.rubik.system.service.IntegrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 首页 业务处理
 */
@Controller
public class SysIndexController extends BaseController {
    @Resource
    private ISysMenuService menuService;
    @Resource
    private ISysLogininforService logininforService;
    @Resource
    private ISysUserService userService;
    @Resource
    private SysPasswordService passwordService;
    @Resource
    private IntegrationService integrationService;
    /**
     *  系统首页
     */
    @GetMapping("/index")
    public String index(ModelMap mmap) {
        // 取身份信息
        SysUser user = ShiroUtils.getSysUser();
        // 根据用户id取出菜单
        List<SysMenu> menus = menuService.selectMenusByUser(user);
        Integration integration = integrationService.getById(ShiroUtils.getUserId());
        if (integration == null) {
            integration = new Integration();
            integration.setIntegration(new BigDecimal(0));
        }

        mmap.put("menus", menus);
        mmap.put("user", user);
        mmap.put("roleId", user.getRoles().get(0).getRoleId());
        mmap.put("loginInfo", logininforService.getLastLogin(ShiroUtils.getUserName()));
        mmap.put("copyrightYear", Global.getCopyrightYear());
        mmap.put("integration", integration.getIntegration());
        return "index";
    }

    /**
     * 切换主题
      */
    @GetMapping("/system/switchSkin")
    public String switchSkin(ModelMap mmap) {
        return "skin";
    }

    /**
     * 系统介绍
     */
    @GetMapping("/system/main")
    public String main(ModelMap mmap) {
        mmap.put("version", Global.getVersion());
        return "main";
    }

    @GetMapping("/system/mainUser")
    public String mainUser (ModelMap modelMap) {
        SysUser user = ShiroUtils.getSysUser();
        modelMap.put("user", user);
        modelMap.put("roleGroup", userService.selectUserRoleGroup(user.getUserId()));
        return  "/mainUser";
    }

    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @GetMapping("/findPwd")
    public String findPwd() {
        return "/findPwd";
    }

    @Log(title = "找回密码", businessType = BusinessType.UPDATE)
    @PostMapping("/findPwd")
    @ResponseBody
    public AjaxResult findPwd(SysUser user) {
        if (StringUtils.isNotNull(user.getUserId()) && SysUser.isAdmin(user.getUserId())) {
            return error("不允许重置超级管理员用户密码");
        }
        SysUser sysUser = userService.selectUserByPhoneNumber(user.getPhonenumber());
        SysUser sysUserE = userService.selectUserByEmail(user.getEmail());
        if (sysUser.getUserId().equals(sysUserE.getUserId())) {
            user.setSalt(ShiroUtils.randomSalt());
            user.setPassword(passwordService.encryptPassword(user.getUserName(), user.getPassword(), user.getSalt()));
            if (userService.resetUserPwd(user) > 0) {
                if (ShiroUtils.getUserId().equals(user.getUserId())) {
                    ShiroUtils.setSysUser(userService.selectUserById(user.getUserId()));
                }
                return success();
            }
            return error();
        } else {
            return error("邮箱或联系方式不正确");
        }
    }
}
