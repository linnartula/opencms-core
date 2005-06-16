/*
 * File   :
 * Date   : 
 * Version: 
 *
 * This library is part of OpenCms -
 * the Open Source Content Mananagement System
 *
 * Copyright (C) 2002 - 2003 Alkacon Software (http://www.alkacon.com)
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

package org.opencms.workplace.threads;

import org.opencms.file.CmsObject;
import org.opencms.main.CmsLog;
import org.opencms.report.A_CmsReportThread;
import org.opencms.validation.CmsPointerLinkValidator;

import org.apache.commons.logging.Log;

/**
 * Thread for extern link validation. <p>
 * 
 * @author Jan Baudisch (j.baudisch@alkacon.com)
 * 
 * @version $Revision: 1.1 $
 */
public class CmsPointerLinkValidatorThread extends A_CmsReportThread {
    
    /** The log object for this class. */
    private static final Log LOG = CmsLog.getLog(CmsPointerLinkValidatorThread.class);
    
    /** the CmsObject to use. */
    private CmsObject m_cms;
    
    /** reference to the HtmlImport. */
    private CmsPointerLinkValidator m_externLinkValidator;
    
    /**
     * Constructor, creates a new CmsExternLinkValidationThread.<p>
     * 
     * @param cms the current CmsObject
     */
    public CmsPointerLinkValidatorThread(CmsObject cms) {
      
        super(cms, "test test");
        initHtmlReport(cms.getRequestContext().getLocale());
        m_cms = cms;
        m_cms.getRequestContext().setUpdateSessionEnabled(false);
        m_externLinkValidator = new CmsPointerLinkValidator(getReport());
        start();
    }
    
    /**
     * @see org.opencms.report.A_CmsReportThread#getReportUpdate()
     */
    public String getReportUpdate() {
        
        return getReport().getReportUpdate();
    }   
    
    /**
     * The run method which starts the import process.<p>
     */
    public void run() {
        try {             
            // do the validation                
            m_externLinkValidator.launch(m_cms, null);         
        } catch (Exception e) {
            getReport().println(e);
            if (LOG.isErrorEnabled()) {
                LOG.error(e.getLocalizedMessage());
            }
        }
    }      
}
