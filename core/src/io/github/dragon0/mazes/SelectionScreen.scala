package io.github.dragon0.mazes;
import io.github.dragon0.mazes.grid._;
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
    var showSolution = false

    override def render(delta:Float) : Unit = {
        if(checkInput){
            drawOptions
        }
        else {
            dispose
        }
    }

    def checkInput : Boolean = {
        if(Gdx.input.isKeyJustPressed(Keys.SPACE)){
            showSolution = !showSolution
            true
        }
        if(Gdx.input.isKeyJustPressed(Keys.Q)){
            setScreen(BinaryTree)
            true
        }
        else if(Gdx.input.isKeyJustPressed(Keys.W)){
            setScreen(Sidewinder)
            true
        }
        else if(Gdx.input.isKeyJustPressed(Keys.E)){
            setScreen(AldousBorder)
            true
        }
        else if(Gdx.input.isKeyJustPressed(Keys.R)){
            setScreen(Wilsons)
            true
        }
        else if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
            Gdx.app.exit;
            false
        }
        else {
            true
        }
    }

    def setScreen(algo: MazeAlgorithm) = {
        val cell_size = 10
        val grid = if(showSolution)
            new ColoredGrid((height-1)/cell_size, (width-1)/cell_size)
        else
            new Grid((height-1)/cell_size, (width-1)/cell_size)
        game.setScreen(new MazeScreen(game, this, grid, cell_size, algo))
    }

    def drawOptions : Unit = {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.font.draw(game.batch,
            "SPACE. " + (if(showSolution) "Colored" else "Grayscale"),
            width/5, height-(height/5)*4);
        game.font.draw(game.batch,
            "Q. Binary Tree", width/5, height-(height/5));
        game.font.draw(game.batch,
            "W. Sidewinder", (width/5)*2, height-(height/5));
        game.font.draw(game.batch,
            "E. Aldous-Broder", (width/5)*3, height-(height/5));
        game.font.draw(game.batch,
            "R. Wilson's", (width/5)*4, height-(height/5));
        game.batch.end();
    }

    override def resize(width: Int, height: Int) : Unit = {
        this.width = width
        this.height = height
    }

    override def dispose : Unit = {
    }
}

