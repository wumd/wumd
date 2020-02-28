//package com.lagou.dao;
//
//import com.lagou.io.Resources;
//import com.lagou.pojo.User;
//import com.lagou.sqlSession.DefaultSqlSession;
//import com.lagou.sqlSession.SqlSession;
//import com.lagou.sqlSession.SqlSessionFactory;
//import com.lagou.sqlSession.SqlSessionFactoryBuilder;
//import org.dom4j.DocumentException;
//
//import java.beans.PropertyVetoException;
//import java.io.InputStream;
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Method;
//import java.lang.reflect.Proxy;
//import java.sql.SQLException;
//import java.util.List;
//
//public class UserDaoImpl implements IUserDao{
//    @Override
//    public List<User> findAll() throws Exception{
//        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        List<User> userList = sqlSession.selectList("user.findAll");
//        return userList;
//    }
//
//    @Override
//    public User findOne(User user) throws Exception {
//        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        User user1 = sqlSession.selectOne("user.selectOne", user);
//        return user1;
//    }
//
//    @Override
//    public <T> T getMapper(Class<?> mapperClass) {
////        生成动态代理实现类返回
//        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
//            @Override
//            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//
//            return null;
//            }
//        });
//        return null;
//    }
//}
