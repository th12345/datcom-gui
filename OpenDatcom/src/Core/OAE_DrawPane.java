/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Core;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;
import javax.swing.JPanel;

/**
 *
 * @author -B-
 */
public class OAE_DrawPane extends JPanel
{
    static BasicStroke dStroke = new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    static BasicStroke rStroke = new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{3}, 0);

    LinkedList<LineObject> objects;
    LinkedList<int[]> refLines;
    int centerH;
    double maxX;
    double maxY;

    public OAE_DrawPane()
    {
        super();
        objects = new LinkedList<LineObject>();
    }

    public void addDrawObject(LineObject object)
    {
        object.normalize(this.getHeight(), this.getWidth());
        objects.add(object);

    }

    public void init()
    {
        centerH = this.getHeight() / 2;
    }

    public void clearObjects()
    {
        objects.clear();
    }

    private void findMaxValues()
    {
        int temp [];
        for (int i = 0; i < objects.size(); i++) 
        {
            temp = objects.get(i).getMaxValues();
            maxX = Math.max(maxX, temp[0]);
            maxY = Math.max(maxY, temp[0]);
        }
    }

    private void normalizeObjects()
    {
        int width = getWidth();
        int height = getHeight();
        for (int i = 0; i < objects.size(); i++)
        {
            objects.get(i).normalize(height, width, maxX, maxY);
        }
    }

    public void generateReferenceLines()
    {
        if(refLines == null)
        {
            refLines = new LinkedList<int[]>();
        }
        int temp[] = new int[4];
        int inc = getWidth() /10;
        int z = 0;
        temp[1] = 0;
        temp[3] = getHeight();

        for(int i = 0; i < 10; i++, z+= inc)
        {
            temp = new int[4];
            temp[0] = z;
            temp[1] = 0;
            temp[2] = z;
            temp[3] = getHeight();
            refLines.add(temp);
        }

        inc = getHeight() / 10;
        z = 0;

        for(int i = 0; i < 10; i++, z+= inc)
        {
            temp = new int[4];
            temp[0] = getWidth();
            temp[1] = z;
            temp[2] = 0;
            temp[3] = z;
            refLines.add(temp);
        }
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D g2D = (Graphics2D)g;
        int currentPoints [];
        LineObject lo;
        int temp[];
        generateReferenceLines();
        g2D.setStroke(rStroke);
        for(int i = 0; i < refLines.size(); i++)
        {
            temp = refLines.get(i);
            g.drawLine(temp[0], temp[1], temp[2], temp[3]);
        }
        g2D.setStroke(dStroke);
        for(int i = 0; i < objects.size(); i++)
        {
            lo = objects.get(i);
            lo.normalize(getHeight(), getWidth());
            currentPoints = objects.get(i).getPoints();
            g2D.setColor(lo.getColor());
            g2D.setStroke(lo.getStroke());
            for(int j = 0; j < (currentPoints.length - 3); j+=2)
            {
                g.drawLine(currentPoints[j]  , currentPoints[j+1],
                           currentPoints[j+2], currentPoints[j+3]);
            }
            g2D.setColor(Color.BLACK);
            g2D.setStroke(dStroke);
        }
    }
}
