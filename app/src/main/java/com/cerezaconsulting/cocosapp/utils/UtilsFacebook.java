package com.cerezaconsulting.cocosapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;


import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import android.net.Uri;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by junior on 08/12/16.
 */

public class UtilsFacebook {


    public static void onShareResult(Context context, Fragment view, String contentTitle,
                                     String contentDescription, String contentUrl, String imgUrl) throws URISyntaxException {
        FacebookSdk.sdkInitialize(context);
        CallbackManager callbackManager = CallbackManager.Factory.create();
        final ShareDialog shareDialog = new ShareDialog(view);

        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {

            @Override
            public void onSuccess(Sharer.Result result) {
                Log.d("SHARE", "success");
            }


            @Override
            public void onCancel() {
                Log.d("SHARE", "cancel");
            }

            @Override
            public void onError(FacebookException error) {

            }
        });

       /* ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://developers.facebook.com"))
                .build();

        shareDialog.show(content);*/

       // if(contentUrl!=null){
         //   if (shareDialog.canShow(ShareLinkContent.class)) {

        Uri b = Uri.parse(imgUrl);

        shareDialog.canShow(ShareLinkContent.class);
              //  Bitmap image = BitmapFactory.decodeFile(imgUrl);

                SharePhoto photo = new SharePhoto.Builder()
                        .setImageUrl(b)
                        .setCaption("Cocos App")
                        //.setBitmap(image)
                        .build();

                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .setShareHashtag(new ShareHashtag.Builder()
                                .setHashtag("#CocosApp")
                                .build())
                        .build();
                shareDialog.show(content);

               /* ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentTitle(contentTitle)
                        .setContentDescription(contentDescription)
                        .setContentUrl(Uri.parse(contentUrl))
                        .build();

                shareDialog.show(linkContent);*/
            }
         /*else{
            if (shareDialog.canShow(ShareLinkContent.class)) {

                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle(contentTitle)
                    .setContentDescription(contentDescription)
                    .build();

            shareDialog.show(linkContent);

            }
        }*/





}
