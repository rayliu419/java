package main.antlr;

import main.antlr.Edge;
import main.antlr.Vertex;
import main.antlr.autogen.GraphBaseListener;
import main.antlr.autogen.GraphParser;

class MyGraphBaseListener extends GraphBaseListener {

    Graph g;

    @Override
    public void exitGraph(GraphParser.GraphContext ctx) {
        System.out.println("exit graph");
    }

    public MyGraphBaseListener(Graph g) {
        this.g = g;
    }

    @Override
    public void exitEdge(GraphParser.EdgeContext ctx) {
        //Once the edge rule is exited the data required for the edge i.e
        //vertices and the weight would be available in the EdgeContext
        //and the same can be used to populate the semantic model
        Vertex fromVertex = new Vertex(ctx.vertex(0).ID().getText());
        Vertex toVertex = new Vertex(ctx.vertex(1).ID().getText());
        double weight = Double.parseDouble(ctx.NUM().getText());
        Edge e = new Edge(fromVertex, toVertex, weight);
        g.addEdge(e);
    }


}