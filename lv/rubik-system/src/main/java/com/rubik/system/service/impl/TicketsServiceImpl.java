package com.rubik.system.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rubik.system.mapper.TicketsMapper;
import com.rubik.system.domain.Tickets;
import com.rubik.system.service.TicketsService;
@Service
public class TicketsServiceImpl extends ServiceImpl<TicketsMapper, Tickets> implements TicketsService{

}
