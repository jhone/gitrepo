package com.redsun.platf.service.sys.impl;

/**
 * <p>Title        : com.webapp        </p>
 * <p>Description  :   get configuration from xml file such as language,theme     </p>
 * <p>Copyright    : Copyright (c) 2010</p>
 * <p>Company      : FreedomSoft       </p>
 * 
 */

/**
 * @author Dick Pan 
 * @version 1.0
 * @since   1.0
 * <p><H3>Change history</H3></p>
 * <p>2010/10/28   : Created </p>
 *
 */
import com.redsun.platf.dao.DataAccessObjectFactory;
import com.redsun.platf.entity.sys.SystemLanguage;
import com.redsun.platf.entity.sys.SystemTheme;
import com.redsun.platf.exception.ServiceException;
import com.redsun.platf.service.sys.ConfigLoaderService;
import com.redsun.platf.sys.SystemConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.*;
import java.util.Map.Entry;

@Component
public class ApplicationConfigLoader implements ConfigLoaderService {
	private static Log log = LogFactory.getLog(ApplicationConfigLoader.class);
	
	// theme node name and type
	private static final String THEME_NAME = "themes";
	private static final String THEME_TYPE = "system";
	// language node name and type
	private static final String LANGUAGE_NAME = "languages";
	private static final String LANGUAGE_TYPE = "system";
	
	private List<SystemLanguage> languages = new ArrayList<SystemLanguage>();
	private List<SystemTheme> themes = new ArrayList<SystemTheme>();
//	private List<Authority> allAuthorityList=new ArrayList<Authority>();

	@Resource//from xml
	private SystemConfiguration systemConfiguration;
	
	@Resource
	DataAccessObjectFactory dataAccess;
	
	
	/*public  ConfigLoaderService getInstance() {
		if (instance == null) {
			instance = new ApplicationConfigLoader();
			instance.initFromDb();
		}
		return instance;
	}*/

	/*
	 * destory
	 */
	@PreDestroy
	public void destoryConfig() {
		System.out.println("configurator destroy! ");
	}

	/*
	 * init 僅加載一次
	 */

	@PostConstruct
	public void initConfig() {
		log.info("[themes and language:]init configurator ok!");
		initFromDb();
	}
	
//	private void initFromXML(){
//		languages = getLanguagesObject();
//		themes =  getThemesObject();
//	}
	
	
	private void initFromDb(){
		
		languages =  dataAccess.getSystemLanguageDao().loadAll();
		
		themes =  dataAccess.getSystemThemeDao().loadAll();
		
//		allAuthorityList = dataAccess.getAuthorityDao().loadAll();
//		System.out.println("init..."+allAuthorityList);
	}

	private Document doc = null;

	private ApplicationConfigLoader() {
		try {
			SAXReader reader = new SAXReader(false);
			doc = reader.read(this.getClass().getResourceAsStream(
					"/configuration.xml"));
		} catch (DocumentException de) {
			de.printStackTrace();
			doc = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.batchcard.service.main.ConfigLoaderService#getLanguages()
	 * @Version 1.1 load from db
	 * 
	 */
	public List<SystemLanguage> getLanguagesObject() {
		Map<String, String> result = getLanguagesMap();//read language from xml file

		List<SystemLanguage> languages = new ArrayList<SystemLanguage>();

		for (Entry<String, String> k : result.entrySet()) {
			languages.add(new SystemLanguage(k.getKey(), k.getValue()));
		}
		log.info("system lanaugages list:" + languages);
		return languages;
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.batchcard.service.main.ConfigLoaderService#getLanguagesMap()
	 */
	public Map<String, String> getLanguagesMap() {
		Map<String, String> result = new HashMap<String, String>();

		Node themesNode = getThemeNode(LANGUAGE_NAME, LANGUAGE_TYPE,
				LANGUAGE_NAME);

		String xpath = "//language";
		loadElement(result, themesNode, xpath);
		return result;
	}

	protected String getSubject(Node email) {
		String subject = null;
		Node subjectNode = email.selectSingleNode("subject");
		if (subjectNode == null) {
			subject = "";
		} else {
			subject = subjectNode.getText();
		}
		return subject;
	}

	protected Node getThemeNode(String name, String type, String config) {
		String xpath = "//configuration/" + config + "[@name='" + name
				+ "' and @type='" + type + "']";
		Node theme = doc.selectSingleNode(xpath);
		if (theme == null) {
			log.warn(String
					.format("Configuaration file not found the root node with name [%s] and type [%s] !",
							name, type));
			throw new ServiceException("can't load service !");
		}
		return theme;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.batchcard.service.main.ConfigLoaderService#getThemes()
	 */
	@SuppressWarnings("unused")
	private List<SystemTheme> getThemesObject() {
		Map<String, String> result = getThemesMap();//read theme  from xml file

		List<SystemTheme> themes = new ArrayList<SystemTheme>();

		for (Entry<String, String> k : result.entrySet()) {
			themes.add(new SystemTheme(k.getKey(), k.getValue()));
		}
		log.info("system themes list:" + themes);
		return themes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.batchcard.service.main.ConfigLoaderService#getThemesMap()
	 */
	public Map<String, String> getThemesMap() {
		Map<String, String> result = new HashMap<String, String>();

		Node themesNode = getThemeNode(THEME_NAME, THEME_TYPE, THEME_NAME);

		String xpath = "//theme";
		loadElement(result, themesNode, xpath);
		return result;
	}

	@SuppressWarnings("unchecked")
	private void loadElement(Map<String, String> result, Node themesNode,
			String xpath) {

		List<Element> list = themesNode.selectNodes(xpath);
		if (list.size() == 0) {
			log.warn(String.format(
					"Configuaration file not exists sub node with name [%s] !",
					xpath));
			throw new ServiceException("can't load configuaration  !");
			// return;

		}
		Iterator<Element> iter = list.iterator();
		while (iter.hasNext()) {
			Element element = (Element) iter.next();
			String name = element.attributeValue("name");
			String value = element.attributeValue("description");
			// String text =element.getTextTrim();
			result.put(name, value);
		}
	}

	public List<SystemLanguage> getLanguages() {
		return languages;
	}

	public void setLanguages(List<SystemLanguage> languages) {
		this.languages = languages;
	}

	public List<SystemTheme> getThemes() {
		return themes;
	}

	public void setThemes(List<SystemTheme> themes) {
		this.themes = themes;
	}

	public SystemConfiguration getSystemConfiguration() {
		return systemConfiguration;
	}

	public void setSystemConfiguration(SystemConfiguration systemConfiguration) {
		this.systemConfiguration = systemConfiguration;
	}


	/**
	 * test main
	 * 
	 * @param args
	 * 
	 *            public static void main(String[] args) { ConfigLoader config =
	 *            ConfigLoader.getInstance();
	 * 
	 *            System.out.println(config.getThemes());
	 *            System.out.println(config.getLanguages());
	 * 
	 *            }//~~
	 */
}
