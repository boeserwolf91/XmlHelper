package de.boeserwolf91.xml.installer.exception;

public class XmlException extends RuntimeException
{
    public XmlException(String message)
    {
        super(message);
    }

    public XmlException(String message, Throwable t)
    {
        super(message, t);
    }
}
