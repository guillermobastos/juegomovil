package com.pruebas.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Locale;

public class MiJuego extends Game {
    public OrthographicCamera camera;
    SpriteBatch spriteBatch;

    // Personaje del videojuego
    Personaje personaje;
    Objeto objeto;

    // Colección de todos los proyectiles visibles
    public Array<Objeto> objetos;

    // Alto y ancho de la pantalla
    float altoPantalla, anchoPantalla;
    Box2DDebugRenderer b2;
    World world;

    // Para saber en que screen del juego se ha seleccionado
    static int gameState;

    // Clase fondos para aplicar dichas imágenes
    Fondos fondos;
    InputAdapter procesadorEntradaMenu;

    // Animaciones de Dia y de Noche
    ParallaxBackground animacionDia, animacionNoche;

    // Colección de imágenes para el fondo: fondo de dia y de noche
    Array<Texture> textures1, textures2;

    // activar o desactiva propiedades y el fondo de dia o de noche
    boolean isDia, idioma_es, isTutorial, sonido;

    // Colección de los records
    public static Array<Double> records = new Array<>();
    Viewport viewport;
    private Stage stage;

    // Uso para la traducción entre inglés y español
    public I18NBundle myBundle;
    public Locale locale;
    public FileHandle baseFileHandle;

    // Sonidos para la muerte del personaje y al hacer click en cualquier botón
    private Sound muerte, click;

    // Música de fondo
    private Music musica;


    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        // Dimensiones
        altoPantalla = Gdx.graphics.getHeight();
        anchoPantalla = Gdx.graphics.getWidth();
        fondos = new Fondos(spriteBatch, this);
        musica = Gdx.audio.newMusic(Gdx.files.internal("musica_fondo.mp3"));
        click = Gdx.audio.newSound(Gdx.files.internal("click.mp3"));
        muerte = Gdx.audio.newSound(Gdx.files.internal("sonido_muerte.mp3"));
        stage = new Stage(new ScreenViewport());
        camera = (OrthographicCamera) stage.getViewport().getCamera();
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        viewport = new StretchViewport(10.8f, 7.2f, camera);
        world = new World(new Vector2(0, -50), true);

        gameState = GameConstants.screenMapa;
        isDia = sonido = idioma_es = true;
        isTutorial = false;
        personaje = new Personaje(200, 200, world);
        objeto = new Objeto(anchoPantalla, altoPantalla, world, this);
        objetos = new Array<>();
        objetos.add(objeto);

        fondos.metros = 0;
        baseFileHandle = Gdx.files.internal("i18n/MyBundle");
        locale = idioma_es ? new Locale("es") : new Locale("en");
        myBundle = I18NBundle.createBundle(baseFileHandle, locale);

        // Fondo del screenPlay del dia soleado
        textures1 = new Array<Texture>();
        for (int i = 1; i < 2; i++) {
            textures1.add(new Texture(Gdx.files.internal("background_cielo_" + i + ".png")));
            textures1.get(textures1.size - 1).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        }
        // Fondo del screenPlay de noche
        textures2 = new Array<Texture>();
        for (int i = 1; i < 2; i++) {
            textures2.add(new Texture(Gdx.files.internal("background_noche_" + i + ".png")));
            textures2.get(textures2.size - 1).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        }
        animacionDia = new ParallaxBackground(textures1);
        animacionDia.setSize(anchoPantalla, altoPantalla);
        animacionDia.setSpeed(1);
        animacionNoche = new ParallaxBackground(textures2);
        animacionNoche.setSize(anchoPantalla, altoPantalla);
        animacionNoche.setSpeed(1);
        animacionNoche.setVisible(false);
        stage.addActor(animacionDia);
        stage.addActor(animacionNoche);

        /**
         * Procesa las pulsaciones en el juego
         */
        procesadorEntradaMenu = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                return true;
            }

            @Override
            public boolean keyUp(int keycode) {
                return true;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                screenY = Gdx.graphics.getHeight() - screenY;

                // Menu
                if (gameState == GameConstants.screenMenu) {
                    if (fondos.rJugar.contains(screenX, screenY)) {
                        click.play();
                        gameState = GameConstants.screenPlay;
                    }
                    if (fondos.rPersonaje.contains(screenX, screenY)) {
                        click.play();
                        gameState = GameConstants.screenPersonaje;
                    }
                    if (fondos.rConfig.contains(screenX, screenY)) {
                        click.play();
                        gameState = GameConstants.screenConfig;
                    }
                    if (fondos.rMapa.contains(screenX, screenY)) {
                        click.play();
                        gameState = GameConstants.screenMapa;
                    }
                    if (fondos.rRecord.contains(screenX, screenY)) {
                        click.play();
                        gameState = GameConstants.screenRecord;
                    }

                    // Mapa
                } else if (gameState == GameConstants.screenMapa) {
                    click.play();
                    if (screenX < anchoPantalla / 2) {
                        fondos.isDia = true;
                        animacionNoche.setVisible(false);
                        animacionDia.setVisible(true);
                    } else {
                        fondos.isDia = false;
                        animacionNoche.setVisible(true);
                        animacionDia.setVisible(false);
                    }
                    gameState = GameConstants.screenMenu;

                    // Personaje
                } else if (gameState == GameConstants.screenPersonaje) {
                    click.play();
                    if (screenX < anchoPantalla / 2) {
                        personaje.animation = personaje.animationNaranja;
                        personaje.isNaranja = true;
                    } else {
                        personaje.animation = personaje.animationAzul;
                        personaje.isNaranja = false;
                    }
                    gameState = GameConstants.screenMenu;

                    // Juego
                } else if (gameState == GameConstants.screenPlay) {
                    if (sonido) {
                        musica.play();
                        musica.setLooping(true);
                    }
                    if (isTutorial && fondos.rVolver.contains(screenX, screenY)) {
                        gameState = GameConstants.screenMenu;
                        isTutorial = false;
                    }
                    if (personaje.death) {
                        musica.stop();
                        if (fondos.rVolverJugar.contains(screenX, screenY)) {
                            click.play();
                            personaje.death = false;
                            personaje.body.setTransform(200, 200, 0);
                            objeto = new Objeto(anchoPantalla, altoPantalla, world, fondos.miJuego);
                            objetos.add(objeto);
                            actualizaAnimacion();
                        }
                        if (fondos.rSalir.contains(screenX, screenY)) {
                            click.play();
                            personaje.death = false;
                            personaje.body.setTransform(200, 200, 0);
                            objeto = new Objeto(anchoPantalla, altoPantalla, world, fondos.miJuego);
                            objetos.add(objeto);
                            records.add(fondos.metros);
                            gameState = GameConstants.screenMenu;
                            fondos.font.setColor(Color.WHITE);
                            actualizaAnimacion();
                        }
                        fondos.metros = 0;
                    }

                    // Record
                } else if (gameState == GameConstants.screenRecord) {
                    if (fondos.rVolver.contains(screenX, screenY)) {
                        click.play();
                        gameState = GameConstants.screenMenu;
                    }

                    // Config
                } else if (gameState == GameConstants.screenConfig) {
                    if (fondos.rIdioma.contains(screenX, screenY)) {
                        click.play();
                        idioma_es = !idioma_es;
                        locale = idioma_es ? new Locale("es") : new Locale("en");
                        myBundle = I18NBundle.createBundle(baseFileHandle, locale);
                    }
                    if (fondos.rSonido.contains(screenX, screenY)) {
                        click.play();
                        musica.stop();
                        sonido = !sonido;
                    }
                    if (fondos.rVolver.contains(screenX, screenY)) {
                        click.play();
                        gameState = GameConstants.screenMenu;
                    }

                    // Tutorial
                } else if (gameState == GameConstants.screenTurorial) {
                    if (fondos.rVolver.contains(screenX, screenY)) {
                        gameState = GameConstants.screenMenu;
                    }

                    // Créditos
                } else if (gameState == GameConstants.screenCreditos) {
                    if (fondos.rVolver.contains(screenX, screenY)) {
                        gameState = GameConstants.screenMenu;
                    }
                }
                return true;
            }
        };

        Gdx.input.setInputProcessor(procesadorEntradaMenu);
        b2 = new Box2DDebugRenderer();
//        b2.setDrawBodies(false);  // IMportante
        world.setContactListener(new ContactListener() {

            /**
             * detecta las colisiones
             * @param contact cuerpo
             */
            @Override
            public void beginContact(Contact contact) {
                Fixture fa = contact.getFixtureA();
                Fixture fb = contact.getFixtureB();

                if(fa.getBody().getUserData().equals("player") ||
                        fb.getBody().getUserData().equals("player")) {
                    if (sonido) {
                        muerte.play();
                    }
                    Gdx.input.vibrate(800);
                    hit();
                }
            }

            @Override
            public void endContact(Contact contact) {
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }
        });
    }

    /**
     * Actualiza la pantalla y los recursos de la misma, cambiando entre las distintas screens
     */
    @Override
    public void render() {
        switch (gameState) {
            // Pantalla de Juego
            case GameConstants.screenPlay:
                world.step(GameConstants.TIMESTEP, GameConstants.VELOCITY_ITERATIONS, GameConstants.POSITION_ITERATIONS);
                spriteBatch.begin();
                Jugar();
                spriteBatch.end();
                b2.render(world, camera.combined);
                break;

            // Pantalla del Menú
            case GameConstants.screenMenu:
                spriteBatch.begin();
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                fondos.dibujarFondoMenu();
                stage.act();
                stage.draw();
                spriteBatch.end();
                break;

            // Pantalla de Configuración
            case GameConstants.screenConfig:
                spriteBatch.begin();
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                fondos.dibujarFondoConfig();
                spriteBatch.end();
                break;

            // Pantalla de Records
            case GameConstants.screenRecord:
                spriteBatch.begin();
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                fondos.dibujarFondoRecord(records);
                spriteBatch.end();
                break;

            // Pantalla para escoger el fondo del Mapa
            case GameConstants.screenMapa:
                spriteBatch.begin();
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                fondos.dibujarFondoMapa();
                spriteBatch.end();
                break;

            // Pantalla para escoger el personaje
            case GameConstants.screenPersonaje:
                spriteBatch.begin();
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                fondos.dibujarFondoPersonaje();
                spriteBatch.end();
                break;

            // Pantalla Tutorial
            case GameConstants.screenTurorial:
                spriteBatch.begin();
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                Jugar();
                spriteBatch.end();
                break;
        }
    }


    /**
     * Pantalla de juego en la que se pintan los proyectiles, el personaje, el fondo
     * además de procesar todas las variables en caso de que el personaje muera
     */
    public void Jugar() {
        fueraLimites();
        fondos.font.getData().setScale(1);
        fondos.font.setColor(Color.WHITE);
        stage.act();
        stage.draw();
        fondos.dibujarSolOLuna();
        fondos.dibujarMetros();
        personaje.update();
        personaje.render(spriteBatch);
        if (!personaje.death) {
            fondos.metros++;
            if (isTutorial) {
                fondos.dibujarTutorial();
            }
            procesarEntrada();
            for (int i = 0; i < objetos.size; i++) {
                objetos.get(i).update(-anchoPantalla / altoPantalla, -altoPantalla / altoPantalla, fondos.metros);
                objetos.get(i).render(spriteBatch);
                if(objetos.get(i).body.getPosition().x < 0 || objetos.get(i).body.getPosition().y < 0) {
                    objetos.removeIndex(i);
                }
            }

        } else {
            musica.stop();
            for (int i = 0; i < objetos.size; i++) {
                world.destroyBody(objetos.get(i).body);
            }
            objetos.clear();
            fondos.font.setColor(Color.RED);
            fondos.dibujarMetros();
            fondos.dibujarFondoMuerte();
        }
    }

    /**
     * Gestiona el movimiento del personaje
     */
    public void procesarEntrada() {
        if (Gdx.input.isTouched() && !personaje.death) {
            if (Gdx.input.getX() < anchoPantalla / 2) {
                personaje.move(-5, 3);
            } else {
                personaje.move(5, 3);
            }
        }
    }

    /**
     * Reinicia la animación del personaje una vez muerto
     */
    public void actualizaAnimacion() {
        if (personaje.animation == personaje.muerteNaranja) {
            personaje.animation = personaje.animationNaranja;
        } else {
            personaje.animation = personaje.animationAzul;
        }
    }

    /**
     * Gestiona las acciones a realizar una vez se colisiona con los objetos o con los bordes de la pantalla
     */
    public void hit() {
        personaje.death = true;
        personaje.body.setTransform(objeto.body.getLinearVelocity().x - 5, objeto.body.getLinearVelocity().y - 50, 0);
    }

    /**
     * Colision con los bordes de la pantalla
     */
    public void fueraLimites() {
        if (personaje.body.getPosition().x < 0 || personaje.body.getPosition().x > anchoPantalla
                || personaje.body.getPosition().y < 0 || personaje.body.getPosition().y > altoPantalla) {
            hit();
        }
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        stage.dispose();
        fondos.font.dispose();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }
}
