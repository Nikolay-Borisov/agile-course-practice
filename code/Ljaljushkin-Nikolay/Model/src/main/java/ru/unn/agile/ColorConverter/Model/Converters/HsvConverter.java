package ru.unn.agile.ColorConverter.model.Converters;

import ru.unn.agile.ColorConverter.model.ColorSpaces.*;

public final class HsvConverter extends BaseConverter {

    public static final double MAX_RGB = 255.0;
    public static final double R_DIVIDER = 6.0;
    public static final int G_OFFSET = 2;
    public static final int B_OFFSET = 4;
    public static final int DEGREES = 60;
    public static final double EPS = 0.0001;

    @Override
    public void fromRgb(final Rgb srcColor, final ColorSpace3D dstColor) {

        double arithmeticR = srcColor.getFirstChannel() / MAX_RGB;
        double arithmeticG = srcColor.getSecondChannel() / MAX_RGB;
        double arithmeticB = srcColor.getThirdChannel() / MAX_RGB;

        double max = Math.max(arithmeticR, Math.max(arithmeticG, arithmeticB));
        double min = Math.min(arithmeticR, Math.min(arithmeticG, arithmeticB));
        double delta = max - min;

        double h = 0;
        if (delta >= EPS) {
            if (max == arithmeticR) {
                h = DEGREES * ((arithmeticG - arithmeticB) / delta % R_DIVIDER);
            } else if (max == arithmeticG) {
                h = DEGREES * ((arithmeticB - arithmeticR) / delta + G_OFFSET);
            } else if (max == arithmeticB) {
                h = DEGREES * ((arithmeticR - arithmeticG) / delta + B_OFFSET);
            }
        }

        double s = max <= 0 ? 0 : 1 - min / max;
        double v = max;

        dstColor.setChannels(h, s, v);
    }

    @Override
    public Rgb toRgbColor(final ColorSpace3D srcColor) {

        double h = srcColor.getFirstChannel();
        double s = srcColor.getSecondChannel();
        double v = srcColor.getThirdChannel();

        final double degrees = 60.0;
        final double maxRGB = 255.0;
        final int numberOfSection = 6;

        int section = (int) (Math.floor(h / degrees)) % numberOfSection;
        double fractionalPartOfHue = h / degrees - Math.floor(h / degrees);

        double p = v * (1 - s);
        double q = v * (1 - fractionalPartOfHue * s);
        double t = v * (1 - (1 - fractionalPartOfHue) * s);

        final int firstSection = 0;
        final int secondSection = 1;
        final int thirdSection = 2;
        final int fourthSection = 3;
        final int fifthSection = 4;

        double r, g, b;
        switch (section) {
            case firstSection:
                r = v;
                g = t;
                b = p;
                break;
            case secondSection:
                r = q;
                g = v;
                b = p;
                break;
            case thirdSection:
                r = p;
                g = v;
                b = t;
                break;
            case fourthSection:
                r = p;
                g = q;
                b = v;
                break;
            case fifthSection:
                r = t;
                g = p;
                b = v;
                break;
            default:
                r = v;
                g = p;
                b = q;
        }
        return new Rgb(r * maxRGB, g * maxRGB, b * maxRGB);
    }
}
