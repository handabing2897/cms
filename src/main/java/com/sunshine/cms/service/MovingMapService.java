package com.sunshine.cms.service;

import com.sunshine.cms.entity.MovingMap;
import com.sunshine.cms.mapper.MovingMapMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * (CmsMovingMap)表服务实现类
 *
 * @author makejava
 * @since 2020-04-28 17:18:17
 */
@Service
@Transactional
public class MovingMapService {
    @Resource
    private MovingMapMapper movingMapMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public MovingMap queryById(Long id) {
        return movingMapMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    public List<MovingMap> queryAllByLimit(int offset, int limit) {
        return movingMapMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param movingMap 实例对象
     * @return 实例对象
     */
    public MovingMap insert(MovingMap movingMap) {
        movingMapMapper.insert(movingMap);
        return movingMap;
    }

    /**
     * 修改数据
     *
     * @param movingMap 实例对象
     * @return 实例对象
     */
    public MovingMap update(MovingMap movingMap) {
        movingMapMapper.update(movingMap);
        return this.queryById(movingMap.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(Long id) {
        return movingMapMapper.deleteById(id) > 0;
    }
}