package com.iware.common.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;

/**
 * @author xiaoH
 * @date 2019/8/13
 * @since 1.0
 */
public class RequestWrapper extends HttpServletRequestWrapper {

    private static final Logger LOG = LoggerFactory.getLogger(RequestWrapper.class);

    private final byte[] body;

    private static final Charset utf8 = StandardCharsets.UTF_8;

    public RequestWrapper(HttpServletRequest request) throws IOException, ServletException {
        super(request);
        String sessionStream;
        String contentType = request.getHeader("content-type");
        if (contentType.toLowerCase().contains("multipart/form-data")) {
            sessionStream = getFileBytes(request);
        } else {
            sessionStream = getBodyString(request);
        }
        body = sessionStream.getBytes(utf8);
    }

    public String getFileBytes(HttpServletRequest request) throws IOException, ServletException {

        StringBuilder sb = new StringBuilder("");
        BufferedReader reader = null;
        InputStream is = null;
        Map<String, String[]> params = request.getParameterMap();
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, String[]> entry : params.entrySet()) {

                if (entry.getKey().contains("X-TIME")) {
                    continue;
                }
                sb.append(entry.getKey());
                String[] value = entry.getValue();
                if (value.length > 0) {
                    sb.append(StringUtils.join(entry.getValue()));
                }

            }
        }

        Collection<Part> parts = request.getParts();
        if (!CollectionUtils.isEmpty(parts)) {
            for (Part part : parts) {
                if (part.getContentType() != null) {
                    is = part.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(is, utf8));
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                }
            }
        }

        return sb.toString();
    }

    /**
     * 获取请求Body
     *
     * @param request
     * @return
     */
    public String getBodyString(final ServletRequest request) {
        StringBuilder sb = new StringBuilder();

        try (InputStream inputStream = cloneInputStream(request.getInputStream());
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, utf8))) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            LOG.error("error:", e.getMessage());
        }
        return sb.toString();
    }

    /**
     * Description: 复制输入流</br>
     *
     * @param inputStream
     * @return</br>
     */
    public InputStream cloneInputStream(ServletInputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buffer)) > -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            byteArrayOutputStream.flush();
        } catch (IOException e) {
            LOG.error("error: {}", e.getMessage());
        }
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {

        final ByteArrayInputStream bais = new ByteArrayInputStream(body);

        return new ServletInputStream() {

            @Override
            public int read() throws IOException {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                //do noting
            }
        };
    }

    public String getBody() {
        return new String(body, utf8);
    }
}
