package com.pruebas.game;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.List;

public class GameConstants {

    public static final float TIMESTEP = 1.0f / 60.0f;
    public static final int VELOCITY_ITERATIONS = 8;
    public static final int POSITION_ITERATIONS = 3;
    public static final int screenMenu = 1;
    public static final int screenPlay = 2;
    public static final int screenConfig = 3;
    public static final int screenRecord = 4;
    public static final int screenMapa = 5;
    public static final int screenPersonaje = 6;
    public static final int screenTurorial = 7;
    public static final int screenCreditos = 8;
    public static  final float altoPantalla = Gdx.graphics.getHeight();
    public static  final float anchoPantalla = Gdx.graphics.getWidth();

    public static final String [] creditosNombres = {"Guillermo Bastos","Javier Cousido", "Nicolás Fernández"};


}
