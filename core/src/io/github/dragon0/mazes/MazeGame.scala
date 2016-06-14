package io.github.dragon0.mazes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

class MazeGame extends Game {
    var batch:SpriteBatch = _
    var font:BitmapFont = _
    
    override def create : Unit = {
        batch = new SpriteBatch()
        font = new BitmapFont()
        setScreen(new SelectionScreen(this))
    }

    override def dispose : Unit = {
        batch.dispose()
        font.dispose()
        super.dispose()
    }
}
