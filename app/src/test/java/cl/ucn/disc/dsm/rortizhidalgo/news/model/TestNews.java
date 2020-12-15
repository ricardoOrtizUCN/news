/*
 * Copyright (c) 2020.
 * Copyright Stoday.year Ricardo Ortiz-Hidalgo, ricardo.ortiz@alumnos.ucn.cl
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package cl.ucn.disc.dsm.rortizhidalgo.news.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

/**
 * Testing validation of class News
 *
 * @author Diego Urrutia-Astorga.
 */
public class TestNews {

    /**
     * The logger.
     */
    private static final Logger log = LoggerFactory.getLogger(TestNews.class);

    @Test
    public void testConstructor() {

        log.debug("Testing ..");

        // all data ok
        News news = new News(
                "The Title",
                "The Source",
                "The Author",
                "URL",
                "URL Image",
                "Description",
                "The Content",
                ZonedDateTime.now(ZoneId.of("-3"))
        );

        log.debug("The id: {}.", news.getId());

        Assertions.assertEquals(1182003507361219124L, news.getId(), "Wrong id !");

        log.debug("Title null ..");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new News(
                    null,
                    "The Source",
                    "The Author",
                    "URL",
                    "URL Image",
                    "Description",
                    "The Content",
                    ZonedDateTime.now(ZoneId.of("-3"))
            );
        });

        log.debug("Source null ..");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new News(
                    "The Title",
                    null,
                    "The Author",
                    "URL",
                    "URL Image",
                    "Description",
                    "The Content",
                    ZonedDateTime.now(ZoneId.of("-3"))
            );
        });

        log.debug("Author null ..");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new News(
                    "The Title",
                    "The Source",
                    null,
                    "URL",
                    "URL Image",
                    "Description",
                    "The Content",
                    ZonedDateTime.now(ZoneId.of("-3"))
            );
        });

        log.debug("Description null ..");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new News(
                    "The Title",
                    "The Source",
                    "The Author",
                    "URL",
                    "URL Image",
                    null,
                    "The Content",
                    ZonedDateTime.now(ZoneId.of("-3"))
            );
        });

        log.debug("Content null ..");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new News(
                    "The Title",
                    "The Source",
                    "The Author",
                    "URL",
                    "URL Image",
                    "Description",
                    null,
                    ZonedDateTime.now(ZoneId.of("-3"))
            );
        });

        log.debug("PublisheAt null ..");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new News(
                    "The Title",
                    "The Source",
                    "The Author",
                    "URL",
                    "URL Image",
                    "Description",
                    "The Content",
                    null

            );
        });

        log.debug(".. Done!");
    }
}

