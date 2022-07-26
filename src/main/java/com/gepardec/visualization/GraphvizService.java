package com.gepardec.visualization;

import guru.nidi.graphviz.attribute.Rank;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Node;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static guru.nidi.graphviz.attribute.Rank.RankDir.LEFT_TO_RIGHT;
import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.node;
import static guru.nidi.graphviz.model.Link.to;

public class GraphvizService {

    public void drawGraph(HashMap<String, String> allRepos) {

        List<Node> nodeList = new ArrayList<>();

        for (Map.Entry<String,String> entry : allRepos.entrySet()) {
            Node n = node(entry.getKey()).link(to(node(entry.getValue())));
            nodeList.add(n);
        }

        Graph g = graph("example1").directed()
                .graphAttr().with(Rank.dir(LEFT_TO_RIGHT))
                //.nodeAttr().with(Font.name("arial"))
                .linkAttr().with("class", "link-class")
                .with(
                        nodeList
                );

        try {
            Graphviz.fromGraph(g).height(1000).render(Format.PNG).toFile(new File("example/ex1.png"));
        } catch (IOException ex) {
            System.err.println("ERROR");
        }
    }
}
