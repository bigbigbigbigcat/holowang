package com.holo.springboot.holoclient.action;

import com.holo.springboot.holoclient.beans.OSVConfig;
import com.holo.springboot.holoclient.beans.ProtoVideoQryForm;
import com.holo.springboot.holoclient.utils.Encoder;
import com.holo.springboot.holoclient.utils.RSA;
import io.swagger.annotations.Api;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(tags = "OSV测试类")
public class OsvController {
    private final RestTemplate restTemplate;

    public OsvController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @RequestMapping(value = "/v2/getOSVConfig.json",method = RequestMethod.POST)
    @ResponseBody
    public String getOSVConfig(){
        OSVConfig osvObject = restTemplate.getForObject("http://10.23.6.54:81/obm-osv/v2/getOSVConfig.json", OSVConfig.class);
        System.out.println(osvObject.toString());
        return osvObject.toString();
    }

    @RequestMapping(value = "/v2/getVideoStatementFile.json",method = RequestMethod.POST)
    public String getVideoStatementFile(HttpServletRequest request, String state_content) throws IOException {
//        Map<String,String> paramMap = new HashMap<String, String>();
//        paramMap.put("state_content",state_content);
        ResponseEntity<Resource> forEntity = restTemplate.postForEntity("http://10.23.6.54:81/obm-osv/v2/getVideoStatementFile.json?state_content={1}",request, Resource.class,state_content);
        if (forEntity.getStatusCode().equals(HttpStatus.OK)) {
            // ...
            try {

                InputStream in = forEntity.getBody().getInputStream();

                File file = new File("d://TEST.mp3");
                file.getParentFile().mkdirs();
                FileOutputStream fileout = new FileOutputStream(file);
                /**
                 * 根据实际运行效果 设置缓冲区大小
                 */
                byte[] buffer = new byte[1024];
                int ch = 0;
                while ((ch = in.read(buffer)) != -1) {
                    fileout.write(buffer, 0, ch);
                }
                in.close();
                fileout.flush();
                fileout.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        return "";
    }


    @RequestMapping(value = "/v2/getVideoStatement.json",method = RequestMethod.POST)
    public String getVideoStatement(HttpServletRequest request, String acpt_id,String business_kind) throws InvalidKeySpecException, NoSuchAlgorithmException {
//        Map<String,String> paramMap = new HashMap<String, String>();
//        paramMap.put("state_content",state_content);
        // 使用公钥进行加密
        String acptIdEncrypt = RSA.publicEncrypt(acpt_id, RSA.getPublicKey(RSA.OUT_PUBLICKEY));
        String business_kindEncrypt = RSA.publicEncrypt(business_kind, RSA.getPublicKey(RSA.OUT_PUBLICKEY));

        Object forEntity = restTemplate.getForObject("http://10.23.6.54:81/obm-osv/v2/getVideoStatement.json?acpt_id={1}&business_kind={2}", Object.class, Encoder.encodeURL(acptIdEncrypt),Encoder.encodeURL(business_kindEncrypt));

        return forEntity.toString();

    }

    @RequestMapping(value = "/v2/protoVideoQry.json",method = RequestMethod.POST)
    @ResponseBody
    public String protoVideoQry(ProtoVideoQryForm pvq) throws InvalidKeySpecException, NoSuchAlgorithmException {
        Map<String,String> paramMap = new HashMap<String, String>();
        String acptIdEncrypt = RSA.publicEncrypt(pvq.getAcpt_id(), RSA.getPublicKey(RSA.OUT_PUBLICKEY));
        String business_kindEncrypt = RSA.publicEncrypt(pvq.getBusiness_kind(), RSA.getPublicKey(RSA.OUT_PUBLICKEY));

//        paramMap.put("business_kind","0010");
//        paramMap.put("acpt_id","2021033000000154");
        paramMap.put("business_kind",Encoder.encodeURL(business_kindEncrypt));
        paramMap.put("acpt_id",Encoder.encodeURL(acptIdEncrypt));

        ResponseEntity<String> responseEntity =  restTemplate.getForEntity("http://10.23.6.54:81/obm-osv/v2/protoVideoQry.json?business_kind={business_kind}&acpt_id={acpt_id}", String.class,paramMap);
//        String osvObject = restTemplate.getForObject("http://10.23.6.54:81/obm-osv/v2/protoVideoQry.json", String.class);
//        System.out.println(osvObject);
        return responseEntity.getBody();
    }


    @RequestMapping(value = "/v2/getSoundRecognize.json",method = RequestMethod.POST)
    @ResponseBody
    public String getSoundRecognize(HttpServletRequest request, String file_data) throws InvalidKeySpecException, NoSuchAlgorithmException {
        Map<String,String> paramMap = new HashMap<String, String>();
        String file_dataEncrypt = RSA.publicEncrypt(testInputStreamBase64().toString(), RSA.getPublicKey(RSA.OUT_PUBLICKEY));

//        paramMap.put("business_kind","0010");
//        paramMap.put("acpt_id","2021033000000154");
//        paramMap.put("file_data",Encoder.encodeURL(testInputStreamBase64().toString()));
//        paramMap.put("file_data","123456");
//        SoundRecognize sr = new SoundRecognize();
//        sr.setFile_data(testInputStreamBase64().toString());
//        sr.setFile_data("123456");

//        String responseEntity =  restTemplate.getForObject("http://10.23.6.54:81/obm-osv/v2/getSoundRecognize.json?file_data={file_data}",String.class, paramMap);
//        ResponseEntity<String> responseEntity1 =  restTemplate.postForEntity("http://10.23.6.54:81/obm-osv/v2/getSoundRecognize.json?file_data={file_data}",request,String.class, paramMap);
//        String osvObject = restTemplate.getForObject("http://10.23.6.54:81/obm-osv/v2/protoVideoQry.json", String.class);
//        System.out.println(osvObject);
//        return responseEntity1.getBody();

        System.out.println(Encoder.encodeURL(file_dataEncrypt));

        return "";
    }


    /**
     * 本地测试时使用。
     * @return
     */
    public static StringBuffer testInputStreamBase64(){
        InputStream in = null;
        try{
            StringBuffer sb = new StringBuffer();
            in = new BufferedInputStream(new FileInputStream("D:\\20200325_st_16k.txt"));
            byte [] buf = new byte[1024];
            int bytesRead = in.read(buf);
            while(bytesRead != -1)
            {
                for(int i=0;i<bytesRead;i++) {
                    sb.append((char)buf[i]);
                }
                bytesRead = in.read(buf);
            }
            return sb;
        }catch (IOException e)
        {
            e.printStackTrace();
        }finally{
            try{
                if(in != null){
                    in.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return new StringBuffer();
    }


}
