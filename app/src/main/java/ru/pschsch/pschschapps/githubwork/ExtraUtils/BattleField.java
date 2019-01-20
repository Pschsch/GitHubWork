package ru.pschsch.pschschapps.githubwork.ExtraUtils;

public class BattleField {
    static int[][] filed = new int[][]{{1, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                                {1, 0, 1, 0, 0, 0, 0, 0, 1, 0},
                                {1, 0, 1, 0, 1, 1, 1, 0, 1, 0},
                                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                                {0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                                {0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

    public static boolean fieldValidator(int[][] field) {
        if(field.length!=10) return false;
        for(int[] fields : field){
            if (fields.length!=10) return false;

        }
        int fourShip = 0;
        int threeShip = 0;
        int twoShip = 0;
        int oneShip = 0;
        for(int j=0; j<field.length; j++){
            for(int i=0; i<field[j].length;i++){
                if(i!=1&&i!=0) return true;
                if(i==0) continue;
                if(i==1){

                }
            }
        }

return false;
    }
    public static void main(String[] args){

    }
}


