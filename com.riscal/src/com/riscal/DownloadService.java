package com.riscal;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.service.ServiceHandler;

public class DownloadService implements ServiceHandler {
	
	private final byte[] data;
    private final String filename;
    private String id;

    public DownloadService(byte[] data, String filename) {
        this.data = data;
        this.filename = filename;
        this.id = calculateId();
    }

    public String getURL() {
        return RWT.getServiceManager().getServiceHandlerUrl(getId());
    }

    private String getId() {
        return id;
    }

    private String calculateId() {
        return String.valueOf(System.currentTimeMillis()) + data.length;
    }

    public boolean register() {
        try {
            RWT.getServiceManager().registerServiceHandler(getId(), this);
            return true;
        } catch (Exception e) {
        	System.out.println("failed to register download service handler");
            return false;
        }
    }

    private boolean unregister() {
        try {
            RWT.getServiceManager().unregisterServiceHandler(getId());
            return true;
        } catch (Exception e) {
        	System.out.println("failed to unregister download service handler");
            return false;
        }
    }
    
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		try {
            response.setContentType("application/octet-stream");
            response.setContentLength(data.length);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + filename
                    + "\"");
            response.getOutputStream().write(data);
        } catch (Exception e) {
            System.out.println("failed to dispatch download");
        } finally {
            unregister();
        }
	}

}
