package com.zhaoyao.miaomiao.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * 漫画章节详情
 * Created by liuwei on 2017/8/28 17:12
 */

public class ComicChapterDetailsEntity implements Parcelable ,Serializable{


    /**
     * comic_name : 斗破苍穹
     * comic_type : {"rexue":"热血","shenmo":"神魔"}
     * comic_status : 1
     * comic_author : 天蚕土豆 任翔
     * comic_desc :
     * 此漫画由天蚕土豆热门小说《斗破苍穹》改编。故事漫画斗破苍穹漫画版简介这里是属于斗气的世界，没有花俏艳丽的魔法，有的，仅仅是繁衍到巅峰的斗气！斗气十境界斗者内视，聚气成气旋。斗师斗气纱衣，聚气化液态。大斗师斗气铠甲，斗气外放，聚气化固态，呈菱形。斗灵斗气凝物，固态，形似海胆。斗王斗气化翼，调动外界空间少量的能量。斗皇斗气化翼，可大量调动外界同属性能量，不借外力短时间停留虚空（高阶斗皇）。斗宗不借助外力停留虚空，可以制造空间锁（高阶斗宗）。斗尊可以掌握并运用空间之力。斗圣举手投足间，山崩地裂，空间破碎。而从虚无空间中开辟一方可供人居住的空间，则是斗圣强者的标志。而六星斗圣的标志则是空间挪位。斗圣巅峰强者，血脉开始变异。（半圣想要进阶斗圣境界就必须踏入半圣）斗帝改变自身的血脉，令自己的后人变得更加强大。
     * comic_notice :
     * comic_copyright :
     * last_chapter_id : 618
     * update_time : 1503369101
     * comic_media : 知音漫客
     * comic_chapter : [{"chapter_type":"连载","chapter_list":[{"chapter_name":"第1话 陨落的天才（上）","chapter_id":"dpcq_1h","chapter_topic_id":1400558,"create_date":1502988183,
     * "chapter_source":[{"rule":"/comic/D/斗破苍穹拆分版/1话/$$.jpg","site_id":-1,"source_url":"http://www.manhuatai.com/doupocangqiong/dpcq_1h.html?from=kmhapp","start_num":1,"end_num":13,
     * "chapter_domain":"zymkcdn.com"}]}]},{"chapter_type":"番外","chapter_list":[{"chapter_name":"白兔","chapter_id":"fwp-bt","chapter_topic_id":69864,"create_date":1343397243,
     * "chapter_source":[{"site_id":-1,"source_url":"http://www.manhuatai.com/doupocangqiong/fwp-bt.html?from=kmhapp","rule":"/comic/D/斗破苍穹/番外篇白兔/$$.jpg","start_num":1,"end_num":16,
     * "chapter_domain":""},{"site_id":2,"errortimes":2,"source_id":30759,"source_url":"http://www.mh160.com/kanmanhua/11105/225094.html"}]}]}]
     */

    private String comic_name;
    private ComicTypeBean comic_type;
    private int comic_status;
    private String comic_author;
    private String comic_desc;
    private String comic_notice;
    private String comic_copyright;
    private String last_chapter_id;
    private int update_time;
    private String comic_media;
    private List<ComicChapterBean> comic_chapter;

    public String getComic_name() {
        return comic_name;
    }

    public void setComic_name(String comic_name) {
        this.comic_name = comic_name;
    }

    public ComicTypeBean getComic_type() {
        return comic_type;
    }

    public void setComic_type(ComicTypeBean comic_type) {
        this.comic_type = comic_type;
    }

    public int getComic_status() {
        return comic_status;
    }

    public void setComic_status(int comic_status) {
        this.comic_status = comic_status;
    }

    public String getComic_author() {
        return comic_author;
    }

    public void setComic_author(String comic_author) {
        this.comic_author = comic_author;
    }

    public String getComic_desc() {
        return comic_desc;
    }

    public void setComic_desc(String comic_desc) {
        this.comic_desc = comic_desc;
    }

    public String getComic_notice() {
        return comic_notice;
    }

    public void setComic_notice(String comic_notice) {
        this.comic_notice = comic_notice;
    }

    public String getComic_copyright() {
        return comic_copyright;
    }

    public void setComic_copyright(String comic_copyright) {
        this.comic_copyright = comic_copyright;
    }

    public String getLast_chapter_id() {
        return last_chapter_id;
    }

    public void setLast_chapter_id(String last_chapter_id) {
        this.last_chapter_id = last_chapter_id;
    }

    public int getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(int update_time) {
        this.update_time = update_time;
    }

    public String getComic_media() {
        return comic_media;
    }

    public void setComic_media(String comic_media) {
        this.comic_media = comic_media;
    }

    public List<ComicChapterBean> getComic_chapter() {
        return comic_chapter;
    }

    public void setComic_chapter(List<ComicChapterBean> comic_chapter) {
        this.comic_chapter = comic_chapter;
    }

    public static class ComicTypeBean implements Parcelable ,Serializable{

        /**
         * rexue : 热血
         * shenmo : 神魔
         */

        private String rexue;
        private String shenmo;

        public String getRexue() {
            return rexue;
        }

        public void setRexue(String rexue) {
            this.rexue = rexue;
        }

        public String getShenmo() {
            return shenmo;
        }

        public void setShenmo(String shenmo) {
            this.shenmo = shenmo;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.rexue);
            dest.writeString(this.shenmo);
        }

        public ComicTypeBean() {
        }

        protected ComicTypeBean(Parcel in) {
            this.rexue = in.readString();
            this.shenmo = in.readString();
        }

        public static final Creator<ComicTypeBean> CREATOR = new Creator<ComicTypeBean>() {
            @Override
            public ComicTypeBean createFromParcel(Parcel source) {
                return new ComicTypeBean(source);
            }

            @Override
            public ComicTypeBean[] newArray(int size) {
                return new ComicTypeBean[size];
            }
        };

        @Override
        public String toString() {
            return "ComicTypeBean{" +
                    "rexue='" + rexue + '\'' +
                    ", shenmo='" + shenmo + '\'' +
                    '}';
        }
    }

    public static class ComicChapterBean implements Parcelable {

        /**
         * chapter_type : 连载
         * chapter_list : [{"chapter_name":"第1话 陨落的天才（上）","chapter_id":"dpcq_1h","chapter_topic_id":1400558,"create_date":1502988183,"chapter_source":[{"rule":"/comic/D/斗破苍穹拆分版/1话/$$.jpg",
         * "site_id":-1,"source_url":"http://www.manhuatai.com/doupocangqiong/dpcq_1h.html?from=kmhapp","start_num":1,"end_num":13,"chapter_domain":"zymkcdn.com"}]}]
         */

        private String chapter_type;
        private List<ChapterListBean> chapter_list;

        public String getChapter_type() {
            return chapter_type;
        }

        public void setChapter_type(String chapter_type) {
            this.chapter_type = chapter_type;
        }

        public List<ChapterListBean> getChapter_list() {
            return chapter_list;
        }

        public void setChapter_list(List<ChapterListBean> chapter_list) {
            this.chapter_list = chapter_list;
        }

        public static class ChapterListBean implements Parcelable ,Serializable{

            /**
             * chapter_name : 第1话 陨落的天才（上）
             * chapter_id : dpcq_1h
             * chapter_topic_id : 1400558
             * create_date : 1502988183
             * chapter_source : [{"rule":"/comic/D/斗破苍穹拆分版/1话/$$.jpg","site_id":-1,"source_url":"http://www.manhuatai.com/doupocangqiong/dpcq_1h.html?from=kmhapp","start_num":1,"end_num":13,
             * "chapter_domain":"zymkcdn.com"}]
             */

            private String chapter_name;
            private String chapter_id;
            private int chapter_topic_id;
            private int create_date;
            private List<ChapterSourceBean> chapter_source;

            public String getChapter_name() {
                return chapter_name;
            }

            public void setChapter_name(String chapter_name) {
                this.chapter_name = chapter_name;
            }

            public String getChapter_id() {
                return chapter_id;
            }

            public void setChapter_id(String chapter_id) {
                this.chapter_id = chapter_id;
            }

            public int getChapter_topic_id() {
                return chapter_topic_id;
            }

            public void setChapter_topic_id(int chapter_topic_id) {
                this.chapter_topic_id = chapter_topic_id;
            }

            public int getCreate_date() {
                return create_date;
            }

            public void setCreate_date(int create_date) {
                this.create_date = create_date;
            }

            public List<ChapterSourceBean> getChapter_source() {
                return chapter_source;
            }

            public void setChapter_source(List<ChapterSourceBean> chapter_source) {
                this.chapter_source = chapter_source;
            }

            public static class ChapterSourceBean implements Parcelable ,Serializable{

                /**
                 * rule : /comic/D/斗破苍穹拆分版/1话/$$.jpg
                 * site_id : -1
                 * source_url : http://www.manhuatai.com/doupocangqiong/dpcq_1h.html?from=kmhapp
                 * start_num : 1
                 * end_num : 13
                 * chapter_domain : zymkcdn.com
                 */

                private String rule;
                private int site_id;
                private String source_url;
                private int start_num;
                private int end_num;
                private String chapter_domain;

                public String getRule() {
                    return rule;
                }

                public void setRule(String rule) {
                    this.rule = rule;
                }

                public int getSite_id() {
                    return site_id;
                }

                public void setSite_id(int site_id) {
                    this.site_id = site_id;
                }

                public String getSource_url() {
                    return source_url;
                }

                public void setSource_url(String source_url) {
                    this.source_url = source_url;
                }

                public int getStart_num() {
                    return start_num;
                }

                public void setStart_num(int start_num) {
                    this.start_num = start_num;
                }

                public int getEnd_num() {
                    return end_num;
                }

                public void setEnd_num(int end_num) {
                    this.end_num = end_num;
                }

                public String getChapter_domain() {
                    return chapter_domain;
                }

                public void setChapter_domain(String chapter_domain) {
                    this.chapter_domain = chapter_domain;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.rule);
                    dest.writeInt(this.site_id);
                    dest.writeString(this.source_url);
                    dest.writeInt(this.start_num);
                    dest.writeInt(this.end_num);
                    dest.writeString(this.chapter_domain);
                }

                public ChapterSourceBean() {
                }

                protected ChapterSourceBean(Parcel in) {
                    this.rule = in.readString();
                    this.site_id = in.readInt();
                    this.source_url = in.readString();
                    this.start_num = in.readInt();
                    this.end_num = in.readInt();
                    this.chapter_domain = in.readString();
                }

                public static final Creator<ChapterSourceBean> CREATOR = new Creator<ChapterSourceBean>() {
                    @Override
                    public ChapterSourceBean createFromParcel(Parcel source) {
                        return new ChapterSourceBean(source);
                    }

                    @Override
                    public ChapterSourceBean[] newArray(int size) {
                        return new ChapterSourceBean[size];
                    }
                };

                @Override
                public String toString() {
                    return "ChapterSourceBean{" +
                            "rule='" + rule + '\'' +
                            ", site_id=" + site_id +
                            ", source_url='" + source_url + '\'' +
                            ", start_num=" + start_num +
                            ", end_num=" + end_num +
                            ", chapter_domain='" + chapter_domain + '\'' +
                            '}';
                }
            }

            @Override
            public String toString() {
                return "ChapterListBean{" +
                        "chapter_name='" + chapter_name + '\'' +
                        ", chapter_id='" + chapter_id + '\'' +
                        ", chapter_topic_id=" + chapter_topic_id +
                        ", create_date=" + create_date +
                        ", chapter_source=" + chapter_source +
                        '}';
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.chapter_name);
                dest.writeString(this.chapter_id);
                dest.writeInt(this.chapter_topic_id);
                dest.writeInt(this.create_date);
                dest.writeTypedList(this.chapter_source);
            }

            public ChapterListBean() {
            }

            protected ChapterListBean(Parcel in) {
                this.chapter_name = in.readString();
                this.chapter_id = in.readString();
                this.chapter_topic_id = in.readInt();
                this.create_date = in.readInt();
                this.chapter_source = in.createTypedArrayList(ChapterSourceBean.CREATOR);
            }

            public static final Creator<ChapterListBean> CREATOR = new Creator<ChapterListBean>() {
                @Override
                public ChapterListBean createFromParcel(Parcel source) {
                    return new ChapterListBean(source);
                }

                @Override
                public ChapterListBean[] newArray(int size) {
                    return new ChapterListBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.chapter_type);
            dest.writeTypedList(this.chapter_list);
        }

        public ComicChapterBean() {
        }

        protected ComicChapterBean(Parcel in) {
            this.chapter_type = in.readString();
            this.chapter_list = in.createTypedArrayList(ChapterListBean.CREATOR);
        }

        public static final Creator<ComicChapterBean> CREATOR = new Creator<ComicChapterBean>() {
            @Override
            public ComicChapterBean createFromParcel(Parcel source) {
                return new ComicChapterBean(source);
            }

            @Override
            public ComicChapterBean[] newArray(int size) {
                return new ComicChapterBean[size];
            }
        };

        @Override
        public String toString() {
            return "ComicChapterBean{" +
                    "chapter_type='" + chapter_type + '\'' +
                    ", chapter_list=" + chapter_list +
                    '}';
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.comic_name);
        dest.writeParcelable(this.comic_type, flags);
        dest.writeInt(this.comic_status);
        dest.writeString(this.comic_author);
        dest.writeString(this.comic_desc);
        dest.writeString(this.comic_notice);
        dest.writeString(this.comic_copyright);
        dest.writeString(this.last_chapter_id);
        dest.writeInt(this.update_time);
        dest.writeString(this.comic_media);
        dest.writeTypedList(this.comic_chapter);
    }

    public ComicChapterDetailsEntity() {
    }

    protected ComicChapterDetailsEntity(Parcel in) {
        this.comic_name = in.readString();
        this.comic_type = in.readParcelable(ComicTypeBean.class.getClassLoader());
        this.comic_status = in.readInt();
        this.comic_author = in.readString();
        this.comic_desc = in.readString();
        this.comic_notice = in.readString();
        this.comic_copyright = in.readString();
        this.last_chapter_id = in.readString();
        this.update_time = in.readInt();
        this.comic_media = in.readString();
        this.comic_chapter = in.createTypedArrayList(ComicChapterBean.CREATOR);
    }

    public static final Creator<ComicChapterDetailsEntity> CREATOR = new Creator<ComicChapterDetailsEntity>() {
        @Override
        public ComicChapterDetailsEntity createFromParcel(Parcel source) {
            return new ComicChapterDetailsEntity(source);
        }

        @Override
        public ComicChapterDetailsEntity[] newArray(int size) {
            return new ComicChapterDetailsEntity[size];
        }
    };

    @Override
    public String toString() {
        return "ComicChapterDetailsEntity{" +
                "comic_name='" + comic_name + '\'' +
                ", comic_type=" + comic_type +
                ", comic_status=" + comic_status +
                ", comic_author='" + comic_author + '\'' +
                ", comic_desc='" + comic_desc + '\'' +
                ", comic_notice='" + comic_notice + '\'' +
                ", comic_copyright='" + comic_copyright + '\'' +
                ", last_chapter_id='" + last_chapter_id + '\'' +
                ", update_time=" + update_time +
                ", comic_media='" + comic_media + '\'' +
                ", comic_chapter=" + comic_chapter +
                '}';
    }
}
