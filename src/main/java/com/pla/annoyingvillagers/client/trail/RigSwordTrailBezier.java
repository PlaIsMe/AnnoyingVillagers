package com.pla.annoyingvillagers.client.trail;

import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

/**
 * Cubic Bezier interpolation adapted from Epic Fight's CubicBezierCurve (GPL-3.0).
 * Reworked here to use plain Java arrays and AnnoyingVillagers' Vec3 trail pipeline.
 * Source: https://github.com/Antikythera-Studios/epicfight
 */
final class RigSwordTrailBezier {
    private RigSwordTrailBezier() {}

    static List<Vec3> interpolate(List<Vec3> points, int sliceBegin, int sliceEnd, int interpolatedResults) {
        if (points.size() < 3 || interpolatedResults < 1) return List.of();
        sliceBegin = Math.max(sliceBegin, 0);
        sliceEnd = Math.min(sliceEnd, points.size() - 1);

        int size = points.size();
        double[] x = new double[size];
        double[] y = new double[size];
        double[] z = new double[size];
        for (int i = 0; i < size; i++) {
            x[i] = points.get(i).x;
            y[i] = points.get(i).y;
            z[i] = points.get(i).z;
        }

        Controls xc = controls(x);
        Controls yc = controls(y);
        Controls zc = controls(z);
        List<Vec3> result = new ArrayList<>();

        for (int i = sliceBegin; i < sliceEnd; i++) {
            if (!result.isEmpty()) result.remove(result.size() - 1);
            Vec3 start = points.get(i);
            Vec3 end = points.get(i + 1);
            for (int j = 0; j <= interpolatedResults; j++) {
                double t = (double) j / (double) interpolatedResults;
                result.add(new Vec3(
                        cubic(start.x, end.x, xc.a[i], xc.b[i], t),
                        cubic(start.y, end.y, yc.a[i], yc.b[i], t),
                        cubic(start.z, end.z, zc.a[i], zc.b[i], t)
                ));
            }
        }
        return result;
    }

    private static Controls controls(double[] points) {
        int n = points.length - 1;
        double[] a = new double[n];
        double[] b = new double[n];
        double[] rhs = new double[n];
        double[] matrix = new double[Math.max(1, n)];
        matrix[0] = 0.5D;

        rhs[0] = points[0] + 2.0D * points[1];
        for (int i = 1; i < n - 1; i++) rhs[i] = 4.0D * points[i] + 2.0D * points[i + 1];
        rhs[n - 1] = 8.0D * points[n - 1] + points[n];

        for (int i = 1; i < n; i++) matrix[i] = 1.0D / (4.0D - matrix[i - 1]);

        double[] converted = new double[n];
        converted[0] = rhs[0] * 0.5D;
        for (int i = 1; i < n; i++) {
            if (i == n - 1) converted[i] = (rhs[i] - 2.0D * converted[i - 1]) / (7.0D - 2.0D * matrix[i - 1]);
            else converted[i] = (rhs[i] - converted[i - 1]) / (4.0D - matrix[i - 1]);
        }

        for (int i = n - 1; i >= 0; i--) {
            a[i] = i == n - 1 ? converted[i] : converted[i] - a[i + 1] * matrix[i];
        }
        for (int i = 0; i < n; i++) {
            b[i] = i == n - 1 ? (a[i] + points[i + 1]) * 0.5D : 2.0D * points[i + 1] - a[i + 1];
        }
        return new Controls(a, b);
    }

    private static double cubic(double start, double end, double a, double b, double t) {
        double omt = 1.0D - t;
        return omt * omt * omt * start + 3.0D * t * omt * omt * a + 3.0D * t * t * omt * b + t * t * t * end;
    }

    private record Controls(double[] a, double[] b) {}
}
