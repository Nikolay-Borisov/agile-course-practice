package ru.unn.agile.Metrics.Model;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Vector;

import static org.junit.Assert.*;

public class TestMetrics {

    public final float epsilon = 0.01f;

    @Test
    public void checkL1MetricForOneDimensionVectors() {
        Float[] array = new Float[]{0.0f};

        Vector<Float> vector1 = new Vector<Float>(Arrays.asList(array));
        Vector<Float> vector2 = new Vector<Float>(Arrays.asList(array));

        float metricL1 = Metrics.L1(vector1, vector2);

        assertEquals(metricL1, 0.0f, epsilon);
    }

    @Test
    public void checkL2MetricForOneDimensionVectors() {
        Float[] array = new Float[]{0.0f};

        Vector<Float> vector1 = new Vector<Float>(Arrays.asList(array));
        Vector<Float> vector2 = new Vector<Float>(Arrays.asList(array));

        float metricL2 = Metrics.L2(vector1, vector2);

        assertEquals(metricL2, 0.0f, epsilon);
    }
}