package com.pruebas.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class GameConstants {

    /**
     * Define el intervalo de tiempo entre cada iteración de la simulación física
     */
    public static final float TIMESTEP = 1.0f / 60.0f;

    /**
     * Especifica cuántas veces se iterará para resolver las velocidades
     * de los cuerpos físicos en cada paso de tiempo.
     */
    public static final int VELOCITY_ITERATIONS = 8;

    /**
     *  Determina cuántas veces se iterará para resolver las posiciones
     *  de los cuerpos físicos en cada paso de tiempo
     */
    public static final int POSITION_ITERATIONS = 3;
    public static final int SCREEN_MENU = 1;
    public static final int SCREEN_PLAY = 2;
    public static final int SCREEN_CONFIG = 3;
    public static final int SCREEN_RECORD = 4;
    public static final int SCREEN_MAPA = 5;
    public static final int SCREEN_PERSONAJE = 6;
    public static final int SCREEN_TUTORIAL = 7;
    public static final int SCREEN_CREDITOS = 8;

    /**
     * Alto de la pantalla
     */
    public static float ALTO_PANTALLA = Gdx.graphics.getHeight();

    /**
     * Ancho de la pantalla
     */
    public static float ANCHO_PANTALLA = Gdx.graphics.getWidth();

    public static final Color BLANCO = Color.WHITE;
    public static final Color ROSA = Color.PINK;
    public static final Color MARRON = Color.BROWN;
    public static final Color AMARILLO = Color.YELLOW;
    public static final Color NARANJA = Color.ORANGE;
    public static final Color ROJO = Color.RED;

    /**
     * Colección colores para determinar el nivel
     */
    public static final Color[] COLORES = {BLANCO, ROSA, MARRON, AMARILLO, NARANJA, ROJO};

    /**
     * Lista de personas que han participado en el proyecto
     */
    public static final String [] CREDITOS_NOMBRES_EQUIPO = {
            "Guillermo Bastos",
            "Javier Cousido",
            "Nicolás Fernández",
            "Laura",
            "Adrián"
    };

    /**
     * Lista de personas que han elaborado la música
     */
    public static final String [] CREDITOS_NOMBRES_MUSICA = {
            "Alex daiz",
            "Pixabay.com",
    };

    /**
     * lista de paginas web para los recursos
     */
    public static final String [] CREDITOS_PAGINAS_IMAGENES = {
            "https://www.pixilart.com",
            "https://www.flaticon.es",
            "https://www.clickminded.com"
    };
//    /button-generator/
}
