package com.rubik.system.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rubik.system.domain.ExchangeHistory;
import com.rubik.system.mapper.ExchangeHistoryMapper;
import com.rubik.system.service.ExchangeHistoryService;
@Service
public class ExchangeHistoryServiceImpl extends ServiceImpl<ExchangeHistoryMapper, ExchangeHistory> implements ExchangeHistoryService{

}
