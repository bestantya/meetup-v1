package com.bisaai.vconconnect;

import android.app.Person;
import android.text.TextUtils;
import android.util.Log;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.bisaai.vconconnect.Sha1;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;



public class Koneksi {

    private static final String TAG_XML_PULL_PARSER = "TAG XML";
//    private static String url = "https://m1.meetup-id.com/bigbluebutton/api/";
    private static String url = "https://gate.bisaai.id/m1.php/";
    private static String moda = "";
    private static String endUrl = "";
    private static String query = "";
    private static String secret = "Tag4QPCyt4DUevWbuG205PznMyBMSdPVuYcNxGna0";
    private static String J = "";


//    public static void CreateRoom(String tulisan){
//        Log.d("AWESOME", tulisan);
//    }

    public static String GenerateUrlRoom(String nama, String idCaller) {
        moda = "create";
        query = "allowStartStopRecording=false&attendeePW=751a2146460d93a9ff1da4d5b81e14b4&autoStartRecording=false" +
                "&meetingID="+idCaller+"-meeting&moderatorPW=751a2146460d93a9ff1da4d5b81e14b4" +
                "&name="+nama+"&record=false";
        endUrl = url + moda + "?"+ query +"&checksum="+Sha1.SHA1(moda + query + secret);

        return endUrl;
    }

    public static String StartMeeting(String nama, String idCaller){
        moda = "join";
        query = "fullName="+nama+"&meetingID="+idCaller+"-meeting&password=751a2146460d93a9ff1da4d5b81e14b4&redirect=true";
        endUrl = url + moda + "?"+ query +"&checksum="+Sha1.SHA1(moda + query + secret);
        return endUrl;
    }

    public static String SendInvitation( String namaTujuan, String idCaller){
        moda = "join";
        query = "fullName="+namaTujuan+"&meetingID="+idCaller+"-meeting&password=751a2146460d93a9ff1da4d5b81e14b4&redirect=true";
        endUrl = url + moda + "?"+ query +"&checksum="+Sha1.SHA1(moda + query + secret);
        return endUrl;
    }

    public static String EndMeeting(String nama, String idCaller){
        moda = "end";
        query = "meetingID="+idCaller+"-meeting&password=751a2146460d93a9ff1da4d5b81e14b4";
        endUrl = url + moda + "?"+ query +"&checksum="+Sha1.SHA1(moda + query + secret);
        return endUrl;
    }

    public static String status(String url){

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
                J = "ERROR";
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    J = response.body().string();
                    J = parseXmlUsePullParser(J);
//                    Log.d("isinya J", J );
                } else {
                    Log.e("Errr", "Error Call API");
                    J = "ERROR";
                }
            }
        });
        return J;
    }

    public static String parseXmlUsePullParser(String xmlString)
    {
        StringBuffer retBuf = new StringBuffer();
        try {
            // Create xml pull parser factory.

            XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
            // Create XmlPullParser.
            XmlPullParser xmlPullParser = parserFactory.newPullParser();
            // Create a new StringReader.
            StringReader xmlStringReader = new StringReader(xmlString);
            // Set the string reader as XmlPullParser input.
            xmlPullParser.setInput(xmlStringReader);
            // Get event type during xml parse.
            int eventType = xmlPullParser.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT) {
                // Get xml element node name.
                String nodeName = xmlPullParser.getName();

//                Log.e("di while", "masuk di kondisi while");

                if (!TextUtils.isEmpty(nodeName)) {
//                    Log.e("di while", "masuk di kondisi IF");

                    if (eventType == XmlPullParser.START_TAG) {
//                        Log.d(TAG_XML_PULL_PARSER, "Start element " + nodeName);
                        if ("returncode".equalsIgnoreCase(nodeName)) {
//                            retBuf.append(nodeName);
                            // Get xml element text value.
                            String value = xmlPullParser.nextText();
//                            Log.d(TAG_XML_PULL_PARSER, "element text : " + value);
//                            retBuf.append(" = ");
                            retBuf.append(value);

                        }

                    } else if (eventType == XmlPullParser.END_TAG) {
//                        Log.d(TAG_XML_PULL_PARSER, "End element " + nodeName);
//                        if("response".equalsIgnoreCase(nodeName))
//                        {
//                            retBuf.append("************************\r\n\r\n");
//                        }
                    }
                }
                eventType = xmlPullParser.next();
            }
        }catch(XmlPullParserException ex)
        {
            // Add error message.
            retBuf.append(ex.getMessage());
        }finally {
            return retBuf.toString();
        }
    }

}
