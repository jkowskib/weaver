package jkowski.weaver;

import jkowski.weaver.errors.MalformedXML;

import java.util.Stack;
import java.util.ArrayList;

public class XML {
    public static Tag parse(String xmlBlob) {
        Stack<Tag> stack = new Stack<>();
        StringBuilder contentBuffer = new StringBuilder();

        int pointer = 0;

        while (pointer < xmlBlob.length()) {
            if (xmlBlob.charAt(pointer) == '<') {
                /*
                 * In XML the character after the < could be one of the following
                 * ? for an XML header
                 *  (f.ex) <?xml version="1.0" encoding="UTF-8"?>
                 *
                 * / for a closing tag
                 *
                 * > a broken tag
                 * (f.ex) <> this is invalid
                 *
                 * Anything else means it's an opening tag with a name inside
                 * (f.ex) <tagname>
                 */

                int endOfTag = xmlBlob.indexOf('>', pointer + 1);
                String innerTag = xmlBlob.substring(pointer + 1, endOfTag);

                switch (xmlBlob.charAt(pointer + 1)) {
                    case '?':
                        contentBuffer.setLength(0);
                        break;
                    case '/':
                        if (stack.size() != 1) {
                            Tag childTag = stack.pop();
                            childTag.setContent(contentBuffer.toString());
                            stack.peek().addChild(childTag);
                            contentBuffer.setLength(0); // Clear buffer
                        }
                        break;
                    case '>':
                        throw new MalformedXML("xml file contained broken tags");
                    default:
                        Tag parsedTag = parseOpeningTag(innerTag);
                        stack.push(parsedTag);
                        contentBuffer.setLength(0);
                        break;
                }

                pointer = endOfTag + 1;
                continue;
            }

            contentBuffer.append(xmlBlob.charAt(pointer));
            pointer++; // move to the next character
        }

        return stack.pop();
    }

    private static Tag parseOpeningTag(String tagData) {
        int nameEnd = tagData.indexOf(' ');

        /*
         * This means the tag has no attributes
         * (f.ex) <example>
         */
        if (nameEnd == -1) {
            return new Tag(tagData);
        }

        String tagName = tagData.substring(0, nameEnd);

        Tag createdTag = new Tag(tagName);

        ArrayList<String> attributes = extractAttributes(tagData.substring(nameEnd));

        for (String attribute : attributes) {
            String[] parts = attribute.split("=");

            /*
             * A boolean attribute (attribute with no value)
             * (f.ex) <tag boolean>
             */
            if (parts.length != 2) {
                createdTag.setAttribute(parts[0], null);
                continue;
            }

            String name = parts[0];
            String value = parts[1].substring(1, parts[1].length() - 1);

            createdTag.setAttribute(name, value);
        }

        return createdTag;
    }

    private static ArrayList<String> extractAttributes(String tagData) {
        StringBuilder buffer = new StringBuilder();
        ArrayList<String> attributes = new ArrayList<>();
        boolean inString = false;

        for (char c : tagData.toCharArray()) {
            if (c == '"') {
                // When we see a " flip the state of inString
                inString = !inString;
            }

            if (c == ' ' && !inString) {
                // If the buffer is empty there is no need to append or reset the buffer
                if (buffer.isEmpty()) {
                    continue;
                }

                attributes.add(buffer.toString());
                buffer.setLength(0);

                continue;
            }

            buffer.append(c);
        }

        // If the buffer has any last data in it, make sure its added
        if (!buffer.isEmpty()) {
            attributes.add(buffer.toString());
        }

        return attributes;
    }
}
