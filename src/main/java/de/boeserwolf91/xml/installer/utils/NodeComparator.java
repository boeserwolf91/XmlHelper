package de.boeserwolf91.xml.installer.utils;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.Comparator;

public class NodeComparator implements Comparator<Node>
{
    public int compare(Node first, Node second)
    {
        Integer firstPriority = 0;
        Integer secondPriority = 0;

        NamedNodeMap attributes = first.getAttributes();

        Node priorityNode = attributes.getNamedItem("priority");
        if (priorityNode != null)
        {
            firstPriority = Integer.valueOf(priorityNode.getTextContent());
        }

        attributes = second.getAttributes();
        priorityNode = attributes.getNamedItem("priority");
        if (priorityNode != null)
        {
            secondPriority = Integer.valueOf(priorityNode.getTextContent());
        }
        return secondPriority.compareTo(firstPriority);
    }
}
