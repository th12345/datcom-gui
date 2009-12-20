/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Abstracts;

import java.awt.Color;
import java.awt.Stroke;
import java.util.LinkedList;

/**
 *
 * @author -B-
 */
public class LineObject
{
    LinkedList<Double> rawDataX;
    LinkedList<Double> rawDataY;
    int normalizedData[];
    int size;
    double maxX;
    double maxY;
    int lastSize;
    boolean isNormalized;
    Color color;
    Stroke stroke;

    public LineObject(LinkedList<Double> xpoints, LinkedList<Double> ypoints)
    {
        maxX = 1;
        maxY = 1;
        isNormalized = false;
        this.rawDataX = xpoints;
        this.rawDataY = ypoints;

        // Make sure that the x coordinate & y coordinates are the same size,
        // if not use the smaller to avoid array overruns
        size = Math.min(rawDataX.size(), rawDataY.size());
        normalizedData = new int[size * 2];
    }

    public int[] getPoints()
    {
        return normalizedData;
    }  

    public void normalize(int maxHeight, int maxWidth)
    {
        // Do a quick check to see if the object has previously been normalized
        // for the current screen size, if it has abort to save cycles..
        int newSize = maxHeight + maxWidth;
        if(newSize == lastSize)
        {
            return;
        }

        // If not, normalize
        int z = 0;
        Double tempX = 0.0;
        Double tempY = 0.0;
        calcMaxValues();

        // Translate the points from cartesian coordinate system to the
        // Java, top-left coordinate system.
        for(int i = 0; i < size; i++)
        {
            // Just scale the X value, its pretty normal
            tempX = ((rawDataX.get(i) / maxX) * (double)maxWidth);
            // The Y coordinate has to be flipped and scaled
            tempY = maxHeight - ((rawDataY.get(i) / maxY) * (double)maxHeight);
            // The normalized data is stuffed into 1 array, the odd index values
            // are the y's and the even are the x's
            // (Memory structure):
            // Ex: [x1][y1][x2][y2]...[xn][yn]....
            normalizedData[z++] = tempX.intValue();
            normalizedData[z++] = tempY.intValue();
        }
        isNormalized = true;
        lastSize = maxHeight + maxWidth;
    }

    private void calcMaxValues()
    {
        for(int i = 0; i < size; i ++)
        {
            if(rawDataX.get(i) > maxX)
            {
                maxX = rawDataX.get(i).intValue();
            }

            if(rawDataY.get(i) > maxY)
            {
                maxY = rawDataY.get(i).intValue();
            }
        }
        // Scale the max values so that there is a bit of a border in the drawing
        // panel
        maxX *= 1.1;
        maxY *= 1.1;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Stroke getStroke() {
        return stroke;
    }

    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
    }
}
