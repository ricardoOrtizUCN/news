/*
 * Copyright (c) 2020 Ricardo Ortiz-Hidalgo, ricardo.ortiz@alumnos.ucn.cl
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package cl.ucn.disc.dsm.rortizhidalgo.news;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import cl.ucn.disc.dsm.rortizhidalgo.news.model.News;
import cl.ucn.disc.dsm.rortizhidalgo.news.services.Contracts;
import cl.ucn.disc.dsm.rortizhidalgo.news.services.ContractsImplNewsApi;

/**
 * The Main Class.
 * @author Ricardo Ortiz-Hidalgo
 */

public final class MainActivity extends AppCompatActivity {
    /**
     * The logger.
     */
    private static final Logger log = LoggerFactory.getLogger(MainActivity.class);

    /**
     *  The List View
     */
    protected ListView listView;

    /**
     * Oncreate.
     *
     * @param savedInstanceState used ti reload the app.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log.debug("onCreate...");
        setContentView(R.layout.activity_main);

        // Get the ListView from layout
        this.listView = findViewById(R.id.am_t_toolbar);

        // Click un one item
        this.listView.setOnItemClickListener((parent, view, position, id) -> {
            log.debug("Position: {}, Id: {}.",position,id);
        });
        // Running in BackgroundThread...
        AsyncTask.execute(()->{
            // Using the contracts to get the news...
            Contracts contracts = new ContractsImplNewsApi("d49251f091fe4c96a56694b17fd3c739");

            // Get the news from NewAPi (internet)
            List<News> listNews = contracts.retrieveNews(30);

            //Build the simple adapter to populate the list
            ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listNews);

            //... update the listView in Uithread
            runOnUiThread(()->{
                this.listView.setAdapter(adapter);
            });
        });
    }
}
