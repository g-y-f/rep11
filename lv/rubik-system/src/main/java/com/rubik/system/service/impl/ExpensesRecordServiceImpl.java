package com.rubik.system.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rubik.system.mapper.ExpensesRecordMapper;
import com.rubik.system.domain.ExpensesRecord;
import com.rubik.system.service.ExpensesRecordService;
@Service
public class ExpensesRecordServiceImpl extends ServiceImpl<ExpensesRecordMapper, ExpensesRecord> implements ExpensesRecordService{

}
