
public class Player
{
    private int pNumber;
    private int x, y;

    private boolean isSelected;

    public Player(int pNumber)
    {
        this.pNumber = pNumber;
        isSelected = false;
        switch(pNumber)
        {
            case 1:
                x = -2;
                y =  2;
                break;
            case 2:
                x =  2;
                y = -2;
                break;
            case 3:
                x = -2;
                y = -2;
                break;
            case 4:
                x =  2;
                y =  2;
                break;
        }
    }

    public int getpNumber() {
        return pNumber;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isSelected()
    {
        return isSelected;
    }
    public void select()
    {
        isSelected = true;
    }
    public void deselect()
    {
        isSelected = false;
    }
}
