/*
 * File   : $Source: /alkacon/cvs/opencms/src/org/opencms/file/Attic/CmsOrganizationalUnit.java,v $
 * Date   : $Date: 2007/01/08 14:03:04 $
 * Version: $Revision: 1.1.2.1 $
 *
 * This library is part of OpenCms -
 * the Open Source Content Mananagement System
 *
 * Copyright (c) 2005 Alkacon Software GmbH (http://www.alkacon.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * For further information about Alkacon Software GmbH, please see the
 * company website: http://www.alkacon.com
 *
 * For further information about OpenCms, please see the
 * project website: http://www.opencms.org
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.opencms.file;

import org.opencms.db.CmsDefaultUsers;
import org.opencms.main.CmsIllegalStateException;
import org.opencms.main.OpenCms;
import org.opencms.util.CmsUUID;

/**
 * An organizational unit in the OpenCms permission system.<p>
 *
 * @author Michael Moossen 
 * 
 * @version $Revision: 1.1.2.1 $
 * 
 * @since 6.5.6 
 * 
 * @see CmsUser
 * @see CmsGroup
 */
public class CmsOrganizationalUnit {

    /** The description of this organizational unit. */
    protected String m_description;

    /** The flags of this organizational unit. */
    protected int m_flags;

    /** The unique id of this organizational unit. */
    protected CmsUUID m_id;

    /** The name of this organizational unit. */
    protected String m_name;

    /** The default users for this organizational unit. */
    private CmsDefaultUsers m_defaultUsers;

    /** The parent organizational units full qualified name. */
    private String m_parentFqn;

    /**
     * Creates a new OpenCms organizational unit principal.
     * 
     * @param id the unique id of the organizational unit
     * @param parentFqn the fully qualified name of the parent organizational unit (should end with slash)
     * @param name the name of the organizational unit
     * @param description the description of the organizational unit
     * @param flags the flags of the organizational unit
     */
    public CmsOrganizationalUnit(CmsUUID id, String parentFqn, String name, String description, int flags) {

        m_id = id;
        m_parentFqn = parentFqn;
        m_name = name;
        m_description = description;
        m_flags = flags;
    }

    /**
     * Returns the new fully qualified name.<p>
     * 
     * @param prefix the prefix fully qualified name
     * @param name the name to append
     * 
     * @return the new fully qualified name
     */
    public static final String appendFqn(String prefix, String name) {

        if (prefix == null) {
            prefix = "";
        }
        return prefix + "/" + name;
    }

    /**
     * Returns the last name of the given fully qualified name.<p>
     * 
     * @param fqn the fully qualified name to get the last name from
     * 
     * @return the last name of the given fully qualified name
     */
    public static final String getLastNameFromFqn(String fqn) {

        int pos = fqn.lastIndexOf("/");
        if (pos == -1) {
            return fqn;
        }
        return fqn.substring(pos + 1);
    }

    /**
     * Returns the parent fully qualified name.<p>
     * 
     * @param fqn the fully qualified name to get the parent from
     * 
     * @return the parent fully qualified name
     */
    public static final String getParentFqn(String fqn) {

        int pos = fqn.lastIndexOf("/");
        if (pos == -1) {
            return null;
        }
        return fqn.substring(0, pos);
    }

    /**
     * Returns a new fully qualified name, composed from this organizational fully qualified name and the given name.<p> 
     * 
     * @param name the name to use
     * 
     * @return the new fully qualified name
     */
    public String appendFqn(String name) {

        return appendFqn(getFqn(), name);
    }

    /**
     * Checks if the provided organizational unit name is valid and can be used as a name.<p> 
     * 
     * An organizational unit name must not be empty or whitespace only.<p>
     * 
     * @param name the organizational unit name to check
     * 
     * @see org.opencms.security.I_CmsValidationHandler#checkOrganizationalUnitName(String)
     */
    public void checkName(String name) {

        OpenCms.getValidationHandler().checkOrganizationalUnitName(name);
    }

    /**
     * @see java.lang.Object#clone()
     */
    public Object clone() {

        return new CmsOrganizationalUnit(m_id, m_parentFqn, m_name, m_description, m_flags);
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }
        if (obj instanceof CmsOrganizationalUnit) {
            if (m_id != null) {
                return m_id.equals(((CmsOrganizationalUnit)obj).getId());
            }
        }
        return false;
    }

    /**
     * Returns the default users for this organizational unit.<p>
     * 
     * @return the default users for this organizational unit
     */
    public CmsDefaultUsers getDefaultUsers() {

        if (m_defaultUsers == null) {
            // initialize organizational unit default users and groups
            String userAdmin = appendFqn(OpenCms.getDefaultUsers().getUserAdmin());
            String userGuest = appendFqn(OpenCms.getDefaultUsers().getUserGuest());
            String userExport = appendFqn(OpenCms.getDefaultUsers().getUserExport());
            String userDeletedResource = appendFqn(OpenCms.getDefaultUsers().getUserDeletedResource());
            String groupAdministrators = appendFqn(OpenCms.getDefaultUsers().getGroupAdministrators());
            String groupProjectmanagers = appendFqn(OpenCms.getDefaultUsers().getGroupProjectmanagers());
            String groupUsers = appendFqn(OpenCms.getDefaultUsers().getGroupUsers());
            String groupGuests = appendFqn(OpenCms.getDefaultUsers().getGroupGuests());

            m_defaultUsers = new CmsDefaultUsers(
                userAdmin,
                userGuest,
                userExport,
                userDeletedResource,
                groupAdministrators,
                groupProjectmanagers,
                groupUsers,
                groupGuests);
        }
        return m_defaultUsers;
    }

    /**
     * Returns the description of this organizational unit.<p>
     *
     * @return the description of this organizational unit
     */
    public String getDescription() {

        return m_description;
    }

    /**
     * Returns the flags of this organizational unit.<p>
     *
     * The organizational unit flags are used to store special information about the 
     * organizational unit state encoded bitwise. Usually the flags int value should not 
     * be directly accessed. <p>
     * 
     * @return the flags of this organizational unit
     */
    public int getFlags() {

        return m_flags;
    }

    /**
     * Returns the fully qualified name of this organizational unit.<p>
     * 
     * @return the fully qualified name of this organizational unit
     */
    public String getFqn() {

        if (m_parentFqn == null) {
            // root ou
            return "";
        }
        // general
        return appendFqn(m_parentFqn, m_name);
    }

    /**
     * Returns the id of this organizational unit.
     *
     * @return the id of this organizational unit.
     */
    public CmsUUID getId() {

        return m_id;
    }

    /**
     * Returns the name of this organizational unit.
     *
     * @return the name of this organizational unit.
     */
    public String getName() {

        return m_name;
    }

    /**
     * Returns the full qualified name of the parent organizational unit of this organizational unit.<p>
     * 
     * @return the full qualified name of the parent organizational unit of this organizational unit
     */
    public String getParentFqn() {

        return m_parentFqn;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {

        if (m_id != null) {
            return m_id.hashCode();
        }
        return CmsUUID.getNullUUID().hashCode();
    }

    /**
     * Sets the description of this organizational unit.<p>
     * 
     * @param description the principal organizational unit to set
     */
    public void setDescription(String description) {

        if (m_parentFqn == null) {
            throw new CmsIllegalStateException(Messages.get().container(Messages.ERR_ORGUNIT_ROOT_EDITION_0));
        }
        m_description = description;
    }

    /**
     * Sets this organizational unit flags to the specified value.<p>
     *
     * The organizational unit flags are used to store special information about the 
     * organizational units state encoded bitwise. Usually the flags int value should not 
     * be directly accessed. <p>
     *
     * @param value the value to set this organizational units flags to
     */
    public void setFlags(int value) {

        if (m_parentFqn == null) {
            throw new CmsIllegalStateException(Messages.get().container(Messages.ERR_ORGUNIT_ROOT_EDITION_0));
        }
        m_flags = value;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {

        StringBuffer result = new StringBuffer();
        result.append("[Organizational Unit]");
        result.append(" fqn:");
        result.append(getFqn());
        result.append(" id:");
        result.append(m_id);
        result.append(" description:");
        result.append(m_description);
        return result.toString();
    }
}
