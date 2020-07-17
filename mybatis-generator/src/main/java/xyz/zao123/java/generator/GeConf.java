package xyz.zao123.java.generator;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author gejt
 */
@Data
@NoArgsConstructor
@Accessors(chain = true,fluent = true)
public class GeConf {

    private String dbIp;
    private String dbPort;
    private String db;
    private String dbUser;
    private String dbPass;

    private String module;
    private String basePackage;
    private Set<String> tables;
    private String tablePrefix;

    private String author;

    public GeConf(String dbIp, String dbPort, String db) {
        this.dbIp = dbIp;
        this.dbPort = dbPort;
        this.db = db;
    }

    public GeConf(String dbIp, String dbPort, String db, String dbUser, String dbPass) {
        this.dbIp = dbIp;
        this.dbPort = dbPort;
        this.db = db;
        this.dbUser = dbUser;
        this.dbPass = dbPass;
    }

    public GeConf addTable(String... tables){
        if(this.tables==null){
             this.tables = new HashSet<String>();
        }
        this.tables.addAll(Arrays.asList(tables));
        return this;
    }
}
