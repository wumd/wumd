package com.lagou.sqlSession;

import com.lagou.config.BoundSql;
import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;
import com.lagou.utils.GenericTokenParser;
import com.lagou.utils.ParameterMapping;
import com.lagou.utils.ParameterMappingTokenHandler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleExecutor implements Executor {
    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception{
        PreparedStatement preparedStatement = getPreparedStatement(configuration, mappedStatement, params);
//        5执行sql
        ResultSet resultSet = preparedStatement.executeQuery();
        String resultType = mappedStatement.getResultType();
        Class<?> resultTypeClass = getClassType(resultType);
        List list = new ArrayList<>();
//        6封装返回结果集
        while (resultSet.next()){
            Object o = resultTypeClass.newInstance();
            ResultSetMetaData metaData = resultSet.getMetaData();
            for(int i=1;i<=metaData.getColumnCount();i++){
//                字段名
                String columnName = metaData.getColumnName(i);
                Object value = resultSet.getObject(columnName);

//              s使用反射进行封装
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(o,value);
            }
            list.add(o);
        }
        return list;
    }

//    执行更新或者删除
    @Override
    public <E> void deleteOrInsert(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        PreparedStatement preparedStatement = getPreparedStatement(configuration, mappedStatement, params);
        preparedStatement.execute();
    }

    //    执行更新
    @Override
    public int update(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        PreparedStatement preparedStatement = getPreparedStatement(configuration, mappedStatement, params);

//        5执行sql
        int i = preparedStatement.executeUpdate();
        return i;
    }



    //转换sql的格式
    private BoundSql getBoundSql(String sql){
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        String parseSql = genericTokenParser.parse(sql);
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
        BoundSql boundSql = new BoundSql(parseSql, parameterMappings);
        return boundSql;
    }

    private Class<?> getClassType(String parameterType) throws ClassNotFoundException {
        if(parameterType != null){
            Class<?> aClass = Class.forName(parameterType);
            return aClass;
        }
        return null;
    }

//    获取准备对象
    private PreparedStatement getPreparedStatement(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        //1.注册驱动，获取连接
                    Connection connection = configuration.getDataSource().getConnection();

//        2.获取sql语句，并转换成JDBC的格式
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);

//        3获取预处理对象 preparedStatement
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());

//        4设置参数
//          获取到参数的全路径
        String parameterType = mappedStatement.getParameterType();
//        根据路劲获取实体类
        Class<?> parametertypeClass = getClassType(parameterType);
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String content = parameterMapping.getContent();

//            反射
            Field declaredField = parametertypeClass.getDeclaredField(content);
//            暴力访问
            declaredField.setAccessible(true);
            Object o = declaredField.get(params[0]);
            preparedStatement.setObject(i+1,o);
        }
        return preparedStatement;
    }
}

