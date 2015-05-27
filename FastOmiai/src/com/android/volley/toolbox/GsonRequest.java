package com.android.volley.toolbox;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class GsonRequest<T> extends Request<T> {
    private final Gson mGson;
    private final Class<T> mClazz;
    private final Response.Listener<T> mListener;

    public GsonRequest(int method,
                       String url,
                       Class<T> clazz,
                       Response.Listener<T> listener,
                       Response.ErrorListener errorListener,
                       boolean needDecode) {
        super(method, url, errorListener);
        mClazz = clazz;
        mListener = listener;

        if (needDecode) {
            mGson = new GsonBuilder().registerTypeAdapter(String.class, new StringDeserializer()).create();
        } else {
            mGson = new GsonBuilder().create();
        }
    }

    public GsonRequest(String url,
                       Class<T> clazz,
                       Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {
        this(Method.GET, url, clazz, listener, errorListener, true);
    }

    public GsonRequest(String url,
                       Class<T> clazz,
                       Response.Listener<T> listener,
                       Response.ErrorListener errorListener,
                       boolean needDecode) {
        this(Method.GET, url, clazz, listener, errorListener, needDecode);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        final String json = new String(response.data);
        return Response.success(mGson.fromJson(json, mClazz),
                HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    /**
     * decode the encoded String
     */
    private class StringDeserializer implements JsonDeserializer<String> {
        @Override
        public String deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            try {
                return URLDecoder.decode(jsonElement.getAsJsonPrimitive().getAsString(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        }
    }
}
