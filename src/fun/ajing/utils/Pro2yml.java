package fun.ajing.utils;

import com.intellij.openapi.util.text.StringUtil;
import fun.ajing.entity.Node;

import java.io.*;
import java.util.*;

public class Pro2yml {

    private static Map<String, Node> propertiesTrees = new Hashtable<>();

    public static void convert2YmlFile(String sourceFile, String targetFile){
        buildTrees(sourceFile);
        writer(targetFile);
    }

    private static void buildTrees(String sourceFile){
        try(BufferedReader reader = new BufferedReader(new FileReader(sourceFile))){
            String line;
            while((line = reader.readLine()) != null){
                if(line.trim().startsWith("#")) continue;
                String[] strings = line.split("=", 2);
                String value = strings.length < 2 ? "" : strings[1].trim();
                String[] path = strings[0].split("\\.");
                Node root = propertiesTrees.computeIfAbsent(path[0].trim(), Node::new);
                for(int i = 1; i < path.length; ++i){
                    root = findOrCreateChild(root, path[i].trim());
                }
                if (!StringUtil.isEmpty(value)) {
                    root.setValue(value);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static Node findOrCreateChild(Node parent, String childKey) {
        List<Node> children = parent.getChildren();
        if (children == null) {
            children = new ArrayList<>();
            parent.setChildren(children);
        }
        for (Node child : children) {
            if (childKey.equals(child.getPath())) {
                return child;
            }
        }
        Node newNode = new Node(childKey);
        parent.addChild(newNode);
        return newNode;
    }

    private static void writer(String targetFile){
        try(final BufferedWriter writer = new BufferedWriter(new FileWriter(targetFile))){
            propertiesTrees.values().forEach(node -> dfs(node, writer, 0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void dfs(Node node, BufferedWriter bw, int depth){
        if (node == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < depth; ++i){
            sb.append("  ");
        }
        sb.append(node.getPath()).append(":");
        if(node.getChildCount() == 0) {
            sb.append(" ").append(node.getValue());
        }
        try {
            bw.write(sb.toString());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Optional<List<Node>> children = Optional.ofNullable(node.getChildren());
        children.ifPresent(kids -> kids.forEach(kid -> dfs(kid, bw, depth + 1)));
    }
}
