package com.lutheroaks.tacoswebsite;
import com.lutheroaks.tacoswebsite.TacosWebsiteApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

@SpringBootTest
public class TacosWebsiteApplicationTest {
   
   @Test
   public void mainMethodExists() {
       TacosWebsiteApplication.main(new String[] {});
   }
}
