package flumptabot.yee.audio;

import java.io.IOException;
import java.util.List;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchResult;

public class GoogleSearch {

	public static YouTube youtube;
	public static YouTube.Search.List youtubeSearcher;
	public static YouTube.Search.List youtubeSearcher2;
	
	public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	public static final JsonFactory JSON_FACTORY = new JacksonFactory();
	
	public static void init() throws IOException{
		youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
            @Override public void initialize(HttpRequest request) throws IOException {}
        }).setApplicationName("LaxBot").build();
		youtubeSearcher = youtube.search().list("id");
		youtubeSearcher.setKey("Insert API key here");
		youtubeSearcher.setType("video");
		youtubeSearcher.setFields("items(id/videoId)");
		youtubeSearcher.setMaxResults(1L);
		
		youtubeSearcher2 = youtube.search().list("id,snippet");
		youtubeSearcher2.setKey("Insert API key here");
		youtubeSearcher2.setType("video");
		youtubeSearcher2.setFields("items(id/videoId,snippet/title,snippet/channelTitle)");
		youtubeSearcher2.setMaxResults(10L);
	}

	public static String searchOne(String keywords) throws IOException{
		youtubeSearcher.setQ(keywords);
		List<SearchResult> searchResultList = youtubeSearcher.execute().getItems();
		if(searchResultList.size() >= 1){
			SearchResult r = searchResultList.get(0);
			ResourceId rId = r.getId();
			return "https://www.youtube.com/watch?v="+rId.getVideoId();
		}
		return null;
	}

	public static List<SearchResult> searchList(String keywords) throws IOException{
		youtubeSearcher2.setQ(keywords);
		return youtubeSearcher2.execute().getItems();
	}
	
}
