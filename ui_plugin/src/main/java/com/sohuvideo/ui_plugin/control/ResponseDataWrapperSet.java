/*
 * Copyright Sohu TV 2014. All rights reserved.
 */

package com.sohuvideo.ui_plugin.control;



import com.sohuvideo.ui_plugin.model.AlbumData;
import com.sohuvideo.ui_plugin.model.AlbumListData;
import com.sohuvideo.ui_plugin.model.Channel;
import com.sohuvideo.ui_plugin.model.ColumnList;
import com.sohuvideo.ui_plugin.model.ColumnListData;
import com.sohuvideo.ui_plugin.model.PgcListData;
import com.sohuvideo.ui_plugin.model.ProducerAlbumListData;
import com.sohuvideo.ui_plugin.model.ProducerInfoData;
import com.sohuvideo.ui_plugin.model.ProducerVideoListData;
import com.sohuvideo.ui_plugin.model.SoHuProduceListData;

import java.util.List;

/**
 * This class providers wrappers that extends {@link V4APIResponse} .
 * <p/>
 * These wrappers define explicit type for data of {@link V4APIResponse} .
 *
 * @author mth
 */
public class ResponseDataWrapperSet {

    public static class ChannelListDataWrapper extends V4APIResponse<List<Channel>> {

    }

    public static class ColumnListDataWrapper extends V4APIResponse<ColumnList<List<ColumnListData>>> {

    }

    public static class PgcListDataWrapper extends V4APIResponse<PgcListData> {

    }

    public static class SoHuProduceListDataWrapper extends V4APIResponse<SoHuProduceListData> {

    }

    public static class AlbumDataWrapper extends V4APIResponse<AlbumData>{

    }

    public static class AlbumListDataWrapper extends V4APIResponse<AlbumListData>{

    }

    public static class ProducerDataWrapper extends V4APIResponse<ProducerInfoData>{

    }

    public static class ProducerAlbumListDataWrapper extends V4APIResponse<ProducerAlbumListData>{

    }

    public static class ProducerVideoListDataWrapper extends V4APIResponse<ProducerVideoListData>{

    }

}
