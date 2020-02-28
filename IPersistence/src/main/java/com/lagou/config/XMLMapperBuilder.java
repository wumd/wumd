package com.lagou.config;

import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

public class XMLMapperBuilder {

    private Configuration configuration;
    public XMLMapperBuilder(Configuration configuration){
        this.configuration = configuration;
    }

    public void parse(InputStream inputStream) throws DocumentException {
        Document document = new SAXReader().read(inputStream);
        Element rootElement = document.getRootElement();
        String namespace = rootElement.attributeValue("namespace");
        List<Element> list = rootElement.selectNodes("//select|//update|//delete|//insert");
        for (Element element : list) {
            MappedStatement mappedStatement = new MappedStatement();
            String id = element.attributeValue("id");
            String parameter = element.attributeValue("paramType");
            String resultType = element.attributeValue("resultType");
            String sql = element.getTextTrim();
            mappedStatement.setId(id);
            mappedStatement.setParameterType(parameter);
            mappedStatement.setResultType(resultType);
            mappedStatement.setSql(sql);
            configuration.getMappedStatementMap().put(namespace+"."+id,mappedStatement);
        }
    }
}
