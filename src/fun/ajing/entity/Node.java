package fun.ajing.entity;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private int childCount = 0;
    private String path;
    private String value;
    private List<Node> children;

    public int getChildCount() {
        return childCount;
    }

    public String getPath() {
        return path;
    }

    public String getValue() {
        return value;
    }

    public Node(String path){
        this.path = path;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public Node(String path, List<Node> children){
        this.path = path;
        this.children = children;
    }

    public List<Node> getChildren(){
        return children;
    }

    public boolean addChild(Node node){
        if(children == null){
            children = new ArrayList<>();
        }
        ++childCount;
        children.add(node);
        return true;
    }

    private Node getLastChild(){
        if (children == null || childCount == 0) {
            return null;
        }
        return children.get(childCount - 1);
    }

    public Node getParentNode(Node root, int depth){
        Node t = root;
        if (t == null) return null;
        for(int i = 0; i < depth; ++i){
            assert t != null;
            t = t.getLastChild();
        }
        return t;
    }
}
