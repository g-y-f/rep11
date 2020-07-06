package com.rubik.web.controller.tourism;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rubik.common.core.controller.BaseController;
import com.rubik.common.core.domain.AjaxResult;
import com.rubik.common.core.page.PageDomain;
import com.rubik.common.core.page.TableDataInfo;
import com.rubik.common.core.page.TableSupport;
import com.rubik.common.core.text.Convert;
import com.rubik.common.utils.StringUtils;
import com.rubik.framework.util.ShiroUtils;
import com.rubik.system.domain.GuidebookUser;
import com.rubik.system.domain.SysUser;
import com.rubik.system.service.GuidebookUserService;
import com.rubik.system.service.ISysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/tourism/guidebookUser")
public class GuidebookUserController extends BaseController {

    private String prefix = "tourism/guidebookUser";

    @Resource
    private GuidebookUserService guidebookUserService;
    @Resource
    private ISysUserService userService;

    @GetMapping()
    public String commodity() {
        return prefix + "/guidebookUser";
    }

    @GetMapping("/my")
    public String my(ModelMap mmap) {
        mmap.put("userId", ShiroUtils.getUserId());
        return prefix + "/my";
    }

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(GuidebookUser guidebookUser, BigDecimal begin, BigDecimal end) {
        LambdaQueryWrapper<GuidebookUser> queryWrapper = Wrappers.lambdaQuery();
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        queryWrapper.eq(StringUtils.isNotNull(guidebookUser.getId()), GuidebookUser::getId, guidebookUser.getId())
                .eq(StringUtils.isNotEmpty(guidebookUser.getUserName()), GuidebookUser::getUserName, guidebookUser.getUserName())
                .eq(StringUtils.isNotEmpty(guidebookUser.getTouristguideName()), GuidebookUser::getTouristguideName, guidebookUser.getTouristguideName())
                .eq(StringUtils.isNotNull(guidebookUser.getUserId()), GuidebookUser::getUserId, guidebookUser.getUserId())
                .eq(StringUtils.isNotNull(guidebookUser.getTouristguideId()), GuidebookUser::getTouristguideId, guidebookUser.getTouristguideId());

        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
            // 使用分页查询
            Page<GuidebookUser> page = new Page<>(pageNum, pageSize);
            IPage<GuidebookUser> iPage = guidebookUserService.page(page, queryWrapper);
            return getMpDataTable(iPage.getRecords(), iPage.getTotal());
        } else {
            List<GuidebookUser> list = guidebookUserService.list(queryWrapper);
            return getMpDataTable(list, (long) list.size());
        }
    }

    @GetMapping("/add")
    public String add(){
        return prefix+"/add";
    }

    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(GuidebookUser guidebookUser){
        SysUser guide = userService.selectUserById(guidebookUser.getTouristguideId());
        guidebookUser.setTouristguideName(guide.getUserName());
        guidebookUser.setUserId(ShiroUtils.getUserId());
        guidebookUser.setUserName(ShiroUtils.getUserName());
        return toAjax(guidebookUserService.save(guidebookUser));
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap){
        mmap.put("guidebookUser", guidebookUserService.getById(id));
        return prefix+"/edit";
    }

    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(GuidebookUser guidebookUser){
        return toAjax(guidebookUserService.updateById(guidebookUser));
    }

    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        if (StringUtils.isNull(ids)){
            return AjaxResult.error("id为空");
        }
        return toAjax(guidebookUserService.removeByIds(Arrays.asList(Convert.toLongArray(ids))));
    }
}
