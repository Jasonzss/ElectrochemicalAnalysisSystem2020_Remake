package com.bluedot.domain.process.model;

import com.bluedot.infrastructure.utils.Quantity;
import com.bluedot.infrastructure.utils.UnitConversion.Unit;

import java.math.BigDecimal;

/**
 * @author Jason
 * @creationDate 2023/07/05 - 23:11
 * 波形图中计算出来的波形参数
 *
 * TODO 从学长的资料中发现波形参数有三个：电流频率、电压频率、相位差。最后这个相位差是什么？
 * 应该是需求ppt中Ep和Ip下面的那个Ap，是怎么算出来的呢？
 *
 */
public class CurveParameter {
    private static final Unit DEFAULT_IP_UNIT = Unit.AMP_A;
    private static final Unit DEFAULT_EP_UNIT = Unit.VOL_V;

    private Quantity ip;
    private Quantity ep;
    //目前还没用途
    private Quantity ap;

    public CurveParameter(Quantity ip, Quantity ep) {
        this.ip = ip;
        this.ep = ep;
    }

    public CurveParameter(Double ip, Double ep) {
        this.ip = new Quantity(BigDecimal.valueOf(ip), DEFAULT_IP_UNIT);
        this.ep = new Quantity(BigDecimal.valueOf(ep), DEFAULT_EP_UNIT);
    }

    public Quantity getIp() {
        return ip;
    }

    public void setIp(Quantity ip) {
        this.ip = ip;
    }

    public Quantity getEp() {
        return ep;
    }

    public void setEp(Quantity ep) {
        this.ep = ep;
    }

    public Quantity getAp() {
        return ap;
    }

    public void setAp(Quantity ap) {
        this.ap = ap;
    }
}
