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

object AldousBorder extends MazeAlgorithm {
    override def apply(grid: Grid): Unit = {
        val rand = new Random
        var cell = grid.randomCell
        var unvisited = grid.size - 1

        while(unvisited > 0){
            var neighbors = cell.neighbors
            var index = rand.nextInt(neighbors.length)
            var neighbor = neighbors(index)

            if(neighbor.linkSet.isEmpty){
                cell.link(neighbor)
                unvisited = unvisited - 1
            }

            cell = neighbor
        }
    }
}

object Wilsons extends MazeAlgorithm {
    override def apply(grid: Grid): Unit = {
        val rand = new Random
        var unvisited: List[Cell] = List()
        grid eachCell { cell => unvisited = cell :: unvisited  }
        def sample(l: List[Cell]) = {
            val index = rand.nextInt(l.length)
            l(index)
        }

        var first = sample(unvisited)
        unvisited = unvisited diff List(first)

        while(unvisited.nonEmpty){
            var cell = sample(unvisited)
            var path = Vector(cell)

            while(unvisited contains cell){
                cell = sample(cell.neighbors)
                var position = path indexOf cell
                if(position >= 0) {
                    path = path take position+1
                }
                else {
                    path = path :+ cell
                }
            }

            for(index <- 0 until path.length-1){
                path(index) link path(index+1)
            }
            unvisited = unvisited diff path
        }
    }
}

