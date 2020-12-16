/*
 * Copyright (c) 2020 Ricardo Ortiz-Hidalgo, ricardo.ortiz@alumnos.ucn.cl
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */


package cl.ucn.disc.dsm.rortizhidalgo.news.services;

import com.kwabenaberko.newsapilib.models.Article;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import cl.ucn.disc.dsm.rortizhidalgo.news.model.News;
import cl.ucn.disc.dsm.rortizhidalgo.news.utils.Validation;

/**
 * The NewsAPI implementation via Retrofit.
 *
 * @author Ricardo Ortiz-Hidalgo
 */
public final class ContractsImplNewsApi implements Contracts {
    /**
     * The logger
     */
    private static final Logger log = LoggerFactory.getLogger(ContractsImplNewsApi.class);

    /**
     * The Connection to NewApi
     */
    private final NewsApiService newsApiService;

    /**
     * the constructor
     *
     * @param theApikey to use
     */
    public ContractsImplNewsApi(final String theApikey) {
        //validation apikey
        //Validation.notNull(apikey, "apikey");
        Validation.minSize(theApikey,10,"ApiKey !!");
        this.newsApiService = new NewsApiService(theApikey);
    }

    /**
     * Article to news/Transformer pattern
     * @param article used to convert
     * @return the News.
     */
    private static News toNews(final Article article){
        //validation null news
        Validation.notNull(article,"Article null??");

        //warning message
        boolean needFix=false;

        //Fix the author is null
        if(article.getAuthor()==null || article.getAuthor().length()==0){
            article.setAuthor("*No author*");
            needFix=true;
        }
        //more restriction
        if (article.getDescription()==null||article.getDescription().length()==0){
            article.setDescription("*No description*");
            needFix=true;
        }
        //yes warning
        if(needFix==true){
            //Debug of Article
            log.warn("Article with invalid restriction: ().", ToStringBuilder.reflectionToString(
                    article, ToStringStyle.MULTI_LINE_STYLE));
        }
        //the date
        ZonedDateTime publishedAt= ZonedDateTime.parse(article.getPublishedAt()).withZoneSameInstant(ZoneId.of("-3"));

        //the news
        return new News(article.getTitle(),
                article.getSource().getName(),
                article.getAuthor(),
                article.getUrl(),
                article.getUrlToImage(),
                article.getDescription(),
                article.getDescription(), //Fix content
                publishedAt);
    }

    /**
     * Get the list of News.
     *
     * @param size size of the list.
     * @return the list of News.
     */
    @Override
    public List<News> retrieveNews(final Integer size) {

        try {
            //Request to newApi category "general"
            List<Article> articles =newsApiService.getTopHeadlines("technology",size);

            //The list of articles to list of news
            List<News> news = new ArrayList<>();

            //iterate over the articles
            for (Article article : articles){
                //articles -> News
                news.add(toNews(article));
            }
            //Filter and sort the News
            return news.stream().filter(distintById(News::getId))
                    // Remote the duplicates (by id)
                    .sorted((k1,k2)->k2
                            // Sort the stream by publishedAt
                            .getPublishedAt().compareTo(k1.getPublishedAt()))
                    // Return the stream to List
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            //log.error("Error",ex);
            //return null;
            //Encapsulate!
            throw new RuntimeException(ex);
        }
    }

    /**
     * Filter the stream.
     *
     * @param idExtractor
     * @param <T> news to filter
     * @return true if the news already exists.
     */
    private static <T> Predicate<T> distintById (Function<? super T, ?> idExtractor){
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(idExtractor.apply(t),Boolean.TRUE)==null;
    }

    /**
     * Save one News into the System.
     *
     * @param news to save.
     */
    @Override
    public void saveNews(News news) {
        throw new NotImplementedException("Can´t save news in NewsApi!");
    }
}