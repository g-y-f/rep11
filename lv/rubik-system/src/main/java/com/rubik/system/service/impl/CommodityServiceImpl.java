package com.rubik.system.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rubik.system.mapper.CommodityMapper;
import com.rubik.system.domain.Commodity;
import com.rubik.system.service.CommodityService;
@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements CommodityService{

}
