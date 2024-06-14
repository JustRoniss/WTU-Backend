package fiap.wtu_ancora.config;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlaywrightConfig {

    @Bean(destroyMethod = "close")
    public Playwright playwright() {
        return Playwright.create();
    }

    @Bean(destroyMethod = "close")
    public Browser chromiumBrowser(Playwright playwright) {
        return playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)
        );
    }

    @Bean(destroyMethod = "close")
    public Browser firefoxBrowser(Playwright playwright) {
        return playwright.firefox().launch(new BrowserType.LaunchOptions()
                .setHeadless(true)

        );
    }

    @Bean(destroyMethod = "close")
    public Browser webkitBrowser(Playwright playwright) {
        return playwright.webkit().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)
        );
    }
}