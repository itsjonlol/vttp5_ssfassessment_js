package vttp.batch5.ssf.noticeboard.repositories;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.batch5.ssf.noticeboard.constant.ConstantVar;

@Repository
public class NoticeRepository {

	@Autowired
    @Qualifier("notice")
	private RedisTemplate<String,Object> template;

	// TODO: Task 4
	// You can change the signature of this method by adding any number of parameters
	// and return any type
	// 
	/*
	 * Write the redis-cli command that you use in this method in the comment. 
	 * For example if this method deletes a field from a hash, then write the following
	 * redis-cli command 
	 * 	hdel myhashmap a_key
	 *
	 *
	 */
	public String insertNotices(String noticeResponse) {
		
		InputStream is = new ByteArrayInputStream(noticeResponse.getBytes());
        JsonReader reader = Json.createReader(is);
        JsonObject noticeResponseJson = reader.readObject();
		JsonObject responseJsonObject = Json.createObjectBuilder()
											.add("id",noticeResponseJson.getString("id"))
											.add("timestamp",noticeResponseJson.getJsonNumber("timestamp").longValue())
											.build();

											template.opsForHash().put(ConstantVar.redisKey,noticeResponseJson.getString("id"), responseJsonObject.toString());
		//return id
		return noticeResponseJson.getString("id");
		//to delete
		//hdel noticesresponses <id>
	}


}
