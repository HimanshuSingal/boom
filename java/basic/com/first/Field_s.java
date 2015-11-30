package basic.com.first;

/**
 * Created by DELL on 10/23/2015.
 */
public class Field_s {
    public int val;
    public int player;  // 0 if neutral, 1 player 1,2 player 2
    public Field_s()
    {
        this.player=0;
        this.val=0;
    }

    public Field_s(int val,int player)
    {
        this.player=player;
        this.val=val;
    }
    public void setVal(int val,int player)
    {
        this.player=player;
        this.val=val;
    }

    public boolean cmp(Field_s b)
    {
        return (this.player==b.player) && (this.val==b.val);
    }

}
