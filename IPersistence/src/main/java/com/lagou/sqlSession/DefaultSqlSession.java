package com.lagou.sqlSession;

import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;

import java.beans.IntrospectionException;
import java.lang.reflect.*;
import java.sql.SQLException;
import java.util.List;

public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;
    public DefaultSqlSession(Configuration configuration){
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementid, Object... params) throws  Exception {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementid);
        List<E> list = simpleExecutor.query(configuration, mappedStatement, params);
        return list;
    }

    @Override
    public <T> T selectOne(String statementid, Object... params) throws Exception {
        List<Object> objects = selectList(statementid, params);
        if(objects.size()==1){
            return (T) objects.get(0);
        }else {
            throw new RuntimeException("查询结果为空或者返回结果过多");
        }
    }

    @Override
    public <T> T getMapper(Class<?> mapperClass) {
//        使用jdk动态代理来为dao接口生成代理对象
        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //准备参数1.statementid,namespace.id
                //2方法名，findAll
                String methodName = method.getName();
//                根据方法获取所在类，根据所在类获取全限定名
                String name = method.getDeclaringClass().getName();
//               statementId
                String statementId = name+"."+methodName;
//                根据statementId获取sql
                String sql = configuration.getMappedStatementMap().get(statementId).getSql();
//                根据sql判断是增删改查
                if (sql.contains("select")){
//                获取返回值类型
                    Type genericReturnType = method.getGenericReturnType();
//                判断返回值类型是否有泛型
                    if(genericReturnType instanceof ParameterizedType){
                        List<Object> list = selectList(statementId);
                        return list;
                    }
                    return selectOne(statementId,args);
                }else if (sql.contains("update")){
                    return update(statementId,args);
                }else if (sql.contains("insert")|sql.contains("delete")){
                    deleteOrInsert(statementId,args);
                }else if (sql.contains("delete")){
                    deleteOrInsert(statementId,args);
                }else {
                    throw new Exception("sql语句错误");
                }
                return null;
            }
        });
        return (T)proxyInstance;
    }

    @Override
    public int update(String statementid, Object... params) throws Exception {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementid);
        int update = simpleExecutor.update(configuration, mappedStatement, params);
        return update;
    }

    @Override
    public void deleteOrInsert(String statementid, Object... params) throws Exception {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementid);
        simpleExecutor.deleteOrInsert(configuration,mappedStatement,params);
    }


}
