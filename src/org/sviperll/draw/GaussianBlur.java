/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sviperll.draw;

import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

/**
 *
 * @author vir
 */
public class GaussianBlur {
    public static GaussianBlur prepare(double sigmaHor, double sigmaVer, int width, int height, RenderingHints renderingHints) {
        BufferedImageOp opHor = null;
        if (sigmaVer != 0) {
            float[] kernelValuesHor = gausianKernelValues(sigmaHor, width);
            Kernel kernelHor = new Kernel(kernelValuesHor.length, 1, kernelValuesHor);
            opHor = new ConvolveOp(kernelHor, ConvolveOp.EDGE_NO_OP, renderingHints);
        }

        BufferedImageOp opVer = null;
        if (sigmaVer != 0) {
            float[] kernelValuesVer = gausianKernelValues(sigmaVer, height);
            Kernel kernelVer = new Kernel(1, kernelValuesVer.length, kernelValuesVer);
            opVer = new ConvolveOp(kernelVer, ConvolveOp.EDGE_NO_OP, renderingHints);
        }
        return new GaussianBlur(opHor, opVer);
    }

    private static float[] gausianKernelValues(double sigma, int n) {
        double[] dvs = new double[n * 2 + 1];
        double sum = 0.0;
        for (int i = 0; i < dvs.length; i++) {
            double x = i - n;
            double v = 1.0 / Math.sqrt(2 * Math.PI * sigma * sigma) * Math.exp(- x * x / (2 * sigma * sigma));
            dvs[i] = v;
            sum += v;
        }

        float[] res = new float[n * 2 + 1];
        for (int i = 0; i < res.length; i++) {
            res[i] = (float)(dvs[i] / sum);
        }
        return res;
    }
    private final BufferedImageOp opHor;
    private final BufferedImageOp opVer;

    private GaussianBlur(BufferedImageOp opHor, BufferedImageOp opVer) {
        this.opHor = opHor;
        this.opVer = opVer;
    }

    public BufferedImage filter(BufferedImage image) {
        if (opHor != null) {
            image = opHor.filter(image, null);
        }
        if (opVer != null) {
            image = opVer.filter(image, null);
        }
        return image;
    }
}
