import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.util.Cookie;

public class CFTokenGen {
         private Proxy CFProxy = null;
     	 private BrowserVersion UAControl = null;
     	 private String URL = null;
     	 
     	 public CFTokenGen(String URL,String UserAgent) {
     		this.UAControl = new BrowserVersion.BrowserVersionBuilder(BrowserVersion.CHROME)
		    .setUserAgent(UserAgent)
		    .build();
     		this.URL=URL;
     	 }
     	 public CFTokenGen(String URL,String UserAgent,Proxy CFProxy) 
     	 {
     	 this.CFProxy = CFProxy;
     		this.UAControl = new BrowserVersion.BrowserVersionBuilder(BrowserVersion.CHROME)
		    .setUserAgent(UserAgent)
		    .build();
     		this.URL=URL;
     	 }
         public Map<String, String> GetGoodCookies() {
    		 WebClient client=null;
    		 java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
    		 for(;;)
    		    try {
    		    	client = new WebClient(this.UAControl);
    		    	if (this.CFProxy!=null) {
    		    	ProxyConfig proxyConfig = new ProxyConfig( ((InetSocketAddress)CFProxy.address()).getHostName(),((InetSocketAddress)CFProxy.address()).getPort());
    		        client.getOptions().setProxyConfig(proxyConfig);
    		    	}
    		    	client.getOptions().setJavaScriptEnabled(true);
    		        client.getOptions().setCssEnabled(true);
    		        client.getOptions().setUseInsecureSSL(true);
    		        client.getOptions().setThrowExceptionOnFailingStatusCode(false);
    		        client.getOptions().setRedirectEnabled(true);
    		       Page htmlPage = client.getPage(this.URL);

    		      for (int i = 0; i < 40; i++) {
    		        synchronized (htmlPage) {
    		        if (client.getCookieManager().getCookie("cf_clearance")!=null)
    		        {
    		        	Map<String,String> cookies = new HashMap<>();
    		        	for (Cookie x:client.getCookieManager().getCookies())
    		        		cookies.put(x.getName(), x.getValue());
    		        	client.close();
    		        	return cookies;
    		        	
    		        }
    		          htmlPage.wait(500);
    		        }
    		      }
    		    } catch(javax.net.ssl.SSLException e) {}catch (Exception e) {e.printStackTrace();client.close();}
    		  }
}
