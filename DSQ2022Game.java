package com.dsq2022.game;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;
/** this is the main entry point for the libgdx version of the game.
 *  nice starting framework: https://happycoding.io/tutorials/libgdx/game-screens
 */
public class DSQ2022Game extends Game {
	public Board b;
	public boolean soundOn=true, nameOn=true, rankOn = true;
	public MainController mc;
	public MainView mv;
	public PrefController pc;
	public int fromR=-1, fromC=-1, toR=-1, toC=-1, frameCount=-1;
    //-----------------------------------------------------------------------
	@Override public void create ( ) {
		this.fromR = this.fromC = this.toR = this.toC = this.frameCount = -1;
		init();
		this.mc = new MainController( this );
		this.mv = new MainView( this );
		this.pc = new PrefController( this );
		setScreen( this.mc );
	}
	//-----------------------------------------------------------------------
	private void init ( ) {
		this.b = new Board();
		//load preferences (including previous game in progress), if any
		// https://libgdx.com/wiki/preferences
		/*
		if (false) {  //disable for v1
			Preferences prefs = Gdx.app.getPreferences( "dsq2022.prefs" );
			//load board
			String boardString = prefs.getString( "board", null );
			System.out.println( "load board: " + boardString );
			if (boardString != null) {
				Json json = new Json();
				this.b = json.fromJson( Board.class, boardString );
				System.out.println( this.b );
				System.out.println( json.prettyPrint( b ) );
			}
			this.soundOn = prefs.getBoolean( "soundOn", true );
			this.nameOn = prefs.getBoolean( "namesOn", true );
			this.rankOn = prefs.getBoolean( "rankOn", true );
		}

		 */
	}
	//-----------------------------------------------------------------------
	@Override public void dispose ( ) {
		if (this.mv == null)    return;
		if (this.b == null)    return;
		this.mv.dispose();
		savePrefs();
	}
	//-----------------------------------------------------------------------
	/** save preferences (and current board state) */
	public void savePrefs ( ) {
		//https://stackoverflow.com/questions/23181623/libgdx-json-json-tojsonobject-keeps-returning
		Json json = new Json();
		//json.setTypeName( null );
		json.setUsePrototypes( false );  //won't work without it! (why?)
		//json.setIgnoreUnknownFields( true );
		//json.setOutputType( JsonWriter.OutputType.json );
		Preferences prefs = Gdx.app.getPreferences( "dsq2022.prefs" );
		prefs.putString(  "board",   json.toJson(this.b) );
		prefs.putBoolean( "soundOn", this.soundOn );
		prefs.putBoolean( "namesOn", this.nameOn );
		prefs.putBoolean( "rankOn",  this.rankOn );
		prefs.flush();
	}
}
