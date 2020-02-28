package com.lagou.sqlSession;

import com.lagou.config.XMLConfigBuilder;
import com.lagou.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.sql.SQLException;

public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(InputStream inputStream) throws PropertyVetoException, DocumentException, SQLException {
        //使用dom4j解析配置文件，将解析出来的类容封装在configuration
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parseConfig(inputStream);

        //创建sqlSessionFactory对象，生产sqlsession
        DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(configuration);

        return defaultSqlSessionFactory;
    }
}
