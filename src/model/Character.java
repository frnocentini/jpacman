package model;

import image.ImageSet;

public abstract class Character extends Sprite{

    protected ImageSet imageSet;

    public abstract void move();

    public abstract void addImageSet();

}
