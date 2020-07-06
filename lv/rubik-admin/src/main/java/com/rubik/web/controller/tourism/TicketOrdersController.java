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
import com.rubik.framework.config.ShiroConfig;
import com.rubik.framework.util.ShiroUtils;
import com.rubik.system.domain.TicketOrders;
import com.rubik.system.domain.Tickets;
import com.rubik.system.service.CommodityService;
import com.rubik.system.service.TicketOrdersService;
import com.rubik.system.service.TicketsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/tourism/ticketOrders")
public class TicketOrdersController extends BaseController {

    private String prefix = "tourism/ticketOrders";

    @Resource
    private TicketOrdersService ticketOrdersService;
    @Resource
    private TicketsService ticketsService;

    @GetMapping()
    public String ticketOrders() {
        return prefix + "/ticketOrders";
    }

    @GetMapping("/my")
    public String my(ModelMap modelMap) {
        modelMap.put("userId", ShiroUtils.getUserId());
        return prefix + "/my";
    }

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TicketOrders ticketOrders, Date begin, Date end) {
        LambdaQueryWrapper<TicketOrders> queryWrapper = Wrappers.lambdaQuery();
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        queryWrapper.eq(StringUtils.isNotNull(ticketOrders.getId()), TicketOrders::getId, ticketOrders.getId())
                .eq(StringUtils.isNotEmpty(ticketOrders.getUserName()), TicketOrders::getUserName, ticketOrders.getUserName())
                .eq(StringUtils.isNotNull(ticketOrders.getTicketsId()), TicketOrders::getTicketsId, ticketOrders.getTicketsId())
                .eq(StringUtils.isNotEmpty(ticketOrders.getTicketsName()), TicketOrders::getTicketsName, ticketOrders.getTicketsName())
                .gt(StringUtils.isNotNull(begin), TicketOrders::getStartingTime, begin)
                .lt(StringUtils.isNotNull(end), TicketOrders::getEndTime, end)
                .eq(StringUtils.isNotNull(ticketOrders.getStatus()), TicketOrders::getStatus, ticketOrders.getStatus());

        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
            // 使用分页查询
            Page<TicketOrders> page = new Page<>(pageNum, pageSize);
            IPage<TicketOrders> iPage = ticketOrdersService.page(page, queryWrapper);
            return getMpDataTable(iPage.getRecords(), iPage.getTotal());
        } else {
            List<TicketOrders> list = ticketOrdersService.list(queryWrapper);
            return getMpDataTable(list, (long) list.size());
        }
    }

    @GetMapping("/add/{id}")
    public String add(@PathVariable("id") Long id, ModelMap mmap){
        Tickets tickets = ticketsService.getById(id);
        mmap.put("ticketsId", id);
        mmap.put("ticketsName", tickets.getName());
        mmap.put("actuallyPaid", tickets.getIntegration());
        return prefix+"/add";
    }

    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TicketOrders ticketOrders){
        ticketOrders.setUserId(ShiroUtils.getUserId());
        ticketOrders.setUserName(ShiroUtils.getUserName());
        ticketOrders.setStatus(0);
        return toAjax(ticketOrdersService.save(ticketOrders));
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap){
        mmap.put("ticketOrders", ticketOrdersService.getById(id));
        return prefix+"/edit";
    }

    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TicketOrders ticketOrders){
        return toAjax(ticketOrdersService.updateById(ticketOrders));
    }

    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        if (StringUtils.isNull(ids)){
            return AjaxResult.error("id为空");
        }
        return toAjax(ticketOrdersService.removeByIds(Arrays.asList(Convert.toLongArray(ids))));
    }
}
