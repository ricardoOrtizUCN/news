/*
 * Copyright (c) 2020 Ricardo Ortiz-Hidalgo, ricardo.ortiz@alumnos.ucn.cl
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package cl.ucn.disc.dsm.rortizhidalgo.news.model;

import net.openhft.hashing.LongHashFunction;

import org.threeten.bp.ZonedDateTime;

import cl.ucn.disc.dsm.rortizhidalgo.news.utils.Validation;

/**
 * The Domain model: News.
 *
 * @author Ricardo Ortiz-Hidalgo.
 */

public final class News {

    /**
     * Unique id.
     */
    private final Long id;

    /**
     * The Title.
     * Restrictions: not null, size > 2.
     */
    private final String title;

    /**
     * The Source.
     */
    private final String source;

    /**
     * The Author.
     */
    private final String author;

    /**
     * The URL.
     */
    private final String url;

    /**
     * The URL of image.
     */
    private final String urlImage;

    /**
     * The Description.
     */
    private final String description;

    /**
     * The Content.
     */
    private final String content;

    /**
     * The Date of publish.
     */
    private final ZonedDateTime publishedAt;

    /**
     * The constructor.
     *
     * @param title       can't be null
     * @param source      can't be null
     * @param author      can't be null
     * @param url         to the main article.
     * @param urlImage    to the image.
     * @param description ~full article.
     * @param content     can't be null.
     * @param publishedAt can't be null.
     */
    public News(String title, String source, String author, String url, String urlImage, String description, String content, ZonedDateTime publishedAt) {
        // Validation de title
        Validation.minSize(title, 2, "title");
        this.title = title;

        // Validation source
        Validation.minSize(source, 2, "source");
        this.source = source;

        // Validation author
        Validation.minSize(author, 3, "author");
        this.author = author;

        // Hashing unique! https://github.com/Cyan4973
        this.id = LongHashFunction.xx().hashChars(title + "|" + source + "|" + author);

        // Can't be null
        this.url = url;
        this.urlImage = urlImage;

        //Validation description
        Validation.minSize(description, 10, "description");
        this.description = description;

        //Validation content
        Validation.notNull(content, "content");
        this.content = content;

        //Validation publishedAt
        Validation.notNull(publishedAt, "publishedAt");
        this.publishedAt = publishedAt;
    }


    /**
     * @return the id.
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the source.
     */
    public String getSource() {
        return source;
    }

    /**
     * @return the author.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @return the URL.
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return the URL image.
     */
    public String getUrlImage() {
        return urlImage;
    }

    /**
     * @return the descriptions.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the content.
     */
    public String getContent() {
        return content;
    }

    /**
     * @return the date of published
     */
    public ZonedDateTime getPublishedAt() {
        return publishedAt;
    }
}
