package com.bluedot.domain.process.model;

import com.bluedot.infrastructure.repository.converter.PointsConverter;
import com.bluedot.infrastructure.utils.LinearEquation;

import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.util.List;

/**
 * @author Jason
 * @creationDate 2023/07/11 - 16:53
 *
 */
@Embeddable
public class Curve {
    @Convert(converter = PointsConverter.class)
    private List<Point> points = null;

    public Curve() {
    }

    public Curve(List<Point> points) {
        this.points = points;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "Curve{" +
                "points=" + points +
                '}';
    }
}
