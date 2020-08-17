package spriteManagers;

import constants.Constants;
import sprites.Fruit;
import image.Image;
import image.ImageFactory;

import javax.swing.*;
import java.util.ArrayList;

public class FruitManager {

    private static ArrayList<ImageIcon> fruitList;
    private static Fruit fruit;
    private static long gameStart;

    public static void initialize() {
        fruitList = new ArrayList<>();
        fruitList.add(ImageFactory.createImage(Image.FRUIT0));
        fruitList.add(ImageFactory.createImage(Image.FRUIT1));
        fruitList.add(ImageFactory.createImage(Image.FRUIT2));
        fruitList.add(ImageFactory.createImage(Image.FRUIT3));
        fruitList.add(ImageFactory.createImage(Image.FRUIT4));
        fruitList.add(ImageFactory.createImage(Image.FRUIT5));
        fruitList.add(ImageFactory.createImage(Image.FRUIT6));
        fruitList.add(ImageFactory.createImage(Image.FRUIT7));
    }

    public static void chooseFruit(int level){
        int i = ((level-1) % fruitList.size());
        fruit = new Fruit(Constants.FRUIT_POINTS[i],fruitList.get(i));
    }

    public static void setGameStart(){
        gameStart = System.currentTimeMillis();
    }

    public static Fruit getFruit(){
        if(System.currentTimeMillis() >= gameStart + 15000 && System.currentTimeMillis() <= gameStart + 21000 && !fruit.isDead()){
            return fruit;
        }
        return null;
    }
}
