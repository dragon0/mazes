package io.github.dragon0.mazes.grid;
import scala.math.{round}
import com.badlogic.gdx.Gdx

class ColoredGrid(rows: Int, columns: Int) extends Grid(rows, columns){
    var max: Int = 0

    override def colorFor(cell: Cell): Option[(Float, Float, Float, Float)] = {
        distances match {
            case None => None
            case Some(d) => {
                val distance = d(cell)
                val intensity = (max - distance).toFloat / max
                val dark = intensity
                val bright = (intensity / 2) + 0.5f
                Some((dark, bright, dark, 1.0f))
            }
        }
    }

    override def setDistances(distances:Distances) = {
        super.setDistances(distances)
        Option(distances) match {
            case None => {}
            case Some(d) => max = d.max._2
        }
    }
}

