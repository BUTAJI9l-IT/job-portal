package com.github.butaji9l.jobportal.be.configuration;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.font.FontProgramFactory;
import java.io.IOException;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.webresources.TomcatURLStreamHandlerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ResourceLoader;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * Configuration class for Thymeleaf processor.
 *
 * @author Vitalii Bortsov
 */
@Configuration
@RequiredArgsConstructor
public class ThymeleafConfiguration {

  /**
   * CSS files location
   */
  public static final String CSS_PREFIX = "static/css/";
  /**
   * HTML templates files location
   */
  public static final String BASE_URI = "classpath:/templates/";
  /**
   * Message files location
   */
  public static final String MESSAGES = "templates/messages/messages";
  private final ResourceLoader resourceLoader;

  @Bean
  public SpringTemplateEngine templateEngine() {
    final var templateEngine = new SpringTemplateEngine();
    templateEngine.addTemplateResolver(thymeleafTemplateResolver());
    templateEngine.addTemplateResolver(cssTemplateResolver());
    templateEngine.addDialect(new Java8TimeDialect());
    return templateEngine;
  }

  @Bean
  public ResourceBundleMessageSource messageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasename(MESSAGES);
    messageSource.setDefaultEncoding("UTF-8");
    messageSource.setDefaultLocale(Locale.ENGLISH);
    return messageSource;
  }

  @Bean
  public ClassLoaderTemplateResolver thymeleafTemplateResolver() {
    final var templateResolver = new ClassLoaderTemplateResolver();
    templateResolver.setPrefix("templates/");
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode(TemplateMode.HTML);
    return templateResolver;
  }

  @Bean
  public ClassLoaderTemplateResolver cssTemplateResolver() {
    final var templateResolver = new ClassLoaderTemplateResolver(
      Thread.currentThread().getContextClassLoader());
    templateResolver.setTemplateMode(TemplateMode.CSS);
    templateResolver.setPrefix(CSS_PREFIX);
    templateResolver.setSuffix(".css");
    templateResolver.setCharacterEncoding("UTF-8");
    return templateResolver;
  }

  @Bean
  public ConverterProperties converterProperties() throws IOException {
    final var converterProperties = new ConverterProperties();
    TomcatURLStreamHandlerFactory.register();
    converterProperties.setBaseUri(BASE_URI);
    final var dfp = new DefaultFontProvider();

    addFont(dfp, ApplicationFonts.ARIAL_FONT_PATH);
    addFont(dfp, ApplicationFonts.ARIAL_BOLD_FONT_PATH);
    addFont(dfp, ApplicationFonts.LATO_BLACK_FONT_PATH);
    addFont(dfp, ApplicationFonts.LATO_BLACK_ITALIC_FONT_PATH);
    addFont(dfp, ApplicationFonts.LATO_BOLD_FONT_PATH);
    addFont(dfp, ApplicationFonts.LATO_BOLD_ITALIC_FONT_PATH);
    addFont(dfp, ApplicationFonts.LATO_ITALIC_FONT_PATH);
    addFont(dfp, ApplicationFonts.LATO_LIGHT_FONT_PATH);
    addFont(dfp, ApplicationFonts.LATO_LIGHT_ITALIC_FONT_PATH);
    addFont(dfp, ApplicationFonts.LATO_REGULAR_FONT_PATH);
    addFont(dfp, ApplicationFonts.LATO_THIN_FONT_PATH);
    addFont(dfp, ApplicationFonts.LATO_THIN_ITALIC_FONT_PATH);
    addFont(dfp, ApplicationFonts.OSWALD_BOLD_FONT_PATH);
    addFont(dfp, ApplicationFonts.OSWALD_EXTRALIGHT_FONT_PATH);
    addFont(dfp, ApplicationFonts.OSWALD_LIGHT_FONT_PATH);
    addFont(dfp, ApplicationFonts.OSWALD_MEDIUM_FONT_PATH);
    addFont(dfp, ApplicationFonts.OSWALD_REGULAR_FONT_PATH);
    addFont(dfp, ApplicationFonts.OSWALD_SEMIBOLD_FONT_PATH);

    converterProperties.setFontProvider(dfp);
    return converterProperties;
  }

  private void addFont(DefaultFontProvider dfp, String arialFontPath) throws IOException {
    dfp.addFont(
      FontProgramFactory.createFont(resourceLoader.getResource(arialFontPath).getFile().getPath()));
  }

}
