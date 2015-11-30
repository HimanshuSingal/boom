package basic.com.first;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Vector;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by DELL on 10/21/2015.
 */
public class Parameters_s {
    public static int turn ;
    public static Queue<Integer> queue = new LinkedList<Integer>();
    public static int col_size=4;
    public static int row_size=5;
    int i=0;
    int j=0;
    //public static int[][] num=new int[row_size][col_size];
    //public static int[][] tmp=new int[row_size][col_size];
    public static Field_s[][] num;
    public static Field_s[][] tmp;
    //  public static Arra_s num=new Arra_s(new int[row_size][col_size],row_size,col_size);
    public Parameters_s() {
         num=new Field_s[row_size][col_size];
        tmp=new Field_s[row_size][col_size];

        for (int i = 0; i < row_size; i++) {
            for (int j = 0; j < col_size; j++) {
                tmp[i][j] = new Field_s();
                num[i][j] = new Field_s();

            }

        }
    }
    public static void set_size(int cols,int rows)
    {
        col_size=cols;
        row_size=rows;

    }

    public static int clicked(int position, int t)
    {
        Vector<Arra_s> v = new Vector<Arra_s>() ;
        turn =t ;
        queue.add(position) ;
        int i = position / Parameters_s.col_size;
        int j = position % Parameters_s.col_size;
        if(num[i][j].player!=turn && num[i][j].player!=0)
            return -1;

        (num[i][j].val)++;
        (num[i][j].player)=turn;

        int index  =0  ;
        v.add(new Arra_s(num,row_size,col_size));
        for(i=0;i<row_size;i++){
            for( j=0;j<col_size;j++){
            tmp[i][j] = num[i][j];
            }
        }
        int count = 0 ;
        while(!queue .isEmpty()) {
            count++ ;
            boolean flag  = false  ;
            position = queue.peek();
            queue.remove();
             i = position / Parameters_s.col_size;
             j = position % Parameters_s.col_size;
            if (corner(i, j)) {
                if (num[i][j].val >= 2) {
                    num[i][j].val %= 2;
                    if(num[i][j].val==0)
                        num[i][j].player=0;
                    flag = true ;
                    pass(i, j, t);
                }
            } else if (side(i, j)) {
                if (num[i][j].val >= 3) {
                    num[i][j].val %= 3;
                    if(num[i][j].val==0)
                        num[i][j].player=0;

                    flag = true;
                    pass(i, j, t);
                }

            } else {
                if (num[i][j].val >= 4) {
                    num[i][j].val %= 4;
                    if(num[i][j].val==0)
                        num[i][j].player=0;

                    flag= true;
                    pass(i, j, t);
                }

            }
            boolean value = true ;
            if(flag) {
                for (int k = 0; k < v.size() - 1; k++) {
                    Arra_s tp = v.get(k);
                    if (tp.cmp(num)) {
                        value = false;
                        break;
                    }
                }
            }
            if(!value) {
                int r = 0 ;
                if(queue.isEmpty())r=1;
                while(!queue.isEmpty())queue.remove() ;
                if(r == 0 ){
                    for(i=0;i<row_size;i++){
                        for(j=0;j<col_size;j++)
                        {
                            num[i][j].val=0;
                            num[i][j].player=0;
                        }
                    }
                }v.clear();
                return r;
            }
            index++;
            v.add(new Arra_s(num, row_size, col_size));
        }
        v.clear();
        return 1;
    }
public  static  void  reset()
{
    while(!queue.isEmpty())queue.remove() ;

    for(int i=0;i<row_size;i++){
        for(int j=0;j<col_size;j++)
        {
            num[i][j].val=0;
            num[i][j].player=0;
        }
    }

}
   public  static boolean checkEnd(){
       boolean[] pl=new boolean[3];//default false

       int pt;
        for(int i=0;i<row_size;i++){
            for(int j=0;j<col_size;j++){
                pt=num[i][j].player;
                pl[pt]=true;
            }
        }
       boolean rt=pl[1]^pl[2];
       if(!rt)
           return rt;
       reset();
     return rt;
    }

   public static void ad(int i,int j){
        if(!outside(i,j)){
            num[i][j].val++;
            num[i][j].player=turn;

            if(corner(i,j)){if(num[i][j].val>=2)
                queue.add(findpoistion(i , j));}
            else if(side(i,j)){if(num[i][j].val>=3)
                queue.add(findpoistion(i , j));}
            else{if(num[i][j].val>=4)
                queue.add(findpoistion(i, j));}
        }

    }
    public static void pass(int i,int j,int t){
        ad(i - 1, j);ad(i, j - 1);ad(i + 1, j);ad(i, j + 1);
    }
    public static int findpoistion(int i ,int j){
        return i*Parameters_s.col_size + j ;
    }
    public static Field_s value(int position)
    {
        int i=position/Parameters_s.col_size;
        int j=position%Parameters_s.col_size;
        return num[i][j];
    }


    static boolean corner(int i,int j)
    {
        return (i==0&&j==0) || (i==0 && j==(col_size-1)) || (i==(row_size-1) && j==0) || (i==(row_size-1) && j== (col_size-1));
    }
   static boolean side(int i,int j)
    {
        return (i==0) || (i==(row_size-1)) || (j==0) || (j==(col_size-1));
    }
    static boolean outside(int i,int j){
        return ((i < 0 || j < 0 ) || (i >= row_size) || (j >= col_size)) ;
    }

}
