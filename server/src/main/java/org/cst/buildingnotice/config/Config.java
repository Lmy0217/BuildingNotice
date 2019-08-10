package org.cst.buildingnotice.config;

public class Config {
	
	public final static String ROOT_HOST = "https://www.gasfjd.cn";
	public final static String ROOT_NAME = "危房信息采集";
	public final static String NAME_SHORT = ROOT_NAME;
	public final static String NAME_SYSTEM = NAME_SHORT + "系统";
	public final static String NAME_TEAM = NAME_SHORT + "团队";

	public final static boolean USE_INVITE = true;
	
	public final static int COUNT_PAGE_ITEM = 15;
	
	public final static int LIMIT_MAX_CREATE_INVITE = 10;
	
	public final static int SIZE_STREAM_BUFFER = 1024;

	public final static String PATH_TEMPLATE = "/uploads/template";
	public final static String PATH_IMAGE = "/uploads/images";
	public final static String PATH_ARCHIVE = "/downloads/archive";
	public final static String PATH_ZIP = "/downloads/zip";
	
	public final static String PATTERN_NAME = "^[a-zA-Z][a-zA-Z0-9]{2,15}$";
	public final static String PATTERN_PWD = "^[a-zA-Z0-9_]{8,16}$";
	public final static String PATTERN_EMAIL = "^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
	
	public final static int ROLE_NOPERM = 0;
	public final static int ROLE_BAISE = 1;
	public final static int ROLE_ADMIN = 2;
	public final static int ROLE_SUPERADMIN = 10;
	
	public final static int STATUS_INVITE_NOUSE = 0;
	public final static int STATUS_INVITE_USED = 1;
	
	public final static int STATUS_ARCHIVE_NODOWN = 0;
	public final static int STATUS_ARCHIVE_DOWNED = 1;
	public final static int STATUS_ARCHIVE_DELETE = -1; // Mapper used
	
	public final static String EMAIL_SMTP_HOST = "smtp.ym.163.com";
	public final static int EMAIL_SMTP_PORT = 994;
	public final static String EMAIL_ACCOUNT = "admin@gasfjd.cn";
	public final static String EMAIL_PWD = "Q63LO!J1irSNY09g";
	public final static String EMAIL_VERIFY_URL = ROOT_HOST + "/verifyemail.html?code=";
	
	public final static long GAP_TOKEN_FALSE = 3 * 24 * 60 * 60 * 1000;
	public final static long GAP_EMAIL_SEND = 60 * 1000;
	public final static long GAP_EMAIL_VERIFY_FALSE = 3 * 60 * 60 * 1000;
}
