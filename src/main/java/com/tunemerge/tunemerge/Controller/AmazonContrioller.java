package com.tunemerge.tunemerge.Controller;

import com.tunemerge.tunemerge.Service.OAuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class AmazonContrioller {
    @Autowired
    OAuthUtil oAuthUtil;
    @GetMapping("/loginAmazonMusic")
    public RedirectView login() {
        String oAuth=OAuthUtil.getAmazonAuthURL();
        return new RedirectView(oAuth);
    }
    @GetMapping("/amazonToken")
    public void token(@RequestParam("code") String code) {
        oAuthUtil.getAccessToken(code);
    }
}

