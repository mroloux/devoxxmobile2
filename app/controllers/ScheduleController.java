package controllers;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;
import static com.google.common.collect.Maps.newHashMap;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import models.Period;
import models.Talk;
import play.libs.WS;
import play.mvc.Controller;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Ordering;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class ScheduleController extends Controller {

	public static void scheduleOfDay(String day) throws IOException {
		renderJSON(toJsonMap(day, getTalkGroups(day)));
	}

	protected static ImmutableListMultimap<Period, Talk> getTalkGroups(String day) throws IOException {
		ImmutableListMultimap<Period, Talk> talkGroups = Multimaps.index(sort(getTalks(day)), new Function<Talk, Period>() {
			@Override
			public Period apply(Talk talk) {
				return new Period(talk.fromTime, talk.toTime);
			}
		});
		return talkGroups;
	}

	protected static Map toJsonMap(String day, ImmutableListMultimap<Period, Talk> talkGroups) {
		Map result = newHashMap();
		result.put("day", day);
		result.put("talkGroups", toJson(talkGroups));
		return result;
	}

	private static JsonElement toJson(ImmutableListMultimap<Period, Talk> talkGroups) {
		JsonArray result = new JsonArray();
		for (Entry<Period, Collection<Talk>> entry : talkGroups.asMap().entrySet()) {
			result.add(new Gson().toJsonTree(entry));
		}
		return result;
	}

	private static Iterable<Talk> sort(List<Talk> talks) {
		return Ordering.from(new Comparator<Talk>() {
			@Override
			public int compare(Talk talk1, Talk talk2) {
				return talk1.fromTime.compareTo(talk2.fromTime);
			}
		}).sortedCopy(talks);
	}

	private static List<Talk> getTalks(String day) throws IOException {
		List<Map<String, Object>> talks = new Gson().fromJson(WS.url("https://cfp.devoxx.com/rest/v1/events/7/schedule/day/%s", day).get().getString(), List.class);
		return validTalks(transform(talks, new Function<Map<String, Object>, Talk>() {
			@Override
			public Talk apply(Map<String, Object> talkAsMap) {
				return new Talk(talkAsMap);
			}
		}));
	}

	private static List<Talk> validTalks(List<Talk> talks) {
		return newArrayList(filter(talks, new Predicate<Talk>() {
			@Override
			public boolean apply(Talk talk) {
				return isNotBlank(talk.title);
			}
		}));
	}

}
