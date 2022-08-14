package com.multi.datasource.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 这里切换读/写模式
 * 原理是利用ThreadLocal保存当前线程是否处于读模式（通过开始READ_ONLY注解在开始操作前设置模式为读模式，
 * 操作结束后清除该数据，避免内存泄漏，同时也为了后续在该线程进行写操作时任然为读模式
 *
 * @author zxliuyu
 */
@Slf4j
public class DbContextHolder {

    public static final String WRITE = "write";
    public static final String READ = "read";

    private static ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setDbType(String dbType) {
        if (StringUtils.isBlank(dbType)) {
            log.error("DbContextHolder dbType is null");
        }
        contextHolder.set(dbType);
    }

    public static String getDbType() {
        return StringUtils.isBlank(contextHolder.get()) ? WRITE : contextHolder.get();
    }

    public static void clearDbType() {
        contextHolder.remove();
    }
}