package com.lagou.dao;

import com.lagou.pojo.User;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.List;

public interface IUserDao {
//      查询所有
    public List<User> findAll() throws Exception;
//    根据条件查询
    public User findOne(User user) throws Exception;


//    修改一条数据
    public int  update(User user) throws Exception;

//    删除一条数据
    public void delete(User user)throws Exception;

//    新增一条数据
    public void insert(User user) throws Exception;
}
