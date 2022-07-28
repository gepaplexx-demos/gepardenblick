package com.gepardec.visualization;

import guru.nidi.graphviz.attribute.Rank;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Factory;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Node;

import javax.enterprise.context.ApplicationScoped;
import java.util.*;
import java.util.stream.Collectors;

import static guru.nidi.graphviz.attribute.Rank.RankDir.LEFT_TO_RIGHT;
import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.node;
import static guru.nidi.graphviz.model.Link.to;

@ApplicationScoped
public class GraphvizService {

    public String drawGraphFromSimpleStringHashMap(HashMap<String, String> allRepos) {

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

        return Graphviz.fromGraph(g).render(Format.SVG_STANDALONE).toString();
    }

    public String drawGraphFromComplexStringHashMap(HashMap<String, List<String>> allHooks) {

        List<Node> nodeList = new ArrayList<>();

        for (Map.Entry<String,List<String>> entry : allHooks.entrySet()) {
            List<String> hooks = entry.getValue();

            if(hooks == null) {
                continue;
            }

            for (String hook : hooks) {
                Node n = node(entry.getKey()).link(to(node(hook)));
                nodeList.add(n);
            }
        }

        Graph g = graph("example1").directed()
                .graphAttr().with(Rank.dir(LEFT_TO_RIGHT))
                //.nodeAttr().with(Font.name("arial"))
                .linkAttr().with("class", "link-class")
                .with(
                        nodeList
                );

        return Graphviz.fromGraph(g).render(Format.SVG_STANDALONE).toString();
    }

    public String drawGraphFromNestedStringHashMap(HashMap<String, HashMap<String, List<String>>> orgRepoHookMap) {

        HashMap<String, List<String>> repoHookMap;
        List<Node> hookNodeList;
        List<Node> orgRepoHookNodeList = new ArrayList<>();

        for (Map.Entry<String, HashMap<String, List<String>>> orgRepoHookEntry : orgRepoHookMap.entrySet()) {

            repoHookMap = orgRepoHookEntry.getValue();

            for (Map.Entry<String,List<String>> entry : repoHookMap.entrySet()) {
                List<String> hooks = entry.getValue();

                if(hooks == null) {
                    continue;
                }

                hookNodeList = hooks.stream().map(Factory::node).collect(Collectors.toList());

                Node n = node(orgRepoHookEntry.getKey()).link(to(node(entry.getKey()).link(hookNodeList)));
                hookNodeList.clear();
                orgRepoHookNodeList.add(n);
            }
        }

        Graph g = graph("example1").directed()
                .graphAttr().with(Rank.dir(LEFT_TO_RIGHT))
                //.nodeAttr().with(Font.name("arial"))
                .linkAttr().with("class", "link-class")
                .with(
                        orgRepoHookNodeList
                );

        return Graphviz.fromGraph(g).render(Format.SVG_STANDALONE).toString();
    }

    public String drawGraphFromBooleanHashMap(HashMap<String, Boolean> allRepos) {

        List<Node> nodeList = new ArrayList<>();

        for (Map.Entry<String,Boolean> entry : allRepos.entrySet()) {
            Node n = node(entry.getKey()).link(to(node(entry.getValue().toString())));
            nodeList.add(n);
        }

        Graph g = graph("example1").directed()
                .graphAttr().with(Rank.dir(LEFT_TO_RIGHT))
                //.nodeAttr().with(Font.name("arial"))
                .linkAttr().with("class", "link-class")
                .with(
                        nodeList
                );

        return Graphviz.fromGraph(g).render(Format.SVG_STANDALONE).toString();
    }
}
