package org.crosswire.common.config.swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;

import org.crosswire.common.config.Choice;
import org.crosswire.common.util.Convert;

/**
 * A StringArrayField allows editing of an array of Strings in a JList.
 * It allows the user to specify additional classes that extend the
 * functionality of the program.
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
public class PathField extends JPanel implements Field
{
    /**
     * Create a PropertyHashtableField for editing String arrays.
     */
    public PathField()
    {
        JPanel buttons = new JPanel(new FlowLayout());

        list.setFont(new Font("Monospaced", Font.PLAIN, 12)); //$NON-NLS-1$
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // list.setPreferredScrollableViewportSize(new Dimension(30, 100));

        scroll.setViewportView(list);

        buttons.add(add);
        buttons.add(remove);
        buttons.add(update);

        add.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                addEntry();
            }
        });

        remove.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                removeEntry();
            }
        });

        update.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                updateEntry();
            }
        });

        Border title = BorderFactory.createTitledBorder(Msg.PATH_EDITOR.toString());
        Border pad = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        setBorder(BorderFactory.createCompoundBorder(title, pad));

        setLayout(new BorderLayout());
        add(BorderLayout.CENTER, scroll);
        add(BorderLayout.SOUTH, buttons);
    }

    /**
     * Some fields will need some extra info to display properly
     * like the options in an options field. FieldMap calls this
     * method with options provided by the choice.
     * @param param The options provided by the Choice
     */
    public void setChoice(Choice param)
    {
    }

    /**
     * Return a string version of the current value
     * @return The current value
     */
    public String getValue()
    {
        return Convert.stringArray2String(getArray(), File.pathSeparator);
    }

    /**
     * Return the actual Hashtable being edited
     * @return The current value
     */
    public String[] getArray()
    {
        String[] retcode = new String[model.getSize()];
        for (int i=0; i<retcode.length; i++)
        {
            retcode[i] = (String) model.getElementAt(i);
        }

        return retcode;
    }

    /**
     * Set the current value using a string
     * @param value The new text
     */
    public void setValue(String value)
    {
        setArray(Convert.string2StringArray(value, File.pathSeparator));
    }

    /**
     * Set the current value using a hashtable
     * @param value The new text
     */
    public void setArray(String[] value)
    {
        model = new DefaultComboBoxModel(value);
        list.setModel(model);
    }

    /**
     * Get the component for the JConfigure dialog.
     * In our case that is <code>this</code>
     * @return The editing Compoenent
     */
    public JComponent getComponent()
    {
        return this;
    }

    /**
     * Pop up a dialog to allow editing of a new value
     */
    public void addEntry()
    {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            String path = chooser.getSelectedFile().getPath();
            model.addElement(path);
        }
    }

    /**
     * Pop up a dialog to allow editing of a current value
     */
    public void updateEntry()
    {
        JFileChooser chooser = new JFileChooser(currentValue());
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            String path = chooser.getSelectedFile().getPath();

            model.removeElement(currentValue());
            model.addElement(path);
        }
    }

    /**
     * Delete the current value in the hashtable
     */
    public void removeEntry()
    {
        model.removeElement(currentValue());
    }

    /**
     * What is the currently selected value?
     * @return The currently selected value
     */
    private final String currentValue()
    {
        return (String) model.getElementAt(list.getSelectedIndex());
    }

    /**
     * The TableModel that points the JTable at the Hashtable
     */
    private DefaultComboBoxModel model = new DefaultComboBoxModel();

    /**
     * The Table - displays the Hashtble
     */
    private JList list = new JList(model);

    /**
     * The Scroller for the JTable
     */
    private JScrollPane scroll = new JScrollPane();

    /**
     * Button bar: add
     */
    private JButton add = new JButton(Msg.ADD.toString());

    /**
     * Button bar: remove
     */
    private JButton remove = new JButton(Msg.REMOVE.toString());

    /**
     * Button bar: update
     */
    private JButton update = new JButton(Msg.UPDATE.toString());
}