package vttp.batch5.ssf.noticeboard.services;



import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.repositories.NoticeRepository;

@Service
public class NoticeService {

	@Autowired
	NoticeRepository noticeRepo;

	@Value("${post.url}")
	String url;

	// TODO: Task 3	
	// You can change the signature of this method by adding any number of parameters
	// and return any type
	public ResponseEntity<String> postToNoticeServer(Notice notice) throws Exception {

		RestTemplate restTemplate = new RestTemplate();
		List<String> categoriesList = notice.getCategories();
		JsonArrayBuilder categoryArrayBuilder = Json.createArrayBuilder();
		for (String category : categoriesList) {
			categoryArrayBuilder.add(category);

		}
		JsonArray categoryArray = categoryArrayBuilder.build();
		JsonObject noticeJson = Json.createObjectBuilder()
									.add("title",notice.getTitle())
									.add("poster",notice.getPoster())
									.add("postDate",notice.getPostDate().getTime())
									.add("categories",categoryArray)
									.add("text",notice.getText())
									.build();
		
		String noticeJsonString = noticeJson.toString();
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		 RequestEntity<String> requestEntity = RequestEntity.post(url)
                                                           .headers(headers)
                                                           .body(noticeJsonString);

		try {
			ResponseEntity<String> responseResult = restTemplate.exchange(requestEntity,String.class);
			if (responseResult.getStatusCode() == HttpStatus.CREATED) {
				System.out.println("Successful create");
				System.out.println(responseResult.getBody());
				
			}
			if (responseResult.getStatusCode() == HttpStatus.OK) {
				System.out.println("Successful ok");
				System.out.println(responseResult.getBody());
				
			}
			return responseResult;
			
		} catch (HttpClientErrorException ex) {
			System.out.println(ex.getStatusCode());
			System.out.println(ex.getResponseBodyAsString());
			InputStream is = new ByteArrayInputStream(ex.getResponseBodyAsString().getBytes());
			JsonReader reader = Json.createReader(is);
			JsonObject errorJson = reader.readObject();
			//get the error message
			String errorMessage = errorJson.getString("message");
			
			return ResponseEntity.status(ex.getStatusCode()).header("Content-Type", "application/json").body(errorMessage);
	} 


}
//only for successful responses
	public String saveSuccessfulNoticeResponse(Notice notice) throws Exception {
		String noticeResponse = this.postToNoticeServer(notice).getBody();
		String id = noticeRepo.insertNotices(noticeResponse);
		notice.setId(id);
		return id;
	}

    public void checkHealth() throws Exception {
        noticeRepo.checkHealth();
    }
}
