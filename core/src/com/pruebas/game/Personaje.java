package com.pruebas.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Personaje {

    // Vector donde se encuentra el personaje
    Vector2 position;

    // Animación del personaje
    Animation animation;

    // Frame actual del personaje
    TextureRegion currentFrame;
    float cont = 0;

    /**
     * Ancho y alto de la imagen y body del personaje
     */
    public static int anchoPersonaje,altoPersonaje;

    /**
     * BodyDef del personaje
     */
    BodyDef bodyDef;

    /**
     * cuerpo del personaje
     */
    Body body;

    /**
     * Controla el color del personaje
     */
    boolean isNaranja = true;

    /**
     * Controla si el personaje está muerto
     */
    boolean death;

    /**
     * Animación del personaje Naranja
     */
    public Animation animationNaranja = new Animation(0.25f, new TextureRegion[]{
            new Sprite(new Texture(Gdx.files.internal("personajes/personaje_naranja_0.png"))), new Sprite(new Texture(Gdx.files.internal("personajes/personaje_naranja_1.png"))),
            new Sprite(new Texture(Gdx.files.internal("personajes/personaje_naranja_2.png"))), new Sprite(new Texture(Gdx.files.internal("personajes/personaje_naranja_3.png"))),
            new Sprite(new Texture(Gdx.files.internal("personajes/personaje_naranja_4.png")))

    });

    /**
     * Animación del personaje Naranja muriendo
     */
    public Animation muerteNaranja = new Animation(0.25f, new TextureRegion[]{
            new Sprite(new Texture(Gdx.files.internal("personajes/personaje_naranja_muerte_0.png"))), new Sprite(new Texture(Gdx.files.internal("personajes/personaje_naranja_muerte_1.png"))),
            new Sprite(new Texture(Gdx.files.internal("personajes/personaje_naranja_muerte_2.png"))), new Sprite(new Texture(Gdx.files.internal("personajes/personaje_naranja_muerte_3.png"))),
            new Sprite(new Texture(Gdx.files.internal("personajes/personaje_naranja_muerte_4.png"))), new Sprite(new Texture(Gdx.files.internal("personajes/personaje_naranja_muerte_5.png"))),
            new Sprite(new Texture(Gdx.files.internal("personajes/personaje_naranja_muerte_6.png")))

    });

    /**
     * Animación del personaje Azul
     */
    public Animation animationAzul = new Animation(0.25f, new TextureRegion[]{
            new Sprite(new Texture(Gdx.files.internal("personajes/personaje_azul_0.png"))), new Sprite(new Texture(Gdx.files.internal("personajes/personaje_azul_1.png"))),
            new Sprite(new Texture(Gdx.files.internal("personajes/personaje_azul_2.png"))), new Sprite(new Texture(Gdx.files.internal("personajes/personaje_azul_3.png"))),
            new Sprite(new Texture(Gdx.files.internal("personajes/personaje_azul_4.png")))

    });

    /**
     * Animación del personaje Azul muriendo
     */
    public Animation muerteAzul = new Animation(0.25f, new TextureRegion[]{
            new Sprite(new Texture(Gdx.files.internal("personajes/personaje_azul_muerte_0.png"))), new Sprite(new Texture(Gdx.files.internal("personajes/personaje_azul_muerte_1.png"))),
            new Sprite(new Texture(Gdx.files.internal("personajes/personaje_azul_muerte_2.png"))), new Sprite(new Texture(Gdx.files.internal("personajes/personaje_azul_muerte_3.png"))),
            new Sprite(new Texture(Gdx.files.internal("personajes/personaje_azul_muerte_4.png"))), new Sprite(new Texture(Gdx.files.internal("personajes/personaje_azul_muerte_5.png"))),
            new Sprite(new Texture(Gdx.files.internal("personajes/personaje_azul_muerte_6.png")))
    });


    /**
     * Inicializa el personaje en las coordenadas x,y en el mundo que se le pase
     * @param x coordenada x donde se crea el personaje
     * @param y coordenada y donde se crea el personaje
     * @param world mundo donde se va a crear
     */
    public Personaje(float x, float y, World world) {
        position = new Vector2(x, y);
        death = false;
        animation = animationNaranja;
        anchoPersonaje = (int)GameConstants.ANCHO_PANTALLA/24;
        altoPersonaje = (int)GameConstants.ALTO_PANTALLA/12;

        bodyDef = new BodyDef();
        bodyDef.fixedRotation = true;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(anchoPersonaje / 2, altoPersonaje / 2 -10);
        body.setUserData("player");

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = ps;
        fixtureDef.density = 10f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;
        Fixture fixture = body.createFixture(fixtureDef);
    }

    /**
     * mueve el body y la imagen del personaje dichas coordenadas x e y
     * @param x suma a x a la posicion.x del personaje y body
     * @param y suma a y a la posicion.y del personaje y body
     */
    public void move(float x, float y) {;
        body.setTransform(new Vector2(body.getPosition().x+x*2,body.getPosition().y+y*2),0);
//        position.add(body.getPosition().x, body.getPosition().y);
    }

    /**
     * dibuja la imagen en la posición indicada para poder detectar las colisiones de manera óptima
     * @param batch
     */
    public void render(SpriteBatch batch) {
        batch.draw(currentFrame, body.getPosition().x - (anchoPersonaje / 2), body.getPosition().y - (altoPersonaje / 2));
    }

    /**
     * Cada frame actualiza la pantalla con los nuevos valores , color de la animación del personaje,
     * frame de la animación, posición
     */
    public void update() {
        if(death) {
            animation = isNaranja?muerteNaranja:muerteAzul;
        }
        cont++;
        body.setTransform(new Vector2(body.getPosition().x,body.getPosition().y-1),0);
        currentFrame = (TextureRegion) animation.getKeyFrame(cont, true);
    }
}
