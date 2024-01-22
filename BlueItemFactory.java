import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BlueItemFactory here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BlueItemFactory extends Actor implements BeltItemFactory
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
        return 2.0;
    }
    
    public BlueItem create() {
        BlueItem item = nextIsGarbage
            ? new TrashBlueItem()
            : new GoodBlueItem();
            
        nextIsGarbage = !nextIsGarbage;
        
        getWorld().addObject(item, getX(), getY() + 50);
        
        return item;
    }
    
    public int getWorldX() {
        return getX();
    }
}
