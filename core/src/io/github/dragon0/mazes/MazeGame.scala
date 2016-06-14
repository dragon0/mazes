package io.github.dragon0.mazes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class MazeGame extends Game {
    var batch:SpriteBatch = _
    
    override def create : Unit = {
        batch = new SpriteBatch()
        setScreen(new SelectionScreen(this))
    }
}
