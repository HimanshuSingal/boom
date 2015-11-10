package basic.com.first;

/**
 * Created by $rohit on 10/22/2015.
 */
public class Arra {
    public  Field[][] aray = new Field [Parameters.row_size][Parameters.col_size];
    public  Arra(Field[][] initialArray, int row ,int col){
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++)
            {
                aray[i][j]=new Field();
                aray[i][j].setVal(initialArray[i][j].val,initialArray[i][j].player) ;
                 }
        }
    }

    public boolean cmp(Field [][] nb){
        for(int i=0;i<Parameters.row_size;i++){
            for(int j=0;j<Parameters.col_size;j++){
                if(!nb[i][j].cmp(aray[i][j]))return false;
            }
        }
        return true;
    }
}

