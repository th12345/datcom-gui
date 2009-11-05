/**
 * Copyright (c) 2009, Michael Dop, All rights reserved
 * 
 * Project: FinalProject
 * Package: property
 * File: ScreenResolution.java (Class ScreenResolution)
 * 
 */

package opendatcom;

/**
 * Class to get the screen resolution of the current computer
 * so it can be used to place the main frame.  This class is an 
 * easy way to access the screen height and width.  
 */
public class ScreenResolution{
		
	/**
	 * Instantiates a Screen Resolution object.
	 */
	private ScreenResolution(){
		toolkit = java.awt.Toolkit.getDefaultToolkit ();
    	d = toolkit.getScreenSize();
	}
	
    /**
     * A convenient static getter for the ScreenResolution instance.
     * 
     * @return the instance of ScreenResolution.
     */
    public static ScreenResolution getInstance() {
        if(screenResolution == null)
        	screenResolution = new ScreenResolution();
        
        return screenResolution;
    }     
    
    /**
     * Returns the width component of the screen resolution.
     * 
     * @return the width of the screen.
     */
    public int getWidth(){
    	return (int)d.getWidth();
    }
    	
    /**
     * Returns the height component of the screen resolution.
     * 
     * @return the height of the screen.
     */
    public int getHeight(){
    	return (int)d.getHeight();	
    }
    
    private java.awt.Dimension d = new java.awt.Dimension();
    private java.awt.Toolkit toolkit;    
    private static ScreenResolution screenResolution;
	    
}