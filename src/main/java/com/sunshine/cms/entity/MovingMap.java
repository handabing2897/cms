package com.sunshine.cms.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (MovingMap)实体类
 *
 * @author dabing
 * @since 2020-04-28 16:19:42
 */
public class MovingMap implements Serializable {
    private static final long serialVersionUID = -12869800925448731L;
    /**
    * 主键id
    */
    private Long id;
    /**
    * 动图名
    */
    private String movingName;
    /**
    * 动态图路径
    */
    private String movingPath;
    /**
    * 状态：01有效，02无效
    */
    private String status;
    /**
    * 类型 01轮廓图，02logo
    */
    private String type;
    /**
    * 创建人
    */
    private String createUser;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 修改人
    */
    private String updateUser;
    /**
    * 修改时间
    */
    private Date updateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMovingName() {
        return movingName;
    }

    public void setMovingName(String movingName) {
        this.movingName = movingName;
    }

    public String getMovingPath() {
        return movingPath;
    }

    public void setMovingPath(String movingPath) {
        this.movingPath = movingPath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}