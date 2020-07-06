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
import com.rubik.system.domain.Tickets;
import com.rubik.system.service.TicketsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/tourism/tickets")
public class TicketsController extends BaseController {

    private String prefix = "tourism/tickets";
    
    @Resource
    private TicketsService ticketsService;

    @GetMapping()
    public String tickets() {
        return prefix + "/tickets";
    }

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Tickets tickets) {
        LambdaQueryWrapper<Tickets> queryWrapper = Wrappers.lambdaQuery();
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        queryWrapper.eq(StringUtils.isNotNull(tickets.getId()), Tickets::getId, tickets.getId())
                .eq(StringUtils.isNotEmpty(tickets.getName()), Tickets::getName, tickets.getName());

        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
            // 使用分页查询
            Page<Tickets> page = new Page<>(pageNum, pageSize);
            IPage<Tickets> iPage = ticketsService.page(page, queryWrapper);
            return getMpDataTable(iPage.getRecords(), iPage.getTotal());
        } else {
            List<Tickets> list = ticketsService.list(queryWrapper);
            return getMpDataTable(list, (long) list.size());
        }
    }

    @GetMapping("/add")
    public String add(){
        return prefix+"/add";
    }

    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Tickets tickets){
        return toAjax(ticketsService.save(tickets));
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap){
        mmap.put("tickets", ticketsService.getById(id));
        return prefix+"/edit";
    }

    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Tickets tickets){
        return toAjax(ticketsService.updateById(tickets));
    }

    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        if (StringUtils.isNull(ids)){
            return AjaxResult.error("id为空");
        }
        return toAjax(ticketsService.removeByIds(Arrays.asList(Convert.toLongArray(ids))));
    }
    
}
