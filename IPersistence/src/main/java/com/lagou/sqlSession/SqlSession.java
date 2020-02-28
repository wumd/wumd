package com.lagou.sqlSession;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface SqlSession {

    //查询所有
    public <E> List<E> selectList(String statementid, Object... params) throws Exception;

    //查询一个
    public <T> T selectOne(String statementid ,Object... params) throws Exception;

    //为dao接口生成代理实现类
    public <T> T getMapper(Class<?> mapperClass);

//    更新数据
    public int update(String statementid ,Object... params) throws Exception;

//    删除数据
    public void deleteOrInsert(String statementid ,Object... params) throws Exception;




}

