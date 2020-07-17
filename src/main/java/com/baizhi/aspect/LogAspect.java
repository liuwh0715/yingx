package com.baizhi.aspect;

import com.baizhi.entity.Admin;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Aspect
@Configuration
public class LogAspect {

    @Resource
    HttpServletRequest request;

    //记录管理员的日志
    @Around("execution(* com.baizhi.service.impl.*.*(..)) && !execution(* com.baizhi.service.impl.*.query*(..))")//环绕通知
    public Object addLog(ProceedingJoinPoint proceedingJoinPoint) {

        //谁  操作 时间  是否成功
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        //时间
        Date date = new Date();
        //操作 获取方法名
        String methodname = proceedingJoinPoint.getSignature().getName();
        //放行
        Object resultMethod = null;
        try {
            resultMethod = proceedingJoinPoint.proceed();
            String message = "success";
            // System.out.println("管理员"+admin.getUsername()+"---时间"+date+"----操作"+methodname+"-------"+message);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            String message = "error";
            //System.out.println("管理员"+admin.getUsername()+"---时间"+date+"----操作"+methodname+"-------"+message);
        }

        return resultMethod;
    }
}
