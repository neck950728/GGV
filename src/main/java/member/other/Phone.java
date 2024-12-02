package member.other;

import java.util.HashMap;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Component
@PropertySource(value = "classpath:key.properties")
public class Phone {
	@Value("${api_key}")
	private String api_key;
	
	@Value("${api_secret}")
	private String api_secret;
	
	@Value("${sender_phoneNum}")
	private String sender_phoneNum;
	
	private Message coolSms;
	
	@Autowired
	public void init() {
		coolSms = new Message(api_key, api_secret);
	}
	
	public int send(String recipient_phoneNum) {
		int authNum = (int)(Math.random() * (999999 - 100000 + 1)) + 100000;
		String msg = "GGV 서비스를 이용해주셔서 감사합니다.\n인증번호는 [" + authNum + "]입니다.";
		
		HashMap<String, String> params = new HashMap<String, String>();
	    params.put("to", recipient_phoneNum);
	    params.put("from", sender_phoneNum);
	    params.put("type", "SMS");
	    params.put("text", msg);
	    // params.put("app_version", "test app 1.2"); // application name and version
	    
		try {
			JSONObject obj = (JSONObject)coolSms.send(params); // 인증번호 전송
			System.out.println(obj.toString());
	    }catch(CoolsmsException e) { // 인증번호 전송 실패
			System.out.println(e.getMessage());
			System.out.println(e.getCode());
	    }
	    
	    return authNum;
	}
}