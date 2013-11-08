package de.boeserwolf91.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.DTD;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.XMLEvent;

import de.boeserwolf91.xml.exception.XmlSaveException;
import de.boeserwolf91.xml.utils.StringUtils;

/**
 * This class is a little helper class. It helps to save xml files easily.
 * <p/>
 * With the help of the class one can create big complex xml files with the help of a
 * little code. Just implement with your class the de.rotkaeppchenproductions.utillib.xml.XmlSaver
 * interface and use the save(XmlSaver) method of this class.
 * <p/>
 * Furthermore this class provides the most important and most helpful functions at creating a xml file.
 * For example one can create a tag by using the createTextElement(..) method.<br/>
 * <b>Have a look at it and try something!</b>
 */
public class DefaultXmlSaver
{
    private static DefaultXmlSaver INSTANCE;

    /**
     * This static method returns an instance of this class.
     *
     * @return instance of this class
     */
    public static DefaultXmlSaver getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new DefaultXmlSaver();
        }
        return INSTANCE;
    }

    private XMLEventFactory eventFactory;

    private DefaultXmlSaver()
    {
        this.eventFactory = XMLEventFactory.newInstance();
    }

    /**
     * This method takes the main work by creating a xml file and calls the
     * save(XmlEventWriter) method of the XmlSaver, therewith one can concentrate
     * on the important things.
     *
     * @param saver the XmlSaver instance which want to create a xml file
     *
     * @throws XmlSaveException is thrown if something went wrong!
     */
    public void save(XmlSaver saver) throws XmlSaveException
    {
        String path = saver.getPath();
        String fileExtension = StringUtils.getFileExtension(path);

        if (fileExtension == null || !fileExtension.equals("xml"))
        {
            throw new XmlSaveException("Wrong file extension is given with file '" + path + "'!");
        }

        File file = new File(saver.getPath());  // Step 1: create File object
        if (!saver.overwritingOldFiles())
        {
            int i = 0;
            path = StringUtils.stripFileExtension(path);
            while (file.exists())
            {
                file = new File(path + "_" + (++i) + ".xml");
            }
        }
        else
        {
            if (file.exists() && !file.delete())  // Step 2: if file should be overwritten, delete the old one!
            {
                throw new XmlSaveException("Could not overwrite the older file on path: '" + file.getAbsolutePath() + "'!");
            }
        }

        try
        {
            if (!file.createNewFile()) // Step 3: create new file
            {
                throw new XmlSaveException("");
            }
        }
        catch (Exception e)
        {
            throw new XmlSaveException("Can't create a new file on path: '" + file.getAbsolutePath() + "'!");
        }

        try
        {
            XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();       // Step 4: open file
            FileOutputStream outputStream = new FileOutputStream(file);
            XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(outputStream);

            StartDocument startDocument = this.eventFactory.createStartDocument();
            XMLEvent eol = this.eventFactory.createDTD("\n");
            eventWriter.add(startDocument);
            eventWriter.add(eol);

            eventWriter.add(this.eventFactory.createStartElement("", "", saver.getRootTag()));   // Step 5: create root tag with all kinds of specified attributes
            Integer priority = saver.getPriority();
            if (priority != null)
            {
                eventWriter.add(this.eventFactory.createAttribute("priority", priority.toString()));
            }
            Attribute[] attributes = saver.getRootAttributes();
            if (attributes != null)
            {
                for (Attribute attribute : attributes)
                {
                    eventWriter.add(attribute);
                }
            }
            eventWriter.add(eol);

            saver.save(eventWriter, this.eventFactory);                          // Step 6: Die Arbeit zum Füllen der Datei abgeben

            eventWriter.add(this.eventFactory.createEndElement("", "", saver.getRootTag()));     // Step 7: Root tag schließen!
            eventWriter.add(eol);
            eventWriter.add(this.eventFactory.createEndDocument());
            eventWriter.close();                                                         //Step 8: Datei schließen
            outputStream.close();
        }
        catch (FileNotFoundException e)
        {
            throw new XmlSaveException("The file couln't be found. So something went wrong by creating it!", e);
        }
        catch (XMLStreamException e)
        {
            throw new XmlSaveException("An error occurred by writing or creating a xml element!", e);
        }
        catch (IOException e)
        {
            throw new XmlSaveException("The file couldn't be closed at the end of the creation!", e);
        }
    }
}
