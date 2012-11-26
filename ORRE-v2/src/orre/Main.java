package orre;

import orre.core.GameMain;
import orre.util.Logger;

public class Main {
	public static void main(String[] args) 
	{
		new Logger();
		GameMain game = new GameMain();
		game.initialize();
		game.mainLoop();
	}
}
