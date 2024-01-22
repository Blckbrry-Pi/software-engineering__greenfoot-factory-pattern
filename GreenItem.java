import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A BeltItem created by a GreenItemFactory
 * 
 * @author Skyler Calaman and Connie Yang
 * @version 0.1
 */
public abstract class GreenItem extends Actor implements BeltItem
{
    public void destroy() {
        getWorld().removeObject(this);
    }
    
    public void doAction() {
        turn(120);
    }
    
    public void setWorldX(int x) {
        setLocation(x, getY());
    }
    
    
}
