package com.rubik.system.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rubik.system.mapper.GuidebookUserMapper;
import com.rubik.system.domain.GuidebookUser;
import com.rubik.system.service.GuidebookUserService;
@Service
public class GuidebookUserServiceImpl extends ServiceImpl<GuidebookUserMapper, GuidebookUser> implements GuidebookUserService{

}
