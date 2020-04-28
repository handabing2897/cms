package com.sunshine.cms.mapper;

import com.sunshine.cms.entity.MovingMap;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (CmsMovingMap)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-28 16:23:34
 */
public interface MovingMapMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MovingMap queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<MovingMap> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param cmsMovingMap 实例对象
     * @return 对象列表
     */
    List<MovingMap> queryAll(MovingMap cmsMovingMap);

    /**
     * 新增数据
     *
     * @param cmsMovingMap 实例对象
     * @return 影响行数
     */
    int insert(MovingMap cmsMovingMap);

    /**
     * 修改数据
     *
     * @param cmsMovingMap 实例对象
     * @return 影响行数
     */
    int update(MovingMap cmsMovingMap);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}