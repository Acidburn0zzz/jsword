package org.crosswire.common.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * .
 * 
 * <p><table border='1' cellPadding='3' cellSpacing='0'>
 * <tr><td bgColor='white' class='TableRowColor'><font size='-7'>
 *
 * Distribution Licence:<br />
 * JSword is free software; you can redistribute it
 * and/or modify it under the terms of the GNU General Public License,
 * version 2 as published by the Free Software Foundation.<br />
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br />
 * The License is available on the internet
 * <a href='http://www.gnu.org/copyleft/gpl.html'>here</a>, or by writing to:
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston,
 * MA 02111-1307, USA<br />
 * The copyright to this program is held by it's authors.
 * </font></td></tr></table>
 * @see gnu.gpl.Licence
 * @author Joe Walker [joe at eireneh dot com]
 * @version $Id$
 */
public class IOUtil
{
    /**
     * Prevent instansiation
     */
    private IOUtil()
    {
    }

    /**
     * Unpack a zip file to a given directory
     * @param f The zip file to download
     * @param destdir The directory to unpack up
     * @throws IOException If there is an file error
     */
    public static void unpackZip(File f, URL destdir) throws IOException
    {
        // unpack the zip.
        byte[] dbuf = new byte[4096];
        ZipFile zf = new ZipFile(f);
        Enumeration entries = zf.entries();
        while (entries.hasMoreElements())
        {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            String entrypath = entry.getName();
            String filename = entrypath.substring(entrypath.lastIndexOf('/') + 1);
            URL child = NetUtil.lengthenURL(destdir, filename);
    
            OutputStream dataOut = NetUtil.getOutputStream(child);
            InputStream dataIn = zf.getInputStream(entry);
            for (int count = 0; -1 != (count = dataIn.read(dbuf)); )
            {
                dataOut.write(dbuf, 0, count);
            }
            dataOut.close();
        }
    }

    /**
     * Close the stream whatever without complaining
     * @param out The stream to close
     */
    public static void close(OutputStream out)
    {
        if (null != out)
        {
            try
            {
                out.close();
            }
            catch (IOException ex)
            {
                log.error("close", ex); //$NON-NLS-1$
            }
        }
    }

    /**
     * Close the stream whatever without complaining
     * @param in The stream to close
     */
    public static void close(InputStream in)
    {
        if (null != in)
        {
            try
            {
                in.close();
            }
            catch (IOException ex)
            {
                log.error("close", ex); //$NON-NLS-1$
            }
        }
    }

    /**
     * The log stream
     */
    private static final Logger log = Logger.getLogger(IOUtil.class);
}