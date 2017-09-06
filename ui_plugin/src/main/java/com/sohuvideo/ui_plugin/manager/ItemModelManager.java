package com.sohuvideo.ui_plugin.manager;

import com.sohuvideo.ui_plugin.model.Column;
import com.sohuvideo.ui_plugin.model.ColumnListData;
import com.sohuvideo.ui_plugin.model.ItemModel;

import java.util.ArrayList;
import java.util.List;


public class ItemModelManager {

    private static final String TAG = ItemModelManager.class.getSimpleName();
    private static int ROW_NUM = 2;

    /**
     * convert List
     *
     * @param columns
     * @return
     */
    public static List<ItemModel> convertColumns2ItemModelList(List<ColumnListData> columns) {
        List<ItemModel> modelList = new ArrayList<ItemModel>();
        for (int i = 0; i < columns.size(); i++) {
            ColumnListData data = columns.get(i);
            ItemModel titleModel = createTitelItemModel(data);
            modelList = createColumnListItemModel(titleModel, modelList, data);
        }
        return modelList;
    }

    /**
     * create title item
     *
     * @param data
     * @return
     */
    private static ItemModel createTitelItemModel(ColumnListData data) {
        ItemModel model = new ItemModel();
        model.setItemType(ItemModel.ITEM_TYPE_NORMAL_TITLE);
        model.setTitle(data.getColumn_name());
        model.setCategory_code(data.getCategory_code());
        model.setSize(data.getVideos().size());
        return model;
    }

    /**
     * creaye  pgcList item
     *
     * @param titleModel titleitem
     * @param modelList  listitem
     * @param data
     * @return
     */
    private static List<ItemModel> createColumnListItemModel(ItemModel titleModel, List<ItemModel> modelList, ColumnListData data) {

        boolean isSub = false;
        //add title item
        modelList.add(titleModel);
        List<Column> videos = data.getVideos();
        int size = videos.size();
        int count = 0;

        if (size > 0) {
            for (int i = 0; i < size; i++) {
                if (count >= size) {
                    break;
                }
                ItemModel model = new ItemModel();
                int start = i * ROW_NUM;
                int end = start + ROW_NUM;
                List<Column> subList = null;
                try {
                    subList = videos.subList(start, end);
                    model.setItemType(ItemModel.ITEM_TYPE_NORMAL_VIDEO_LIST);
                    model.setmColumnList(subList);
                    count += 2;
                    modelList.add(model);
                    isSub = true;
                } catch (Exception e) {
                    if (!isSub) {
                        modelList.remove(titleModel);
                    }
                    break;
                }
            }
        }
        if (isSub) {
            ItemModel separator = new ItemModel();
            separator.setItemType(ItemModel.ITEM_TYPE_VIDEO_LIST_SEPARATOR);
            modelList.add(separator);
        }
        return modelList;
    }

    /**
     * copy
     *
     * @param column
     * @return
     */

    private static Column copy(Column column) {
        Column c = new Column();
        c.setShow(false);
        c.setAid(column.getAid());
        c.setAlbum_name(column.getAlbum_name());
        c.setHor_high_pic(column.getHor_high_pic());
        c.setVer_high_pic(column.getVer_high_pic());
        c.setPlay_count(column.getPlay_count());
        c.setTime_length(column.getTime_length());
        c.setSite(column.getSite());
        c.setVid(column.getVid());
        c.setVideo_name(column.getVideo_name());
        return c;
    }
}
