package com.wunian.shirodemo;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;

public class ShiroTest {

    public static void main(String[] args) {
        //testAuthentication();
        testRoles();
    }

    /**
     * 登录认证
     */
    public static void testAuthentication(){
        //创建一个SimpleAccountRealm
        SimpleAccountRealm simpleAccountRealm=new SimpleAccountRealm();
        //添加一个账号
        simpleAccountRealm.addAccount("Jack","123456");
        //1,构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
        //设置Realm
        defaultSecurityManager.setRealm(simpleAccountRealm);


        //2,主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        //获取主体
        Subject subject=SecurityUtils.getSubject();
        //用户名和密码的token
        UsernamePasswordToken token=new UsernamePasswordToken("Jack","123456");
        //进行登入
        subject.login(token);
        System.out.print("是否权限认证：isAuthenticated="+subject.isAuthenticated());
        //登出logout
        subject.logout();
        //如果登入的信息中用户名或者密码错误就会抛出异常。
        System.out.print("是否权限认证：isAuthenticated="+subject.isAuthenticated());
    }

    /**
     * 权限认证
     */
    public static void testRoles(){

        SimpleAccountRealm simpleAccountRealm=new SimpleAccountRealm();
        simpleAccountRealm.addAccount("jack","123456","admin");
        DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);


        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject=SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken("jack","123456");
        subject.login(token);
        System.out.println("是否权限认证：isAuthenticated="+subject.isAuthenticated());
        //判断用户是否有角色权限，如果没有会抛出异常
        subject.checkRoles("admin","user");

    }


}
