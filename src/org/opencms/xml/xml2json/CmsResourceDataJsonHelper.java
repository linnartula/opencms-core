/*
 * This library is part of OpenCms -
 * the Open Source Content Management System
 *
 * Copyright (c) Alkacon Software GmbH & Co. KG (http://www.alkacon.com)
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
 * For further information about Alkacon Software, please see the
 * company website: http://www.alkacon.com
 *
 * For further information about OpenCms, please see the
 * project website: http://www.opencms.org
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.opencms.xml.xml2json;

import org.opencms.file.CmsObject;
import org.opencms.file.CmsProperty;
import org.opencms.file.CmsResource;
import org.opencms.json.JSONException;
import org.opencms.json.JSONObject;
import org.opencms.main.CmsException;
import org.opencms.main.OpenCms;

import java.util.List;

/**
 * Helper class for formatting resource data as JSON.
 */
public class CmsResourceDataJsonHelper {

    /** The CMS context. */
    private CmsObject m_cms;

    /** The resource. */
    private CmsResource m_resource;

    /**
     * Creates a new instance.
     *
     * @param cms the CMS context
     * @param resource the resource
     */
    public CmsResourceDataJsonHelper(CmsObject cms, CmsResource resource) {

        m_cms = cms;
        m_resource = resource;
    }

    /**
     * Adds path and link fields for the resource to the given JSON object.
     *
     * @param json the JSON object to add the fields to
     *
     * @throws JSONException if something goes wrong
     */
    public void addPathAndLink(JSONObject json) throws JSONException {

        json.put("path", m_resource.getRootPath());
        json.put("link", OpenCms.getLinkManager().substituteLinkForUnknownTarget(m_cms, m_cms.getSitePath(m_resource)));

    }

    /**
     * Creates a JSON object with the attributes of the resource.
     *
     * @return the JSON for the attributes
     * @throws JSONException if something goes wrong
     */
    public JSONObject attributes() throws JSONException {

        JSONObject attributes = new JSONObject();
        attributes.put("type", OpenCms.getResourceManager().getResourceType(m_resource).getTypeName());
        attributes.put("lastModified", Long.valueOf(m_resource.getDateLastModified()));
        return attributes;
    }

    /**
     * Creates a JSON object with the properties of the resource.
     *
     * @return the JSON object
     * @throws CmsException if something goes wrong
     * @throws JSONException if something goes wrong
     */
    public JSONObject properties() throws CmsException, JSONException {

        List<CmsProperty> props = m_cms.readPropertyObjects(m_resource, false);
        JSONObject propJson = new JSONObject(true);
        for (CmsProperty prop : props) {
            propJson.put(prop.getName(), prop.getValue());
        }
        return propJson;
    }

}
