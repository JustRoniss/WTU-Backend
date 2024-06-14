package fiap.wtu_ancora.service;

import com.microsoft.playwright.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class PlaywrightService {
    private static final Logger logger = Logger.getLogger(PlaywrightService.class.getName());
    @Autowired
    private Playwright playwright;

    @Autowired
    private Browser chromiumBrowser;
    @Autowired
    private Browser webkitBrowser;


    private Page page;
    private Page pageGoogle;

    //Variáveis
    private String urlStreamYard = "https://streamyard.com/";
    private String emailFiapEsor = "fiapjrv@gmail.com";
    private String senhaFiapEsor = "fiap@123";
    private String urlGoogleEmail = "https://mail.google.com/";
    private String strCodigo;

    // Elementos
    private Locator btnLogin;
    private Locator campoEmail;
    private Locator btnLoginEmailSubmit;
    private Locator lblVerificarEmail;
    private Locator campoCodigoEmail1;
    private Locator campoCodigoEmail2;
    private Locator campoCodigoEmail3;
    private Locator campoCodigoEmail4;
    private Locator campoCodigoEmail5;
    private Locator campoCodigoEmail6;
    private Locator campoEmailGoogle;
    private Locator campoSenhaGoogle;
    private Locator bntAvancarGoogle;
    private Locator btnSimplify;
    private Locator trEmailCodigo;
    private Locator lblCodigoGoogle;
    private Locator lblCodigoGoogleAlternative;
    private Locator h1SessionLimit;
    private Locator bntSessionLimit;
    private Locator bntSessionLimitSubmit;
    private Locator btnWebnarButton;
    private Locator lblTitulo;
    private Locator lblDescricao;
    private Locator dateDia;
    private Locator dateHoras;
    private Locator dateMinutos;
    private Locator dateDuracao;
    private Locator btnCriar;

    //Parametros evento
    private String titulo = "Titulo Teste";
    private String descricao = "Descricao Teste";
    private String dia = "";
    private String startHoras = "";
    private String startMinutos = "";
    private String duracao = "";

    @Async
    public void runStreamYardAutomation() {
        try {
            runSetWebnar(chromiumBrowser);
        } catch (Exception e) {
            logger.info("Error during test execution: \" + e.getMessage()");
        }
    }

    private void runSetWebnar(Browser browser) {
        BrowserContext context = browser.newContext();
        page = context.newPage();
        pageGoogle = context.newPage();

        //Elementos StreamYard
        btnLogin = page.locator("//span[contains(text(), 'Entrar')]");
        campoEmail = page.locator("//*[@id=\"email\"]");
        btnLoginEmailSubmit = page.locator("//button[contains(@data-testid, 'LoginEmailSubmit')]");
        lblVerificarEmail = page.locator("//h1[contains(text(), 'Verifique o seu e-mail')]");
        btnWebnarButton = page.locator("//span[contains(text(),'Webinário On-Air')]");
        h1SessionLimit = page.locator("//h1[contains(text(), 'Você atingiu seu limite de sessões ativas')]");
        bntSessionLimit = page.locator("//input[contains(@class, 'RadioButton')][1]");
        bntSessionLimitSubmit = page.locator("//span[contains(text(), 'Sair desta sessão')]");
        lblTitulo = page.locator("//div[contains(@class, 'InputLabel')]//child::label[contains(text(), 'Título')]//following::input[1]");
        lblDescricao = page.locator("//div[contains(@class, 'InputLabel')]//child::label[contains(text(), 'Descrição')]//following::textarea[1]");
        dateDia = page.locator("//div[contains(@class, 'InputLabel')]//child::label[contains(text(), 'Título')]//following::input[contains(@value, 'Hoje')]");

        //Elementos Google
        campoEmailGoogle = pageGoogle.locator("//input[contains(@type, 'email')]");
        bntAvancarGoogle = pageGoogle.locator("//span[contains(text(),'Avançar')]");
        campoSenhaGoogle = pageGoogle.locator("//input[contains(@type, 'password')]");
        btnSimplify = pageGoogle.locator("//span[contains(text(),'Not now') or contains(text(), 'Não agora')]");
        trEmailCodigo = pageGoogle.locator("//tr[contains(., 'Código de login do StreamYard')][1]");
        lblCodigoGoogle = pageGoogle.locator("//span[contains(@style, 'font-size:14px;letter-spacing:1') and contains(text(), 'Aqui está o seu código')]//parent::div//following-sibling::div//child::span[contains(@style, 'line-height:30px;font-size:22px')]");
        lblCodigoGoogleAlternative = pageGoogle.locator("//span[contains(@style, 'font-size:14px;letter-spacing:1') and contains(text(), 'Aqui está o seu código')]//parent::div//parent::span//parent::div//child::span[contains(@style, 'line-height:30px;font-size:22px')]");

        //Código Email
        campoCodigoEmail1 = page.locator("//*[@aria-label=\"Número do dígito do código da senha 1\"]");
        campoCodigoEmail2 = page.locator("//*[@aria-label=\"Número do dígito do código da senha 2\"]");
        campoCodigoEmail3 = page.locator("//*[@aria-label=\"Número do dígito do código da senha 3\"]");
        campoCodigoEmail4 = page.locator("//*[@aria-label=\"Número do dígito do código da senha 4\"]");
        campoCodigoEmail5 = page.locator("//*[@aria-label=\"Número do dígito do código da senha 5\"]");
        campoCodigoEmail6 = page.locator("//*[@aria-label=\"Número do dígito do código da senha 6\"]");


        try (context) {
            page.bringToFront();
            page.navigate(urlStreamYard);
            btnLogin.click();
            Thread.sleep(2000);
            campoEmail.fill(emailFiapEsor);
            Thread.sleep(2000);
            btnLoginEmailSubmit.click();
            Thread.sleep(4000);
            if(lblVerificarEmail.isVisible()){
                pageGoogle.bringToFront();
                pageGoogle.navigate(urlGoogleEmail);
                Thread.sleep(2000);
                campoEmailGoogle.fill(emailFiapEsor);
                Thread.sleep(2000);
                bntAvancarGoogle.click();
                campoSenhaGoogle.fill(senhaFiapEsor);
                Thread.sleep(2000);
                bntAvancarGoogle.click();
                Thread.sleep(3000);
                if(btnSimplify.isVisible()){
                    btnSimplify.click();
                }
                Thread.sleep(3000);
                trEmailCodigo.click();
                if(lblCodigoGoogle.isVisible()){
                    strCodigo =  lblCodigoGoogle.textContent().trim();
                } else if (lblCodigoGoogleAlternative.isVisible()) {
                    strCodigo =  lblCodigoGoogleAlternative.textContent().trim();
                }
                strCodigo = strCodigo.replaceAll("\\D", "");
                pageGoogle.close();
                page.bringToFront();
                campoCodigoEmail1.fill(Character.toString(strCodigo.charAt(0)));
                campoCodigoEmail2.fill(Character.toString(strCodigo.charAt(1)));
                campoCodigoEmail3.fill(Character.toString(strCodigo.charAt(2)));
                campoCodigoEmail4.fill(Character.toString(strCodigo.charAt(3)));
                campoCodigoEmail5.fill(Character.toString(strCodigo.charAt(4)));
                campoCodigoEmail6.fill(Character.toString(strCodigo.charAt(5)));
            }
            if(h1SessionLimit.isVisible()){
                bntSessionLimit.check();
                bntSessionLimitSubmit.click();
            }
            btnWebnarButton.click();
            lblTitulo.fill(titulo);
            lblDescricao.fill(descricao);
            page.fill(dateDia.textContent(), dia, new Page.FillOptions().setForce(true));
            page.close();
            context.close();
            browser.close();
        } catch (Exception e) {
            logger.severe("Error during test execution: " + e.getMessage());
        } finally {
            playwright.close();
        }
    }
}