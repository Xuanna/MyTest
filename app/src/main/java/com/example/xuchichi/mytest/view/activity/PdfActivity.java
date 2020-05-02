package com.example.xuchichi.mytest.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.example.xuchichi.mytest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.util.FileUtil;

public class PdfActivity extends AppCompatActivity implements DownloadFile.Listener {
    RemotePDFViewPager remotePDFViewPager;

    Context context;
    @BindView(R.id.view)
    LinearLayout view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        ButterKnife.bind(this);
        context = this;

        String url = "http://202.76.236.10/gkashmy/gkashwalletupload/tnc/GkasheWalletT&C.pdf";

        remotePDFViewPager =
                new RemotePDFViewPager(context, url, this);
    }

    PDFPagerAdapter adapter;

    @Override
    public void onSuccess(String url, String destinationPath) {
        // That's the positive case. PDF Download went fine

        adapter = new PDFPagerAdapter(context, FileUtil.extractFileNameFromURL(url));
        remotePDFViewPager.setAdapter(adapter);
        view.addView(remotePDFViewPager,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onFailure(Exception e) {
        // This will be called if download fails
    }

    @Override
    public void onProgressUpdate(int progress, int total) {
        // You will get download progress here
        // Always on UI Thread so feel free to update your views here
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        adapter.close();
    }
}
