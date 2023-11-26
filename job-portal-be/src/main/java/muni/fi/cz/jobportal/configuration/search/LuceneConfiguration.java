package muni.fi.cz.jobportal.configuration.search;

import org.apache.lucene.analysis.charfilter.HTMLStripCharFilterFactory;
import org.apache.lucene.analysis.core.KeywordTokenizerFactory;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilterFactory;
import org.apache.lucene.analysis.ngram.EdgeNGramFilterFactory;
import org.apache.lucene.analysis.ngram.NGramFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurationContext;
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurer;
import org.springframework.context.annotation.Configuration;

/**
 * Custom LuceneAnalysisConfigurer for the application
 *
 * @author Vitalii Bortsov
 */
@Configuration
public class LuceneConfiguration implements LuceneAnalysisConfigurer {

  public static final String FULLTEXT_ANALYZER = "job_portal_fulltext_analyzer";
  public static final String SUGGESTER = "job_portal_suggester";
  public static final String KEYWORD_ANALYZER = "job_portal_keyword_analyzer";
  public static final String SORT_NORMALIZER = "job_portal_normalizer";

  @Override
  public void configure(LuceneAnalysisConfigurationContext luceneAnalysisConfigurationContext) {
    luceneAnalysisConfigurationContext
      .analyzer(FULLTEXT_ANALYZER)
      .custom()
      .tokenizer(StandardTokenizerFactory.class)
      .charFilter(HTMLStripCharFilterFactory.class)
      .tokenFilter(LowerCaseFilterFactory.class)
      .tokenFilter(ASCIIFoldingFilterFactory.class)
      .tokenFilter(NGramFilterFactory.class)
      .param("minGramSize", "1")
      .param("maxGramSize", "40")
      .param("preserveOriginal", "true");

//    source: https://stackoverflow.com/questions/43044350/hibernate-search-ngram-analyzer-with-mingramsize-1
    luceneAnalysisConfigurationContext
      .analyzer(SUGGESTER)
      .custom()
      .tokenizer(StandardTokenizerFactory.class)
      .charFilter(HTMLStripCharFilterFactory.class)
      .tokenFilter(LowerCaseFilterFactory.class)
      .tokenFilter(ASCIIFoldingFilterFactory.class);

    luceneAnalysisConfigurationContext
      .analyzer(KEYWORD_ANALYZER)
      .custom()
      .tokenizer(KeywordTokenizerFactory.class);

    luceneAnalysisConfigurationContext
      .normalizer(SORT_NORMALIZER)
      .custom()
      .tokenFilter(LowerCaseFilterFactory.class)
      .tokenFilter(ASCIIFoldingFilterFactory.class);
  }
}
