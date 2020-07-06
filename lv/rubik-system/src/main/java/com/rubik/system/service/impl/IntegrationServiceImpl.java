package com.rubik.system.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rubik.system.domain.Integration;
import com.rubik.system.mapper.IntegrationMapper;
import com.rubik.system.service.IntegrationService;
@Service
public class IntegrationServiceImpl extends ServiceImpl<IntegrationMapper, Integration> implements IntegrationService{

}
