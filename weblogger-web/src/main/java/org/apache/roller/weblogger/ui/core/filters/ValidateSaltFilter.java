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

package org.apache.roller.weblogger.ui.core.filters;
 
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.roller.weblogger.ui.rendering.util.cache.SaltCache;
 
public class ValidateSaltFilter implements Filter  {
    private static Log log = LogFactory.getLog(ValidateSaltFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
 
		if (httpReq.getMethod().equals("POST")) {
        	String salt = (String) httpReq.getParameter("salt");
			SaltCache saltCache = SaltCache.getInstance();
			if (salt == null || saltCache.get(salt) == null || saltCache.get(salt).equals(false)) {
            	throw new ServletException("Security Violation");
			}
		}
        chain.doFilter(request, response);
    }
 
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
 
    @Override
    public void destroy() {
    }
}