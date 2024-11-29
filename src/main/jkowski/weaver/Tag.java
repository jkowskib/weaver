package jkowski.weaver;

import java.util.HashMap;
import java.util.ArrayList;

/**
 * Tag
 */
public class Tag {
    private final String name;
    /**
     * Tag content
     */
    protected String content;
    private final HashMap<String, String> attributes = new HashMap<>();
    private final ArrayList<Tag> children = new ArrayList<>();

    /**
     * XML Tag
     * @param name String
     */
    public Tag(String name) { this.name = name; }

    /**
     * Tag name
     * @return String
     */
    public String name() { return name; }

    /**
     * Content of Tag
     * @return String
     */
    public String content() { return content; }

    /**
     * Sets the content of the tag
     * @param content String
     */
    protected void setContent(String content) { this.content = content; }

    /**
     * Adds a child Tag to the current Tag
     * @param child child Tag
     */
    protected void addChild(Tag child) {
        children.add(child);
    }

    /**
     * Sets an attribute of the current Tag
     * @param name name
     * @param value value
     */
    protected void setAttribute(String name, String value) {
        attributes.put(name, value);
    }

    /**
     * Get an attribute of the current Tag
     * @param name attribute name
     * @return String or null if not found
     */
    public String getAttribute(String name) {
        return attributes.get(name);
    }

    /**
     * Gets the first child Tag by its name
     * @param name tag name
     * @return Tag or null if not found
     */
    public Tag getTag(String name) {
        for (Tag child : children) {
            if (child.name().equals(name)) {
                return child;
            }
        }
        return null;
    }

    /**
     * Gets all child Tags by a given name
     * @param name tag name
     * @return Tag[]
     */
    public Tag[] getAllTags(String name) {
        ArrayList<Tag> tags = new ArrayList<>();
        for (Tag child : children) {
            if (child.name().equals(name)) {
                tags.add(child);
            }
        }
        return tags.toArray(new Tag[0]);
    }
}
