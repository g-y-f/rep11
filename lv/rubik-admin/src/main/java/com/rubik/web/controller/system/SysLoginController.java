package com.rubik.web.controller.system;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rubik.system.domain.SysRole;
import com.rubik.system.service.ISysRoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.rubik.common.core.controller.BaseController;
import com.rubik.common.core.domain.AjaxResult;
import com.rubik.common.utils.ServletUtils;
import com.rubik.common.utils.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 登录验证
 */
@Controller
public class SysLoginController extends BaseController {

    @Resource
    private ISysRoleService roleService;

    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        // 如果是Ajax请求，返回Json字符串。
        if (ServletUtils.isAjaxRequest(request)) {
            return ServletUtils.renderString(response, "{\"code\":\"1\",\"msg\":\"未登录或登录超时。请重新登录\"}");
        }

        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public AjaxResult ajaxLogin(String username, String password, Boolean rememberMe) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        //获取当前的用户的登录信息
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return success();
        } catch (AuthenticationException e) {
            String msg = "用户或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage())) {
                msg = e.getMessage();
            }
            return error(msg);
        }
    }

    @GetMapping("/unauth")
    public String unauth() {
        return "error/unauth";
    }

    @GetMapping("/registered")
    public String registered(ModelMap mmap) {
        List<SysRole> roleList = roleService.selectRoleAll();
        List<SysRole> roles = roleList.stream().filter(sysRole -> sysRole.getRoleId() != 1).collect(Collectors.toList());
        mmap.put("roles", roles);
        return "registered";
    }
}
