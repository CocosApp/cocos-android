package com.cocos.cocosapp.presentation.main.home;


import com.cocos.cocosapp.data.entities.SubCatEntity;

/**
 * Created by katherine on 24/04/17.
 */

public interface SubCategoryItem {

    void clickItem(SubCatEntity subCatEntity);

    void deleteItem(SubCatEntity subCatEntity, int position);
}
