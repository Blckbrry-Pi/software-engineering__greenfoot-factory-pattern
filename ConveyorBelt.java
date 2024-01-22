import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class ConveyorBelt here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ConveyorBelt extends Actor
{
    public static int LOOP_SIZE = 100;
    
    public static double ADVANCE_AMOUNT = 0.01;
    public static double TIME_DEC = 0.1;
    
    public static double GARBAGE_BOUNDARY = 0.6;
    public static double ACTIVATE_BOUNDARY = 0.8;
    
    HashMap<BeltItem, Double> items = new HashMap<>();
    
    HashMap<BeltItemFactory, Double> factories = new HashMap<>();
    
    public void addItem(double position, BeltItem item) {
        items.putIfAbsent(item, new Double(position));
    }
    public void addFactory(BeltItemFactory factory) {
        factories.putIfAbsent(factory, new Double(0));
    }
    
    
    private int getWorldX(double pos) {
        return (int) (pos * getWorld().getWidth());
    }
    private double getInternalPos(int worldX) {
        return (double) worldX / getWorld().getWidth();
    }
    
    private void advanceItems() {
        items.replaceAll((k, v) -> v + ADVANCE_AMOUNT);
        items.forEach((k, v) -> k.setWorldX(getWorldX(v)));
    }
    
    private void cullItems() {
        ArrayList<BeltItem> toRemove = new ArrayList<>();
        
        for (Map.Entry<BeltItem, Double> entry : items.entrySet()) {
            if (entry.getValue() > 1.0)  {
                toRemove.add(entry.getKey());
            }
        }
        
        for (BeltItem item : toRemove) {
            item.destroy();
            items.remove(item);
        }
    }
    
    private void activate() {
        items.forEach((k, v) -> {
            boolean wasBefore = v - ADVANCE_AMOUNT < ACTIVATE_BOUNDARY;
            boolean isAhead = v >= ACTIVATE_BOUNDARY;
            if (wasBefore && isAhead) {
                k.doAction();
            }
        });
    }
    
    private void cullGarbage() {
        ArrayList<BeltItem> toRemove = new ArrayList<>();
        
        for (Map.Entry<BeltItem, Double> entry : items.entrySet()) {
            boolean wasBefore = entry.getValue() - ADVANCE_AMOUNT < GARBAGE_BOUNDARY;
            boolean isAhead = entry.getValue() >= GARBAGE_BOUNDARY;
            if (wasBefore & isAhead && entry.getKey().isGarbage())  {
                toRemove.add(entry.getKey());
            }
        }
        
        for (BeltItem item : toRemove) {
            item.destroy();
            items.remove(item);
        }
    }
    
    
    private void generate() {
        factories.replaceAll((k, v) -> {
            if (v > TIME_DEC) {
                return v - TIME_DEC;
            } else {
                addItem(getInternalPos(k.getWorldX()), k.create());
                return k.getSecondDelay();
            }
        });
    }
    
    /**
     * Act - do whatever the ConveyorBelt wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        generate();
        
        advanceItems();
        cullItems();
        cullGarbage();
        activate();
        
        updateImage();
        
        int worldWidth = getWorld().getWidth();
        int relevantOldWorldPos = getX() - worldWidth / 2 + LOOP_SIZE;
        double oldInternalPos = getInternalPos(relevantOldWorldPos);
        int newPos = getWorldX(getInternalPos(getX()) + ADVANCE_AMOUNT);
        int newAdjustedPos = newPos % LOOP_SIZE + worldWidth / 2 - LOOP_SIZE;
        setLocation(newAdjustedPos, getWorld().getHeight() * 3 / 4);
    }
    
    private void updateImage() {
        World w = getWorld();
        GreenfootImage img = new GreenfootImage(
            w.getWidth() + LOOP_SIZE * 2,
            w.getHeight() / 2);
        img.setColor(Color.DARK_GRAY);
        img.fill();
        img.setColor(Color.LIGHT_GRAY);
            
        for (int i = 0; i <= w.getWidth() + LOOP_SIZE; i += LOOP_SIZE) {
            img.drawLine(i, 0, i, img.getHeight());
        }
        
        setImage(img);
    }
}
