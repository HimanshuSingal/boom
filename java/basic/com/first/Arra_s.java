package basic.com.first;

/**
 * Created by $rohit on 10/22/2015.
 */
public class Arra_s {
    public  Field_s[][] aray = new Field_s [Parameters_s.row_size][Parameters_s.col_size];
    public  Arra_s(Field_s[][] initialArray, int row ,int col){
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++)
            {
                aray[i][j]=new Field_s();
                aray[i][j].setVal(initialArray[i][j].val,initialArray[i][j].player) ;
                 }
        }
    }

    public boolean cmp(Field_s [][] nb){
        for(int i=0;i<Parameters_s.row_size;i++){
            for(int j=0;j<Parameters_s.col_size;j++){
                if(!nb[i][j].cmp(aray[i][j]))return false;
            }
        }
        return true;
    }
}

