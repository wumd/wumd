package com.lagou.sqlSession;

import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;

import java.util.List;

public interface Executor {
//    执行查询
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement,Object... params) throws Exception;

//    执行更新
    public <E> int update(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception;

//    执行删除
    public <E> void deleteOrInsert(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception;
}
