package cn.lmjia.market.wechat.controller;

import cn.lmjia.market.core.entity.Login;
import cn.lmjia.market.core.entity.support.ManageLevel;
import cn.lmjia.market.wechat.WechatTestBase;
import cn.lmjia.market.wechat.page.IndexPage;
import cn.lmjia.market.wechat.page.LoginPage;
import me.jiangcai.wx.model.WeixinUserDetail;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author CJ
 */
public class WechatControllerTest extends WechatTestBase {

    @Test
    public void newWechat() throws Exception {
        WeixinUserDetail detail = nextCurrentWechatAccount();

        // 使用一个陌生的微信用户 打开 /toLoginWechat 会跳转到 登录界面
        // 完成之后 则立刻跳转到主页
        // 下次再使用该帐号登录则直接来到主页

        driver.get("http://localhost/toLoginWechat");
        LoginPage loginPage = initPage(LoginPage.class);

        // 弄一个登录过来
        String rawPassword = UUID.randomUUID().toString();
        Login login = newRandomManager(rawPassword, ManageLevel.root);

        loginPage.login(login.getLoginName(), rawPassword + 1);
        loginPage.assertHaveTooltip();

        // 尝试使用正确的密码登录吧
        loginPage.login(login.getLoginName(), rawPassword);

        // 如何去公众号？
        initPage(IndexPage.class);
        assertThat(loginService.asWechat(detail.getOpenId()))
                .isNotNull();
    }

}