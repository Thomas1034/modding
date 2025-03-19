package com.startraveler.verdant.util;

import java.awt.*;
import java.util.List;

public class OKLabBlender {
    private OKLabBlender() {
    }

    public static int blendColors(List<Integer> colors) {
        return blendColors(colors.stream().map(Color::new).toList(), false).getRGB();
    }

    public static Color blendColors(List<Color> colors, boolean dummy) {
        double sumL = 0, sumA = 0, sumB = 0;
        int n = colors.size();

        for (Color color : colors) {
            double[] oklab = sRGBtoOKLab(color);
            sumL += oklab[0];
            sumA += oklab[1];
            sumB += oklab[2];
        }

        double avgL = sumL / n;
        double avgA = sumA / n;
        double avgB = sumB / n;

        return OKLabToSRGB(avgL, avgA, avgB);
    }

    private static double[] sRGBtoOKLab(Color color) {
        double r = linearize(color.getRed() / 255.0);
        double g = linearize(color.getGreen() / 255.0);
        double b = linearize(color.getBlue() / 255.0);

        // Conversion to LMS
        double l = 0.4122214708 * r + 0.5363325363 * g + 0.0514459929 * b;
        double m = 0.2119034982 * r + 0.6806995451 * g + 0.1073969566 * b;
        double s = 0.0883024619 * r + 0.2817188376 * g + 0.6299787005 * b;

        // Nonlinear transform
        l = Math.cbrt(l);
        m = Math.cbrt(m);
        s = Math.cbrt(s);

        // Convert to OKLab
        double L = 0.2104542553 * l + 0.7936177850 * m - 0.0040720468 * s;
        double A = 1.9779984951 * l - 2.4285922050 * m + 0.4505937099 * s;
        double B = 0.0259040371 * l + 0.7827717662 * m - 0.8086757660 * s;

        return new double[]{L, A, B};
    }

    private static Color OKLabToSRGB(double L, double A, double B) {
        // Convert OKLab to LMS
        double l = L + 0.3963377774 * A + 0.2158037573 * B;
        double m = L - 0.1055613458 * A - 0.0638541728 * B;
        double s = L - 0.0894841775 * A - 1.2914855480 * B;

        // Reverse nonlinear transform
        l = Math.pow(l, 3);
        m = Math.pow(m, 3);
        s = Math.pow(s, 3);

        // Convert to linear sRGB
        double r = 4.0767416621 * l - 3.3077115913 * m + 0.2309699292 * s;
        double g = -1.2684380046 * l + 2.6097574011 * m - 0.3413193965 * s;
        double b = -0.0041960863 * l - 0.7034186147 * m + 1.7076147010 * s;

        // Gamma correction and clamping
        r = delinearize(r);
        g = delinearize(g);
        b = delinearize(b);

        return new Color(
                (float) Math.max(0, Math.min(1, r)),
                (float) Math.max(0, Math.min(1, g)),
                (float) Math.max(0, Math.min(1, b))
        );
    }

    private static double linearize(double channel) {
        return (channel <= 0.04045) ? (channel / 12.92) : Math.pow((channel + 0.055) / 1.055, 2.4);
    }

    private static double delinearize(double channel) {
        return (channel <= 0.0031308) ? (12.92 * channel) : (1.055 * Math.pow(channel, 1 / 2.4) - 0.055);
    }
}