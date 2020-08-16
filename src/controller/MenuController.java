package controller;

import constants.Constants;

import java.io.File;

public class MenuController {

    private int mazesNumber;

    public MenuController() {
        this.mazesNumber = loadMazes();
    }

    private int loadMazes() {
        return new File(Constants.MAZES_DIR).list().length;
    }


    public String[] populateMazeStrings() {
        String[] mazeStrings = new String[this.mazesNumber];
        for(int i=0;i<this.mazesNumber;i++){
            mazeStrings[i] = new String(""+(i+1));
        }
        return mazeStrings;
    }

}
