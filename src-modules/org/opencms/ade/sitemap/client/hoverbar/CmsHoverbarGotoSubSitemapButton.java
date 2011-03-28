/*
 * File   : $Source: /alkacon/cvs/opencms/src-modules/org/opencms/ade/sitemap/client/hoverbar/Attic/CmsHoverbarGotoSubSitemapButton.java,v $
 * Date   : $Date: 2011/03/28 09:57:07 $
 * Version: $Revision: 1.9 $
 *
 * This library is part of OpenCms -
 * the Open Source Content Management System
 *
 * Copyright (C) 2002 - 2009 Alkacon Software (http://www.alkacon.com)
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

package org.opencms.ade.sitemap.client.hoverbar;

import org.opencms.ade.sitemap.client.Messages;
import org.opencms.ade.sitemap.client.control.CmsSitemapController;
import org.opencms.ade.sitemap.client.ui.css.I_CmsImageBundle;
import org.opencms.ade.sitemap.shared.CmsClientSitemapEntry;
import org.opencms.gwt.client.CmsCoreProvider;
import org.opencms.gwt.client.ui.CmsPushButton;
import org.opencms.gwt.client.ui.I_CmsButton.ButtonStyle;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

/**
 * A button for navigating to a sub-sitemap.<p>
 * 
 * @author Georg Westenberger
 * 
 * @version $Revision: 1.9 $
 * 
 * @since 8.0.0
 */
public class CmsHoverbarGotoSubSitemapButton extends CmsPushButton {

    /**
     * Constructor.<p>
     * 
     * @param hoverbar the hoverbar 
     */
    public CmsHoverbarGotoSubSitemapButton(final CmsSitemapHoverbar hoverbar) {

        setImageClass(I_CmsImageBundle.INSTANCE.buttonCss().hoverbarGotoSub());
        setTitle(Messages.get().key(Messages.GUI_HOVERBAR_GOTO_SUB_0));
        setButtonStyle(ButtonStyle.TRANSPARENT);
        setVisible(false);
        addClickHandler(new ClickHandler() {

            /**
             * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
             */
            public void onClick(ClickEvent event) {

                hoverbar.hide();

                String sitePath = hoverbar.getSitePath();
                if (!sitePath.endsWith("/")) {
                    sitePath += "/";
                }
                CmsSitemapController controller = hoverbar.getController();
                String sitemapLocation = CmsCoreProvider.get().getUri() + "?path=" + sitePath;
                controller.leaveEditor(sitemapLocation);
            }
        });
        hoverbar.addShowHandler(new I_CmsHoverbarShowHandler() {

            /**
             * @see org.opencms.ade.sitemap.client.hoverbar.I_CmsHoverbarShowHandler#onShow(org.opencms.ade.sitemap.client.hoverbar.CmsHoverbarShowEvent)
             */
            public void onShow(CmsHoverbarShowEvent event) {

                String sitePath = hoverbar.getSitePath();
                CmsSitemapController controller = hoverbar.getController();
                CmsClientSitemapEntry entry = controller.getEntry(sitePath);
                setVisible((entry != null) && entry.isSubSitemapType() && !controller.isRoot(sitePath));
            }
        });
    }

}