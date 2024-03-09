package com.pruebas.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import java.util.Locale;

public class Fondos {

    /**
     * TexturePack donde se guardan las imágenes de todos los botones
     */
    TextureAtlas textureAtlas;

    /**
     * Imágenes botones
     */
    Sprite botonJugar, botonConfig, botonPersonaje, botonMapa, botonRecord, botonTutorial, botonCreditos, botonVolverJugar, botonSalir,
            botonSonido, botonSinSonido, botonEsp, botonEng, botonVibracion, botonSinVibracion;

    /**
     * Bodies de los botones
     */
    Rectangle rJugar, rConfig, rRecord, rPersonaje, rMapa, rVolverJugar, rSalir, rVolver, rTutorial, rCreditos,
            rIdioma, rSonido, rVibracion;

    Batch spriteBatch;

    /**
     * Indica si es o no de dia
     */
    boolean isDia;

    /**
     * Metros recorridos por el personaje
     */
    public float metros;

    /**
     * Tamaño botones y espacio entre ellos
     */
    public float width, height, espacio_entre_botones;

    /**
     * Fuente usada
     */
    BitmapFont font;

    /**
     * Imágenes de los fondos, personajes y del boton volver
     */
    Texture fondoDia, fondoNoche, sol, luna, personaje_skin_naranja, personaje_skin_azul, volver, vidas;

    /**
     * Tamaño ancho de la pantalla
     */
    public float anchoPantalla;

    /**
     * Tamaño alto de la pantalla
     */
    public float altoPantalla;

    /**
     * Juego Principal
     */
    MiJuego miJuego;

    /**
     * Inicializa todas las variables para poder usarlas en los fondos
     *
     * @param spriteBatch lienzo donde se va a dibujar
     */

    /**
     * Para generar una fuente con el tamaño deseado
     */
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    /**
     * Tamaño de la fuente que deseas
     */
    float desiredFontSize;

    TextureRegion textureRegion;

    public Fondos(Batch spriteBatch, MiJuego miJuego) {
        desiredFontSize = Math.min(miJuego.anchoPantalla,miJuego.altoPantalla)/22;
        font = generateFont(Gdx.files.internal("fuente_01.ttf").path(), desiredFontSize);
        font.setColor(Color.WHITE);
        this.miJuego = miJuego;
        anchoPantalla = miJuego.anchoPantalla;
        altoPantalla = miJuego.altoPantalla;
        width = anchoPantalla / 7;
        height = altoPantalla / 10;
        espacio_entre_botones = anchoPantalla / 15;
        fondoDia = new Texture(Gdx.files.internal("fondos/background_cielo_0.png"));
        fondoNoche = new Texture(Gdx.files.internal("fondos/background_noche_0.png"));
        sol = new Texture(Gdx.files.internal("fondos/sol.png"));
        luna = new Texture(Gdx.files.internal("fondos/luna.png"));
        vidas = new Texture(Gdx.files.internal("fondos/corazon.png"));
        textureRegion = new TextureRegion(vidas);
        textureRegion.setRegionWidth(vidas.getWidth());
        textureRegion.setRegionHeight(vidas.getHeight());
        personaje_skin_naranja = new Texture(Gdx.files.internal("personajes/personaje_naranja_0.png"));
        personaje_skin_azul = new Texture(Gdx.files.internal("personajes/personaje_azul_0.png"));
        isDia = true;
        miJuego.locale = miJuego.idioma_es ? new Locale("es") : new Locale("en");
        this.spriteBatch = spriteBatch;
    }

    /**
     * Dibuja el Menú Principal con todos los casillas posibles que pueda elegir el jugador
     * Jugar Partida // Configuración // Records // Personaje // Mapa
     */
    public void dibujarFondoMenu() {
        textureAtlas = miJuego.idioma_es ? new TextureAtlas("botones/botones_es.txt") : new TextureAtlas("botones/botones_en.txt");
        // Btn Jugar
        botonJugar = textureAtlas.createSprite("button_jugar_partida");
        botonJugar.setSize(width, height);
        botonJugar.setPosition(anchoPantalla - botonJugar.getWidth() - espacio_entre_botones, espacio_entre_botones);
        rJugar = new Rectangle(anchoPantalla - botonJugar.getWidth() - espacio_entre_botones, espacio_entre_botones, width, height);
        botonJugar.draw(spriteBatch);

        // Btn Personaje
        botonPersonaje = textureAtlas.createSprite("button_personaje");
        botonPersonaje.setSize(width, height);
        botonPersonaje.setPosition(anchoPantalla - botonPersonaje.getWidth() - espacio_entre_botones, botonJugar.getY() + botonJugar.getHeight() + espacio_entre_botones / 2);
        rPersonaje = new Rectangle(anchoPantalla - botonJugar.getWidth() - espacio_entre_botones, botonJugar.getY() + botonJugar.getHeight() + espacio_entre_botones / 2, width, height);
        botonPersonaje.draw(spriteBatch);

        // Btn Mapa
        botonMapa = textureAtlas.createSprite("button_mapa");
        botonMapa.setSize(width, height);
        botonMapa.setPosition(anchoPantalla - botonMapa.getWidth() - espacio_entre_botones, botonPersonaje.getY() + botonPersonaje.getHeight() + espacio_entre_botones / 2);
        rMapa = new Rectangle(anchoPantalla - botonJugar.getWidth() - espacio_entre_botones, botonPersonaje.getY() + botonPersonaje.getHeight() + espacio_entre_botones / 2, width, height);
        botonMapa.draw(spriteBatch);

        // Btn Tutorial
        botonTutorial = textureAtlas.createSprite("button_tutorial");
        botonTutorial.setSize(width, height);
        botonTutorial.setPosition(anchoPantalla - botonJugar.getWidth() - botonTutorial.getWidth() - 1.5f * espacio_entre_botones, espacio_entre_botones);

        rTutorial = new Rectangle(anchoPantalla - botonJugar.getWidth() - botonTutorial.getWidth() - 1.5f * espacio_entre_botones, espacio_entre_botones, width, height);
        botonTutorial.draw(spriteBatch);

        // Btn Config
        botonConfig = textureAtlas.createSprite("button_configuracion");
        botonConfig.setSize(width, height);
        botonConfig.setPosition(espacio_entre_botones, espacio_entre_botones);
        rConfig = new Rectangle(espacio_entre_botones, espacio_entre_botones, width, height);
        botonConfig.draw(spriteBatch);

        // Btn Record
        botonRecord = textureAtlas.createSprite("button_records");
        botonRecord.setSize(width, height);
        botonRecord.setPosition(espacio_entre_botones, botonConfig.getY() + botonConfig.getHeight() + espacio_entre_botones / 2);
        rRecord = new Rectangle(espacio_entre_botones, botonConfig.getY() + botonConfig.getHeight() + espacio_entre_botones / 2, width, height);
        botonRecord.draw(spriteBatch);

        // Btn Créditos
        botonCreditos = textureAtlas.createSprite("button_creditos");
        botonCreditos.setSize(width, height);
        botonCreditos.setPosition(1.5f * espacio_entre_botones + botonConfig.getWidth(), espacio_entre_botones);
        rCreditos = new Rectangle(1.5f * espacio_entre_botones + botonConfig.getWidth(), espacio_entre_botones, width, height);
        botonCreditos.draw(spriteBatch);
    }

    /**
     * dibuja el fondo de Configuración
     */
    public void dibujarFondoConfig() {
        dibujarFondo();
        textureAtlas = new TextureAtlas("botones/botones_vibracion.txt");
        if (miJuego.isVibracion) {
            botonVibracion = textureAtlas.createSprite("imagen_vibracion");
            botonVibracion.setSize(width, height);
            botonVibracion.setPosition(anchoPantalla * 1 / 7, altoPantalla / 2);
            botonVibracion.draw(spriteBatch);
        } else {
            botonSinVibracion = textureAtlas.createSprite("imagen_no_vibracion");
            botonSinVibracion.setSize(width, height);
            botonSinVibracion.setPosition(anchoPantalla * 1 / 7, altoPantalla / 2);
            botonSinVibracion.draw(spriteBatch);
        }
        rVibracion = new Rectangle(anchoPantalla * 1 / 7, altoPantalla / 2, width, height);

        textureAtlas = new TextureAtlas("botones/botones_sonido.txt");
        if (miJuego.isSonido) {
            botonSonido = textureAtlas.createSprite("sonido_on");
            botonSonido.setSize(width, height);
            botonSonido.setPosition(anchoPantalla * 3 / 7, altoPantalla / 2);
            botonSonido.draw(spriteBatch);
        } else {
            botonSinSonido = textureAtlas.createSprite("sonido_off");
            botonSinSonido.setSize(width, height);
            botonSinSonido.setPosition(anchoPantalla * 3 / 7, altoPantalla / 2);
            botonSinSonido.draw(spriteBatch);
        }
        rSonido = new Rectangle(anchoPantalla * 3 / 7, altoPantalla / 2, width, height);
        textureAtlas = new TextureAtlas("botones/botones_idioma.txt");
        if (miJuego.idioma_es) {
            botonEsp = textureAtlas.createSprite("idioma_es");
            botonEsp.setSize(width, height);
            botonEsp.setPosition(anchoPantalla * 5 / 7, altoPantalla / 2);
            botonEsp.draw(spriteBatch);

        } else {
            botonEng = textureAtlas.createSprite("idioma_en");
            botonEng.setSize(width, height);
            botonEng.setPosition(anchoPantalla * 5 / 7, altoPantalla / 2);
            botonEng.draw(spriteBatch);
        }
        rIdioma = new Rectangle(anchoPantalla * 5 / 7, altoPantalla / 2, width, height);
        dibujarBotonVolver();
    }

    /**
     * Dibuja el fondo del tutorial
     */
    public void dibujarTutorial() {
        font.setColor(Color.WHITE);
        reiniciarFuente(desiredFontSize);
        font.draw(spriteBatch, miJuego.myBundle.get("tutorial_btn_derecho"), espacio_entre_botones, altoPantalla - espacio_entre_botones, anchoPantalla / 5, 50, true);
        font.draw(spriteBatch, miJuego.myBundle.get("tutorial_btn_izquierdo"), anchoPantalla * 3 / 4, altoPantalla - espacio_entre_botones, anchoPantalla / 5, 50, true);
        if (metros < 200) {
            font.draw(spriteBatch, miJuego.myBundle.get("tutorial_esquiva"), anchoPantalla * 5 / 12, altoPantalla - espacio_entre_botones, anchoPantalla / 4, 10, true);
        } else if (metros < 700) {
            font.draw(spriteBatch, miJuego.myBundle.get("tutorial_sobrevive"), anchoPantalla * 5 / 12, altoPantalla - espacio_entre_botones, anchoPantalla / 4, 50, true);
        } else {
            font.getData().setScale(1.2f);
            font.draw(spriteBatch, miJuego.myBundle.get("tutorial_completado"), anchoPantalla * 5 / 12, altoPantalla - espacio_entre_botones, anchoPantalla / 4, 100, false);
        }
        reiniciarFuente(desiredFontSize);
    }


    /**
     * Dibuja el fondo de los records
     *
     * @param records
     */
    public void dibujarFondoRecord(Array<Float> records) {
        dibujarFondo();
        dibujarRecords(records);
        dibujarBotonVolver();
    }

    /**
     * Dibuja el fondo de los créditos con todos los participantes del proyecto
     */
    public void dibujarFondoCreditos() {
        dibujarFondo();
        reiniciarFuente(desiredFontSize);
        font.draw(spriteBatch, String.format(miJuego.myBundle.get("creditos") + ": " + miJuego.myBundle.get("gracias")), anchoPantalla / 3 - espacio_entre_botones / 2, altoPantalla - espacio_entre_botones / 2);
        font.draw(spriteBatch, String.format(miJuego.myBundle.get("participantes")), anchoPantalla / 5, altoPantalla - espacio_entre_botones * 1.5f);
        font.draw(spriteBatch, String.format(miJuego.myBundle.get("musica")), anchoPantalla / 2 +espacio_entre_botones/2, altoPantalla - espacio_entre_botones * 1.5f);
        reiniciarFuente(desiredFontSize*0.7f);
        // Participantes
        for (int i = 0; i < GameConstants.CREDITOS_NOMBRES_EQUIPO.length; i++) {
            font.draw(spriteBatch, String.format("%s", GameConstants.CREDITOS_NOMBRES_EQUIPO[i]), anchoPantalla / 5, altoPantalla - espacio_entre_botones - ((i + 2) * espacio_entre_botones * 2 / 3));
        }
        // Musica
        for (int i = 0; i < GameConstants.CREDITOS_NOMBRES_MUSICA.length; i++) {
            font.draw(spriteBatch, String.format("%s", GameConstants.CREDITOS_NOMBRES_MUSICA[i]), anchoPantalla /  2 +espacio_entre_botones/2, altoPantalla - espacio_entre_botones - ((i + 2) * espacio_entre_botones * 2 / 3),anchoPantalla / 4, 50, true);
        }
        // Recursos
        for (int i = 0; i < GameConstants.CREDITOS_PAGINAS_IMAGENES.length; i++) {
            font.draw(spriteBatch, String.format("%s", GameConstants.CREDITOS_PAGINAS_IMAGENES[i]), anchoPantalla /  2 +espacio_entre_botones/2, altoPantalla - espacio_entre_botones - ((i + 4) * espacio_entre_botones * 2 / 3),anchoPantalla / 4, 50, true);
        }
        reiniciarFuente(desiredFontSize);
        dibujarBotonVolver();
    }


    /**
     * Dibuja en fondo de Dia y el Sol en la mitad izquierda y el fondo de Noche y la Luna
     * en la mitad derecha
     */
    public void dibujarFondoMapa() {
        reiniciarFuente(desiredFontSize*1.2f);
        spriteBatch.draw(fondoDia, 0, 0, anchoPantalla / 2, altoPantalla);
        spriteBatch.draw(sol, -sol.getWidth() / 3, altoPantalla - sol.getHeight() / 2);
        font.draw(spriteBatch, miJuego.myBundle.get("dia"), anchoPantalla / 5, altoPantalla * 3 / 4);
        spriteBatch.draw(fondoNoche, anchoPantalla / 2, 0, anchoPantalla / 2, altoPantalla);
        spriteBatch.draw(luna, anchoPantalla - luna.getWidth() * 2 / 3, altoPantalla - luna.getHeight() / 2);
        font.draw(spriteBatch, miJuego.myBundle.get("noche"), anchoPantalla * 3 / 5, altoPantalla * 3 / 4);
        reiniciarFuente(desiredFontSize);
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
        textureAtlas = miJuego.idioma_es ? new TextureAtlas("botones/botones_muerte_es.txt") : new TextureAtlas("botones/botones_muerte_en.txt");
        botonVolverJugar = textureAtlas.createSprite("button_volver_jugar");
        botonVolverJugar.setSize(width, height);
        botonVolverJugar.setPosition(anchoPantalla / 2 - botonJugar.getWidth() - 30, altoPantalla / 2);
        botonVolverJugar.draw(spriteBatch);
        rVolverJugar = new Rectangle(anchoPantalla / 2 - botonJugar.getWidth() - 30, altoPantalla / 2, width, height);

        botonSalir = textureAtlas.createSprite("button_salir");
        botonSalir.setSize(width, height);
        botonSalir.setPosition(anchoPantalla / 2 + botonJugar.getWidth() - 30, altoPantalla / 2);
        botonSalir.draw(spriteBatch);
        rSalir = new Rectangle(anchoPantalla / 2 + botonJugar.getWidth() - 30, altoPantalla / 2, width, height);

        font.setColor(Color.RED);
        font.getData().setScale(1.2f);
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
     * y el Acelerómetro
     */
    public void dibujarMetros() {
        reiniciarFuente(desiredFontSize*0.8f);
        font.setColor(GameConstants.COLORES[miJuego.nivel - 1]);
        if (metros < 1000) {
            font.draw(spriteBatch, String.format("%s %d: %.0fm", miJuego.myBundle.get("nivel"), miJuego.nivel, metros), anchoPantalla / 2 - espacio_entre_botones, altoPantalla - altoPantalla / 25);
        } else {
            font.draw(spriteBatch, String.format("%s %d: %.2fkm", miJuego.myBundle.get("nivel"), miJuego.nivel, metros / 1000), anchoPantalla / 2 - espacio_entre_botones, altoPantalla - altoPantalla / 25);
        }
        reiniciarFuente(desiredFontSize);
    }

    /**
     * Dibuja el botón de volver
     */
    public void dibujarBotonVolver() {
        volver = miJuego.idioma_es ? new Texture(Gdx.files.internal("botones/button_volver_es.png")) : new Texture(Gdx.files.internal("botones/button_volver_en.png"));
        spriteBatch.draw(volver, anchoPantalla - width - espacio_entre_botones, espacio_entre_botones, width, height);
        rVolver = new Rectangle(anchoPantalla - width - espacio_entre_botones, espacio_entre_botones, width, height);
    }

    /**
     * Dibuja los records desde la máxima puntuación hasta un máximo de 10 puntuaciones y
     * si no hay ninguno guardado
     *
     * @param records matriz de records
     */
    public void dibujarRecords(Array<Float> records) {
        reiniciarFuente(desiredFontSize);
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
            font.getData().setScale(desiredFontSize*1.2f);
            font.setColor(Color.RED);
            font.draw(spriteBatch, miJuego.myBundle.get("no_records"), anchoPantalla / 4, altoPantalla / 2, anchoPantalla * 3 / 5, 10, false);
        }
        font.setColor(Color.WHITE);
        reiniciarFuente(desiredFontSize);
    }


    /**
     * Dibuja las vidas restantes del personaje
     */
    public void dibujarVidas() {
        for (int i = 0; i < miJuego.vidas; i++) {
            spriteBatch.draw(textureRegion, anchoPantalla / 8 + (i + 1) * vidas.getWidth() * 1.2f, altoPantalla - vidas.getHeight() * 1.2f);
        }
    }

    /**
     * Genera una fuente con la ruta del archivo y con cierto tamaño
     *
     * @param path ruta del archivo de la fuente
     * @param size tamaño de la fuente
     * @return
     */
    public BitmapFont generateFont(String path, double size) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(path));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.genMipMaps = true;
        parameter.size = (int) Math.ceil(size);
        parameter.magFilter = Texture.TextureFilter.Linear;
        parameter.minFilter = Texture.TextureFilter.Linear;
        generator.scaleForPixelHeight((int) size);

        return generator.generateFont(parameter);
    }

    public void reiniciarFuente(float size) {
        font.getData().setScale(size/desiredFontSize);
    }


}
