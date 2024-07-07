package fun.ajing.utils;

import fun.ajing.entity.Node;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;

public class Yml2pro {

    private static Map<String, Node> ymlTrees = new TreeMap<>();

    public static void convert2PropertiesFile(String sourceFile, String targetFile){
        buildTrees(sourceFile);
        writer(targetFile);
    }

    private static void buildTrees(String sourceFile){
        try(BufferedReader reader = new BufferedReader(new FileReader(sourceFile))){
            String line;
            Node root = null;
            while((line = reader.readLine()) != null){
                if (line.trim().startsWith("#")) {
                    continue;
                }
                String[] strings = line.split(":", 2);
                if(line.startsWith(" ")) {
                    int blankCount = blankCount(strings[0]);
                    assert root != null;
                    Node parent = root.getParentNode(root, (blankCount - 2) >> 1);
                    Node child = new Node(strings[0].trim());
                    if(strings.length > 1) {
                        child.setValue(strings[1].trim());
                    }
                    parent.addChild(child);
                } else {
                    root = new Node(strings[0].trim());
                    ymlTrees.put(root.getPath(), root);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int blankCount(String s){
        int cnt = 0;
        while(s.charAt(cnt) == ' '){
            ++cnt;
        }
        return cnt;
    }

    public static void writer(String targetFile){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(targetFile))) {
            ymlTrees.values().forEach(node -> backtrack(node, writer, new StringBuilder()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void backtrack(Node node, BufferedWriter bw, StringBuilder sb){
        if (node == null) {
            return;
        }

        if (node.getChildCount() == 0) {
            String s = node.getPath() + "=" + node.getValue();
            sb.append(s);
            try {
                bw.write(sb.toString());
                bw.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            sb.delete(sb.length() - s.length(), sb.length());
        } else {
            String s = node.getPath() + ".";
            sb.append(s);
            node.getChildren().forEach(child -> backtrack(child, bw, sb));
            sb.delete(sb.length() - s.length(), sb.length());
        }

    }

}
