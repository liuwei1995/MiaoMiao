package com.zhaoyao.miaomiao.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuwei on 2017/8/28 11:33
 */

public class KanDongManEntity implements Parcelable ,Serializable{

    /**
     * tab_title : 热门
     * data : [{"comic_id":25934,"comic_name":"斗破苍穹","last_chapter_name":"第618话 千百二老（下）"},{"comic_id":25933,"comic_name":"斗罗大陆","last_chapter_name":"第193话 大海，我们来了！"},{"comic_id":5754,
     * "comic_name":"穿越西元3000后","last_chapter_name":"第192话 电磁风暴"},{"comic_id":105150,"comic_name":"霸道忠犬寻爱记","last_chapter_name":"第5话"},{"comic_id":105151,"comic_name":"纯情总裁宠娇妻",
     * "last_chapter_name":"第5话 祁晔（上）"},{"comic_id":52,"comic_name":"航海王","last_chapter_name":"875话"},{"comic_id":7119,"comic_name":"绝世唐门","last_chapter_name":"第114话 极限测试"},{"comic_id":9680,
     * "comic_name":"风起苍岚","last_chapter_name":"苍岚之巅15"},{"comic_id":5324,"comic_name":"武动乾坤","last_chapter_name":"第56话 宝藏与血蝠龙"},{"comic_id":53,"comic_name":"火影忍者","last_chapter_name":"第711话"},
     * {"comic_id":60,"comic_name":"龙珠","last_chapter_name":"最终话"},{"comic_id":5323,"comic_name":"神印王座","last_chapter_name":"第120话 情魔神西迪"},{"comic_id":17745,"comic_name":"凤逆天下",
     * "last_chapter_name":"第82话 洗髓丹药(1)"},{"comic_id":56,"comic_name":"死神/境·界","last_chapter_name":"第686话 完结"},{"comic_id":85273,"comic_name":"白夜玲珑","last_chapter_name":"第62话预告"},{"comic_id":7704,
     * "comic_name":"哑舍","last_chapter_name":"第85话 震仰盂（三）"},{"comic_id":1206,"comic_name":"新网球王子","last_chapter_name":"第223话"},{"comic_id":7638,"comic_name":"龙族3黑月之潮","last_chapter_name":"第51话
     * 长大的笨蛋"},{"comic_id":10274,"comic_name":"勇者是女孩","last_chapter_name":"第72话 另一半"},{"comic_id":71,"comic_name":"银魂","last_chapter_name":"第647话"},{"comic_id":1368,"comic_name":"阿衰",
     * "last_chapter_name":"第274话"},{"comic_id":3487,"comic_name":"偷星九月天","last_chapter_name":"番外2 偷星九月天少年版"},{"comic_id":8524,"comic_name":"妃夕妍雪","last_chapter_name":"第121话 解救"},{"comic_id":8842,
     * "comic_name":"寻找前世之旅","last_chapter_name":"第150话 误入宴会"},{"comic_id":9033,"comic_name":"血族禁域","last_chapter_name":"第98话 我恋爱了"},{"comic_id":7643,"comic_name":"X龙时代","last_chapter_name":"第61话
     * 碧蓝的怒海"},{"comic_id":10868,"comic_name":"花千骨","last_chapter_name":"第113话 在劫难逃2"},{"comic_id":8458,"comic_name":"三眼哮天录","last_chapter_name":"番外&#45;&#45;倾城2"},{"comic_id":86080,
     * "comic_name":"斗罗大陆3龙王传说","last_chapter_name":"第37话（2）"},{"comic_id":9486,"comic_name":"见习魔法师","last_chapter_name":"第73话 相煎何太急"},{"comic_id":54,"comic_name":"家庭教师","last_chapter_name":"第409话
     * 最终话"},{"comic_id":9332,"comic_name":"彼之千年","last_chapter_name":"第51话 挖地找人"},{"comic_id":5660,"comic_name":"加油大魔王","last_chapter_name":"番外 遥远的梦"},{"comic_id":85791,"comic_name":"班长大人II极限教室",
     * "last_chapter_name":"第29话 两个叶木栖？"},{"comic_id":5471,"comic_name":"星海镖师","last_chapter_name":"第182话预告"},{"comic_id":7722,"comic_name":"精灵录/恶魔党","last_chapter_name":"第66话 绿狼"},
     * {"comic_id":82625,"comic_name":"万里晴川","last_chapter_name":"第26话 生死一线"},{"comic_id":8163,"comic_name":"骑士幻想夜","last_chapter_name":"第161话 永远保护你"},{"comic_id":87114,"comic_name":"虚之结社",
     * "last_chapter_name":"第28话预告"},{"comic_id":7674,"comic_name":"遮天","last_chapter_name":"第61话 盗贼求关注"},{"comic_id":26252,"comic_name":"拜托了！田老爷","last_chapter_name":"第27话 弱智团体受难记之卧难眠"},
     * {"comic_id":97337,"comic_name":"神武将星录","last_chapter_name":"第44话 咆哮"},{"comic_id":95121,"comic_name":"逆转木兰辞","last_chapter_name":"第15话 共同进退"},{"comic_id":91961,"comic_name":"网游之近战法师",
     * "last_chapter_name":"第35话 隐藏的线索"},{"comic_id":14038,"comic_name":"东旅劫谈","last_chapter_name":"第43话预告"},{"comic_id":15469,"comic_name":"王者名昭","last_chapter_name":"第74话 学习大战！"},
     * {"comic_id":94732,"comic_name":"星辰航路","last_chapter_name":"第26话预告"},{"comic_id":82424,"comic_name":"不要向我弟弟许愿","last_chapter_name":"第13话  来自学生会的邀请"},{"comic_id":5658,"comic_name":"九九八十一",
     * "last_chapter_name":"第112话预告"},{"comic_id":92888,"comic_name":"完美世界","last_chapter_name":"第49话 成为传说"},{"comic_id":25520,"comic_name":"砂与海之歌","last_chapter_name":"第53话预告"},{"comic_id":7290,
     * "comic_name":"极品家丁","last_chapter_name":"第96话 女魔头来夜袭啦"},{"comic_id":26207,"comic_name":"斗罗大陆2绝世唐门","last_chapter_name":"第114话 极限测试"}]
     * slide : [{"comic_id":105151,"image":"http://image.samanlehua.com/news/57/376957.jpg","slide_desc":"二次元霸少狂宠穿越女"},{"comic_id":105150,"image":"http://image.samanlehua.com/news/71/377771.jpg",
     * "slide_desc":"小小萌神的捕心计划~"},{"comic_id":10274,"image":"http://image.samanlehua.com/news/39/351139.jpg","slide_desc":"变男变女？KISS说了算"},{"comic_id":14750,"image":"http://image.samanlehua
     * .com/news/33/350833.jpg","slide_desc":"十年后，云歌带着儿时的诺言来到长安..."},{"comic_id":92888,"image":"http://image.samanlehua.com/news/30/350830.jpg","slide_desc":"一粒尘可填海，一根草斩尽日月星辰"},{"comic_id":25941,
     * "image":"http://image.samanlehua.com/news/88/327688.jpg","slide_desc":"人类寻找天行者的旅程"}]
     */

    private String tab_title;
    private List<DataBean> data;
    private List<SlideBean> slide;

    public String getTab_title() {
        return tab_title;
    }

    public void setTab_title(String tab_title) {
        this.tab_title = tab_title;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public List<SlideBean> getSlide() {
        return slide;
    }

    public void setSlide(List<SlideBean> slide) {
        this.slide = slide;
    }

    public static class DataBean implements Parcelable  ,Serializable{

        /**
         * comic_id : 25934
         * comic_name : 斗破苍穹
         * last_chapter_name : 第618话 千百二老（下）
         */

        private int comic_id;
        private String comic_name;
        private String last_chapter_name;

        public int getComic_id() {
            return comic_id;
        }

        public void setComic_id(int comic_id) {
            this.comic_id = comic_id;
        }

        public String getComic_name() {
            return comic_name;
        }

        public void setComic_name(String comic_name) {
            this.comic_name = comic_name;
        }

        public String getLast_chapter_name() {
            return last_chapter_name;
        }

        public void setLast_chapter_name(String last_chapter_name) {
            this.last_chapter_name = last_chapter_name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.comic_id);
            dest.writeString(this.comic_name);
            dest.writeString(this.last_chapter_name);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.comic_id = in.readInt();
            this.comic_name = in.readString();
            this.last_chapter_name = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        @Override
        public String toString() {
            return "DataBean{" +
                    "comic_id=" + comic_id +
                    ", comic_name='" + comic_name + '\'' +
                    ", last_chapter_name='" + last_chapter_name + '\'' +
                    '}';
        }
    }

    public static class SlideBean implements Parcelable  ,Serializable{

        /**
         * comic_id : 105151
         * image : http://image.samanlehua.com/news/57/376957.jpg
         * slide_desc : 二次元霸少狂宠穿越女
         */

        private int comic_id;
        private String image;
        private String slide_desc;

        public int getComic_id() {
            return comic_id;
        }

        public void setComic_id(int comic_id) {
            this.comic_id = comic_id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getSlide_desc() {
            return slide_desc;
        }

        public void setSlide_desc(String slide_desc) {
            this.slide_desc = slide_desc;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.comic_id);
            dest.writeString(this.image);
            dest.writeString(this.slide_desc);
        }

        public SlideBean() {
        }

        protected SlideBean(Parcel in) {
            this.comic_id = in.readInt();
            this.image = in.readString();
            this.slide_desc = in.readString();
        }

        public static final Creator<SlideBean> CREATOR = new Creator<SlideBean>() {
            @Override
            public SlideBean createFromParcel(Parcel source) {
                return new SlideBean(source);
            }

            @Override
            public SlideBean[] newArray(int size) {
                return new SlideBean[size];
            }
        };

        @Override
        public String toString() {
            return "SlideBean{" +
                    "comic_id=" + comic_id +
                    ", image='" + image + '\'' +
                    ", slide_desc='" + slide_desc + '\'' +
                    '}';
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tab_title);
        dest.writeList(this.data);
        dest.writeList(this.slide);
    }

    public KanDongManEntity() {
    }

    protected KanDongManEntity(Parcel in) {
        this.tab_title = in.readString();
        this.data = new ArrayList<DataBean>();
        in.readList(this.data, DataBean.class.getClassLoader());
        this.slide = new ArrayList<SlideBean>();
        in.readList(this.slide, SlideBean.class.getClassLoader());
    }

    public static final Creator<KanDongManEntity> CREATOR = new Creator<KanDongManEntity>() {
        @Override
        public KanDongManEntity createFromParcel(Parcel source) {
            return new KanDongManEntity(source);
        }

        @Override
        public KanDongManEntity[] newArray(int size) {
            return new KanDongManEntity[size];
        }
    };

    @Override
    public String toString() {
        return "KanDongManEntity{" +
                "tab_title='" + tab_title + '\'' +
                ", data=" + data +
                ", slide=" + slide +
                '}';
    }
}
