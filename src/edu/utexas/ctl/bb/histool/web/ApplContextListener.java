/*
 * Created on Nov 19, 2004

 */
package edu.utexas.ctl.bb.histool.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.PropertyConfigurator;

/**
 * @author Sejal Patel
 */
/**
 * Initializes the logging api for the application
 */
public class ApplContextListener implements ServletContextListener {
    
    public void contextInitialized(ServletContextEvent event) {
        String prefix =  event.getServletContext().getRealPath("/");
        String file = event.getServletContext().getInitParameter("log4j-init-file");
        
        if(file != null) {
            PropertyConfigurator.configure(prefix+file);
        }
    }

    public void contextDestroyed(ServletContextEvent arg0) {
    }

}
