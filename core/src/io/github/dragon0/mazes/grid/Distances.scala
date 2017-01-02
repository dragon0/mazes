package io.github.dragon0.mazes.grid;

class Distances(val root:Cell) {
    var cells: Map[Cell, Int] = Map(root->0);

    def apply(cell: Cell): Int = cells(cell)
    def get(cell: Cell): Option[Int] = cells get cell
    def +(kv: (Cell, Int)): Unit = { cells = cells + kv }

    def pathTo(goal:Cell):Distances = {
        var current = goal
        var breadcrumbs = new Distances(root)
        breadcrumbs.cells = breadcrumbs.cells + (current -> cells(current))

        while(current != root){
            for(neighbor <- current.linkSet){
                if(cells(neighbor) < cells(current)){
                    breadcrumbs.cells = breadcrumbs.cells + (neighbor -> cells(neighbor))
                    current = neighbor
                }
            }
        }

        breadcrumbs
    }

    def max: (Cell, Int) = cells maxBy {case (cell, dist) => dist}
}

