/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Abstracts;

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

    LinkedList<LineObject> objects;
    int centerH;

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

    @Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D g2D = (Graphics2D)g;
        int currentPoints [];
        LineObject lo;
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
