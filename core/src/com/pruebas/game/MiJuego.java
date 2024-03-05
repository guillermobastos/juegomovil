package com.pruebas.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
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

    /**
     * clase que representa una cámara
     * con proyección ortográfica en un entorno tridimensional
     */
    public OrthographicCamera camera;

    /**
     * Clase en LibGDX que se utiliza para renderizar gráficos en 2D
     */
    SpriteBatch spriteBatch;

    /**
     * Personaje del videojuego
     */
    Personaje personaje;

    /**
     * Proyectil
     */
    Objeto objeto;

    // Colección de todos los proyectiles visibles
    public Array<Objeto> objetos;

    // Alto y ancho de la pantalla
    float altoPantalla, anchoPantalla;

    /**
     * Clase en LibGDX que se utiliza
     * para renderizar visualmente el mundo físico creado con Box2D
     */
    Box2DDebugRenderer b2;

    /**
     * Se utiliza para crear y simular el mundo físico en un juego 2D.
     */
    World world;

    // Para saber en que screen del juego se ha seleccionado
    static int gameState;

    /**
     * Nivel del juego
     */
    public int nivel;

    /**
     * Vidas del personaje
     */
    public int vidas;

    /**
     * Clase fondos para aplicar dichas imágenes
     */
    Fondos fondos;

    /**
     * clase en LibGDX que proporciona una forma conveniente
     * de manejar eventos de entrada en un juego o aplicación
     */
    InputAdapter procesadorEntradaMenu;

    /**
     * Animaciones de Dia y de Noche
     */
    ParallaxBackground animacionDia, animacionNoche;

    /**
     * Colección de imágenes para el fondo: fondo de dia y de noche
     */
    Array<Texture> textures1, textures2;

    /**
     * Activa o desactiva propiedades y el fondo de dia o de noche
     */
    boolean isDia, idioma_es, isTutorial, isSonido, isVibracion;

    /**
     * Colección de los records
     */
    public static Array<Double> records = new Array<>();

    /**
     * Clase que define una región visible del mundo del juego y
     * cómo se mapea esta región al área de la pantalla
     */
    Viewport viewport;

    /**
     * Clase que representa un contenedor para actores y
     * maneja la renderización y el manejo de eventos
     * de estos actores en una escena
     */
    private Stage stage;

    /**
     * Uso para la traducción entre inglés y español
     */
    public I18NBundle myBundle;
    public Locale locale;
    public FileHandle baseFileHandle;

    /**
     * Sonidos para la muerte del personaje y
     * al hacer click en cualquier botón
     */
    private Sound muerte, click;

    /**
     * Música de fondo
     */
    private Music musica;

    /**
     * Persistencia de records
     */
    private Preferences prefRecords;

    /**
     * Persistencia de idioma
     */
    private Preferences prefIdioma;


    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        // Dimensiones
        altoPantalla = Gdx.graphics.getHeight();
        anchoPantalla = Gdx.graphics.getWidth();
        fondos = new Fondos(spriteBatch, this);
        musica = Gdx.audio.newMusic(Gdx.files.internal("sonidos/musica_fondo.mp3"));
        click = Gdx.audio.newSound(Gdx.files.internal("sonidos/click.mp3"));
        muerte = Gdx.audio.newSound(Gdx.files.internal("sonidos/sonido_muerte.mp3"));
        stage = new Stage(new ScreenViewport());
        camera = (OrthographicCamera) stage.getViewport().getCamera();
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        viewport = new StretchViewport(10.8f, 7.2f, camera);
        world = new World(new Vector2(0, -50), true);

        gameState = GameConstants.SCREEN_MENU;
        isDia = isSonido = idioma_es = isVibracion = true;
        isTutorial = false;
        personaje = new Personaje(200, 200, world);
        objeto = new Objeto(anchoPantalla, altoPantalla, world, this);
        objetos = new Array<>();
        objetos.add(objeto);

        fondos.metros = 0;
        nivel = 1;
        vidas = 3;

        // Persistencia de los records
        prefRecords = Gdx.app.getPreferences("records");
        if (prefRecords.get().size() > 0) {
            for (int i = 0; i < prefRecords.get().size(); i++) {
                records.add((double) prefRecords.getFloat("records" + i));
            }
        }
        // Persistencia del idioma
        prefIdioma = Gdx.app.getPreferences("idioma");
        if (prefIdioma.get().size() > 0) {
            idioma_es = prefIdioma.getBoolean("idioma");
        }
        baseFileHandle = Gdx.files.internal("i18n/MyBundle");
        locale = idioma_es ? new Locale("es") : new Locale("en");
        myBundle = I18NBundle.createBundle(baseFileHandle, locale);

        // Fondo del screenPlay del dia soleado
        textures1 = new Array<Texture>();
        for (int i = 1; i < 2; i++) {
            textures1.add(new Texture(Gdx.files.internal("fondos/background_cielo_" + i + ".png")));
            textures1.get(textures1.size - 1).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        }
        // Fondo del screenPlay de noche
        textures2 = new Array<Texture>();
        for (int i = 1; i < 2; i++) {
            textures2.add(new Texture(Gdx.files.internal("fondos/background_noche_" + i + ".png")));
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
                if (gameState == GameConstants.SCREEN_MENU) {
                    if (fondos.rJugar.contains(screenX, screenY)) {
                        Click();
                        gameState = GameConstants.SCREEN_PLAY;
                    }
                    if (fondos.rPersonaje.contains(screenX, screenY)) {
                        Click();
                        gameState = GameConstants.SCREEN_PERSONAJE;
                    }
                    if (fondos.rConfig.contains(screenX, screenY)) {
                        Click();
                        gameState = GameConstants.SCREEN_CONFIG;
                    }
                    if (fondos.rMapa.contains(screenX, screenY)) {
                        Click();
                        gameState = GameConstants.SCREEN_MAPA;
                    }
                    if (fondos.rRecord.contains(screenX, screenY)) {
                        Click();
                        gameState = GameConstants.SCREEN_RECORD;
                    }
                    if (fondos.rTutorial.contains(screenX, screenY)) {
                        Click();
                        isTutorial = true;
                        gameState = GameConstants.SCREEN_TUTORIAL;
                    }
                    if (fondos.rCreditos.contains(screenX, screenY)) {
                        Click();
                        gameState = GameConstants.SCREEN_CREDITOS;
                    }

                    // Mapa
                } else if (gameState == GameConstants.SCREEN_MAPA) {
                    Click();
                    if (screenX < anchoPantalla / 2) {
                        fondos.isDia = true;
                        animacionNoche.setVisible(false);
                        animacionDia.setVisible(true);
                    } else {
                        fondos.isDia = false;
                        animacionNoche.setVisible(true);
                        animacionDia.setVisible(false);
                    }
                    gameState = GameConstants.SCREEN_MENU;

                    // Personaje
                } else if (gameState == GameConstants.SCREEN_PERSONAJE) {
                    Click();
                    if (screenX < anchoPantalla / 2) {
                        personaje.animation = personaje.animationNaranja;
                        personaje.isNaranja = true;
                    } else {
                        personaje.animation = personaje.animationAzul;
                        personaje.isNaranja = false;
                    }
                    gameState = GameConstants.SCREEN_MENU;

                    // Juego
                } else if (gameState == GameConstants.SCREEN_PLAY || gameState == GameConstants.SCREEN_TUTORIAL) {
                    if (isSonido) {
                        musica.play();
                        musica.setLooping(true);
                    }
                    if (personaje.death) {
                        musica.stop();
                        if (fondos.rVolverJugar.contains(screenX, screenY)) {
                            Click();
                            personaje.death = false;
                            vidas = 3;
                            personaje.body.setTransform(anchoPantalla / 5, altoPantalla / 5, 0);
                            objeto = new Objeto(anchoPantalla, altoPantalla, world, fondos.miJuego);
                            objetos.add(objeto);
                            actualizaAnimacion();
                        }
                        if (fondos.rSalir.contains(screenX, screenY)) {
                            Click();
                            if (records.size > 0) {
                                for (int i = 0; i < records.size && i < 10; i++) {
                                    prefRecords.putFloat("record" + i, Float.parseFloat(records.get(i).toString()));
                                }
                                prefRecords.flush();
                            }
                            personaje.death = isTutorial = false;
                            vidas = 3;
                            personaje.body.setTransform(anchoPantalla / 5, altoPantalla / 5, 0);
                            objeto = new Objeto(anchoPantalla, altoPantalla, world, fondos.miJuego);
                            objetos.add(objeto);
                            records.add(fondos.metros);
                            gameState = GameConstants.SCREEN_MENU;
                            fondos.font.setColor(Color.WHITE);
                            actualizaAnimacion();
                        }
                        fondos.metros = 0;
                    }

                    // Record
                } else if (gameState == GameConstants.SCREEN_RECORD) {
                    ClickVolver(screenX, screenY);

                    // Config
                } else if (gameState == GameConstants.SCREEN_CONFIG) {
                    if (fondos.rIdioma.contains(screenX, screenY)) {
                        Click();
                        idioma_es = !idioma_es;
                        locale = idioma_es ? new Locale("es") : new Locale("en");
                        myBundle = I18NBundle.createBundle(baseFileHandle, locale);
                        prefIdioma.putBoolean("idioma", idioma_es);
                        prefIdioma.flush();
                    }
                    if (fondos.rSonido.contains(screenX, screenY)) {
                        Click();
                        musica.stop();
                        isSonido = !isSonido;
                    }
                    if (fondos.rVibracion.contains(screenX, screenY)) {
                        Click();
                        isVibracion = !isVibracion;
                    }
                    ClickVolver(screenX, screenY);

                    // Tutorial
                } else if (gameState == GameConstants.SCREEN_CREDITOS) {
                    ClickVolver(screenX, screenY);
                }
                return true;
            }
        };
        Gdx.input.setInputProcessor(procesadorEntradaMenu);


        b2 = new Box2DDebugRenderer();
//        b2.setDrawBodies(false);
        world.setContactListener(new ContactListener() {

            /**
             * detecta las colisiones
             * @param contact cuerpo
             */
            @Override
            public void beginContact(Contact contact) {
                Fixture fa = contact.getFixtureA();
                Fixture fb = contact.getFixtureB();
                if (fa.getBody().getUserData().equals("player") ||
                        fb.getBody().getUserData().equals("player")) {
                    if (isSonido) {
                        muerte.play();
                    }
                    if (isVibracion) {
                        Gdx.input.vibrate(800);
                    }
                    if (vidas <= 0) {
                        hit();
                    } else {
                        personaje.body.setTransform(new Vector2(anchoPantalla / 5, altoPantalla / 5), 0);
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {
                Fixture fa = contact.getFixtureA();
                Fixture fb = contact.getFixtureB();
                Gdx.app.log("Vidas", vidas + "");
                if (fa.getBody().getUserData().equals("player") ||
                        fb.getBody().getUserData().equals("player")) {
                    vidas--;
                }
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
            case GameConstants.SCREEN_PLAY:
                world.step(GameConstants.TIMESTEP, GameConstants.VELOCITY_ITERATIONS, GameConstants.POSITION_ITERATIONS);
                spriteBatch.begin();
                Jugar();
                spriteBatch.end();
                b2.render(world, camera.combined);
                break;

            // Pantalla del Menú
            case GameConstants.SCREEN_MENU:
                spriteBatch.begin();
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                stage.act();
                stage.draw();
                fondos.dibujarFondoMenu();
                fondos.dibujarSolOLuna();
                spriteBatch.end();
                break;

            // Pantalla de Configuración
            case GameConstants.SCREEN_CONFIG:
                spriteBatch.begin();
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                fondos.dibujarFondoConfig();
                spriteBatch.end();
                break;

            // Pantalla de Records
            case GameConstants.SCREEN_RECORD:
                spriteBatch.begin();
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                fondos.dibujarFondoRecord(records);
                spriteBatch.end();
                break;

            // Pantalla para escoger el fondo del Mapa
            case GameConstants.SCREEN_MAPA:
                spriteBatch.begin();
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                fondos.dibujarFondoMapa();
                spriteBatch.end();
                break;

            // Pantalla para escoger el personaje
            case GameConstants.SCREEN_PERSONAJE:
                spriteBatch.begin();
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                fondos.dibujarFondoPersonaje();
                spriteBatch.end();
                break;

            // Pantalla Tutorial
            case GameConstants.SCREEN_TUTORIAL:
                world.step(GameConstants.TIMESTEP, GameConstants.VELOCITY_ITERATIONS, GameConstants.POSITION_ITERATIONS);
                spriteBatch.begin();
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                Jugar();
                spriteBatch.end();
                b2.render(world, camera.combined);
                break;
            case GameConstants.SCREEN_CREDITOS:
                spriteBatch.begin();
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                fondos.dibujarFondoCreditos();
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
        fondos.dibujarVidas();
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
                if (objetos.get(i).body.getPosition().x < 0 || objetos.get(i).body.getPosition().y < 0) {
                    world.destroyBody(objetos.get(i).body);
                    objetos.removeIndex(i);
                    Gdx.app.log("Objetos", objetos.size + "");
                }
            }
            actualizacionNivel();

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

    /**
     * Actualiza el nivel del juego según los metros avanzados
     */
    public void actualizacionNivel() {
        if (fondos.metros < 1000) {
            nivel = 1;
        } else if (fondos.metros < 2000) {
            nivel = 2;
        } else if (fondos.metros < 4000) {
            nivel = 3;
        } else if (fondos.metros < 6000) {
            nivel = 4;
        } else if (fondos.metros < 8500) {
            nivel = 5;
        } else {
            nivel = 6;
        }
    }

    /**
     * Hace el sonido de clickar si en la configuración está activado
     */
    public void Click() {
        if (isSonido) {
            click.play();
        }
    }


    /**
     * Vuelve al menu principal una vez se clique en el botón de volver
     *
     * @param screenX posición x donde se pulsa
     * @param screenY posición y donde se pulsa
     */
    public void ClickVolver(float screenX, float screenY) {
        if (fondos.rVolver.contains(screenX, screenY)) {
            Click();
            gameState = GameConstants.SCREEN_MENU;
        }
    }

    /**
     * Cuando la aplicación se cierra
     */
    @Override
    public void dispose() {
        spriteBatch.dispose();
        stage.dispose();
        fondos.font.dispose();
    }

    /**
     * Cambia las variables al cambiar de tamaño la pantalla
     *
     * @param width  the new width in pixels
     * @param height the new height in pixels
     */
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        altoPantalla = height;
        anchoPantalla = width;
        fondos.anchoPantalla = GameConstants.ANCHO_PANTALLA;
        fondos.altoPantalla = GameConstants.ALTO_PANTALLA;
        fondos.width = anchoPantalla / 7;
        fondos.height = altoPantalla / 10;
        fondos.espacio_entre_botones = anchoPantalla / 15;
    }
}
