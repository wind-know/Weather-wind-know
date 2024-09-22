package com.example.weatherwindknow.myMap.help;
import android.text.Html;
import android.view.View;

import com.example.weatherwindknow.R;

import org.json.JSONObject;

/**
 * 图标工具类
 */
public class IconUtils {

    /**
     * 获取白天深色天气图标
     */
    public static int getDayIconDark(String weather) {
        int imageId;
        switch (weather) {
            case "100":
                imageId = R.mipmap.icon_100d;
                break;
            case "101":
                imageId = R.mipmap.icon_101d;
                break;
            case "102":
                imageId = R.mipmap.icon_102d;
                break;
            case "103":
                imageId = R.mipmap.icon_103d;
                break;
            case "104":
                imageId = R.mipmap.icon_104d;
                break;
            case "200":
                imageId = R.mipmap.icon_200d;
                break;
            case "201":
                imageId = R.mipmap.icon_201d;
                break;
            case "202":
                imageId = R.mipmap.icon_202d;
                break;
            case "203":
                imageId = R.mipmap.icon_203d;
                break;
            case "204":
                imageId = R.mipmap.icon_204d;
                break;
            case "205":
                imageId = R.mipmap.icon_205d;
                break;
            case "206":
                imageId = R.mipmap.icon_206d;
                break;
            case "207":
                imageId = R.mipmap.icon_207d;
                break;
            case "208":
                imageId = R.mipmap.icon_208d;
                break;
            case "209":
                imageId = R.mipmap.icon_209d;
                break;
            case "210":
                imageId = R.mipmap.icon_210d;
                break;
            case "211":
                imageId = R.mipmap.icon_211d;
                break;
            case "212":
                imageId = R.mipmap.icon_212d;
                break;
            case "213":
                imageId = R.mipmap.icon_213d;
                break;
            case "300":
                imageId = R.mipmap.icon_300d;
                break;
            case "301":
                imageId = R.mipmap.icon_301d;
                break;
            case "302":
                imageId = R.mipmap.icon_302d;
                break;
            case "303":
                imageId = R.mipmap.icon_303d;
                break;
            case "304":
                imageId = R.mipmap.icon_304d;
                break;
            case "305":
                imageId = R.mipmap.icon_305d;
                break;
            case "306":
                imageId = R.mipmap.icon_306d;
                break;
            case "307":
                imageId = R.mipmap.icon_307d;
                break;
            case "308":
                imageId = R.mipmap.icon_308d;
                break;
            case "309":
                imageId = R.mipmap.icon_309d;
                break;
            case "310":
                imageId = R.mipmap.icon_310d;
                break;
            case "311":
                imageId = R.mipmap.icon_311d;
                break;
            case "312":
                imageId = R.mipmap.icon_312d;
                break;
            case "313":
                imageId = R.mipmap.icon_313d;
                break;
            case "314":
                imageId = R.mipmap.icon_314d;
                break;
            case "315":
                imageId = R.mipmap.icon_315d;
                break;
            case "316":
                imageId = R.mipmap.icon_316d;
                break;
            case "317":
                imageId = R.mipmap.icon_317d;
                break;
            case "318":
                imageId = R.mipmap.icon_318d;
                break;
            case "399":
                imageId = R.mipmap.icon_399d;
                break;
            case "400":
                imageId = R.mipmap.icon_400d;
                break;
            case "401":
                imageId = R.mipmap.icon_401d;
                break;
            case "402":
                imageId = R.mipmap.icon_402d;
                break;
            case "403":
                imageId = R.mipmap.icon_403d;
                break;
            case "404":
                imageId = R.mipmap.icon_404d;
                break;
            case "405":
                imageId = R.mipmap.icon_405d;
                break;
            case "406":
                imageId = R.mipmap.icon_406d;
                break;
            case "407":
                imageId = R.mipmap.icon_407d;
                break;
            case "408":
                imageId = R.mipmap.icon_408d;
                break;
            case "409":
                imageId = R.mipmap.icon_409d;
                break;
            case "410":
                imageId = R.mipmap.icon_410d;
                break;
            case "499":
                imageId = R.mipmap.icon_499d;
                break;
            case "500":
                imageId = R.mipmap.icon_500d;
                break;
            case "501":
                imageId = R.mipmap.icon_501d;
                break;
            case "502":
                imageId = R.mipmap.icon_502d;
                break;
            case "503":
                imageId = R.mipmap.icon_503d;
                break;
            case "504":
                imageId = R.mipmap.icon_504d;
                break;
            case "507":
                imageId = R.mipmap.icon_507d;
                break;
            case "508":
                imageId = R.mipmap.icon_508d;
                break;
            case "509":
                imageId = R.mipmap.icon_509d;
                break;
            case "510":
                imageId = R.mipmap.icon_510d;
                break;
            case "511":
                imageId = R.mipmap.icon_511d;
                break;
            case "512":
                imageId = R.mipmap.icon_512d;
                break;
            case "513":
                imageId = R.mipmap.icon_513d;
                break;
            case "514":
                imageId = R.mipmap.icon_514d;
                break;
            case "515":
                imageId = R.mipmap.icon_515d;
                break;
            case "900":
                imageId = R.mipmap.icon_900d;
                break;
            case "901":
                imageId = R.mipmap.icon_901d;
                break;
            case "999":
                imageId = R.mipmap.icon_999d;
                break;
            default:
                imageId = R.mipmap.icon_100d;
                break;

        }

        return imageId;
    }

//    public void finish(View view) {
//        tianqicode=editText.getText().toString();
//        analyzeJSONArray(tubiaoDay);
//    }
//
//    String tubiaoDay="{" +
//            "'100':'&#xF101;','101':'&#xF102;','102':'&#xF103;'," +
//            "'103':'&#xF104;','104':'&#xF105;','300':'&#xF10A;'," +
//            "'301':'&#xF10B;','302':'&#xF10C;','303':'&#xF10D;'," +
//            "'304':'&#xF10E;','305':'&#xF10F;','306':'&#xF110;'," +
//            "'307':'&#xF111;','308':'&#xF112;','309':'&#xF113;'," +
//            "'310':'&#xF114;','311':'&#xF115;','312':'&#xF116;'," +
//            "'313':'&#xF117;','314':'&#xF118;','315':'&#xF119;'," +
//            "'316':'&#xF11A;','317':'&#xF11B;','318':'&#xF11C;'," +
//            "'399':'&#xF11F;','400':'&#xF120;','401':'&#xF121;'," +
//            "'402':'&#xF122;','403':'&#xF123;','404':'&#xF124;'," +
//            "'405':'&#xF125;','406':'&#xF126;','407':'&#xF127;'," +
//            "'408':'&#xF128;','409':'&#xF129;','410':'&#xF12A;'," +
//            "'499':'&#xF12D;','500':'&#xF12E;','501':'&#xF12F;'," +
//            "'502':'&#xF130;','503':'&#xF131;','504':'&#xF132;'," +
//            "'507':'&#xF133;','508':'&#xF134;','509':'&#xF135;'," +
//            "'510':'&#xF136;','511':'&#xF137;','512':'&#xF138;'," +
//            "'513':'&#xF139;','514':'&#xF13A;','515':'&#xF13B;'," +
//            "'900':'&#xF144;','901':'&#xF145;'}" ;
//
//    public void analyzeJSONArray(String json) {
//        try {
//            JSONObject jsonObjectALL = new JSONObject(json);
//            String b=jsonObjectALL.getString(tianqicode);
//            textView.setText(Html.fromHtml(b));
//            System.out.println(tianqicode);
//            System.out.println(b+"数据");
//        } catch (Exception e) {
//            //e.printStackTrace();
//            textView.setText(Html.fromHtml("&#xF146;"));//如果未找到则显示N/A
//        }
//    }
    /**
     * 获取晚上深色天气图标
     */
    public static int getNightIconDark(String weather) {
        int imageId;
        switch (weather) {
            case "100":
                imageId = R.mipmap.icon_100n;
                break;
            case "101":
                imageId = R.mipmap.icon_101n;
                break;
            case "102":
                imageId = R.mipmap.icon_102n;
                break;
            case "103":
                imageId = R.mipmap.icon_103n;
                break;
            case "104":
                imageId = R.mipmap.icon_104n;
                break;
            case "200":
                imageId = R.mipmap.icon_200n;
                break;
            case "201":
                imageId = R.mipmap.icon_201n;
                break;
            case "202":
                imageId = R.mipmap.icon_202n;
                break;
            case "203":
                imageId = R.mipmap.icon_203n;
                break;
            case "204":
                imageId = R.mipmap.icon_204n;
                break;
            case "205":
                imageId = R.mipmap.icon_205n;
                break;
            case "206":
                imageId = R.mipmap.icon_206n;
                break;
            case "207":
                imageId = R.mipmap.icon_207n;
                break;
            case "208":
                imageId = R.mipmap.icon_208n;
                break;
            case "209":
                imageId = R.mipmap.icon_209n;
                break;
            case "210":
                imageId = R.mipmap.icon_210n;
                break;
            case "211":
                imageId = R.mipmap.icon_211n;
                break;
            case "212":
                imageId = R.mipmap.icon_212n;
                break;
            case "213":
                imageId = R.mipmap.icon_213n;
                break;
            case "300":
                imageId = R.mipmap.icon_300n;
                break;
            case "301":
                imageId = R.mipmap.icon_301n;
                break;
            case "302":
                imageId = R.mipmap.icon_302n;
                break;
            case "303":
                imageId = R.mipmap.icon_303n;
                break;
            case "304":
                imageId = R.mipmap.icon_304n;
                break;
            case "305":
                imageId = R.mipmap.icon_305n;
                break;
            case "306":
                imageId = R.mipmap.icon_306n;
                break;
            case "307":
                imageId = R.mipmap.icon_307n;
                break;
            case "308":
                imageId = R.mipmap.icon_308n;
                break;
            case "309":
                imageId = R.mipmap.icon_309n;
                break;
            case "310":
                imageId = R.mipmap.icon_310n;
                break;
            case "311":
                imageId = R.mipmap.icon_311n;
                break;
            case "312":
                imageId = R.mipmap.icon_312n;
                break;
            case "313":
                imageId = R.mipmap.icon_313n;
                break;
            case "314":
                imageId = R.mipmap.icon_314n;
                break;
            case "315":
                imageId = R.mipmap.icon_315n;
                break;
            case "316":
                imageId = R.mipmap.icon_316n;
                break;
            case "317":
                imageId = R.mipmap.icon_317n;
                break;
            case "318":
                imageId = R.mipmap.icon_318n;
                break;
            case "399":
                imageId = R.mipmap.icon_399n;
                break;
            case "400":
                imageId = R.mipmap.icon_400n;
                break;
            case "401":
                imageId = R.mipmap.icon_401n;
                break;
            case "402":
                imageId = R.mipmap.icon_402n;
                break;
            case "403":
                imageId = R.mipmap.icon_403n;
                break;
            case "404":
                imageId = R.mipmap.icon_404n;
                break;
            case "405":
                imageId = R.mipmap.icon_405n;
                break;
            case "406":
                imageId = R.mipmap.icon_406n;
                break;
            case "407":
                imageId = R.mipmap.icon_407n;
                break;
            case "408":
                imageId = R.mipmap.icon_408n;
                break;
            case "409":
                imageId = R.mipmap.icon_409n;
                break;
            case "410":
                imageId = R.mipmap.icon_410n;
                break;
            case "499":
                imageId = R.mipmap.icon_499n;
                break;
            case "500":
                imageId = R.mipmap.icon_500n;
                break;
            case "501":
                imageId = R.mipmap.icon_501n;
                break;
            case "502":
                imageId = R.mipmap.icon_502n;
                break;
            case "503":
                imageId = R.mipmap.icon_503n;
                break;
            case "504":
                imageId = R.mipmap.icon_504n;
                break;
            case "507":
                imageId = R.mipmap.icon_507n;
                break;
            case "508":
                imageId = R.mipmap.icon_508n;
                break;
            case "509":
                imageId = R.mipmap.icon_509n;
                break;
            case "510":
                imageId = R.mipmap.icon_510n;
                break;
            case "511":
                imageId = R.mipmap.icon_511n;
                break;
            case "512":
                imageId = R.mipmap.icon_512n;
                break;
            case "513":
                imageId = R.mipmap.icon_513n;
                break;
            case "514":
                imageId = R.mipmap.icon_514n;
                break;
            case "515":
                imageId = R.mipmap.icon_515n;
                break;
            case "900":
                imageId = R.mipmap.icon_900n;
                break;
            case "901":
                imageId = R.mipmap.icon_901n;
                break;
            case "999":
                imageId = R.mipmap.icon_999n;
                break;
            default:
                imageId = R.mipmap.icon_100n;
                break;

        }
        return imageId;
    }

}

