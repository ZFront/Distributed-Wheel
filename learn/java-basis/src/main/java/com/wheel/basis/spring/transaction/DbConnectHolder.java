package com.wheel.basis.spring.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @desc 将取出来的连接对象绑定到线程上，方便在AOP处理中取出，进行提交或者回滚操作。
 * @author: zhouf
 */
@Component
public class DbConnectHolder {

    @Autowired
    DataSource dataSource;

    /**
     * 线程绑定对象
     */
    ThreadLocal<Connection> resources = new NamedThreadLocal<>("Transactional resources");

    /**
     * 获取连接
     *
     * @return
     */
    public Connection getConnection() {
        Connection con = resources.get();
        if (con != null) {
            return con;
        }
        try {
            con = dataSource.getConnection();
            //为了体现事务，全部设置为手动提交事务
            con.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resources.set(con);
        return con;
    }

    /**
     * 释放
     */
    public void cleanHolder() {
        Connection con = resources.get();
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        resources.remove();
    }
}
