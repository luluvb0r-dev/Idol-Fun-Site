package jp.co.idolFunSite.app.controller.site;

import java.time.LocalDate;

import jp.co.idolFunSite.domain.common.Status;
import jp.co.idolFunSite.domain.common.Visibility;
import jp.co.idolFunSite.domain.master.CallTypeMaster;
import jp.co.idolFunSite.domain.master.CallTypeMasterRepository;
import jp.co.idolFunSite.domain.release.ReleaseType;
import jp.co.idolFunSite.domain.release.SingleRelease;
import jp.co.idolFunSite.domain.release.SingleReleaseRepository;
import jp.co.idolFunSite.domain.site.Site;
import jp.co.idolFunSite.domain.site.SiteRepository;
import jp.co.idolFunSite.domain.site.SiteThemeSetting;
import jp.co.idolFunSite.domain.site.SiteThemeSettingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 追加した仕様準拠APIの統合テストです。
 */
@SpringBootTest
@Transactional
class SiteApiIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private SiteThemeSettingRepository siteThemeSettingRepository;

    @Autowired
    private SingleReleaseRepository singleReleaseRepository;

    @Autowired
    private CallTypeMasterRepository callTypeMasterRepository;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getSite_returnsThemeWrappedByCommonResponse() throws Exception {
        Site site = createFixture();

        mockMvc.perform(get("/api/v1/sites/{siteKey}", site.getSiteKey()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.siteKey").value(site.getSiteKey()))
                .andExpect(jsonPath("$.data.siteName").value("API Test Site"))
                .andExpect(jsonPath("$.data.theme.primaryColorHex").value("#F06292"))
                .andExpect(jsonPath("$.meta.version").value("v1"))
                .andExpect(jsonPath("$.errors.length()").value(0));
    }

    @Test
    void getReleases_returnsWrappedItemList() throws Exception {
        Site site = createFixture();

        mockMvc.perform(get("/api/v1/sites/{siteKey}/releases", site.getSiteKey()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.items[0].title").value("Test Single"))
                .andExpect(jsonPath("$.data.items[0].releaseType").value("SINGLE"))
                .andExpect(jsonPath("$.meta.version").value("v1"));
    }

    @Test
    void getCallTypes_returnsWrappedItemList() throws Exception {
        Site site = createFixture();

        mockMvc.perform(get("/api/v1/sites/{siteKey}/masters/call-types", site.getSiteKey()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.items[0].callTypeCode").value("CHANT"))
                .andExpect(jsonPath("$.data.items[0].callTypeLabel").value("掛け声"))
                .andExpect(jsonPath("$.meta.version").value("v1"));
    }

    private Site createFixture() {
        Site site = new Site();
        site.setSiteKey("site-api-test");
        site.setSiteName("API Test Site");
        site.setIdolName("Test Idol");
        site.setVisibility(Visibility.PUBLIC);
        site.setStatus(Status.PUBLISHED);
        site = siteRepository.save(site);

        SiteThemeSetting theme = new SiteThemeSetting();
        theme.setSite(site);
        theme.setPrimaryColorHex("#F06292");
        theme.setSecondaryColorHex("#FFFFFF");
        theme.setAccentColorHex("#FF4081");
        siteThemeSettingRepository.save(theme);

        SingleRelease release = new SingleRelease();
        release.setSite(site);
        release.setTitle("Test Single");
        release.setReleaseType(ReleaseType.SINGLE);
        release.setReleaseDate(LocalDate.of(2025, 1, 1));
        release.setDisplayOrder(1);
        singleReleaseRepository.save(release);

        CallTypeMaster callType = new CallTypeMaster();
        callType.setSite(site);
        callType.setCallTypeCode("CHANT");
        callType.setCallTypeLabel("掛け声");
        callType.setColorHex("#F06292");
        callType.setIconKey("mic");
        callType.setDisplayOrder(1);
        callType.setIsActive(true);
        callTypeMasterRepository.save(callType);

        return site;
    }
}
