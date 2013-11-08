package de.boeserwolf91.xml.utils;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;

public class XmlUtils
{
    private XMLEvent EOL;
    private XMLEvent TAB;

    private void initXmlUtils(XMLEventFactory eventFactory)
    {
        if(this.EOL == null)
        {
            this.EOL = eventFactory.createDTD("\n");
        }
        if(this.TAB == null)
        {
            this.TAB = eventFactory.createDTD("\t");
        }
    }

    /**
     * This method creates an open and a closing tag with text between those both tags.
     *
     * @param eventWriter the eventWriter of the file
     * @param eventFactory the eventFactory which creates the content
     * @param hierarchy   the hierarchy level of the element
     * @param name        the name of the tags
     * @param text        the text between the tags
     * @param attributes  the attributes of the tags
     *
     * @throws javax.xml.stream.XMLStreamException is thrown if an error occurred by writing or creating a xml element
     */
    public void createTextElement(XMLEventWriter eventWriter, XMLEventFactory eventFactory, int hierarchy, String name, String text, Attribute... attributes) throws XMLStreamException
    {
        this.initXmlUtils(eventFactory);

        for (int i = 0; i < hierarchy; i++)
        {
            eventWriter.add(this.TAB);
        }
        eventWriter.add(eventFactory.createStartElement("", "", name));
        for (Attribute attribute : attributes)
        {
            eventWriter.add(attribute);
        }
        eventWriter.add(eventFactory.createCharacters(text));
        eventWriter.add(eventFactory.createEndElement("", "", name));
        eventWriter.add(this.EOL);
    }

    /**
     * This method creates an opening tag with the given attributes
     *
     * @param eventWriter the eventWriter of the file
     * @param eventFactory the eventFactory which creates the content
     * @param hierarchy   the hierarchy level of the element
     * @param name        the name of the tag
     * @param attributes  the attributes of the tag
     *
     * @throws XMLStreamException is thrown if an error occurred by writing or creating a xml element
     */
    public void createStartElement(XMLEventWriter eventWriter, XMLEventFactory eventFactory, int hierarchy, String name, Attribute... attributes) throws XMLStreamException
    {
        this.initXmlUtils(eventFactory);
        for (int i = 0; i < hierarchy; i++)
        {
            eventWriter.add(this.TAB);
        }
        eventWriter.add(eventFactory.createStartElement("", "", name));
        for (Attribute attribute : attributes)
        {
            eventWriter.add(attribute);
        }
        eventWriter.add(this.EOL);
    }

    /**
     * This method creates a closing tag.
     *
     * @param eventWriter the eventWriter of the file
     * @param eventFactory the eventFactory which creates the content
     * @param hierarchy   the hierarchy level of the element
     * @param name        the name of the tag
     *
     * @throws XMLStreamException is thrown if an error occurred by writing or creating a xml element
     */
    public void createEndElement(XMLEventWriter eventWriter, XMLEventFactory eventFactory, int hierarchy, String name) throws XMLStreamException
    {
        this.initXmlUtils(eventFactory);

        for (int i = 0; i < hierarchy; i++)
        {
            eventWriter.add(this.TAB);
        }
        eventWriter.add(eventFactory.createEndElement("", "", name));
        eventWriter.add(this.EOL);
    }

    /**
     * This method creates a comment in the file
     *
     * @param eventWriter the eventWriter of the file
     * @param eventFactory the eventFactory which creates the content
     * @param hierarchy   the hierarchy level of the element
     * @param comment     comment message
     *
     * @throws XMLStreamException is thrown if an error occurred by writing or creating a xml element
     */
    public void createSimpleComment(XMLEventWriter eventWriter, XMLEventFactory eventFactory, int hierarchy, String comment) throws XMLStreamException
    {
        this.initXmlUtils(eventFactory);

        for (int i = 0; i < hierarchy; i++)
        {
            eventWriter.add(this.TAB);
        }
        eventWriter.add(eventFactory.createComment(comment));
        eventWriter.add(this.EOL);
    }


    /**
     * This method creates a multiline comment in the file.
     *
     * @param eventWriter  the eventWriter of the file
     * @param eventFactory the eventFactory which creates the content
     * @param hierarchy    the hierarchy level of the element
     * @param charsPerLine the number of characters which should be in one line
     * @param comment      the comment message
     *
     * @throws XMLStreamException is thrown if an error occurred by writing or creating a xml element
     */
    public void createMultilineComment(XMLEventWriter eventWriter, XMLEventFactory eventFactory, int hierarchy, int charsPerLine, String comment) throws XMLStreamException
    {
        this.initXmlUtils(eventFactory);

        List<String> commentList = new ArrayList<String>(1);
        String word = "";
        String currentLine = "";

        for (char character : comment.toCharArray())
        {
            if (character == ' ' || character == '\t' || character == '\n')
            {
                if (currentLine.length() != 0 && currentLine.length() + word.length() > charsPerLine)
                {
                    commentList.add(currentLine.trim());
                    currentLine = "";
                }
                currentLine += word;
                word = "";
                if (character == '\n')
                {
                    commentList.add(currentLine);
                    currentLine = "";
                }
                else
                {
                    currentLine += character;
                }
            }
            else
            {
                word += character;
            }
        }
        currentLine += word;
        commentList.add(currentLine);

        String displayComment = "";
        for (int i = 0; i < commentList.size() + 1; i++)
        {
            displayComment += '\n';
            for (int j = 0; j < hierarchy; j++)
            {
                displayComment += '\t';
            }
            try
            {
                displayComment += commentList.get(i);
            }
            catch (RuntimeException ignored) {}
        }

        for (int i = 0; i < hierarchy; i++)
        {
            eventWriter.add(this.TAB);
        }
        eventWriter.add(eventFactory.createComment(displayComment));
        eventWriter.add(this.EOL);
    }

    /**
     * This method creates simple text in the file
     *
     * @param eventWriter the eventWriter of the file
     * @param eventFactory the eventFactory which creates the content
     * @param hierarchy   the hierarchy level of the element
     * @param text        text
     *
     * @throws XMLStreamException is thrown if an error occurred by writing or creating a xml element
     */
    public void createCharacaters(XMLEventWriter eventWriter, XMLEventFactory eventFactory, int hierarchy, String text) throws XMLStreamException
    {
        for (int i = 0; i < hierarchy; i++)
        {
            eventWriter.add(this.TAB);
        }
        eventWriter.add(eventFactory.createCharacters(text));
        eventWriter.add(this.EOL);
    }
}
