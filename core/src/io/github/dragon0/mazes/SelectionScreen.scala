package io.github.dragon0.mazes;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

class SelectionScreen(val game: MazeGame) extends ScreenAdapter {

    var width = Gdx.graphics.getWidth()
    var height = Gdx.graphics.getHeight()

    override def render(delta:Float) : Unit = {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.font.draw(game.batch, "Hello World", width/2, height/2);
        game.batch.end();
    }

    override def resize(width: Int, height: Int) : Unit = {
        this.width = width
        this.height = height
    }

    override def dispose : Unit = {
    }
}

