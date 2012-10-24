package controllers;

import java.util.Map;

import play.libs.WS;
import play.mvc.Controller;

import com.google.gson.Gson;

public class TalkController extends Controller {

	public static void talk(String escapedUri) {
		renderJSON(getTalk(escapedUri.replaceAll("---", "/")));
	}

	private static Map<String, String> getTalk(String uri) {
		return (Map<String, String>) new Gson().fromJson(WS.url(uri).get().getString(), Object.class);
	}

}
