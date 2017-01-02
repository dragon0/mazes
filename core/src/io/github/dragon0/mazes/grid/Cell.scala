package io.github.dragon0.mazes.grid;
class Cell(val row: Int, val column: Int){
    var north: Option[Cell] = None
    var south: Option[Cell] = None
    var east: Option[Cell] = None
    var west: Option[Cell] = None
    var linkSet: Set[Cell] = Set()

    def link(cell: Cell): Unit = {
        linkSet = linkSet + cell
        cell.reverseLink(this)
    }

    private def reverseLink(cell: Cell): Unit = {
        linkSet = linkSet + cell
    }

    def unlink(cell: Cell): Unit = {
        linkSet = linkSet - cell
        cell.reverseUnlink(this)
    }

    private def reverseUnlink(cell: Cell): Unit = {
        linkSet = linkSet - cell
    }

    def isLinked(cell: Option[Cell]): Boolean = {
        cell match {
            case None => false
            case Some(cell) => linkSet(cell)
        }
    }

    def isLinked(cell: Cell): Boolean = {
        linkSet(cell)
    }

    def neighbors : List[Cell] = {
        var res: List[Cell] = List()
        if(north.isDefined){
            res = res :+ north.get
        }
        if(south.isDefined){
            res = res :+ south.get
        }
        if(east.isDefined){
            res = res :+ east.get
        }
        if(west.isDefined){
            res = res :+ west.get
        }
        res
    }

    def distances : Distances = {
        // New distances using this as root
        var distances = new Distances(this)
        var frontier = Set(this)

        while(frontier.nonEmpty){
            var newFrontier:Set[Cell] = Set()
            for(cell <- frontier; linked <- cell.linkSet){
                if(!(distances.cells contains linked)){
                    distances.cells = distances.cells + (linked -> (distances(cell) + 1))
                    newFrontier = newFrontier + linked
                }
            }
            frontier = newFrontier
        }

        distances
    }

}

