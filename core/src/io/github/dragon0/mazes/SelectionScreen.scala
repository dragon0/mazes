package io.github.dragon0.mazes;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

class SelectionScreen(val game: MazeGame) extends ScreenAdapter {

    var img:Texture = new Texture("badlogic.jpg");

    override def render(delta:Float) : Unit = {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(img, 0, 0);
        game.batch.end();
    }
}

