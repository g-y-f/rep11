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
import com.rubik.system.domain.Commodity;
import com.rubik.system.domain.ExchangeHistory;
import com.rubik.system.service.CommodityService;
import com.rubik.system.service.ExchangeHistoryService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/tourism/exchangeHistory")
public class ExchangeHistoryController extends BaseController {

    private String prefix = "tourism/exchangeHistory";

    @Resource
    private ExchangeHistoryService historyService;
    @Resource
    private CommodityService commodityService;

    @GetMapping()
    public String exchangeHistory() {
        return prefix + "/history";
    }

    @GetMapping("/my")
    public String my(ModelMap modelMap) {
        modelMap.put("userId", ShiroUtils.getUserId());
        return prefix + "/my";
    }

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ExchangeHistory history, Date begin, Date end) {
        LambdaQueryWrapper<ExchangeHistory> queryWrapper = Wrappers.lambdaQuery();
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        queryWrapper.eq(StringUtils.isNotNull(history.getId()), ExchangeHistory::getId, history.getId())
                .like(StringUtils.isNotEmpty(history.getCommodityName()), ExchangeHistory::getCommodityName, history.getCommodityName())
                .gt(StringUtils.isNotNull(begin), ExchangeHistory::getRedemptionTime, begin)
                .lt(StringUtils.isNotNull(end), ExchangeHistory::getRedemptionTime, end);

        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
            // 使用分页查询
            Page<ExchangeHistory> page = new Page<>(pageNum, pageSize);
            IPage<ExchangeHistory> iPage = historyService.page(page, queryWrapper);
            return getMpDataTable(iPage.getRecords(), iPage.getTotal());
        } else {
            List<ExchangeHistory> list = historyService.list(queryWrapper);
            return getMpDataTable(list, (long) list.size());
        }
    }

    @GetMapping("/add/{commodityId}")
    public String add(@PathVariable("commodityId") Long commodityId, ModelMap mmap){
        Commodity commodity = commodityService.getById(commodityId);
        mmap.put("commodityId", commodityId);
        mmap.put("commodityName", commodity.getName());
        return prefix+"/add";
    }

    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ExchangeHistory history){
        Commodity commodity = commodityService.getById(history.getCommodityId());
        if (StringUtils.isNull(commodity)) {
            return AjaxResult.error("该商品不存在");
        }
        if (commodity.getQty() - history.getExchangeQuantity() < 1) {
            return AjaxResult.error("该商品数量不够兑换");
        }
        history.setCommodityName(commodity.getName());
        history.setUserId(ShiroUtils.getUserId());
        history.setUserName(ShiroUtils.getUserName());
        history.setRedemptionTime(new Date());
        if (historyService.save(history)) {
            commodity.setQty(commodity.getQty()-history.getExchangeQuantity());
            return toAjax(commodityService.updateById(commodity));
        } else {
            return AjaxResult.error("兑换失败");
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap){
        mmap.put("history", historyService.getById(id));
        return prefix+"/edit";
    }

    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ExchangeHistory history){
        return toAjax(historyService.updateById(history));
    }

    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        if (StringUtils.isNull(ids)){
            return AjaxResult.error("id为空");
        }
        return toAjax(historyService.removeByIds(Arrays.asList(Convert.toLongArray(ids))));
    }
}
