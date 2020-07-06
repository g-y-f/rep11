package com.rubik.framework.shiro.service;

import java.io.Serializable;

import com.rubik.framework.shiro.session.OnlineSession;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rubik.common.utils.StringUtils;
import com.rubik.system.domain.SysUserOnline;
import com.rubik.system.service.ISysUserOnlineService;

/**
 * 会话db操作处理
 * 
 * @author jeefast
 */
@Component
public class SysShiroService
{
    @Autowired
    private ISysUserOnlineService onlineService;

    /**
     * 删除会话
     *
     * @param onlineSession 会话信息
     */
    public void deleteSession(OnlineSession onlineSession)
    {
        onlineService.deleteOnlineById(String.valueOf(onlineSession.getId()));
    }

    /**
     * 获取会话信息
     *
     * @param sessionId
     * @return
     */
    public Session getSession(Serializable sessionId)
    {
        SysUserOnline userOnline = onlineService.selectOnlineById(String.valueOf(sessionId));
        return StringUtils.isNull(userOnline) ? null : createSession(userOnline);
    }

    public Session createSession(SysUserOnline userOnline)
    {
        OnlineSession onlineSession = new OnlineSession();
        if (StringUtils.isNotNull(userOnline))
        {
            onlineSession.setId(userOnline.getSessionId());
            onlineSession.setHost(userOnline.getIpaddr());
            onlineSession.setBrowser(userOnline.getBrowser());
            onlineSession.setOs(userOnline.getOs());
            onlineSession.setDeptName(userOnline.getDeptName());
            onlineSession.setUserName(userOnline.getUserName());
            onlineSession.setStartTimestamp(userOnline.getStartTimestamp());
            onlineSession.setLastAccessTime(userOnline.getLastAccessTime());
            onlineSession.setTimeout(userOnline.getExpireTime());
        }
        return onlineSession;
    }
}
