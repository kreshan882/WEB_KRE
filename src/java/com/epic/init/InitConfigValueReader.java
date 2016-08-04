package com.epic.init;

import com.epic.init.InitConfigValue;
import org.jpos.core.Configuration;
import org.jpos.core.SimpleConfiguration;


public class InitConfigValueReader {
	
	public static void readConfigValues() throws Exception {

            Configuration cfg = new SimpleConfiguration(InitConfigValue.SCONFIGPATH+"dbInit.int");
            InitConfigValue.DBUSERNAME                          = cfg.get("DB_USERNAME").trim();
            InitConfigValue.DBPASSWORD                          = cfg.get("DB_PASSWORD").trim();
            InitConfigValue.DBDRIVER                            = cfg.get("DB_DRIVER").trim();
            InitConfigValue.DBURL                               = cfg.get("DB_URL").trim();
            InitConfigValue.MINPOOL                             = Integer.parseInt(cfg.get("MIN_POOL").trim());
            InitConfigValue.MAXPOOL                             = Integer.parseInt(cfg.get("MAX_POOL").trim());
            InitConfigValue.MAXCON                              = Integer.parseInt(cfg.get("MAX_CON").trim());

            InitConfigValue.DBCONNECTIONTIMEOUT                 = Integer.parseInt(cfg.get("DB_CONNECTION_TIMEOUT").trim());
            InitConfigValue.DBCONEXPIRTIMEOUT                   = Integer.parseInt(cfg.get("DB_CON_EXPIR_TIMEOUT").trim());
           
            InitConfigValue.AD_URL                              = cfg.get("AD_URL").trim();
            InitConfigValue.AD_STATUS                           = Integer.parseInt(cfg.get("AD_STATUS").trim());     
            
        }
    
		
	
	
}
