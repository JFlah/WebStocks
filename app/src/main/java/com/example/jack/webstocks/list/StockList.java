package com.example.jack.webstocks.list;

import android.os.AsyncTask;
import android.util.Log;

import com.example.jack.webstocks.http.StockHttp;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by Jack on 1/8/2015.
 */
public class StockList extends AsyncTask<Void, Void, List<String>> {

    public static final String USER_AGENT = "Chrome";

    protected List<String> doInBackground(Void... params) {
        return scrapeStocks();
    }

    public String makeRequest(String url) throws IOException {
//        HttpClient httpClient = HttpClients.createDefault();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        request.addHeader("User-Agent", USER_AGENT);
        HttpResponse response = httpClient.execute(request);
        String responseString = EntityUtils.toString(response.getEntity());
        return responseString;
    }

    public String html2text(String html) { return Jsoup.parse(html).text(); }

    public List<String> scrapeStocks() {
        String url = "http://blogs.barrons.com/stockstowatchtoday/";

        Log.d("SL-ss", "Hello World.");

        String response = "";
        try {
            response = makeRequest(url);

            Log.d("SL-ss", "Response: " + response);

            String regex = "\\([A-Z]+\\)";
            String respText = html2text(response);
            Pattern stockPattern = Pattern.compile(regex);
            Matcher matcher = stockPattern.matcher(respText);
            List<String> result = new ArrayList<String>();
            while (matcher.find()) {
                result.add(matcher.group(0));
            }
            Log.d("SL-ss", "Result: " + result.toString());
            return result;
//  TODO: KEVIN: I believe the stuff below will go in StockHttp (other Async) right?

//            try {
//                JSONObject json = StockHttp.queryYahoo(result);
//                StockHttp.processJSON(json);
//            } catch (IOException io) {
//                io.printStackTrace();
//            }
        }catch (IOException io) {
            io.printStackTrace();
        }
        return new ArrayList<String>();
    }

}
