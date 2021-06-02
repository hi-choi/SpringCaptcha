package hi_choi.spring.mvc;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CaptchaController {

	@RequestMapping("/captcha1")
	public String captcha1() {
		return "captcha1";
	}
	
	@RequestMapping("/captcha2")
	public String captcha2() {
		return "captcha2";
	}
	
	
	@RequestMapping(value="/captcha2",method=RequestMethod.POST)
	public String captcha2ok(HttpServletRequest req) {
		
		String gCapRes = req.getParameter("g-recaptcha");
		System.out.println(gCapRes);
		
		// secretkey 관련
		// 클라이언트에서 생성한 코드를 지정한 검증용 주소를 통해 올바른 값인지 확인함
		String secretKey = "";
		String verifyURL = "https://www.google.com/recaptcha/api/siteverify";
        String params = "secret=" + secretKey + "&response=" + gCapRes;
        
        try {
        	String jsonData = "";
        	
        	// 서버 연결
        	URL url = new URL(verifyURL);
        	HttpURLConnection huconn = (HttpURLConnection) url.openConnection();
			huconn.setRequestMethod("POST");	// 서버 통신 방식 지정
			huconn.setDoInput(true);		// 서버 통신 시 입력 가능하게 함
			huconn.setDoOutput(true);		// 서버 통신 시 출력 가능하게 함
			
			// 서버로 데이터 보내기
			DataOutputStream os = new DataOutputStream(huconn.getOutputStream());
		    os.writeBytes(params);	// 보낼 데이터 설정(key)
			os.flush();		// write함수 실행(아웃풋 스트림의 경우 write 후에 바로 전송X 버퍼가 가득 차거나 flush를 호출 시 전송시작)
			os.close();		// 전송 완료 후 리소스 반환
			
			
			// 서버에서 데이터 가져옴
			InputStream is = huconn.getInputStream();
			BufferedReader br = new BufferedReader(
							new InputStreamReader(is, "UTF-8"));
			StringBuffer sb = new StringBuffer();
			
			while ((jsonData = br.readLine()) != null) {
				sb.append(jsonData);
			}
			
			// 가져온 데이터 형태를 json형태로 변환
			JSONParser parser = new JSONParser();
			Object resvObj = parser.parse(sb.toString());
			JSONObject jsonObj = (JSONObject) resvObj;

			// 즉, {"success":true|false, ...} 에서 success의 값을 출력한다.
			System.out.println( jsonObj.get("success") );	//v2
			//System.out.println( jsonObj.get("score") );	//v3
			//System.out.println( jsonObj.get("action") );  //v3


        } catch (Exception e) {
			
			e.printStackTrace();
		}


		return "captcha2";
	}
}
