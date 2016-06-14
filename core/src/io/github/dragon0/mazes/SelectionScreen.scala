package io.github.dragon0.mazes;
import io.github.dragon0.mazes.algorithms._;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

class SelectionScreen(val game: MazeGame) extends ScreenAdapter {

    var width = Gdx.graphics.getWidth()
    var height = Gdx.graphics.getHeight()

    override def render(delta:Float) : Unit = {
        if(checkInput){
            drawOptions
        }
        else {
            dispose
        }
    }

    def checkInput : Boolean = {
        if(Gdx.input.isKeyJustPressed(Keys.Q)){
            game.setScreen(new MazeScreen(game, BinaryTree))
            false
        }
        else if(Gdx.input.isKeyJustPressed(Keys.W)){
            game.setScreen(new MazeScreen(game, Sidewinder))
            false
        }
        else if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
            Gdx.app.exit;
            false
        }
        else {
            true
        }
    }

    def drawOptions : Unit = {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.font.draw(game.batch,
            "Q. Binary Tree", width/5, height-(height/4));
        game.font.draw(game.batch,
            "W. Sidewinder", (width/5)*2, height-(height/4));
        game.batch.end();
    }

    override def resize(width: Int, height: Int) : Unit = {
        this.width = width
        this.height = height
    }

    override def dispose : Unit = {
    }
}

