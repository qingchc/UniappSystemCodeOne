package com.cqcqya.application.controller;

import com.cqcqya.application.mapper.UserMapper;
import com.cqcqya.application.pojo.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.ibatis.io.Resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

@RestController
public class UserController {
    //1.获取sqlsession方法
    public static SqlSession getSession() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        return sqlSession;
    }
    //2.登录接口
    @CrossOrigin
    @RequestMapping("/userLogin")
    public void login(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        String account = httpServletRequest.getParameter("account");
        String password = httpServletRequest.getParameter("password");
        UserMapper userMapper;
        try
        {
            userMapper = getSession().getMapper(UserMapper.class);
            if(account.contains("@")){
                User user = userMapper.selectUserByMail(account);
                if(user==null){
                    httpServletResponse.getWriter().write("NOACCOUNT");
                }else if(!Objects.equals(user.getPassword(), password)){
                    httpServletResponse.getWriter().write("PASSWORDFALSE");
                }else if(Objects.equals(user.getPassword(), password)){
                    System.out.println(user.getId());
                    httpServletResponse.getWriter().write(user.getId().toString());
                }
            }else{
                System.out.println(userMapper.selectUserByTel(account));
                User user = userMapper.selectUserByTel(account);
                if(user==null){
                    httpServletResponse.getWriter().write("NOACCOUNT");
                }else if(!Objects.equals(user.getPassword(), password)){
                    httpServletResponse.getWriter().write("PASSWORDFALSE");
                }else if(Objects.equals(user.getPassword(), password)){
                    System.out.println(user.getId());
                    httpServletResponse.getWriter().write(user.getId().toString());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //3.创建用户
    @CrossOrigin
    @RequestMapping("/createUser")
    public void create(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws IOException {
        String mail = httpServletRequest.getParameter("mail");
        String tel = httpServletRequest.getParameter("tel");
        String password = httpServletRequest.getParameter("password");
        SqlSession sqlSession= getSession();;
        UserMapper userMapper;
        try{
            userMapper = sqlSession.getMapper(UserMapper.class);
            Integer flag = userMapper.createUser(mail, tel, password);
            sqlSession.commit();
            httpServletResponse.getWriter().write(flag.toString());
        }catch (Exception e){
            sqlSession.rollback();
            e.printStackTrace();
        }


    }
    //4.手机号是否已经存在
    @CrossOrigin
    @RequestMapping("/mailExis")
    public void mailExis(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String mail = httpServletRequest.getParameter("mail");
        UserMapper userMapper;
        try{
            userMapper = getSession().getMapper(UserMapper.class);
            if(userMapper.selectUserByMail(mail)==null){
                httpServletResponse.getWriter().write("NO");
            }else{
                httpServletResponse.getWriter().write("YES");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //4.邮箱是否已经存在
    @CrossOrigin
    @RequestMapping("/telExis")
    public void telExis(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String tel = httpServletRequest.getParameter("tel");
        UserMapper userMapper;
        try{
            userMapper = getSession().getMapper(UserMapper.class);
            if(userMapper.selectUserByTel(tel)==null){
                httpServletResponse.getWriter().write("NO");
            }else{
                httpServletResponse.getWriter().write("YES");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
