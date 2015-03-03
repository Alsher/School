import java.util.Random;

public class Game
{
    public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;

    private Random r;

    private Player p1, p2, p3, p4;
    private Renderer renderer;
    private GUI gui;

    private int curPlayer;

    public Game()
    {
        r = new Random();

        p1 = new Player(1);
        p2 = new Player(2);
        p3 = new Player(3);
        p4 = new Player(4);

        curPlayer = 4;

        gui = new GUI(this);
        renderer = new Renderer(p1, p2, p3, p4);

        newRound();
    }

    public void newRound()
    {
        if(curPlayer == 4) { curPlayer = 1; }
        else { ++curPlayer; }
        int d1 = getRandom();
        int d2 = getRandom();
        gui.update(curPlayer, d1, d2);
    }

    public boolean updatePlayerPosition(int selectedPlayer, int direction)
    {
        Player curPlayer = getPlayer(selectedPlayer);
        switch(direction)
        {
            case UP: {
                if(curPlayer.getY() == 4) return false;
                else curPlayer.setY(curPlayer.getY() + 1);
                break;
            }
            case DOWN: {
                if(curPlayer.getY() == -4) return false;
                else curPlayer.setY(curPlayer.getY() - 1);
                break;
            }
            case LEFT: {
                if(curPlayer.getX() == -4) return false;
                else curPlayer.setX(curPlayer.getX() - 1);
                break;
            }
            case RIGHT: {
                if(curPlayer.getX() == 4) return false;
                else curPlayer.setX(curPlayer.getX() + 1);
                break;
            }
        }
        renderer.update();
        if(curPlayer.getX() == 0 && curPlayer.getY() == 0)
        {
            System.out.println("Team " + (selectedPlayer <= 2 ? "Blau" : "Rot") + " hat gewonnen!");
            System.exit(1);
        }
        return true;
    }

    public Player getPlayer(int number)
    {
        switch(number)
        {
            case 1: return p1;
            case 2: return p2;
            case 3: return p3;
            case 4: return p4;
            default: return null;
        }
    }
    private int getRandom()
    {
        return Math.abs(r.nextInt() % 4) + 1;
    }
}
