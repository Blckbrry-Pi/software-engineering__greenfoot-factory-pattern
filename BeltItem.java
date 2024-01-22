/**
 * Write a description of class BeltItem here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public interface BeltItem  
{
    public void destroy();
    public boolean isGarbage();
    public void doAction();
    
    public void setWorldX(int x);
}
