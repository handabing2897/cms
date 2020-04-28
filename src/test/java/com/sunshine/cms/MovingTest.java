package com.sunshine.cms;


import com.sunshine.cms.entity.MovingMap;
import com.sunshine.cms.service.MovingMapService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * DESC：
 *
 * @author handabing
 * DATE：2020/4/28
 * TIME：5:49 下午
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MovingTest {

    @Resource
    private MovingMapService movingMapService;

    @Test
    public void getList(){
        List<MovingMap> movingMaps = movingMapService.queryAllByLimit(10, 0);
        if(movingMaps == null){
            return;
        }

    }
}
