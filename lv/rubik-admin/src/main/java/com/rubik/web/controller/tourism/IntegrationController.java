package com.rubik.web.controller.tourism;

import com.rubik.common.core.controller.BaseController;
import com.rubik.common.core.domain.AjaxResult;
import com.rubik.system.domain.Integration;
import com.rubik.system.service.IntegrationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/tourism/integration")
public class IntegrationController extends BaseController {

    @Resource
    private IntegrationService integrationService;

    @PostMapping("/getIntegration")
    public AjaxResult getIntegration(Long userId){
        Integration integration = integrationService.getById(userId);
        if (integration == null) {
            return AjaxResult.success(0);
        } else {
            return AjaxResult.success(integration.getIntegration());
        }

    }
}
