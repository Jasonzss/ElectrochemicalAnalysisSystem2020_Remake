package com.bluedot.infrastructure.repository;

import com.bluedot.BaseTest;
import com.bluedot.application.electrochemistry.dto.CurveData;
import com.bluedot.domain.process.model.Curve;
import com.bluedot.domain.process.model.Point;
import com.bluedot.domain.rbac.User;
import com.bluedot.infrastructure.repository.data_object.BufferSolution;
import com.bluedot.infrastructure.repository.data_object.MaterialType;
import com.bluedot.infrastructure.utils.Quantity;
import com.bluedot.infrastructure.utils.UnitUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * @author Jason
 * @creationDate 2023/07/13 - 21:27
 */
public class CurveDataRepositoryTest extends BaseTest {
    private CurveDataRepository repository;
    private CurveData data;

    @Before
    public void init(){
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringApp.class);
        repository = context.getBean(CurveDataRepository.class);

        data = new CurveData();
        data.setUser(new User("456"));
        data.setPh(12.2);
        data.setDescription("这是描述");
        data.setMaterialName("这是物质名称");
        data.setMaterialSolubility(new Quantity(BigDecimal.ONE, UnitUtil.Unit.MOL_M));
        data.setBufferSolution(new BufferSolution(10000007));
        data.setOriginalIp(new Quantity(BigDecimal.TEN, UnitUtil.Unit.AMP_A));
        data.setOriginalEp(new Quantity(BigDecimal.ZERO, UnitUtil.Unit.VOL_V));
        data.setMaterialType(new MaterialType(1));
        Curve curve = new Curve();
        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(BigDecimal.ONE, BigDecimal.TEN));
        curve.setPoints(points);
        data.setOriginalPointsData(curve);
    }

    @Test
    public void testSave(){
        CurveData save = repository.save(data);
        log.info(save.toString());
//        repository.delete(save);
    }
}
