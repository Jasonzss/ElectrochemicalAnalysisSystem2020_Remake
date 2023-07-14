package com.bluedot.application.electrochemistry;

import cn.hutool.core.collection.CollectionUtil;
import com.bluedot.application.electrochemistry.dto.CurveData;
import com.bluedot.application.electrochemistry.vo.CurveProcessTable;
import com.bluedot.domain.process.model.Curve;
import com.bluedot.domain.process.model.Point;
import com.bluedot.domain.rbac.User;
import com.bluedot.infrastructure.exception.CommonErrorCode;
import com.bluedot.infrastructure.exception.CustomException;
import com.bluedot.infrastructure.repository.CurveDataRepository;
import com.bluedot.infrastructure.utils.Quantity;
import com.bluedot.infrastructure.utils.UnitConversion;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jason
 * @creationDate 2023/07/11 - 16:00
 */
public class ElectrochemistryService {
    @Inject
    private CurveDataRepository curveDataRepository;

    public CurveData processFile(CurveProcessTable table, String userEmail){
        CurveData curveData = table.generateCurveData();
        curveData.setUser(new User(userEmail));

        readFile(table.getFile(), curveData);

        return curveDataRepository.save(curveData);
    }

    /**
     * 读取用户上传的txt文件，将其中的ep、ip、点位数据读取出来并存入波形数据对象中
     * @param fileItem txt文件
     * @param curveData 波形数据
     */
    private void readFile(FileItem fileItem, CurveData curveData){
        try {
            //创建字符输入流读取对象读取传入的文件
            InputStreamReader isr = new InputStreamReader(fileItem.getInputStream(),"GBK");
            BufferedReader br = new BufferedReader(isr);

            //将所有的点位数据读取到CurveData中
            List<Point> points = new ArrayList<>();
            BigDecimal ep = null;
            BigDecimal ip = null;

            //先读取第一行
            String line = br.readLine();
            boolean flag = false;

            while(!flag){
                if(!StringUtils.isEmpty(line)){
                    if(line.startsWith("Ep = ")){
                        line = line.trim();
                        ep = new BigDecimal(line.substring(line.indexOf("Ep = ")+5, line.length()-1));
                    } else if(line.startsWith("ip = ")){
                        line = line.trim();
                        ip = new BigDecimal(line.substring(line.indexOf("ip = ")+5, line.length()-1));
                        flag = true;
                    }
                }

                line = br.readLine();
            }

            line = br.readLine();
            flag = false;
            while (line != null){
                if(flag){
                    line = line.trim();
                    if (StringUtils.isNoneBlank(line)){
                        String[] split = line.split(", ");
                        points.add(new Point(new BigDecimal(split[0]), new BigDecimal(split[1])));
                    }
                } else if (line.trim().contains("Potential/V, Current/A")){
                    flag = true;
                }

                //不断读取每一行
                line = br.readLine();
            }

            //将电压电流放入ExpData中
            if (CollectionUtil.isEmpty(points)){
                throw new CustomException(CommonErrorCode.E_4002);
            }

            Curve curve = new Curve();
            curve.setPoints(points);

            curveData.setOriginalPointsData(curve);
            curveData.setOriginalEp(new Quantity(ep, UnitConversion.Unit.VOL_V));
            curveData.setOriginalIp(new Quantity(ip, UnitConversion.Unit.AMP_A));
        } catch (NumberFormatException e) {
            throw new CustomException(CommonErrorCode.E_4004);
        } catch (IOException e) {
            throw new CustomException(CommonErrorCode.E_4003);
        }
    }

    public CurveDataRepository getCurveDataRepository() {
        return curveDataRepository;
    }

    public void setCurveDataRepository(CurveDataRepository curveDataRepository) {
        this.curveDataRepository = curveDataRepository;
    }
}
