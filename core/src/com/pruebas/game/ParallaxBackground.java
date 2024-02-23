package com.pruebas.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class ParallaxBackground extends Actor {

    /**
     * Variable que lleva la cuenta del desplazamiento horizontal
     */
    private int scroll;

    /**
     * Un array de texturas que representan las diferentes capas del fondo.
     */
    private Array<Texture> layers;

    /**
     * Constante que define la diferencia de velocidad entre las capas del fondo
     */
    private final int LAYER_SPEED_DIFFERENCE = 1;

    /**
     *  Variables que controlan la posición, tamaño, escala, origen,
     *  rotación y volteo de las texturas al dibujarlas.
     */
    float x,y,width,heigth,scaleX,scaleY;

    /**
     *  Variables que controlan la posición y origen
     */
    int originX, originY,rotation,srcX,srcY;

    /**
     *  Variables volteo de las texturas al dibujarlas
     */
    boolean flipX,flipY;

    /**
     *  La velocidad de desplazamiento del fondo
     */
    private int speed;

    /**
     * Constructor de la clase que recibe un array de texturas como parámetro.
     * Configura las texturas para que se repitan en modo espejo y establece
     * algunos valores por defecto para las variables de posición y escala.
     * @param textures
     */
    public ParallaxBackground(Array<Texture> textures){
        layers = textures;
        for(int i = 0; i <textures.size;i++){
            layers.get(i).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        }
        scroll = 0;
        speed = 0;

        x = y = originX = originY = rotation = srcY = 0;
        width =  Gdx.graphics.getWidth();
        heigth = Gdx.graphics.getHeight();
        scaleX = scaleY = 1;
        flipX = flipY = false;
    }

    public void setSpeed(int newSpeed){
        this.speed = newSpeed;
    }

    /**
     *  Método que dibuja el fondo.
     *  Ajusta el color del lote (batch), actualiza el desplazamiento horizontal según la velocidad y
     *  luego dibuja cada capa del fondo en función de su posición de desplazamiento y
     *  la diferencia de velocidad entre capas.
     * @param batch lienzo
     * @param parentAlpha The parent alpha, to be multiplied with this actor's alpha, allowing the parent's alpha to affect all
     *           children.
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);

        scroll+=speed;
        for(int i = 0;i<layers.size;i++) {
            srcX = scroll + i*this.LAYER_SPEED_DIFFERENCE *scroll;
            batch.draw(layers.get(i), x, y, originX, originY, width, heigth,scaleX,scaleY,rotation,srcX,srcY,layers.get(i).getWidth(),layers.get(i).getHeight(),flipX,flipY);
        }
    }

}
