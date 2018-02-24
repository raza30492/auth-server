package com.jazasoft.authserver;

import com.jazasoft.authserver.dto.Permission;
import com.jazasoft.util.YamlUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class UTest {

    @Test
    public void saveConfig() throws IOException{
//        String filename = Utility.getAppHome() + File.separator + "conf" + File.separator + "permission.yml";
//        Map<String, Object> data = new HashMap<>();
//        List<Permission> permissions = Arrays.asList(
//                new Permission("buyer", "Buyer", "", new Date().getTime()),
//                new Permission("activity", "Activity", "", new Date().getTime())
//        );
//        data.put("tna", permissions);
//        YamlUtils.getInstance().writeProperties(filename, data);

        String str = "ROLE_admin";
        System.out.println(str.substring(5));

    }
}
