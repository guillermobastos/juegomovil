package com.pruebas.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import java.util.Locale;

public class Fondos {
    TextureAtlas textureAtlas;
    Sprite botonJugar, botonConfig, botonPersonaje, botonMapa, botonRecord, botonVolverJugar, botonSalir, botonEsp, botonEng, botonSonido, botonSinSonido;
    Rectangle rJugar, rConfig, rRecord, rPersonaje, rMapa, rVolverJugar, rSalir, rVolver, rIdioma, rSonido;
    Batch spriteBatch;
    boolean isDia;
    public double metros;
    BitmapFont font;
    Texture fondoDia, fondoNoche, sol, luna, personaje_skin_naranja, personaje_skin_azul, volver;
    public float anchoPantalla;
    public float altoPantalla;
    MiJuego miJuego;

    /**
     * Inicializa todas las variables para poder usarlas en los fondos
     *
     * @param spriteBatch lienzo donde se va a dibujar
     */
    public Fondos(Batch spriteBatch, MiJuego miJuego) {
        font = new BitmapFont(Gdx.files.internal("fast99.fnt"));
        font.getData().setScale(4);
        this.miJuego = miJuego;
        anchoPantalla = GameConstants.anchoPantalla;
        altoPantalla = GameConstants.altoPantalla;
        fondoDia = new Texture(Gdx.files.internal("background_cielo_0.png"));
        fondoNoche = new Texture(Gdx.files.internal("background_noche_0.png"));
        sol = new Texture(Gdx.files.internal("sol.png"));
        luna = new Texture(Gdx.files.internal("luna.png"));
        personaje_skin_naranja = new Texture(Gdx.files.internal("personaje_naranja_0.png"));
        personaje_skin_azul = new Texture(Gdx.files.internal("personaje_azul_0.png"));
        isDia = true;
        miJuego.locale = miJuego.idioma_es ? new Locale("es") : new Locale("en");
        this.spriteBatch = spriteBatch;
    }

    /**
     * Dibuja el Menú Principal con todos los casillas posibles que pueda elegir el jugador
     * Jugar Partida // Configuración // Records // Personaje // Mapa
     */
    public void dibujarFondoMenu() {
        dibujarFondo();
        textureAtlas = miJuego.idioma_es ? new TextureAtlas("botones_es.txt") : new TextureAtlas("botones_en.txt");
        botonJugar = textureAtlas.createSprite("button_jugar_partida");
        botonJugar.setSize(250, 90);
        botonJugar.setPosition(anchoPantalla - botonJugar.getWidth() - 30, 30);

        rJugar = new Rectangle(anchoPantalla - botonJugar.getWidth() - 30, 30, 250, 90);
        botonJugar.draw(spriteBatch);

        botonConfig = textureAtlas.createSprite("button_configuracion");
        botonConfig.setSize(250, 90);
        botonConfig.setPosition(30, 30);
        rConfig = new Rectangle(30, 30, 250, 90);
        botonConfig.draw(spriteBatch);

        botonRecord = textureAtlas.createSprite("button_records");
        botonRecord.setSize(250, 90);
        botonRecord.setPosition(30, botonConfig.getY() + botonConfig.getHeight() + 10);
        rRecord = new Rectangle(30, botonConfig.getY() + botonConfig.getHeight() + 10, 250, 90);
        botonRecord.draw(spriteBatch);

        botonPersonaje = textureAtlas.createSprite("button_personaje");
        botonPersonaje.setSize(250, 90);
        botonPersonaje.setPosition(anchoPantalla - botonPersonaje.getWidth() - 30, botonJugar.getY() + botonJugar.getHeight() + 10);
        rPersonaje = new Rectangle(anchoPantalla - botonJugar.getWidth() - 30, botonJugar.getY() + botonJugar.getHeight() + 10, 250, 90);
        botonPersonaje.draw(spriteBatch);

        botonMapa = textureAtlas.createSprite("button_mapa");
        botonMapa.setSize(250, 90);
        botonMapa.setPosition(anchoPantalla - botonMapa.getWidth() - 30, botonPersonaje.getY() + botonPersonaje.getHeight() + 10);
        rMapa = new Rectangle(anchoPantalla - botonJugar.getWidth() - 30, botonPersonaje.getY() + botonPersonaje.getHeight() + 10, 250, 90);
        botonMapa.draw(spriteBatch);
    }

    /**
     * dibuja el fondo de Configuración
     */
    public void dibujarFondoConfig() {
        dibujarFondo();
        textureAtlas = new TextureAtlas("botones_sonido.txt");
        if (miJuego.sonido) {
            botonSonido = textureAtlas.createSprite("sonido_on");
            botonSonido.setSize(250, 150);
            botonSonido.setPosition(anchoPantalla / 3, altoPantalla / 2);
            botonSonido.draw(spriteBatch);
        } else {
            botonSinSonido = textureAtlas.createSprite("sonido_off");
            botonSinSonido.setSize(250, 150);
            botonSinSonido.setPosition(anchoPantalla / 3, altoPantalla / 2);
            botonSinSonido.draw(spriteBatch);
        }
        rSonido = new Rectangle(anchoPantalla / 3, altoPantalla / 2, 250, 150);
        textureAtlas = new TextureAtlas("botones_idioma.txt");
        if (miJuego.idioma_es) {
            botonEsp = textureAtlas.createSprite("idioma_es");
            botonEsp.setSize(250, 150);
            botonEsp.setPosition(anchoPantalla / 2 + 150, altoPantalla / 2);
            botonEsp.draw(spriteBatch);

        } else {
            botonEng = textureAtlas.createSprite("idioma_en");
            botonEng.setSize(250, 150);
            botonEng.setPosition(anchoPantalla / 2 + 150, altoPantalla / 2);
            botonEng.draw(spriteBatch);
        }
        rIdioma = new Rectangle(anchoPantalla / 2 + 150, altoPantalla / 2, 250, 150);
        dibujarBotonVolver();
    }

    public void dibujarTutorial() {
        font.getData().setScale(1.50F);
        font.draw(spriteBatch, miJuego.myBundle.get("tutorial_btn_derecho"), 200, altoPantalla - 200, anchoPantalla/5, 50, true);
        font.draw(spriteBatch, miJuego.myBundle.get("tutorial_btn_izquierdo"), anchoPantalla * 3 / 4, altoPantalla - 200, anchoPantalla/5, 50, true);
        if (metros < 200) {
            font.draw(spriteBatch, miJuego.myBundle.get("tutorial_esquiva"), anchoPantalla *5/ 12 , altoPantalla - 100, anchoPantalla/3, 10, true);
        } else if (metros < 700) {
            font.draw(spriteBatch, miJuego.myBundle.get("tutorial_sobrevive"), anchoPantalla *5/ 12, altoPantalla - 100, anchoPantalla/3, 50, true);
        } else {
            font.getData().setScale(2.50F);
            font.draw(spriteBatch, miJuego.myBundle.get("tutorial_completado"), anchoPantalla / 2, altoPantalla - 100, anchoPantalla/3, 100, false);
            font.getData().setScale(1);
        }
        dibujarBotonVolver();
    }


    /**
     * Dibuja el fondo de los records
     *
     * @param records
     */
    public void dibujarFondoRecord(Array<Double> records) {
        dibujarFondo();
        dibujarRecords(records);
        dibujarBotonVolver();
    }

    public void dibujarFondoCreditos(Array<Double> records) {
        dibujarFondo();
        font.draw(spriteBatch, String.format(miJuego.myBundle.get("creditos")), anchoPantalla / 2 - 100, altoPantalla - 60);
        for (int i = 0; i < GameConstants.creditosNombres.length; i++) {
            font.draw(spriteBatch, String.format("%2d- %8.1fm", records.get(i)), anchoPantalla / 2 - 100, altoPantalla - (i + 1) * 60);
            i--;
        }
        dibujarBotonVolver();
    }
    

    /**
     * Dibuja en fondo de Dia y el Sol en la mitad izquierda y el fondo de Noche y la Luna
     * en la mitad derecha
     */
    public void dibujarFondoMapa() {
        spriteBatch.draw(fondoDia, 0, 0, anchoPantalla / 2, altoPantalla);
        spriteBatch.draw(sol, -sol.getWidth() / 3, altoPantalla - sol.getHeight() / 2);
        font.draw(spriteBatch, miJuego.myBundle.get("dia"), anchoPantalla / 5, altoPantalla * 3 / 4);
        spriteBatch.draw(fondoNoche, anchoPantalla / 2, 0, anchoPantalla / 2, altoPantalla);
        spriteBatch.draw(luna, anchoPantalla - luna.getWidth() * 2 / 3, altoPantalla - luna.getHeight() / 2);
        font.draw(spriteBatch, miJuego.myBundle.get("noche"), anchoPantalla * 3 / 5, altoPantalla * 3 / 4);
    }

    /**
     * Dibuja el Fondo para poder escoger entre los dos distintos personajes
     */
    public void dibujarFondoPersonaje() {
        dibujarFondo();
        spriteBatch.draw(personaje_skin_naranja, anchoPantalla / 7, altoPantalla / 3,
                anchoPantalla / 3, altoPantalla / 3);
        spriteBatch.draw(personaje_skin_azul, anchoPantalla * 6 / 10, altoPantalla / 3,
                anchoPantalla / 3, altoPantalla / 3);
        dibujarSolOLuna();
    }

    /**
     * Dibujar tantos los botones de Volver a Jugar como el de Salir además del mensaje
     * en rojo de que ha muerto
     */
    public void dibujarFondoMuerte() {
        textureAtlas = new TextureAtlas("botones_muerte.txt");
        botonVolverJugar = textureAtlas.createSprite("button_volver_jugar");
        botonVolverJugar.setSize(250, 90);
        botonVolverJugar.setPosition(anchoPantalla / 2 - botonJugar.getWidth() - 30, altoPantalla / 2);
        botonVolverJugar.draw(spriteBatch);
        rVolverJugar = new Rectangle(anchoPantalla / 2 - botonJugar.getWidth() - 30, altoPantalla / 2, 250, 90);

        botonSalir = textureAtlas.createSprite("button_salir");
        botonSalir.setSize(250, 90);
        botonSalir.setPosition(anchoPantalla / 2 + botonJugar.getWidth() - 30, altoPantalla / 2);
        botonSalir.draw(spriteBatch);
        rSalir = new Rectangle(anchoPantalla / 2 + botonJugar.getWidth() - 30, altoPantalla / 2, 250, 90);

        font.setColor(Color.RED);
        font.getData().setScale(3);
        font.draw(spriteBatch, miJuego.myBundle.get("muerte"), anchoPantalla * 3 / 10, altoPantalla * 4 / 5);
    }

    /**
     * Dibujar el fondo de dia o de noche junto con el sol o luna dependiendo de la booleana isDia
     */
    public void dibujarFondo() {
        if (isDia) {
            spriteBatch.draw(fondoDia, 0, 0, anchoPantalla, altoPantalla);
        } else {
            spriteBatch.draw(fondoNoche, 0, 0, anchoPantalla, altoPantalla);
        }
        dibujarSolOLuna();
    }

    /**
     * Dibuja el Sol o la Luna en la esquina superior izquierda dependiendo del valor de la
     * booleana isDia
     */
    public void dibujarSolOLuna() {
        if (isDia) {
            spriteBatch.draw(sol, -sol.getWidth() / 3, altoPantalla - sol.getHeight() / 2);
        } else {
            spriteBatch.draw(luna, -luna.getWidth() / 3, altoPantalla - luna.getHeight() / 2);
        }
    }

    /**
     * Dibuja los metros recorridos por el personaje en el centro superior de la pantalla
     */
    public void dibujarMetros() {
        if (metros < 1000) {
            font.draw(spriteBatch, String.format("%.0fm", metros), anchoPantalla / 2 - 50, altoPantalla - 5);
        } else {
            font.draw(spriteBatch, String.format("%.2fkm", metros / 1000), anchoPantalla / 2 - 50, altoPantalla - 5);
        }
    }

    /**
     * Dibuja el botón de volver
     */
    public void dibujarBotonVolver() {
        volver = miJuego.idioma_es ? new Texture(Gdx.files.internal("button_volver_es.png")) : new Texture(Gdx.files.internal("button_volver_en.png"));
        spriteBatch.draw(volver, anchoPantalla - 300, 100, 250, 90);
        rVolver = new Rectangle(anchoPantalla - 300, 100, 250, 90);
    }

    /**
     * Dibuja los records desde la máxima puntuación hasta un máximo de 10 puntuaciones y
     * si no hay ninguno guardado
     *
     * @param records matriz de records
     */
    public void dibujarRecords(Array<Double> records) {
        font.getData().setScale(2);
        font.setColor(Color.WHITE);
        if (records.size > 0) {
            records.sort();
            int cont = 1;
            int i = records.size - 1;
            if (records.size < 10) {
                while (i >= 0) {
                    font.draw(spriteBatch, String.format("%2d- %8.1fm", cont, records.get(i)), anchoPantalla / 2 - 100, altoPantalla - (cont + 1) * 60);
                    i--;
                    cont++;
                }
            } else {
                while (i >= records.size - 10) {
                    font.draw(spriteBatch, String.format("%2d- %8.1fm", cont, records.get(i)), anchoPantalla / 2 - 100, altoPantalla - (cont + 1) * 60);
                    i--;
                    cont++;
                }
            }
        } else {
            font.getData().setScale(2);
            font.setColor(Color.RED);
            font.draw(spriteBatch, miJuego.myBundle.get("no_records"), anchoPantalla / 4, altoPantalla / 2, anchoPantalla * 3 / 5, 10, false);
        }
    }


}
