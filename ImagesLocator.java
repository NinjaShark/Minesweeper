//ImagesLocator.java

import javax.swing.*;

/**
* Load images from correct place (directory or JAR file) into GUI.
*/
public class ImagesLocator
{
/**
* Load image from correct place (directory or JAR file).
*
* @param imageName name of image to be loaded (with no path)
* @return loaded image
*/
    public static ImageIcon getImage(String imageName)
    {
        ClassLoader cl = ImagesLocator.class.getClassLoader();
        ImageIcon i = new ImageIcon(cl.getResource(imageName));
        return i;
    }

}
