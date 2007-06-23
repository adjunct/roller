/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  The ASF licenses this file to You
 * under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.  For additional information regarding
 * copyright in this work, please see the NOTICE file in the top level
 * directory of this distribution.
 */
package org.apache.roller.weblogger.business.jpa;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.roller.weblogger.WebloggerException;
import org.apache.roller.weblogger.business.BookmarkManager;
import org.apache.roller.weblogger.business.FileManager;
import org.apache.roller.weblogger.business.PropertiesManager;
import org.apache.roller.weblogger.business.Roller;
import org.apache.roller.weblogger.business.RollerImpl;
import org.apache.roller.weblogger.business.UserManager;
import org.apache.roller.weblogger.business.WeblogManager;
import org.apache.roller.weblogger.business.runnable.ThreadManager;
import org.apache.roller.weblogger.business.pings.AutoPingManager;
import org.apache.roller.weblogger.business.pings.PingQueueManager;
import org.apache.roller.weblogger.business.pings.PingTargetManager;
import org.apache.roller.weblogger.business.plugins.PluginManager;
import org.apache.roller.weblogger.business.referrers.RefererManager;
import org.apache.roller.weblogger.business.referrers.ReferrerQueueManager;
import org.apache.roller.weblogger.business.search.IndexManager;
import org.apache.roller.weblogger.business.themes.ThemeManager;

/**
 * A JPA specific implementation of the Roller business layer.
 */
@com.google.inject.Singleton
public class JPARollerImpl extends RollerImpl {

    static final long serialVersionUID = 5256135928578074652L;

    protected static Log logger = LogFactory.getLog(JPARollerImpl.class);

    // our singleton instance
    private static JPARollerImpl me = null;

    // a persistence utility class
    protected JPAPersistenceStrategy strategy = null;
    private Roller roller = null;

    // references to the managers we maintain
    private BookmarkManager bookmarkManager = null;
    private PropertiesManager propertiesManager = null;
    private RefererManager referrerManager = null;
    private UserManager userManager = null;
    private WeblogManager weblogManager = null;
    private PingQueueManager pingQueueManager = null;
    private AutoPingManager autoPingManager = null;
    private PingTargetManager pingTargetManager = null;
    private ThreadManager threadManager = null;    
    
    /**
     * Single constructor.
     * @throws org.apache.roller.weblogger.WebloggerException on any error
     */
    @com.google.inject.Inject
    protected JPARollerImpl(
        JPAPersistenceStrategy strategy,
        AutoPingManager      autoPingManager,
        BookmarkManager      bookmarkManager,
        FileManager          fileManager,
        IndexManager         indexManager,
        PingQueueManager     pingQueueManager,
        PingTargetManager    pingTargetManager,
        PluginManager        pluginManager,
        PropertiesManager    propertiesManager,
        RefererManager       refererManager,
        ReferrerQueueManager refererQueueManager,
        ThemeManager         themeManager,
        ThreadManager        threadManager,
        UserManager          userManager,
        WeblogManager        weblogManager) throws WebloggerException {
        
        super(
            autoPingManager,
            bookmarkManager,
            fileManager,
            indexManager,
            pingQueueManager,
            pingTargetManager,
            pluginManager,
            propertiesManager,
            refererManager,
            refererQueueManager,
            themeManager,
            threadManager,
            userManager,
            weblogManager); 
        this.strategy = strategy;
    }
        
    public void initialize() {
        // no-op
    }
    
    public void flush() throws WebloggerException {
        this.strategy.flush();
    }

    
    public void release() {
        super.release();
        // tell JPA to close down
        this.strategy.release();
    }

    
    public void shutdown() {
        // do our own shutdown first
        this.release();

        // then let parent do its thing
        super.shutdown();
    }    
}
