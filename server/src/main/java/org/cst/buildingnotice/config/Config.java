package org.cst.buildingnotice.config;

public class Config {

	public final static boolean USE_INVITE = true;
	
	public final static int COUNT_PAGE_ITEM = 15;
	
	public final static int SIZE_STREAM_BUFFER = 1024;

	public final static String PATH_TEMPLATE = "/uploads/template";
	public final static String PATH_IMAGE = "/uploads/images";
	public final static String PATH_ARCHIVE = "/downloads/archive";
	public final static String PATH_ZIP = "/downloads/zip";
	
	public final static String PATTERN_NAME = "^[a-zA-Z][a-zA-Z0-9]{2,15}$";
	public final static String PATTERN_PWD = "^[a-zA-Z0-9_]{8,16}$";
	
	public final static int ROLE_NOPERM = 0;
	public final static int ROLE_BAISE = 1;
	public final static int ROLE_ADMIN = 2;
	public final static int ROLE_SUPERADMIN = 10;
	
	public final static int STATUS_INVITE_NOUSE = 0;
	public final static int STATUS_INVITE_USED = 1;
	
	public final static int STATUS_ARCHIVE_NODOWN = 0;
	public final static int STATUS_ARCHIVE_DOWNED = 1;
	public final static int STATUS_ARCHIVE_DELETE = -1; // Mapper used
}
