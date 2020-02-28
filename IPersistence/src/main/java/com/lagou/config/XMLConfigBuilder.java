package com.lagou.config;

import com.lagou.io.Resources;
import com.lagou.pojo.Configuration;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class XMLConfigBuilder {

    private Configuration configuration;

    public XMLConfigBuilder(){
        this.configuration = new Configuration();
    }

//使用dom4j对配置文件进行解析，封装到configuration
    public Configuration    parseConfig(InputStream inputStream) throws DocumentException, PropertyVetoException, SQLException {
        Document document = new SAXReader().read(inputStream);
//        获取到<configuration>标签
        Element rootElement = document.getRootElement();
        List<Element> elemetList = rootElement.selectNodes("//property");
        Properties properties = new Properties();
//        <property name="driverClass" value="com.mysql.jdbc.sql"></property>
//        <property name="jdbcUrl" value="jdbc:mysql///lagou"></property>
//        <property name="username" value="root"></property>
//        <property name="password" value="123456"></property>
        for (Element element : elemetList) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.setProperty(name,value);
        }
//        配置连接池
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(properties.getProperty("driverClass"));
        comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbcUrl").replace("&amp;","&"));
        comboPooledDataSource.setUser(properties.getProperty("username"));
        comboPooledDataSource.setPassword(properties.getProperty("password"));
        System.out.println(comboPooledDataSource.getConnection());
        configuration.setDataSource(comboPooledDataSource);

//        解析mapper
        List<Element> mapperList = rootElement.selectNodes("//mapper");
        for (Element element : mapperList) {
            String mapperPath = element.attributeValue("resource");
            InputStream mapperStream = Resources.getResourceAsStream(mapperPath);
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
            xmlMapperBuilder.parse(mapperStream);
        }

        return configuration;
    }

}


