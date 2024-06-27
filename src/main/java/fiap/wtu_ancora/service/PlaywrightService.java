package fiap.wtu_ancora.service;

import com.microsoft.playwright.*;
import fiap.wtu_ancora.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;
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

    private String Iframe;
    private Page page;
    private Page pageGoogle;

    //Variáveis
    private String urlStreamYard = "https://streamyard.com/";
    private String emailFiapEsor = "tatapay729@devncie.com";
    private String senhaFiapEsor = "";
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
    private Locator btnContinuar;
    private Locator btnAceitarCookie;
    private Locator dateDiaContainer;
    private Locator dateHoras;
    private Locator dateMinutos;
    private Locator dateDuracao;
    private Locator lblMonthYear;
    private Locator btnNextMonth;
    private Locator btnPreviousMonth;
    private Locator dayButtons;
    private Locator btnContinuarWebNar;
    private Locator btnCriar;
    private Locator btnCompartilharWebNar;
    private Locator btnInconporar;
    private Locator txtAreaIframe;

    //Parametros evento
    private String duracao = "";

    @Async
    public String runStreamYardAutomation(String titulo, String descricao, LocalDateTime startDateTime, int durationMinutes) {
        try {
            Iframe = runSetWebnar(chromiumBrowser, startDateTime,  titulo, descricao, durationMinutes);
        } catch (Exception e) {
            logger.info("Error during test execution: \" " + e.getMessage());
        }
        return Iframe;
    }

    private String runSetWebnar(Browser browser, LocalDateTime startDateTime, String titulo, String descricao, int durationMinutes) {
        duracao = String.valueOf(durationMinutes);
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
        btnContinuar = page.locator("//span[contains(text(), 'Continuar')]");
        btnAceitarCookie = page.locator("//button[contains(@class, 'osano-cm-accept osano-cm-buttons__button osano-cm-button osano-cm-button--type_accept')]");
        btnCriar = page.locator("//span[contains(text(), 'Criar')]");
        btnContinuarWebNar = page.locator("//span[contains(text(), 'Continuar') and contains(@class, 'Button__InnerSpan-sc-1ewvxrb-1 kZLLcK')]");
        btnCompartilharWebNar = page.locator("//span[contains(text(), 'Compartilhar')]");
        btnInconporar = page.locator("//span[contains(text(), 'Incorporar')]");
        txtAreaIframe = page.locator("//textarea[contains(@id, 'input_me5T3RH8p9RA')]");

        //Elementos Dia
        lblTitulo = page.locator("//div[contains(@class, 'InputLabel')]//child::label[contains(text(), 'Título')]//following::input[1]");
        lblDescricao = page.locator("//div[contains(@class, 'InputLabel')]//child::label[contains(text(), 'Descrição')]//following::textarea[1]");
        dateDiaContainer = page.locator("//input[contains(@value, 'Hoje') or contains(@value, ' 2024')]");
        lblMonthYear = page.locator("//h4[@color='default' and contains(@class, 'Text__StyledText') and contains(normalize-space(.), '2024')]");

        btnNextMonth = page.locator("//button[@aria-label='Mês seguinte']");
        btnPreviousMonth = page.locator("//button[@aria-label='Mês passado']");
        dayButtons = page.locator("//div[contains(@class, 'DatePicker__Weeks-sc-179d9x6-4')]//button[@aria-label]");
        dateHoras = page.locator("//select[contains(@id, 'select_TH36wjNCJfKZ') or contains(@class, 'Default__StyledSelect-sc-usmdbc-4')]");

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
            Thread.sleep(20000);
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
                Thread.sleep(3000);
                if(lblCodigoGoogle.isVisible()){
                    strCodigo =  lblCodigoGoogle.textContent().trim();
                } else if (lblCodigoGoogleAlternative.isVisible()) {
                    strCodigo =  lblCodigoGoogleAlternative.textContent().trim();
                }
                strCodigo = strCodigo.replaceAll("\\D", "");
                page.bringToFront();
                campoCodigoEmail1.fill(Character.toString(strCodigo.charAt(0)));
                campoCodigoEmail2.fill(Character.toString(strCodigo.charAt(1)));
                campoCodigoEmail3.fill(Character.toString(strCodigo.charAt(2)));
                campoCodigoEmail4.fill(Character.toString(strCodigo.charAt(3)));
                campoCodigoEmail5.fill(Character.toString(strCodigo.charAt(4)));
                campoCodigoEmail6.fill(Character.toString(strCodigo.charAt(5)));
            }
            Thread.sleep(5000);
            if(h1SessionLimit.isVisible()){
                Thread.sleep(3000);
                Locator checkboxes = page.locator("//input[contains(@class, 'RadioButton')]");
                int count = checkboxes.count();
                for (int i = 0; i < count; i++) {
                    if (!h1SessionLimit.isVisible()) {
                        break;
                    }
                    checkboxes.nth(i).check();
                    Thread.sleep(3000);
                    if(btnAceitarCookie.isVisible()){
                        btnAceitarCookie.click();
                    }
                    bntSessionLimitSubmit.click();
                    Thread.sleep(1500);
                    if (btnContinuar.isVisible()){
                        btnContinuar.click();
                    }
                }
            }
            Thread.sleep(2000);
            btnWebnarButton.click();
            lblTitulo.fill(titulo);
            lblDescricao.fill(descricao);
            dateDiaContainer.click();
            String expectedMonthYear = startDateTime.format(DateTimeFormatter.ofPattern("MMMM yyyy", new Locale("pt", "BR")));
            String dia = String.valueOf(startDateTime.getDayOfMonth());
            String startHoras = String.valueOf(startDateTime.getHour());
            String startMinutos = String.valueOf(startDateTime.getMinute());
            String currentMonthYear = lblMonthYear.textContent().trim();

            String[] parts = currentMonthYear.split(" ");
            String currentMonth = parts[0];
            String currentYear = parts[1];
            String expectedMonth = startDateTime.getMonth().getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));
            String expectedYear = String.valueOf(startDateTime.getYear());
            while (!currentMonth.contains(expectedMonth) || !currentYear.contains(expectedYear)) {
                if (isEarlierThan(currentMonthYear, expectedMonthYear)) {
                    btnNextMonth.click();
                } else {
                    btnPreviousMonth.click();
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                currentMonthYear = lblMonthYear.textContent().trim();
                parts = currentMonthYear.split(" ");
                currentMonth = parts[0].toLowerCase();
                currentYear = parts[1];
            }

            System.out.println("Chegou ao mês esperado: " + expectedMonthYear);


            int dayButtonCount = dayButtons.count();
            for (int i = 0; i < dayButtonCount; i++) {
                Locator button = dayButtons.nth(i);
                if (Objects.equals(button.getAttribute("aria-label"), dia)) {
                    int retryCount = 0;
                    while (!button.isEnabled() && retryCount < 10) {
                        retryCount++;
                    }
                    if (button.isEnabled()) {
                        Thread.sleep(1000);
                        button.click();
                        break;
                    } else {
                        System.out.println("Botão não está habilitado após várias tentativas.");
                    }
                }
            }
            if (dateHoras.count() > 0) {
                dateHoras.first().selectOption(startHoras);
                dateHoras.all().get(1).selectOption(startMinutos);
                dateHoras.all().get(2).selectOption(duracao);
            } else {
                logger.severe("Erro: Não foram encontrados elementos em dateHoras.");
            }
            btnCriar.click();
            btnContinuarWebNar.click();
            btnCompartilharWebNar.last().click();
            btnInconporar.click();
            Iframe = txtAreaIframe.getAttribute("value");
            page.close();
            pageGoogle.close();
            context.close();
            browser.close();

        } catch (Exception e) {
            logger.severe("Error during test execution: " + e.getMessage());
        } finally {
            playwright.close();
        }
        return Iframe;
    }
    private boolean isEarlierThan(String current, String expected) {
        String[] currentParts = current.split(" ");
        String[] expectedParts = expected.split(" ");
        int currentYear = Integer.parseInt(currentParts[1]);
        int expectedYear = Integer.parseInt(expectedParts[1]);
        if (currentYear != expectedYear) {
            return currentYear < expectedYear;
        }
        int currentMonth = getMonthNumber(currentParts[0]);
        int expectedMonth = getMonthNumber(expectedParts[0]);
        return currentMonth < expectedMonth;
    }

    private int getMonthNumber(String month) {
        switch (month.toLowerCase()) {
            case "janeiro": return 1;
            case "fevereiro": return 2;
            case "março": return 3;
            case "abril": return 4;
            case "maio": return 5;
            case "junho": return 6;
            case "julho": return 7;
            case "agosto": return 8;
            case "setembro": return 9;
            case "outubro": return 10;
            case "novembro": return 11;
            case "dezembro": return 12;
            default: return 0;
        }
    }

}

