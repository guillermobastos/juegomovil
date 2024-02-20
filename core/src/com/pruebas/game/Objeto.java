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

public class Objeto {
    Vector2 position;
    Animation proyectil;
    Texture bolaFuego;
    int random;
    int cont = 0;
    int anchoObjeto, altoObjeto;
    BodyDef bodyDef;
    World world;
    Body body;
    TextureRegion currentFrame;
    MiJuego miJuego;

    boolean isBolaFuego;

    boolean isCohete, isFlecha;

    /**
     * Crea un nuevo objeto aleatorio entre los posibles de la clase Objeto
     *
     * @param x       coordenada x donde se crea
     * @param y       coordenada y donde se crea
     * @param world   mundo en el que se crea, que debe coincidir con el resto
     * @param miJuego variable de la clase principal para controlar variables externas
     */
    public Objeto(float x, float y, World world, MiJuego miJuego) {
        this.miJuego = miJuego;
        objetoAleatorio();
        this.world = world;
        bodyDef = new BodyDef();
        bodyDef.fixedRotation = true;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        if (isBolaFuego) {
            x = (float) (Math.random() * (GameConstants.anchoPantalla - anchoObjeto) + anchoObjeto / 2);
            position = new Vector2(x, GameConstants.altoPantalla);
            bodyDef.position.set(x, GameConstants.altoPantalla);
        } else {
            position = new Vector2(x, y);
            bodyDef.position.set(x, y);
        }

        body = world.createBody(bodyDef);
        PolygonShape ps = new PolygonShape();
        if (isCohete) {
            ps.setAsBox(anchoObjeto / 2, altoObjeto / 2 - 25);
        } else if (isFlecha) {
            ps.setAsBox(anchoObjeto / 2-15, altoObjeto / 2 - 15);
        } else if (isBolaFuego) {
            ps.setAsBox(anchoObjeto / 2, altoObjeto / 2 );
        }
        FixtureDef fixtureDef = new FixtureDef();
        body.setUserData("objeto");
        body.setType(BodyDef.BodyType.DynamicBody);

        fixtureDef.shape = ps;
        fixtureDef.density = 0.2f;
        fixtureDef.friction = 0.1f;
        fixtureDef.restitution = 0.6f;

        body.createFixture(fixtureDef);
        body.createFixture(ps, 0.1f);
        ps.dispose();
    }

    /**
     * Cambia de objeto de manera aleatoria entre los tres distintos objetos, y asigna la
     * animación apropiada y proporciona las dimensiones adecuadas
     */
    public void objetoAleatorio() {
        random = (int) (Math.random() * 3 + 1);
        switch (random) {
            case 1:
                proyectil = new Animation(1f, new TextureRegion[]{
                        new Sprite(new Texture(Gdx.files.internal("cohete_0.png"))),
                        new Sprite(new Texture(Gdx.files.internal("cohete_1.png")))
                });
                anchoObjeto = (new Texture(Gdx.files.internal("cohete_0.png"))).getWidth();
                altoObjeto = (new Texture(Gdx.files.internal("cohete_0.png"))).getHeight();
                isCohete = true;
                isBolaFuego = false;
                break;
            case 2:
                proyectil = new Animation(1f, new TextureRegion[]{
                        new Sprite(new Texture(Gdx.files.internal("flecha_0.png"))),
                        new Sprite(new Texture(Gdx.files.internal("flecha_1.png")))
                });
                anchoObjeto = (new Texture(Gdx.files.internal("flecha_0.png"))).getWidth();
                altoObjeto = (new Texture(Gdx.files.internal("flecha_1.png"))).getHeight();
                isFlecha = true;
                isBolaFuego = false;
                break;
            case 3:
                bolaFuego = new Texture(Gdx.files.internal("bola_de_fuego.png"));
                anchoObjeto = bolaFuego.getWidth();
                altoObjeto = bolaFuego.getHeight();
                isBolaFuego = true;
                break;
        }
    }

    /**
     * mueve de posición al objeto
     *
     * @param x indicada las coordenadas en el eje x que se van a mover
     * @param y indicada las coordenadas en el eje y que se van a mover
     */
    public void move(float x, float y) {
        if (isCohete) {
            body.setTransform(new Vector2(body.getPosition().x + 2 * x, body.getPosition().y + 2 * y), 0);
        }
        if (isFlecha) {
            if (body.getPosition().x < GameConstants.anchoPantalla / 2) {
                body.setTransform(new Vector2(body.getPosition().x + x, body.getPosition().y + 1.5f * y), 0);
            } else {
                body.setTransform(new Vector2(body.getPosition().x + (1.5f * x), body.getPosition().y + y), 0);
            }
        }
        if (isBolaFuego) {
//            body.setTransform(new Vector2(body.getPosition().x, body.getPosition().y + 2 * y), 0);
            body.setTransform(new Vector2(body.getPosition().x, body.getPosition().y + 0.4f * y), 0);
        }
    }

    /**
     * Dibuja la imagen en dicha posición para que coincida con el body y se ajuste a la imagen
     *
     * @param batch lienzo donde se pinta dicha imagen
     */
    public void render(SpriteBatch batch) {
        if (isBolaFuego) {
            batch.draw(bolaFuego, body.getPosition().x - (anchoObjeto / 2) - 10, body.getPosition().y - (altoObjeto / 2));
        } else if (isFlecha) {
            batch.draw(currentFrame, body.getPosition().x - (anchoObjeto / 2) - 10, body.getPosition().y - (altoObjeto / 2) - 10);
        } else if (isCohete) {
//            batch.draw(currentFrame, body.getPosition().x - (anchoObjeto / 2) - 10, body.getPosition().y - (altoObjeto / 2) - 10);
            batch.draw(currentFrame, body.getPosition().x - (anchoObjeto / 2) - 10, body.getPosition().y - (altoObjeto / 2) - 10);
        }
    }

    /**
     * acutaliza los valores del Objeto (proyectil)
     *
     * @param x      coordenada x donde se encuentra el objeto
     * @param y      coordenada y donde se encuentra el objeto
     * @param metros metros recorridos por el personaje
     */
    public void update(float x, float y, double metros) {
        creaNuevoObjeto(position.x, position.y, metros);
        cont++;
        if (!isBolaFuego) {
            currentFrame = (TextureRegion) proyectil.getKeyFrame(cont, true);
            move(x * 2, y);
        } else {
            move(x, y);
        }
    }


    /**
     * Dependiendo de la posición de los objetos visibles se crean nuevos
     *
     * @param x      posicion x  donde se encuentra el objeto
     * @param y      posicion y donde se encuentra el objeto
     * @param metros metros actuales que lleva el personaje recorridos
     */
    public void creaNuevoObjeto(float x, float y, double metros) {
        if (metros % 100 == 0 && (x > 0 && y > 0)) {
            miJuego.fondos.metros++;
            Objeto nuevoObjeto = new Objeto(GameConstants.anchoPantalla, GameConstants.altoPantalla, world, miJuego);
            miJuego.objetos.add(nuevoObjeto);
        }
    }
}
