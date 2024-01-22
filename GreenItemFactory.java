import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GreenItemFactory here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GreenItemFactory extends Actor implements BeltItemFactory
{
    private boolean nextIsGarbage = false;
    
    
    public void act() {
        try {
            getWorld()
                .getObjects(ConveyorBelt.class)
                .get(0)
                .addFactory(this);
        } catch (Exception e) {
            System.out.println(":(");
        }
    }
    
    public double getSecondDelay() {
        return Greenfoot.getRandomNumber(10) / 5.0 + 0.5;
    }
    
    public GreenItem create() {
        GreenItem item = nextIsGarbage
            ? new TrashGreenItem()
            : new GoodGreenItem();
            
        nextIsGarbage = !nextIsGarbage;
        
        getWorld().addObject(item, getX(), getY() + 20);
        
        return item;
    }
    
    public int getWorldX() {
        return getX();
    }
}
