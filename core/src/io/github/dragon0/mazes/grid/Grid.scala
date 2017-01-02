package io.github.dragon0.mazes.grid;
import scala.util.Random;

class Grid(val rows: Int, val columns: Int){
    val grid = prepareGrid

    val size = rows * columns
    var distances: Option[Distances] = None

    private def prepareGrid : Array[Array[Cell]] = {
        (0 until rows) map {
            row => (0 until columns) map {
                column => new Cell(row, column)} toArray } toArray
    }

    eachCell {(cell) => {
        val (row, column) = (cell.row, cell.column)
        cell.north = (this(row-1, column))
        cell.south = (this(row+1, column))
        cell.east = (this(row, column+1))
        cell.west = (this(row, column-1))
    }}

    def apply(row: Int, column: Int) : Option[Cell] = {
        if(!((0 until rows) contains row)) {
            None
        }
        else if (!((0 until grid(row).length) contains column)){
            None
        }
        else{
            Option(grid(row)(column))
        }
    }

    def randomCell(implicit rand: Random = new Random()): Cell = {
        val row = rand.nextInt(rows)
        val column = rand.nextInt(grid(row).length)
        this(row, column).get
    }

    def eachRow(f: Array[Cell]=>Unit): Unit = {
        for( row <- grid ) f(row)
    }

    def eachCell(f: Cell=>Unit): Unit = {
        for( row <- grid; cell <- row ) f(cell)
    }

    def colorFor(cell: Cell): Option[(Float, Float, Float, Float)] = None

    def setDistances(distances:Distances) = {
        this.distances = Option(distances)
    }
}

