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
import com.rubik.system.domain.ExpensesRecord;
import com.rubik.system.domain.ExpensesRecord;
import com.rubik.system.domain.Integration;
import com.rubik.system.domain.SysUser;
import com.rubik.system.service.*;
import io.swagger.models.auth.In;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/tourism/expensesRecord")
public class ExpensesRecordController extends BaseController {

    private String prefix = "tourism/expensesRecord";

    @Resource
    private ExpensesRecordService recordService;
    @Resource
    private ISysUserService userService;
    @Resource
    private IntegrationService integrationService;

    @GetMapping()
    public String record() {
        return prefix + "/record";
    }

    @GetMapping("/my")
    public String my(ModelMap mmap) {
        mmap.put("userId", ShiroUtils.getUserId());
        return prefix + "/my";
    }

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ExpensesRecord record, Date begin, Date end) {
        LambdaQueryWrapper<ExpensesRecord> queryWrapper = Wrappers.lambdaQuery();
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        queryWrapper.eq(StringUtils.isNotNull(record.getId()), ExpensesRecord::getId, record.getId())
                .eq(StringUtils.isNotNull(record.getUserId()), ExpensesRecord::getUserId, record.getUserId())
                .eq(StringUtils.isNotEmpty(record.getType()), ExpensesRecord::getType, record.getType())
                .gt(StringUtils.isNotNull(begin), ExpensesRecord::getTimeSpent, begin)
                .lt(StringUtils.isNotNull(end), ExpensesRecord::getTimeSpent, end);

        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
            // 使用分页查询
            Page<ExpensesRecord> page = new Page<>(pageNum, pageSize);
            IPage<ExpensesRecord> iPage = recordService.page(page, queryWrapper);
            return getMpDataTable(iPage.getRecords(), iPage.getTotal());
        } else {
            List<ExpensesRecord> list = recordService.list(queryWrapper);
            return getMpDataTable(list, (long) list.size());
        }
    }

    @GetMapping("/add")
    public String add(){
        return prefix+"/add";
    }

    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ExpensesRecord history){
        SysUser user = userService.selectUserById(history.getUserId());
        history.setUserName(user.getUserName());
        Integration integration = integrationService.getById(history.getUserId());
        if (integration == null) {
            integration = new Integration();
            integration.setIntegration(history.getIntegration());
            integration.setUserId(history.getUserId());
            integrationService.save(integration);
        } else {
            integration.setIntegration(integration.getIntegration().add(history.getIntegration()));
            integrationService.updateById(integration);
        }

        return toAjax(recordService.save(history));
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap){
        mmap.put("history", recordService.getById(id));
        return prefix+"/edit";
    }

    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ExpensesRecord history){
        return toAjax(recordService.updateById(history));
    }

    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        if (StringUtils.isNull(ids)){
            return AjaxResult.error("id为空");
        }
        return toAjax(recordService.removeByIds(Arrays.asList(Convert.toLongArray(ids))));
    }
}
