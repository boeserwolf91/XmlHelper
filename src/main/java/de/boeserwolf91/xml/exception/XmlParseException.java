package de.boeserwolf91.xml.exception;

/**
 * This exception is thrown if the xml-file is implemented wrong.
 */
public class XmlParseException extends XmlException
{
    public XmlParseException(String message)
    {
        super(message);
    }

    public XmlParseException(String message, Throwable t)
    {
        super(message, t);
    }
}
