package com.rubik.system.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rubik.system.mapper.TicketOrdersMapper;
import com.rubik.system.domain.TicketOrders;
import com.rubik.system.service.TicketOrdersService;
@Service
public class TicketOrdersServiceImpl extends ServiceImpl<TicketOrdersMapper, TicketOrders> implements TicketOrdersService{

}
