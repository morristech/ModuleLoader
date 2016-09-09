package io.github.hotstu.common.util;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;

public class DrawableUtil {
	public static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {  
	    final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
	    DrawableCompat.setTintList(wrappedDrawable, colors);
	    return wrappedDrawable;
	}
}
