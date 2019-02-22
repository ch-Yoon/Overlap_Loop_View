package com.view.loop.overlap.why.yoon.ch.overlaploopviewlib.utils;

import android.content.Context;
import android.support.annotation.NonNull;

public class DisplayUtil {

    public static float convertToDP(@NonNull Context context, float pixel) {

        return pixel / context.getResources().getDisplayMetrics().density;

    }

}
