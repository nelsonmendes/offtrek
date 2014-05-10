package offtrek.mobile.app.api;


import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.security.InvalidParameterException;
import java.util.List;

public abstract class Request{



    public static JSONObject doRequest(String url, RequestType type, List<NameValuePair> nameValuePairs) throws InvalidParameterException,
                                                                                                                    IllegalStateException,
                                                                                                                    IOException,
                                                                                                                    JSONException {


        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = null;

        //add parameters to php script
        if (type == RequestType.GET && nameValuePairs != null) {
            url += '?';
            for (int i = 0; i < nameValuePairs.size(); i++) {
                //first one doesn't need the '&' since it follows the '?'
                if (i != 0) {
                    url += "&";
                }
                url += URLEncoder.encode(nameValuePairs.get(i).getName(), "UTF-8") + "=" + URLEncoder.encode(nameValuePairs.get(i).getValue(), "UTF-8");
            }
        }

        try {
            switch (type) {
                case POST:
                    HttpPost httppost = new HttpPost(url);
                    if (nameValuePairs != null && !nameValuePairs.isEmpty())
                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));

                    response = httpclient.execute(httppost);
                    break;
                case GET:
                    Log.i("doRequest()", url);
                    HttpGet httpget = new HttpGet(url);

                    response = httpclient.execute(httpget);
                    break;
                case DELETE:
                    HttpDelete httpdelete = new HttpDelete(url);

                    response = httpclient.execute(httpdelete);
                    break;
                case PUT:
                    HttpPut httpput = new HttpPut(url);
                    if (nameValuePairs != null && !nameValuePairs.isEmpty())
                        httpput.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));

                    response = httpclient.execute(httpput);
                    break;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response == null || response.getStatusLine().getStatusCode() != 200) {
            HttpEntity responseEntity = response.getEntity();
            if(responseEntity!=null) {
                Log.i("Request.doRequest", EntityUtils.toString(responseEntity));
            }
            throw new IllegalArgumentException();
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String json = in.readLine();
        return new JSONObject(json);
    }



    public enum RequestType{
        POST,
        GET,
        PUT,
        DELETE
    }

    public enum URLs{

        API_ROOT_URL("https://paginas.fe.up.pt/~ei09144/LAPD/api/");


        private String url;

        URLs(String s){
            url = s;
        }

        public String getUrl() {
            return url;
        }
        public void setUrl(String url) {
            this.url = url;
        }



    }
}