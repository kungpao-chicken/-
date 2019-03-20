package com.shanke.forum.common;

import org.junit.Test;

import java.io.File;
import java.util.UUID;

public class Common {

    @Test
    public void test() {
        try {
            String[] shpath = new String[]{"/Users/wuchaojing/scp.sh", new File("/Users/wuchaojing/settings.xml").getPath()};
            Process ps = Runtime.getRuntime().exec(shpath);
            System.out.println(ps.waitFor());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testUUID() {

        System.out.println(UUID.randomUUID().toString());

    }


}
