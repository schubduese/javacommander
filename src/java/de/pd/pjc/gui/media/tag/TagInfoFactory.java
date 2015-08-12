/*
 * TagInfoFactory.
 * 
 * JavaZOOM : jlgui@javazoom.net
 *            http://www.javazoom.net
 * 
 *-----------------------------------------------------------------------
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU Library General Public License as published
 *   by the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Library General Public License for more details.
 *
 *   You should have received a copy of the GNU Library General Public
 *   License along with this program; if not, write to the Free Software
 *   Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *----------------------------------------------------------------------
 */
package de.pd.pjc.gui.media.tag;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.pd.pjc.gui.media.tag.ui.APEDialog;
import de.pd.pjc.gui.media.tag.ui.EmptyDialog;
import de.pd.pjc.gui.media.tag.ui.FlacDialog;
import de.pd.pjc.gui.media.tag.ui.MpegDialog;
import de.pd.pjc.gui.media.tag.ui.OggVorbisDialog;
import de.pd.pjc.gui.media.tag.ui.TagInfoDialog;

/**
 * This class is a factory for TagInfo and TagInfoDialog.
 * It allows to any plug custom TagIngfo parser matching to TagInfo
 * interface.
 */
public class TagInfoFactory
{
    private static Log log = LogFactory.getLog(TagInfoFactory.class);
    private static TagInfoFactory instance = null;
    private Class MpegTagInfoClass = null;
    private Class VorbisTagInfoClass = null;
    private Class APETagInfoClass = null;
    private Class FlacTagInfoClass = null;

    private TagInfoFactory()
    {
        super();
        String classname = "de.pd.pjc.gui.media.tag.MpegInfo";
        MpegTagInfoClass = getTagInfoImpl(classname);
        if (MpegTagInfoClass == null)
        {
            log.error("Error : TagInfo implementation not found in " + classname + " hierarchy");
            MpegTagInfoClass = getTagInfoImpl("javazoom.jlgui.player.amp.tag.MpegInfo");
        }
        classname = "de.pd.pjc.gui.media.tag.OggVorbisInfo";
        VorbisTagInfoClass = getTagInfoImpl(classname);
        if (VorbisTagInfoClass == null)
        {
            log.error("Error : TagInfo implementation not found in " + classname + " hierarchy");
            VorbisTagInfoClass = getTagInfoImpl("javazoom.jlgui.player.amp.tag.OggVorbisInfo");
        }
        classname = "de.pd.pjc.gui.media.tag.APEInfo";
        APETagInfoClass = getTagInfoImpl(classname);
        if (APETagInfoClass == null)
        {
            log.error("Error : TagInfo implementation not found in " + classname + " hierarchy");
            APETagInfoClass = getTagInfoImpl("javazoom.jlgui.player.amp.tag.APEInfo");
        }
        classname = "de.pd.pjc.gui.media.tag.FlacInfo";
        FlacTagInfoClass = getTagInfoImpl(classname);
        if (FlacTagInfoClass == null)
        {
            log.error("Error : TagInfo implementation not found in " + classname + " hierarchy");
            FlacTagInfoClass = getTagInfoImpl("javazoom.jlgui.player.amp.tag.FlacInfo");
        }
    }

    public static synchronized TagInfoFactory getInstance()
    {
        if (instance == null)
        {
            instance = new TagInfoFactory();
        }
        return instance;
    }

    /**
     * Return tag info from a given URL.
     *
     * @param location
     * @return TagInfo structure for given URL
     */
    public TagInfo getTagInfo(URL location)
    {
        TagInfo taginfo;
        try
        {
            taginfo = getTagInfoImplInstance(MpegTagInfoClass);
            taginfo.load(location);
        }
        catch (IOException ex)
        {
            log.debug(ex);
            taginfo = null;
        }
        catch (UnsupportedAudioFileException ex)
        {
            // Not Mpeg Format
            taginfo = null;
        }
        if (taginfo == null)
        {
            // Check Ogg Vorbis format.
            try
            {
                taginfo = getTagInfoImplInstance(VorbisTagInfoClass);
                taginfo.load(location);
            }
            catch (UnsupportedAudioFileException ex)
            {
                // Not Ogg Vorbis Format
                taginfo = null;
            }
            catch (IOException ex)
            {
                log.debug(ex);
                taginfo = null;
            }
        }
        if (taginfo == null)
        {
            // Check APE format.
            try
            {
                taginfo = getTagInfoImplInstance(APETagInfoClass);
                taginfo.load(location);
            }
            catch (UnsupportedAudioFileException ex)
            {
                // Not APE Format
                taginfo = null;
            }
            catch (IOException ex)
            {
                log.debug(ex);
                taginfo = null;
            }
        }
        if (taginfo == null)
        {
            // Check Flac format.
            try
            {
                taginfo = getTagInfoImplInstance(FlacTagInfoClass);
                taginfo.load(location);
            }
            catch (UnsupportedAudioFileException ex)
            {
                // Not Flac Format
                taginfo = null;
            }
            catch (IOException ex)
            {
                log.debug(ex);
                taginfo = null;
            }
        }
        return taginfo;
    }

    /**
     * Return tag info from a given String.
     *
     * @param location
     * @return TagInfo structure for given location
     */
    public TagInfo getTagInfo(String location)
    {
        if (false)
        {
            try
            {
                return getTagInfo(new URL(location));
            }
            catch (MalformedURLException e)
            {
                return null;
            }
        }
        else
        {
            return getTagInfo(new File(location));
        }
    }

    /**
     * Get TagInfo for given file.
     *
     * @param location
     * @return TagInfo structure for given location
     */
    public TagInfo getTagInfo(File location)
    {
        TagInfo taginfo;
        // Check Mpeg format.
        try
        {
            taginfo = getTagInfoImplInstance(MpegTagInfoClass);
            taginfo.load(location);
        }
        catch (IOException ex)
        {
            log.debug(ex);
            taginfo = null;
        }
        catch (UnsupportedAudioFileException ex)
        {
            // Not Mpeg Format
            taginfo = null;
        }
        if (taginfo == null)
        {
            // Check Ogg Vorbis format.
            try
            {
                //taginfo = new OggVorbisInfo(location);
                taginfo = getTagInfoImplInstance(VorbisTagInfoClass);
                taginfo.load(location);
            }
            catch (UnsupportedAudioFileException ex)
            {
                // Not Ogg Vorbis Format
                taginfo = null;
            }
            catch (IOException ex)
            {
                log.debug(ex);
                taginfo = null;
            }
        }
        if (taginfo == null)
        {
            // Check APE format.
            try
            {
                taginfo = getTagInfoImplInstance(APETagInfoClass);
                taginfo.load(location);
            }
            catch (UnsupportedAudioFileException ex)
            {
                // Not APE Format
                taginfo = null;
            }
            catch (IOException ex)
            {
                log.debug(ex);
                taginfo = null;
            }
        }
        if (taginfo == null)
        {
            // Check Flac format.
            try
            {
                taginfo = getTagInfoImplInstance(FlacTagInfoClass);
                taginfo.load(location);
            }
            catch (UnsupportedAudioFileException ex)
            {
                // Not Flac Format
                taginfo = null;
            }
            catch (IOException ex)
            {
                log.debug(ex);
                taginfo = null;
            }
        }
        return taginfo;
    }

    /**
     * Return dialog (graphical) to display tag info.
     *
     * @param taginfo
     * @return TagInfoDialog for given TagInfo
     */
    public TagInfoDialog getTagInfoDialog(TagInfo taginfo)
    {
        TagInfoDialog dialog;
        if (taginfo != null)
        {
            if (taginfo instanceof OggVorbisInfo)
            {
                dialog = new OggVorbisDialog(new JFrame(), "OggVorbis info", (OggVorbisInfo) taginfo);
            }
            else if (taginfo instanceof MpegInfo)
            {
                dialog = new MpegDialog(new JFrame(), "Mpeg info", (MpegInfo) taginfo);
            }
            else if (taginfo instanceof APEInfo)
            {
                dialog = new APEDialog(new JFrame(), "Ape info", (APEInfo) taginfo);
            }
            else if (taginfo instanceof FlacInfo)
            {
                dialog = new FlacDialog(new JFrame(), "Flac info", (FlacInfo) taginfo);
            }
            else
            {
                dialog = new EmptyDialog(new JFrame(), "No info", taginfo);
            }
        }
        else
        {
            dialog = new EmptyDialog(new JFrame(), "No info", null);
        }
        return dialog;
    }

    /**
     * Load and check class implementation from classname.
     *
     * @param classname
     * @return TagInfo implementation for given class name
     */
    public Class getTagInfoImpl(String classname)
    {
        Class aClass = null;
        boolean interfaceFound = false;
        if (classname != null)
        {
            try
            {
                aClass = Class.forName(classname);
                Class superClass = aClass;
                // Looking for TagInfo interface implementation.
                while (superClass != null)
                {
                    Class[] interfaces = superClass.getInterfaces();
                    for (int i = 0; i < interfaces.length; i++)
                    {
                        if ((interfaces[i].getName()).equals("de.pd.pjc.gui.media.tag.TagInfo"))
                        {
                            interfaceFound = true;
                            break;
                        }
                    }
                    if (interfaceFound) break;
                    superClass = superClass.getSuperclass();
                }
                if (interfaceFound) log.info(classname + " loaded");
                else log.info(classname + " not loaded");
            }
            catch (ClassNotFoundException e)
            {
                log.error("Error : " + classname + " : " + e.getMessage());
            }
        }
        return aClass;
    }

    /**
     * Return new instance of given class.
     *
     * @param aClass
     * @return TagInfo for given class
     */
    public TagInfo getTagInfoImplInstance(Class aClass)
    {
        TagInfo instance = null;
        if (aClass != null)
        {
            try
            {
                Class[] argsClass = new Class[] {};
                Constructor c = aClass.getConstructor(argsClass);
                instance = (TagInfo) (c.newInstance(null));
            }
            catch (Exception e)
            {
                log.error("Cannot Instanciate : " + aClass.getName() + " : " + e.getMessage());
            }
        }
        return instance;
    }
}
