package com.lagou.test;

import com.lagou.dao.IUserDao;
import com.lagou.io.Resources;
import com.lagou.pojo.User;
import com.lagou.sqlSession.SqlSession;
import com.lagou.sqlSession.SqlSessionFactory;
import com.lagou.sqlSession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class IPersistenceTest {

    @Test
    public void test() throws Exception {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
//        获取IUserDaod的代理实现类
        IUserDao iUserDao = sqlSession.getMapper(IUserDao.class);

//        查询一条数据
//        User user = new User();
//        user.setId(2);
//        user.setUsername("wu");
//        User user1 = iUserDao.findOne(user);
//        System.out.println(user1);

//        查询所有数据
//        List<User> list = iUserDao.findAll();
//        for (User user2 : list) {
//            System.out.println(user2);
//        }

//        更新一条
//        User user3 = new User();
//        user3.setId(4);
//        user3.setUsername("linda");
//        int update = iUserDao.update(user3);
//        if(update==1){
//            System.out.println("更新成功");
//        }else {
//            System.out.println("更新失败");
//        }

//        删除一条
//        User user4 = new User();
//        user4.setId(2);
//        iUserDao.delete(user4);

//        新增一条
        User user5 = new User();
        user5.setId(10);
        user5.setUsername("yoyo");
        iUserDao.insert(user5);

    }

}
