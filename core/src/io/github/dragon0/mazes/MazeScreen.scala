package io.github.dragon0.mazes;
import io.github.dragon0.mazes.grid._;
import io.github.dragon0.mazes.algorithms._;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap;

class MazeScreen(val game:MazeGame, val sScreen: SelectionScreen, val grid: Grid, val cell_size: Int, val algo:MazeAlgorithm) extends ScreenAdapter {

    var width = Gdx.graphics.getWidth()
    var height = Gdx.graphics.getHeight()
    val tex = drawMaze

    override def render(delta:Float) : Unit = {
        if(checkInput){
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            val w = width / 2 - tex.getWidth()/2
            val h = height / 2 - tex.getHeight()/2
            game.batch.begin();
            game.batch.draw(tex, w, h)
            game.batch.end();
        }
        else {
            dispose
        }
    }

    def checkInput : Boolean = {
        if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
            game.setScreen(sScreen)
            false
        }
        else if(Gdx.input.isKeyJustPressed(Keys.SPACE)){
            sScreen.setScreen(algo)
            false
        }
        else {
            true
        }
    }

    def drawMaze : Texture = {
        algo(grid)

        val start = grid(grid.rows/2, grid.columns/2)
        start match {
            case Some(s) => {
                grid.setDistances(s.distances)
            }
            case None => {}
        }

        val img_width = cell_size * grid.columns
        val img_height = cell_size * grid.rows

        val background = Color.WHITE
        val wall = Color.BLACK

        val pixmap = new Pixmap(img_width + 1, img_height + 1,
            Pixmap.Format.RGBA8888)
        pixmap.setColor(background)
        pixmap.fill()

        def coords(cell:Cell) = (
                cell.column * cell_size,
                cell.row * cell_size,
                (cell.column + 1) * cell_size,
                (cell.row + 1) * cell_size

            )

        // fill
        grid eachCell {
            cell =>
                val (x1, y1, x2, y2) = coords(cell)
                val w = x2 - x1
                val h = y2 - y1

                grid.colorFor(cell) match {
                    case Some((r, g, b, a)) =>
                        pixmap.setColor(r, g, b, a)
                        pixmap.fillRectangle(x1, y1, w, h)
                    case None => {}
                }
        }

        pixmap.setColor(wall)
        // walls
        grid eachCell {
            cell =>
                val (x1, y1, x2, y2) = coords(cell)

                if(!cell.north.isDefined){
                    pixmap.drawLine(x1, y1, x2, y1)
                }
                if(!cell.west.isDefined){
                    pixmap.drawLine(x1, y1, x1, y2)
                }
                if(!cell.isLinked(cell.east)){
                    pixmap.drawLine(x2, y1, x2, y2)
                }
                if(!cell.isLinked(cell.south)){
                    pixmap.drawLine(x1, y2, x2, y2)
                }
        }

        val tex = new Texture(pixmap)
        pixmap.dispose()
        tex
    }

    override def resize(width: Int, height: Int) : Unit = {
        this.width = width
        this.height = height
    }

    override def dispose : Unit = {
    }
}


