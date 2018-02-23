package com.jazasoft.authserver.service;

import com.jazasoft.authserver.Utility;
import com.jazasoft.authserver.dto.UrlInterceptor;
import com.jazasoft.util.YamlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UrlInterceptorService {
    private final Logger logger = LoggerFactory.getLogger(UrlInterceptorService.class);
    private Map<String, UrlInterceptor> urlInterceptorMap = new HashMap<>();

    public UrlInterceptorService() {
        String filename = Utility.getAppHome() + File.separator + "conf" + File.separator + "url-interceptor.yml";
        File file = new File(filename);
        try {
            List<UrlInterceptor> interceptors = (ArrayList<UrlInterceptor>)YamlUtils.getInstance().getProperty(file);
            if (interceptors != null && !interceptors.isEmpty()) {
                for (UrlInterceptor interceptor: interceptors) {
                    urlInterceptorMap.put(interceptor.getUrl(), interceptor);
                }
            } else {
                logger.warn("No Url Interceptor entry found in file = {}", file.getAbsolutePath());
            }

        } catch (FileNotFoundException e) {
            logger.warn("Url Interceptor file: {} not found", file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public UrlInterceptor getUrlInterceptor(String url) {
        return urlInterceptorMap.get(url);
    }
}

