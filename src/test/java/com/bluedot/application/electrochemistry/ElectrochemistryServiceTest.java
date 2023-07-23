package com.bluedot.application.electrochemistry;

import com.bluedot.BaseTest;
import com.bluedot.application.electrochemistry.dto.CurveData;
import com.bluedot.application.electrochemistry.vo.CurveFileProcessForm;
import com.bluedot.infrastructure.repository.CurveDataRepository;
import com.bluedot.infrastructure.repository.SpringApp;
import org.apache.commons.fileupload.FileItem;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Jason
 * @creationDate 2023/07/14 - 12:32
 */
public class ElectrochemistryServiceTest extends BaseTest {
    ElectrochemistryService service;
    CurveFileProcessForm table = new CurveFileProcessForm();
    CurveDataRepository repository;

    @Before
    public void init() throws IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringApp.class);
        repository = context.getBean(CurveDataRepository.class);

        service = new ElectrochemistryService();
        service.setCurveDataRepository(repository);

        File file = new File("src\\test\\resources\\ea\\aaa.txt");
        FileItem mock = Mockito.mock(FileItem.class);
        Mockito.when(mock.getInputStream()).thenReturn(new FileInputStream(file));
        table.setFile(mock);
        table.setDesc("这是描述信息");
        table.setBufferSolutionId(10000007);
        table.setMaterialName("乱来的名称");
        table.setPh(12.3);
        table.setMaterialSolubility(22.6);
        table.setUnit("mol/L");
        table.setMaterialTypeId(1);
    }

    @Test
    public void testProcessFile(){
        CurveData data = service.processFile(table, "456");
        log.info(data.toString());
        repository.delete(data);
    }
}
