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
import com.rubik.system.domain.Commodity;
import com.rubik.system.service.CommodityService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/tourism/commodity")
public class CommodityController extends BaseController {

    private String prefix = "tourism/commodity";

    @Resource
    private CommodityService commodityService;

    @GetMapping()
    public String commodity() {
        return prefix + "/commodity";
    }

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Commodity commodity, BigDecimal begin, BigDecimal end) {
        LambdaQueryWrapper<Commodity> queryWrapper = Wrappers.lambdaQuery();
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        queryWrapper.eq(StringUtils.isNotNull(commodity.getId()), Commodity::getId, commodity.getId())
                .like(StringUtils.isNotEmpty(commodity.getName()), Commodity::getName, commodity.getName())
                .gt(StringUtils.isNotNull(begin), Commodity::getIntegration, commodity.getIntegration())
                .lt(StringUtils.isNotNull(end), Commodity::getIntegration, commodity.getIntegration());

        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
            // 使用分页查询
            Page<Commodity> page = new Page<>(pageNum, pageSize);
            IPage<Commodity> iPage = commodityService.page(page, queryWrapper);
            return getMpDataTable(iPage.getRecords(), iPage.getTotal());
        } else {
            List<Commodity> list = commodityService.list(queryWrapper);
            return getMpDataTable(list, (long) list.size());
        }
    }

    @GetMapping("/add")
    public String add(){
        return prefix+"/add";
    }

    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Commodity commodity){
        return toAjax(commodityService.save(commodity));
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap){
        mmap.put("commodity", commodityService.getById(id));
        return prefix+"/edit";
    }

    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Commodity commodity){
        return toAjax(commodityService.updateById(commodity));
    }

    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        if (StringUtils.isNull(ids)){
            return AjaxResult.error("id为空");
        }
        return toAjax(commodityService.removeByIds(Arrays.asList(Convert.toLongArray(ids))));
    }
}
