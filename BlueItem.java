import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A BeltItem created by a GreenItemFactory
 * 
 * @author Skyler Calaman and Connie Yang
 * @version 0.1
 */
public abstract class BlueItem extends Actor implements BeltItem
{
    public void destroy() {
        getWorld().removeObject(this);
    }
    
    public void doAction() {
        GreenfootImage newImg = getImage();
        newImg.setTransparency(128);
        setImage(newImg);
    }
    
    public void setWorldX(int x) {
        setLocation(x, getY());
    }
}
