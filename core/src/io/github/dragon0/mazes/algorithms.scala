package io.github.dragon0.mazes.algorithms
import io.github.dragon0.mazes.grid._;
import scala.util.Random

abstract class MazeAlgorithm extends Function1[Grid, Unit] {
    override def apply(grid: Grid): Unit;
}

object BinaryTree extends MazeAlgorithm {
    override def apply(grid: Grid): Unit = {
        val rand = new Random
        grid eachCell {
            cell =>
                var neighbors : List[Cell] = List()
                if(cell.north.isDefined){
                    neighbors = neighbors :+ cell.north.get
                }
                if(cell.east.isDefined){
                    neighbors = neighbors :+ cell.east.get
                }

                if(!neighbors.isEmpty){
                    val index = rand.nextInt(neighbors.length)
                    val neighbor = neighbors(index)
                    cell.link(neighbor)
                }
        }
    }
}

object Sidewinder extends MazeAlgorithm {
    override def apply(grid: Grid): Unit = {
        val rand = new Random
        grid eachRow {
            row =>
                var run: List[Cell] = List()
                row foreach {
                    cell =>
                        run = run :+ cell
                        val atEasternBoundary = !cell.east.isDefined
                        val atNorthernBoundary = !cell.north.isDefined

                        val shouldCloseOut = (
                            atEasternBoundary
                            || (!atNorthernBoundary && rand.nextBoolean))

                        if(shouldCloseOut){
                            val index = rand.nextInt(run.length)
                            val member = run(index)
                            member.north foreach {n => member.link(n)}
                            run = List()
                        }
                        else{
                            cell.east foreach {c => cell.link(c)}
                        }
                }
        }
    }
}

