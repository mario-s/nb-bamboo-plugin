package org.netbeans.modules.bamboo.glue;

/**
 * Paths to REST Resources
 * 
 * @author spindizzy
 */
public final class RestResources {
    
    public static final String JSON_PATH = ".json";
    public static final String PROJECTS = "/project" + JSON_PATH;
    public static final String PLANS = "/plan" + JSON_PATH;
    public static final String INFO = "/info" + JSON_PATH;
    public static final String RESULTS = "/result";
    public static final String QUEUE = "/queue/%s";
    public static final String RESULT = "/result/{buildKey}";
}
