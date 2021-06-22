package com.holo.springboot.holoclient;

import com.holo.springboot.holoclient.utils.RSA;
import lombok.SneakyThrows;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class HoloClientApplicationTests {

    @Test
    void contextLoads() throws InvalidKeySpecException, NoSuchAlgorithmException {
        String acpt_id = "2021032600000022";
//        acpt_id = RSA.publicEncrypt(acpt_id, RSA.getPublicKey(RSA.OUT_PUBLICKEY));
//        acpt_id = "hcElOVE/jkKZZwWjjzrW29a57YePmGRy33QzHRsVIm4yNCL4R+QlWGwxNC4mDMd/aT2kwjiZ7co8+UKicPXgXpx72d274g4ab0XP4PL3I7up6buMcegXORp5aznocdpJKXJYB18AZS8NuexUP4b/PsJyYFXtiZNjalaC70hk590=";
//        acpt_id = "kS6Eehq2TmfbMlJrbHeCIR+gFgNVZgiv7uyVk5hK8kU+CF5v2YlUV+VbIyZqanBu2C+s5syso0RUhER8CjDQZiNv3vxOtEMjiwhfw5eIiDxzMk3kXwpPz10DfZQimfkvMpHu2ob27fzRw4DxNlrrj6Or96cicaZBbP/ripZQw1g=";
        acpt_id = "kS6Eehq2TmfbMlJrbHeCIR+gFgNVZgiv7uyVk5hK8kU+CF5v2YlUV+VbIyZqanBu2C+s5syso0RUhER8CjDQZiNv3vxOtEMjiwhfw5eIiDxzMk3kXwpPz10DfZQimfkvMpHu2ob27fzRw4DxNlrrj6Or96cicaZBbP/ripZQw1g=";
        System.out.println("加密后：" + acpt_id);
//HR5xSFKK6Ngmyyy6smxd5oRV2c6sEBpZOf89Km4qlfqM3w33id78LsiArLRXLEpzV4 9zRt7eq20vqGBJ0Z6odqdQFXw9WTQEDbW1G2aPrp/Nuc5T3XUzV1wykSSRYPGEN2a9uuhl5Nam0ocVSOPzuY12W8RYIOZtzTUOLdkhjw=
        String s = RSA.privateDecrypt(acpt_id, RSA.getPrivateKey(RSA.OUT_PRIVATEKEY));
        s = new String(Base64.decodeBase64(s.getBytes()));

        System.out.println("解密后：" + s);
    }
    BlockingQueue<String> task = new ArrayBlockingQueue<String>(1024*1024);


    @Test
    void blockingQueueTest() throws InterruptedException {
        BlockingQueuePrint();
        for (;;){
            task.add(UUID.randomUUID().toString());
            TimeUnit.SECONDS.sleep(2);
        }

    }


    void BlockingQueuePrint() throws InterruptedException {
        System.out.println("init BlockingQueuePrint");
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                for (;;){
                    String take = task.take();
                    System.out.println(take);
                }
            }
        }).start();
    }

    @Test
    void oom() {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < 100000; i++) {
            map.put("key" + i, "value" + i);
        }
    }

}
